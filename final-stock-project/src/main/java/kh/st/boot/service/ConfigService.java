package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdminDAO;
import kh.st.boot.dao.StockDAO;
import kh.st.boot.model.dto.DashListDTO;
import kh.st.boot.model.dto.HotStockDTO;
import kh.st.boot.model.vo.AdminVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfigService {
	
	private AdminDAO adminDao;
	private StockDAO stockDao;
	
	public AdminVO getConfig() {
		return adminDao.selectAdmin();
	}

	public List<HotStockDTO> getHotStockList() {
		return stockDao.getHotStockList();
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
	
}
