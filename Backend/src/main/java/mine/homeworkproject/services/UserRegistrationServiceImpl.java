package mine.homeworkproject.services;

import mine.homeworkproject.dtos.BalanceDto;
import mine.homeworkproject.dtos.RegistrationErrorDto;
import mine.homeworkproject.dtos.RegistrationResponseDto;
import mine.homeworkproject.dtos.ResponseDto;
import mine.homeworkproject.dtos.UserRegistrationDto;
import mine.homeworkproject.models.User;
import mine.homeworkproject.models.UserBalance;
import mine.homeworkproject.repositories.BalanceRepository;
import mine.homeworkproject.repositories.UserRepository;
import mine.homeworkproject.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
  private final UserRepository userRepository;
  private final BalanceRepository balanceRepository;
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserRegistrationServiceImpl(UserRepository userRepository,
      BalanceRepository balanceRepository) {
    this.userRepository = userRepository;
    this.balanceRepository = balanceRepository;
  }

  @Override
  public ResponseEntity registerUser(UserRegistrationDto userRegistrationDto) {
    if (userRegistrationDto.getUsername().equals("") || userRegistrationDto.getUsername() == null
        || userRegistrationDto.getEmail().equals("") || userRegistrationDto.getEmail() == null
        || userRegistrationDto.getPassword().equals("") || userRegistrationDto.getPassword() == null
    ) {
      return ResponseEntity.status(400).body(new ResponseDto("Please provide valid inputs!"));
    }

    Boolean usernameExists = checkIfUsernameExists(userRegistrationDto.getUsername());
    Boolean emailExists = checkIfEmailExists(userRegistrationDto.getEmail());

    if (usernameExists || emailExists) {
      String message = "";
      if (usernameExists && emailExists) {
        message += "Username and Email already exists!";
      } else if (usernameExists) {
        message += "Username already exists!";
      } else {
        message += "Email already exists!";
      }
      return ResponseEntity.status(400).body(new ResponseDto(message));
    } else {
      UserBalance newBalance = new UserBalance(0.00);
      balanceRepository.save(newBalance);
      passwordEncoder = new BCryptPasswordEncoder();

      User user = new User(
          userRegistrationDto.getUsername(),
          userRegistrationDto.getEmail(),
          passwordEncoder.encode(userRegistrationDto.getPassword()),
          checkIfDBIsEmpty() ? String.valueOf(UserRole.Admin) : String.valueOf(UserRole.User),
          newBalance);

      userRepository.save(user);
      return ResponseEntity.status(200).body(new RegistrationResponseDto(user.getId(), user.getUsername(), new BalanceDto(newBalance)));
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