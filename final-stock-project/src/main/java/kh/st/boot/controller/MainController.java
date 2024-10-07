package kh.st.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.vo.MemberVO;

@Controller
public class MainController {

	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}

}
