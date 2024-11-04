package kh.st.boot.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminDAO;
import kh.st.boot.dao.SearchDAO;
import kh.st.boot.dao.StockDAO;
import kh.st.boot.model.dto.ComRankDTO;
import kh.st.boot.model.dto.DashListDTO;
import kh.st.boot.model.dto.HotStockDTO;
import kh.st.boot.model.dto.SearchDTO;
import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.model.vo.NewsVO;
import kh.st.boot.model.vo.StockJisuVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfigService {
	
	private AdminDAO adminDao;
	private StockDAO stockDao;
	private SearchDAO searchDao;
	
	
	public AdminVO getConfig() {
		return adminDao.selectAdmin();
	}

	public List<HotStockDTO> getHotStockList(int limit) {
		return stockDao.getHotStockList(limit);
	}

	public List<DashListDTO> getDashList(Map<String, Object> param) {
		List<DashListDTO> lists = new ArrayList<DashListDTO>();
		
		if(param.get("tag").equals("mystock")) {
			lists = stockDao.getMyStock((String)param.get("mb_id"));
		} else if(param.get("tag").equals("mywish")){
			lists = stockDao.getMyWish((String)param.get("mb_id"));
		}
		
		return lists;
	}
	
	public List<StockJisuVO> jisuConfig(String type) {
		return stockDao.jisuConfig(type);
	}

	public List<SearchDTO> totalSearch(String type, String stx) {
		List<SearchDTO> list = new ArrayList<SearchDTO>();
		SimpleDateFormat formatter = new SimpleDateFormat("MM월 dd일");
		if(type.equals("stock")) {
			List<DashListDTO> stocks = searchDao.stockSearch(stx);
			if(stocks.isEmpty() == false) {
				for(DashListDTO tmps : stocks) { 
					SearchDTO newSearch = new SearchDTO();
					newSearch.setStockCnt(stocks.size());
					newSearch.setNewsCnt(0);
					newSearch.setCode(tmps.getCode());
					newSearch.setFlt(tmps.getFlt());
					newSearch.setPrice(tmps.getPrice());
					newSearch.setTitle(tmps.getStockName());
					newSearch.setType("stock");
					list.add(newSearch);
				}
			} else {
				SearchDTO newSearch = new SearchDTO();
				newSearch.setNewsCnt(0);
				newSearch.setStockCnt(0);
				list.add(newSearch);
			}
		} else if(type.equals("news")) {
			List<NewsVO> news = searchDao.newsSearch(stx);
			
			if(news.isEmpty() == false) {
				for(NewsVO tmps : news) { 
					SearchDTO newSearch = new SearchDTO();
					newSearch.setNewsCnt(news.size());
					newSearch.setStockCnt(0);
					newSearch.setCode(Integer.toString(tmps.getNe_no()));
					newSearch.setTitle(tmps.getNe_title());
					newSearch.setDate(formatter.format(tmps.getNe_datetime()));
					newSearch.setNewspaper(tmps.getNp_name());
					newSearch.setType("news");
					list.add(newSearch);
				}
			} else {
				SearchDTO newSearch = new SearchDTO();
				newSearch.setNewsCnt(0);
				newSearch.setStockCnt(0);
				list.add(newSearch);
			}
		} else {
			List<DashListDTO> stocks = searchDao.stockSearch(stx);
			List<NewsVO> news = searchDao.newsSearch(stx);
			
			if(stocks.isEmpty() == false) {
				for(DashListDTO tmps : stocks) { 
					SearchDTO newSearch = new SearchDTO();
					newSearch.setStockCnt(stocks.size());
					newSearch.setCode(tmps.getCode());
					newSearch.setFlt(tmps.getFlt());
					newSearch.setPrice(tmps.getPrice());
					newSearch.setTitle(tmps.getStockName());
					newSearch.setType("stock");
					list.add(newSearch);
				}
			} 
			if(news.isEmpty() == false) {
				for(NewsVO tmps : news) { 
					SearchDTO newSearch = new SearchDTO();
					newSearch.setNewsCnt(news.size());
					newSearch.setCode(Integer.toString(tmps.getNe_no()));
					newSearch.setTitle(tmps.getNe_title());
					newSearch.setDate(formatter.format(tmps.getNe_datetime()));
					newSearch.setNewspaper(tmps.getNp_name());
					newSearch.setType("news");
					list.add(newSearch);
				}
			}
			SearchDTO newSearch = new SearchDTO();
			int newsCnt = 0, stockCnt = 0;
			if(news.isEmpty() == false) {
				newsCnt = news.size();
			}
			if (stocks.isEmpty() == false) {
				stockCnt = stocks.size();
			}
			newSearch.setNewsCnt(stockCnt);
			newSearch.setStockCnt(newsCnt);
			list.add(newSearch);
		}
		
		return list;
	}

	public List<ComRankDTO> getCommunityRank() {
		List<ComRankDTO> list = searchDao.getCommunityRank();
		
		for(ComRankDTO tmp : list) {
			tmp.setFlt(searchDao.getFlt(tmp.getCode()));
		}
		
		return list;
	}
	
}
