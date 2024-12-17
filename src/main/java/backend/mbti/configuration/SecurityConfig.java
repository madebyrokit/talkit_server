package backend.mbti.configuration;

import backend.mbti.configuration.jwt.JwtFilter;
import backend.mbti.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SignService signService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable();
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();
        httpSecurity
                .addFilterBefore(new JwtFilter(signService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .requestMatchers().antMatchers(HttpMethod.POST, "/**")
                .requestMatchers().antMatchers(HttpMethod.GET, "/**")
                .requestMatchers().antMatchers(HttpMethod.PUT, "/**")
                .requestMatchers().antMatchers(HttpMethod.DELETE, "/**");
        return httpSecurity.build();

    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//        configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
