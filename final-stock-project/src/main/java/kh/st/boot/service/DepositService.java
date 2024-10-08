package kh.st.boot.service;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.DepositDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class DepositService {
	private DepositDAO depositDao;

	public String getOrderId() {
		
		return null;
	}
}
