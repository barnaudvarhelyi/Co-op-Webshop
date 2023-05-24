package mine.homeworkproject.dtos;

import mine.homeworkproject.models.Balance;

public class BalanceDto {

  private Long id;
  private Double balance;
  private Double onLicit;

  public BalanceDto() {
  }
  public BalanceDto(Balance balance) {
    this.id = balance.getId();
    this.balance = balance.getBalance();
    this.onLicit = balance.getOnLicit();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Double getOnLicit() {
    return onLicit;
  }

  public void setOnLicit(Double onLicit) {
    this.onLicit = onLicit;
  }
}
