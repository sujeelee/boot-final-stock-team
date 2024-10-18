package kh.st.boot.service;

import java.sql.Date;
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

	public void insertBoard(BoardVO newBoard) {
		communityDAO.insertBoard(newBoard);		
	}

	public List<BoardVO> getBoardList(String st_code) {
		if(st_code == null) {
			return null;
		}
		return communityDAO.getBoardList(st_code);
	}
	public CommunityActionVO chkAction(int cg_num, String mb_id) {
		return communityDAO.selectCommunityAction(cg_num, mb_id); // DAO에서 데이터 조회
	}
	public boolean performAction(String st_code, String cg_action, int cg_num, Date cg_datetime, String mb_id) {
		CommunityActionVO communityActionVO = new CommunityActionVO();
		communityActionVO.setSt_code(st_code);
        communityActionVO.setCg_num(cg_num);
        communityActionVO.setMb_id(mb_id);
        communityActionVO.setCg_action(cg_action);
        communityActionVO.setCg_datetime(cg_datetime);
        
        
        if ("like".equals(cg_action)) {
            return communityDAO.insertCommunityAction(communityActionVO);
        } else if ("report".equals(cg_action)) {
            return communityDAO.insertCommunityAction(communityActionVO);
        }
        
        return false;
    }

	public boolean deleteAction(int cg_num, String mb_id) {
		return communityDAO.deleteAction(cg_num,mb_id);
	}
}