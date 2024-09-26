package kh.st.boot.service;

import java.util.Date;
import java.util.List;

import kh.st.boot.model.vo.NewsVO;

public interface NewsService {

	List<NewsVO> getNewsList(Date ne_datetime);

}

