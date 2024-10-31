package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdmUserDAO;
import kh.st.boot.model.vo.AdmMemberVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminUserService {
	
	
	private AdmUserDAO admuserDao;
	
	
	public List<AdmMemberVO> getUserSearch() {
		return admuserDao.AdmUserSearch();
	}


	public void updateUser(String mb_id, String mb_name, String mb_nick, String mb_hp, String mb_stop_date) {
		admuserDao.AdmUserUpdate(mb_id,mb_name,mb_nick,mb_hp,mb_stop_date);
		
	}


	public void deletUser(String mb_id, String mb_name, String mb_nick, String mb_hp, String mb_datetime) {
		admuserDao.AdmUserDelet(mb_id,mb_name,mb_nick,mb_hp,mb_datetime);
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
		return result > 0; // 1 이상의 값을 반환하면 삭제 성공
	}

    // 페이지네이션이 적용된 검색 결과 가져오기
    public List<AdmMemberVO> getSearchUser(String use_sh, UserCriteria cri) {
        return adminuserDAO.selectUser(use_sh, cri);
    }

    // 검색 결과에 따른 PageMaker 생성
    public PageMaker getPageMakerSearch(UserCriteria cri, String use_sh) {
        int totalCount = adminuserDAO.selectUserCount(use_sh, cri); // 검색된 전체 결과 수 반환
        return new PageMaker(10, cri, totalCount); // PageMaker 생성 (displayPageNum을 10으로 설정)
    }

	public boolean getAdmUserIns(AdmMemberVO admMemberVO) {
		adminuserDAO.UserInsert(admMemberVO);
		return true;
	}

	
}
