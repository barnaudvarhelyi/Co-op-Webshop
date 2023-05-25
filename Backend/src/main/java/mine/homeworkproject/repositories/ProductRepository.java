package mine.homeworkproject.repositories;

import java.util.List;
import java.util.Optional;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("SELECT p FROM Product p WHERE p.uploader = p.owner")
  List<Product> findAllByUploaderNotEqualsOwner();
  @Query("SELECT p FROM Product p ORDER BY function('RAND')")
  List<Product> findRandomProducts(Pageable pageable);
  @Query("SELECT p FROM Product p WHERE p.uploader = :uploader AND p.owner.id = p.uploader.id")
  List<Product> findAllByUploaderNotEqualsOwner(@Param("uploader") User uploader);

  Optional<Product> findById(Long id);
  List<Product> findAllByOwner(User user);
  List<Product> findByExpiresAtNotNull();
}