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

import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;
import kh.st.boot.service.NewsService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/newspaper")
public class NewsController {
	private NewsService newsService;
	
	@GetMapping("")
	public String newsList(Model model) {
		return "newspaper/paperList";
	}
	
	@ResponseBody
	@PostMapping("/views")
	public Map<String, List<NewsVO>> views(@RequestBody NewsVO news){
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

}
