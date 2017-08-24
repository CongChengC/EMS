package com.atguigu.ems.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter{
	
	private DateFormat dateFormat = null;
	
	{
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Override
	public Object convertFromString(Map arg0, String[] arg1, Class arg2) {
        if (arg1 !=null && arg1.length == 1) {
			String val = arg1[0];
			try {
				return dateFormat.parse(val);
			} catch (ParseException e) {}
		}
		return null;
	}

	@Override
	public String convertToString(Map arg0, Object arg1) {
        if(arg1 != null && arg1 instanceof Date){
        	Date date = (Date) arg1;
        	return dateFormat.format(date);
        }
		return null;
	}

}
