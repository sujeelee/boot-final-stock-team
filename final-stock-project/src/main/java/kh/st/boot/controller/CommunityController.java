package kh.st.boot.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.st.boot.model.vo.StockVO;
import kh.st.boot.service.StocksHeaderService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/stock/{st_code}/community")
public class CommunityController {
	private StocksHeaderService stocksHeaderService;//v
	
    @GetMapping
    public String community(Model model, Principal principal, @PathVariable String st_code) {
    	model = stocksHeaderService.getModelSet(model, principal, st_code); //v
        return "community/community";
    }
}