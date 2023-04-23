package mine.homeworkproject.dtos;

public class LoginResponseDto {

  private Integer status;
  private String username;
  private String token;

  public LoginResponseDto(Integer status, String username, String token) {
    this.status = status;
    this.username = username;
    this.token = token;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}