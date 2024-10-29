package kh.st.boot.dao;

import java.util.Date;
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

	void insertUser(int mb_no, String mb_id, String mb_password, String mb_name, String mb_nick, String mb_hp,
			String mb_email, int mb_zip, String mb_addr, String mb_addr2, Date mb_birth, int mb_level,
			String mb_datetime, String mb_edit_date, String mb_stop_date, String mb_out_date, String mb_cookie,
			String mb_cookie_limit, int mb_point, int mb_emailing, String mb_account);

}
