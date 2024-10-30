package kh.st.boot.model.vo;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommunityActionVO {
	
	private int cg_no; //게시판과 댓글의 좋아요 수
	private int cg_num; //게시판 이거나 댓글 번호확인
	private String cg_type; //board, comment
	private String st_code; //주식코드
	private String mb_id;  //회원아이디
	private Date cg_datetime; //누른일자
	
	private String cg_like; //report 신고 / like 좋아요로 확은
	private String cg_report;
}
