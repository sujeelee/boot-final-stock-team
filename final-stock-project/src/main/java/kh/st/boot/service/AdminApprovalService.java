package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminApprovalDAO;
import kh.st.boot.model.vo.AdmApprovalVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminApprovalService {

	@Autowired
	private AdminApprovalDAO adminApprovalDAO;

	public List<AdmApprovalVO> allSelect(Criteria cri) {
		return adminApprovalDAO.SelectAll(cri);
	}
	//mp_yn,mp_company,mp_type,mb_no
	public void ynUPDATE( String mp_yn,String mp_company, String mp_type, int mb_no) {
		// 거절한 경우 n값만 넣어주면 됨
		if (mp_yn.equals("n")) {
			adminApprovalDAO.nyUPDATE(mb_no, mp_yn); // n/y 와 승인시간 저장
		}

		else if (mp_yn.equals("y")) {
			adminApprovalDAO.nyUPDATE(mb_no, mp_yn);

			if (mp_type.equals("news")) {
				adminApprovalDAO.newsInsert( mb_no,mp_company);
			}
			else if (mp_type.equals("stock")) {
				adminApprovalDAO.stockInsert(mb_no,mp_company);
			}
		}

	}

	public PageMaker getPageMaker(Criteria cri) {
		int count = adminApprovalDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}

	public List<AdmApprovalVO> search(String mp_company) {
		
		return adminApprovalDAO.searchApproval(mp_company);
		
	}

}

