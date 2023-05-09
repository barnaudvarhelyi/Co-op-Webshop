package mine.homeworkproject.services;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ProductAPIDto;
import mine.homeworkproject.dtos.ProductByIdResponsDto;
import mine.homeworkproject.dtos.ProductCreateDto;
import mine.homeworkproject.dtos.ProductDto;
import mine.homeworkproject.dtos.ResponseDto;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.ProductRepository;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final UserService userService;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository, UserService userService) {
    this.productRepository = productRepository;
    this.userService = userService;
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public ResponseEntity createProduct(ProductCreateDto productCreateDto,
      HttpServletRequest request) {
    Optional<User> user = userService.getUserByToken(request);
    ResponseDto responseDto;

    if (!user.isPresent()) {
      responseDto = new ResponseDto("User not found!");
      return ResponseEntity.status(404).body(responseDto);
    }

    try {
      Product product = new Product(productCreateDto.getName(), productCreateDto.getDescription(),
          productCreateDto.getPhotoUrl(), productCreateDto.getPurchasePrice(),
          productCreateDto.getStartingPrice());

      ResponseEntity response = validateInputFields(product);
      if (response != null) {
        return response;
      }

      product.setUser(user.get());
      productRepository.save(product);
      return ResponseEntity.status(201).body(new ProductDto(product));
    } catch (ResponseStatusException e) {
      responseDto = new ResponseDto("Product creation error!");
      return ResponseEntity.status(e.getStatus()).body(responseDto);
    }
  }

  @Override
  public ResponseEntity getProductById(Long id) {
    Optional<Product> product = productRepository.findById(id);

    if (!product.isPresent()) {
      ResponseDto responseDto = new ResponseDto("Product not found!");
      return ResponseEntity.status(404).body(responseDto);
    }
    String user;
    if (product.get().getUser() == null) {
      user = "Not given!";
    } else {
      user = userService.findUserById(product.get().getUser()).getUsername();
    }

    ProductByIdResponsDto respons = new ProductByIdResponsDto(product.get(), user);
    return ResponseEntity.status(200).body(respons);
  }

  @Override
  public ResponseEntity deleteProductById(Long id, HttpServletRequest request) {
    Optional<Product> product = productRepository.findById(id);
    Optional<User> user = userService.getUserByToken(request);
    ResponseDto responseDto;

    if (!user.isPresent()){
      return ResponseEntity.status(404).body(new ResponseDto("User not found!"));
    }
    if (!product.isPresent()) {
      return ResponseEntity.status(404).body(new ResponseDto("Product not found!"));
    }
    if (!product.get().getUser().equals(user.get().getId())) {
      return ResponseEntity.status(403).body(new ResponseDto("Access denied!"));
    }
    productRepository.delete(product.get());
    return ResponseEntity.status(200).body(new ResponseDto("Resource deleted successfully!"));
  }

  @Override
  public void getRandomProductsFromAPI() {
    for (ProductAPIDto p : parseRespons(getDataFromAPI())) {
      Product product = new Product(p.getTitle(), p.getDescription(), p.getImage(), p.getPrice(),
          p.getPrice() * 0.8);
      product.setUser(userService.findUserById(1L));
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
    Type productListType = new TypeToken<List<ProductAPIDto>>() {
    }.getType();
    return gson.fromJson(data, productListType);
  }

  private ResponseEntity validateInputFields(Product p) {
    ResponseDto responseDto;

    if (p.getDescription().equals("") || p.getDescription() == null
        || p.getPhotoUrl().equals("") || p.getPhotoUrl() == null
        || p.getName().equals("") || p.getName() == null
        || p.getPurchasePrice() == null
        || p.getStartingPrice() == null
    ) {
      responseDto = new ResponseDto("Please provide valid inputs!");
      return ResponseEntity.status(400).body(responseDto);
    }
    if (p.getPurchasePrice() <= 0 || p.getStartingPrice() <= 0) {
      responseDto = new ResponseDto("Please provide valid numbers!");
      return ResponseEntity.status(400).body(responseDto);
    }

    return null;
  }
}