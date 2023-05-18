package mine.homeworkproject.services;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
  User findUserByUsername(String username);

  List<User> findAllUsers();

  User findUserById(Long id);

  Optional<User> getUserByToken(HttpServletRequest request);

  ResponseEntity getUserProfile(HttpServletRequest request);

  ResponseEntity addBalance(HashMap<String, String> balance, HttpServletRequest request);

  ResponseEntity getUserProfileById(Long id);
}
