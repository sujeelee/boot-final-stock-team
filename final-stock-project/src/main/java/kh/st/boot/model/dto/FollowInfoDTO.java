package kh.st.boot.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowInfoDTO {
	private String mb_nick; //팔로우회원 닉네임
	private int follower; //팔로워
	private int following;//팔로잉
}
