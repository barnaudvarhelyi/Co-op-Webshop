package mine.homeworkproject.services;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ProductAPIDto;
import mine.homeworkproject.dtos.ProductAllDto;
import mine.homeworkproject.dtos.ProductByIdResponseDto;
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
import org.springframework.data.domain.PageRequest;
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
  public List<ProductAllDto> getAllAvailableProducts() {
    return productRepository.findAllByForSale(true)
        .stream()
        .map(ProductAllDto::new)
        .collect(Collectors.toList());
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
      ResponseEntity response = validateInputFields(productCreateDto);
      if (response != null) {
        return response;
      }
      LocalDateTime expiresAt;
      Double startingPrice;

      if (productCreateDto.getForBid()) {
        String expires = productCreateDto.getExpiresAt();
        startingPrice = productCreateDto.getStartingPrice();
        switch (expires) {
          case "two_minutes": { expiresAt = LocalDateTime.now().plusMinutes(2); break; }
          case "five_minutes": { expiresAt = LocalDateTime.now().plusMinutes(5); break; }
          case "one_day": { expiresAt = LocalDateTime.now().plusDays(1); break; }
          case "three_days": { expiresAt = LocalDateTime.now().plusDays(3); break; }
          case "one_week": { expiresAt = LocalDateTime.now().plusWeeks(1); break; }
          case "two_weeks": { expiresAt = LocalDateTime.now().plusWeeks(2); break; }
          case "one_month": { expiresAt = LocalDateTime.now().plusMonths(1); break; }
          default: { return ResponseEntity.status(400).body(new ResponseDto("Please provide a valid expiration time!")); }
        }
        expiresAt = expiresAt.plusDays(1).withHour(12).withMinute(0).withSecond(0);
      }
      else {
        startingPrice = null;
        expiresAt = null;
      }
      Product product = new Product
          (productCreateDto.getName(),
          productCreateDto.getDescription(),
          productCreateDto.getPhotoUrl(),
          productCreateDto.getPurchasePrice(),
          startingPrice,
          expiresAt);

      product.setUploader(user.get());
      product.setOwner(user.get());
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
    Long userId;
    if (product.get().getUploader() == null) {
      user = "Not given!";
      userId = 1L;
    } else {
      User u = userService.findUserById(product.get().getUploader());
      user = u.getUsername();
      userId = u.getId();
    }

    List<ProductDto> randomProducts = productRepository.findRandomProducts(PageRequest.of(0, 8))
        .stream()
        .map(ProductDto::new)
        .collect(Collectors.toList());
    ProductByIdResponseDto response = new ProductByIdResponseDto(product.get(), user, userId, randomProducts);
    return ResponseEntity.status(200).body(response);
  }
  @Override
  public Product findProductById(Long id) {
    return productRepository.findById(id).orElse(null);
  }
  @Override
  public ResponseEntity deleteProductById(Long id, HttpServletRequest request) {
    Object[] response = getUserProductAndAccess(id, request);
    if (response[0] != null) {
      return (ResponseEntity) response[0];
    }
    Product product = (Product) response[2];
    productRepository.delete(product);
    return ResponseEntity.status(200).body(new ResponseDto("Resource deleted successfully!"));
  }
  @Override
  public ResponseEntity editProductById(Long id, ProductCreateDto productCreateDto,
      HttpServletRequest request) {
    Object[] response = getUserProductAndAccess(id, request);
    if (response[0] != null) {
      return (ResponseEntity) response[0];
    }
    User user = (User) response[1];
    Product product = (Product) response[2];

    if (product.getExpiresAt() != null && product.getBids().size() > 0) {
      return ResponseEntity.status(400).body(new ResponseDto("Product cannot be edited after someone has bid on it"));
    }

    product.setUploader(user);
    product.setName(productCreateDto.getName());
    product.setDescription(productCreateDto.getDescription());
    product.setPhotoUrl(productCreateDto.getPhotoUrl());
    product.setPurchasePrice(productCreateDto.getPurchasePrice());
    product.setStartingPrice(productCreateDto.getStartingPrice());
    return ResponseEntity.status(200).body(productRepository.save(product));
  }
  @Override
  public ResponseEntity searchItemByStr(String searchItem) {
    return ResponseEntity
        .status(200)
        .body(productRepository.findAllByForSale(true).stream()
            .filter(p -> p.getName().toLowerCase().contains(searchItem.toLowerCase()) || p.getDescription().toLowerCase().contains(searchItem.toLowerCase()))
            .collect(Collectors.toList()));
  }
  @Override
  public ResponseEntity sortProducts(String direction) {
    ResponseEntity response;
    if (direction.equals("asc")){
      response = ResponseEntity.status(200).body(productRepository.findAllByForSale(true).stream()
          .sorted(Comparator.comparingDouble(Product::getPurchasePrice))
          .collect(Collectors.toList()));
    } else if (direction.equals("desc")) {
      response = ResponseEntity.status(200).body(productRepository.findAllByForSale(true).stream()
          .sorted(Comparator.comparingDouble(Product::getPurchasePrice).reversed())
          .collect(Collectors.toList()));
    } else {
       response = ResponseEntity.status(400).body(new ResponseDto("Bad request!"));
    }
    return response;
  }
  @Override
  public void saveProduct(Product product) {
    productRepository.save(product);
  }
  @Override
  public void getRandomProductsFromAPI() {
    int i = 0;
    for (ProductAPIDto p : parseResponse(getDataFromAPI())) {
      Product product = new Product(p.getTitle(), p.getDescription(), p.getImage(), p.getPrice(), p.getPrice() * 0.7, null);
      User u = userService.findUserById(1L);
      product.setUploader(u);
      product.setOwner(u);
      if (i % 2 == 0) { product.setExpiresAt(product.getCreatedAt().plusDays(1).withHour(12).withMinute(0).withSecond(0)); }
      else { product.setStartingPrice(null); }
      productRepository.save(product);
      i++;
    }
  }
  private Object[] getUserProductAndAccess(Long id, HttpServletRequest request) {
    Optional<Product> product = productRepository.findById(id);
    Optional<User> user = userService.getUserByToken(request);

    if (!user.isPresent()) {
      return new Object[]{ResponseEntity.status(404).body(new ResponseDto("User not found!"))};
    }
    if (!product.isPresent()) {
      return new Object[]{ResponseEntity.status(404).body(new ResponseDto("Product not found!"))};
    }
    if (!product.get().getUploader().equals(user.get().getId())) {
      return new Object[]{ResponseEntity.status(403).body(new ResponseDto("Access denied!"))};
    }
    return new Object[]{null, user.get(), product.get()};
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
  private List<ProductAPIDto> parseResponse(String data) {
    Gson gson = new Gson();
    Type productListType = new TypeToken<List<ProductAPIDto>>() {
    }.getType();
    return gson.fromJson(data, productListType);
  }
  private ResponseEntity validateInputFields(ProductCreateDto p) {
    ResponseDto responseDto;

    if (p.getDescription().equals("") || p.getDescription() == null
        || p.getPhotoUrl().equals("") || p.getPhotoUrl() == null
        || p.getName().equals("") || p.getName() == null
        || Objects.isNull(p.getPurchasePrice())
        || p.getForBid() && Objects.isNull(p.getStartingPrice())
    ) {
      responseDto = new ResponseDto("Please provide valid inputs!");
      return ResponseEntity.status(400).body(responseDto);
    }
    if (p.getForBid()) {
      if (p.getStartingPrice() <= 0) {
        responseDto = new ResponseDto("Please provide valid numbers!");
        return ResponseEntity.status(400).body(responseDto);
      }
    }
    if (p.getPurchasePrice() <= 0) {
      responseDto = new ResponseDto("Please provide valid numbers!");
      return ResponseEntity.status(400).body(responseDto);
    }
    return null;
  }
}