package mine.homeworkproject.security;

import io.github.cdimascio.dotenv.Dotenv;

public class JwtProperties {
  public static final int EXPIRATION_TIME = 864000000; // 10 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  static Dotenv dotenv = Dotenv.load();
  public static final String SECRET = dotenv.get("JWT_SECRET_KEY");
}
