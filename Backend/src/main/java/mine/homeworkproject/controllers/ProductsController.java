package mine.homeworkproject.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import mine.homeworkproject.dtos.ProductCreateDto;
import mine.homeworkproject.services.ProductService;
import mine.homeworkproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

  private final ProductService productService;
  @Autowired
  public ProductsController(ProductService productService, UserService userService) {
    this.productService = productService;
  }

  @PostMapping("/products/create")
  public ResponseEntity createNewProduct(@RequestBody @Valid ProductCreateDto product, HttpServletRequest request) {
    return productService.createProduct(product, request);
  }
}
