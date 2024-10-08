package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.vo.StockPriceVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.pagination.StockCriteria;

public interface StockDAO {

	StockVO getStockCompany(@Param("st_code")String st_code);
	
	void companyType(@Param("st_code")String st_code, @Param("st_type")String st_type);

	void insertStockCompany(@Param("st")StockVO newStock);
	
	List<StockVO> getCompanyList();

	StockPriceVO getStockPrice(@Param("si_date")String si_date, @Param("st_code")String st_code);

	void insertStockPrice(@Param("si")StockPriceVO price);

}
