package com.db;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoString {
	public static String HTMLChange(String source){
		String changeStr="";
		changeStr=source.replaceAll("&","&amp;");				//ת���ַ����еġ�&������
		changeStr=changeStr.replaceAll(" ","&nbsp;");				//ת���ַ����еĿո�
		changeStr=changeStr.replaceAll("<","&lt;");				//ת���ַ����еġ�<������
		changeStr=changeStr.replaceAll(">","&gt;");				//ת���ַ����еġ�>������
		changeStr=changeStr.replaceAll("\r\n","<br>");			//ת���ַ����еĻس�����
		return changeStr;
	}

	public static String dateTimeChange(Date source){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String changeTime=format.format(source);
		return changeTime;
	}

}