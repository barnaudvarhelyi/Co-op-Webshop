package mine.homeworkproject.dtos;

import java.util.List;
import mine.homeworkproject.models.Product;

public class UserProfileResponsDto {
  private String username;
  private Integer uplodedProductsCount;
  private List<Product> uplodedProducts;

  public UserProfileResponsDto(String username, Integer uplodedProductsCount,
      List<Product> uplodedProducts) {
    this.username = username;
    this.uplodedProductsCount = uplodedProductsCount;
    this.uplodedProducts = uplodedProducts;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getUplodedProductsCount() {
    return uplodedProductsCount;
  }

  public void setUplodedProductsCount(Integer uplodedProductsCount) {
    this.uplodedProductsCount = uplodedProductsCount;
  }

  public List<Product> getUplodedProducts() {
    return uplodedProducts;
  }

  public void setUplodedProducts(List<Product> uplodedProducts) {
    this.uplodedProducts = uplodedProducts;
  }
}
