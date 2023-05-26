package mine.homeworkproject.services;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ProductCreateDto;
import mine.homeworkproject.models.Product;
import org.springframework.http.ResponseEntity;


public interface ProductService {
  void getRandomProductsFromAPI();

  List<Product> getAllAvailableProducts();

  ResponseEntity createProduct(ProductCreateDto product, HttpServletRequest request);

  ResponseEntity getProductById(Long id);
  Product findProductById(Long id);

  ResponseEntity deleteProductById(Long id, HttpServletRequest request);

  ResponseEntity editProductById(Long id, ProductCreateDto product, HttpServletRequest request);

  ResponseEntity searchItemByStr(String seachItem);

  ResponseEntity sortProducts(String direction);

  void saveProduct(Product product);
}
