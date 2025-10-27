package my.shvetsov.spring.security.springsecuritybasicauthentication.filter;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class HexAuthenticationConverter implements AuthenticationConverter {

  @Override
  public Authentication convert(HttpServletRequest request) {
    final var authentication = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authentication != null && authentication.startsWith("HEX ")) {
      final var rawToken = authentication.replaceAll("^HEX ", "");
      final var token = new String(Hex.decode(rawToken), StandardCharsets.UTF_8);
      final var tokenParts = token.split(":");
      return UsernamePasswordAuthenticationToken.unauthenticated(tokenParts[0], tokenParts[1]);
    }
    return null;
  }
}
