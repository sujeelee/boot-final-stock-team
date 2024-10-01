package kh.st.boot.service;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.EventDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImp implements EventService {

    private EventDAO eventDao;

    
}
