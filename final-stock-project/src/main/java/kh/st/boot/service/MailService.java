package kh.st.boot.service;


public interface MailService {

	boolean setMailCode(String ec_email, int code);

    boolean checkMailCode(String ec_email, int code);
} 