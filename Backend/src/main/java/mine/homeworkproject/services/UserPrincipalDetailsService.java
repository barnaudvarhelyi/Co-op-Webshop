package mine.homeworkproject.services;

import mine.homeworkproject.dtos.UserPrincipalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
  private final UserService userService;

  @Autowired
  public UserPrincipalDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return new UserPrincipalDto(userService.findUserByUsername(username));
  }
}
