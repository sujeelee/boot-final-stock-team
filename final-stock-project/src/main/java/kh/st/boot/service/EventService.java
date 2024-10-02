package kh.st.boot.service;

import java.util.List;

import kh.st.boot.model.dto.EventDTO;

public interface EventService {

    List<EventDTO> getEventList(String eventStatus);
    
}
