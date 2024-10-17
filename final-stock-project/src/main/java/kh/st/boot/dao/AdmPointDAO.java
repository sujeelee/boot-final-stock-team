package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AdmPointVO;
import lombok.Data;


public interface AdmPointDAO {

	List<AdmPointVO> selectAll();

	List<AdmPointVO> selectId(String mb_id);

	void updatePoint(String mb_id, int po_num, String po_content);

	void deletPoint(int po_no);

	
}
