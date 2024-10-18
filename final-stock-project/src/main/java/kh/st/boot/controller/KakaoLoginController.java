package kh.st.boot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.st.boot.service.KakaoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {

        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        System.out.println(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
