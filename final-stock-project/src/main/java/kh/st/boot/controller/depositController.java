package kh.st.boot.controller;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.st.boot.model.vo.DepositOrderVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.service.DepositService;
import kh.st.boot.service.MemberService;


@Controller
@RequestMapping("/deposit")
public class depositController {
	@Autowired
	DepositService depositService;
	
	private final String CLIENT_ID = "S1_6eaa0db1afdc41f3becb770878d67d25"; 
    private final String SECRET_KEY = "e80d068e400649a6ada66777fa350d40";
	
    @GetMapping("")
	public String home(Model model, Principal principal) {
        
    	//로그인상태가 아닐 시
        if (principal == null) {
        	model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
        }
        
        model.addAttribute("clientId", CLIENT_ID);
        
		return "deposit/depositOrder";
	}
    
    @PostMapping("/clientAuth")
    public String main(HttpServletRequest request, Model model){ 
        String resultMsg = request.getParameter("resultMsg");
        String resultCode = request.getParameter("resultCode"); 
        model.addAttribute("resultMsg", resultMsg);
        //응답 request body 로그 확인
        /*Enumeration<String> params = request.getParameterNames(); 
        while(params.hasMoreElements()){
        	String paramName = params.nextElement();
        	System.out.println(paramName+" : "+request.getParameter(paramName));
        }        */

        if (resultCode.equalsIgnoreCase("0000")) {
            // 결제 성공 비즈니스 로직 구현
        	DepositOrderVO upOrder = new DepositOrderVO();
        	
        	upOrder.setDo_auth(request.getParameter("authToken"));
        	upOrder.setDo_tno(request.getParameter("tid"));
        	upOrder.setDo_status("완료");
        	upOrder.setDo_od_id(request.getParameter("orderId"));
        	
        	boolean res = depositService.updateOrder(upOrder);
        	
        	if(res == false) {
        		model.addAttribute("msg" , "충전오류가 발생했습니다.");
    			model.addAttribute("url" , "/deposit");
        	} else {
	        	model.addAttribute("msg" , "예치금이 충전되었습니다.");
				model.addAttribute("url" , "/deposit");
        	}
        	
        } else {
        	model.addAttribute("msg" , resultMsg);
			model.addAttribute("url" , "/deposit");
        }
        return "util/msg";
    }
    
    @PostMapping("/insertOrder")
    @ResponseBody
    public String insertOrder(@RequestParam Map<String, Object> params, Model model, Principal principal, HttpServletRequest req, HttpServletResponse res){ 
    	String id = depositService.getOrderId();
    	DepositOrderVO newOrder = new DepositOrderVO();
    	// VO 클래스의 필드 목록을 가져옴
        Field[] fields = newOrder.getClass().getDeclaredFields();
        
        for (Field field : fields) {
			// 필드명을 가져옴
			String fieldName = field.getName();
			
			// Map에서 필드명과 일치하는 키가 있는지 확인
			if (params.containsKey(fieldName)) {
			    field.setAccessible(true); // private 필드에도 접근할 수 있게 설정
			try {
					Object val = (String)params.get(fieldName);
					if("do_price".equals(fieldName)) {
						val = Integer.parseInt((String) val);
					}
			    	// Map에서 해당하는 값을 가져와서 VO 필드에 할당
			        field.set(newOrder, val);
			    } catch (IllegalAccessException e) {
			        e.printStackTrace();
			    }
			}
        }
        
        newOrder.setDo_od_id(id);
		newOrder.setDo_status("대기");
		//newOrder.setMb_id(user.getMb_id()); //테스트 아이디 지금 회원가입 안됨 ㅅㅂ 20241010
		newOrder.setMb_id(principal.getName());
		id = depositService.insertOrder(newOrder);
		return id;
    }
	
}
