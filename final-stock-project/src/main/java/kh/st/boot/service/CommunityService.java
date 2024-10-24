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

	        return tmp;
	}

	public boolean insertComment(CommentVO newComment) {
		return communityDao.insertComment(newComment);
		
	}

	public List<CommentVO> getCommentList(int wr_no) {

		return communityDao.getCommentList(wr_no);
	}

	public boolean updateCount(CommentVO newComment) {
		if(newComment == null) {
			
		}
		return communityDao.updateCount(newComment.getWr_no());
	}

	public boolean ActionCount(CommunityActionVO feel) {
		
		return communityDao.ActionCount(feel);
	}


}