package kh.st.boot.model.vo;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommunityActionVO {
	
	private int cg_no; //게시판과 댓글의 좋아요 수
	private int cg_num; //게시판 이거나 댓글확인용 
	private String cg_type; //게시판이면 없음 댓글이면 cm
	private String st_code; //주식코드
	private String mb_id;  //회원아이디
	private Date cg_datetime; //누른일자
	private String cg_action; //신고인지 좋아요인지 확인 S 신고/H 좋아요로 확은
}
