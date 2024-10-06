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

import kh.st.boot.model.dto.NewspaperDTO;
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
		System.out.println("안녕");
		return "admin/news"; // admin/news.html로 이동
	}

	// 수정
	@PostMapping("/newspapers/edit")
	public String updateNewspaper(@RequestParam("np_no") int np_no, @RequestParam("np_name") String np_name,
			@RequestParam("np_use") int np_use) {
		NewspaperDTO newNewspaper = new NewspaperDTO();
		newNewspaper.setNp_no(np_no);
		newNewspaper.setNp_name(np_name);
		newNewspaper.setNp_use(np_use);

		newspaperService.updateNewspaper(newNewspaper);
		return "redirect:/admin/news";
	}

	// 등록
	@PostMapping("/newspapers/register")
	public String registerNewspaper(@RequestParam("np_no") int np_no, @RequestParam("np_name") String np_name,
			@RequestParam("np_use") int np_use) {
		NewspaperDTO newNewspaper = new NewspaperDTO();
		newNewspaper.setNp_no(np_no); //
		newNewspaper.setNp_name(np_name);
		newNewspaper.setNp_use(np_use);

		newspaperService.addNewspaper(newNewspaper);
		return "redirect:/admin/news";
	}

	// 삭제
	@PostMapping("/newspapers/delete")
	public String deleteNewspaper(@RequestParam("np_no") int np_no, @RequestParam("np_name") String np_name,
			@RequestParam("np_use") int np_use) {
		NewspaperDTO newNewspaper = new NewspaperDTO();
		newNewspaper.setNp_no(np_no); //
		newNewspaper.setNp_name(np_name);
		newNewspaper.setNp_use(np_use);

		newspaperService.deleteNewspaper(newNewspaper);
		return "redirect:/admin/news";
	}

	// 검색
	@PostMapping("/newspapers/search")
	public String searchNewspapers(@RequestParam(required = false) String np_name,
			@RequestParam(required = false) String np_use, @RequestParam(required = false) Integer np_no, Model model) {

		int intNpNo = (np_no != null) ? np_no.intValue() : 0; // np_no가 null이면 기본값 0 사용
		System.out.println("컨트롤러");

		// np_use를 byte로 변환 (1 또는 0)
		byte useByte = (np_use != null && np_use.equals("1")) ? (byte) 1 : (byte) 0;
		List<NewsPaperVO> newspapers = newspaperService.searchNewspapers(np_name, useByte, intNpNo);

		model.addAttribute("newspapers", newspapers);
		System.out.println("컨트롤러2");
		return "admin/news"; // admin/news.html로 이동
	}
}
