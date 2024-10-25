package kh.st.boot.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentVO {
	
	private int co_id; //댓글 기본키
	private int wr_no; //게시글 기본키
	private int co_good; //댓글 좋아요
	private String co_content; //댓글내용
	private String mb_id; //댓글작성회원
}
