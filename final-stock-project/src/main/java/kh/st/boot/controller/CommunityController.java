package kh.st.boot.controller;

import java.lang.reflect.Field;
import java.security.Principal;
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

import kh.st.boot.model.vo.BoardVO;
import kh.st.boot.model.vo.CommunityActionVO;
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
    	System.out.println(list);
        return "community/community";
    }
    @PostMapping("/insert")  
    @ResponseBody
    public Map<String, Object> boardPostMethod(Model model, @RequestParam Map<String, Object> board, Principal principal) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	//System.out.println(board);
    	String mb_id = "ddsm4826";
    	/*
    	if(principal.getName() != null) {
    		mb_id = principal.getName();
    	} else {
    		result.put("res", "error");
    		result.put("msg", "회원만 작성 가능합니다.");
    		return result;
    	}*/
    	
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
    public Map<String, Object> communityAction(@RequestBody CommunityActionVO cg){
    	String mb_id= "ddsm4826";
    	Map<String ,Object> map = new HashMap<String, Object>();
    	
		System.out.println(cg);
        
        // CommunityActionVO nuguaction = communityService.nuguaction(wr_no, mb_id);
        //System.out.println(nuguaction);
    	return map;
    }
}