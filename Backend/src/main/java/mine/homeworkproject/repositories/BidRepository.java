package mine.homeworkproject.repositories;

import java.util.List;
import mine.homeworkproject.models.Bid;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BidRepository extends JpaRepository<Bid, Long> {
  List<Bid> findAllByProduct(Product product);
  List<Bid> findAllByUser(User user);

  @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
      "FROM Bid b " +
      "WHERE b.product = :product " +
      "AND b.user = :user " +
      "AND b.amount = (SELECT MAX(b2.amount) FROM Bid b2 WHERE b2.product = :product)")
  boolean isHighestBidderOnProduct(@Param("product") Product product, @Param("user") User user);
}
