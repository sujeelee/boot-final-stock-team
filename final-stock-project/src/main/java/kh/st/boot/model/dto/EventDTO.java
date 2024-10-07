package kh.st.boot.model.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDTO {
    //상세이벤트 이전에 이벤트 페이지에서 사용할 예정입니다.
    private int ev_no;
    private String ev_title;
    private Date ev_start;
    private Date ev_end;
    private String ev_status;// Opening, Endding, resUser
    private String fi_path; //파일경로가 저장될 위치
    
}
