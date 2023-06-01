package mine.homeworkproject.dtos;

import java.util.List;

public class UserProfileResponseDto {
  private String username;
  private BalanceDto balance;
  private Integer uploadedProductsCount;
  private Integer ownedProductsCount;
  private List<ProductDto> uploadedProducts;
  private List<ProductDto> ownedProducts;
  private List<UsersActiveBidsDto> usersActiveBids;
  private List<TransactionDto> transactions;
  public UserProfileResponseDto(
      String username,
      Integer uploadedProductsCount,
      List<ProductDto> uploadedProducts,
      BalanceDto balance,
      List<ProductDto> ownedProducts,
      Integer ownedProductsCount,
      List<UsersActiveBidsDto> usersActiveBids,
      List<TransactionDto> transactions
  ) {
    this.username = username;
    this.uploadedProductsCount = uploadedProductsCount;
    this.ownedProductsCount = ownedProductsCount;
    this.uploadedProducts = uploadedProducts;
    this.balance = balance;
    this.ownedProducts = ownedProducts;
    this.usersActiveBids = usersActiveBids;
    this.transactions = transactions;
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
  public List<ProductDto> getUploadedProducts() {
    return uploadedProducts;
  }
  public void setUploadedProducts(List<ProductDto> uploadedProducts) {
    this.uploadedProducts = uploadedProducts;
  }
  public BalanceDto getBalance() {
    return balance;
  }
  public void setBalance(BalanceDto balance) {
    this.balance = balance;
  }
  public List<ProductDto> getOwnedProducts() {
    return ownedProducts;
  }
  public void setOwnedProducts(List<ProductDto> ownedProducts) {
    this.ownedProducts = ownedProducts;
  }
  public Integer getOwnedProductsCount() {
    return ownedProductsCount;
  }
  public void setOwnedProductsCount(Integer ownedProductsCount) {
    this.ownedProductsCount = ownedProductsCount;
  }
  public List<UsersActiveBidsDto> getUsersActiveBids() {
    return usersActiveBids;
  }
  public void setUsersActiveBids(
      List<UsersActiveBidsDto> usersActiveBids) {
    this.usersActiveBids = usersActiveBids;
  }
  public List<TransactionDto> getTransactions() {
    return transactions;
  }
  public void setTransactions(List<TransactionDto> transactions) {
    this.transactions = transactions;
  }
}
