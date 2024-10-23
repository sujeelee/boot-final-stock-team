package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.BoardVO;
import kh.st.boot.model.vo.CommentVO;
import kh.st.boot.model.vo.CommunityActionVO;

public interface CommunityDAO {

	void insertBoard(BoardVO newBoard);

	List<BoardVO> getBoardList(String st_code);

	CommunityActionVO selectCommunityAction(int cg_num, String mb_id);

	boolean insertCommunityAction(CommunityActionVO communityActionVO);
 
	boolean deleteAction(int cg_num, String mb_id);

	CommunityActionVO findBoardByObjBoardVO(CommunityActionVO feel);

	boolean createBoardOfCommunityAction(CommunityActionVO feel);

	boolean updateBoardOfCommunityAction_setLike(CommunityActionVO feel);

	boolean updateBoardOfCommunityAction_setLikeNull(CommunityActionVO feel);

    boolean updateBoardOfCommunityAction_setReport(CommunityActionVO feel);

	boolean updateBoardOfCommunityAction_setReportNull(CommunityActionVO feel);

	void insertComment(CommentVO newComment);

	List<CommentVO> getCommentList(String wr_no);
 

}
