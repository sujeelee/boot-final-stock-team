package kh.st.boot.service;

import org.springframework.stereotype.Service;

import kh.st.boot.dao.CommunityDAO;
import kh.st.boot.dao.MemberDAO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunityService {
	
	private MemberDAO memberDao;
	private CommunityDAO communityDAO;
}
