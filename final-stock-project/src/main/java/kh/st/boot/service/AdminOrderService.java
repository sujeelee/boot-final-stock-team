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


	public List<admOrderPageVO> getAllsltAdminOrder() {
		return adminOrderDAO.selectAlladminOrder();
	}


	public List<admOrderPageVO> searchNameId(String od_name, String mb_id) {
		return 	adminOrderDAO.searchIdName(od_name,mb_id );
		
	}


	public List<admOrderPageVO> ssearchNum(String od_id) {
		return 	adminOrderDAO.searchNum(od_id);
	}



	
}


// 근데 이름 동일한 값 전부 가져와야하면 저거 두개만 가져오면 안되잖아?
