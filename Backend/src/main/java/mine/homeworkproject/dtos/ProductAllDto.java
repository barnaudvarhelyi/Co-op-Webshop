package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.Nullable;
import java.time.format.DateTimeFormatter;
import mine.homeworkproject.models.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAllDto {

  private Long id;
  private String name;
  private String photoUrl;
  private Double purchasePrice;
  @Nullable
  private Double startingPrice;

  public ProductAllDto(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.photoUrl = product.getPhotoUrl();
    this.purchasePrice = product.getPurchasePrice();
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
}