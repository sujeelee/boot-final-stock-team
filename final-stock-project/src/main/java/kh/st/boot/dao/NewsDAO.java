package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;

public interface NewsDAO {
	
	List<NewsVO> selectNewsListByDate(String formatDate);

	NewsPaperVO selectNewsPaper(int np_no);

	List<NewsVO> selectNewsListByPaper(int np_no, String ne_datetime);
}
