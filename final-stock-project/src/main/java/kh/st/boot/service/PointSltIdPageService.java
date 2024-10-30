package kh.st.boot.service;

import java.util.ArrayList;
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

	public List<AdmDaycheckVO> sltAllDay() {

		List<AdmDaycheckVO> allDay = admDaycheckDAO.AllSelect();

		for (AdmDaycheckVO dayNum : allDay) {
			String dcDays = dayNum.getDc_days();

			if (dcDays != null) {
				int count = countOnes(dcDays); // dc_days의 1 개수 세기
				dayNum.setCountDay(count); // countList에 추가
			}
		}
		return allDay;
	}

	public List<AdmDaycheckVO> sltOneDay(String mb_id) {

		List<AdmDaycheckVO> allDay = admDaycheckDAO.OneSelect(mb_id);

		for (AdmDaycheckVO dayNum : allDay) {
			String dcDays = dayNum.getDc_days();

			if (dcDays != null) {
				int count = countOnes(dcDays); // dc_days의 1 개수 세기
				dayNum.setCountDay(count); // countList에 추가
			}
		}
		return allDay;
	}

	// '1'제거한 길이를 원본에서 빼서 1이 몇개인지 확인
	private int countOnes(String dcDays) {
		return (dcDays.length() - dcDays.replace("1", "").length());
	}

}
