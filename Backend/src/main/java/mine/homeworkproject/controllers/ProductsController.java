package mine.homeworkproject.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import mine.homeworkproject.dtos.ProductCreateDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

  @PostMapping("/products/create")
  public ProductCreateDto createNewProduct(@RequestBody @Valid ProductCreateDto product, HttpServletRequest request) {
    return new ProductCreateDto();
  }
}
