package com.atguigu.ems.orm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EnumType;

import org.apache.commons.lang.StringUtils;

public class PropertyFilter {

	private String propertyName;
	private Object propertyVal;
	
	public enum MatchType{
		EQ, GT, GE, LT, LE, LIKE;
	}
	
	private MatchType matchType;
	
	public enum PropertyType{
		
		I(Integer.class), F(Float.class), S(String.class), D(Date.class);
		
		private Class propertyType;
		
		private PropertyType(Class propertyType) {
			this.propertyType = propertyType;
		}
		
		public Class getPropertyType() {
			return propertyType;
		}
	}
	
	private Class propertyType;

	public PropertyFilter(String propertyName, Object propertyVal,
			MatchType matchType, Class propertyType) {
		this.propertyName = propertyName;
		this.propertyVal = propertyVal;
		this.matchType = matchType;
		this.propertyType = propertyType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public Class getPropertyType() {
		return propertyType;
	}
	
	//把请求参数转为 PropertyFilter 的集合
	public static List<PropertyFilter> parseParamsToPropertyFilters(Map<String, Object> params){
		List<PropertyFilter> filters = new ArrayList<>();
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			//value 即为 propertyVal;
			Object propertyVal = entry.getValue();
			if(propertyVal == null || "".equals(propertyVal.toString().trim())){
				continue;
			}

			//需要解析 key 来得到 propertyName, matchType, propertyVal;
			String key = entry.getKey(); //EQI_loginName, GTD_birth
			String str1 = StringUtils.substringBefore(key, "_"); //EQI, GTD
			
			String matchTypeCode = StringUtils.substring(str1, 0, str1.length() - 1); //EQ, GT
			MatchType matchType = Enum.valueOf(MatchType.class, matchTypeCode);
			
			String propertyTypeCode = StringUtils.substring(str1, str1.length() - 1); //I, D
			Class propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getPropertyType();
			
			String propertyName = StringUtils.substringAfter(key, "_");
			
			PropertyFilter filter = new PropertyFilter(propertyName, propertyVal, matchType, propertyType);
			filters.add(filter);
		}
		
		return filters;
	}

	@Override
	public String toString() {
		return "PropertyFilter [propertyName=" + propertyName
				+ ", propertyVal=" + propertyVal + ", matchType=" + matchType
				+ ", propertyType=" + propertyType + "]";
	}
}
