package kh.st.boot.interceptor;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.MemberService;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    // 생성자 주입 사용
    public LoginInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    // 실제 handle이 수행된 후 실행 (로그인이 실행된 후)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // Model에서 user라는 객체를 가져옴
        MemberVO user = (MemberVO) modelAndView.getModel().get("user");

        // user가 null이면 (로그인 실패)
        if (user == null) {
            return;
        }

        // user가 null이 아니면 세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // 자동 로그인이 false이면 리턴
        if (!user.isAuto_login()) {
            return;
        }

        // 자동 로그인이 true이면 쿠키 생성
        String sid = session.getId(); // 세션 ID 가져오기

        // AUTO_LOGIN 쿠키 생성
        Cookie cookie = new Cookie("AUTO_LOGIN", sid);

        // 쿠키 유효기간 설정 (7일)
        int time = 60 * 60 * 24 * 7;
        cookie.setMaxAge(time);
        cookie.setPath("/"); // 모든 경로에서 사용 가능

        // DB에 쿠키 정보 저장
        Date date = new Date(System.currentTimeMillis() + time * 1000);
        user.setMb_cookie(sid); // 세션 ID를 사용자 정보에 저장
        user.setMb_cookie_limit(date); // 만료일 설정

        memberService.setUserCookie(user); // DB에 사용자 쿠키 정보 업데이트

        // 쿠키를 클라이언트에 전송
        response.addCookie(cookie);
    }
}
