package kh.st.boot.dao;

import kh.st.boot.model.dto.NewspaperDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewspaperDAO {
    List<NewspaperDTO> selectAllNewspapers(); // 모든 신문사 조회

    void insertNewspaper(NewspaperDTO newspaperDTO); // 신문사 등록

    void updateNewspaper(NewspaperDTO newspaperDTO); // 신문사 수정

    void deleteNewspaper(int np_no); // 신문사 삭제

    List<NewspaperDTO> selectNewspapersByStatus(@Param("np_use") int np_use); // 사용 여부에 따라 신문사 조회

	
}
