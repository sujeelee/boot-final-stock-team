package kh.st.boot.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.st.boot.info.KakaoUserInfo;
import kh.st.boot.service.KakaoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/oauth/kakao/callback")
    public String callback(@RequestParam("code") String code, Model mo) {

        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        System.out.println("accessToken: " + accessToken);

        KakaoUserInfo userInfo = kakaoService.getUserInfo(accessToken);
        System.out.println("userInfo: " + userInfo.getKakaoAccount().getEmail());


        
        return "";
        // return ResponseEntity.ok()
        // .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // 필요시 토큰을 헤더에 포함
        // .body(userInfo); // 사용자 정보를 반환
    }
}
