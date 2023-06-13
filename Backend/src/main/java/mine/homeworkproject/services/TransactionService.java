package mine.homeworkproject.services;

import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;

public interface TransactionService {
  void save(User user, String action, Boolean transaction, Double amount);
}