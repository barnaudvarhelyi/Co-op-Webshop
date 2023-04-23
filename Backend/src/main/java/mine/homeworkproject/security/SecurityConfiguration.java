package mine.homeworkproject.security;

import mine.homeworkproject.services.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
  private final UserPrincipalDetailsService userPrincipalDetailsService;
  private final UserRepository userRepository;
  private final MessageService messageService;

  @Autowired
  public SecurityConfiguration(
      UserPrincipalDetailsService userPrincipalDetailsService,
      UserRepository userRepository,
      MessageService messageService) {
    this.userPrincipalDetailsService = userPrincipalDetailsService;
    this.userRepository = userRepository;
    this.messageService = messageService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .and()
        .cors()
        .disable()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(
            new JwtAuthenticationFilter(
                authenticationManager(http, passwordEncoder(), userPrincipalDetailsService),
                userRepository,
                messageService))
        .addFilter(
            new JwtAuthorizationFilter(
                authenticationManager(http, passwordEncoder(), userPrincipalDetailsService),
                userRepository));
    return http.build();
  }
  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

    return daoAuthenticationProvider;
  }
  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity http,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      UserPrincipalDetailsService userPrincipalDetailsService)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userPrincipalDetailsService)
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
        .build();
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
