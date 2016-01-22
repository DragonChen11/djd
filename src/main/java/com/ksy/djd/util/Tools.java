package com.ksy.djd.util;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.widget.EditText;

public class Tools {
	
	public static void setSelection(EditText... ets){
		try {
			for(EditText et : ets){
				if(et.isFocused())
					et.setSelection(et.length());
			}
		} catch (Exception e) {
			printStackTrace("Tools", e);
		}
	}
	
	/**
	 * 输出异常信息
	 * @param c
	 * @param e
	 */
	public static void printStackTrace(String tag, Exception e){
		if(!(e instanceof UnknownHostException))
			Log.e(tag, e.getMessage(), e);
	}
	
	
	/**
	 * 判断是否是手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str){
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(str);
		if(matcher.matches()){
			return true;
		}else{
			return false;
		}

	}

}
