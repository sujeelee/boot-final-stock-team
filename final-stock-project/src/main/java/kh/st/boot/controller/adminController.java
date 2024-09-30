package kh.st.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {

	  @GetMapping("/news")
	    public String newsPage() {
	        return "/admin/news";  // admin/news.html로 이동
	    }
}
