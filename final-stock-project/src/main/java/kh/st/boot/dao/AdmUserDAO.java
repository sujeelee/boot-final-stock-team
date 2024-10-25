package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AdmMemberVO;
import kh.st.boot.pagination.Criteria;

public interface AdmUserDAO {

	List<AdmMemberVO> selectAdmUser(Criteria cri);
	

	int selectCountList(Criteria cri);

	AdmMemberVO UseSelect(int mb_no);

	void UseUpdate(AdmMemberVO admMemberVO);

//	AdmMemberVO UserDelete(int mb_no);
	
	int UserDelete(int mb_no);




	List<AdmMemberVO> selectId(String search);


	List<AdmMemberVO> selectName(String search);


	List<AdmMemberVO> selectNick(String search);

}
