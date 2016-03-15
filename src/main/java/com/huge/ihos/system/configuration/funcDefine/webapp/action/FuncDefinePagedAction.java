package com.huge.ihos.system.configuration.funcDefine.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.system.configuration.funcDefine.model.FuncDefine;
import com.huge.ihos.system.configuration.funcDefine.service.FuncDefineManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class FuncDefinePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2368111760716384499L;
	private FuncDefineManager funcDefineManager;
	private List<FuncDefine> funcDefines;
	private FuncDefine funcDefine;
	private String funcId;

	public FuncDefineManager getFuncDefineManager() {
		return funcDefineManager;
	}

	public void setFuncDefineManager(FuncDefineManager funcDefineManager) {
		this.funcDefineManager = funcDefineManager;
	}

	public List<FuncDefine> getfuncDefines() {
		return funcDefines;
	}

	public void setFuncDefines(List<FuncDefine> funcDefines) {
		this.funcDefines = funcDefines;
	}

	public FuncDefine getFuncDefine() {
		return funcDefine;
	}

	public void setFuncDefine(FuncDefine funcDefine) {
		this.funcDefine = funcDefine;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public List<FuncDefine> getFuncDefines() {
		return funcDefines;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String funcDefineGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = funcDefineManager.getFuncDefineCriteria(pagedRequests, filters);
			this.funcDefines = (List<FuncDefine>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			funcDefineManager.save(funcDefine);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "funcDefine.added" : "funcDefine.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (funcId != null) {
			funcDefine = funcDefineManager.get(funcId);
			this.setEntityIsNew(false);
		} else {
			funcDefine = new FuncDefine();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String funcDefineGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//					Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//					FuncDefine funcDefine = funcDefineManager.get(new String(removeId));
					funcDefineManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("funcDefine.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkFuncDefineGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String checkFuncSame() {
		HttpServletRequest request = this.getRequest();
		String checkType = request.getParameter("checkType");
		String checkValue = request.getParameter("checkValue");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_" + checkType, checkValue));
		funcDefines = funcDefineManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(funcDefines) && !funcDefines.isEmpty()) {
			return ajaxForwardError("已存在该名称!");
		}
		return null;
	}
	/**
	 * 获取函数模板，并替换参数
	 * 请求这个方法时，需要传递两个参数：funcId和funcParam
	 */
	public String getFunc() {
		if (OtherUtil.measureNotNull(funcId)) {
			funcDefine = funcDefineManager.get(id);
		}
		HttpServletRequest request = this.getRequest();
		String param = request.getParameter("funcParam");
		String[] params = null;
		if (OtherUtil.measureNotNull(param)) {
			params = param.split(",");
		}
		String funcParam = funcDefine.getFuncParam();
		String funcBody = funcDefine.getFuncBody();
		String[] funcParams = funcParam.split(",");
		//变量替换
		if(params.length == funcParams.length) {
			for (int i = 0; i < funcParams.length; i++) {
				funcBody.replace(funcParams[i], params[i]);
			}
		}
		funcDefine.setFuncBody(funcBody);
		return null;
	}

	private String isValid() {
		if (funcDefine == null) {
			return "Invalid funcDefine Data";
		}

		return SUCCESS;

	}
}
