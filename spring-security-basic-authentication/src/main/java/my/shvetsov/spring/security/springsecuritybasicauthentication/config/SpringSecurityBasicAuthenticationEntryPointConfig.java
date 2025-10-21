package my.shvetsov.spring.security.springsecuritybasicauthentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@Profile("springSecurityBasicAuthenticationEntryPointConfig")
public class SpringSecurityBasicAuthenticationEntryPointConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
    basicAuthenticationEntryPoint.setRealmName("Realm");
    return httpSecurity
        .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(
                (request, response, authException) -> {
                  authException.printStackTrace();
                  basicAuthenticationEntryPoint.commence(request, response, authException);
                }
            )
        )
        .authorizeHttpRequests(authorize ->
            authorize
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated())
        .exceptionHandling(exceptionHandling ->
            exceptionHandling.authenticationEntryPoint(basicAuthenticationEntryPoint))
        .build();
  }
}
