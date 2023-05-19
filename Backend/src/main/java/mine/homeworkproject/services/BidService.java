package mine.homeworkproject.services;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface BidService {

  ResponseEntity addBidToProductById(Long productId, HashMap<String, Double> amount, HttpServletRequest request);
}
