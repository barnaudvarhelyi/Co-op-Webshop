package mine.homeworkproject;

import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AppMain implements CommandLineRunner {

  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  public AppMain(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public static void main(String[] args) {
    SpringApplication.run(AppMain.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    userRepository.save(new User("Admin", "admin@gmail.com", passwordEncoder.encode("Admin"), "Admin"));
    userRepository.save(new User("User", "user@gmail.com", passwordEncoder.encode("User"), "User"));
  }
}
