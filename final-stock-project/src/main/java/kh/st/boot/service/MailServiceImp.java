package kh.st.boot.service;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.MailDAO;
import kh.st.boot.model.vo.MailVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailServiceImp implements MailService{

    private MailDAO mailDao;

	@Override
	public boolean setMailCode(String ec_email, int code) {
		if (ec_email == null) {
            return false;
        }
		return mailDao.setMailCode(ec_email, code);
	}

	@Override
	public boolean checkMailCode(String ec_email, int code) {
		MailVO mailVO = mailDao.getMailCode(ec_email);
		if (mailVO.getEvc_code() == code) {
			return true;
		}
		return false;
	}
    
}
