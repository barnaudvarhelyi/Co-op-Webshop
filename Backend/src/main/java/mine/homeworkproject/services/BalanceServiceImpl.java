package mine.homeworkproject.services;

import mine.homeworkproject.models.Balance;
import mine.homeworkproject.repositories.BalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService{

  private final BalanceRepository balanceRepository;

  public BalanceServiceImpl(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  @Override
  public void saveBalance(Balance balance) {
    balanceRepository.save(balance);
  }
}
