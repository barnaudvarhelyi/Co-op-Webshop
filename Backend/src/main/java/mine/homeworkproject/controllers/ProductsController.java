package mine.homeworkproject.controllers;

import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ProductCreateDto;
import mine.homeworkproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

  private final ProductService productService;
  @Autowired
  public ProductsController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping("/products/create")
  public ResponseEntity createNewProduct(@RequestBody ProductCreateDto product, HttpServletRequest request) {
    return productService.createProduct(product, request);
  }


  @GetMapping("/products/{id}")
  public ResponseEntity getProductById(@PathVariable Long id) {
    return productService.getProductById(id);
  }

  @DeleteMapping("/products/delete/{id}")
  public ResponseEntity deleteProductById(@PathVariable Long id, HttpServletRequest request) {
    return productService.deleteProductById(id, request);
  }
}
