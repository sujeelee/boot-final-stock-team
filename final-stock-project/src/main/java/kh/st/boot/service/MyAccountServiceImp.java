package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.MyAccountDAO;
import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.pagination.TransCriteria;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyAccountServiceImp implements MyAccountService {
	
	private MyAccountDAO myAccountDao;
	
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
	
}
