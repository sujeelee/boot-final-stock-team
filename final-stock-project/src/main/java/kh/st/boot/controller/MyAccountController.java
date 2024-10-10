package kh.st.boot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.st.boot.model.util.DateUtil;
import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.service.MyAccountService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/myaccount")
public class MyAccountController {
	
	private MyAccountService myAccountService;
	
	@GetMapping("/asset")
	public String asset(Model model, Principal principal) {
		String mb_id = principal.getName();
		// 회원 아이디로 예치금 잔액을 가져오는 코드
		AccountVO account = myAccountService.getAccountById(mb_id);
		String lastWeek = DateUtil.getLastWeek(); // 지난주 날짜 정보
		// 일주일 전까지의 거래 내역을 가져옴
		List<DepositVO> depositList = myAccountService.getDepositListByDate(mb_id, lastWeek);
		System.out.println(depositList);
		int money = 0;
		for(DepositVO deposit : depositList) {
			// deposit.getDe_num();으로 바꿀것!!!!!!!!!!!!!!!!!!
			money += deposit.getDe_no();
		}
		// 수익률
		int rateOfReturn = (account.getAc_deposit() - (account.getAc_deposit() - money)) / (account.getAc_deposit() - money) * 100;
		
		model.addAttribute("account", account);
		model.addAttribute("money", money);
		model.addAttribute("rateOfReturn", rateOfReturn);
		return "myaccount/asset";
	}
	
	@GetMapping("/orders")
	public String orders() {
		
		return "myaccount/orders";
	}
	
	@GetMapping("/profit")
	public String profit() {
		
		return "myaccount/profit";
	}
	
	@GetMapping("/point")
	public String point() {
		
		return "myaccount/point";
	}
	
	@GetMapping("/settings")
	public String settings() {
		
		return "myaccount/settings";
	}
}
