package kh.st.boot.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardVO {

	private int wr_no; //게시글 기본키 
	private String wr_category; //주식코드
	private String wr_content; //본문 내용
	private String mb_id; //작성회원
	private int wr_comment; //작성된 댓글수
	private int wr_good; //좋아요갯수
	private int wr_singo; //신고갯수
	private String wr_blind; //숨겨짐여부나중에추가예정

	//DB 상 존제하지 않습니다.
	//report 신고, like 좋아요로 확은
	//join해서 가져올 것
	private String cg_like;
	private String cg_report;
}
