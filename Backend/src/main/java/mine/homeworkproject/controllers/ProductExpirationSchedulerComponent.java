package mine.homeworkproject.controllers;

import java.time.LocalDateTime;
import java.util.List;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.repositories.ProductRepository;
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
//  @Autowired
//  private ProductService productService;

  @Scheduled(fixedDelay = 60000)
  public void forTestCheckExpiredProducts() {

    LocalDateTime currentDateTime = LocalDateTime.now();
    List<Product> expiredProducts = productRepository.findByExpiresAtNotNull();

    for (Product product : expiredProducts) {
      if (currentDateTime.isAfter(product.getExpiresAt())) {
        //TODO update to set the product to owner!
        System.out.println("This product is expired:" + product.getName());
        productRepository.delete(product);
      }
    }
  }

  //TODO every day should run for some times and check!
//  @Scheduled(fixedDelay = 86400000)
//  public void checkExpiredProducts() {
//
//  }
}
