package com.huge.ihos.update.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.update.model.WorkScore;
import com.huge.ihos.update.service.WorkScoreManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class WorkScorePagedAction extends JqGridBaseAction implements Preparable {

	private WorkScoreManager workScoreManager;
	private List<WorkScore> workScores;
	private WorkScore workScore;
	private String id;
	private String parentid;
	private WorkScore pWorkScore;

	public WorkScore getpWorkScore() {
		return pWorkScore;
	}

	public void setpWorkScore(WorkScore pWorkScore) {
		this.pWorkScore = pWorkScore;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public void setWorkScoreManager(WorkScoreManager workScoreManager) {
		this.workScoreManager = workScoreManager;
	}

	public List<WorkScore> getworkScores() {
		return workScores;
	}

	public void setWorkScores(List<WorkScore> workScores) {
		this.workScores = workScores;
	}

	public WorkScore getWorkScore() {
		return workScore;
	}

	public void setWorkScore(WorkScore workScore) {
		this.workScore = workScore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String workScoreGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = workScoreManager
					.getWorkScoreCriteria(pagedRequests,filters);
			this.workScores = (List<WorkScore>) pagedRequests.getList();
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
			if(workScore.getParentid()!=null){
				parentid = workScore.getParentid().getId();
				if(parentid.equals("")||parentid.equals("-1")){
					workScore.setParentid(null);
				}
			}
			workScoreManager.save(workScore);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "workScore.added" : "workScore.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	workScore = workScoreManager.get(id);
        	if(workScore.getParentid()!=null){
        		parentid = workScore.getParentid().getId();
        		pWorkScore = workScoreManager.get(parentid);
        		workScore.setParentid(pWorkScore);
        		
        	}
        	this.setEntityIsNew(false);
        } else {
        	workScore = new WorkScore();
        	if(parentid!=null&&!"".equals(parentid)){
        		if(parentid.equals("-1")){
        			pWorkScore = new WorkScore();
        			pWorkScore.setId("-1");
        			pWorkScore.setWorkName("工作量积分");
        		}else{
        			pWorkScore = workScoreManager.get(parentid);
        			workScore.setId(pWorkScore.getId());
        		}
        		Integer l = pWorkScore.getClevel();
        		if(l!=null&&!"-1".equals(pWorkScore.getId())){
        			workScore.setClevel(l+1);
        		}
        		workScore.setParentid(pWorkScore);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String workScoreGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					WorkScore workScore = workScoreManager.get(removeId);
					workScoreManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("workScore.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkWorkScoreGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	List<Map<String, String>> workScoreTreeNodes;
	public List<Map<String, String>> getWorkScoreTreeNodes() {
		return workScoreTreeNodes;
	}

	public void setWorkScoreTreeNodes(List<Map<String, String>> workScoreTreeNodes) {
		this.workScoreTreeNodes = workScoreTreeNodes;
	}

	public String getWorkScoreTree(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","false"));
		List<WorkScore> workScores = workScoreManager.getByFilters(filters);
		workScoreTreeNodes = new ArrayList<Map<String,String>>();
		Map<String, String> workScoreRoot = new HashMap<String, String>();
		workScoreRoot.put("id", "-1");
		workScoreRoot.put("name", "工作量积分");
		workScoreTreeNodes.add(workScoreRoot);
		for(WorkScore workScore : workScores){
			Map<String, String> workScoreTemp = new HashMap<String, String>();
			workScoreTemp.put("id", workScore.getId());
			workScoreTemp.put("name", workScore.getWorkName());
			if(workScore.getParentid()==null||"".equals(workScore.getParentid())){
				workScoreTemp.put("pId", "-1");
			}else{
				workScoreTemp.put("pId", workScore.getParentid().getId());
			}
			workScoreTreeNodes.add(workScoreTemp);
		}
		return SUCCESS;
	}

	private String isValid() {
		if (workScore == null) {
			return "Invalid workScore Data";
		}

		return SUCCESS;

	}
}

