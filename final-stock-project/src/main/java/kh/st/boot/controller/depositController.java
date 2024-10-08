package kh.st.boot.controller;

import java.util.Enumeration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import kh.st.boot.service.DepositService;


@Controller
@RequestMapping("/deposit")
public class depositController {
	@Autowired
	DepositService depositService;
	
	private final String CLIENT_ID = "S1_6eaa0db1afdc41f3becb770878d67d25"; 
    private final String SECRET_KEY = "e80d068e400649a6ada66777fa350d40";
	
    @GetMapping("")
	public String home(Model model) {
		
		//String od_id = depositService.getOrderId();
    	UUID id = UUID.randomUUID();
        model.addAttribute("orderId", id);
        model.addAttribute("clientId", CLIENT_ID);
        
		return "deposit/depositOrder";
	}
    
    @RequestMapping("/clientAuth")
    public String main(HttpServletRequest request, Model model){ 
        String resultMsg = request.getParameter("resultMsg");
        String resultCode = request.getParameter("resultCode"); 
        model.addAttribute("resultMsg", resultMsg);

        if (resultCode.equalsIgnoreCase("0000")) {
            // 결제 성공 비즈니스 로직 구현
        } else {
            // 결제 실패 비즈니스 로직 구현
        }

        //응답 request body 로그 확인
        Enumeration<String> params = request.getParameterNames(); 
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            System.out.println(paramName+" : "+request.getParameter(paramName));
        }        

        return "deposit/depositOrder"; 
    }
	
}
