package mine.homeworkproject.services;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ProductCreateDto;
import mine.homeworkproject.models.Product;
import org.springframework.http.ResponseEntity;


public interface ProductService {
  void getRandomProductsFromAPI();

  List<Product> getAllProducts();

  ResponseEntity createProduct(ProductCreateDto product, HttpServletRequest request);

  ResponseEntity getProductById(Long id);
}
