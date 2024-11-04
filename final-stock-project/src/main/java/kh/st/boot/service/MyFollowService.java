package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.SearchDAO;
import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.model.vo.FollowVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyFollowService {
	
	private SearchDAO searchDao;
	
	public List<FollowVO> getFollowList(String mb_id) {
		return searchDao.getFollowList(mb_id);
	}
	
}
