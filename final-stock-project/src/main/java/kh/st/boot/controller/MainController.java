package kh.st.boot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.model.vo.NewsVO;
import kh.st.boot.service.NewsService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MainController {
	
	private NewsService newsService;

	@GetMapping("/")
	public String home(Model mo, HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		mo.addAttribute("user", user);
		List<NewsVO> newsImgList = newsService.getNewsListByImg(); 	// 썸네일 있는 뉴스 최신순으로 4개
		List<NewsVO> newsList = newsService.getNewsListByNoImg();	// 썸네일 없는 뉴스 최신순으로 4개 
		mo.addAttribute("newsImgList", newsImgList);
		mo.addAttribute("newsList" ,newsList);
		return "home";
	}

}
