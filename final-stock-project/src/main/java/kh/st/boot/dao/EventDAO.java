package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.dto.EventDTO;
import kh.st.boot.model.vo.EventVO;

public interface EventDAO {

    List<EventDTO> getEventList(String eventStatus);

    EventVO getEvent(String eventStatus, int ev_no);
    
}
