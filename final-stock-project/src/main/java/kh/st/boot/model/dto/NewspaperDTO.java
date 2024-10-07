package kh.st.boot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewspaperDTO {
	private int np_no; // 신문사 번호
	private String np_name; // 신문사 이름
	private int np_use; // 사용 여부
}
