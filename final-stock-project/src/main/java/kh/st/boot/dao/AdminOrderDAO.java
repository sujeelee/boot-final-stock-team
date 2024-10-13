package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.st.boot.model.vo.admOrderPageVO;

@Mapper
public interface AdminOrderDAO {

		List<admOrderPageVO> selectAlladminOrder();

		List<admOrderPageVO> searchIdName(String od_name, String mb_id);

		List<admOrderPageVO> searchNum(String od_id);

		

	}


