package mine.homeworkproject.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ProductDto;
import mine.homeworkproject.dtos.ResponseDto;
import mine.homeworkproject.dtos.UserByIdResponseDto;
import mine.homeworkproject.dtos.UserProfileResponseDto;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.ProductRepository;
import mine.homeworkproject.repositories.UserRepository;
import mine.homeworkproject.security.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository) {
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @Override
  public User findUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new RuntimeException(
                    username + " is not found!"));

  }
  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }
  @Override
  public User findUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }
  @Override
  public Optional<User> getUserByToken(HttpServletRequest request) {
    String header = request.getHeader(JwtProperties.HEADER_STRING);

    if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
      return Optional.empty();
    }
    String token =
        request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

    String username =
        JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
            .build()
            .verify(token)
            .getSubject();

    if (username != null) {
      return userRepository.findByUsername(username);
    }
    return Optional.empty();
  }
  @Override
  public ResponseEntity getUserProfile(HttpServletRequest request) {
    Optional<User> user = getUserByToken(request);
    if (!user.isPresent()) {
      ResponseDto response = new ResponseDto("User not found!");
      return ResponseEntity.status(404).body(response);
    }
    List<ProductDto> uploadedProducts = productRepository.findAllByUploaderAndAvailable(user.get()).stream()
        .map(ProductDto::new)
        .collect(Collectors.toList());;
    List<ProductDto> ownedProducts = productRepository.findAllByOwnerNotEqualsUploader(user.get()).stream()
        .map(ProductDto::new)
        .collect(Collectors.toList());

    return ResponseEntity.status(200).body(
      new UserProfileResponseDto(
        user.get().getUsername(),
        uploadedProducts.size(),
        uploadedProducts,
        user.get().getBalance(),
        ownedProducts,
        ownedProducts.size()
      )
    );
  }
  @Override
  public ResponseEntity addBalance(HashMap<String, String> balance, HttpServletRequest request) {
    Optional<User> user = getUserByToken(request);
    if (!user.isPresent()) {
      ResponseDto response = new ResponseDto("User not found!");
      return ResponseEntity.status(404).body(response);
    }
    Double balanceDb;
    try {
      balanceDb = Double.parseDouble(balance.get("balance"));
    } catch (NumberFormatException nfe) {
      ResponseDto response = new ResponseDto("Please provide valid amount!");
      return ResponseEntity.status(404).body(response);
    }
    user.get().setPlusBalance(balanceDb);
    userRepository.save(user.get());
    return ResponseEntity.status(200).body(new ResponseDto("Balance added successfully!"));
  }
  @Override
  public ResponseEntity getUserProfileById(String username) {
    User user = findUserByUsername(username);
    if (user == null) {
      return ResponseEntity.status(404).body(new ResponseDto("User not found!"));
    }
    List<ProductDto> productsDto = productRepository.findAllByUploaderAndAvailable(user)
        .stream()
        .map(ProductDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(new UserByIdResponseDto(user, productsDto));
  }
  @Override
  public void save(User user) {
    userRepository.save(user);
  }
}