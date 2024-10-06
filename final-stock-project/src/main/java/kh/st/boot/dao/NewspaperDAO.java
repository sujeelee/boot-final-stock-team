package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.dto.NewspaperDTO;
import kh.st.boot.model.vo.NewsPaperVO;

@Mapper
public interface NewspaperDAO {
	List<NewsPaperVO> selectAllNewspapers(); // 모든 신문사 조회

	public void insertNewspaper(NewspaperDTO newspaperDTO); // 신문사 등록

	void updateNewspaper(@Param("np")NewsPaperVO newspaper); // 신문사 수정

	void deleteNewspaper(@Param("np_no")int np_no); // 신문사 삭제

	List<NewsPaperVO> selectNewspapersByStatus(@Param("np_use") int np_use); // 사용 여부에 따라 신문사 조회

	int findMaxNpNo();

}
