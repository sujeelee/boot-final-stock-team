package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.st.boot.model.vo.AdmMemberVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PostCriteria;

@Mapper
public interface AdminUserDAO {

	List<AdmMemberVO> selectAdmUser(Criteria cri);

	int selectCountList(Criteria cri);

	AdmMemberVO UseSelect(int mb_no);

	void UseUpdate(AdmMemberVO admMemberVO);

//	AdmMemberVO UserDelete(int mb_no);
	
	int UserDelete(int mb_no);

}
