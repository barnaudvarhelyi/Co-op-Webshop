package mine.homeworkproject;

import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.UserRepository;
import mine.homeworkproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AppMain implements CommandLineRunner {

  private UserRepository userRepository;
  private ProductService productService;
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public AppMain(UserRepository userRepository, ProductService productService, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.productService = productService;
    this.passwordEncoder = passwordEncoder;
  }

  public static void main(String[] args) {
    SpringApplication.run(AppMain.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//    userRepository.save(new User("Admin", "admin@gmail.com", passwordEncoder.encode("Admin"), "Admin"));
//    userRepository.save(new User("User", "user@gmail.com", passwordEncoder.encode("User"), "User"));

//    productService.getRandomProductsFromAPI();
    System.out.println("---------- App has been started! ----------");
  }
}
