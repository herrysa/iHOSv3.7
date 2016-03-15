package com.huge.ihos.system.reportManager.search.exinterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SearchCallback {

	public static Map<String, Callback> callBackMap;
	
	public SearchCallback(){
		if(callBackMap==null){
			callBackMap = new HashMap<String, Callback>();
			Callback callback = new Callback();
			callback.setName("businessAccountMap");
			callback.setType("beforeFunc");
			callback.setClassName("com.huge.ihos.pz.exinterface.BusinessAccountMap");
			callback.setMethodName("businessAccountMap");
			
			callBackMap.put(callback.getName()+"_"+callback.getType(), callback);
		}
		
	}
	
	public void exeCallback(String callbackName,Map param){
		Callback callback = callBackMap.get(callbackName);
		Class<?> callbackClass=null;
		try {
			callbackClass=Class.forName(callback.getClassName());
			Method method;
			method = callbackClass.getMethod(callback.getMethodName(),Map.class);
			method.invoke(callbackClass.newInstance(),param);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Callback{
	private String name;
	private String type;
	private String className;
	private String methodName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
