package kh.st.boot.config;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.ConfigService;
import kh.st.boot.service.MemberService;

@ControllerAdvice
public class GlobalControllerAdvice {
	
    @Autowired
    private ConfigService configService;
    
    @Autowired
    private MemberService memberService;

    @ModelAttribute("config")
    public AdminVO globalConfig() {
        // configService로 config 테이블 정보를 가져와서 반환
        return configService.getConfig();
    }
    
    @ModelAttribute("member")
    public MemberVO globalMember(Principal principal) {
    	MemberVO member = new MemberVO();
    	if(principal == null) {
    		member.setMb_id(null);
        } else {
        	member = memberService.findById(principal.getName());
        }
        return member;
    }
}
