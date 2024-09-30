package kh.st.boot.controller;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.internet.MimeMessage;

import kh.st.boot.model.util.CustomUtil;
import kh.st.boot.service.MailService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private CustomUtil customUtil;

    private JavaMailSender mailSender;

    private MailService mailService;

    //코드를 이메일로 보내는 일
    @PostMapping("/ajax/set_check")
    public @ResponseBody boolean set_Email_Check(@RequestParam("evc_email") String evc_email){
        //렌덤한 6자리 숫자열
        int code = customUtil.getCustomNumber(6);
        System.out.println(code);
        System.out.println(evc_email);
        String str = "안녕하세요. S2D KEY 회원 인증 메일입니다. 6자리 코드를 회원가입 창에서 입력해 주세요. <p>인증 코드 :" + code + " </p>";
        boolean res = mailService.setMailCode(evc_email, code);
        if (res) res = mailSend(evc_email, "SID 인증 이메일", str);
        // t, f 보내주면, 코드 6자리 입력할 수 있는 창을 열어주기
        return res;
    }

    //유저가 코드를 확인하는 일
    @PostMapping("/ajax/get_check")
    public @ResponseBody boolean get_Email_Check(@RequestParam("evc_email") String evc_email, @RequestParam("code") int code){

        System.out.println(code);
        System.out.println(evc_email);
        
        return mailService.checkMailCode(evc_email, code);
    }



    //MailSend
    public boolean mailSend(String to, String title, String content) {

    String setfrom = "stajun@naver.com";
    try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

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

    //테스트용입니다.
    @GetMapping("/test")
    public String emailTest(){
        int code = customUtil.getCustomNumber(6);
        boolean res = mailSend("bnbz201@naver.com", "SID 인증 이메일", "<p>test 이메일입니다.<p> 이건 태그 안들어감 <p>받아온 코드 : " + code + "</p>");
        System.out.println(res);
        return "redirect:/";
    }


}
