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
	public String myAccountOpened() {
		
		return "myaccount/asset";
	}
}
