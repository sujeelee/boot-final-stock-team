package kh.st.boot.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.NewsDAO;
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
		return newsDao.selectNewsList(ne_datetime);
	} 
}
