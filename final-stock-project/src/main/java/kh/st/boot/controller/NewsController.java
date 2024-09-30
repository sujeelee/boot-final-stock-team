package kh.st.boot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kh.st.boot.model.vo.NewsEmojiVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;
import kh.st.boot.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@AllArgsConstructor
@RequestMapping("/newspaper")
@Log4j2
public class NewsController {
	private NewsService newsService;
	
	@GetMapping("")
	public String newsList(Model model) {
		return "newspaper/paperList";
	}
	
	@ResponseBody
	@PostMapping("/views")
	public Map<String, List<NewsVO>> views(@RequestBody NewsVO news){
		log.info("newspaper/views:post");
		log.info(news);
		Map<String, List<NewsVO>> map = new HashMap<String, List<NewsVO>>();
		Date ne_datetime = news.getNe_datetime();
		// 서비스에게 날짜를 주고 리스트를 가져옴
		List<NewsVO> newsList = newsService.getNewsList(ne_datetime);
		map.put("newsList", newsList);
		return map;
	}
	
	@GetMapping("/newsList/{np_no}/{ne_datetime}")
	public String list(Model model, @PathVariable int np_no, @PathVariable String ne_datetime) {
		NewsPaperVO newspaper = newsService.getNewsPaper(np_no);
		List<NewsVO> newsList = newsService.getNewsList(np_no, ne_datetime);
		model.addAttribute("newspaper",newspaper);
		model.addAttribute("newsList", newsList);
		model.addAttribute("ne_datetime", ne_datetime);
		return "newspaper/newsList";
	}
	
	@ResponseBody
	@PostMapping("/newsList/views")
	public Map<String, List<NewsVO>> newsListViews(@RequestBody NewsVO news){
		Map<String, List<NewsVO>> map = new HashMap<String, List<NewsVO>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String ne_datetime = format.format(news.getNe_datetime());
		int np_no = news.getNp_no();
		// 서비스에게 날짜와 뉴스페이퍼 번호를 주고 리스트를 가져옴
		List<NewsVO> newsList = newsService.getNewsList(np_no, ne_datetime);
		map.put("newsList", newsList);
		return map;
	}
	
	@GetMapping("/detail/{ne_no}")
	public String detail(Model model, @PathVariable int ne_no) {
		NewsVO news = newsService.getNews(ne_no);
		NewsPaperVO newspaper = newsService.getNewsPaper(news.getNp_no());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String ne_datetime = format.format(news.getNe_datetime());
		model.addAttribute("news", news);
		model.addAttribute("newspaper", newspaper);
		model.addAttribute("ne_datetime", ne_datetime);
		return "newspaper/detail";
	}
	
	@ResponseBody
	@PostMapping("/emoji")
	public Map<String, Object> newsEmoji(@RequestBody NewsEmojiVO emoji, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 회원 기능 완료되면 봉인해제
		// MemberVO user = (MemberVO)session.getAttribute("user");
		emoji.setMb_id("www7878");
		NewsVO news = newsService.getNews(emoji.getNe_no());
		// 이전에 선택한 이모지
		NewsEmojiVO prevEmoji = newsService.getNewsEmoji(emoji);
		if(prevEmoji == null) {
			// 사용자가 처음으로 선택한 이모지
			boolean res = newsService.insertNewsEmoji(emoji);
			// 새로운 반응에 대한 카운트 증가
			if(res) {
				newsService.updateNewsEmojiCount(emoji, 1);
				news = newsService.getNews(emoji.getNe_no());
			}else {
				System.out.println("ㅗ^.^ㅗ");
			}
		} else {
			// 사용자가 다른 반응으로 바꾼 경우
			if(prevEmoji.getEm_act() != emoji.getEm_act()) {
				// 이전 카운트를 감소
				newsService.updateNewsEmojiCount(prevEmoji, -1);
				// 새로운 이모지 카운트 증가
				newsService.updateNewsEmojiCount(emoji, 1);
				// 이모지 업데이트
				newsService.updateNewsEmoji(emoji);
				news = newsService.getNews(emoji.getNe_no());
			}
		}
		map.put("news", news);
		return map;
	}
}
