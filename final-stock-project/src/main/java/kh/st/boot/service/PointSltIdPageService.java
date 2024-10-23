package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.dao.AdmDaycheckDAO;
import kh.st.boot.model.vo.AdmDaycheckVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PointSltIdPageService {

	@Autowired
	private AdmDaycheckDAO admDaycheckDAO;

	public List<AdmDaycheckVO> sltAllPoint(Criteria cri) {

		return admDaycheckDAO.AllSelect(cri);
	}

	public List<AdmDaycheckVO> sltOnePoint(Criteria cri) {
		return admDaycheckDAO.OneSelect();
	}

	public List<Integer> sltAllDay(Criteria cri) {
		List<AdmDaycheckVO> allDay = admDaycheckDAO.AllSelect(cri); // 모든 정보 가져오기
		List<Integer> filteredallDay = new ArrayList<>(); // 필터링 된 값을 넣을 리스트를 만들어줍니다

		for (AdmDaycheckVO filtering : allDay) {
			String dayData = filtering.getDc_days(); // vo 타입 allDay 에서 문자열을 가져옵니다

			if (dayData != null) {
				String[] values = dayData.split(","); // 문자열을 , 을 기준으로 배열로 만듭니다
				int countOfOnes = 0; // for 문에서 카운트할 변수

				for (String value : values) { // 문자열로 만든 배열에서
					if (value.trim().equals("1")) { // 각 값이 '1'인지 체크하고
						countOfOnes++; // 카운트합니다
					}

				}
				 // 1의 개수를 filteredallDay에 추가
	            filteredallDay.add(countOfOnes);
			}
		}
		System.out.println(filteredallDay+" 응애 ");
		return filteredallDay;					//  카운트 다 끝난 값을 컨트롤러로 리턴합니다
	}

	
	
	
	
	
	
	
	
	
	
	
	public PageMaker getPageMaker(Criteria cri) {
		int count = admDaycheckDAO.selectCountList(cri);
		return new PageMaker(10, cri, count);
	}

	public List<AdmDaycheckVO> getDaychCount(String mb_id) {
		return admDaycheckDAO.daycouunt(mb_id);
	}

}