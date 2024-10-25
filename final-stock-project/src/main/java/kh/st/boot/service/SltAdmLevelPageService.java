package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.SltAdmLevelPageDAO;
import kh.st.boot.model.vo.AdminLevelPageVO;

@Service
public class SltAdmLevelPageService {

	@Autowired
	private SltAdmLevelPageDAO sltAdmLevelPageDAO;

	public List<AdminLevelPageVO> getAllssltAdminLevelPage() {
		return sltAdmLevelPageDAO.getAllssltAdminLevelPage();
	}

	public boolean addSltAdmLevel(String lv_name, int lv_num, String lv_alpha, String lv_auto_use, int lv_up_limit) {

		AdminLevelPageVO oldLv = sltAdmLevelPageDAO.getLvOne(lv_num);
		if (oldLv != null) {
			return false;
		}
		return sltAdmLevelPageDAO.insertAdmLv(lv_name, lv_num, lv_alpha, lv_auto_use, lv_up_limit);
	}

	public void dltAdmLvService(AdminLevelPageVO AdminLevelPageVO) {
		sltAdmLevelPageDAO.dltAdmLvdao(AdminLevelPageVO);
	}

	public void udtAdmLvService(String lv_name, int lv_num, String lv_alpha, String lv_auto_use, int lv_up_limit) {

		// 전체lv 데이터 가져와서 향상된 for문 돌릴꺼임
		List<AdminLevelPageVO> allNums = sltAdmLevelPageDAO.getAllssltAdminLevelPage();

		for (AdminLevelPageVO level : allNums) {
			if (level.getLv_num() == lv_num) { // 검색한 값이 lv_num 이면
				level.setLv_name(lv_name);
				level.setLv_alpha(lv_alpha);
				level.setLv_auto_use(lv_auto_use);
				level.setLv_up_limit(lv_up_limit);
				sltAdmLevelPageDAO.updateAdmLv(level);
			}
		}

	}
}
