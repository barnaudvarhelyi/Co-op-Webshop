package mine.homeworkproject.dtos;

public class UsersActiveBidsDto {
  private String productName;
  private Double amount;
  public UsersActiveBidsDto(String productName, Double amount) {
    this.productName = productName;
    this.amount = amount;
  }
  public String getProductName() {
    return productName;
  }
  public void setProductName(String productName) {
    this.productName = productName;
  }
  public Double getAmount() {
    return amount;
  }
  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
