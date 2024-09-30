package kh.st.boot.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kh.st.boot.model.vo.FileVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.model.vo.NewsEmojiVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;

public interface NewsService {

	List<NewsVO> getNewsList(Date ne_datetime);

	NewsPaperVO getNewsPaper(int np_no);

	List<NewsVO> getNewsList(int np_no, String ne_datetime);

	NewsVO getNews(int ne_no);

	NewsEmojiVO getNewsEmoji(NewsEmojiVO emoji);

	void insertNewsEmoji(NewsEmojiVO emoji);

	void updateNewsEmojiCount(NewsEmojiVO newEmoji, int i);

	void updateNewsEmoji(NewsEmojiVO emoji);

	void deleteNewsEmoji(NewsEmojiVO emoji);
	
	boolean insertNews(NewsVO news, MemberVO user, MultipartFile file);

	boolean updateNews(NewsVO news, MemberVO user, MultipartFile file, Integer num);

	boolean deleteNews(int ne_no, MemberVO user);

	FileVO getFile(int ne_no);

}

