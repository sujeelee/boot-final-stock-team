package kh.st.boot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.st.boot.dao.DepositDAO;
import kh.st.boot.dao.MemberDAO;
import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositOrderVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class TransService {
	private DepositDAO depositDao;
	private MemberDAO memberDao;
	public AccountVO getAccountAmt(String mb_id) {
		
		MemberVO mb = memberDao.findById(mb_id);
		
		if(mb == null) return null;
		
		AccountVO ac = depositDao.getAccount(mb.getMb_no());
		
		return ac;
	}
	public DepositOrderVO getDepositOrder(String od_id) {
		return depositDao.getOrderCheck(od_id);
	}
	
}
