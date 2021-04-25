package com.x.commons.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

/**
   * 实现金额的转换，支持fastJson的JSONField来配置格式化转换
 * @author 老徐
 *
 */
public class MoneyUtil implements ObjectSerializer, ObjectDeserializer{

	 /**金额为分的格式 */    
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";    
    
	@Override
	public Long deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		String value = parser.parseObject(String.class);
        return changeY2F(value);
	}

	@Override
	public int getFastMatchToken() {
		// TODO Auto-generated method stub
		return 0;
	}
    
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		Integer amount = (Integer) object;
        String text = changeF2Y(amount);
        serializer.write(text);
	}  
    
    /**   
     * 将分为单位的转换为元并返回金额格式的字符串 （除100）  
     *   
     * @param amount  
     * @return  
     * @throws Exception   
     */    
    public static String changeF2Y(Integer amount){    
        if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {    
            return "";    
        }    
            
        int flag = 0;    
        String amString = amount.toString();    
        if(amString.charAt(0)=='-'){    
            flag = 1;    
            amString = amString.substring(1);    
        }    
        StringBuffer result = new StringBuffer();    
        if(amString.length()==1){    
            result.append("0.0").append(amString);    
        }else if(amString.length() == 2){    
            result.append("0.").append(amString);    
        }else{    
            String intString = amString.substring(0,amString.length()-2);    
            for(int i=1; i<=intString.length();i++){    
                if( (i-1)%3 == 0 && i !=1){    
                    result.append(",");    
                }    
                result.append(intString.substring(intString.length()-i,intString.length()-i+1));    
            }    
            result.reverse().append(".").append(amString.substring(amString.length()-2));    
        }    
        if(flag == 1){    
            return "-"+result.toString();    
        }else{    
            return result.toString();    
        }    
    }    
        
    /**  
     * 将分为单位的转换为元 （除100）  
     *   
     * @param amount  
     * @return  
     * @throws Exception   
     */    
    public static String changeF2Y(String amount) throws Exception{    
        if(!amount.matches(CURRENCY_FEN_REGEX)) {    
            throw new Exception("金额格式有误");    
        }    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();    
    }    
        
    /**   
     * 将元为单位的转换为分 （乘100）  
     *   
     * @param amount  
     * @return  
     */    
    public static Long changeY2F(Integer amount){    
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).longValue();    
    }    
        
    /**   
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额  
     *   
     * @param amount  
     * @return  
     */    
    public static Long changeY2F(String amount){    
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
        int index = currency.indexOf(".");    
        int length = currency.length();    
        Long amLong = 0l;    
        if(index == -1){    
            amLong = Long.valueOf(currency+"00");    
        }else if(length - index >= 3){    
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
        }else{    
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
        }    
        return amLong;    
    }
	
}
