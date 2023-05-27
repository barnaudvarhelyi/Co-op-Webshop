package mine.homeworkproject.services;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import org.springframework.http.ResponseEntity;

public interface BidOrPurchaseService {
  ResponseEntity addBidToProductById(Long productId, HashMap<String, Double> amount, HttpServletRequest request);
  ResponseEntity purchaseProductById(Long productId, HttpServletRequest request);
  void setSoldProduct(Product product, User user);
}