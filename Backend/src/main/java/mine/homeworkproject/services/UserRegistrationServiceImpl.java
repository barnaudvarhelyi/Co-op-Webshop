package mine.homeworkproject.services;

import mine.homeworkproject.dtos.RegistrationErrorDto;
import mine.homeworkproject.dtos.RegistrationResponseDto;
import mine.homeworkproject.dtos.UserRegistrationDto;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.UserRepository;
import mine.homeworkproject.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
  private final UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserRegistrationServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public ResponseEntity registerUser(UserRegistrationDto userRegistrationDto) {
    Boolean usernameExists = checkIfUsernameExists(userRegistrationDto.getUsername());
    Boolean emailExists = checkIfEmailExists(userRegistrationDto.getEmail());

    if (usernameExists || emailExists) {
      RegistrationErrorDto registrationErrorDto = new RegistrationErrorDto();
      if (usernameExists && emailExists) {
        registrationErrorDto.setUsernameError("Username already exists!");
      }
      if (usernameExists) {
        registrationErrorDto.setUsernameError("Username already exists!");
      }
      if (emailExists) {
        registrationErrorDto.setEmailError("Email already exists!");
      }

      return ResponseEntity.status(400).body(registrationErrorDto);

    } else {
      User user = new User();
      passwordEncoder = new BCryptPasswordEncoder();

      user.setUsername(userRegistrationDto.getUsername());
      user.setEmail(userRegistrationDto.getEmail());
      user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

      if (checkIfDBIsEmpty()) {
        user.setRole(String.valueOf(UserRole.Admin));
      } else {
        user.setRole(String.valueOf(UserRole.User));
      }

      userRepository.save(user);
      return ResponseEntity.status(200).body(new RegistrationResponseDto(user.getId(), user.getUsername()));
    }
  }

  private Boolean checkIfUsernameExists(String username) {
    return userRepository.findByUsername(username).orElse(null) != null;
  }
  private Boolean checkIfEmailExists(String email) {
    return userRepository.findByEmail(email).orElse(null) != null;
  }
  private Boolean checkIfDBIsEmpty() {
    return userRepository.findAll().isEmpty();
  }
}