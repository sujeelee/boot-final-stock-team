package kh.st.boot.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	public static String getLastWeek() {
		LocalDate now = LocalDate.now();
		LocalDate lastWeek = now.minusWeeks(1);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return lastWeek.format(format);
	}
}
