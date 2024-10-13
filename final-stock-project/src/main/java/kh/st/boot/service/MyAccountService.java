package kh.st.boot.service;

import java.util.List;

import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.model.vo.PointVO;

public interface MyAccountService {
	
	AccountVO getAccountById(String mb_id);
	
	List<DepositVO> getDepositListByDate(String mb_id, String date);

	List<PointVO> getPointList(String mb_id);

	boolean checkPw(MemberVO user, String password);

	boolean updatePw(String name, String mb_password);

	boolean deleteUser(MemberVO user);

}
