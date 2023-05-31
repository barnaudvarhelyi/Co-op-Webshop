package mine.homeworkproject.controllers;

import mine.homeworkproject.repositories.BalanceRepository;
import mine.homeworkproject.repositories.BidRepository;
import mine.homeworkproject.services.BidOrPurchaseService;
import mine.homeworkproject.services.UserService;
import java.time.LocalDateTime;
import java.util.List;
import mine.homeworkproject.models.Bid;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.ProductRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ProductExpirationSchedulerComponent {
  private final ProductRepository productRepository;
  private final BalanceRepository balanceRepository;
  private final UserService userService;
  private final BidRepository bidRepository;
  private final BidOrPurchaseService bidOrPurchaseService;

  public ProductExpirationSchedulerComponent(ProductRepository productRepository,
      BalanceRepository balanceRepository, UserService userService,
      BidRepository bidRepository, BidOrPurchaseService bidOrPurchaseService) {
    this.productRepository = productRepository;
    this.balanceRepository = balanceRepository;
    this.userService = userService;
    this.bidRepository = bidRepository;
    this.bidOrPurchaseService = bidOrPurchaseService;
  }

  @Scheduled(fixedDelay = 30000)
  public void checkExpiredProducts() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    List<Product> ableToExpireProducts = productRepository.findAllByExpiresAtNotNullAndForSale(true);

    System.out.println(ableToExpireProducts.size() + " is on licit!");
    for (Product product : ableToExpireProducts) {

      if (currentDateTime.isAfter(product.getExpiresAt())) {
        try {
          Object[] details = getFirstHighestBidderForProduct(product);
          if (details[0] != null) {
            User winner = (User) details[0];
            Bid highestBid = (Bid) details[1];
            Double amount = (Double) details[2];

            setSoldProduct(product, winner, highestBid, amount);
          } else {
            //If no one bid for the product
            productRepository.delete(product);
          }
        } catch (Exception e) {
          System.out.println("Error: " + e);
          throw new RuntimeException(e);
        }
      }
    }
  }

  private Object[] getFirstHighestBidderForProduct(Product product) {
    List<Bid> bids = product.getBids();
    if (bids.size() > 0) {
      User firstHighestBidder = null;
      Bid highestBid = null;
      Double highestAmount = 0.00;
      for (Bid bid : bids) {
        if (bid.getAmount() > highestAmount) {
          highestAmount = bid.getAmount();
          firstHighestBidder = bid.getUser();
          highestBid = bid;
        }
      }
      return new Object[] {firstHighestBidder, highestBid, highestAmount};
    } else {
      return new Object[] {null};
    }
  }
  //TODO finnish
  private void setSoldProduct(Product product, User customer, Bid highestBid, Double amount) {
    User owner = userService.findUserById(product.getOwner());
    owner.setPlusBalance(amount);
    balanceRepository.save(owner.getBalance());

    User customerWithOwnedProducts = userService.findUserByIdWithOwnedProducts(customer.getId());
    customerWithOwnedProducts.setMinusOnLicit(amount);
    customerWithOwnedProducts.getOwnedProducts().add(product);
    userService.save(customer);
    balanceRepository.save(customerWithOwnedProducts.getBalance());

    product.getBids().remove(highestBid);
    productRepository.save(product);

    bidRepository.delete(highestBid);

    bidOrPurchaseService.handleLeftBidsOnProductAndProduct(product, customer);
  }
  //TODO every day should run for some times and check!
//  @Scheduled(fixedDelay = 86400000)
//  public void checkExpiredProducts() {
//
//  }
}
