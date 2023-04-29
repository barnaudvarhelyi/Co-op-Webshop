package mine.homeworkproject.dtos;

import java.util.Objects;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;

public class ProductDto {
  private Long productId;
  private Long userId;
  private String name;
  private String description;
  private String photoUrl;
  private Double startingPrice;
  private Double purchasePrice;
  public ProductDto() {}
  public ProductDto(Product product) {
    this.productId = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.photoUrl = product.getPhotoUrl();
    this.startingPrice = product.getStartingPrice();
    this.purchasePrice = product.getPurchasePrice();
    this.userId = product.getUser();
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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
}
