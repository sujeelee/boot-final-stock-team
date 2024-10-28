package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.st.boot.model.vo.admOrderPageVO;
<<<<<<< HEAD
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.OrderCriteria;
import kh.st.boot.pagination.UserCriteria;
=======
>>>>>>> parent of 0165321 (주문페이지 페이지네이션)

@Mapper
public interface AdminOrderDAO {

<<<<<<< HEAD
		List<admOrderPageVO> selectAlladminOrder(Criteria cri);
=======
		List<admOrderPageVO> selectAlladminOrder();
>>>>>>> parent of 0165321 (주문페이지 페이지네이션)

		List<admOrderPageVO> searchIdName(String od_name, String mb_id, String od_id);


		List<admOrderPageVO> deletOrder(String od_id);

<<<<<<< HEAD
		int selectCountList(Criteria cri);

		List<admOrderPageVO> orderSearch(String od_sh, OrderCriteria cri);

		int selectOrderCount(String od_sh, OrderCriteria cri);

=======
>>>>>>> parent of 0165321 (주문페이지 페이지네이션)
		

	}


