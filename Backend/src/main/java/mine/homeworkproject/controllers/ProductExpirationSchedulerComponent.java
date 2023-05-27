package mine.homeworkproject.controllers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mine.homeworkproject.models.Bid;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.ProductRepository;
import mine.homeworkproject.services.BidOrPurchaseService;
import mine.homeworkproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//@EnableScheduling
public class ProductExpirationSchedulerComponent {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private BidOrPurchaseService bidOrPurchaseService;

  //TODO test this
  @Scheduled(fixedDelay = 60000)
  public void forTestCheckExpiredProducts() {

    LocalDateTime currentDateTime = LocalDateTime.now();
    List<Product> ableToExpireProducts = productRepository.findByExpiresAtNotNull();

    for (Product product : ableToExpireProducts) {
      if (currentDateTime.isAfter(product.getExpiresAt())) {
        User firstHighestBidder = product.getBids().stream()
            .collect(Collectors.groupingBy(Bid::getAmount))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .orElse(Collections.emptyList())
            .stream()
            .map(Bid::getUser)
            .findFirst()
            .orElse(null);

        if (firstHighestBidder != null) {
          bidOrPurchaseService.setSoldProduct(product, firstHighestBidder);
        } else {
          //If no one bid for the product
          product.setForSale(true);
          product.setExpiresAt(null);
          product.setStartingPrice(null);
          productRepository.save(product);
        }
      }
    }
  }

  //TODO every day should run for some times and check!
//  @Scheduled(fixedDelay = 86400000)
//  public void checkExpiredProducts() {
//
//  }
}
