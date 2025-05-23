package jinTeam.medinyangServer.configuration;

import jinTeam.medinyangServer.session.SessionCollector;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${react.frontend.origin}")
    private String frontendOrigin;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()) // WebMvcConfig을 사용
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**","/","/ws/**").permitAll() // 로그인 관련 API만 비인증 접근 허용
                    .anyRequest().authenticated() // 나머지는 전부 인증 필요
            );


        return http.build();
    }

    //WebSocket 세션 복원에 필요(내가 직접 세션 컨트롤)
    @Bean
    public ServletListenerRegistrationBean<SessionCollector> sessionCollector() {
        return new ServletListenerRegistrationBean<>(new SessionCollector());
    }
}
