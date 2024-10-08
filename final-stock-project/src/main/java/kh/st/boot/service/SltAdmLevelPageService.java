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

}
// 서비스 메서드 만들기 전에 다오 먼저 만들고 오자 