package my.shvetsov.spring.security.springsecuritybasicauthentication;

import my.shvetsov.spring.security.springsecuritybasicauthentication.config.SpringSecurityBasicAuthenticationDatasourceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class SpringSecurityBasicAuthenticationApplicationTests {

  public static void main(String[] args) {
    SpringApplication.from(SpringSecurityBasicAuthenticationApplication::main).run(args);
  }
}
