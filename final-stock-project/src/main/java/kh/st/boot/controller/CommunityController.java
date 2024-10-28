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
import kh.st.boot.model.vo.CommentVO;
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
	public String community(Model mo, Principal principal, @PathVariable String st_code) {
		mo = stocksHeaderService.getModelSet(mo, principal, st_code);
		// principal이 null일 경우 mb_id를 null로 설정
	    String mb_id = null;
	    if (principal != null) {
	        mb_id = principal.getName();
	        mo.addAttribute("userInfo", mb_id);
	    }
		List<BoardVO> list = communityService.getBoardList(st_code,mb_id);

		mo.addAttribute("list", list);
		return "community/community";
	}

	@PostMapping("/insert")
	@ResponseBody
	public Map<String, Object> boardPostMethod(Model mo, @RequestParam Map<String, Object> board, Principal principal) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (principal == null) {
			result.put("res", "error");
			result.put("msg", "회원만 작성 가능합니다.");
			return result;
		}

		BoardVO newBoard = new BoardVO(); // 새로운 BoardVO 객체 생성
		newBoard.setMb_id(principal.getName());
		for (Map.Entry<String, Object> entry : board.entrySet()) {
			String jsonKey = entry.getKey();
			Object value = entry.getValue();
			Field fields;
			try {
				fields = BoardVO.class.getDeclaredField(jsonKey); 
				fields.setAccessible(true);
				fields.set(newBoard, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}



		communityService.insertBoard(newBoard);
		result.put("res", "s");
		return result;
	}


	@PostMapping("/replace")
	public String replaceBoardList_post(Model mo, Principal principal, @PathVariable String st_code){
		// Principal이 null일 경우 mb_id를 null로 설정
	    String mb_id = null;
	    if (principal != null) {
	        mb_id = principal.getName(); // principal이 null이 아닐 때만 getName() 호출
	        mo.addAttribute("userInfo", mb_id); // 사용자 정보가 있을 때만 모델에 추가
	    }
		List<BoardVO> list = communityService.getBoardList(st_code,mb_id);

		mo.addAttribute("list", list);
		return "community/community :: #replace_board";
	}


	@PostMapping("/action")
	@ResponseBody
	public Map<String, Object> communityAction(@RequestBody CommunityActionVO feel, Principal principal,@PathVariable String st_code) {
		Map<String, Object> result = new HashMap<String, Object>();
		//principal는 오브잭트  principal.getName();는 문지열, 오브잭트의 null을 확인해 주는게 좋습니다.
		//principal가 null 일때 map 형식인 result에 key와 value를 넣어서 화면에서 사용할 수 있게 도와줍니다.
		if (principal == null) {
			result.put("res", "401");
			result.put("msg", "로그인한 회원만 이용 가능합니다.");
			return result;
		}
		//필요한 변수를 먼저 선언 해 줍니다.
		String mb_id = principal.getName();
		feel.setMb_id(mb_id);
		feel.setSt_code(st_code);
		
		//변수들을 이용해서 DB에저장하고 필요한 값을 map에 넣습니다.
		boolean res = communityService.setFeelAction(feel);
		if(res){
			result.put("res", "200"); //다 정상
		} else {
			result.put("res", "204"); //DB 에 저장은 되는데 다른곳에 오류가 있음
		}
		
		List<BoardVO> list = communityService.getBoardList(st_code);
		for (BoardVO board : list) {
		    board.setCg_like(feel.getCg_like());
		    board.setCg_report(feel.getCg_report());
		    System.out.println("feel : " + feel);
		}
	
			
		System.out.println(list);
		result.put("list", list);
		return result;
	}
	
	@PostMapping("/comment")
	@ResponseBody
	public Map<String, Object> CommentPostMethod(Model mo, @RequestParam String co_content, int wr_no, Principal principal) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (principal == null) {
			result.put("res", "error");
			result.put("msg", "회원만 작성 가능합니다.");
			return result;
		}				
		CommentVO newComment = new CommentVO(); // 새로운 CommentVO 객체 생성
		newComment.setMb_id(principal.getName());
		newComment.setWr_no(wr_no);
		newComment.setCo_content(co_content);


		boolean res = communityService.insertComment(newComment);	
		if(res) {
			res = communityService.updateCount(newComment);
			
		}
		
		result.put("res", "s");
		return result;
	}


	@PostMapping("/replaceComment")
	public String replaceCommentList_post(Model mo, Principal principal, @RequestParam Map<String, Object> paramMap){
		List<CommentVO> colist = communityService.getCommentList(Integer.parseInt(paramMap.get("wr_no").toString()));
			

		if (principal != null) {
			mo.addAttribute("userInfo", principal.getName());
		}
		
		
		mo.addAttribute("colist", colist);
		return "community/community :: #replace_comment";
	}


}