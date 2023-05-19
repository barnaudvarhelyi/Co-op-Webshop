package mine.homeworkproject.repositories;

import java.util.List;
import mine.homeworkproject.models.Bid;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
  List<Bid> findAllByProduct(Product product);
  List<Bid> findAllByUser(User user);
}
