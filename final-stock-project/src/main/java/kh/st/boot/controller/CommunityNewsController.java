package kh.st.boot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.st.boot.model.vo.NewsVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.service.NewsService;
import kh.st.boot.service.StocksHeaderService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/stock")
public class CommunityNewsController {
	
	private StocksHeaderService stocksHeaderService;
	private NewsService newsService;
	
	@GetMapping("/{st_code}/news")
	public String CommunityNews(Model model, Principal principal, @PathVariable String st_code) {
		model = stocksHeaderService.getModelSet(model, principal, st_code);
		String st_name = ((StockVO)model.getAttribute("stock")).getSt_name();
		List<NewsVO> list = newsService.getNewsList(st_name);
		model.addAttribute("list", list);
		return "newspaper/news";
	}
}
