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
		// 개시글 번호가 음수 이거나 0일 수 없음
		if (feel.getCg_num() < 1) {
			return false;
		}
		
		if (feel.getCg_type().equals("board")) {
			//보더 처리
			boolean tmp = false;
			CommunityActionVO tmpCA = communityDao.findBoardByObjBoardVO(feel);

			if (tmpCA == null) {
				tmp = communityDao.createBoardOfCommunityAction(feel);
				return tmp;
			}

			if (feel.getCg_like().equals("like")) {
				if(tmpCA.getCg_like() == null || tmpCA.getCg_like().trim().length() == 0){
					tmp = communityDao.updateBoardOfCommunityAction_setLike(feel);
				} else {
					tmp = communityDao.updateBoardOfCommunityAction_setLikeNull(feel);
				}
			} else if(feel.getCg_report().equals("report")) {
				if (tmpCA.getCg_report() == null || tmpCA.getCg_report().trim().length() == 0) {
					tmp = communityDao.updateBoardOfCommunityAction_setReport(feel);
				} else {
					tmp = communityDao.updateBoardOfCommunityAction_setReportNull(feel);
				}
				
			}
			return tmp;
		}


		if (feel.getCg_type().equals("다야 나와라")) {
			//comment 처리 (후추)






		}


		return false;
	}

	public void insertComment(CommentVO newComment) {
		communityDao.insertComment(newComment);
		
	}

	public List<CommentVO> getCommentList(int wr_no) {

		return communityDao.getCommentList(wr_no);
	}


}