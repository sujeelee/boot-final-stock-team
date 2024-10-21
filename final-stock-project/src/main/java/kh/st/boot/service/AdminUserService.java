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

	
	
}
