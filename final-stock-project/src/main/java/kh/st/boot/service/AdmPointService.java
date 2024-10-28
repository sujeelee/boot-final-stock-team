package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdmPointDAO;
import kh.st.boot.model.vo.AdmPointVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdmPointService {

	@Autowired
	private AdmPointDAO admPointDAO;

	
	public List<AdmPointVO> allselect(Criteria cri) {
		return 	admPointDAO.selectAll(cri);
	}


	public List<AdmPointVO> idSelect(String mb_id) {
		return 	admPointDAO.selectId(mb_id);
	}


	public void plusminus(String mb_id, int po_num, String po_content) {
		admPointDAO.updatePoint(mb_id,po_num,po_content);
	}


	public void delete(int po_no) {
		admPointDAO.deletPoint(po_no);
	}


	public PageMaker getPageMaker(Criteria cri) {
		int count = admPointDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}


}
