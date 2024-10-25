package kh.st.boot.config;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.st.boot.model.dto.DashListDTO;
import kh.st.boot.model.dto.HotStockDTO;
import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.ConfigService;
import kh.st.boot.service.MemberService;

@ControllerAdvice
@Controller
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
    
    @ModelAttribute("hotlist")
    public List<HotStockDTO> getHotStockList(){
		return configService.getHotStockList();
    }
    
    @PostMapping("/dashlist")
    public String getDashList(@RequestParam Map<String, Object> param, Model model){
    	List<DashListDTO> list = configService.getDashList(param);

    	model.addAttribute("dashList", list);
    	
    	return "layout/sidebar :: #dash-ajax";
    }
}
