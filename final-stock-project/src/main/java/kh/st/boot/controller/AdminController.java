package kh.st.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import groovy.transform.AutoImplement;
import kh.st.boot.model.dto.NewspaperDTO;
import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.service.AdminService;
import kh.st.boot.service.newspaperServiceImp;

@Controller
@AutoImplement
@RequestMapping("/admin") // 기본 경로 설정
public class AdminController {
	
	// 의존성 추가?
	@Autowired
	private AdminService adminService;
	@Autowired
	private newspaperServiceImp newspaperService;
	
	// adminhome에 값을 보내줄 내용
	@GetMapping("/adminHome")
	public String admin(Model model) {
		// db에 저장된 값 DAO와 Service를 통해 받아온 값 리스트에 저장
		List<AdminVO> adminList = adminService.getAdminList();
		
        // adminList가 null인지 확인
        if (adminList == null || adminList.isEmpty()) {
            System.out.println("adminList가 비어 있습니다.");
        } else {
            System.out.println("adminList 크기: " + adminList.size());
        }
        System.out.println(adminList);
        // 리스트 값을 보내주기
		model.addAttribute("adminList", adminList);
		return "/admin/adminHome";
	}
	
//	@PostMapping("/adminHome/update")
//	public String adminUpdate (String cf_title, String cf_info, String cf_privacy, String cf_noemail, String cf_tel,
//			int cf_zip, String cf_addr, String cf_addr2, String cf_fax, String cf_email, String cf_owner_name,
//			String cf_day_point, String cf_check_use, String cf_od_point, String cf_percent, Model model) {
//		List<AdminVO> adminUpdateList = adminService.getAdminUpdateList();
//		System.out.println(adminUpdateList);
//		List<AdminVO> adminList = adminService.getAdminList();
//		
//		model.addAttribute("adminList", adminList);
//		
//		return "redirect:/admin/adminHome";
//	}
	
	
	
	
	
	// -------------------------------------------------------------------------------
	// newspaper 
	@GetMapping("/admNews/news")
	public String newsPage(Model model) {
		List<NewsPaperVO> newspapers = newspaperService.getAllNewspapers();
		model.addAttribute("newspapers", newspapers);
		System.out.println("안녕");
		return "/admin/admNews/news"; // admin/news.html로 이동
	}

	// 수정
	@PostMapping("/admNews/newspapers/edit")
	public String updateNewspaper(@RequestParam("np_no") int np_no, @RequestParam("np_name") String np_name,
			@RequestParam("np_use") int np_use) {
		NewspaperDTO newNewspaper = new NewspaperDTO();
		newNewspaper.setNp_no(np_no);
		newNewspaper.setNp_name(np_name);
		newNewspaper.setNp_use(np_use);

		newspaperService.updateNewspaper(newNewspaper);
		return "redirect:/admin/admNews/news";
	}

	// 등록
	@PostMapping("/admNews/newspapers/register")
	public String registerNewspaper(@RequestParam(required = false) String np_name,
			@RequestParam(required = false) String np_use, Model model) {
		
		// np_use를 byte로 변환 (1 또는 0)
		byte useByte = (np_use != null && np_use.equals("1")) ? (byte) 1 : (byte) 0;
		
		boolean res = newspaperService.addNewspaper(np_name, useByte);
		
		if(res == false) {
			model.addAttribute("msg", "이미 존재하는 신문사 입니다.");
			model.addAttribute("url", "/admin/admNews/news");
			return "util/msg";
		}
		
		return "redirect:/admin/admNews/news";
	}


	
	
	
	// 삭제
	@PostMapping("/admNews/newspapers/delete")
	public String deleteNewspaper(@RequestParam("np_no") int np_no, @RequestParam("np_name") String np_name,
			@RequestParam("np_use") byte np_use) {
		NewsPaperVO newNewspaper = new NewsPaperVO();
		newNewspaper.setNp_no(np_no); //
		newNewspaper.setNp_name(np_name);
		newNewspaper.setNp_use(np_use);
	
		newspaperService.deleteNewspaper(newNewspaper);
		return "redirect:/admin/admNews/news";
	}

	// 검색
	@PostMapping("/admNews/newspapers/search")
	public String searchNewspapers(@RequestParam(required = false) String np_name,
			@RequestParam(required = false) String np_use, @RequestParam(required = false) Integer np_no, Model model) {

		int intNpNo = (np_no != null) ? np_no.intValue() : 0; // np_no가 null이면 기본값 0 사용
		System.out.println("컨트롤러");

		// np_use를 byte로 변환 (1 또는 0)
		byte useByte = (np_use != null && np_use.equals("1")) ? (byte) 1 : (byte) 0;
		List<NewsPaperVO> newspapers = newspaperService.searchNewspapers(np_name, useByte, intNpNo);

		model.addAttribute("newspapers", newspapers);
		System.out.println("컨트롤러2");
		return "/admin/admNews/news"; // admin/news.html로 이동
	}
	
	
}
	


