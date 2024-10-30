package kh.st.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import kh.st.boot.auth.CustomAuthenticationProvider;
import kh.st.boot.dao.MemberDAO;
import kh.st.boot.handler.LoginFailHandler;
import kh.st.boot.handler.LoginSuccessHandler;
import kh.st.boot.model.util.UserRole;
import kh.st.boot.service.MemberDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	private MemberDetailService memberDetailService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private MemberDAO memberDao;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 비활성화 및 모든 URL 접근을 허용
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .anyRequest().permitAll()  // 모든 요청에 대해 접근 허용
            )
            .formLogin((form) -> form
                .loginPage("/member/login")  // 커스텀 로그인 페이지 설정
                .permitAll()           // 로그인 페이지는 접근 허용
                .loginProcessingUrl("/member/login") //실제 로그인 되는 곳
//                .usernameParameter("userId") //아이디 파라미터 명
//                .passwordParameter("password") // 비밀번호 파라미터 명
                .successHandler(new LoginSuccessHandler(memberDao))
                .failureHandler(new LoginFailHandler(memberDao))
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

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.authenticationProvider(customAuthenticationProvider);
        return auth.build();
    }
}