package mine.homeworkproject.dtos;

import java.util.List;
import mine.homeworkproject.models.Product;
import mine.homeworkproject.models.User;
import mine.homeworkproject.models.UserBalance;

public class UserProfileResponsDto {
  private String username;
  private Integer uploadedProductsCount;
  private List<Product> uploadedProducts;
  private BalanceDto balance;

  public UserProfileResponsDto(String username, Integer uploadedProductsCount,
      List<Product> uploadedProducts, BalanceDto balance) {
    this.username = username;
    this.uploadedProductsCount = uploadedProductsCount;
    this.uploadedProducts = uploadedProducts;
    this.balance = balance;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public Integer getUploadedProductsCount() {
    return uploadedProductsCount;
  }
  public void setUploadedProductsCount(Integer uploadedProductsCount) {
    this.uploadedProductsCount = uploadedProductsCount;
  }
  public List<Product> getUploadedProducts() {
    return uploadedProducts;
  }
  public void setUploadedProducts(List<Product> uploadedProducts) {
    this.uploadedProducts = uploadedProducts;
  }
  public BalanceDto getBalance() {
    return balance;
  }
  public void setBalance(BalanceDto balance) {
    this.balance = balance;
  }
}
