package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.SearchDAO;
import kh.st.boot.model.dto.FollowInfoDTO;
import kh.st.boot.model.vo.FollowVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyFollowService {
	
	private SearchDAO searchDao;
	
	public List<FollowVO> getFollowList(String mb_id, Criteria cri) {
		return searchDao.getFollowList(mb_id, cri);
	}

	public PageMaker getPageMaker(String type, Criteria cri, String mb_id) {
		int count = 0;
		count = searchDao.getCount(type, cri, mb_id);
		return new PageMaker(10, cri, count);
	}

	public boolean unfollow(String fo_no, String mb_id) {
		return searchDao.unfollow(fo_no, mb_id);
	}

	public List<FollowVO> getFollowViews(String fo_id, Criteria cri) {
		return searchDao.getFollowViews(fo_id, cri);
	}

	public FollowInfoDTO getFollowInfo(String fo_id) {
		return searchDao.getFollowInfo(fo_id);
	}
	
}
