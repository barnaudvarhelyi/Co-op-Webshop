package mine.homeworkproject.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "balance")
public class Balance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Double balance;
  private Double onLicit;

  @OneToOne(mappedBy = "balance", cascade = CascadeType.REFRESH)
  private User user;
  public Balance() {
    this.balance = 0.00;
    this.onLicit = 0.00;
  }
  public Balance(Double balance) {
    this.balance = balance;
    this.onLicit = 0.00;
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
