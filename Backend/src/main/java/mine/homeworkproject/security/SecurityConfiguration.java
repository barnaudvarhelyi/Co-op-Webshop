package mine.homeworkproject.security;

import java.util.Arrays;
import mine.homeworkproject.repositories.UserRepository;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
  private final UserPrincipalDetailsService userPrincipalDetailsService;
  private final UserRepository userRepository;

  @Autowired
  public SecurityConfiguration(
      UserPrincipalDetailsService userPrincipalDetailsService,
      UserRepository userRepository) {
    this.userPrincipalDetailsService = userPrincipalDetailsService;
    this.userRepository = userRepository;
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
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(
            "/login*",
            "/register*",
            "/api/*").permitAll()
        .and()
        .cors().configurationSource(corsConfiguration())
        .and()
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(
            new AuthenticationFilter(
                authenticationManager(http, passwordEncoder(), userPrincipalDetailsService),
                userRepository))
        .addFilter(
            new AuthorizationFilter(
                authenticationManager(http, passwordEncoder(), userPrincipalDetailsService),
                userRepository));

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

    return daoAuthenticationProvider;
  }

  @Bean
  public CorsConfigurationSource corsConfiguration() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.applyPermitDefaultValues();
    corsConfig.setAllowCredentials(true);
    corsConfig.addAllowedMethod("GET");
    corsConfig.addAllowedMethod("PATCH");
    corsConfig.addAllowedMethod("POST");
    corsConfig.addAllowedMethod("OPTIONS");
    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
    corsConfig.setExposedHeaders(Arrays.asList("X-Get-Header"));
    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return source;
  }
}
