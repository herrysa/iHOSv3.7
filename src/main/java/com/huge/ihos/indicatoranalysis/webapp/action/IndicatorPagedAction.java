package com.huge.ihos.indicatoranalysis.webapp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.indicatoranalysis.model.Indicator;
import com.huge.ihos.indicatoranalysis.model.IndicatorType;
import com.huge.ihos.indicatoranalysis.service.IndicatorManager;
import com.huge.ihos.indicatoranalysis.service.IndicatorTypeManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class IndicatorPagedAction extends JqGridBaseAction implements Preparable {

	private IndicatorManager indicatorManager;
	private List<Indicator> indicators;
	private Indicator indicator;
	private String id;
	private String indicatorTypeId;

	public void setIndicatorTypeId(String indicatorTypeId) {
		this.indicatorTypeId = indicatorTypeId;
	}

	public void setIndicatorManager(IndicatorManager indicatorManager) {
		this.indicatorManager = indicatorManager;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }
	private List<IndicatorType> indicatorTypeList;
	private IndicatorTypeManager indicatorTypeManager;
	public List<IndicatorType> getIndicatorTypeList() {
		return indicatorTypeList;
	}

	public void setIndicatorTypeManager(IndicatorTypeManager indicatorTypeManager) {
		this.indicatorTypeManager = indicatorTypeManager;
	}

	public void prepare() throws Exception {
		indicatorTypeList = indicatorTypeManager.getAll();
		this.clearSessionMessages();
	}
	
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			Indicator pIndicator = indicator.getParent();
			if(pIndicator==null || pIndicator.getId()==null || "".equals(pIndicator.getId().trim())){
				indicator.setParent(null);
			}
			if(this.isEntityIsNew()){
				pIndicator = indicator.getParent();
				if(pIndicator==null){
					indicator.setLevel(1);
				}else{
					pIndicator = this.indicatorManager.get(pIndicator.getId());
					indicator.setLevel(pIndicator.getLevel()+1);
				}
			}
			indicator = indicatorManager.save(indicator);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "indicator.added" : "indicator.updated";
		return ajaxForward(this.getText(key));
	}
	private String parentId;
	private String parentName;
	
    public String getParentName() {
		return parentName;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentId() {
		return parentId;
	}

	public String edit() {
        if (id != null) {
        	indicator = indicatorManager.get(id);
        	if(indicator.getParent()==null){
        		parentName = indicator.getIndicatorType().getName();
        	}else{
        		parentName = indicator.getParent().getNameWithCode();
        	}
        	this.setEntityIsNew(false);
        } else {
        	indicator = new Indicator();
        	IndicatorType indicatorType = this.indicatorTypeManager.get(indicatorTypeId);
        	indicator.setIndicatorType(indicatorType);
        	Indicator parentIndicator = null;
        	if(parentId.equals(indicatorTypeId)){
        		parentName = indicatorType.getName();
        	}else{
        		parentIndicator = this.indicatorManager.get(parentId);
        		indicator.setCode(parentIndicator.getCode());
        		parentName = parentIndicator.getNameWithCode();
        	}
        	indicator.setParent(parentIndicator);
        	indicator.setLevel(1);
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    
	public String delete(){
		try {
			indicatorManager.remove(id);
		} catch (Exception e) {
			gridOperationMessage = e.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		gridOperationMessage = this.getText( "indicator.deleted" );
        return ajaxForward( true, gridOperationMessage, false );
	}
	
	private List<ZTreeSimpleNode> indicatorNodes;
	
	public List<ZTreeSimpleNode> getIndicatorNodes() {
		return indicatorNodes;
	}

	public String makeIndicatorTree(){
		indicatorNodes = new ArrayList<ZTreeSimpleNode>();
		IndicatorType indicatorType = null;
		ZTreeSimpleNode rootNode = new ZTreeSimpleNode(),indicatorNode;
		if(indicatorTypeId==null){
			if(this.indicatorTypeList!=null && indicatorTypeList.size()>0){
				indicatorType = indicatorTypeList.get(0);
			}else{
				return SUCCESS;
			}
		}else{
			indicatorType = this.indicatorTypeManager.get(indicatorTypeId);
		}
		rootNode.setId(indicatorType.getCode());
		rootNode.setIsParent(true);
		rootNode.setName(indicatorType.getName());
		rootNode.setOpen(true);
		indicatorNodes.add(rootNode);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_indicatorType.code",indicatorType.getCode()));
		filters.add(new PropertyFilter("OAS_code",""));
		indicators = this.indicatorManager.getByFilters(filters);
		if(indicators!=null && indicators.size()>0){
			for(Indicator indicator:indicators){
				indicatorNode = new ZTreeSimpleNode();
				indicatorNode.setId(indicator.getId());
				indicatorNode.setName(indicator.getNameWithCode());
				indicatorNode.setParent(!indicator.getLeaf());
				indicatorNode.setpId(indicator.getParent()==null?rootNode.getId():indicator.getParent().getId());
				indicatorNodes.add(indicatorNode);
			}
		}
		return SUCCESS;
	}
	/**
	 * 同一类型的指标编码不能重复
	 * @return
	 */
	public String checkIndiactorCode(){
		HttpServletRequest req = this.getRequest();
		String code = req.getParameter("code");
		String returnMessage = req.getParameter("returnMessage");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_code",code));
		filters.add(new PropertyFilter("EQS_indicatorType.code",indicatorTypeId));
		this.indicators = this.indicatorManager.getByFilters(filters);
		if(indicators!=null && indicators.size()>0){
			return ajaxForward( false, returnMessage, false );
		}else{
			return null;
		}
	}
	/**
	 * 同一个父亲的孩子指标名字不能重复
	 * @return
	 */
	public String checkIndiactorName(){
		HttpServletRequest req = this.getRequest();
		String name = req.getParameter("name");
		String parentId = req.getParameter("parentId");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_name",name));
		filters.add(new PropertyFilter("EQS_indicatorType.code",indicatorTypeId));
		if(!parentId.equals(indicatorTypeId)){
			filters.add(new PropertyFilter("EQS_parent.id",parentId));
		}
		this.indicators = this.indicatorManager.getByFilters(filters);
		if(indicators!=null && indicators.size()>0){
			return ajaxForward( false, "同一个父级的指标名称不能重复！", false );
		}else{
			return null;
		}
	}
	private List<Indicator> childIndicators;
	
	public List<Indicator> getChildIndicators() {
		return childIndicators;
	}

	public String setIndicatorFormula(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_parent.id",id));
		filters.add(new PropertyFilter("OAS_code",""));
		childIndicators = this.indicatorManager.getByFilters(filters);
		indicator = this.indicatorManager.get(id);
		return SUCCESS;
	}
	private String realFormula;
	
	public String getRealFormula() {
		return realFormula;
	}

	public String setIndicatorLeafFormula(){
		realFormula = this.getRequest().getParameter("realFormula");
		return SUCCESS;
	}
	
	private String isValid() {
		if (indicator == null) {
			return "Invalid indicator Data";
		}
		return SUCCESS;
	}
}

