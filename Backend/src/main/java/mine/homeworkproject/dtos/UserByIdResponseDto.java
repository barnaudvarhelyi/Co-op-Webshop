package mine.homeworkproject.dtos;

import java.util.ArrayList;
import java.util.List;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;

public class UserByIdResponseDto {

  private String username;
  private String email;
  private Integer productsCount;
  private List<ProductDto> products;

  public UserByIdResponseDto(User user, List<ProductDto> products) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.productsCount = products.size();
    this.products = products;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public Integer getProductsCount() {
    return productsCount;
  }
  public void setProductsCount(Integer productsCount) {
    this.productsCount = productsCount;
  }
  public List<ProductDto> getProducts() {
    return products;
  }
  public void setProducts(List<ProductDto> products) {
    this.products = products;
  }
}
