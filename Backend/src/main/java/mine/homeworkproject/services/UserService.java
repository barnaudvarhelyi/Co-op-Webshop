package mine.homeworkproject.services;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.models.User;

public interface UserService {
  User findUserByUsername(String username);

  List<User> findAllUsers();

  User findUserById(Long id);

  Optional<User> getUserByToken(HttpServletRequest request);
}
