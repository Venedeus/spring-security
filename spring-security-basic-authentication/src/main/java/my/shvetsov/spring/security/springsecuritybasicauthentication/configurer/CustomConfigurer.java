package my.shvetsov.spring.security.springsecuritybasicauthentication.configurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomConfigurer extends AbstractHttpConfigurer<CustomConfigurer, HttpSecurity> {

  private String realmName;

  @Override
  public void init(HttpSecurity builder) throws Exception {
    builder.httpBasic(httpBasic -> httpBasic.realmName(realmName))
        .authorizeHttpRequests(
            authorizeHttpRequests -> authorizeHttpRequests.anyRequest().authenticated());
    super.init(builder);
  }

  @Override
  public void configure(HttpSecurity builder) throws Exception {
  }

  public CustomConfigurer realmName(String realmName) {
    this.realmName = realmName;
    return this;
  }
}
