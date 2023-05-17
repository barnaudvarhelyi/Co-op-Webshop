package mine.homeworkproject.dtos;

import mine.homeworkproject.models.Product;

public class ProductByIdResponsDto {
  private String uploader;
  private Long id;
  private String description;
  private String name;
  private String photoUrl;
  private Double purchasePrice;
  private Double startingPrice;

  public ProductByIdResponsDto(Product p, String username) {
    this.id = p.getId();
    this.name = p.getName();
    this.description = p.getDescription();
    this.photoUrl = p.getPhotoUrl();
    this.purchasePrice = p.getPurchasePrice();
    this.startingPrice = p.getStartingPrice();
    this.uploader = username;
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

  public void setUploader(String uploder) {
    uploder = uploder;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
