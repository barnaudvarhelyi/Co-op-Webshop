package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import mine.homeworkproject.models.Bid;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAllDto {

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
  private User uploader;
  @Nullable
  private List<BidDto> bids;

  public ProductAllDto(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.photoUrl = product.getPhotoUrl();
    this.createdAt = product.getCreatedAt();
    this.purchasePrice = product.getPurchasePrice();
    this.expiresAt = product.getExpiresAt();
    this.startingPrice = product.getStartingPrice();
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
  public Double getPurchasePrice() {
    return purchasePrice;
  }
  public void setPurchasePrice(Double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  public Double getStartingPrice() {
    return startingPrice;
  }
  public void setStartingPrice(Double startingPrice) {
    this.startingPrice = startingPrice;
  }
  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }
  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }
  public User getUploader() {
    return uploader;
  }
  public void setUploader(User uploader) {
    this.uploader = uploader;
  }
  public List<BidDto> getBids() {
    return bids;
  }
  public void setBids(List<BidDto> bids) {
    this.bids = bids;
  }
}