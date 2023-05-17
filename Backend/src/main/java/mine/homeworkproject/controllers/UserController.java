package mine.homeworkproject.controllers;

import java.util.HashMap;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import mine.homeworkproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  @PostMapping("/balance")
  public ResponseEntity addBalance(@RequestBody HashMap<String, String> balance, HttpServletRequest request) {
    return userService.addBalance(balance, request);
  }
}
