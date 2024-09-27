package kh.st.boot.service;

import java.util.Date;
import java.util.List;

import kh.st.boot.model.vo.NewsEmojiVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;

public interface NewsService {

	List<NewsVO> getNewsList(Date ne_datetime);

	NewsPaperVO getNewsPaper(int np_no);

	List<NewsVO> getNewsList(int np_no, String ne_datetime);

	NewsVO getNews(int ne_no);

	NewsEmojiVO getNewsEmoji(NewsEmojiVO emoji);

	boolean insertNewsEmoji(NewsEmojiVO emoji);

	void updateNewsEmojiCount(NewsEmojiVO newEmoji, int i);

	void updateNewsEmoji(NewsEmojiVO emoji);

}

