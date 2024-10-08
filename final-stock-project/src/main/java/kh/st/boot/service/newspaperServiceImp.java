package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.dto.NewspaperDTO;
import kh.st.boot.model.vo.NewsPaperVO;

@Service
public class newspaperServiceImp implements newspaperService {
	
	@Autowired
	private NewspaperDAO newspaperDAO;

	public List<NewsPaperVO> getAllNewspapers() {
		return newspaperDAO.selectAllNewspapers();
	}

	public boolean addNewspaper(String np_name, byte np_use) {
		// 왜 이렇게 했는지 분석 해서 주석 달기
		NewsPaperVO oldNews = newspaperDAO.getNewsOne(np_name);
		if (oldNews != null) {
			return false;
		}
		return newspaperDAO.insertNewspaper(np_name, np_use);
	}

	public void updateNewspaper(NewspaperDTO newspaperDTO) {
		newspaperDAO.updateNewspaper(newspaperDTO);
	}

	public void deleteNewspaper(NewsPaperVO NewsPaperVO) {
		newspaperDAO.deleteNewspaper(NewsPaperVO);
	}

	public List<NewsPaperVO> searchNewspapers(String np_name, byte np_use, int np_no) {
		List<NewsPaperVO> allNewspapers = newspaperDAO.selectAllNewspapers(); // 모든 신문사 가져오기
		List<NewsPaperVO> filteredNewspapers = new ArrayList<>();

		for (NewsPaperVO newspaper : allNewspapers) {
			boolean matchesName = (np_name == null || np_name.isEmpty() || newspaper.getNp_name().contains(np_name));
			// 여기서 이름을 판별해서 matchesName 안에 넣는데
			boolean matchesStatus = (np_use == 1 && newspaper.getNp_use() == 1)
					|| (np_use == 0 && newspaper.getNp_use() == 0);

			if (matchesName && matchesStatus) {
				filteredNewspapers.add(newspaper);
			}
		}

		return filteredNewspapers; // 필터링된 신문사 목록 반환
	}

}