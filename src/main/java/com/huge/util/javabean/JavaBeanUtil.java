package com.huge.util.javabean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.formula.functions.T;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;
import com.huge.util.TestTimer;

/**
 * javaBean的基本构成字符串转换方法
 * 
 * @author Wesley
 * 
 */
public class JavaBeanUtil {

	private static final char SEPARATOR = '_';

	/**
	 * 将属性样式字符串转成驼峰样式字符串<br>
	 * (例:branchNo -> branch_no)<br>
	 * 
	 * @param inputString
	 * @return
	 */
	public static String toUnderlineString(String inputString) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			boolean nextUpperCase = true;

			if (i < (inputString.length() - 1)) {
				nextUpperCase = Character.isUpperCase(inputString.charAt(i + 1));
			}

			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 将驼峰字段转成属性字符串<br>
	 * (例:branch_no -> branchNo )<br>
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String toCamelCaseString(String inputString) {
		return toCamelCaseString(inputString, false);
	}

	/**
	 * 将驼峰字段转成属性字符串<br>
	 * (例:branch_no -> branchNo )<br>
	 * 
	 * @param inputString
	 *            输入字符串
	 * @param firstCharacterUppercase
	 *            是否首字母大写
	 * @return
	 */
	public static String toCamelCaseString(String inputString, boolean firstCharacterUppercase) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
			case '/':
			case '&':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;

			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}

		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		return sb.toString();
	}

	/**
	 * 得到标准字段名称
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String getValidPropertyName(String inputString) {
		String answer;
		if (inputString == null) {
			answer = null;
		} else if (inputString.length() < 2) {
			answer = inputString.toLowerCase(Locale.US);
		} else {
			if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
				answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
			} else {
				answer = inputString;
			}
		}
		return answer;
	}

	/**
	 * 将属性转换成标准set方法名字符串<br>
	 * 
	 * @param property
	 * @return
	 */
	public static String getSetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();

		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "set");
		return sb.toString();
	}

	/**
	 * 将属性转换成标准get方法名字符串<br>
	 * 
	 * @param property
	 * @return
	 */
	public static String getGetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();

		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "get");
		return sb.toString();
	}
	
	public static List<Map<String, String>> convertListBean(Collection<Object> collection,String colInfo,String dataSep) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{ 
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		for(Object bean : collection){
			resultList.add(convertBean(bean,colInfo,dataSep));
		}
		return resultList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })  
    public static Map<String,String> convertBean(Object bean,String colInfo,String dataSep) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{ 
    	if(dataSep!=null&&"".equals(dataSep)){
    		dataSep = ".";
    	}
    	Map<String,String> map = new HashMap<String,String>();
		Map beanMap = PropertyUtils.describe(bean);
		JSONObject colArr = JSONObject.fromObject(colInfo);
		Iterator colIt = colArr.keys();
		while (colIt.hasNext()) {
			String	key = colIt.next().toString();
			JSONObject col = JSONObject.fromObject(colArr.get(key));
    		String name = col.getString("name");
    		if(name.contains(".")){
    			String[] nameArr = name.split("\\.");
    			String parentName = nameArr[0];
    			String subName = name.replace(parentName+".", "");
    			Object value = beanMap.get(parentName);
    			if(value!=null){
    				converSubBean(parentName,value,map,subName,dataSep);
    			}else{
    				name = name.replace(".", dataSep);
    				map.put(name, "");
    			}
    		}else{
    			Object value = beanMap.get(name);
    			name = name.replace(".", dataSep);
    			if(value!=null){
    				map.put(name, value.toString());
    			}else{
    				map.put(name, "");
    			}
    		}
		}
	    return map;  
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })  
    public static void converSubBean(String parentName,Object bean,Map<String,String> map,String subName,String dataSep) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
    	Map beanMap = PropertyUtils.describe(bean);
    	if(subName.contains(".")){
			String[] nameArr = subName.split("\\.");
			String subBeanColName = nameArr[0];
			parentName += "."+subBeanColName;
			String sub_subBeanColName = subName.replace(subBeanColName+".", "");
			Object value = beanMap.get(subBeanColName);
			if(value!=null){
				converSubBean(parentName,value,map,sub_subBeanColName,dataSep);
			}else{
				String name = parentName+"."+sub_subBeanColName;
				name = name.replace(".", dataSep);
				map.put(name, "");
			}
		}else{
			Object value = beanMap.get(subName);
			String name = parentName+"."+subName;
			name = name.replace(".", dataSep);
			if(value!=null){
				map.put(name, value.toString());
			}else{
				map.put(name, "");
			}
		}
    }
	
	public static List<Map<String, String>> convertListBean(Collection<Object> collection,int level,String dataSep) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{ 
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		for(Object bean : collection){
			resultList.add(convertBean(bean,level,dataSep));
		}
		return resultList;
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public static Map<String,String> convertBean(Object bean,int level,String dataSep) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{ 
    	if(dataSep!=null&&"".equals(dataSep)){
    		dataSep = ".";
    	}
    	Map<String,String> map = new HashMap<String,String>();
    	Map<String,Integer> hashCodeMap = new HashMap<String,Integer>();
    	hashCodeMap.put(""+bean.hashCode(), 1);
		Map beanMap = PropertyUtils.describe(bean);
		Set keySet = beanMap.keySet();
		for(Object key : keySet){
			Object value = beanMap.get(key);
			String colName = key.toString();
			if(value!=null){
				if(value instanceof BaseObject){
					converSubBean(colName,value,map,hashCodeMap,level,dataSep);
				}else{
					map.put(colName, value.toString());
				}
			}else{
				map.put(colName, "");
			}
			
		}
	    return map;  
    }  
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public static void converSubBean(String parentName,Object bean,Map<String,String> map,Map hashCodeMap,int level,String dataSep) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
    	if(level==0){
    		return ;
    	}else{
    		level--;
    	}
    	String hashCode = ""+bean.hashCode();
    	Integer time = (Integer)hashCodeMap.get(hashCode);
    	if(time!=null&&time==2){
    		return ;
    	}else{
    		if(time==null){
    			time = 0;
    		}else{
    			time++;
    		}
    		hashCodeMap.put(hashCode,time);
    	}
    	Map beanMap = PropertyUtils.describe(bean);
		Set keySet = beanMap.keySet();
		for(Object key : keySet){
			Object value = beanMap.get(key);
			if(value!=null){
				String colName = key.toString();
				if(value instanceof BaseObject){
					converSubBean(parentName+dataSep+colName,value,map,hashCodeMap,level,dataSep);
				}else{
					map.put(parentName+dataSep+colName, value.toString());
				}
			}
			
		}
    }

	public static void main(String[] args) {
		/*System.out.println(JavaBeanUtil.toUnderlineString("ISOCertifiedStaff"));
		System.out.println(JavaBeanUtil.getValidPropertyName("CertifiedStaff"));
		System.out.println(JavaBeanUtil.getSetterMethodName("userID"));
		System.out.println(JavaBeanUtil.getGetterMethodName("userID"));
		System.out.println(JavaBeanUtil.toCamelCaseString("iso_certified_staff", true));
		System.out.println(JavaBeanUtil.getValidPropertyName("certified_staff"));
		System.out.println(JavaBeanUtil.toCamelCaseString("site_Id"));*/
		
		Department d1 = new Department();
		d1.setDepartmentId("1");
		d1.setPhone("1111111111");
		Department d2 = new Department();
		d2.setDepartmentId("2");
		d1.setParentDept(d1);
	}

}

