package kh.st.boot.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.MyAccountDAO;
import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberApproveVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.model.vo.PointVO;
import kh.st.boot.model.vo.StockVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyAccountServiceImp implements MyAccountService {
	
	private MyAccountDAO myAccountDao;
	private PasswordEncoder passwordEncoder;
	
	@Override
	public AccountVO getAccountById(String mb_id) {
		if(mb_id == null) {
			return null;
		}
		return myAccountDao.selectAccountById(mb_id);
	}
	
	@Override
	public List<DepositVO> getDepositListByDate(String mb_id, String date) {
		if(mb_id == null || date == null) {
			return null;
		}
		return myAccountDao.selectDepositListByDate(mb_id, date);
	}

	@Override
	public List<PointVO> getPointList(String mb_id) {
		if(mb_id == null) {
			return null;
		}
		return myAccountDao.selectPointList(mb_id);
	}

	@Override
	public boolean checkPw(MemberVO user, String password) {
		if(user == null) {
			return false;
		}
		// matches : 왼쪽에는 암호화 안된 비번, 오른쪽은 암호화된 비번
		if(passwordEncoder.matches(password, user.getMb_password())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updatePw(String mb_id, String mb_password) {
		String encPw = passwordEncoder.encode(mb_password);
		return myAccountDao.updatePw(mb_id, encPw);
	}

	@Override
	public boolean deleteUser(MemberVO user) {
		if(myAccountDao.deleteAccount(user.getMb_no())) {
			return myAccountDao.deleteUser(user.getMb_id());
		}
		return false;
	}

	@Override
	public List<StockVO> getStockList() {
		return myAccountDao.selectStockList();
	}

	@Override
	public MemberApproveVO getMemberApprove(int mb_no) {
		return myAccountDao.selectMemberApprove(mb_no);
	}

	@Override
	public void insertMemberApprove(MemberApproveVO ma) {
		myAccountDao.insertMemberApprove(ma);
	}

	@Override
	public boolean deleteMemberApprove(int mb_no) {
		return myAccountDao.deleteMemberApprove(mb_no);
	}

	@Override
	public String getStockName(String mp_company) {
		return myAccountDao.getStockName(mp_company);
	}
}
