package com.x.commons.util;

import java.util.Calendar;
import java.util.Date;

public class MongoDbUtil {

	/**
              * 计算得到MongoDB存储的日期，（默认情况下mongo中存储的是标准的时间，中国时间是东八区，存在mongo中少8小时，所以增加8小时）
     * http://www.iteye.com/problems/88507
     * 
     * @author: Gao Peng
     * @date: 2016年5月4日 上午9:26:23
     * @param: @param
     *             date
     * @param: @return
     * @return: Date
     */
    public static Date getChinaMongoDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, 8);
        return ca.getTime();
    }
	
}
