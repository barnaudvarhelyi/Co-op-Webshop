package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import mine.homeworkproject.models.Product;

public class ProductCreateDto {
  private String name;
  private String description;
  private String photoUrl;
  private Double purchasePrice;
  private Boolean forBid;
  @Nullable
  private Double startingPrice;
  @Nullable
  private String expiresAt;

  public ProductCreateDto() {}

  public ProductCreateDto(
      String name,
      String description,
      String photoUrl,
      Double purchasePrice,
      Boolean forBid,
      Double startingPrice,
      String expiresAt
  ){
    this.name = name;
    this.description = description;
    this.photoUrl = photoUrl;
    this.purchasePrice = Math.round(purchasePrice*100.0)/100.0;
    this.forBid = forBid;
    this.startingPrice = forBid ? Math.round(startingPrice*100.0)/100.0 : null;
    this.expiresAt = forBid ? expiresAt : null;
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
  public String getExpiresAt() {
    return expiresAt;
  }
  public void setExpiresAt(String expiresAt) {
    this.expiresAt = expiresAt;
  }
  public Boolean getForBid() {
    return forBid;
  }
  public void setForBid(Boolean forBid) {
    this.forBid = forBid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductCreateDto that = (ProductCreateDto) o;
    return Objects.equals(name, that.name) && Objects.equals(description,
        that.description) && Objects.equals(photoUrl, that.photoUrl)
        && Objects.equals(startingPrice, that.startingPrice) && Objects.equals(
        purchasePrice, that.purchasePrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, photoUrl, startingPrice, purchasePrice);
  }

  @Override
  public String toString() {
    return "ProductCreateDto{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", photoUrl='" + photoUrl + '\'' +
        ", startingPrice=" + startingPrice +
        ", purchasePrice=" + purchasePrice +
        '}';
  }
}
