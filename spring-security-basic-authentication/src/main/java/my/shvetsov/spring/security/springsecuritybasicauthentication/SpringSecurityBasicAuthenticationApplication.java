package my.shvetsov.spring.security.springsecuritybasicauthentication;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@EnableWebSecurity
@SpringBootApplication
public class SpringSecurityBasicAuthenticationApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityBasicAuthenticationApplication.class, args);
  }

  //  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> routerFunction() {
    return RouterFunctions.route().GET("/api/v4/greetings",
        request -> {
          UserDetails userDetails = request.principal()
              .map(Authentication.class::cast)
              .map(Authentication::getPrincipal)
              .map(UserDetails.class::cast)
              .orElseThrow();
          return ServerResponse
              .ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(Map.of("greeting", "Hello, %s! [V4]".formatted(userDetails.getUsername())));
        }).build();
  }
}
