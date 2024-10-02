package kh.st.boot.dao;

import java.util.Date;
import java.util.List;

import kh.st.boot.model.vo.NewsVO;

public interface NewsDAO {
	List<NewsVO> selectNewsList(Date ne_datetime);
}
