package my.shvetsov.spring.security.springsecuritybasicauthentication.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

public class HexAuthenticationFilter extends OncePerRequestFilter {

  private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

  private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

  private AuthenticationManager authenticationManager;

  private AuthenticationEntryPoint authenticationEntryPoint;

  public HexAuthenticationFilter(AuthenticationManager authenticationManager,
      AuthenticationEntryPoint authenticationEntryPoint) {
    this.authenticationManager = authenticationManager;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final var authentication = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authentication != null && authentication.startsWith("HEX ")) {
      final var rawToken = authentication.replaceAll("^HEX ", "");
      final var token = new String(Hex.decode(rawToken), StandardCharsets.UTF_8);
      final var tokenParts = token.split(":");
      final var authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
          tokenParts[0], tokenParts[1]);

      try {
        final var authenticationResult = this.authenticationManager.authenticate(
            authenticationRequest);
        final var context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authenticationResult);
        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
      } catch (AuthenticationException e) {
        this.securityContextHolderStrategy.clearContext();
        this.authenticationEntryPoint.commence(request, response, e);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
