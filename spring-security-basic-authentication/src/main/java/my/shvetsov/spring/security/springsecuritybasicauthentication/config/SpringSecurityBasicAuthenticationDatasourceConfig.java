package my.shvetsov.spring.security.springsecuritybasicauthentication.config;

import javax.sql.DataSource;
import my.shvetsov.spring.security.springsecuritybasicauthentication.service.JdbcUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("springSecurityBasicAuthenticationDatasourceConfig")
public class SpringSecurityBasicAuthenticationDatasourceConfig {
  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailService(dataSource);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .build();
  }
}
