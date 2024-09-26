package kh.st.boot.service;

import kh.st.boot.model.dto.JoinDTO;
import kh.st.boot.model.dto.LoginDTO;
import kh.st.boot.model.vo.MemberVO;

public interface MemberService {

	MemberVO login(LoginDTO user_);
	
	void setUserCookie(MemberVO user);
	
	Boolean join(JoinDTO user_);

}

