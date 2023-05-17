package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Objects;

public class RegistrationResponseDto {

  private Long id;
  private String username;

  @JsonInclude(Include.NON_EMPTY)
  private String confirmationToken;

  public RegistrationResponseDto() {}

  public RegistrationResponseDto(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public RegistrationResponseDto(Long id, String username, String confirmationToken) {
    this.id = id;
    this.username = username;
    this.confirmationToken = confirmationToken;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getConfirmationToken() {
    return confirmationToken;
  }

  public void setConfirmationToken(String confirmationToken) {
    this.confirmationToken = confirmationToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegistrationResponseDto that = (RegistrationResponseDto) o;
    return Objects.equals(id, that.id)
        && Objects.equals(username, that.username)
        && Objects.equals(confirmationToken, that.confirmationToken);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
