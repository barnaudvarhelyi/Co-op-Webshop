package mine.homeworkproject.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.dtos.ResponseDto;
import mine.homeworkproject.dtos.UserProfileResponsDto;
import mine.homeworkproject.models.Product;
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
    List<Product> products = productRepository.findAllByUser(user.get());
    return ResponseEntity.status(200).body(new UserProfileResponsDto(user.get().getUsername(),
        products.size(), products));
  }
}
