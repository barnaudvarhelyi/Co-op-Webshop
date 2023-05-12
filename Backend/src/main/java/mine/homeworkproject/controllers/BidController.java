package mine.homeworkproject.controllers;

import mine.homeworkproject.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BidController {
  private BidService bidService;
  @Autowired
  public BidController(BidService bidService) {
    this.bidService = bidService;
  }
}
