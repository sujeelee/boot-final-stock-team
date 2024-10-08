package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.st.boot.model.vo.AdminLevelPageVO;

@Mapper
public interface SltAdmLevelPageDAO {

	List<AdminLevelPageVO> getAllssltAdminLevelPage();
}
