package kh.st.boot.controller;

import java.lang.reflect.Field;
import java.security.Principal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.st.boot.model.vo.BoardVO;
import kh.st.boot.model.vo.CommunityActionVO;
import kh.st.boot.model.vo.WishVO;
import kh.st.boot.service.CommunityService;
import kh.st.boot.service.StocksHeaderService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/stock/{st_code}/community")
public class CommunityController {

    private CommunityService communityService;
    private StocksHeaderService stocksHeaderService;//
    
    @GetMapping
    public String community(Model model, Principal principal, @PathVariable String st_code) {
    	model = stocksHeaderService.getModelSet(model, principal, st_code); // 헤덜
    	// 서버에서 해당 커뮤니티에 게시글을 리스트로 가져와야됩니다.
    	List<BoardVO> list= communityService.getBoardList(st_code);
//    	System.out.println(list);
    	model.addAttribute("list", list);
        return "community/community";
    }
    @PostMapping("/insert")  
    @ResponseBody
    public Map<String, Object> boardPostMethod(Model model, @RequestParam Map<String, Object> board, Principal principal) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	String mb_id = null;
    	if(principal.getName() != null) {
    		mb_id = principal.getName();
    	} else {
    		result.put("res", "error");
    		result.put("msg", "회원만 작성 가능합니다.");
    		return result;
    	}   	
    	// board 값을 확인하기 위한 for문
    	BoardVO newBoard = new BoardVO(); // 새로운 BoardVO 객체 생성
    	newBoard.setMb_id(mb_id);
        for (Map.Entry<String, Object> entry : board.entrySet()) {
        	String jsonKey = entry.getKey();
            Object value = entry.getValue();
            Field fields;
			try {
				fields = BoardVO.class.getDeclaredField(jsonKey); //setWr_content()
				fields.setAccessible(true); // private 필드 접근
				fields.set(newBoard, value); //(String) board.get("wr_category")
			} catch (Exception e) {
				
				e.printStackTrace();
			}
        }
    	//System.out.println(newBoard);
    	communityService.insertBoard(newBoard);
    	result.put("newBoard", newBoard);
    	return result;
    }
    @PostMapping("/action")
    @ResponseBody
    public Map<String, Object> handleAction(@RequestBody CommunityActionVO actionVO, Principal principal) {
        String mb_id = null;
        System.out.println(actionVO);
        Map<String, Object> result = new HashMap<>();
        // 사용자 로그인 확인
        if (principal != null) {
            mb_id = principal.getName();
        } else {
            result.put("res", "fail");
            result.put("msg", "로그인한 회원만 이용 가능합니다.");
            return result;
        }

        boolean boRes = false;

        // 현재 액션 상태 확인
        CommunityActionVO chkAction = communityService.chkAction(actionVO.getCg_num(), mb_id);
        if (chkAction == null) {
        	System.out.println(actionVO);
            if ("like".equals(actionVO.getCg_action())) {
                boRes = communityService.performAction(actionVO.getSt_code(),actionVO.getCg_action(), actionVO.getCg_num(), actionVO.getCg_datetime(), mb_id);
                System.out.println(actionVO);
            } // cg_action이 like일때만 동작하는 조건문
            else if ("report".equals(actionVO.getCg_action())) {
                boRes = communityService.performAction(actionVO.getSt_code(),actionVO.getCg_action(), actionVO.getCg_num(), actionVO.getCg_datetime(), mb_id);
                System.out.println(actionVO);
            } // cg_action이 report일때만 동작하는 조건문
            
        // 이미 신고가 되어있거나 종아요가 되어있는 애들    
        } else {
            // 이미 신고가 되어있거나 좋아요가 되어있는 경우
            if ("like".equals(chkAction.getCg_action())) {
                boRes = communityService.deleteAction(actionVO.getCg_num(), mb_id);
                System.out.println(actionVO);
            } else if ("report".equals(chkAction.getCg_action())) {
                boRes = communityService.deleteAction(actionVO.getCg_num(), mb_id);
                System.out.println(actionVO);
        }
            // 추가적으로 좋아요와 신고 모두 처리할 수 있도록 하려면
            if ("like".equals(actionVO.getCg_action()) && !"like".equals(chkAction.getCg_action())) {
                // 좋아요가 없고 신고만 되어 있는 경우
                boRes = communityService.performAction(actionVO.getSt_code(), "like", actionVO.getCg_num(), actionVO.getCg_datetime(), mb_id);
                System.out.println("Like action added: " + actionVO);
            } else if ("report".equals(actionVO.getCg_action()) && !"report".equals(chkAction.getCg_action())) {
                // 신고가 없고 좋아요만 되어 있는 경우
                boRes = communityService.performAction(actionVO.getSt_code(), "report", actionVO.getCg_num(), actionVO.getCg_datetime(), mb_id);
                System.out.println("Report action added: " + actionVO);
            }
        }
        
        if (boRes) {
            result.put("res", "success");
            result.put("msg", "액션이 정상적으로 처리되었습니다.");
        } else {
            result.put("res", "fail");
            result.put("msg", "액션 처리에 실패했습니다.");
        }

        return result;
    }
}