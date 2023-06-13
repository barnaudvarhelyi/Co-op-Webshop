package mine.homeworkproject.controllers;

import java.util.List;
import mine.homeworkproject.models.User;
import mine.homeworkproject.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {
  private final UserService userService;

  public APIController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/api/users/all")
  public List<User> getAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/api/user/{id}")
  public User getUserById(@RequestParam Long id) {
    return userService.findUserById(id);
  }
}
