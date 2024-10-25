package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.CommunityDAO;
import kh.st.boot.model.vo.BoardVO;
import kh.st.boot.model.vo.CommentVO;
import kh.st.boot.model.vo.CommunityActionVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunityService {

    private CommunityDAO communityDao;

	public void insertBoard(BoardVO newBoard) {
		communityDao.insertBoard(newBoard);		
	}

	public List<BoardVO> getBoardList(String st_code) {
		if(st_code == null) {
			return null;
		}
		return communityDao.getBoardList(st_code);
	}

	public boolean setFeelAction(CommunityActionVO feel) {
	    if (feel == null || feel.getMb_id() == null || 
	            feel.getSt_code() == null || feel.getMb_id().trim().length() == 0) {
	            return false;
	        }
	        // 게시글 번호가 음수이거나 0일 수 없음
	        if (feel.getCg_num() < 1) {
	            return false;
	        }

	        boolean tmp = false;
	        CommunityActionVO tmpCA = communityDao.findBoardByObjBoardVO(feel);

	        if (tmpCA == null) {
	            tmp = communityDao.createBoardOfCommunityAction(feel);
	            return tmp;
	        }

	        // 좋아요 처리
	        if ("like".equals(feel.getCg_like())) {
	            if (tmpCA.getCg_like() == null || tmpCA.getCg_like().trim().isEmpty()) {
	                tmp = communityDao.updateBoardOfCommunityAction_setLike(feel);
	            } else {
	                tmp = communityDao.updateBoardOfCommunityAction_setLikeNull(feel);
	            }
	        // 신고 처리
	        } else if ("report".equals(feel.getCg_report())) {
	            if (tmpCA.getCg_report() == null || tmpCA.getCg_report().trim().isEmpty()) {
	                tmp = communityDao.updateBoardOfCommunityAction_setReport(feel);
	            } else {
	                tmp = communityDao.updateBoardOfCommunityAction_setReportNull(feel);
	            }
	        }
	        if(tmp){
	            // 좋아요 또는 신고가 성공적으로 처리된 경우
	            System.out.println("Action successful: " + feel);
	            
	            // 좋아요 및 신고 수 업데이트 호출 전에 현재 상태 로그
	            System.out.println("Before updating counts: " +
	                "cg_num=" + feel.getCg_num() + 
	                ", cg_type=" + feel.getCg_type());
	        		updateActionCounts(feel);
	        		
	        		System.out.println("Counts updated successfully.");
	        }else {
	        	System.out.println("Action failed for: " + feel);
			}
	        return tmp;	        
	}

	private void updateActionCounts(CommunityActionVO feel) {
	    // 게시글에 대한 좋아요 및 신고 수 업데이트
	    if ("board".equals(feel.getCg_type())) {
	        int likeCount = communityDao.getLikeCountForBoard(feel.getCg_num());
	        System.out.println("Retrieved likeCount for board: " + likeCount);
	        int reportCount = communityDao.getReportCountForBoard(feel.getCg_num());
	        System.out.println("Retrieved reportCount for board: " + reportCount);

	        // 추가 로그 출력
	        System.out.println("Board - cg_num: " + feel.getCg_num() + ", LikeCount: " + likeCount + ", ReportCount: " + reportCount);
	        System.out.println("Retrieved likeCount for board: " + likeCount);

	        boolean success = communityDao.updateBoardCounts(feel.getCg_num(), likeCount, reportCount);
	        if (!success) {
	            System.out.println("Failed to update board counts for cg_num=" + feel.getCg_num());
	        } else {
	            System.out.println("Successfully updated board counts for cg_num=" + feel.getCg_num());
	        }
	    }
	    // 댓글에 대한 좋아요 및 신고 수 업데이트
	    else if ("comment".equals(feel.getCg_type())) {
	        int likeCount = communityDao.getLikeCountForComment(feel.getCg_num());
	        int reportCount = communityDao.getReportCountForComment(feel.getCg_num());

	        // 추가 로그 출력
	        System.out.println("Comment - cg_num: " + feel.getCg_num() + ", LikeCount: " + likeCount + ", ReportCount: " + reportCount);
	        

	        boolean success = communityDao.updateCommentCounts(feel.getCg_num(), likeCount, reportCount);
	        if (!success) {
	            System.out.println("Failed to update comment counts for cg_num=" + feel.getCg_num());
	        } else {
	            System.out.println("Successfully updated comment counts for cg_num=" + feel.getCg_num());
	        }
	    }
    }

	public boolean insertComment(CommentVO newComment) {
		return communityDao.insertComment(newComment);
		
	}
	public boolean updateCount(CommentVO newComment) {
		if(newComment == null) {
			
		}
		return communityDao.updateCount(newComment.getWr_no());
	}
	public List<CommentVO> getCommentList(int wr_no) {

		return communityDao.getCommentList(wr_no);
	}
}