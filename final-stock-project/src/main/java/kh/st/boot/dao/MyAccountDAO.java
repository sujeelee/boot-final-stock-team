package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberApproveVO;
import kh.st.boot.model.vo.NewsMemberVO;
import kh.st.boot.model.vo.PointVO;
import kh.st.boot.model.vo.StockMemberVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.pagination.TransCriteria;

public interface MyAccountDAO {
	
	AccountVO selectAccountById(String mb_id);
	
	List<DepositVO> selectDepositListByDate(String mb_id, String date);
	
	List<PointVO> selectPointList(@Param("cri")TransCriteria cri, @Param("mb_id")String mb_id);

	boolean updatePw(String mb_id, String mb_password);
	
	boolean deleteAccount(int mb_no);

	boolean deleteUser(String mb_id);

	List<StockVO> selectStockList();

	MemberApproveVO selectMemberApprove(int mb_no);

	void insertMemberApprove(MemberApproveVO ma);

	boolean deleteMemberApprove(int mb_no);

	String getStockName(@Param("mp_company")String mp_company);

	NewsMemberVO selectNewsMember(int mb_no);

	StockMemberVO selectStockMember(int mb_no);

	void deleteNewsMember(int mb_no);

	void deleteStockMember(int mb_no);

}
