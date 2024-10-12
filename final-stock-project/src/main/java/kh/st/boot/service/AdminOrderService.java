package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminOrderDAO;
import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.admOrderPageVO;

@Service
public class AdminOrderService {
	
	@Autowired
	private AdminOrderDAO adminOrderDAO ;

	
	public List<admOrderPageVO> getAllNewspapers() {
		return adminOrderDAO.selectAlladminOrder();
	}
	
}
