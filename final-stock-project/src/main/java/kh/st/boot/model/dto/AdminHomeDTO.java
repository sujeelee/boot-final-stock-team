package kh.st.boot.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminHomeDTO {
	
	private String cf_title;
	private String cf_info;
	private String cf_privacy;
	private String cf_noemail;
	private String cf_tel;
	private int cf_zip;
	private String cf_addr;
	private String cf_addr2;
	private String cf_fax;
	private String cf_email;
	private String cf_owner_name;
	private int cf_day_point;
	private byte cf_check_use;
	private int cf_od_point;
	private int cf_percent;
	
}


