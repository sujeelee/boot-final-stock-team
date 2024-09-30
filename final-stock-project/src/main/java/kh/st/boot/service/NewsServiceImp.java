package kh.st.boot.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.NewsDAO;
import kh.st.boot.model.vo.NewsEmojiVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class NewsServiceImp implements NewsService{

	private NewsDAO newsDao;

	@Override
	public List<NewsVO> getNewsList(Date ne_datetime) {
		if(ne_datetime == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String formatDate = format.format(ne_datetime);
		return newsDao.selectNewsListByDate(formatDate);
	}

	@Override
	public NewsPaperVO getNewsPaper(int np_no) {
		return newsDao.selectNewsPaper(np_no);
	}

	@Override
	public List<NewsVO> getNewsList(int np_no, String ne_datetime) {
		String regex = "^\\d{4}-\\d{2}-\\d{2}$";
		if(!Pattern.matches(regex, ne_datetime)) {
			return null;
		}
		return newsDao.selectNewsListByPaper(np_no, ne_datetime);
	}

	@Override
	public NewsVO getNews(int ne_no) {
		return newsDao.selectNews(ne_no);
	}

	@Override
	public NewsEmojiVO getNewsEmoji(NewsEmojiVO emoji) {
		if(emoji == null) {
			return null;
		}
		return newsDao.selectNewsEmoji(emoji);
	}

	@Override
	public boolean insertNewsEmoji(NewsEmojiVO emoji) {
		if(emoji == null) {
			return false;
		}
		return newsDao.insertNewsEmoji(emoji);
	}

	@Override
	public void updateNewsEmojiCount(NewsEmojiVO emoji, int count) {
		if(emoji == null) {
			return;
		}
		newsDao.updateNewsEmojiCount(emoji, count);
	}

	@Override
	public void updateNewsEmoji(NewsEmojiVO emoji) {
		if(emoji == null) {
			return;
		}
		newsDao.updateNewsEmoji(emoji);
	}
}
