package mine.homeworkproject.services;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import mine.homeworkproject.dtos.ProductAPIDto;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.repositories.ProductRepository;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> getAll() {
    return productRepository.findAll();
  }

  @Override
  public void getRandomProductsFromAPI() {

    String response = getDataFromAPI();
    List<ProductAPIDto> list = parseRespons(response);

    for (ProductAPIDto p :
        list) {
      Product product = new Product(p.getTitle(), p.getDescription(), p.getImage(), p.getPrice());
      productRepository.save(product);
    }
  }

  private String getDataFromAPI() {
    String apiUrl = "https://fakestoreapi.com/products";
    StringBuilder response = new StringBuilder();

    try {
      URL url = new URL(apiUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        reader.close();

      } else {
        System.out.println("Request failed. Response Code: " + responseCode);
      }
      connection.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response.toString();
  }

  private List<ProductAPIDto> parseRespons(String data) {
    Gson gson = new Gson();
    Type productListType = new TypeToken<List<ProductAPIDto>>() {}.getType();
    return gson.fromJson(data, productListType);
  }
}

