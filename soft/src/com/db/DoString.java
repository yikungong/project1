package com.db;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoString {
	public static String HTMLChange(String source){
		String changeStr="";
		changeStr=source.replaceAll("&","&amp;");				//转换字符串中的“&”符号
		changeStr=changeStr.replaceAll(" ","&nbsp;");				//转换字符串中的空格
		changeStr=changeStr.replaceAll("<","&lt;");				//转换字符串中的“<”符号
		changeStr=changeStr.replaceAll(">","&gt;");				//转换字符串中的“>”符号
		changeStr=changeStr.replaceAll("\r\n","<br>");			//转换字符串中的回车换行
		return changeStr;
	}

	public static String dateTimeChange(Date source){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String changeTime=format.format(source);
		return changeTime;
	}

}