package mine.homeworkproject;

import mine.homeworkproject.models.User;
import mine.homeworkproject.models.UserBalance;
import mine.homeworkproject.repositories.BalanceRepository;
import mine.homeworkproject.repositories.UserRepository;
import mine.homeworkproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AppMain implements CommandLineRunner {

  private final UserRepository userRepository;
  private final ProductService productService;
  private final BalanceRepository balanceRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public AppMain(UserRepository userRepository, ProductService productService,
      BalanceRepository balanceRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.productService = productService;
    this.balanceRepository = balanceRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public static void main(String[] args) {
    SpringApplication.run(AppMain.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    UserBalance balance1 = new UserBalance(100.0, 0.0);
    UserBalance balance2 = new UserBalance(100.0, 0.0);
    UserBalance balance3 = new UserBalance(100.0, 0.0);
    balanceRepository.save(balance1);
    balanceRepository.save(balance2);
    balanceRepository.save(balance3);

    userRepository.save(new User("Admin", "admin@gmail.com", passwordEncoder.encode("Admin"), "Admin", balance1));
    userRepository.save(new User("User", "user@gmail.com", passwordEncoder.encode("User"), "User", balance2));
    userRepository.save(new User("User2", "user2@gmail.com", passwordEncoder.encode("User2"), "User", balance3));

    productService.getRandomProductsFromAPI();

    System.out.println("---------- App has been started! ----------");
  }
}
