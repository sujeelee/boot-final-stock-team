package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.dto.NewspaperDTO;
import kh.st.boot.model.vo.NewsPaperVO;

@Mapper
public interface NewspaperDAO {

	List<NewsPaperVO> selectAllNewspapers(); // 모든 신문사 조회

	boolean insertNewspaper(@Param("np_name")String np_name, @Param("np_use") byte np_use); // 신문사 등록

	void updateNewspaper(NewspaperDTO newspaperDTO);

	void deleteNewspaper(NewsPaperVO newspaperVO); // 신문사 삭제

	List<NewsPaperVO> selectNewspapersByStatus(@Param("np_use") int np_use); // 사용 여부에 따라 신문사 조회

	int findMaxNpNo();
	
	//왜 이렇게 했는지 주석 달기
	NewsPaperVO getNewsOne(@Param("name") String np_name);

	
	//  NewspaperVO 로 결과() 보내줌 
	
	
	
}
