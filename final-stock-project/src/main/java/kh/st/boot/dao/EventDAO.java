package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.dto.EventDTO;
import kh.st.boot.model.vo.EventVO;
import kh.st.boot.model.vo.FileVO;

public interface EventDAO {

    List<EventDTO> getEventList(String eventStatus);

    EventVO getEvent(String eventStatus, int ev_no);

    boolean setEvent(EventVO event);

    EventVO getEventToImportAFile();

    void setEventFile(FileVO fileVo);

    boolean updateEventDateAndStatus();

    List<EventDTO> getEventPostList(String eventStatus);

    EventVO getEventPost(int ev_no);

    boolean deleteEventPost(int ev_no);

    FileVO getFileByEventNumber(int ev_no);

    boolean updateEvent(EventVO event);

    boolean deleteEventThumbnailFile(int ev_no);

    boolean setNewCalenderEvent(String mb_id, int[] checkList);
    
    boolean setCalenderEvent(String mb_id, int[] checkList);
    
}
