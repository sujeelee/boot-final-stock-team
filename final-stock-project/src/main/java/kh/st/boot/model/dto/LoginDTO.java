package kh.st.boot.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    
    private String id; // 아이디
    private String pw; // 페스워드
    private String re; // 아이디 기억하기 체크박스 on or null
    
    //확인작업입니다 나중에 지워야 해요
	@Override
	public String toString() {
		return "LoginDTO [id=" + id + ", pw=" + pw + ", re=" + re + "]";
	}
    
}
