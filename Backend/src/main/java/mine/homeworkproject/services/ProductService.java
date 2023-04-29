package mine.homeworkproject.services;

import java.util.List;
import mine.homeworkproject.models.Product;


public interface ProductService {
  void getRandomProductsFromAPI();

  List<Product> getAll();
}
