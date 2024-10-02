package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.vo.NewsPaperVO;

@Service
public class NewspaperService {

	@Autowired
	private NewspaperDAO newspaperDAO;
	
	public List<NewsPaperVO> getAllNewspapers() {
		return newspaperDAO.selectAllNewspapers();
	}

	public void addNewspaper(NewsPaperVO newspaper) {
		newspaperDAO.insertNewspaper(newspaper);
	}

	public void updateNewspaper(NewsPaperVO newspaper) {
		newspaperDAO.updateNewspaper(newspaper);
	}

	public void deleteNewspaper(int np_no) {
		newspaperDAO.deleteNewspaper(np_no);
	}


	
	public List<NewsPaperVO> searchNewspapers(String name, String status) {
		List<NewsPaperVO> allNewspapers = newspaperDAO.selectAllNewspapers(); // 모든 신문사 가져오기
		List<NewsPaperVO> filteredNewspapers = new ArrayList<>();

		for (NewsPaperVO newspaper : allNewspapers) {
			boolean matchesName = (name == null || name.isEmpty() || newspaper.getNp_name().contains(name));
			boolean matchesStatus = (status == null || status.isEmpty()
					|| (status.equals("active") && newspaper.getNp_use() == 1)
					|| (status.equals("inactive") && newspaper.getNp_use() == 0));

			if (matchesName && matchesStatus) {
				filteredNewspapers.add(newspaper);
			}
		}

		return filteredNewspapers; // 필터링된 신문사 목록 반환
	}
}
