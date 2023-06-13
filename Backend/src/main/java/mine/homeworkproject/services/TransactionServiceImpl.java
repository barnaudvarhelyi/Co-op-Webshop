package mine.homeworkproject.services;

import mine.homeworkproject.models.Transaction;
import mine.homeworkproject.models.User;
import mine.homeworkproject.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  @Autowired
  public TransactionServiceImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public void save(User user, String action, Boolean transaction, Double amount
  ) {
    transactionRepository.save(
        new Transaction(
            user,
            action,
            transaction,
            amount
        )
    );
  }
}
