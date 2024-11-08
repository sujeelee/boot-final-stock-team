package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminUserDAO;
import kh.st.boot.dao.MemberDAO;
import kh.st.boot.model.util.CustomUtil;
import kh.st.boot.model.vo.AdmMemberVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.pagination.UserCriteria;

@Service
public class AdminUserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminUserDAO adminuserDao;
	
	@Autowired
	private MemberDAO memberDao;

	public List<AdmMemberVO> getAdminMem(Criteria cri) {
		return adminuserDao.selectAdmUser(cri);
	}

	public PageMaker getPageMaker(Criteria cri) {
		int count = adminuserDao.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}

	public AdmMemberVO getAdmUseSel(int mb_no) {
		return adminuserDao.UseSelect(mb_no);
	}

	public boolean getAdmUserUpd(AdmMemberVO admMemberVO) {
		AdmMemberVO existingMember = getAdmUseSel(admMemberVO.getMb_no());
	    int emailing = admMemberVO.getMb_emailing_test() != null ? 1 : 0;
	    String encodePw;
	    // 입력된 비밀번호와 기존 비밀번호를 비교
	    if (!admMemberVO.getMb_password().equals(existingMember.getMb_password())) {
	        // 기존 비밀번호와 다르면 인코딩 진행
	        encodePw = passwordEncoder.encode(admMemberVO.getMb_password());
	    } else {
	        // 기존 비밀번호와 같으면 그대로 사용
	        encodePw = admMemberVO.getMb_password();
	    }
		/*
		int emailing;
		if(admMemberVO.getMb_emailing_test() != null) {
			emailing = 1;
		}else {
			emailing = 0;
		}
		String encodePw = passwordEncoder.encode(admMemberVO.getMb_password());
		*/
		admMemberVO.setMb_emailing(emailing);
		admMemberVO.setMb_password(encodePw);
		adminuserDao.UseUpdate(admMemberVO);
		return true;
	}


	public boolean getAdmUseDel(int mb_no) {
		int result = adminuserDao.UserDelete(mb_no);
		return result > 0; // 1 이상의 값을 반환하면 삭제 성공
	}

    // 페이지네이션이 적용된 검색 결과 가져오기
    public List<AdmMemberVO> getSearchUser(String use_sh, UserCriteria cri) {
    	
    	
        return adminuserDao.selectUser(use_sh, cri);
    }

    // 검색 결과에 따른 PageMaker 생성
    public PageMaker getPageMakerSearch(UserCriteria cri, String use_sh) {
        int totalCount = adminuserDao.selectUserCount(use_sh, cri); // 검색된 전체 결과 수 반환
        return new PageMaker(10, cri, totalCount); // PageMaker 생성 (displayPageNum을 10으로 설정)
    }

    // 회원정보 등록
	public boolean getAdmUserIns(AdmMemberVO admMemberVO) {
		String encodePw = passwordEncoder.encode(admMemberVO.getMb_password());
		int emailing;
		if(admMemberVO.getMb_emailing_test() != null) {
			emailing = 1;
		}else {
			emailing = 0;
		}
		admMemberVO.setMb_emailing(emailing);
		admMemberVO.setMb_password(encodePw);
		boolean res = adminuserDao.UserInsert(admMemberVO);
		if(!res) {
        	return false;
        } else {
        	checkAccountNum(admMemberVO.getMb_id());
        	memberDao.insertAccount(admMemberVO.getMb_id());
        	return true;
        }
	}
	
	private void checkAccountNum(String mb_id) {
    	int result = 0;
    	do {
    		String mb_account = createAccountNum();
    		if(!memberDao.updateUserAccount(mb_account, mb_id)) {
    			result++;
    		}
    	}while(result != 0);
    } // 회원가입이 된 유저에 계좌 번호를 추가하는 메소드입니다.

	private String createAccountNum() {
		CustomUtil cu = new CustomUtil();
		
		int firstNum = cu.getCustomNumber(2);
		int secondNum = cu.getCustomNumber(6);
		
		String first = String.valueOf(firstNum);
		String second = String.valueOf(secondNum);
		
		if(first.length() < 2) {
			while(first.length() < 2) {
				first = "0" + first;
			}
		}
		
		if(second.length() < 6) {
			while(second.length() < 6) {
				second = "0" + second;
			}
		}
		return "SID-" + first + "-" + second;
	} // 계좌번호 생성 메소드입니다.
	
	public int getAdmMemberCheck(String mb_id) {
		return adminuserDao.MemberCheck(mb_id);
	}

	public boolean deleteUserAccount(int mb_no) {
		return adminuserDao.deleteUserAccount(mb_no);
	}
	
}

