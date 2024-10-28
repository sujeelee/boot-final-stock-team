package kh.st.boot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kh.st.boot.dao.MemberDAO;
import kh.st.boot.model.util.CustomUser;
import kh.st.boot.model.vo.MemberVO;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberDAO memberDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 사용자 정보 확인
        MemberVO member = memberDao.findById(username);
        if (member == null) {
            throw new UsernameNotFoundException(username + " 은/(는) 없는 아이디 입니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, member.getMb_password())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 인증 성공 시, 사용자 정보를 담아 Authentication 객체 생성
        CustomUser customUser = new CustomUser(member);
        return new UsernamePasswordAuthenticationToken(customUser, password, customUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
