package mine.homeworkproject.controllers;

import java.util.List;
import mine.homeworkproject.dtos.ProductDto;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import mine.homeworkproject.services.ProductService;
import mine.homeworkproject.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {
  private final UserService userService;
  private final ProductService productService;

  public APIController(UserService userService, ProductService productService) {
    this.userService = userService;
    this.productService = productService;
  }

  @GetMapping("/api/users/all")
  public List<User> getAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/api/user/{id}")
  public User getUserById(@RequestParam Long id) {
    return userService.findUserById(id);
  }

  @GetMapping("/api/products/all")
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }
}
