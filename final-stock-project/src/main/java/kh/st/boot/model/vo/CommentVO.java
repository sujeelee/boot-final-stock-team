package kh.st.boot.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentVO {
	
	private int co_id; //댓글 기본키
	private int wr_no; //게시글 기본키
	private int co_good; //댓글 좋아요
	private int co_bad; //댓글 싫어요
	private String co_content; //댓글내용
	private String mb_id; //댓글작성회원
	
	//DB 상 존제하지 않습니다.
	//report 신고, like 좋아요로 확은
	//join해서 가져올 것
	private String cg_like; //좋아요 눌렀는지 확인
	private String cg_report; // 신고 눌렀는지 확인
}
