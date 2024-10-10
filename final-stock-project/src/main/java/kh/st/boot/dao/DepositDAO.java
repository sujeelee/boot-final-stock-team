package kh.st.boot.dao;

import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositOrderVO;
import kh.st.boot.model.vo.DepositVO;

public interface DepositDAO {

	String findMaxOrderId(@Param("date")String date);

	DepositOrderVO getOrderCheck(@Param("od_id")String do_od_id);

	void insertOrderData(@Param("do")DepositOrderVO newOrder);

	boolean updateOrder(@Param("do")DepositOrderVO upOrder);

	AccountVO getAccount(@Param("mb_no")int mb_no);

	void insertAccountDeposit(@Param("ac")AccountVO ac); 
	
	void updateAccountDeposit(@Param("ac")AccountVO ac); 
	
	void insertDepositLog(@Param("de")DepositVO deposit);

}
