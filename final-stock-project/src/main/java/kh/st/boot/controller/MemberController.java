package kh.st.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.dto.JoinDTO;
import kh.st.boot.model.dto.LoginDTO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.MemberService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private MemberService memberService;
	
	//로그인
    @GetMapping("/login")
    public String login(){
        //화면 미구현
        return "member/login";
    }
    //로그인post
    @PostMapping("/login")
    public String login_post(Model mo, LoginDTO user_){
    	//화면에서 id, pw, re(자동로그인 여부 => on, null로 값이 전달됨) 가져옴
    	System.out.println("입력받은 로그인 정보 : " + user_);//디버깅용

        //받은 정보를 DB에서 있는지 없는지 확인 함 
        MemberVO user = memberService.login(user_);

        if (user == null) {
            //실패

            return "member/login"; //다시 로그인 하세용
        } else {
            // 성공
            //user_가 on 값을 가져온 경우 *(null일때 오류가 난다면 수정해 주어야 할)
            if (user_.getRe().equals("on")) {
                user.setAuto_login(true); //자동로그인 하겠습니다.
            } else {
                user.setAuto_login(false); //자동로그인 안하겠습니다.
            }
            
            //postHandle에서 사용하기 위해 mo에 user 저장 (자동로그인을 위한 re값이 처리되어 있습니다.)
            mo.addAttribute("user", user);
            return "home";
        }
    }
    
    //로그아웃
    @GetMapping("/logout")
    public String logout(Model mo, HttpSession session, HttpServletResponse response){

        //세션에서 user 가져옵니다.
        MemberVO user = (MemberVO)session.getAttribute("user");

        //로그인상태가 아닐 시
        if (user == null) {

            mo.addAttribute("msg", "로그인 상태가 아닙니다.");
            mo.addAttribute("url", "/home");
            return "util/msg";
        }

        //로그인 쿠키가 있을 경우를 대비해서
        if (user != null) {
            //DB user cookie 정보를 null로 변경
            user.setMb_cookie(null);
            user.setMb_cookie_limit(null);
            memberService.setUserCookie(user);

            // AUTO_LOGIN 쿠키 삭제
            deleteCookie(response , "AUTO_LOGIN");
        }

        //서버의 세션에서 user정보를 삭제
        session.removeAttribute("user");
        return "home";
    }

    //회원가입
    @GetMapping("/join")
    public String join(){
    	//화면 미구현
    	return "member/join";
    }
    
    
    @PostMapping("/join")
    public String join_post(Model mo, JoinDTO user_) {
    	//LoginDTO를 나중에 MemberVO로 바꾸어 주세용
        //들어가야할 정보 id, pw, name, nick, hp, email, birth, emailing(on, null) 
    	System.out.println("회원가입시 정보 : " + user_);

        //나중에 추가할 수 있을만한 정보 addr(주소) account 회원 전용 주식 계좌, zip(우편번호)
        //히든으로 들어갈 정보 datetime(가입일자)
        //디폴트 설정 fail = 0 , level = 1, point = 50
        
        //회원가입의 성공, 실패 여부(중복 확인 등은 화면에서 진행)
        Boolean res = memberService.join(user_);

        if (res) {
            //회원가입이 성공일 시
            return "home";
        } else {
            //회원가입이 실패일 시
            return "member/join";
        }

    }


    //쿠키 사용할 일이 많아지면 Cookie Manager class를 만들어서 사용하자
    public void deleteCookie(HttpServletResponse response, String cookie_name){
        //받아온 쿠키이름을 가진 쿠키를 값 null이 들어간 상태로 생성
        Cookie cookie  = new Cookie(cookie_name, null);
        //생성한 쿠키의 기간을 0으로 설정
        cookie.setMaxAge(0);
        //화면에 쿠키를 저장(기존의 쿠키와 같은 이름으로) -> 쿠키 값과, 기간이 0으로 되서 삭제됨
        response.addCookie(cookie);
    }
}
