package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import mine.homeworkproject.models.Product;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductByIdResponseDto {
  private String uploader;
  private Long uploaderId;
  private Long id;
  private String name;
  private String description;
  private String photoUrl;
  private Double purchasePrice;
  private LocalDateTime createdAt;
  private Double startingPrice;
  private LocalDateTime expiresAt;
  private List<Product> randomProducts;

  public ProductByIdResponseDto() {}
  public ProductByIdResponseDto(Product p, String username, Long userId,
      List<Product> randomProducts) {
    this.id = p.getId();
    this.name = p.getName();
    this.description = p.getDescription();
    this.photoUrl = p.getPhotoUrl();
    this.purchasePrice = p.getPurchasePrice();
    this.startingPrice = p.getStartingPrice();
    this.uploader = username;
    this.uploaderId = userId;
    this.expiresAt = p.getExpiresAt();
    this.createdAt = p.getCreatedAt();
    this.randomProducts = randomProducts;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPhotoUrl() {
    return photoUrl;
  }
  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }
  public Double getPurchasePrice() {
    return purchasePrice;
  }
  public void setPurchasePrice(Double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }
  public Double getStartingPrice() {
    return startingPrice;
  }
  public void setStartingPrice(Double startingPrice) {
    this.startingPrice = startingPrice;
  }
  public String getUploader() {
    return uploader;
  }
  public void setUploader(String uploader) {
    this.uploader = uploader;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Long getUploaderId() {
    return uploaderId;
  }
  public void setUploaderId(Long uploaderId) {
    this.uploaderId = uploaderId;
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
  public List<Product> getRandomProducts() {
    return randomProducts;
  }
  public void setRandomProducts(List<Product> randomProducts) {
    this.randomProducts = randomProducts;
  }
}
