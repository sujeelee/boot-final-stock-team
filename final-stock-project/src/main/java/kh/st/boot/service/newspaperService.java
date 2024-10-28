package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.NewspaperDAO;
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

		
		// 부분문자열 ( 디비 값이 입력한 문자열 포함하는지 판별 ) 
		for (NewsPaperVO newspaper : allNewspapers) {
			boolean matchesName = (np_name == null || np_name.isEmpty() || newspaper.getNp_name().contains(np_name));
			// 여기서 이름을 판별해서 matchesName 안에 넣는데
			boolean matchesStatus = (useByte == 1 && newspaper.getNp_use() == 1)
					|| (useByte == 0 && newspaper.getNp_use() == 0);
			
			if (matchesName && matchesStatus) {
				filteredNewspapers.add(newspaper);  
			}
		}

		return filteredNewspapers; // 필터링된 신문사 목록 반환
	}

	public PageMaker getPageMaker(Criteria cri) {
		int count = newspaperDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}

}