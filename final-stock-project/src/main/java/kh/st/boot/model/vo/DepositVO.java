package kh.st.boot.model.vo;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositVO {
	
	private int de_no;				// 예치금 기본키
	private String de_content;		// 예치금 거래내역
	private Date de_datetime;		// 예치금 내역 날짜
	private String de_stock_code;	// 거래한 주식 코드
	private String mb_id;			// 회원 아이디
}
