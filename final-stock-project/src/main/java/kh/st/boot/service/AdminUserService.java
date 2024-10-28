package kh.st.boot.service;

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
}

