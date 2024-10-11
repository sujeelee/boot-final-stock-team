package kh.st.boot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.service.MyAccountService;
import kh.st.boot.service.TransService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/myaccount/transactions")
public class TransactionsController {
	
	private TransService transService;
	private MyAccountService myAccountService;
	
	@GetMapping("")
	public String transactions(Model model, Principal principal) {
		/*if(principal == null) {
			model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
		}
		String mb_id = principal.getName();*/
		String mb_id = "zelda1234"; //test용
		// 거래내역을 가져오는 코드
		List<DepositVO> list = myAccountService.getDepositList(mb_id);
		//계좌 잔액을 가져오게 되
		AccountVO ac = transService.getAccountAmt(mb_id);
		
		model.addAttribute("account", ac);
		model.addAttribute("list", list);
		System.out.println(list);
		return "myaccount/transactions";
	}
}
