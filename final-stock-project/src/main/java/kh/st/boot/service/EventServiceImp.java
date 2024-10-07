package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.st.boot.dao.EventDAO;
import kh.st.boot.model.dto.EventDTO;
import kh.st.boot.model.vo.EventVO;
import kh.st.boot.model.vo.FileVO;
import kh.st.boot.utils.UploadFileUtils;

@Service
public class EventServiceImp implements EventService {

    @Autowired
    private EventDAO eventDao;
    
    @Value("${file.upload-dir}")
	String uploadPath;

    @Override
    public List<EventDTO> getEventList(String eventStatus) {
        return eventDao.getEventList(eventStatus);
    }

    @Override
    public EventVO getEvent(String eventStatus, int ev_no) {
        return eventDao.getEvent(eventStatus, ev_no);
    }

    @Override
    public boolean setEvent(EventVO event, MultipartFile file) {
        if (event == null) {
            return false;
        }

        if (event.getEv_title() == null || event.getEv_title().trim().length() == 0) {
            return false;
        }

        boolean res = eventDao.setEvent(event);

        if (res) {
            EventVO tmpEv = eventDao.getEventToImportAFile();//방금 set한 event를 가져온다.
            uploadFile(file, tmpEv.getEv_no());
        }
        return false;
    }

	private void uploadFile(MultipartFile file, int ev_no) {
		if(file == null || file.getOriginalFilename().trim().length() == 0) {
			return;
		}
		// 첨부 파일을 서버에 업로드
		String fi_org_name = file.getOriginalFilename();
		try {
			String fi_path = UploadFileUtils.uploadFile(uploadPath, fi_org_name, file.getBytes());
			// 업로드한 정보를 DB에 추가
			FileVO fileVo = new FileVO(fi_path, fi_org_name, ev_no, "event");
			eventDao.setEventFile(fileVo);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public boolean updateEventDateAndStatus() {

        return eventDao.updateEventDateAndStatus();
    }

    
}
