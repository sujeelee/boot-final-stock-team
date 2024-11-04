package kh.st.boot.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.st.boot.dao.MemberDAO;
import kh.st.boot.model.vo.FollowVO;
import kh.st.boot.service.CommunityService;
import kh.st.boot.service.FollowerService;
import kh.st.boot.service.StocksHeaderService;
import kh.st.boot.service.newspaperService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/stock/{st_code}/follower")
public class FollowerController {

	private FollowerService followerService; 
	
    @PostMapping("/addfollower")
    public Map<String, Object> addFoller(@RequestParam String Fo_mb_id,Principal principal){
        Map<String, Object> result = new HashMap<String, Object>();
		if (principal == null) {
			result.put("res", "false");
			result.put("msg", "회원만 가능합니다.");
			return result;
		}
		FollowVO follow = new FollowVO();
		String mb_id = principal.getName();
		
		follow.setFo_mb_id(Fo_mb_id);
		follow.setMb_id(mb_id);
		
		


        return result;
    }


}
