package kh.st.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kh.st.boot.model.vo.ReservationVO;

public interface OrderDAO {
	List<ReservationVO> getReservation(@Param("code")String st_code, @Param("mb_id")String mb_id);
}
