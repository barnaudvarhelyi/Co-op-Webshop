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
import mine.homeworkproject.repositories.BidRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BidServiceImpl implements BidService{

  private final UserService userService;
  private final ProductService productService;
  private final BidRepository bidRepository;

  public BidServiceImpl(UserService userService, ProductService productService, BidRepository bidRepository) {
    this.userService = userService;
    this.productService = productService;
    this.bidRepository = bidRepository;
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
    if (u.getUploadedProducts().contains(product.getId())) { return ResponseEntity.status(400).body(new ResponseDto("You can not bid your own product!")); }
    if (Objects.isNull(amount.get("amount")) || amount.get("amount") <= 0) { return ResponseEntity.status(400).body(new ResponseDto("Please provide valid input!")); }

    Double amountDb = amount.get("amount");

    try {
      if (amountDb < product.getStartingPrice()) {
        return ResponseEntity.status(400).body(new ResponseDto("The bid amount must start from the Starting Price"));
      }
      List<Bid> usersBidsOnProduct = bidRepository.findAllByUser(u);
      if (usersBidsOnProduct.size() > 0) {
        for (Bid bid : usersBidsOnProduct) {
          u.setPlusBalance(bid.getAmount());
          u.setMinusOnLicit(bid.getAmount());
          bidRepository.delete(bid);
        }
      }
      if (u.getBalance().getBalance() - amountDb < 0 || u.getBalance().getBalance() < product.getStartingPrice()) {
        return ResponseEntity.status(403).body(new ResponseDto("Not enough balance!"));
      } else {
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

    //TODO
    try {
      if (u.getBalance().getBalance() < product.getStartingPrice()) { return ResponseEntity.status(400).body(new ResponseDto("Not enough balance!")); }
      return null;

    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatus()).body(new ResponseDto("Purchase error!"));
    }
  }
//TODO
//  @Override
//  public ResponseEntity setSoldProduct(Product product, User user) {
//    return null;
//  }
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
