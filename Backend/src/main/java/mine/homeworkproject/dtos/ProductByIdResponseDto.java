package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  private String createdAt;
  private Double startingPrice;
  private String expiresAt;
  private List<ProductDto> randomProducts;

  public ProductByIdResponseDto() {}
  public ProductByIdResponseDto(Product p, String username, Long userId,
      List<ProductDto> randomProducts) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    this.id = p.getId();
    this.name = p.getName();
    this.description = p.getDescription();
    this.photoUrl = p.getPhotoUrl();
    this.purchasePrice = p.getPurchasePrice();
    this.startingPrice = p.getStartingPrice();
    this.uploader = username;
    this.uploaderId = userId;
    this.createdAt = p.getCreatedAt().format(formatter);
    this.expiresAt = p.getExpiresAt() == null ? null : p.getExpiresAt().format(formatter);
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
  public List<ProductDto> getRandomProducts() {
    return randomProducts;
  }
  public void setRandomProducts(List<ProductDto> randomProducts) {
    this.randomProducts = randomProducts;
  }
}
