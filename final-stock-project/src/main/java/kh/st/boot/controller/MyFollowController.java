package kh.st.boot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.st.boot.model.vo.FollowVO;
import kh.st.boot.service.MyFollowService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/myaccount/follow")
public class MyFollowController {
	
	private MyFollowService myFollowService;
	
	@GetMapping("")
	public String asset(Model model, Principal principal) {
		//로그인상태가 아닐 시
		String mb_id = null;
        if (principal == null) {
        	model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
        }
        
        mb_id = principal.getName();
        
        List<FollowVO> list = myFollowService.getFollowList(mb_id);
        
        model.addAttribute("follow", list);
        
		return "myaccount/follow";
	}

}
