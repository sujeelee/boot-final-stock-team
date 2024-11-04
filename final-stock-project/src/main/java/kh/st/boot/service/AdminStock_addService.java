package kh.st.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import kh.st.boot.dao.AdminStock_addDAO;
import kh.st.boot.model.vo.AdminStock_addVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminStock_addService {

	@Autowired
	private AdminStock_addDAO adminStock_addDAO;

	public List<AdminStock_addVO> nullSelect() {
		System.out.println("서비스 진입");
		return adminStock_addDAO.nullSelectAll();
	}

	public AdminStock_addVO Select(@RequestParam int sa_qty, @RequestParam String mb_id) {
		return adminStock_addDAO.SelectAll(sa_qty, mb_id);
	}

	public void update(int sa_no, String sa_yn, String sa_feedback, int sa_qty,String mb_id) {
		System.out.println(sa_qty);
		adminStock_addDAO.updateAll(sa_no, sa_yn, sa_feedback);
		adminStock_addDAO.styUpdate(sa_qty,mb_id);
		
	}

	public List<AdminStock_addVO> search(String mb_id) {
		
		return adminStock_addDAO.search(mb_id);
	}

		
	}

