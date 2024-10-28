package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdmDaycheckDAO;
import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.vo.AdmDaycheckVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor	
public class PointSltIdPageService {
	
	
	@Autowired
	private AdmDaycheckDAO admDaycheckDAO;
	
	
	
	public List<AdmDaycheckVO> sltAllPoint() {
	
		return admDaycheckDAO.AllSelect();
	}
	// 아이디 검색해서 조회
	// 아이디 일부만 검색해도 나오게 하고싶으면 여기서 처리
	// 전체 입력할꺼여서 쿼리로 처리하려고 함 
	public List<AdmDaycheckVO> sltOnePoint(String mb_id) {
	
		return admDaycheckDAO.OneSelect(mb_id);
	}
	
	
	
	


}
