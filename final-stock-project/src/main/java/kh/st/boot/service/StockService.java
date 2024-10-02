package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.StockDAO;
import kh.st.boot.model.vo.StockPriceVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.pagination.Criteria;
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
	
	public List<StockVO> getCompanyList(String type, StockCriteria cri) {
		return stockDao.getCompanyList(type, cri);
	}
	
	public StockPriceVO getStockPrice(String si_date, String st_code) {
		StockPriceVO price = stockDao.getStockPrice(si_date, st_code);
		return price;
	}
	
	public boolean insertPrice(StockPriceVO price) {
		StockPriceVO oldPrice = getStockPrice(price.getSi_date(), price.getSt_code());
		if(oldPrice == null) {
			return stockDao.insertStockPrice(price);
		} else {
			return false;
		}
	}
	
	public void updateCompanyType(String st_code, String st_type, String st_status) {
		StockVO stock = getCompanyOne(st_code);
		
		if(stock == null) return;
		
		stockDao.companyType(st_code, st_type, st_status);
	}

	public PageMaker getPageMaker(StockCriteria cri) {
		int count = stockDao.getCount(cri);
		return new PageMaker(10, cri, count);
	}
	
	public List<StockPriceVO> getStockInfoList(String st_code) {
		List<StockPriceVO> list = stockDao.getStockInfoList(st_code);
		return list;
	}

	public int getCompanyStockCount(String st_code) {
		StockVO stock = getCompanyOne(st_code);
		
		if(stock == null ) return 0;
		
		return stockDao.getCountStockPrice(st_code);
	}
}
