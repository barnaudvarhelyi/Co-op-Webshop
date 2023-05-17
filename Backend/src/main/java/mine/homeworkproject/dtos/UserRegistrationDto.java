package mine.homeworkproject.dtos;

import java.util.Objects;
import javax.validation.constraints.Pattern;

public class UserRegistrationDto extends UserDto {

  @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
  private String email;

  public UserRegistrationDto() {}

  public UserRegistrationDto(String username, String email, String password) {
    super(username, password);
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserRegistrationDto that = (UserRegistrationDto) o;
    return Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "UserRegistrationDto{"
        + "username='"
        + getUsername()
        + '\''
        + "email='"
        + email
        + '\''
        + "password='"
        + getPassword()
        + '\''
        + '}';
  }
}
