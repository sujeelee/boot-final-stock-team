package kh.st.boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kh.st.boot.info.KakaoUserInfo;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.KakaoService;
import kh.st.boot.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KakaoLoginController {

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private MemberService memberService;
    
    @GetMapping("/oauth/kakao/callback")
    public String callback(@RequestParam("code") String code, Model mo) {

        try {
            String accessToken = kakaoService.getAccessTokenFromKakao(code);        
            KakaoUserInfo userInfo = kakaoService.getUserInfo(accessToken); 

            if (userInfo == null || userInfo.getKakaoAccount() == null) {
                System.out.println("카카오 로그인 컨트롤러 userInfo null");
                return "error"; // 에러 페이지
            }

            // if (userInfo != null) {
            //     kakaoService.kakaoLogout(accessToken); //로그인해서 정보만 가져온 뒤 로그아웃
            //     System.out.println("카카오 로그아웃 완료");
            // }
            //카카오 로그인 >> 정보를 가져온 뒤 DB에 저장 >> 카카오 로그아웃

            log.info("Kakao accessToken: {}", accessToken);
            log.info("Kakao userInfo email: {}", userInfo.getKakaoAccount().getEmail());

            String info = "Kakao";
            String snsUser = "k"+ userInfo.getKakaoAccount().getEmail().split("@")[0];
            mo.addAttribute("where", info);
            mo.addAttribute("userInfo", userInfo.getKakaoAccount().getEmail());
            mo.addAttribute("snsUser", snsUser);
            
            MemberVO user = memberService.findById(snsUser);
            
            if(user != null) {
                // 이미 존재하는 사용자라면 로그인 처리
                log.info("로그인된 사용자: {}", snsUser);
                
                // 로그인 처리 로직 추가 (세션이나 JWT 토큰 등을 이용한 로그인 처리)
                // 예를 들어, 세션에 사용자 정보 저장:
                mo.addAttribute("user", user);  // 세션 또는 사용자 정보 전달
                
                return "redirect:/"; // 로그인 후 메인 페이지로 리디렉션
            }
            
            //회원가입 페이지로 이동 후 이메일을 아이디, 인증 요소로 사용
            return "member/join"; // join.html로 렌더링
        } catch (Exception e) {
            log.error("카카오 로그인 컨트롤러 오류 발생 : {}", e);
            return "error"; // 에러 페이지
        }
    }
}







