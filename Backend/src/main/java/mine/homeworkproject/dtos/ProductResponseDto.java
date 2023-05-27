package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mine.homeworkproject.models.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {
  private Long productId;
  private Long userId;
  private String name;
  private String description;
  private String photoUrl;
  private Double purchasePrice;
  private String createdAt;
  private Double startingPrice;
  private String expiresAt;
  public ProductResponseDto() {}
  public ProductResponseDto(Product product) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    this.productId = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.photoUrl = product.getPhotoUrl();
    this.purchasePrice = product.getPurchasePrice();
    this.userId = product.getUploader();
    this.createdAt = product.getCreatedAt().format(formatter);
    this.expiresAt = product.getExpiresAt().format(formatter);
    this.startingPrice = product.getStartingPrice();
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

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(String expiresAt) {
    this.expiresAt = expiresAt;
  }
}
