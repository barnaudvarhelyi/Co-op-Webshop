package mine.homeworkproject.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
  private Boolean isVerified;

  public User(String username, String email, String password, String role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.isVerified = false;
  }

  public User(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public User() {}

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

  public List<String> getRoleList() {
    if (this.role.length() > 0) {
      return Arrays.asList(this.role.split(","));
    }
    return new ArrayList<>();
  }

  public List<Ad> getAds() {
    return ads;
  }

  public void setAds(List<Ad> ads) {
    this.ads = ads;
  }

  public Boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean verified) {
    isVerified = verified;
  }

  public void verifyUser() {
    isVerified = true;
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
    return Objects.equals(id, user.id)
        && Objects.equals(username, user.username)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password)
        && Objects.equals(role, user.role)
        && Objects.equals(ads, user.ads)
        && Objects.equals(isVerified, user.isVerified);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}