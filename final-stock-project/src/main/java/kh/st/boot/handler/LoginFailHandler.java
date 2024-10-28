package kh.st.boot.handler;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.st.boot.dao.MemberDAO;

public class LoginFailHandler implements AuthenticationFailureHandler {

    private MemberDAO memberDao;

    @Autowired
    public LoginFailHandler(MemberDAO memberDao){
        this.memberDao = memberDao;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        String errorMessage = "errorType";
        // String username = request.getParameter("username"); //한번만 사용할 거라 주석처리

        //아이디 비존재
        if(exception instanceof UsernameNotFoundException){
            errorMessage = request.getParameter("username") + " 은/(는) 없는 아이디 입니다.";

            String encodingErrorMsg = URLEncoder.encode(errorMessage, "UTF-8");
            response.sendRedirect("/member/login?error=true&id=false&pw=false&msg=" + encodingErrorMsg);

        }


        //비밀번호 불일치
        if(exception instanceof BadCredentialsException){
            errorMessage = "비밀번호가 일치하지 않습니다.";
            memberDao.add_Fail_Number(request.getParameter("username"));

            String encodingErrorMsg = URLEncoder.encode(errorMessage, "UTF-8");
            response.sendRedirect("/member/login?error=true&username=" +request.getParameter("username") + "&pw=false&msg=" + encodingErrorMsg);
        }


    }

}
