package kh.st.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.service.NewspaperService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private NewspaperService newspaperService;

	@GetMapping("/news")
	public String newsPage(Model model) {
		List<NewsPaperVO> newspapers = newspaperService.getAllNewspapers();
		model.addAttribute("newspapers", newspapers);
		return "admin/news"; // admin/news.html로 이동
	}

	@PostMapping("/newspapers/add")
	public String addNewspaper(@ModelAttribute NewsPaperVO newspaper) {
		newspaperService.addNewspaper(newspaper);
		return "redirect:/news"; // 신문사 목록으로 리다이렉트
	}

	@PostMapping("/newspapers/delete/{np_no}")
	public String deleteNewspaper(@PathVariable int np_no) {
		newspaperService.deleteNewspaper(np_no);
		return "redirect:/news"; // 신문사 목록으로 리다이렉트
	}

	@GetMapping("/newspapers/search")
	public String searchNewspapers(@RequestParam(required = false) String name,
			@RequestParam(required = false) String status, Model model) {
		List<NewsPaperVO> newspapers = newspaperService.searchNewspapers(name, status);
		model.addAttribute("newspapers", newspapers);
		return "admin/news"; // admin/news.html로 이동
	}
}
