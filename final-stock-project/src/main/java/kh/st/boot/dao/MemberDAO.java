package kh.st.boot.dao;

import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.vo.MemberVO;

public interface MemberDAO {
	
	MemberVO findById(@Param("id")String username);

	MemberVO findById(@Param("id") String id);

    Boolean join(MemberVO new_User);

	MemberVO findIdByCookie(@Param("sid")String sid);
}
