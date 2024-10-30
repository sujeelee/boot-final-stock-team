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


	public List<admOrderPageVO> getAllsltAdminOrder(Criteria cri) {
		return adminOrderDAO.selectAlladminOrder(cri);
	}

	public PageMaker getPageMaker(Criteria cri) {
		int count = adminOrderDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}
	
	public List<admOrderPageVO> searchNameId(String searchType, String searchText) {
		return 	adminOrderDAO.searchIdName(searchType,searchText);
		
	}



	public List<admOrderPageVO> deletOrderNum(String od_id) {
		return adminOrderDAO.deletOrder(od_id);
	}



	
}


// 근데 이름 동일한 값 전부 가져와야하면 저거 두개만 가져오면 안되잖아?
