package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.st.boot.model.vo.AdmApprovalVO;
import kh.st.boot.pagination.Criteria;

@Mapper
public interface AdminApprovalDAO {

	List<AdmApprovalVO> SelectAll(Criteria cri);

	List<AdmApprovalVO> updateApprove(int mb_no, String mp_yn);

	void nyUPDATE(int mb_no, String mp_yn);

	void newsInsert(int mb_no, String mp_company);

	void stockInsert(int mb_no, String mp_company);

	int selectCountList(Criteria cri);

	List<AdmApprovalVO> searchApproval(String mp_company);

	void stockLvUp(int mb_no);

	void newsLvUp(int mb_no);

}
