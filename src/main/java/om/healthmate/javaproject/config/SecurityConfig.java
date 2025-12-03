package om.healthmate.javaproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF để tương thích với session-based auth hiện tại
            .csrf(AbstractHttpConfigurer::disable)
            // Cho phép tất cả request (vì đang dùng session-based auth trong controller)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            // Disable form login mặc định của Spring Security
            .formLogin(AbstractHttpConfigurer::disable)
            // Disable HTTP Basic authentication
            .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

