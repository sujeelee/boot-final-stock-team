package kh.st.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/myaccount")
public class MyAccountController {

	@GetMapping("/asset")
	public String asset() {

		return "myaccount/asset";
	}
	
	@GetMapping("/transactions")
	public String transactions() {
		
		return "myaccount/transactions";
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
