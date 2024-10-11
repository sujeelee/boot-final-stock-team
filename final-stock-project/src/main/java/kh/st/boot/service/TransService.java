package kh.st.boot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.st.boot.dao.DepositDAO;
import kh.st.boot.dao.MemberDAO;
import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositOrderVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.pagination.TransCriteria;
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
	
	public PageMaker getPageMaker(TransCriteria cri, String mb_id) {
		int count = depositDao.getCount(cri, mb_id);
		return new PageMaker(5, cri, count);
	}
	
	public List<DepositVO> getDepositList(String mb_id, TransCriteria cri) {
		if(mb_id == null) {
			return null;
		}
		return depositDao.getDepositMember(mb_id, cri);
	}
	
}
