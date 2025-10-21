package my.shvetsov.spring.security.springsecuritybasicauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class SpringSecurityBasicAuthenticationApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityBasicAuthenticationApplication.class, args);
  }
}
