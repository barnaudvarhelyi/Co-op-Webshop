package mine.homeworkproject.dtos;

import java.util.List;
import mine.homeworkproject.models.Product;

public class UserProfileResponseDto {
  private String username;
  private Integer uploadedProductsCount;
  private List<Product> uploadedProducts;
  private BalanceDto balance;
  private List<Product> ownedProducts;
  private Integer ownedProductsCount;

  public UserProfileResponseDto(String username, Integer uploadedProductsCount,
      List<Product> uploadedProducts, BalanceDto balance, List<Product> ownedProducts, Integer ownedProductsCount) {
    this.username = username;
    this.uploadedProductsCount = uploadedProductsCount;
    this.ownedProductsCount = ownedProductsCount;
    this.uploadedProducts = uploadedProducts;
    this.balance = balance;
    this.ownedProducts = ownedProducts;
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
  public List<Product> getOwnedProducts() {
    return ownedProducts;
  }
  public void setOwnedProducts(List<Product> ownedProducts) {
    this.ownedProducts = ownedProducts;
  }
  public Integer getOwnedProductsCount() {
    return ownedProductsCount;
  }
  public void setOwnedProductsCount(Integer ownedProductsCount) {
    this.ownedProductsCount = ownedProductsCount;
  }
}
