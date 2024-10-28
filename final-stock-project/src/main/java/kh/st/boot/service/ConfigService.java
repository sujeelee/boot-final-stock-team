package kh.st.boot.service;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminDAO;
import kh.st.boot.model.vo.AdminVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfigService {
	
	private AdminDAO adminDao;
	
	public AdminVO getConfig() {
		return adminDao.selectAdmin();
	}
	
}
