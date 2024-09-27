package kh.st.boot.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.MemberService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {

    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberVO user = (MemberVO) session.getAttribute("user");
        // 로그인 체크
        if (user != null) return true;

        //없으면 쿠키 체크
        Cookie cookie = WebUtils.getCookie(request, "AUTO_LOGIN"); 
        if (cookie == null) return true; // 없으면 리턴
        String sid = cookie.getValue();
        user = memberService.findIdByCookie(sid); // 있으면 쿠키로 세션에 저장
        if (user != null) session.setAttribute("user", user);
        return true;
    }

}
