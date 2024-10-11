package kh.st.boot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositOrderVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.pagination.TransCriteria;
import kh.st.boot.service.StockService;
import kh.st.boot.service.TransService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/myaccount/transactions")
public class TransactionsController {
	
	private StockService stockService;
	private TransService transService;
	
	@GetMapping("/{type}")
	public String transactions(Model model, Principal principal, TransCriteria cri, @PathVariable String type) {
		/*if(principal == null) {
			model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
		}
		String mb_id = principal.getName();*/
		String mb_id = "zelda1234";
		cri.setPerPageNum(2); //여기서는 한번에 n개까지 보여줄게요
		// 거래내역을 가져오는 코드
		List<DepositVO> list = transService.getDepositList(mb_id, cri);
		//페이지를 넣게 되
		PageMaker pm = transService.getPageMaker(cri, mb_id);
		//계좌 잔액을 가져오게 되
		AccountVO ac = transService.getAccountAmt(mb_id);
		
		for(DepositVO tmps : list) {
			String content_view = "";
			if(tmps.getDe_stock_code() == null || tmps.getDe_stock_code() == "") {
				String od_id = tmps.getDe_content().trim().split("주문번호 : ")[1];
				DepositOrderVO dov = transService.getDepositOrder(od_id); 
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
	
	@GetMapping("/{type}/{detail}")
	public String transactionsAlpha(Model model, Principal principal, TransCriteria cri, @PathVariable String type, @PathVariable String detail) {
		/*if(principal == null) {
			model.addAttribute("msg", "회원만 이용가능합니다.\n로그인 페이지로 이동합니다.");
        	model.addAttribute("url", "/member/login");
        	
            return "util/msg";
		}
		String mb_id = principal.getName();*/
		String mb_id = "zelda1234";
		cri.setPerPageNum(2); //여기서는 한번에 n개까지 보여줄게요
		// 거래내역을 가져오는 코드
		List<DepositVO> list = transService.getDepositList(mb_id, cri);
		//페이지를 넣게 되
		PageMaker pm = transService.getPageMaker(cri, mb_id);
		//계좌 잔액을 가져오게 되
		AccountVO ac = transService.getAccountAmt(mb_id);
		
		for(DepositVO tmps : list) {
			String content_view = "";
			if(tmps.getDe_stock_code() == null || tmps.getDe_stock_code() == "") {
				String od_id = tmps.getDe_content().trim().split("주문번호 : ")[1];
				DepositOrderVO dov = transService.getDepositOrder(od_id); 
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
}
