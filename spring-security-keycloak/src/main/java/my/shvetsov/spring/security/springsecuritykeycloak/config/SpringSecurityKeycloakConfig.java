package my.shvetsov.spring.security.springsecuritykeycloak.config;

import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import my.shvetsov.spring.security.springsecuritykeycloak.converter.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityKeycloakConfig {

  private final JwtAuthConverter jwtAuthConverter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(c -> c
            .requestMatchers("/error").permitAll()
            .requestMatchers("/manager.html").hasRole("MANAGER")
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
    http.oauth2Login(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
    var oidcUserService = new OidcUserService();
    return userRequest -> {
      var oidcUser = oidcUserService.loadUser(userRequest);
      var roles = oidcUser.getClaimAsStringList("spring_sec_user_roles");
      var authorities = Stream.concat(oidcUser.getAuthorities().stream(),
              roles.stream()
                  .filter(role -> role.startsWith("ROLE_"))
                  .map(SimpleGrantedAuthority::new)
                  .map(GrantedAuthority.class::cast))
          .toList();
      return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    };
  }
}
