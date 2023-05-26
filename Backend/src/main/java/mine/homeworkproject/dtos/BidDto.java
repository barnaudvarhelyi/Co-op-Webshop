package mine.homeworkproject.dtos;

import mine.homeworkproject.models.Bid;
import mine.homeworkproject.models.User;

public class BidDto {
  private Long id;
  private Double amount;
  private User user;

  public BidDto(Bid bid) {
    this.id = bid.getId();
    this.amount = bid.getAmount();
    this.user = bid.getUser();
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Double getAmount() {
    return amount;
  }
  public void setAmount(Double amount) {
    this.amount = amount;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
}