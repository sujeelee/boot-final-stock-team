package kh.st.boot.model.vo;

import java.time.LocalDateTime;

public class admOrderPageVO {
	
	private String od_id;
	private String od_name;
	private String mb_id;
	private int od_price;
	private int od_point;
	private LocalDateTime od_date;
	// 이렇게 메핑해주면 DATETIME 객체로 넘길수 있음 
	private String od_status;
	private String od_st_code;
	private String od_st_name;
	private int od_qty;
	private int od_st_price;
}
