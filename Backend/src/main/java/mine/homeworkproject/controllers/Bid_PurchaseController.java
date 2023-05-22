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
public class Bid_PurchaseController {
  private final BidService bidService;
  @Autowired
  public Bid_PurchaseController(BidService bidService) {
    this.bidService = bidService;
  }

  @PostMapping("/purchase")
  public ResponseEntity purchaseProductById(@RequestParam(required = false) Long productId, HttpServletRequest request) {
    return bidService.purchaseProductById(productId, request);
  }
  @PostMapping("/bid")
  public ResponseEntity addBidToProductById(@RequestParam(required = false) Long productId, @RequestBody HashMap<String, Double> amount, HttpServletRequest request) {
    return bidService.addBidToProductById(productId, amount, request);
  }

  //TODO
  @PostMapping("/bid/cancel")
  public ResponseEntity cancelBidOnProductById(@RequestParam(required = false) Long productId, HttpServletRequest request) {
    return null;
  }
  @PostMapping("/bid/cancel/all")
  public ResponseEntity cancelAllBids(HttpServletRequest request) {
    return null;
  }


}
