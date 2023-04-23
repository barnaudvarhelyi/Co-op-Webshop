package mine.homeworkproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
public class UserServiceImpl {
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
}
