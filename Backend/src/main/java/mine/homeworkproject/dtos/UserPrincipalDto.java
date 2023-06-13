package mine.homeworkproject.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mine.homeworkproject.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipalDto implements UserDetails {

  private User user;

  public UserPrincipalDto(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    List<GrantedAuthority> authorities = new ArrayList<>();

    this.user
        .getRoleList()
        .forEach(
            r -> {
              GrantedAuthority authority = new SimpleGrantedAuthority(r);
              authorities.add(authority);
            });

    return authorities;
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public User getUser() {
    return user;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
