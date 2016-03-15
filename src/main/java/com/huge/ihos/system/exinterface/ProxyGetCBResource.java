package com.huge.ihos.system.exinterface;

import com.huge.webapp.util.SpringContextHelper;

public class ProxyGetCBResource extends GetCBResource{
private GetCBResource getCBResource;
	
	public ProxyGetCBResource(){
		getCBResource = (GetCBResource)SpringContextHelper.getBean("realizeGetCBResource");
	}
	@Override
	public Boolean cbModelStatusClose(String period) {
		if(getCBResource!=null){
			return getCBResource.cbModelStatusClose(period);
		}
		return true;
	}
	@Override
	public Boolean cbModelStatusAntiClose(String period){
		if(getCBResource!=null){
			return getCBResource.cbModelStatusAntiClose(period);
		}
		return true;
	}
}
