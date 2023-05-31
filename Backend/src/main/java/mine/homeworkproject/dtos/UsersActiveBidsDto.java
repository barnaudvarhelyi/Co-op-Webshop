package mine.homeworkproject.dtos;

public class UsersActiveBidsDto {
  private String bidEnd;
  private String productPhotoUrl;
  private Long productId;
  private String productName;
  private Double amount;
  public UsersActiveBidsDto(String bidEnd, String productPhotoUrl, Long productId, String productName, Double amount) {
    this.bidEnd = bidEnd;
    this.productPhotoUrl = productPhotoUrl;
    this.productId = productId;
    this.productName = productName;
    this.amount = amount;
  }

  public String getBidEnd() {
    return bidEnd;
  }
  public void setBidEnd(String bidEnd) {
    this.bidEnd = bidEnd;
  }
  public Long getProductId() {
    return productId;
  }
  public void setProductId(Long productId) {
    this.productId = productId;
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
  public String getProductPhotoUrl() {
    return productPhotoUrl;
  }
  public void setProductPhotoUrl(String productPhotoUrl) {
    this.productPhotoUrl = productPhotoUrl;
  }
}
