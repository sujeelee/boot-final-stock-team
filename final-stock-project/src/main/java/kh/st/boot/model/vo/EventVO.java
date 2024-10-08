package kh.st.boot.model.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventVO {

    private int ev_no;
    private String ev_title;
    private String ev_content;
    private int ev_start_level;
    private int ev_end_level;
    private int ev_point;
    private Date ev_datetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ev_start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ev_end;
    private String ev_status;
    private int ev_cnt;
    

}
