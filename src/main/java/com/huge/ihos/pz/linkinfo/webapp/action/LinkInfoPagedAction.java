package com.huge.ihos.pz.linkinfo.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.pz.linkinfo.model.LinkInfo;
import com.huge.ihos.pz.linkinfo.service.LinkInfoManager;
import com.huge.ihos.system.datacollection.model.DataSourceDefine;
import com.huge.ihos.system.datacollection.service.DataSourceDefineManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class LinkInfoPagedAction extends JqGridBaseAction implements Preparable {

	private LinkInfoManager linkInfoManager;
	private List<LinkInfo> linkInfoes;
	private LinkInfo linkInfo;
	private String infoId;
	private DataSourceDefineManager dataSourceDefineManager;
	private List<DataSourceDefine> dataSources;
	private String middleDbLinkId;
	private String cwDbLinkId;

	public List<DataSourceDefine> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<DataSourceDefine> dataSources) {
		this.dataSources = dataSources;
	}

	public void setDataSourceDefineManager(DataSourceDefineManager dataSourceDefineManager) {
		this.dataSourceDefineManager = dataSourceDefineManager;
	}

	public LinkInfoManager getLinkInfoManager() {
		return linkInfoManager;
	}

	public void setLinkInfoManager(LinkInfoManager linkInfoManager) {
		this.linkInfoManager = linkInfoManager;
	}

	public List<LinkInfo> getlinkInfoes() {
		return linkInfoes;
	}

	public void setLinkInfos(List<LinkInfo> linkInfoes) {
		this.linkInfoes = linkInfoes;
	}

	public LinkInfo getLinkInfo() {
		return linkInfo;
	}

	public void setLinkInfo(LinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String getMiddleDbLinkId() {
		return middleDbLinkId;
	}

	public void setMiddleDbLinkId(String middleDbLinkId) {
		this.middleDbLinkId = middleDbLinkId;
	}

	public String getCwDbLinkId() {
		return cwDbLinkId;
	}

	public void setCwDbLinkId(String cwDbLinkId) {
		this.cwDbLinkId = cwDbLinkId;
	}

	public String linkInfoList() {
		this.dataSources = dataSourceDefineManager.getAll();
		this.middleDbLinkId = this.linkInfoManager.getDataSourceId("1");
		this.cwDbLinkId = this.linkInfoManager.getDataSourceId("2");
		return SUCCESS;
	}

	public String saveLinkInfoMessage() {
		HttpServletRequest request = this.getRequest();
		String middleLink = request.getParameter("middleLink");
		String cwLink = request.getParameter("cwLink");
		List<LinkInfo> linkInfos = new ArrayList<LinkInfo>();
		if (middleLink != null && !"".equals(middleLink) && !"undefined".equals(middleLink)) {
			this.linkInfoManager.changeLinkInfo("1");
			LinkInfo linkInfo = new LinkInfo();
			linkInfo.setType("1");
			linkInfo.setDataSourceId(Long.parseLong(middleLink));
			linkInfos.add(linkInfo);
		}
		if (cwLink != null && !"".equals(cwLink) && !"undefined".equals(cwLink)) {
			this.linkInfoManager.changeLinkInfo("2");
			LinkInfo linkInfo = new LinkInfo();
			linkInfo.setType("2");
			linkInfo.setDataSourceId(Long.parseLong(cwLink));
			linkInfos.add(linkInfo);
		}
		if (!linkInfos.isEmpty()) {
			linkInfoManager.saveAll(linkInfos);
		}
		return ajaxForwardSuccess("添加成功！");
	}

	public String linkInfoGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = linkInfoManager.getLinkInfoCriteria(pagedRequests, filters);
			this.linkInfoes = (List<LinkInfo>) pagedRequests.getList();
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
			linkInfoManager.save(linkInfo);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "linkInfo.added" : "linkInfo.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (infoId != null) {
			linkInfo = linkInfoManager.get(infoId);
			this.setEntityIsNew(false);
		} else {
			linkInfo = new LinkInfo();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String linkInfoGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//LinkInfo linkInfo = linkInfoManager.get(removeId);
					linkInfoManager.remove(new String(removeId));

				}
				gridOperationMessage = this.getText("linkInfo.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkLinkInfoGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (linkInfo == null) {
			return "Invalid linkInfo Data";
		}

		return SUCCESS;

	}
}
