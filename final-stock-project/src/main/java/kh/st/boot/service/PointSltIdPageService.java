package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdmDaycheckDAO;
import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.vo.AdmDaycheckVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class PointSltIdPageService {
	
	
	@Autowired
	private AdmDaycheckDAO admDaycheckDAO;
	
	
	
	public List<AdmDaycheckVO> sltAllPoint() {
	
		return admDaycheckDAO.AllSelect();
	}
	
	
	public List<AdmDaycheckVO> sltOnePoint(String mb_id) {
		return admDaycheckDAO.OneSelect(mb_id);
	}

	// 1 갯수새는 서비스 
	public int countOnesInDays(String mb_id) {
	        List<AdmDaycheckVO> daycheckList = sltOnePoint(mb_id);
	        int count = 0;

	        for (AdmDaycheckVO daycheck : daycheckList) {
	            // dc_days 값이 "1"인지 확인
	            if ("1".equals(daycheck.getDc_days())) {
	                count++;
	            }
	        }
	        return count;  // 1의 개수 반환
	    }
	
	
	


}
