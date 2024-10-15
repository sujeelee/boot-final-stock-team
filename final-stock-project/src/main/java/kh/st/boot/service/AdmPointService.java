package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdmPointDAO;
import kh.st.boot.model.vo.AdmPointVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdmPointService {

	@Autowired
	private AdmPointDAO admPointDAO;

	
	public List<AdmPointVO> allselect() {
		return 	admPointDAO.selectAll();
	}


	public List<AdmPointVO> idSelect(String mb_id) {
		return 	admPointDAO.selectId(mb_id);
	}


	public void plusminus(String mb_id, int po_num, String po_content, String po_end_date) {
		admPointDAO.updatePoint(mb_id,po_num,po_content,po_end_date);
	}

}
