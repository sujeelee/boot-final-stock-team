package kh.st.boot.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		int money = 0;
		for(DepositVO deposit : depositList) {
			money += deposit.getDe_num();
		}
		// 수익률
		int rateOfReturn = (account.getAc_deposit() - (account.getAc_deposit() - money)) / (account.getAc_deposit() - money) * 100;
		
		model.addAttribute("account", account);
		model.addAttribute("money", money);
		model.addAttribute("rateOfReturn", rateOfReturn);
		return "myaccount/asset";
	}
	
	@ResponseBody
	@PostMapping("/asset/period")
	public Map<String, Object> period(@RequestParam String period, Principal principal){
		Map<String, Object> map = new HashMap<String, Object>();
		String mb_id = principal.getName();
		String date = "";
		String text = "";
		switch(period) {
		case "1week":
			// 지난주 날짜 
			date = DateUtil.getLastWeek();
			text = "지난주";
			break;
		case "1month":
			// 지난달 날짜
			date = DateUtil.getLastMonth();
			text = "지난달";
			break;
		case "3month":
			// 3달전 날짜
			date = DateUtil.getLast3Month();
			text = "3달전";
			break;
		case "1year":
			// 1년전 날짜
			date = DateUtil.getLastYear();
			text = "1년전";
			break;
		}
		// 회원 아이디로 예치금 잔액을 가져오는 코드
		AccountVO account = myAccountService.getAccountById(mb_id);
		// 해당 기간들의 거래내역들을 가져옴
		List<DepositVO> depositList = myAccountService.getDepositListByDate(mb_id, date);
		int money = 0;
		for(DepositVO deposit : depositList) {
			money += deposit.getDe_num();
		}
		// 수익률
		int rateOfReturn = (account.getAc_deposit() - (account.getAc_deposit() - money)) / (account.getAc_deposit() - money) * 100;
		map.put("date", date);
		map.put("text", text);
		map.put("money", money);
		map.put("rateOfReturn", rateOfReturn);
		return map;
	}
	/*
	@GetMapping("/transactions")
	public String transactions(Model model, Principal principal) {
		String mb_id = principal.getName();
		// 거래내역을 가져오는 코드
		List<DepositVO> list = myAccountService.getDepositList(mb_id);
		model.addAttribute(list);
		return "myaccount/transactions";
	}
	*/
	
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
