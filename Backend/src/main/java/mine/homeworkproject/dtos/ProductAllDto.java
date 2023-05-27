package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  private String createdAt;
  @Nullable
  private Double startingPrice;
  @Nullable
  private String expiresAt;
  private User uploader;
  @Nullable
  private List<BidDto> bids;

  public ProductAllDto(Product product) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.photoUrl = product.getPhotoUrl();
    this.createdAt = product.getCreatedAt().format(formatter);
    this.purchasePrice = product.getPurchasePrice();
    this.expiresAt = product.getExpiresAt() == null ? null : product.getExpiresAt().format(formatter);
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
  public String getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
  public Double getStartingPrice() {
    return startingPrice;
  }
  public void setStartingPrice(Double startingPrice) {
    this.startingPrice = startingPrice;
  }
  public String getExpiresAt() {
    return expiresAt;
  }
  public void setExpiresAt(String expiresAt) {
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