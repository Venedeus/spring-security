package my.shvetsov.spring.security.springsecuritybasicauthentication.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class DeniedClientFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.USER_AGENT);
    if(header != null && header.startsWith("curl")) {
      response.sendError(HttpStatus.FORBIDDEN.value());
      return;
    }
    filterChain.doFilter(request, response);
  }
}
