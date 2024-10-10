package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;

public interface MyAccountDAO {
	
	AccountVO selectAccountById(String mb_id);
	
	List<DepositVO> selectDepositListByDate(String mb_id, String date);

	List<DepositVO> selectDepositList(String mb_id);

}
