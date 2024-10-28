package kh.st.boot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<BoardVO> getBoardList(String st_code, String mb_id) {
		if(st_code == null) {
			return null;
		}
		List<BoardVO> list = communityDao.getBoardList(st_code);
		for(BoardVO tmp : list) {
			CommunityActionVO ca = new CommunityActionVO();
			ca.setCg_num(tmp.getWr_no());
			ca.setCg_type("board");
			ca.setMb_id(mb_id);
			
		     if (mb_id != null) { // 로그인한 사용자
		            CommunityActionVO actionStatus = communityDao.checkUserActions(ca);

		            // actionStatus가 null이 아닐 경우에만 속성 설정
		            if (actionStatus != null) {
		                // 좋아요와 신고 상태를 설정
		                tmp.setCg_like(actionStatus.getCg_like().equals("like") ? "y" : "n");
		                tmp.setCg_report(actionStatus.getCg_report().equals("report") ? "y" : "n");
		            } else {
		                // actionStatus가 null일 경우 기본값 설정
		                tmp.setCg_like("n");
		                tmp.setCg_report("n");
		            }
		        } else {
		            // 로그인하지 않은 사용자에 대해 기본값 설정
		            tmp.setCg_like("n");
		            tmp.setCg_report("n");
		        }
		}
		return list;
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
	            // tmpCA에다가 다시 위 코드에 추가한 인설트를 다시 셀렉트로 가져온다.
	            tmpCA = communityDao.selectCommunityAction(feel.getCg_num(), feel.getMb_id());
	            tmpCA.setCg_like(null);
	            tmpCA.setCg_report(null);
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
	        		updateActionCounts(feel);
	        		
	        }else {
	        	System.out.println("Action failed for: " + feel);
			}
	        return tmp;	        
	}

	private void updateActionCounts(CommunityActionVO feel) {
	    // 게시글에 대한 좋아요 및 신고 수 업데이트
	    if ("board".equals(feel.getCg_type())) {
	        int likeCount = communityDao.getLikeCountForBoard(feel.getCg_num());
	        int reportCount = communityDao.getReportCountForBoard(feel.getCg_num());



	        boolean success = communityDao.updateBoardCounts(feel.getCg_num(), likeCount, reportCount);
	    }
	    // 댓글에 대한 좋아요 및 신고 수 업데이트
	    else if ("comment".equals(feel.getCg_type())) {
	        int likeCount = communityDao.getLikeCountForComment(feel.getCg_num());
	        int reportCount = communityDao.getReportCountForComment(feel.getCg_num());

	        

	        boolean success = communityDao.updateCommentCounts(feel.getCg_num(), likeCount, reportCount);
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

	public List<BoardVO> getBoardList(String st_code) {
		return communityDao.getBoardList(st_code);
	}

}