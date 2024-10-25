package kh.st.boot.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.st.boot.service.StocksHeaderService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/stock")
public class CommunityNewsController {
	
	private StocksHeaderService stocksHeaderService;
	
	@GetMapping("/{st_code}/news")
	public String CommunityNews(Model model, Principal principal, @PathVariable String st_code) {
		System.out.println(st_code);
		model = stocksHeaderService.getModelSet(model, principal, st_code);
		return "newspaper/news";
	}
}
