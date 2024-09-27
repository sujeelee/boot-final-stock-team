package kh.st.boot.handler;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.MemberService;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private MemberService memberService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {

		
		HttpSession session = request.getSession();
		
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			UserDetails user_ = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			System.out.println(user_.getUsername());
			MemberVO user = memberService.findById(user_.getUsername());
			session.setAttribute("user", user);
			System.out.println(user);

			// 자동 로그인이 false이면 리턴
			if (!user.isAuto_login()) {
				response.sendRedirect("/");
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
		} else {
			String user = "anonymousUser";
		}
		response.sendRedirect("/");//메인으로

	}

}
