package kh.st.boot.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import kh.st.boot.model.vo.PointVO;
import kh.st.boot.service.MyAccountService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/myaccount")
public class MyAccountController {
	
	private MyAccountService myAccountService;
	
	@GetMapping("/asset")
	public String asset(Model model, Principal principal) {
		//로그인상태가 아닐 시
        if (principal == null) {
        	model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
        }
		String mb_id = principal.getName();
		// 회원 아이디로 예치금 잔액을 가져오는 코드
		AccountVO account = myAccountService.getAccountById(mb_id);
		String lastWeek = DateUtil.getLastWeek(); // 지난주 날짜 정보
		// 일주일 전까지의 거래 내역을 가져옴
		List<DepositVO> depositList = myAccountService.getDepositListByDate(mb_id, lastWeek);
		int money = 0;			// 얻은 이익
		int rateOfReturn = 0;	// 수익률
		List<Map<String, Object>> graphData = new ArrayList<>();
		if(depositList != null) {
			for(DepositVO deposit : depositList) {
				money += deposit.getDe_num();
				// 각 거래의 날짜와 금액을 graphData에 추가
		        Map<String, Object> graphItem = new HashMap<>();
		        graphItem.put("date", deposit.getDe_datetime());	// 거래 날짜
		        graphItem.put("amount", deposit.getDe_num());		// 거래 금액
		        graphData.add(graphItem);
	
			}
			int begin = account.getAc_deposit() - money; // 초기 금액
			if (begin != 0) {
			    rateOfReturn = (money * 100) / begin;
			}
		}
		int stockMoney = 0; // 투자중인 금액
		if(depositList != null) {
			for(DepositVO deposit : depositList) {
				if(deposit.getDe_stock_code() != null) {
					stockMoney += deposit.getDe_num();
				}
			}
		}
		int orderMoney = account.getAc_deposit() - stockMoney; // 주문 가능 금액
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String isMonth = format.format(now) + "-01";
		List<DepositVO> depositListMonth = myAccountService.getDepositListByDate(mb_id, isMonth);
		int monthMoney = 0;
		if(depositListMonth != null) {
			for(DepositVO deposit : depositList) {
				if(deposit.getDe_stock_code() != null) {
					monthMoney += deposit.getDe_num();
				}
			}
		}
		List<PointVO> pointList = myAccountService.getPointList(mb_id); // 포인트 리스트 가져와야됨
		int pointMoney = 0;
		if(pointList != null) {
			for(PointVO point : pointList) {
				pointMoney += point.getPo_num();
			}
		}
		model.addAttribute("money", money);
		model.addAttribute("account", account);
		model.addAttribute("rateOfReturn", rateOfReturn);
		model.addAttribute("graphData", graphData);
		model.addAttribute("stockMoney", stockMoney);	// 투자중인 금액
		model.addAttribute("orderMoney", orderMoney);	// 주문 가능 금액
		model.addAttribute("monthMoney", monthMoney);	// 월 수익
		model.addAttribute("pointMoney", pointMoney);	// 포인트
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
		int money = 0;			// 얻은 이익
		int rateOfReturn = 0;	// 수익률
		List<Map<String, Object>> graphData = new ArrayList<>();
		if(depositList != null) {
			for(DepositVO deposit : depositList) {
				money += deposit.getDe_num();
				// 각 거래의 날짜와 금액을 graphData에 추가
		        Map<String, Object> graphItem = new HashMap<>();
		        graphItem.put("date", deposit.getDe_datetime());	// 거래 날짜
		        graphItem.put("amount", deposit.getDe_num());		// 거래 금액
		        graphData.add(graphItem);
	
			}
			int begin = account.getAc_deposit() - money;	// 초기 금액
			if (begin != 0) {
			    rateOfReturn = (money * 100) / begin;
			}
		}
		map.put("text", text);
		map.put("money", money);
		map.put("rateOfReturn", rateOfReturn);
		map.put("graphData", graphData);  // 그래프에 사용할 데이터 추가
		return map;
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
