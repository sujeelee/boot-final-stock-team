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


	public void ynUPDATE(int mp_no, String mp_type, String mp_company, String mp_yn) {
		// 거절한 경우 n값만 넣어주면 됨 
		if(mp_yn.equals("n")) {
			adminApprovalDAO.nyUPDATE( mp_no,mp_yn);
		}
		
	}



}
