package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AdminVO;

public interface AdminDAO {
	
	// DB값 받아오기
	List<AdminVO> selectAdminList();



}
