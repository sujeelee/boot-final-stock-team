package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.dto.ComRankDTO;
import kh.st.boot.model.dto.DashListDTO;
import kh.st.boot.model.vo.BoardVO;
import kh.st.boot.model.vo.NewsVO;

public interface SearchDAO {
	List<DashListDTO> stockSearch(String stx);

	List<NewsVO> newsSearch(String stx);

	List<ComRankDTO> getCommunityRank();

	String getFlt(String code);

	List<BoardVO> getCommunityList(String code);
}
