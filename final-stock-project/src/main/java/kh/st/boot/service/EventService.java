package kh.st.boot.service;

import java.util.List;

import kh.st.boot.model.dto.EventDTO;
import kh.st.boot.model.vo.EventVO;

public interface EventService {

    List<EventDTO> getEventList(String eventStatus);

    EventVO getEvent(String eventStatus, int ev_no);
    
}
