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
  public AuthenticationFilter(
      AuthenticationManager authenticationManager,
      UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    UserDto user;
    boolean isEmptyPassword = false;
    boolean isEmptyUsername = false;

    try {
      user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);

      if (user.getPassword().equals("") || user.getPassword() == null) {
        isEmptyPassword = true;
        response.setStatus(401);
      }
      if (user.getUsername().equals("") || user.getUsername() == null) {
        isEmptyUsername = true;
        response.setStatus(401);
      }

      if (isEmptyUsername && isEmptyPassword) {
        responseInBody(response, new ResponseDto("Username and password fields are empty!"));
        return null;
      } else if (isEmptyUsername) {
        responseInBody(response, new ResponseDto("Username field is empty!"));
        return null;
      } else if (isEmptyPassword) {
        responseInBody(response, new ResponseDto("Password field is empty!"));
        return null;
      }
      if (!userRepository.findByUsername(user.getUsername()).isPresent()) {
        response.setStatus(401);
        responseInBody(response,
            new ResponseDto("Username or password field is incorrect!"));
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
    response.setStatus(200);
    String token =
        JWT.create()
            .withClaim("userId", principal.getUser().getId())
            .withClaim("username", principal.getUsername())
            .withSubject(principal.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

    response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
    responseInBody(response,
        new LoginResponseDto(response.getStatus(), principal.getUsername(), token));
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException {

    response.setStatus(401);
    responseInBody(
        response,
        new ResponseDto("Username or password field is incorrect!"));
  }

  private void responseInBody(HttpServletResponse response, Object message) throws IOException {
    ServletServerHttpResponse servletServerHttpResponse = new ServletServerHttpResponse(response);
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.write(
        message, MediaType.APPLICATION_JSON, servletServerHttpResponse);
  }
}
