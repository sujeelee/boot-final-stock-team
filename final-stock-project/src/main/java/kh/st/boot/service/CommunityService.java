package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.CommunityDAO;
import kh.st.boot.dao.MemberDAO;
import kh.st.boot.model.vo.BoardVO;
import kh.st.boot.model.vo.CommentVO;
import kh.st.boot.model.vo.CommunityActionVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunityService {

    private CommunityDAO communityDAO;
    
//    public void addLike(int wr_no) {
//        communityDAO.incrementLikes(wr_no);
//    }
//
//    public void addReport(int wr_no) {
//        communityDAO.incrementReports(wr_no);
//    }
//
//    public void addComment(CommentVO comment) {
//        communityDAO.addComment(comment);
//    }

	public void insertBoard(BoardVO newBoard) {
		communityDAO.insertBoard(newBoard);		
	}

	public List<BoardVO> getBoardList(String st_code) {
		if(st_code == null) {
			return null;
		}
		return communityDAO.getBoardList(st_code);
	}

	public CommunityActionVO nuguaction(Integer wr_no, String mb_id) {
        CommunityActionVO action = communityDAO.nuguaction(wr_no, mb_id);
       
        if (action == null) {
            return null;
        }
        return action;
    }
}