package kh.st.boot.service;

import java.util.List;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;

public interface MyAccountService {
	
	AccountVO getAccountById(String mb_id);
	
	List<DepositVO> getDepositListByDate(String mb_id, String date);
	
	List<DepositVO> getDepositList(String mb_id);

}
