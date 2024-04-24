package com.do4you.do4you.config;

import com.do4you.do4you.common.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/check").authenticated()                      // 로그인테스트용 (삭제 시 HomeController에서 같이 삭제)
                        .requestMatchers("/role_user").hasRole(UserRole.USER.name())    // 권한 테스트용 - user (삭제 시 HomeController에서 같이 삭제)
                        .requestMatchers("/role_admin").hasRole("ADMIN")                // 권한 테스트용 - admin (삭제 시 HomeController에서 같이 삭제)
                        .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin
                    .loginPage("/auth/login")             // GET
                    .loginProcessingUrl("/auth/login")    // POST
                    .defaultSuccessUrl("/index")
                    .failureUrl("/loginError")    // 로그인 오류 확인용 임시 페이지 (삭제 시 HomeController에서 같이 삭제)

                    // FIXME: 예외 출력용 임시 코드 (추후 변경 또는 삭제)
                    .failureHandler((request, response, exception) -> exception.printStackTrace())
                    
                    // default 가 username 이므로 변경 필요
                    .usernameParameter("userId")
                )
                .build();
    }

}
