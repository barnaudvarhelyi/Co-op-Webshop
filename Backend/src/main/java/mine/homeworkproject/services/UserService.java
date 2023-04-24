package mine.homeworkproject.services;

import java.util.List;
import mine.homeworkproject.models.User;

public interface UserService {
  User findUserByUsername(String username);

  List<User> findAllUsers();

  User findUserById(Long id);
}
