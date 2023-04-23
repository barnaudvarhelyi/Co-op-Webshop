package mine.homeworkproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mine.homeworkproject.dtos.LoginResponseDto;
import mine.homeworkproject.dtos.ResponseDto;
import mine.homeworkproject.dtos.UserDto;
import mine.homeworkproject.models.UserPrincipal;
import mine.homeworkproject.repositories.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private Boolean isEmptyPassword;
  private Boolean isEmptyUsername;

  public AuthenticationFilter(
      AuthenticationManager authenticationManager,
      UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.isEmptyPassword = false;
    this.isEmptyUsername = false;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    UserDto user;

    try {
      user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);

      if (user.getPassword().equals("") || user.getPassword() == null) {
        isEmptyPassword = true;
      } else if (user.getUsername().equals("") || user.getUsername() == null) {
        isEmptyUsername = true;
      }

      if (!isEmptyUsername
          && !userRepository.findByUsername(user.getUsername()).isPresent()) {
        response.setStatus(401);
        responseInBody(
            response,
            new ResponseDto("no_user_for_username_response"));

        return null;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            user.getUsername(), user.getPassword(), new ArrayList<>());

    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException {

    UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

    String token =
        JWT.create()
            .withClaim("userId", principal.getUser().getId())
            .withClaim("username", principal.getUsername())
            .withSubject(principal.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

    response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
    responseInBody(response, new LoginResponseDto(response.getStatus(), principal.getUsername(), token));
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException {

    if (isEmptyUsername || isEmptyPassword) {
      response.setStatus(400);
      responseInBody(
          response,
          new ResponseDto("username_or_password_field_is_empty_response"));
    } else {
      response.setStatus(401);
      responseInBody(
          response,
          new ResponseDto("username_or_password_field_is_incorrect_response"));
    }
    this.isEmptyUsername = false;
    this.isEmptyPassword = false;
  }
  private void responseInBody(HttpServletResponse response, Object message) throws IOException {
    ServletServerHttpResponse servletServerHttpResponse = new ServletServerHttpResponse(response);
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.write(
        message, MediaType.APPLICATION_JSON, servletServerHttpResponse);
  }
}
