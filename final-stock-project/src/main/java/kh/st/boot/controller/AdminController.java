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
import kh.st.boot.model.vo.AdmMemberVO;
import kh.st.boot.model.vo.AdmPointVO;
import kh.st.boot.model.vo.AdminLevelPageVO;
import kh.st.boot.model.vo.AdminStock_addVO;
import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.admOrderPageVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.OrderCriteria;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.pagination.UserCriteria;
import kh.st.boot.service.AdmPointService;
import kh.st.boot.service.AdminApprovalService;
import kh.st.boot.service.AdminOrderService;
import kh.st.boot.service.AdminService;
import kh.st.boot.service.AdminStock_addService;
import kh.st.boot.service.AdminUserService;
import kh.st.boot.service.PointSltIdPageService;
import kh.st.boot.service.SltAdmLevelPageService;
import kh.st.boot.service.newspaperService;

@Controller
@AutoImplement
@RequestMapping("/admin") // 기본 경로 설정
public class AdminController {

	// 의존성 추가?
	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminUserService admUserService;

	@Autowired
	private newspaperService newspaperService;

	@Autowired
	private SltAdmLevelPageService sltAdmLevelPageService;

	@Autowired
	private AdminOrderService adminOrderService;

	@Autowired
	private PointSltIdPageService pointSltIdPageService;

	@Autowired
	private AdminApprovalService adminApprovalService;

	@Autowired
	private AdmPointService admPointService;

	@Autowired
	private AdminStock_addService adminStock_addService;
	
	// 관리자 기본 페이지

	// 관리자 설정 페이지 값 전송 코드
	@GetMapping("/adminHome")	
	public String admin(Model model) {
		// db에 저장된 값 DAO와 Service를 통해 받아온 값 리스트에 저장
		AdminVO adminH = adminService.getAdminH();
		System.out.println("admin홈페이지 DB값 받아오기");
		System.out.println("admin홈페이지 DB값 받아오기");
		// 값을 보내주기
		model.addAttribute("adminH", adminH);
		return "/admin/adminHome";
	}

	// 관리자 설정 페이지 값 변경
	@PostMapping("/adminHome/update")
	public String admUpdate(AdminVO adminVO, Model model) {
		System.out.println("페이지 값 받아오기" + adminVO);
		boolean res = adminService.admUpdate(adminVO);
		if (res == false) {
			model.addAttribute("msg", "실패");
			model.addAttribute("url", "/admin/adminHome");
			System.out.println("dadd");
			return "util/msg";
		}

		return "redirect:/admin/adminHome";
	}

	// -------------------------------------------------------------------------------
	// -------------------------- 회원관리 회원정보 정보 수정 -------------------------------
	// -------------------------------------------------------------------------------
	
	@GetMapping("/admMember/adminUser")
	public String admUser(Model model, Criteria cri) {
		cri.setPerPageNum(12);
		List<AdmMemberVO> user = admUserService.getAdminMem(cri);
		PageMaker pm_use = admUserService.getPageMaker(cri);
		model.addAttribute("user", user);
		model.addAttribute("pm_use", pm_use);

		return "/admin/admMember/adminUser";
	}

	// 회원 정보 상세페이지 조회
	@PostMapping("/admMember/admUserSelect")
	public String admUserSelUpd(Model model, int mb_no) {
		AdmMemberVO admUseSel = admUserService.getAdmUseSel(mb_no);

		model.addAttribute("admUseSel", admUseSel);

		return "/admin/admMember/admUserSelect";
	}

	// 회원 정보 상세페이지 업데이트
	@PostMapping("/admMember/admUserSelect/Update")
	public String admUserUpdate(Model model, AdmMemberVO admMemberVO) {
		boolean res = admUserService.getAdmUserUpd(admMemberVO);
		System.out.println(res);
		if (res == false) {
			model.addAttribute("msg", "실패");
			model.addAttribute("url", "/admin/adminHome");
			return "util/msg";
		}
		System.out.println("수정값 보내기");
		return "redirect:/admin/admMember/adminUser";
	}

	// 회원 정보 삭제
	// 이건 잘 모르겠음
	@PostMapping("/admMember/adminUser/delete")
	public String admUserDelete(int mb_no) {
		boolean admimUserDel = admUserService.getAdmUseDel(mb_no);

		return "redirect:/admin/admMember/adminUser";
	}
	
	@PostMapping("/admMember/admUserInsert")
	public String admUserInsert() {
		
		
		return "/admin/admMember/admUserInsert";
	}

	@GetMapping("/admMember/adminUser/userSearch")
	public String admuseSearch(@RequestParam("use_sh") String use_sh, @RequestParam("search") String search,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model, UserCriteria cri) { // UserCriteria

		// 한 페이지당 게시물 숫자
		cri.setPerPageNum(12);
		// cri로 넘겨줄 페이지
		cri.setPage(page);
		// 검색 매퍼에서 사용 
		cri.setSearch(search);

		List<AdmMemberVO> searchUser = admUserService.getSearchUser(use_sh, cri);

		PageMaker pm_use = admUserService.getPageMakerSearch(cri, use_sh);

		model.addAttribute("user", searchUser);
		model.addAttribute("pm_use", pm_use);
		model.addAttribute("use_sh", use_sh);
		model.addAttribute("search", search);

		return "/admin/admMember/adminUser";
	}

	// -------------------------------------------------------------------------------
	// -------------------------- 뉴스 관리 컨트롤러 -------------------------------
	// -------------------------------------------------------------------------------
	// newspaper 뉴스
	@GetMapping("/admNews/news")
	public String newsPage(Model model, Criteria cri) {
		cri.setPerPageNum(7);
		List<NewsPaperVO> newspapers = newspaperService.getAllNewspapers(cri);
		PageMaker pm_news = newspaperService.getPageMaker(cri);
		model.addAttribute("newspapers", newspapers);
		model.addAttribute("pm_news", pm_news);
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

	@GetMapping("/admNews/newspapers/search")
	public String searchNewspapers(Model model,@RequestParam("np_name") String np_name, Criteria cri,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		
		cri.setPerPageNum(7);
		cri.setPage(page);
		
		List<AdmMemberVO> newspapers = newspaperService.getSearchNews(np_name, cri);
		PageMaker pm_news = newspaperService.getPageMakerSearch(cri, np_name);

		model.addAttribute("newspapers", newspapers);
		model.addAttribute("pm_news", pm_news);

		return "/admin/admNews/news";

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
	public String istAdmLv(@RequestParam String lv_name, @RequestParam int lv_num, @RequestParam String lv_alpha,
			@RequestParam char lv_auto_use, @RequestParam int lv_up_limit, Model model) {
		boolean res = sltAdmLevelPageService.addSltAdmLevel(lv_name, lv_num, lv_alpha, lv_auto_use, lv_up_limit);
		if (res == false) {
			model.addAttribute("msg", " 이미 존제하는 Lv 정보 입니다 ");
			model.addAttribute("url", "/admin/admLevel/admLevelPage");
			return "util/msg";
		}
		return "redirect:/admin/admLevel/admLevelPage";
	}

	// 삭제하기
	@PostMapping("/admLevel/admLevelPage/delet")
	public String dltAdmLv(@RequestParam String lv_name, @RequestParam int lv_num, @RequestParam String lv_alpha,
			@RequestParam char lv_auto_use, @RequestParam int lv_up_limit) {
		AdminLevelPageVO dltAdm = new AdminLevelPageVO();
		dltAdm.setLv_name(lv_name); //
		dltAdm.setLv_num(lv_num);
		dltAdm.setLv_alpha(lv_alpha);
		dltAdm.setLv_auto_use(lv_auto_use);
		dltAdm.setLv_up_limit(lv_up_limit);

		sltAdmLevelPageService.dltAdmLvService(dltAdm);
		return "redirect:/admin/admLevel/admLevelPage";
	}
	
	// 회원 정보 상세페이지 조회
		@PostMapping("/admLevel/admLevSel")
		public String admlevSel(Model model, int lv_num) {
			AdminLevelPageVO admlevSel = sltAdmLevelPageService.getAdmlevSel(lv_num);
			
			model.addAttribute("admlevSel", admlevSel);
			
			return "/admin//admLevel/admLevSel";
		}
		
	

	// 수정하기
	@PostMapping("/admLevel/admLevSel/update")
	public String udtAdmLv(AdminLevelPageVO admLevVO, Model model) {
		boolean res = sltAdmLevelPageService.admLevUpdate(admLevVO);
		System.out.println(admLevVO);
		if (res == false) {
			model.addAttribute("msg", "실패");
			model.addAttribute("url", "/admin/adminHome");
			System.out.println("dadd");
			return "util/msg";
		}
		return "redirect:/admin/admLevel/admLevelPage";
	}
	

	// -------------------------------------------------------------------------------
	// ------------------- 출석체크  포인트 적립내역 검색 컨트롤러 -------------------
	// -------------------------------------------------------------------------------

	// 검색하기랑 
	// 불러오기  페이지네이션 , 날짜 합쳐서 출력하기 
	
	
	// 접속시 불러오기
	@GetMapping("/admDaycheck/daycheckAdm")
	public String sltAdmPointPage(Model model, Criteria cri) {
		cri.setPerPageNum(12);
		List<AdmDaycheckVO> sltPoint = pointSltIdPageService.sltAllDay(cri);
		PageMaker pm_day = pointSltIdPageService.getPageMaker(cri);
		model.addAttribute("list", sltPoint);
		model.addAttribute("pm_day", pm_day);
		return "/admin/admDaycheck/daycheckAdm";
	}

	@PostMapping("/admDaycheck/daycheckAdm/update")
	public String sltIdPointPage(@RequestParam String mb_id, Model model) {
		List<AdmDaycheckVO> sltPointOne = pointSltIdPageService.sltOneDay(mb_id);
		
		model.addAttribute("list", sltPointOne);
		return "/admin/admDaycheck/daycheckAdm";
	}

	// -------------------------------------------------------------------------------
	// -------------------------- 주문내역 조회 컨트롤러 ------------------------------------
	// -------------------------------------------------------------------------------

	// 접속시 불러오기
	@GetMapping("/admOrder/orderAdm")
	public String sltOrder(Model model, Criteria cri) {
		cri.setPerPageNum(15);
		List<admOrderPageVO> sltAdminOrder = adminOrderService.getAllsltAdminOrder(cri);
		PageMaker pm_ord = adminOrderService.getPageMaker(cri);
		model.addAttribute("pm_ord", pm_ord);
		model.addAttribute("list", sltAdminOrder);
		model.addAttribute("pm_ord", pm_ord);
		return "/admin/admOrder/orderAdm";
	}

	// 주문내역 검색 

	@PostMapping("/admOrder/orderAdm/search")
	public String searchIdName(@RequestParam String od_name, @RequestParam String mb_id, @RequestParam String od_id,
			Model model) {

		List<admOrderPageVO> searchOrder = adminOrderService.searchNameId(od_name, mb_id, od_id);
		model.addAttribute("list", searchOrder);
		return "/admin/admOrder/orderAdm";

	}
	// 주문번호로 삭제

	@PostMapping("/admOrder/orderAdm/delet")
	public String orderDelet(@RequestParam String od_id, Model model) {
		List<admOrderPageVO> deletOrder = adminOrderService.deletOrderNum(od_id);
		return "redirect:/admin/admOrder/orderAdm";

	}

	@GetMapping("/admOrder/orderAdm/AdmOrderSearch")
	public String rderSearch(@RequestParam("od_sh") String od_sh, @RequestParam("od_search") String od_search,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model, OrderCriteria cri) {
//		페이지에 보여주는 리스트 수
		cri.setPerPageNum(8);
//		현재 페이지 넘기기		
		cri.setPage(page);
//		검색어 넘기기
		cri.setOd_search(od_search);
		List<admOrderPageVO> orderSearch = adminOrderService.getOrderSearch(od_sh, cri);
		PageMaker pm_ord = adminOrderService.getPageMakerSearch(cri, od_sh);

		pm_ord.setCri(cri);
		model.addAttribute("list", orderSearch);
		model.addAttribute("pm_ord", pm_ord);
		model.addAttribute("od_sh", od_sh);
		model.addAttribute("od_search", od_search);

		return "/admin/admOrder/orderAdm";
	}

	// -------------------------------------------------------------------------------
	// --------------------------주식/뉴스 회원승인 ----------------------------------
	// -------------------------------------------------------------------------------

	// 접속시 신청승인여부 null 인지 확인해서 처리해야 하는것만 불러오기

	@GetMapping("/admApproval/admApprovalPage")
	public String nullSltApproval(Model model, Criteria cri) {
		List<AdmApprovalVO> nullSlt = adminApprovalService.nullSelect(cri);
		PageMaker pm_Approval = adminApprovalService.getPageMaker(cri);
		model.addAttribute("pm_Approval", pm_Approval);
		model.addAttribute("list", nullSlt);
		return "/admin/admApproval/admApprovalPage";
	}

	// 승인/거절 했을때

	@PostMapping("/admApproval/admApprovalPage/slt")
	public String ySltApproval(@RequestParam int mp_no, @RequestParam String mp_type, @RequestParam String mp_company,
			@RequestParam String mp_yn, @RequestParam int mb_no) {

		adminApprovalService.ynUPDATE(mp_no, mp_type, mp_company, mp_yn, mb_no);

		return "redirect:/admin/admApproval/admApprovalPage";
	}

	// -------------------------------------------------------------------------------
	// --------------------------사용자포인트 관리 컨트롤러 --------------------------
	// -------------------------------------------------------------------------------

	// 페이지 이동시 리스트 당겨옴
	@GetMapping("/admPoint/admPointPage")
	public String pointSelect(Model model, Criteria cri) {
		cri.setPerPageNum(7);
		List<AdmPointVO> Slt = admPointService.allselect(cri);
		PageMaker pm_point = admPointService.getPageMaker(cri);
		model.addAttribute("list", Slt);
		model.addAttribute("pm_point", pm_point);
		return "/admin/admPoint/admPointPage";
	}

	// 아이디로 사용자 검색
	@PostMapping("admPoint/admPointPage/Id")
	public String idSelect(@RequestParam String mb_id, Model model,Criteria cri) {
		List<AdmPointVO> idSlt = admPointService.idSelect(mb_id);
		PageMaker pm_point = admPointService.getPageMaker(cri);
		model.addAttribute("list", idSlt);
		model.addAttribute("pm_point", pm_point);
		return "/admin/admPoint/admPointPage";
	}

	// 포인트 적립, 차감
	@PostMapping("/admPoint/admPointPage/point")
	public String plusMinus(@RequestParam String mb_id, @RequestParam int po_num, @RequestParam String po_content) {

		admPointService.plusminus(mb_id, po_num, po_content);
		return "redirect:/admin/admPoint/admPointPage";
	}

	// 내역 삭제
	@PostMapping("admPoint/admPointPage/delete")
	public String plusMinus(@RequestParam int po_no) {

		admPointService.delete(po_no);
		return "redirect:/admin/admPoint/admPointPage";
	}

	@GetMapping("admPoint/admPointPage/pointSearch")
	public String pointSearch(Model model, @RequestParam("mb_id") String mb_id, Criteria cri,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		cri.setPerPageNum(7);
		cri.setPage(page);

		List<AdmPointVO> Slt = admPointService.getPointUserSearch(cri, mb_id);
		PageMaker pm_point = admPointService.getPageMaker(cri, mb_id);

		model.addAttribute("list", Slt);
		model.addAttribute("pm_point", pm_point);
		model.addAttribute("mb_id", mb_id);

		return "/admin/admPoint/admPointPage";

	}

	//		이거 vo 랑 html 이랑 서비스 다오 메퍼 까지 다 없음  그럼 어떡하지? 
	
	// 		
	
	//-------------------------------------------------------------------------------
	// --------------------------주식주 증/감 여부 승인  ----------------------------
	// ------------------------------------------------------------------------------

	
	// 페이지 이동시 리스트 당겨옴
	@GetMapping("/admStock/admStock_add")
	public String stock_add(Model model) {
		List<AdminStock_addVO> selecte = adminStock_addService.nullSelect();
		model.addAttribute("list", selecte); 
		return "/admin/admStock/admStock_add";
	}

	// 검색하기
	@PostMapping("/admStock/admStock_add/search")
	public String Stock_addSearch(Model model, String mb_id) {
		System.out.println(mb_id);
		List<AdminStock_addVO> search = adminStock_addService.search (mb_id);
		System.out.println(mb_id);
		model.addAttribute("list",search);
		return "/admin/admStock/admStock_add";
	}
	
	


	// 상세페이지에서  승인/거절시 update 하고 리스트 페이지로 이동
	@PostMapping("/admStock/admStock_add/choose")
	public String chooseStock_add( int sa_no, String sa_yn, String sa_feedback) {
//		sa_yn 체크시 : on // 아니면 "null" 		
		System.out.println("int sa_no : " + sa_no);
		System.out.println("String sa_yn : " + sa_yn);
		System.out.println("String sa_feedback : " + sa_feedback);
		adminStock_addService.update(sa_no,sa_yn,sa_feedback);
		
		return "redirect:/admin/admStock/admStock_add";
	}
	
	
	
	
	
	
	
	
	
	
	
}
