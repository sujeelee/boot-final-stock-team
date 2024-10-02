package kh.st.boot.service;

import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.dto.NewspaperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewspaperService {

    @Autowired
    private NewspaperDAO newspaperDAO;

    public List<NewspaperDTO> getAllNewspapers() {
        return newspaperDAO.selectAllNewspapers();
    }

    public void addNewspaper(NewspaperDTO newspaperDTO) {
        newspaperDAO.insertNewspaper(newspaperDTO);
    }

    public void updateNewspaper(NewspaperDTO newspaperDTO) {
        newspaperDAO.updateNewspaper(newspaperDTO);
    }

    public void deleteNewspaper(int np_no) {
        newspaperDAO.deleteNewspaper(np_no);
    }

    public List<NewspaperDTO> searchNewspapers(String name, String status) {
        List<NewspaperDTO> allNewspapers = newspaperDAO.selectAllNewspapers(); // 모든 신문사 가져오기
        List<NewspaperDTO> filteredNewspapers = new ArrayList<>();

        for (NewspaperDTO newspaper : allNewspapers) {
            boolean matchesName = (name == null || name.isEmpty() || newspaper.getNp_name().contains(name));
            boolean matchesStatus = (status == null || status.isEmpty() || 
                (status.equals("active") && newspaper.getNp_use() == 1) || 
                (status.equals("inactive") && newspaper.getNp_use() == 0));

            if (matchesName && matchesStatus) {
                filteredNewspapers.add(newspaper);
            }
        }

        return filteredNewspapers; // 필터링된 신문사 목록 반환
    }
}
