package kh.st.boot.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.OrderDAO;
import kh.st.boot.model.vo.ReservationVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class OrderService {
	private OrderDAO OrderDao;

	public List<ReservationVO> getReservation(String st_code, String mb_id) {
		return OrderDao.getReservation(st_code, mb_id);
	}

	public String orderUpdate(Map<String, Object> form) {
		//예약으로 처리했는지, 바로 거래로 처리할건지 확인하기용
		return null;
	}
	
	
}
