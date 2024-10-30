package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AdmDaycheckVO;
import kh.st.boot.pagination.Criteria;

public interface AdmDaycheckDAO {

	List<AdmDaycheckVO> AllSelect(Criteria cri);

	List<AdmDaycheckVO> OneSelect(String mb_id);

	int selectCountList(Criteria cri);




}
