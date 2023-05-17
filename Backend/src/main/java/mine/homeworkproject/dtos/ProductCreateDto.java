package mine.homeworkproject.dtos;

import java.text.DecimalFormat;
import java.util.Objects;
import mine.homeworkproject.models.Product;

public class ProductCreateDto {
  private String name;
  private String description;
  private String photoUrl;
  private Double startingPrice;
  private Double purchasePrice;

  public ProductCreateDto() {}

  public ProductCreateDto(String name, String description, String photoUrl, Double purchasePrice, Double startingPrice){
    this.name = name;
    this.description = description;
    this.photoUrl = photoUrl;
    this.startingPrice = Math.round(startingPrice*100.0)/100.0;
    this.purchasePrice = Math.round(purchasePrice*100.0)/100.0;
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
