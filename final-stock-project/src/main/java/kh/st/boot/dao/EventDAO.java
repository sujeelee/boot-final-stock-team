package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.dto.EventDTO;

public interface EventDAO {

    List<EventDTO> getEventList(String eventStatus);
    
}
