package mine.homeworkproject.services;

import mine.homeworkproject.dtos.UserRegistrationDto;
import org.springframework.http.ResponseEntity;

public interface UserRegistrationService {
  ResponseEntity registerUser(UserRegistrationDto userRegistrationDto);
}
