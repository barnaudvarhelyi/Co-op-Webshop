package mine.homeworkproject.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "balance")
public class UserBalance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Double balance;
  private Double onLicit;

  @OneToOne(mappedBy = "balance", cascade = CascadeType.REFRESH)
  private User user;
  public UserBalance() {
    this.balance = 0.00;
    this.onLicit = 0.00;
  }
  public UserBalance(Double balance, Double onLicit) {
    this.balance = balance;
    this.onLicit = onLicit;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
