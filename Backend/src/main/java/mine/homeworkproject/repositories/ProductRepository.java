package mine.homeworkproject.repositories;

import java.util.List;
import java.util.Optional;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("SELECT p FROM Product p WHERE p.uploader = p.owner")
  List<Product> findAllByUploaderNotEqualsOwner();
  @Query("SELECT p FROM Product p ORDER BY function('RAND')")
  List<Product> findRandomProducts(Pageable pageable);

  Optional<Product> findById(Long id);
  List<Product> findAllByUploader(User user);
  List<Product> findAllByOwner(User user);
  List<Product> findByExpiresAtNotNull();
}