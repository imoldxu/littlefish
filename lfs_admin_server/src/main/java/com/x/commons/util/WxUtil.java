package com.x.commons.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.SneakyThrows;
import net.bytebuddy.asm.Advice.Thrown;

public class WxUtil {
	
	public static String convertAvatar(String headerImgURL) {
		if (null != headerImgURL && !headerImgURL.isEmpty()) {
			// 去掉微信图片为0的标号
			headerImgURL = headerImgURL.substring(0, headerImgURL.length() - 3);
			// 设置微信图片为64的标号
			headerImgURL = headerImgURL + "64";
		}
		return headerImgURL;
	}

	/**
	 * 去掉微信的昵称中符号
	 */
//	public static String converWxNick(String wxnick) {
//		String regEx = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
//		Pattern p = Pattern.compile(regEx);
//		Matcher m = p.matcher(wxnick);
//		return m.replaceAll("").trim();
//	}
	
	/**
	 * 微信昵称转码
	 * @param nickName
	 * @return
	 */
	@SneakyThrows
	public static String converWxNick(String nickName) {
		String ret = new String(nickName.getBytes("ISO-8859-1"), "UTF-8");
		return ret;
	}
	
	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}
	
}
