package my.shvetsov.spring.security.springsecuritybasicauthentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("springSecurityBasicAuthenticationEntryPointConfig")
public class SpringSecurityBasicAuthenticationEntryPointConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .authorizeHttpRequests(authorize ->
            authorize
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated())
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint((request, response, authException) -> {
              authException.printStackTrace();
              response.sendRedirect("http://localhost:8080/public/403.html");
            }))
        .build();
  }
}
