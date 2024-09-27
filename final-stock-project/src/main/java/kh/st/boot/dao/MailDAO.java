package kh.st.boot.dao;

import kh.st.boot.model.vo.MailVO;

public interface MailDAO {

	boolean setMailCode(String ec_email, int code);
	
	MailVO getMailCode(String ec_email);
    
}
