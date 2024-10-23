package kh.st.boot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kh.st.boot.info.KakaoUserInfo;
import kh.st.boot.service.KakaoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/oauth/kakao/callback")
    public String callback(@RequestParam("code") String code, Model mo) {

        String accessToken = kakaoService.getAccessTokenFromKakao(code);        
        KakaoUserInfo userInfo = kakaoService.getUserInfo(accessToken); 

        // if (userInfo != null) {
        //     kakaoService.kakaoLogout(accessToken); //로그인해서 정보만 가져온 뒤 로그아웃
        //     System.out.println("카카오 로그아웃 완료");
        // }
        //카카오 로그인 >> 정보를 가져온 뒤 DB에 저장 >> 카카오 로그아웃
        System.out.println("accessToken: " + accessToken);
        System.out.println("userInfo: " + userInfo.getKakaoAccount().getEmail());
        mo.addAttribute("userInfo", userInfo.getKakaoAccount().getEmail());
        
        return "member/join"; // join.html로 렌더링
    }
}