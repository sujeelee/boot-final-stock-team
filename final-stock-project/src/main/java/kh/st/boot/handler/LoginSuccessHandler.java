package kh.st.boot.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
		
		String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            request.getSession().removeAttribute("prevPage");
        }

        // 기본 URI
        String uri = "/";
        
        if (prevPage != null && !prevPage.equals("")) {
            // 회원가입 - 로그인으로 넘어온 경우 "/"로 redirect
            if (prevPage.contains("/member/join")) {
                uri = "/";
            } else {
                uri = prevPage;
            }
        }
		response.sendRedirect(uri);// 이전 페이지로

	}

}
