package kh.st.boot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.StockDAO;
import kh.st.boot.model.vo.StockJisuVO;
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
	
	public List<StockVO> getCompanyList(String type, StockCriteria cri) {
		if(cri.getMrk() != null) {
			return stockDao.getCompanyListMrk(type, cri);
		}
		return stockDao.getCompanyList(type, cri);
	}
	
	public StockPriceVO getStockPrice(String si_date, String st_code) {
		StockPriceVO price;
		if(si_date == null) {
			price = stockDao.getStockPriceOne(st_code);
		} else {
			price = stockDao.getStockPrice(si_date, st_code);
		}
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
		int count = 0;
		if(cri.getMrk() != null) {
			count = stockDao.getCountMrk(cri);
		} else {
			count = stockDao.getCount(cri);
		}
		return new PageMaker(10, cri, count);
	}
	
	public List<StockPriceVO> getStockInfoList(String st_code) {
		List<StockPriceVO> list = stockDao.getStockInfoList(st_code);
		return list;
	}
	
	public List<StockPriceVO> getStockInfoListDate(String st_code, String type) {
		String to_date = null, from_date = null;
		
		if(type.equals("3month")) {
			// 년월 형식으로 변환 (yyyyMM)
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
			// 2개월 전 날짜
	        to_date = LocalDate.now().minusMonths(2).format(formatter);
	        // 1개월 후 날짜
	        from_date = LocalDate.now().plusMonths(1).format(formatter);
		    
		} else if(type.equals("year")) {
			// 년형식으로 변환 (yyyy)
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
			// 현재년도
	        to_date = LocalDate.now().format(formatter);
	        // 1년후
	        from_date = LocalDate.now().plusYears(1).format(formatter);
		} else if(type.equals("month")) {
			// 년월 형식으로 변환 (yyyyMM)
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
			// 오늘 날짜
	        to_date = LocalDate.now().format(formatter);
	        // 1개월 후 날짜
	        from_date = LocalDate.now().plusMonths(1).format(formatter);
		} 
		
		/*else if(type.equals("7days")) {
			// 년월일 형식으로 변환 (yyyyMMdd)
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			// 7일 전 날짜
	        to_date = LocalDate.now().minusDays(7).format(formatter);
	        // 오늘 날짜로부터 1일 후 
	        from_date = LocalDate.now().plusDays(1).format(formatter);
		} */
		
		List<StockPriceVO> list = stockDao.getStockInfoListDate(st_code, to_date, from_date);
		return list;
	}

	public int getCompanyStockCount(String st_code) {
		StockVO stock = getCompanyOne(st_code);
		
		if(stock == null ) return 0;
		
		return stockDao.getCountStockPrice(st_code);
	}

	public StockJisuVO getOldJisu(String date, String type) {
		return stockDao.getOldJisu(date, type);
	}

	public boolean insertStockJisu(StockJisuVO jisu) {
		return stockDao.insertStockJisu(jisu);
	}
}
