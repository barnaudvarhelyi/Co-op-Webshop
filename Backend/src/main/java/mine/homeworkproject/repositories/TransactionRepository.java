package mine.homeworkproject.repositories;

import java.util.List;
import mine.homeworkproject.models.Transaction;
import mine.homeworkproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findAllByUser(User user);
}
