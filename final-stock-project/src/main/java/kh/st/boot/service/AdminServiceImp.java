package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminDAO;
import kh.st.boot.model.vo.AdminVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImp implements AdminService{
	
	private AdminDAO adminDao;
	
	// 
	public List<AdminVO> getAdminList() {
		
		return adminDao.selectAdminList();
	}

	

}

