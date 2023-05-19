package mine.homeworkproject.controllers;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {
  private final BidService bidService;
  @Autowired
  public BidController(BidService bidService) {
    this.bidService = bidService;
  }

  @PostMapping("/bid")
  public ResponseEntity addBidToProductById(@RequestParam(required = false) Long productId, @RequestBody HashMap<String, Double> amount, HttpServletRequest request) {
    return bidService.addBidToProductById(productId, amount, request);
  }
}
