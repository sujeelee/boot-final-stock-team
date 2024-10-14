package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminApprovalDAO;
import kh.st.boot.model.vo.AdmApprovalVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminApprovalService {
	
	@Autowired
	private AdminApprovalDAO adminApprovalDAO;
	
	
	public List<AdmApprovalVO> nullSelect() {
		return adminApprovalDAO.nullSelectAll();
	}



}
