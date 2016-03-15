package com.huge.exceptions;


public class ModelStatusException extends Exception{

	String modelId;
	String periodType;
	String period;
	String message;
	
	public ModelStatusException(){
		
	}
	
	public ModelStatusException(String modelId,String periodType,String period,String message){
		
	}
}
