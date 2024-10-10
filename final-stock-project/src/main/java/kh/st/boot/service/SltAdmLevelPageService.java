package kh.st.boot.service;

import java.util.ArrayList;
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
		return sltAdmLevelPageDAO.insertAdmLv(lv_name, lv_num,lv_alpha,lv_auto_use,lv_up_limit);
	}

	public void dltAdmLvService(AdminLevelPageVO AdminLevelPageVO) {
		sltAdmLevelPageDAO.dltAdmLvdao(AdminLevelPageVO );
	}

	public void udtAdmLvService(AdminLevelPageVO AdminLevelPageVO) {
		sltAdmLevelPageDAO.dltAdmLvdao(AdminLevelPageVO );
		여기서 특정값으로 데이터 조회 해보고 
		
		다시 넣는거 
	
		sltAdmLevelPageDAO.dltAdmLvdao(AdminLevelPageVO );
	}
	
	
}

/// 다오에서 파라미터로 넘긴 값이랑 디비값 비교함 그거 받아야함 