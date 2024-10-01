package kh.st.boot.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.st.boot.dao.MemberDAO;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private MemberDAO memberDao;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {

		//성공시 failNumber = 0 
		if (authentication.getName() != null) {
			memberDao.reset_Fail_Number(authentication.getName());
		}
		//관련 예제 
		/**
		 * https://velog.io/@dh1010a/Spring-Spring-Security%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-3.X-%EB%B2%84%EC%A0%84-1
		 * https://cornarong.tistory.com/81
		 * https://cornarong.tistory.com/83
		 * https://how-can-i.tistory.com/41
		 * 참고해서 작성할 예정
		 */
		
	}

}
