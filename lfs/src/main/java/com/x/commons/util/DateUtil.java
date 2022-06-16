package com.x.commons.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date getDayStartTime(Date date) {
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTime(date);
		dayStart.set(Calendar.HOUR_OF_DAY, 0);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		return dayStart.getTime();
	}

	public static Date getDayEndTime(Date date) {
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(date);
		dayEnd.set(Calendar.HOUR_OF_DAY, 23);
		dayEnd.set(Calendar.MINUTE, 59);
		dayEnd.set(Calendar.SECOND, 59);
		return dayEnd.getTime();
	}

}
