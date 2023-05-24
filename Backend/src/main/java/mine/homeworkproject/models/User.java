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
  @OneToMany(mappedBy = "uploader", cascade = CascadeType.REFRESH)
  private List<Product> uploadedProducts;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "balance_id")
  private Balance balance;
  @OneToMany(mappedBy = "user")
  private List<Bid> bids;
  @OneToMany(mappedBy = "owner")
  private List<Product> ownedProducts;

  public User() {}

  public User(String username, String email, String password, String role, Balance balance) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.balance = balance;
    this.bids = new ArrayList<>();
    this.ownedProducts = new ArrayList<>();
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
  public void setBalance(Balance balance) {this.setBalance(balance);}
  public BalanceDto getBalance() {
    return new BalanceDto(this.balance);
  }
  public void setPlusBalance(Double balance) { this.balance.setBalance(this.getBalance().getBalance() + balance); }
  public void setMinusBalance(Double balance) { this.balance.setBalance(this.getBalance().getBalance() - balance); }
  public List<Bid> getBids() {
    return bids;
  }
  public void setBids(Bid bid) {
    this.bids.add(bid);
  }
  public void setPlusOnLicit(Double onLicit) { this.balance.setOnLicit(this.balance.getOnLicit() + onLicit); }
  public void setMinusOnLicit(Double onLicit) { this.balance.setOnLicit(this.balance.getOnLicit() - onLicit); }
  public List<String> getRoleList() {
    if (this.role.length() > 0) {
      return Arrays.asList(this.role.split(","));
    }
    return new ArrayList<>();
  }
  public List<Long> getUploadedProducts() {
    List<Long> longs = new ArrayList<>();
    for (Product i : uploadedProducts) {
      longs.add(i.getId());
    }
    return longs;
  }
  public void setUploadedProducts(List<Product> uploadedProducts) {
    this.uploadedProducts = uploadedProducts;
  }
  public List<Long> getOwnedProducts() {
    List<Long> longs = new ArrayList<>();
    for (Product i : ownedProducts) {
      longs.add(i.getId());
    }
    return longs;
  }
  public void setOwnedProducts(Product product) {
    this.ownedProducts.add(product);
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
        uploadedProducts, user.uploadedProducts) && Objects.equals(balance, user.balance)
        && Objects.equals(bids, user.bids) && Objects.equals(ownedProducts,
        user.ownedProducts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, password, role, uploadedProducts, balance, bids,
        ownedProducts);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", role='" + role + '\'' +
        ", uploadedProducts=" + uploadedProducts +
        ", balance=" + balance +
        ", bids=" + bids +
        ", ownedProducts=" + ownedProducts +
        '}';
  }
}