package kh.st.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/deposit")
public class depositController {

	@GetMapping("")
	public String home(Model model) {
		return "deposit/depositOrder";
	}
	
}
