package mine.homeworkproject.services;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ResponseDto;
import mine.homeworkproject.models.Bid;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.BalanceRepository;
import mine.homeworkproject.repositories.BidRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BidOrPurchaseServiceImpl implements BidOrPurchaseService {
  private final UserService userService;
  private final ProductService productService;
  private final BidRepository bidRepository;
  private final BalanceRepository balanceRepository;

  public BidOrPurchaseServiceImpl(UserService userService, ProductService productService, BidRepository bidRepository,
      BalanceRepository balanceRepository) {
    this.userService = userService;
    this.productService = productService;
    this.bidRepository = bidRepository;
    this.balanceRepository = balanceRepository;
  }
  @Override
  public ResponseEntity addBidToProductById(Long productId, HashMap<String, Double> amount, HttpServletRequest request) {
    Object[] response = getUserByTokenAndProductById(productId, request);
    if (response[0] != null) {
      return (ResponseEntity) response[0];
    }
    User u = (User) response[1];
    Product product = (Product) response[2];

    if (product.getExpiresAt() == null) { return ResponseEntity.status(400).body(new ResponseDto("This product is for purchase only!")); }
    if (!product.getForSale()) { return ResponseEntity.status(400).body(new ResponseDto("This item is not for sale!")); }
    if (u.getUploadedProducts().contains(product.getId())) { return ResponseEntity.status(400).body(new ResponseDto("You can not bid your own product!")); }
    if (Objects.isNull(amount.get("amount")) || amount.get("amount") <= 0) { return ResponseEntity.status(400).body(new ResponseDto("Please provide valid input!")); }

    Double amountDb = amount.get("amount");

    try {
      if (amountDb < product.getStartingPrice()) {
        return ResponseEntity.status(400).body(new ResponseDto("The bid amount must start from the Starting Price"));
      }
      if (u.getBalanceAsDto().getBalance() - amountDb < 0 || u.getBalanceAsDto().getBalance() < product.getStartingPrice()) {
        return ResponseEntity.status(403).body(new ResponseDto("Not enough balance!"));
      } else {
        List<Bid> usersBidsOnProduct = bidRepository.findAllByUser(u);
        if (usersBidsOnProduct.size() > 0) {
          for (Bid bid : usersBidsOnProduct) {
            u.setPlusBalance(bid.getAmount());
            u.setMinusOnLicit(bid.getAmount());
            bidRepository.delete(bid);
          }
        }
        u.setMinusBalance(amountDb);
        u.setPlusOnLicit(amountDb);
        Bid newBidOnProduct = new Bid(amountDb, u, product);
        bidRepository.save(newBidOnProduct);
        u.setBids(newBidOnProduct);
        userService.save(u);
        return ResponseEntity.status(200).body(new ResponseDto("Bid was successful!"));
      }
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatus()).body(new ResponseDto("Bid error!"));
    }
  }
  @Override
  public ResponseEntity purchaseProductById(Long productId, HttpServletRequest request) {
    Object[] response = getUserByTokenAndProductById(productId, request);
    if (response[0] != null) {
      return (ResponseEntity) response[0];
    }
    User u = (User) response[1];
    Product product = (Product) response[2];
    if (u.getUploadedProducts().contains(product.getId()) || u.getOwnedProducts().contains(product.getId())) {
      return ResponseEntity.status(400).body(new ResponseDto("You cannot purchase your own item!"));
    }
    if (!product.getForSale()) {
      return ResponseEntity.status(400).body(new ResponseDto("This item is not for sale!"));
    }
    try {
      if (u.getBalanceAsDto().getBalance() < product.getPurchasePrice()) { return ResponseEntity.status(400).body(new ResponseDto("Not enough balance!")); }
      setSoldProduct(product, u);
      return ResponseEntity.status(200).body(new ResponseDto("You successfully purchased this item!"));
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatus()).body(new ResponseDto("Purchase error!"));
    }
  }

  @Override
  public void handleLeftBidsOnProductAndProduct(Product product, User customer) {
    if (product.getBids().size() > 0) {
      for (Bid bid : product.getBids()) {
        bid.getUser().setMinusOnLicit(bid.getAmount());
        bid.getUser().setPlusBalance(bid.getAmount());
        bidRepository.delete(bid);
      }
    }

    product.setOwner(customer);
    product.setPurchasePrice(0.00);
    product.setStartingPrice(0.00);
    product.resetBids();
    product.setForSale(false);
    productService.saveProduct(product);
  }
  private void setSoldProduct(Product product, User customer) {
    User owner = userService.findUserById(product.getOwner());
    owner.setPlusBalance(product.getPurchasePrice());
    userService.save(owner);

    customer.setMinusBalance(product.getPurchasePrice());
    customer.setOwnedProducts(product);
    userService.save(customer);

    handleLeftBidsOnProductAndProduct(product, customer);
  }
  private Object[] getUserByTokenAndProductById(Long productId, HttpServletRequest request) {
    Optional<User> user = userService.getUserByToken(request);
    if (!user.isPresent()) {
      return new Object[] {ResponseEntity.status(404).body(new ResponseDto("User not found!"))};
    }
    Product product = productService.findProductById(productId);
    if (product == null || productId == null) {
      return new Object[] {ResponseEntity.status(404).body(new ResponseDto("Product not found!"))};
    }
    return new Object[] {null, user.get(), product};
  }
}
