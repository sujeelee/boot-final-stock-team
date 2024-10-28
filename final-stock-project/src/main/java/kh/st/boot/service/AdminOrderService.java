package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminOrderDAO;
import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.admOrderPageVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;

@Service 
public class AdminOrderService {
	
	@Autowired
	private AdminOrderDAO adminOrderDAO ;


	public List<admOrderPageVO> getAllsltAdminOrder( Criteria cri) {
		return adminOrderDAO.selectAlladminOrder(cri);
	}


	public List<admOrderPageVO> searchNameId(String od_name, String mb_id,  String od_id) {
		return 	adminOrderDAO.searchIdName(od_name,mb_id,od_id );
		
	}



	public List<admOrderPageVO> deletOrderNum(String od_id) {
		return adminOrderDAO.deletOrder(od_id);
	}


	public PageMaker getPageMaker(Criteria cri) {
		int count = adminOrderDAO.selectCountList(cri);

		return new PageMaker(10, cri, count);
	}
	
			


	
}

