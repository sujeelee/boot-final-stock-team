package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdmUserDAO;
import kh.st.boot.model.vo.AdmMemberVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminUserService {
	
	
	private AdmUserDAO adminuserDAO;
	
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


	
	
// 다 따로 검색해서 값을 넘겨준다면? 그냥 편하게 검색할수 있지 않을까 ? 
	//아이디검색
	public List<AdmMemberVO> adminId(String search) {
		System.out.println("여기입니다 "+  search);
		return adminuserDAO.selectId(search);
	}
	//이름검색
	public List<AdmMemberVO> adminName(String search) {
		System.out.println("여기입니다 "+  search);
		return adminuserDAO.selectName(search);
	}
	//닉네임검색
	public List<AdmMemberVO> adminNick(String search) {
		System.out.println("여기입니다 "+  search);
		return adminuserDAO.selectNick(search);		
	}
	
	
	
	
	
}
