package mine.homeworkproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RegistrationErrorDto {
  @JsonInclude(Include.NON_EMPTY)
  private String usernameError;

  @JsonInclude(Include.NON_EMPTY)
  private String emailError;

  @JsonInclude(Include.NON_EMPTY)
  private String passwordError;

  public RegistrationErrorDto() {}

  public RegistrationErrorDto(String usernameError, String emailError, String passwordError) {
    this.usernameError = usernameError;
    this.emailError = emailError;
    this.passwordError = passwordError;
  }

  public String getUsernameError() {
    return usernameError;
  }

  public void setUsernameError(String usernameError) {
    this.usernameError = usernameError;
  }

  public String getEmailError() {
    return emailError;
  }

  public void setEmailError(String emailError) {
    this.emailError = emailError;
  }

  public String getPasswordError() {
    return passwordError;
  }

  public void setPasswordError(String passwordError) {
    this.passwordError = passwordError;
  }
}
