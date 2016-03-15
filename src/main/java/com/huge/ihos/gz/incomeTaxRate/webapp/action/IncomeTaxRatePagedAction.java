package com.huge.ihos.gz.incomeTaxRate.webapp.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate;
import com.huge.ihos.gz.incomeTaxRate.service.IncomeTaxRateManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class IncomeTaxRatePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6344619231284222899L;
	private IncomeTaxRateManager incomeTaxRateManager;
	private List<IncomeTaxRate> incomeTaxRates;
	private IncomeTaxRate incomeTaxRate;
	private String rateId;

	public IncomeTaxRateManager getIncomeTaxRateManager() {
		return incomeTaxRateManager;
	}

	public void setIncomeTaxRateManager(IncomeTaxRateManager incomeTaxRateManager) {
		this.incomeTaxRateManager = incomeTaxRateManager;
	}

	public List<IncomeTaxRate> getincomeTaxRates() {
		return incomeTaxRates;
	}

	public void setIncomeTaxRates(List<IncomeTaxRate> incomeTaxRates) {
		this.incomeTaxRates = incomeTaxRates;
	}

	public IncomeTaxRate getIncomeTaxRate() {
		return incomeTaxRate;
	}

	public void setIncomeTaxRate(IncomeTaxRate incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
        this.rateId = rateId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String incomeTaxRateList(){
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("gzTypeList Error", e);
		}
		return SUCCESS;
	}
	public String incomeTaxRateGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			//filters.add(new PropertyFilter("OAI_level",""));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = incomeTaxRateManager
					.getIncomeTaxRateCriteria(pagedRequests,filters);
			this.incomeTaxRates = (List<IncomeTaxRate>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			incomeTaxRateManager.saveIncomeTaxRate(incomeTaxRate);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "incomeTaxRate.added" : "incomeTaxRate.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (rateId != null) {
        	incomeTaxRate = incomeTaxRateManager.get(rateId);
        	this.setEntityIsNew(false);
        } else {
        	incomeTaxRate = new IncomeTaxRate();
        	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        	filters.add(new PropertyFilter("EQB_disabled","0"));
        	filters.add(new PropertyFilter("ODI_level",""));
        	incomeTaxRates = incomeTaxRateManager.getByFilters(filters);
        	if(OtherUtil.measureNotNull(incomeTaxRates)&&!incomeTaxRates.isEmpty()){
        		incomeTaxRate.setBaseNum(incomeTaxRates.get(0).getBaseNum());
        		incomeTaxRate.setLevel(incomeTaxRates.get(0).getLevel()+1);
        	}else{
        		incomeTaxRate.setBaseNum(3500);
        		incomeTaxRate.setLevel(1);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String incomeTaxRateGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
//					IncomeTaxRate incomeTaxRate = incomeTaxRateManager.get(new String(removeId));
					incomeTaxRateManager.remove(new String(removeId));
					//删除后应该重新生成level
					Resort();
				}
				gridOperationMessage = this.getText("incomeTaxRate.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkIncomeTaxRateGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (incomeTaxRate == null) {
			return "Invalid incomeTaxRate Data";
		}

		return SUCCESS;

	}
	
	/**
	 * 编写重新生成级次顺序的方法
	 * */
	public void Resort(){
		List<IncomeTaxRate> lst = incomeTaxRateManager.getAll();
		if(lst.size()>0){
		   for(int i=0;i<lst.size();i++){
			   lst.get(i).setLevel(i+1);   
		   }
		}
	}
}

