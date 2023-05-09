package mine.homeworkproject.controllers;

import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/profile")
  public ResponseEntity userProfile(HttpServletRequest request) {
    return userService.getUserProfile(request);
  }
}
