package kh.st.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import kh.st.boot.handler.LoginSuccessHandler;
import kh.st.boot.service.MemberDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private MemberDetailService memberDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 비활성화 및 모든 URL 접근을 허용
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .anyRequest().permitAll()  // 모든 요청에 대해 접근 허용
            )
            .formLogin((form) -> form
                .loginPage("/member/login")  // 커스텀 로그인 페이지 설정
                .permitAll()                 // 로그인 페이지 접근 허용
                .loginProcessingUrl("/member/login")
                .defaultSuccessUrl("/")      // 로그인 성공 시 리다이렉트
                .successHandler(new LoginSuccessHandler())
            )
            .rememberMe((rm) -> rm
                .key("team1")
                .rememberMeParameter("re")
                .userDetailsService(memberDetailService)
                .rememberMeCookieName("AUTO_LOGIN")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")       // 로그아웃 URL 설정
                .logoutSuccessUrl("/")      // 로그아웃 성공 후 이동할 페이지
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("AUTO_LOGIN") // 로그아웃 시 쿠키 삭제
                .permitAll()
            );
        return http.build();
    }
}
