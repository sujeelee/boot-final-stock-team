package kh.st.boot.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminUserDAO;
import kh.st.boot.model.vo.AdmMemberVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;

@Service
public class AdminUserService {

	@Autowired
	private AdminUserDAO adminuserDAO;
	
	public List<AdmMemberVO> getAdminMem(Criteria cri) {
		return adminuserDAO.selectAdmUser(cri);
	}

	public PageMaker getPageMaker(Criteria cri) {
		int count = adminuserDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}

	public AdmMemberVO getAdmUseSel(int mb_no) {
		return adminuserDAO.UseSelect(mb_no);
	}


	public boolean getAdmUserUpd(AdmMemberVO admMemberVO) {
		adminuserDAO.UseUpdate(admMemberVO);
		return true;
	}

//	public AdmMemberVO getAdmUseDel(int mb_no) {
//		return adminuserDAO.UserDelete(mb_no);
//	}

	public boolean getAdmUseDel(int mb_no) {
	    int result = adminuserDAO.UserDelete(mb_no);
	    return result > 0;  // 1 이상의 값을 반환하면 삭제 성공
	}

	public void insertUser(int mb_no, String mb_id, String mb_password, String mb_name, String mb_nick, String mb_hp,
			String mb_email, int mb_zip, String mb_addr, String mb_addr2, Date mb_birth, int mb_level,
			String mb_datetime, String mb_edit_date, String mb_stop_date, String mb_out_date, String mb_cookie,
			String mb_cookie_limit, int mb_point, int mb_emailing, String mb_account) {
			adminuserDAO.insertUser( mb_no, mb_id, mb_password, mb_name, mb_nick, mb_hp, mb_email, mb_zip,
			        mb_addr, mb_addr2, mb_birth, mb_level, mb_datetime, mb_edit_date, mb_stop_date,
			        mb_out_date, mb_cookie, mb_cookie_limit, mb_point, mb_emailing, mb_account);
		
		
		
		
		
		
		
	}

	public List<AdmMemberVO> searchUser(String searchType, String searchText) {
		return adminuserDAO.userSearch(searchType,searchText);
	}
}

