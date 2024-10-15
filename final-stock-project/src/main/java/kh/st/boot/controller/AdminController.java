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
import kh.st.boot.model.vo.AdmApprovalVO;
import kh.st.boot.model.vo.AdmDaycheckVO;
import kh.st.boot.model.vo.AdminLevelPageVO;
import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.admOrderPageVO;
import kh.st.boot.service.AdminApprovalService;
import kh.st.boot.service.AdminOrderService;
import kh.st.boot.service.AdminService;
import kh.st.boot.service.PointSltIdPageService;
import kh.st.boot.service.SltAdmLevelPageService;
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
	
	@Autowired
	private SltAdmLevelPageService sltAdmLevelPageService;
	
	@Autowired
	private AdminOrderService adminOrderService;

	@Autowired
	private PointSltIdPageService pointSltIdPageService;
	
	@Autowired
	private AdminApprovalService adminApprovalService;
	
	
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



	// -------------------------------------------------------------------------------
	// -------------------------- 뉴스 관리 컨트롤러 -------------------------------
	// -------------------------------------------------------------------------------
	// newspaper 뉴스
	@GetMapping("/admNews/news")
	public String newsPage(Model model) {
		List<NewsPaperVO> newspapers = newspaperService.getAllNewspapers();
		model.addAttribute("newspapers", newspapers);
		System.out.println("안녕");
		return "/admin/admNews/news"; // admin/news.html로 이동
	}

	// 뉴스수정
	@PostMapping("/admNews/newspapers/edit")
	public String updateNewspaper(@RequestParam(required = false) String np_name,
			@RequestParam(required = false) byte np_use, Model model) {

		boolean res = newspaperService.updateNewspaper(np_name, np_use);
		if (res == false) {
			model.addAttribute("msg", "기존 신문사 이름과 동일합니다.");
			model.addAttribute("url", "/admin/admNews/news");
			return "util/msg";
		}

		return "redirect:/admin/admNews/news";
	}

	// 뉴스등록
	@PostMapping("/admNews/newspapers/register")
	public String registerNewspaper(@RequestParam(required = false) String np_name,
			@RequestParam(required = false) String np_use, Model model) {

		// np_use를 byte로 변환 (1 또는 0)
		byte useByte = (np_use != null && np_use.equals("1")) ? (byte) 1 : (byte) 0;

		boolean res = newspaperService.addNewspaper(np_name, useByte);

		if (res == false) {
			model.addAttribute("msg", "이미 존재하는 신문사 입니다.");
			model.addAttribute("url", "/admin/admNews/news");
			return "util/msg";
		}

		return "redirect:/admin/admNews/news";
	}

	// 뉴스삭제
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

	// 뉴스검색
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

	// -------------------------------------------------------------------------------
	// -------------------------- LV 관리 컨트롤러 -------------------------------
	// -------------------------------------------------------------------------------

	
	
	
	
	
	
		// 접속시 불러오기 
	@GetMapping("/admLevel/admLevelPage")
	public String sltAdmLevelPage(Model model) {
		List<AdminLevelPageVO> ssltAdminLevelPage = sltAdmLevelPageService.getAllssltAdminLevelPage();
		model.addAttribute("list", ssltAdminLevelPage);
		return "/admin/admLevel/admLevelPage"; 
	}
	
	
		// 등록하기     
		@PostMapping("/admLevel/admLevelPage/insert")
		public String istAdmLv(@RequestParam String lv_name,
								@RequestParam int lv_num,
								@RequestParam String lv_alpha,
								@RequestParam String lv_auto_use,
								@RequestParam int lv_up_limit, Model model) {
			boolean res = sltAdmLevelPageService.addSltAdmLevel(lv_name, lv_num,lv_alpha,lv_auto_use,lv_up_limit);
			if (res == false) {
				model.addAttribute("msg"," 이미 존제하는 Lv 정보 입니다 ");
				model.addAttribute("url", "/admin/admLevel/admLevelPage");
				return "util/msg";
			}
			return "redirect:/admin/admLevel/admLevelPage";
		}
	
	
	
		// 삭제하기 
		@PostMapping("/admLevel/admLevelPage/delet")
		public String dltAdmLv(
				@RequestParam String lv_name,
				@RequestParam int lv_num,
				@RequestParam String lv_alpha,
				@RequestParam String lv_auto_use,
				@RequestParam int lv_up_limit) {
			AdminLevelPageVO dltAdm = new AdminLevelPageVO();
			dltAdm.setLv_name(lv_name); //
			dltAdm.setLv_num(lv_num);
			dltAdm.setLv_alpha(lv_alpha);
			dltAdm.setLv_auto_use(lv_auto_use);
			dltAdm.setLv_up_limit(lv_up_limit);

			sltAdmLevelPageService.dltAdmLvService(dltAdm);
			return "redirect:/admin/admLevel/admLevelPage";
		}
	
		// 수정하기
		@PostMapping("/admLevel/admLevelPage/update")
		public String udtAdmLv(
				@RequestParam String lv_name,
				@RequestParam int lv_num,
				@RequestParam String lv_alpha,
				@RequestParam String lv_auto_use,
				@RequestParam int lv_up_limit) {
			

			sltAdmLevelPageService.udtAdmLvService(lv_name,lv_num,lv_alpha,lv_auto_use,lv_up_limit);
			return "redirect:/admin/admLevel/admLevelPage";
		}
	
	

		// -------------------------------------------------------------------------------
		// -------------------------- 포인트 적립내역 검색 컨트롤러 -----------------------------------
		// -------------------------------------------------------------------------------
	
		
		// 접속시 불러오기 
		@GetMapping("/admDaycheck/daycheckAdm")
		public String sltAdmPointPage(Model model) {
			List<AdmDaycheckVO> sltPoint= pointSltIdPageService.sltAllPoint();
			model.addAttribute("list", sltPoint);
			return "/admin/admDaycheck/daycheckAdm"; 
		}
		
		
		
		 // 검색하기
		  
		  @PostMapping("/admDaycheck/daycheckAdm/update")
		  public String sltIdPointPage(@RequestParam String mb_id,Model model) {
			  List<AdmDaycheckVO> sltPointOne = pointSltIdPageService.sltOnePoint(mb_id); 
	  		  model.addAttribute("list",sltPointOne);
	  		  return "/admin/admDaycheck/daycheckAdm"; 
	  		  
		  }
		 
	
	
		
		// -------------------------------------------------------------------------------
		// -------------------------- 주문내역 조회 컨트롤러 ----------------------------------
		// -------------------------------------------------------------------------------
			
		  
		  
		// 접속시 불러오기 
		@GetMapping("/admOrder/orderAdm")
		public String sltOrder(Model model) {
			List<admOrderPageVO> sltAdminOrder = adminOrderService.getAllsltAdminOrder();
			model.addAttribute("list", sltAdminOrder);
			return "/admin/admOrder/orderAdm"; 
		}
	
		
		
	// 이름 + 아이디로 검색
		
		@PostMapping("/admOrder/orderAdm/search")
		public String searchIdName(@RequestParam String od_name,@RequestParam String mb_id , Model model) {
			List<admOrderPageVO> searchOrder = adminOrderService.searchNameId(od_name, mb_id);
			model.addAttribute("list", searchOrder);
			return "/admin/admOrder/orderAdm";
			
		}
		
		
	
		// 주문번호로 검색
		
		@PostMapping("/admOrder/orderAdm/ssearch")
		public String searchNum(@RequestParam String od_id, Model model) {
			List<admOrderPageVO> ssearchOrder = adminOrderService.ssearchNum(od_id);
			model.addAttribute("list", ssearchOrder);
			return "/admin/admOrder/orderAdm";
			
		}
	
		
		// 주문번호로 삭제 
		
		@PostMapping("/admOrder/orderAdm/delet")
		public String orderDelet(@RequestParam String od_id, Model model) {
			List<admOrderPageVO> deletOrder = adminOrderService.deletOrderNum(od_id);
			return "redirect:/admin/admOrder/orderAdm";
			
		}

		
		
		// -------------------------------------------------------------------------------
		// --------------------------주식/뉴스 회원승인 ----------------------------------
		// -------------------------------------------------------------------------------
				
		
		// 접속시 신청승인여부 null 인지 확인해서 처리해야 하는것만 불러오기 
		
		@GetMapping("/admApproval/admApprovalPage")
		public String nullSltApproval( Model model) {
			List<AdmApprovalVO> nullSlt = adminApprovalService.nullSelect();
			model.addAttribute("list", nullSlt);
			return "/admin/admApproval/admApprovalPage"; 
		}
		
		
		// 승인/거절 했을때 (전부다 인설트  1DB 나우날짜 Y/N 
	
		@PostMapping("/admApproval/admApprovalPage/slt")
		public String ySltApproval(@RequestParam int mp_no, @RequestParam String mp_type,
								   @RequestParam String mp_company, @RequestParam String mp_yn) {
			
 			adminApprovalService.ynUPDATE(mp_no,mp_type,mp_company,mp_yn);
			
			return "redirect:/admin/admApproval/admApprovalPage"; 
			}
		
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
}
