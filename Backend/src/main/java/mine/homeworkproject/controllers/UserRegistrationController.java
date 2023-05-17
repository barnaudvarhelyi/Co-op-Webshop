package mine.homeworkproject.controllers;

import mine.homeworkproject.dtos.UserRegistrationDto;
import mine.homeworkproject.services.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {
  private final UserRegistrationService userRegistrationService;

  @Autowired
  public UserRegistrationController(UserRegistrationService userRegistrationService){
    this.userRegistrationService = userRegistrationService;
  }

  @PostMapping("/register")
  public ResponseEntity registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
    return userRegistrationService.registerUser(userRegistrationDto);
  }
}
