package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.vo.AdmMemberVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;

@Service
public class newspaperService {
	
	@Autowired
	private NewspaperDAO newspaperDAO;

	public List<NewsPaperVO> getAllNewspapers(Criteria cri) {
		return newspaperDAO.selectAllNewspapers(cri);
	}

	public boolean addNewspaper(String np_name, byte np_use) {
		// 왜 이렇게 했는지 분석 해서 주석 달기
		NewsPaperVO oldNews = newspaperDAO.getNewsOne(np_name);
		if (oldNews != null) {
			return false;
		}
		return newspaperDAO.insertNewspaper(np_name, np_use);
	}

	public boolean updateNewspaper(String np_name, byte np_use) {
		NewsPaperVO oldNews = newspaperDAO.getNewsOne(np_name);
		if (oldNews != null) {
			return false;
		}
		return newspaperDAO.insertNewspaper(np_name, np_use);
	}
	public void deleteNewspaper(NewsPaperVO NewsPaperVO) {
		newspaperDAO.deleteNewspaper(NewsPaperVO);
	}

	public List<NewsPaperVO> searchNewspapers(String np_name, byte useByte, int np_no, Criteria cri) {
		List<NewsPaperVO> allNewspapers = newspaperDAO.selectAllNewspapers(cri); // 모든 신문사 가져오기
		List<NewsPaperVO> filteredNewspapers = new ArrayList<>();

	public PageMaker getPageMaker(Criteria cri) {
		int count = newspaperDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}

	public List<AdmMemberVO> getSearchNews(String np_name, Criteria cri) {
		return newspaperDAO.searchNews(cri, np_name);
	}

	public PageMaker getPageMakerSearch(Criteria cri, String np_name) {
		int totalCount = newspaperDAO.selectTotalCount(cri, np_name);
		return new PageMaker(10, cri, totalCount);
	}

	public PageMaker getPageMaker(Criteria cri) {
		int count = newspaperDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}

}