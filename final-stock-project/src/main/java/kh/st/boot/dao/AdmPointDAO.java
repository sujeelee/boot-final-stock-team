package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AdmPointVO;
import kh.st.boot.pagination.Criteria;
import lombok.Data;


public interface AdmPointDAO {

	List<AdmPointVO> selectAll(Criteria cri);

	List<AdmPointVO> selectId(String mb_id);

	void updatePoint(String mb_id, int po_num, String po_content);

	void deletPoint(int po_no);

	int selectCountList(Criteria cri);

	
}
