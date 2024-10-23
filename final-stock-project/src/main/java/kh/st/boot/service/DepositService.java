package kh.st.boot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import kh.st.boot.dao.DepositDAO;
import kh.st.boot.dao.MemberDAO;
import kh.st.boot.model.vo.AccountVO;
import kh.st.boot.model.vo.DepositOrderVO;
import kh.st.boot.model.vo.DepositVO;
import kh.st.boot.model.vo.MemberVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class DepositService {
	
	private DepositDAO depositDao;
	
	@Transactional
	public String getOrderId(String type) {
		LocalDate now = LocalDate.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyMMdd"));
        
        // 현재 날짜로 시작하는 가장 큰 주문 번호 찾기
        String maxOrderId = depositDao.findMaxOrderId(date, type);
        
        int newOrderIdNumber;
        if (maxOrderId == null) {
            // 해당 날짜로 시작하는 주문이 없는 경우 1로 시작
            newOrderIdNumber = 1;
        } else {
            // 마지막 4자리 숫자를 가져와서 1 증가
            int lastOrderIdNumber = Integer.parseInt(maxOrderId.substring(6));
            newOrderIdNumber = lastOrderIdNumber + 1;
        }

        // 새로운 주문 번호 생성 (yyMMdd + 4자리 숫자)
        String newOrderId = date + String.format("%04d", newOrderIdNumber);
        
		return newOrderId;
	}

	public String insertOrder(DepositOrderVO newOrder) {
		String od_id = newOrder.getDo_od_id();
		DepositOrderVO chk = depositDao.getOrderCheck(od_id);
		if(chk != null) {
			od_id = getOrderId("");
			newOrder.setDo_od_id(od_id);
		}
		depositDao.insertOrderData(newOrder);
		
		return od_id;
	}

	@SuppressWarnings("null")
	public boolean updateOrder(DepositOrderVO upOrder, Model model) {
		DepositOrderVO chk = depositDao.getOrderCheck(upOrder.getDo_od_id());
		
		if(chk == null) {
			return false;
		}
		
		MemberVO mb = (MemberVO) model.getAttribute("member");
		
		if(mb == null) return false;
		
		AccountVO ac = depositDao.getAccount(mb.getMb_no());
		
		DepositVO deposit = new DepositVO();
		
		deposit.setDe_content("예치금 충전 : " + chk.getDo_price() + "원 주문번호 : " + upOrder.getDo_od_id());
		deposit.setDe_num(chk.getDo_price());
		deposit.setMb_id(mb.getMb_id());
		deposit.setDe_before_num(0);
		
		if(ac == null) {
			AccountVO newaAc = new AccountVO();
			newaAc.setMb_no(mb.getMb_no());
			newaAc.setAc_deposit(chk.getDo_price());
			depositDao.insertAccountDeposit(newaAc);
		} else {
			deposit.setDe_before_num(ac.getAc_deposit());
			depositDao.updateAccountDeposit(ac, chk.getDo_price());
		}
		
		depositDao.insertDepositLog(deposit);
		
		return depositDao.updateOrder(upOrder);
	}

	public void deleteStatusStay(String mb_id) {
		depositDao.deleteStatusStay(mb_id);
	}
}
