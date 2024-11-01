package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.dto.DashListDTO;
import kh.st.boot.model.vo.NewsVO;

public interface SearchDAO {
	List<DashListDTO> stockSearch(String stx);

	List<NewsVO> newsSearch(String stx);
}
