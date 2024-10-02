package kh.st.boot.controller;

import kh.st.boot.model.dto.NewspaperDTO;
import kh.st.boot.service.NewspaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class adminController {

    @Autowired
    private NewspaperService newspaperService;

    @GetMapping("/news")
    public String newsPage(Model model) {
        List<NewspaperDTO> newspapers = newspaperService.getAllNewspapers();
        model.addAttribute("newspapers", newspapers);
        return "/admin/news";  // admin/news.html로 이동
    }

    @PostMapping("/newspapers/add")
    public String addNewspaper(@ModelAttribute NewspaperDTO newspaperDTO) {
        newspaperService.addNewspaper(newspaperDTO);
        return "redirect:/news"; // 신문사 목록으로 리다이렉트
    }

    @PostMapping("/newspapers/delete/{np_no}")
    public String deleteNewspaper(@PathVariable int np_no) {
        newspaperService.deleteNewspaper(np_no);
        return "redirect:/news"; // 신문사 목록으로 리다이렉트
    }

    @GetMapping("/newspapers/search")
    public String searchNewspapers(@RequestParam(required = false) String name, @RequestParam(required = false) String status, Model model) {
        List<NewspaperDTO> newspapers = newspaperService.searchNewspapers(name, status);
        model.addAttribute("newspapers", newspapers);
        return "/admin/news";  // admin/news.html로 이동
    }
}
