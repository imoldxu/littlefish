package com.x.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
 
/**
 * 身份证验证的工具（支持5位或18位省份证）
 * 身份证号码结构：
 * 17位数字和1位校验码：6位地址码数字，8位生日数字，3位出生时间顺序号，1位校验码。
 * 地址码（前6位）：表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。
 * 出生日期码，（第七位 至十四位）：表示编码对象出生年、月、日，按GB按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 顺序码（第十五位至十七位）：表示在同一地址码所标示的区域范围内，对同年、同月、同日出生的人编订的顺序号，
 * 顺序码的奇数分配给男性，偶数分配给女性。 
 * 校验码（第十八位数）：
 * 十七位数字本体码加权求和公式 s = sum(Ai*Wi), i = 0,,16，先对前17位数字的权求和；   
 *  Ai:表示第i位置上的身份证号码数字值.Wi:表示第i位置上的加权因.Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2；
 * 计算模 Y = mod(S, 11) 
 * 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2 
 */
public class IdCardUtil {
    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();
    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }
     
    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 
        5, 8, 4, 2};
     
    /**
     * 身份证验证
     *@param s  号码内容
     *@return 是否有效 null和"" 都是false 
     */
    public static boolean isIDCard(String certNo){
    	if(certNo == null || certNo.length() != 18)
            return false;
        final char[] cs = certNo.toUpperCase().toCharArray();
        //校验位数
        int power = 0;
        for(int i=0; i<cs.length; i++){
            if(i==cs.length-1 && cs[i] == 'X')
                break;//最后一位可以 是X或x
            if(cs[i]<'0' || cs[i]>'9')
                return false;
            if(i < cs.length -1){
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }
         
        //校验区位码
//        if(!zoneNum.containsKey(Integer.valueOf(certNo.substring(0,2)))){
//            return false;
//        }
         
        //校验年份
        String year = certNo.length() == 15 ? getIdcardCalendar() + certNo.substring(6,8) :certNo.substring(6, 10);
         
        final int iyear = Integer.parseInt(year);
        if(iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
            return false;//1900年的PASS，超过今年的PASS
         
        //校验月份
        String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10,12);
        final int imonth = Integer.parseInt(month);
        if(imonth <1 || imonth >12){
            return false;
        }
         
        //校验天数      
        String day = certNo.length() ==15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if(iday < 1 || iday > 31)
            return false;       
        
        return cs[cs.length -1 ] == PARITYBIT[power % 11];
    }
     
    private static int getIdcardCalendar() {        
         GregorianCalendar curDay = new GregorianCalendar();
         int curYear = curDay.get(Calendar.YEAR);
         int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));          
         return  year2bit;
    }     
    
    public static String getBirthday(String certNo){
    	//校验年份
        String year = certNo.substring(6, 10);
        //校验月份
        String month = certNo.substring(10,12);         
        //校验天数      
        String day =  certNo.substring(12, 14);
        
        return year+"-"+month+"-"+day;
    }
    
    public static String getSex(String certNo){
    	String sexTag = certNo.substring(14, 17);
    	int sexNum = Integer.parseInt(sexTag);
    	int sex = sexNum%2;
    	if(sex==1){
    		return "男";
    	}else{
    		return "女";
    	}
    }
    
    public static int getAge(String certNo) throws ParseException{
    	String birthday = getBirthday(certNo);
  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date birthDay = sdf.parse(birthday);
    	
    	return getAge(birthDay);
    }
    
  //由出生日期获得年龄 
    /**
	 * 根据出生日期计算年龄、不足1岁精确到几月或几天
	 * @param birthDayStr
	 * @return
	 * @throws ParseException
	 */
	public static String getAgeStr(Date birthDay) {
		Calendar now = Calendar.getInstance();
        long nowmillSeconds = now.getTimeInMillis();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDay);
        long birmillSeconds = birth.getTimeInMillis();
        Calendar difference = Calendar.getInstance();
        long millis = nowmillSeconds - birmillSeconds;
        difference.setTimeInMillis(millis);
        int year = difference.get(Calendar.YEAR);
        int month = difference.get(Calendar.MONTH);
        int day = difference.get(Calendar.DAY_OF_MONTH);
        //int hour = difference.get(Calendar.HOUR_OF_DAY);
        if (year > 1970) {
            return year - 1970 + "岁";
        } else if (month > Calendar.JANUARY) {
            return month - Calendar.JANUARY + "个月";
        } else if (day > 1) {
            return day - 1 + "天";
        }else{
            return "1天";
        }
    }
	
	//获取年龄，单位是岁
    public static int getAge(Date birthDay) {  
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
            throw new IllegalArgumentException(  
                    "The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }
    
    public static void main(String[] args) {
    	boolean idCard = isIDCard("652323198705263522");
    	System.out.println(idCard);
	}
    
}
