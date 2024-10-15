package kh.st.boot.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.st.boot.model.vo.StockPriceVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.model.vo.WishVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.pagination.StockCriteria;
import kh.st.boot.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
@RequestMapping("/stock")
public class StockController {
	
	private StockService stockService;
	
	@GetMapping(value = {"/stockList/{type}", "/stockList/{type}/{mrk}", "/stockList"})
	public String stockList(Model model, Principal principal, StockCriteria cri, @PathVariable(required = false) String type, @PathVariable(required = false) String mrk) {
		
		String mb_id = null;
		
		if(principal != null) {
			mb_id = principal.getName();
		}

		String typeText = null;
		
		if(type != null && !type.equals("all")) {
			List<String> types = new ArrayList<String>();
			types.add(type);
			cri.setSt_type(types);
			if(type.equals("KOSPI")) {
				typeText = "코스피 시장만 보기";
			} else if(type.equals("KOSDAQ")) {
				typeText = "코스닥 시장만 보기";
			}
			
		}
		String mrkText = null;
		if(mrk != null) {
			cri.setMrk(mrk);
			switch (mrk) {
			case "medium":
				mrkText = "중형주(3,000억원 ~ 1조원 미만)";
			break;
			case "large":
				mrkText = "대형주(1조원 이상)";
			break;
			default:
				mrkText = "소형주(3,000억원 미만)";
				break;
			}
		}
		
		List<StockVO> list = stockService.getCompanyList("", cri);
		PageMaker pm = stockService.getPageMaker(cri);
		
		int total = pm.getTotalCount();
		
		List<StockPriceVO> priceList = new ArrayList<StockPriceVO>();
		String price_text = "";
		for(StockVO tmp : list) {
			tmp.setWish("N");
			StockPriceVO price = stockService.getStockPrice(null, tmp.getSt_code());
			double amount = Double.parseDouble(price.getSi_mrkTotAmt()); // 문자열을 Double로 변환
			if (amount >= 1_000_000_000_000L) { // 1조 이상
			    double trillion = amount / 1_000_000_000_000L; // 조 단위로 변환
			    price_text = String.format("%.1f조원", trillion); // 소수점 첫째 자리까지 표시
			} else if (amount >= 1_000_000_000) { // 1억 이상
			    double hundredMillion = amount / 1_000_000_000; // 억 단위로 변환
			    price_text = String.format("%.1f억원", hundredMillion); // 소수점 첫째 자리까지 표시
			} else if (amount >= 1_000) { // 1천 이상
			    double thousand = amount / 1_000; // 천 단위로 변환
			    price_text = String.format("%.1f천원", thousand); // 소수점 첫째 자리까지 표시
			} else { // 그 이하
			    price_text = String.format("%.0f원", amount); // 원 단위로 표시
			}
			price.setPrice_text(price_text);
			priceList.add(price);
			
			//회원일 때 주식을 찜했는지 체크해볼겟
			if(mb_id != null || mb_id != "") {
				WishVO wish = stockService.wishCheck(tmp.getSt_code(), mb_id);
				if(wish != null) {
					tmp.setWish("Y");
				}
			}
		}
		
		model.addAttribute("list", list);
		model.addAttribute("prices", priceList);
		model.addAttribute("pm", pm); // 페이지 정보 추가
		model.addAttribute("total", total);
		model.addAttribute("type", type);
		model.addAttribute("mrk", mrk);
		model.addAttribute("typeText", typeText);
		model.addAttribute("mrkText", mrkText);
		model.addAttribute("mb_id", mb_id);
		
		return "stockuser/stockList";
	}
	
	@GetMapping("/detail/{st_code}")
	public String stockDetail(Model model, Principal principal, @PathVariable String st_code) {
		StockVO stock = stockService.getCompanyOne(st_code);
		
		List<StockPriceVO> list = stockService.getStockInfoList(st_code);
		StockPriceVO today = null;
		
		if (list != null) {
			// 0번째 값 가져오기
			today = list.get(0);
		}
		
		model.addAttribute("stock", stock);
		model.addAttribute("list", list);
		model.addAttribute("today", today);
		System.out.println(today);
		return "stockuser/detail";
	}
	
	@PostMapping("/wish")
	@ResponseBody
	public Map<String, String> stockWish(@RequestParam String st_code, @RequestParam String status, Principal principal, HttpServletRequest req, HttpServletResponse res) {
		String mb_id = null;
		Map<String, String> result = new HashMap<String, String>();
		
		if(principal != null) {
			mb_id = principal.getName();
		} else {
			result.put("res", "fail");
			result.put("msg", "로그인한 회원만 이용가능합니다.");
			return result;
		}
		
		boolean boRes = false;
		
		WishVO chkWish = stockService.wishCheck(st_code, mb_id);
		
		if(chkWish == null) {
			status = "N";
		} else {
			status = "Y";
		}

		status = status.toUpperCase();
		
		
		if(status.equals("Y")) {
			boRes = stockService.wishDelete(st_code, mb_id);
		} else {
			boRes =stockService.wishInsert(st_code, mb_id);
		}
		
		if(boRes) {
			result.put("res", "success");
			if(status.equals("Y")) {
				result.put("msg", "위시를 정상적으로 삭제했습니다.");
			} else {
				result.put("msg", "위시를 정상적으로 등록했습니다.");
			}
		} else {
			result.put("res", "fail");
			result.put("msg", "위시를 처리하지 못했습니다.");
		}
		
		return result;
	}
	
}
