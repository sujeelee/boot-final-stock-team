package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.BoardVO;
import kh.st.boot.model.vo.CommentVO;
import kh.st.boot.model.vo.CommunityActionVO;

public interface CommunityDAO {


//	void incrementLikes(int wr_no);
//
//	void incrementReports(int wr_no);
//
//	void addComment(CommentVO comment);

	void insertBoard(BoardVO newBoard);

	List<BoardVO> getBoardList(String st_code);

	CommunityActionVO selectCommunityAction(int cg_num, String mb_id);

	boolean insertCommunityAction(CommunityActionVO communityActionVO);

	boolean deleteAction(int cg_num, String mb_id);

}
