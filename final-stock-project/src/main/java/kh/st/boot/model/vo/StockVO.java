package kh.st.boot.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockVO { 
	private String st_code; //주식 주 고유 번호
	private String st_name; //주식 주 이름
	private String st_qty; //총 주식 개수(발행주식수)
	private int board_cnt; //이 주식의 커뮤니티 글 몇개 써있음?
	private String st_category; //주식 업종 분류인데 이걸 가져오는 API가 없음 공공데이터 개식히....
	private String st_status; //상장폐지등 주식 상태
	private String st_issue; //청산간주등의 정보가 있는 주식발행사 명칭
	private String st_type; //코스닥, 코스피 등 어떤 주식인지
	
	private int st_price_cnt; //DB에는 없는데 주식 가격 정보 몇개인지 보는 용으로 사용
}
