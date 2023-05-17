package mine.homeworkproject.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import mine.homeworkproject.dtos.BalanceDto;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String email;
  private String password;
  private String role;
  @OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH)
  private List<Product> products;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "balance_id")
  private UserBalance balance;

  @OneToMany(mappedBy = "user")
  private List<Bid> bids;

  public User() {
  }

  public User(String username, String email, String password, String role, UserBalance balance) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.balance = balance;
    this.bids = new ArrayList<>();
  }

  public User(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
  public BalanceDto getBalance() {
    return new BalanceDto(this.balance);
  }
  public void setBalance(Double balance) {
    this.balance.setBalance(this.balance.getBalance() + balance);
  }
  public void setBalance(UserBalance balance) {this.setBalance(balance);}
  public List<Bid> getBids() {
    return bids;
  }
  public void setBids(Bid bid) {
    this.bids.add(bid);
  }
  public void setOnLicit(Double onLicit) {
    this.balance.setBalance(this.balance.getBalance() - onLicit);
    this.balance.setOnLicit(this.balance.getOnLicit() + onLicit);
  }
  public List<String> getRoleList() {
    if (this.role.length() > 0) {
      return Arrays.asList(this.role.split(","));
    }
    return new ArrayList<>();
  }
  public List<Long> getProducts() {

    List<Long> longs = new ArrayList<>();
    for (Product i : products) {
      longs.add(i.getId());
    }
    return longs;
  }
  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(username, user.username)
        && Objects.equals(email, user.email) && Objects.equals(password,
        user.password) && Objects.equals(role, user.role) && Objects.equals(
        products, user.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, password, role, products);
  }
}