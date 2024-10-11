package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.PointVO;

public interface MyAccountDAO {
	
	AccountVO selectAccountById(String mb_id);
	
	List<DepositVO> selectDepositListByDate(String mb_id, String date);
	
	List<PointVO> selectPointList(String mb_id);

	List<DepositVO> selectDepositList(String mb_id);

}
