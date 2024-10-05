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
        System.out.println("안녕");
        return "admin/news"; // admin/news.html로 이동
    }

    @PostMapping("/newspapers/add")
    public String addNewspaper(@ModelAttribute NewsPaperVO newspaper) {
        newspaperService.addNewspaper(newspaper);
        return "redirect:/admin/news"; // 신문사 목록으로 리다이렉트
    }

    @PostMapping("/newspapers/delete/{np_no}")
    public String deleteNewspaper(@PathVariable int np_no) {
        newspaperService.deleteNewspaper(np_no); // 서비스에서 삭제 메서드 호출
        return "redirect:/admin/news"; // 삭제 후 신문사 목록으로 리다이렉트
    }

    @PostMapping("/newspapers/search")
    public String searchNewspapers(
        @RequestParam(required = false) String np_name,
        @RequestParam(required = false) String np_use,
        @RequestParam(required = false) Integer  np_no, Model model) {
    	int intNpNo = (np_no != null) ? np_no.intValue() : 0; // np_no가 null이면 기본값 0 사용
        System.out.println("컨트롤러");
        byte useByte = (np_use != null && np_use.equals("1")) ? (byte) 1 : (byte) 0;
        List<NewsPaperVO> newspapers = newspaperService.searchNewspapers(np_name, useByte, intNpNo);
        model.addAttribute("newspapers", newspapers);
        System.out.println("컨트롤러2");
        return "admin/news"; // admin/news.html로 이동
    }
}
