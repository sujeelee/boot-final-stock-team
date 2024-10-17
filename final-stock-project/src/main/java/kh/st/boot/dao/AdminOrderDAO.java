package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.st.boot.model.vo.admOrderPageVO;

@Mapper
public interface AdminOrderDAO {

		List<admOrderPageVO> selectAlladminOrder();

		List<admOrderPageVO> searchIdName(String od_name, String mb_id, String od_id);


		List<admOrderPageVO> deletOrder(String od_id);

		

	}


