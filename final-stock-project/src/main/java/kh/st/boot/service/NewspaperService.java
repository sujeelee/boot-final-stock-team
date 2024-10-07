package kh.st.boot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.st.boot.controller.NewspaperVO;
import kh.st.boot.dao.NewspaperDAO;
import kh.st.boot.model.dto.NewspaperDTO;
import kh.st.boot.model.vo.NewsPaperVO;

@Service
public class NewspaperService {

	@Autowired
	private NewspaperDAO newspaperDAO;
	
	public List<NewsPaperVO> getAllNewspapers() {
		return newspaperDAO.selectAllNewspapers();
	}

	
    public boolean addNewspaper(String np_name, byte np_use) {
    	//왜 이렇게 했는지 분석 해서 주석 달기
    	NewsPaperVO oldNews = newspaperDAO.getNewsOne(np_name);
    	if(oldNews != null) {
    		return false;
    	}
    	newspaperDAO.insertNewspaper(np_name,np_use);
        return true;
    }
	
	/*	
	 * 	 정리 
	 * 	
	 *		컨트롤러에서 boolean res 로 일시킴				false 면 메세지 보냄 
	 *		
	 *		다오의 getNewsOne 실행시킨 후 결과를 NewsPaperVO oldNews 로 받음 
	 *		
	 *		getNewsOne 에서 같은이름이 있는지 메퍼로 검색 하고 결과를 NewspaperVO 로 보냄
	 *
	 *				#{} 을 통해 입력값 넣고 같은 np_name  검색  			오류 대비  limit1 로 하나만 가져옴 
	 *				
	 *			
	 *		NewsPaperVO oldNews 가 비어있다면 등록 기능 정상실행됨 
	 * 		아니라면 메세지 출력 
	 * 
	 * 	
	 * 
	 *  
	 */
	
	
	

    
    
    
    
    
	
	 public void updateNewspaper(NewspaperDTO newspaperDTO) {
	        newspaperDAO.updateNewspaper(newspaperDTO);
	    }
	 
	public void deleteNewspaper(NewspaperVO  newspaperVO ) {
		newspaperVO .deleteNewspaper(newspaperVO );
	}

	
	public List<NewsPaperVO> searchNewspapers(String np_name, byte np_use, int np_no) {
		List<NewsPaperVO> allNewspapers = newspaperDAO.selectAllNewspapers(); // 모든 신문사 가져오기
		List<NewsPaperVO> filteredNewspapers = new ArrayList<>();
	
		for (NewsPaperVO newspaper : allNewspapers) {
			boolean matchesName = (np_name == null || np_name.isEmpty() || newspaper.getNp_name().contains(np_name));
			// 여기서 이름을 판별해서 matchesName 안에 넣는데 
			boolean matchesStatus = (np_use == 1 && newspaper.getNp_use() == 1) || 
                    				(np_use == 0 && newspaper.getNp_use() == 0); 
			
			if (matchesName && matchesStatus) {
				filteredNewspapers.add(newspaper);
			}
		}

		return filteredNewspapers; // 필터링된 신문사 목록 반환
	}



	
}
