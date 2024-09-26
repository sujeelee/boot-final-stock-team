package kh.st.boot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.internet.MimeMessage;
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
	
    private JavaMailSender mailSender;
	
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

            return "redirect:/member/login"; //다시 로그인 하세용
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
            return "redirect:/home";
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
    public String join_post(Model mo, JoinDTO user_, String ec) {
    	//LoginDTO를 나중에 MemberVO로 바꾸어 주세용
        //들어가야할 정보 id, pw, name, nick, hp, email, birth, emailing(on, null) 
    	System.out.println("회원가입시 정보 : " + user_);

        //나중에 추가할 수 있을만한 정보 addr(주소) account 회원 전용 주식 계좌, zip(우편번호)
        //히든으로 들어갈 정보 datetime(가입일자)
        //디폴트 설정 fail = 0 , level = 1, point = 50
        
        //회원가입의 성공, 실패 여부(중복 확인 등은 화면에서 진행)
        // !!! 회원가입시 이메일로 코드를 보내주어 email 인증을 하자! 보이는 input은 email말고 다른 name으로 넣어주고
        // ec로 넣어주고 히든 > 이메일 체크가 되면 on, 아니면 null
        // 세션아이디 앞부터 6개를 짤라서 주려고 합니다
        // !!! 화면에 input hidden 을 email로 하고 인증이 되면 해당하는 이메일을 거기에 value값으로 넣어준 뒤 전송하는 방법을 사용하겠습니다.
        Boolean res = false;
        if (ec != null) {
            res = memberService.join(user_);
        }
        if (res) {
            //회원가입이 성공일 시
            return "redirect:/home";
        } else {
            //회원가입이 실패일 시
            return "redirect:/member/join";
        }

    }

    
    @PostMapping("/ajax-email")
    public @ResponseBody Map<String, String> email_Check(HttpSession session, @RequestParam("ec_email") String ec_email){    	
    	HashMap<String, String> map = new HashMap<String, String>();
    	String sids = session.getId().substring(0,6);
    	mailSend(ec_email, "SID 인증 이메일", "인증 코드 : " + sids);
    	map.put("ec", sids);
    	return map;
    }
    
    
    //메세지 보낼 것
    public boolean mailSend(String to, String title, String content) {

        String setfrom = "stajun@naver.com";
       try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper
                = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(setfrom);// 보내는사람 생략하거나 하면 정상작동을 안함
            messageHelper.setTo(to);// 받는사람 이메일
            messageHelper.setSubject(title);// 메일제목은 생략이 가능하다
            messageHelper.setText(content, true);// 메일 내용

            mailSender.send(message);
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
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
