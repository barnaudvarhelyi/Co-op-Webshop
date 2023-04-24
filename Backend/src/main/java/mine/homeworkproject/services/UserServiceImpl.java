package mine.homeworkproject.services;

import java.util.List;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () ->
                new RuntimeException(
                    username + "no_user_for_username_response"));

  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User findUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }
}
