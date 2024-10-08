package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.StockDAO;
import kh.st.boot.model.vo.StockPriceVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.pagination.StockCriteria;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class StockService {
	private StockDAO stockDao;
	
	public StockVO getCompanyOne(String st_code) {
		StockVO stock = stockDao.getStockCompany(st_code);
		return stock;
	}
	
	public void insertStockCompany(StockVO newStock) {
		StockVO stock = getCompanyOne(newStock.getSt_code());
		if(stock != null) {
			return;
		} else {
			if(newStock.getSt_status() != "") {
				newStock.setSt_status("상장폐지");
			} else {
				newStock.setSt_status("정상");
			}
			stockDao.insertStockCompany(newStock);
		}
	}
	
	public List<StockVO> getCompanyList() {
		return stockDao.getCompanyList();
	}
	
	public void insertPrice(StockPriceVO price) {
		StockPriceVO oldPrice = stockDao.getStockPrice(price.getSi_date(), price.getSt_code());
		if(oldPrice == null) {
			stockDao.insertStockPrice(price);
		} else {
			return;
		}
	}
	
	public void updateCompanyType(String st_code, String st_type) {
		StockVO stock = getCompanyOne(st_code);
		 
		if(stock == null) return;
		
		stockDao.companyType(st_code, st_type);
	}
}
