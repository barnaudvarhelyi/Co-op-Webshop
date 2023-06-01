package mine.homeworkproject.dtos;

public class UsersActiveBidsDto {
  private String bidEnd;
  private String productPhotoUrl;
  private Long productId;
  private String productName;
  private Double amount;
  private Boolean highestBidder;
  public UsersActiveBidsDto(String bidEnd, String productPhotoUrl, Long productId, String productName, Double amount, Boolean highestBidder) {
    this.bidEnd = bidEnd;
    this.productPhotoUrl = productPhotoUrl;
    this.productId = productId;
    this.productName = productName;
    this.amount = amount;
    this.highestBidder = highestBidder;
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
  public Boolean getHighestBidder() {
    return highestBidder;
  }
  public void setHighestBidder(Boolean highestBidder) {
    this.highestBidder = highestBidder;
  }
}
