package kh.st.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import kh.st.boot.model.util.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//csrf : 사이트간 공격을 막아줄때 사용하는 
		//URL에 접근 권한을 설정. MemberInterceptor, AdminInterceptor를 합친 기능이라고 생각하면 됨
        http.csrf(csrf ->csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/post/insert/*")
                //.hasAuthority(UserRole.USER.name())
                //위 URL을 권한이 "USER"인 회원만 접근하도록 설정
                //.hasRole(UserRole.USER.name())
                //위 URL권한이 "ROLE_USER"인 회원만 접근하도록 설정
                .hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name()) //여러 권한 설정
                .requestMatchers("/admin/**").hasAnyAuthority(UserRole.ADMIN.name())
                .anyRequest().permitAll()  // 그 외 요청은 인증 필요
            )                                                                                                                                                                     
            .formLogin((form) -> form
                .loginPage("/member/login")  // 커스텀 로그인 페이지 설정
                .permitAll()           // 로그인 페이지는 접근 허용
                .loginProcessingUrl("/login")//
                .defaultSuccessUrl("/")
            )
            .logout((logout) -> logout
            		.logoutUrl("/logout") //이 URL로  post방식으로 전송하면 자동으로 로그아웃이 실행됨
            		.logoutSuccessUrl("/")
            		.clearAuthentication(true)
            		.invalidateHttpSession(true)
            		.permitAll());  // 로그아웃도 모두 접근 가능
        return http.build();
    }

}
