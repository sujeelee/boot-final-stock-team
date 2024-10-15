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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.util.DateUtil;
import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositOrderVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberApproveVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.model.vo.PointVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.pagination.TransCriteria;
import kh.st.boot.service.MemberService;
import kh.st.boot.service.MyAccountService;
import kh.st.boot.service.NewsService;
import kh.st.boot.service.StockService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/myaccount")
public class MyAccountController {
	
	private MyAccountService myAccountService;
	private MemberService memberService;
	private NewsService newsService;
	private StockService stockService;
	
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
	
	@GetMapping("/settings")
	public String settings(Model model, Principal principal) {
		//로그인상태가 아닐 시
        if (principal == null) {
        	model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
            return "util/msg";
        }
		return "myaccount/settings";
	}
	
	@PostMapping("/settings")
	public String settingsPost(MemberApproveVO mp) {
		if(myAccountService.getMemberApprove(mp.getMb_no()) == null) {
			myAccountService.insertMemberApprove(mp);
		}else {
			if(myAccountService.deleteMemberApprove(mp.getMb_no())) {
				myAccountService.insertMemberApprove(mp);
			}
		}
		return "myaccount/settings";
	}
	
	@ResponseBody
	@PostMapping("/settings/list")
	public Map<String, Object> settingsList(String mp_type){
		Map<String, Object> map = new HashMap<String, Object>();
		List<?> list = null;
		switch(mp_type) {
		case "news":
			list = newsService.getNewsPaperList(); 
			map.put("list", list);
			break;
		case "stock":
			list = myAccountService.getStockList();
			map.put("list", list);
			break;
		}
		return map;
	}
	
	@GetMapping("/password")
	public String password() {
		return "myaccount/password";
	}
	
	@PostMapping("/password")
	@ResponseBody
	public Map<String, Object> passwordPost(Model model, Principal principal, String password, MemberVO member, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberVO user = memberService.findById(principal.getName());
		boolean res = myAccountService.checkPw(user, password);
	    if(res) {
	    	if(myAccountService.updatePw(principal.getName(), member.getMb_password())) {
	    		map.put("success", true);
	    		session.removeAttribute("user");
	    	}else {
	    		map.put("success", false);
	    	}
	    } else {
	        map.put("success", false);
	    }
	    return map;
	}
	
	@GetMapping("/delete")
	public String delete() {
		return "myaccount/delete";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> deletePost(Model model, Principal principal, MemberVO member) {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberVO user = memberService.findById(principal.getName());
		boolean res = myAccountService.checkPw(user, member.getMb_password());
		if(user.getMb_id().equals(member.getMb_id()) && res) {
			if(myAccountService.deleteUser(user)) {
				map.put("success", true);
			}else {
				map.put("success", false);
			}
		}else {
			map.put("success", false);
		}
	    return map;
	}
	
	@ResponseBody
	@PostMapping("/checkStatus")
	public Map<String, Object> checkStatus(Principal principal){
		Map<String, Object> map = new HashMap<String, Object>();
		MemberVO user = memberService.findById(principal.getName());
		MemberApproveVO mp = myAccountService.getMemberApprove(user.getMb_no());
		if(mp == null) {
			map.put("status", "none");
		}
		else if(mp.getMp_yn() == null) {
			if(mp.getMp_type().equals("news")) {
				map.put("status", "news");
			}
			else if(mp.getMp_type().equals("stock")) {
				map.put("status", "stock");
			}
		}
		else if(mp.getMp_yn().equals("y")) {
			map.put("status", "success");
			if(mp.getMp_type().equals("stock")) {
				mp.setMp_company(myAccountService.getStockName(mp.getMp_company()));
			}
			map.put("mp", mp);
			
		}
		else if(mp.getMp_yn().equals("n")) {
			map.put("status", "fail");
		}
		return map;
	}
	
	@ResponseBody
	@PostMapping("/cancel")
	public Map<String, Object> cancel(Principal principal){
		Map<String, Object> map = new HashMap<String, Object>();
		String mb_id = principal.getName();
		MemberVO user = memberService.findById(mb_id);
		boolean res = myAccountService.deleteMemberApprove(user.getMb_no());
		if(res) {
			map.put("status", true);
		}else {
			map.put("status", false);
		}
		return map;
	}
	
	@GetMapping("/transactions/{type}")
	public String transactions(Model model, Principal principal, TransCriteria cri, @PathVariable String type) {
		if(principal == null) {
			model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
		}
		String mb_id = principal.getName();
		cri.setPerPageNum(10); //여기서는 한번에 n개까지 보여줄게요
		// 거래내역을 가져오는 코드
		List<DepositVO> list = myAccountService.getDepositList(mb_id, cri);
		//페이지를 넣게 되
		PageMaker pm = myAccountService.getPageMaker(cri, mb_id);
		//계좌 잔액을 가져오게 되
		AccountVO ac = myAccountService.getAccountAmt(mb_id);
		
		for(DepositVO tmps : list) {
			String content_view = "";
			if(tmps.getDe_stock_code() == null || tmps.getDe_stock_code() == "") {
				String od_id = tmps.getDe_content().trim().split("주문번호 : ")[1];
				DepositOrderVO dov = myAccountService.getDepositOrder(od_id); 
				content_view = dov.getDo_name();
			} else {
				StockVO stock = stockService.getCompanyOne(tmps.getDe_stock_code());
				content_view = stock.getSt_name();
				content_view += tmps.getDe_content().trim().split("매수 :")[1];
			}
			tmps.setContent_view(content_view);
			tmps.setDe_content(tmps.getDe_content().trim().split(" :")[0]);
		}
		
		model.addAttribute("account", ac);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		model.addAttribute("pm", pm); // 페이지 정보 추가
		
		return "myaccount/transactions";
	}
	
	@GetMapping("/transactions/{type}/{detail}")
	public String transactionsAlpha(Model model, Principal principal, TransCriteria cri, @PathVariable String type, @PathVariable String detail) {
		if(principal == null) {
			model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
		}
		String mb_id = principal.getName();
		cri.setPerPageNum(10); //여기서는 한번에 n개까지 보여줄게요
		// 거래내역을 가져오는 코드
		List<DepositVO> list = myAccountService.getDepositList(mb_id, cri);
		//페이지를 넣게 되
		PageMaker pm = myAccountService.getPageMaker(cri, mb_id);
		//계좌 잔액을 가져오게 되
		AccountVO ac = myAccountService.getAccountAmt(mb_id);
		
		for(DepositVO tmps : list) {
			String content_view = "";
			if(tmps.getDe_stock_code() == null || tmps.getDe_stock_code() == "") {
				String od_id = tmps.getDe_content().trim().split("주문번호 : ")[1];
				DepositOrderVO dov = myAccountService.getDepositOrder(od_id); 
				content_view = dov.getDo_name();
			} else {
				StockVO stock = stockService.getCompanyOne(tmps.getDe_stock_code());
				content_view = stock.getSt_name();
				content_view += tmps.getDe_content().trim().split("매수 :")[1];
			}
			tmps.setContent_view(content_view);
			tmps.setDe_content(tmps.getDe_content().trim().split(" :")[0]);
		}
		
		model.addAttribute("account", ac);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		model.addAttribute("detail", detail);
		model.addAttribute("pm", pm); // 페이지 정보 추가
		
		return "myaccount/transactions";
	}
	
	@GetMapping("/profit")
	public String profit(Model model, Principal principal) {
		//로그인상태가 아닐 시
        if (principal == null) {
        	model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
        }
        String mb_id = principal.getName();
        
		return "myaccount/profit";
	}
	
	@GetMapping("/point")
	public String point() {
		
		return "myaccount/point";
	}

}
