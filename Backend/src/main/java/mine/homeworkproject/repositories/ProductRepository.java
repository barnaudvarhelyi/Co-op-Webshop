package mine.homeworkproject.repositories;

import java.util.List;
import java.util.Optional;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findById(Long id);
  List<Product> findAllByUploader(User user);
  List<Product> findByExpiresAtNotNull();
}
