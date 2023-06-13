package mine.homeworkproject.controllers;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.services.BidOrPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidOrPurchaseController {
  private final BidOrPurchaseService bidOrPurchaseService;
  @Autowired
  public BidOrPurchaseController(BidOrPurchaseService bidOrPurchaseService) {
    this.bidOrPurchaseService = bidOrPurchaseService;
  }
  @PostMapping("/purchase")
  public ResponseEntity purchaseProductById(@RequestParam(required = false) Long productId, HttpServletRequest request) {
    return bidOrPurchaseService.purchaseProductById(productId, request);
  }

  @PostMapping("/bid")
  public ResponseEntity addBidToProductById(@RequestParam(required = false) Long productId, @RequestBody HashMap<String, Double> amount, HttpServletRequest request) {
    return bidOrPurchaseService.addBidToProductById(productId, amount, request);
  }
}
