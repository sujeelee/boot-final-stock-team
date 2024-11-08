package kh.st.boot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kh.st.boot.model.util.CustomUser;
import kh.st.boot.model.vo.MemberVO;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	
	
	@Autowired
	private MemberService memberService;
	
 // DefaultOAuth2UserService는 Spring Security에서 제공하는 기본 OAuth2UserService 구현체입니다.
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
	
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 예시: Kakao 로그인 시, 반환된 속성에서 사용자 정보를 추출
        String email = (String) attributes.get("kakao_account.email");
        String nickname = (String) attributes.get("properties.nickname");
        MemberVO user = memberService.findByEmail(email);
        // 추가적으로 DB에 사용자 정보를 저장하거나 처리하는 로직을 여기에 추가
        System.out.println("test" + user);
        return (OAuth2User) new CustomUser(user);
    }

}
