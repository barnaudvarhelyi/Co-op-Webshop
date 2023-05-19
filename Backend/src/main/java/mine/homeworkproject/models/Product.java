package mine.homeworkproject.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import mine.homeworkproject.dtos.ProductCreateDto;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private String photoUrl;
  private Double purchasePrice;
  private LocalDateTime createdAt;
  @Nullable
  private Double startingPrice;
  @Nullable
  private LocalDateTime expiresAt;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  @OneToMany(mappedBy = "product")
  private List<Bid> bids = new ArrayList<>();

  public Product() {
  }

  public Product(
      String name,
      String description,
      String photoUrl,
      Double purchasePrice,
      Double startingPrice,
      LocalDateTime expiresAt
  ) {
    this.name = name;
    this.description = description;
    this.photoUrl = photoUrl;
    this.createdAt = getFormattedCurrentTime(LocalDateTime.now());
    this.purchasePrice = Math.round(purchasePrice*100.0)/100.0;
    this.expiresAt = expiresAt == null || expiresAt.equals("") ? null : getFormattedCurrentTime(expiresAt);
    this.startingPrice = startingPrice == null ? null : Math.round(startingPrice*100.0)/100.0;
  }
  private LocalDateTime getFormattedCurrentTime(LocalDateTime datetime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return LocalDateTime.parse(datetime.format(formatter), formatter);
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser() {
    return user == null ? 1 : user.getId();
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getPhotoUrl() {
    return photoUrl;
  }
  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }
  public Double getStartingPrice() {
    return startingPrice;
  }
  public void setStartingPrice(Double startingPrice) {
    this.startingPrice = startingPrice;
  }
  public Double getPurchasePrice() {
    return purchasePrice;
  }
  public void setPurchasePrice(Double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }
  public List<Bid> getBids() {
    return bids;
  }
  public void setBid(Bid bid) {
    this.bids.add(bid);
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }
  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(id, product.id) && Objects.equals(name, product.name)
        && Objects.equals(description, product.description) && Objects.equals(
        photoUrl, product.photoUrl) && Objects.equals(purchasePrice, product.purchasePrice)
        && Objects.equals(createdAt, product.createdAt) && Objects.equals(
        startingPrice, product.startingPrice) && Objects.equals(expiresAt,
        product.expiresAt) && Objects.equals(user, product.user)
        && Objects.equals(bids, product.bids);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, photoUrl, purchasePrice, createdAt, startingPrice,
        expiresAt, user, bids);
  }
}
