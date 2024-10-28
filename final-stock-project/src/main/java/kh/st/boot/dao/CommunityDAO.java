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

	boolean insertComment(CommentVO newComment);

	List<CommentVO> getCommentList(int wr_no);
	
	int getLikeCountForBoard(int cg_num);

	int getReportCountForBoard(int cg_num);

	boolean updateBoardCounts(int cg_num, int likeCount, int reportCount);

	int getLikeCountForComment(int cg_num);

	int getReportCountForComment(int cg_num);

	boolean updateCommentCounts(int cg_num, int likeCount, int reportCount);

	boolean updateCount(int wr_no);

	CommunityActionVO checkUserActions(CommunityActionVO ca);
 

}
