package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kh.st.boot.model.vo.AdmApprovalVO;

@Mapper
public interface AdminApprovalDAO {

	List<AdmApprovalVO> nullSelectAll();

	List<AdmApprovalVO> updateApprove(int mp_no, String mp_yn);

}
