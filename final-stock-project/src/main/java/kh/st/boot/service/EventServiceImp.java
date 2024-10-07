package kh.st.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.EventDAO;
import kh.st.boot.model.dto.EventDTO;
import kh.st.boot.model.vo.EventVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImp implements EventService {

    private EventDAO eventDao;

    @Override
    public List<EventDTO> getEventList(String eventStatus) {
        return eventDao.getEventList(eventStatus);
    }

    @Override
    public EventVO getEvent(String eventStatus, int ev_no) {
        return eventDao.getEvent(eventStatus, ev_no);
    }



    
}
