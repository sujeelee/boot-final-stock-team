package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AdmDaycheckVO;

public interface AdmDaycheckDAO {

	List<AdmDaycheckVO> AllSelect();

	List<AdmDaycheckVO> OneSelect(String mb_id);




}
