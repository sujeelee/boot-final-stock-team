package kh.st.boot.dao;

import java.util.List;

import kh.st.boot.model.vo.AdminStock_addVO;

public interface AdminStock_addDAO {
	
	List<AdminStock_addVO> nullSelectAll();

	AdminStock_addVO SelectAll(int sa_qty, String mb_id);

}
