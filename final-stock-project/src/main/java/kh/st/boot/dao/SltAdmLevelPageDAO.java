package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.vo.AdminLevelPageVO;
import kh.st.boot.model.vo.NewsPaperVO;

@Mapper
public interface SltAdmLevelPageDAO {

	List<AdminLevelPageVO> getAllssltAdminLevelPage();

	AdminLevelPageVO getLvOne(@Param("lv_num") int lv_num);

	boolean insertAdmLv(@Param("lv_name")String lv_name,
						@Param("lv_num")int lv_num, 
						@Param("lv_alpha")String lv_alpha,
						@Param("lv_auto_use")String lv_auto_use,
						@Param("lv_up_limit") int lv_up_limit);

	void dltAdmLvdao(AdminLevelPageVO adminLevelPageVO);
	

	
}
