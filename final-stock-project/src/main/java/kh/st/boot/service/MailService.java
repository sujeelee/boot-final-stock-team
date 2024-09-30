package kh.st.boot.service;


public interface MailService {

	boolean setMailCode(String evc_email, int code);

    boolean checkMailCode(String evc_email, int code);
} 