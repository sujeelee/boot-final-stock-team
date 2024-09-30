package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.vo.NewsEmojiVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;

public interface NewsDAO {
	
	List<NewsVO> selectNewsListByDate(String formatDate);

	NewsPaperVO selectNewsPaper(int np_no);

	List<NewsVO> selectNewsListByPaper(int np_no, String ne_datetime);

	NewsVO selectNews(int ne_no);

	NewsEmojiVO selectNewsEmoji(NewsEmojiVO emoji);

	void insertNewsEmoji(NewsEmojiVO emoji);

	void updateNewsEmojiCount(@Param("em")NewsEmojiVO emoji, @Param("count")int count);

	void updateNewsEmoji(NewsEmojiVO emoji);
	
	void deleteNewsEmoji(NewsEmojiVO emoji);

	boolean insertNews(NewsVO news);

	boolean updateNews(NewsVO news);

	boolean deleteNews(int ne_no);

}
