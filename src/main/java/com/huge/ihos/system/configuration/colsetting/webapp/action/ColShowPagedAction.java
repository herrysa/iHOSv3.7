package com.huge.ihos.system.configuration.colsetting.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.ihos.system.configuration.colsetting.model.TableCol;
import com.huge.ihos.system.configuration.colsetting.service.ColShowManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ColShowPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2149256607542524051L;
	private ColShowManager colShowManager;
	private List<ColShow> colShows;
	private ColShow colShow;
	private String id;
	private String cols;
	private String titles;
	private String hiddens;
	private String colIsShows;
	private String colWidths;
	private String grid;
	private List<TableCol> colList;
	private List<HashMap<String,String>> templtShowList;
	private Boolean templetToDept;
	private Boolean templetToRole;
	private Boolean templetToPublic;
	private String colshowType;//类型

	public Boolean getTempletToDept() {
		return templetToDept;
	}

	public void setTempletToDept(Boolean templetToDept) {
		this.templetToDept = templetToDept;
	}

	public Boolean getTempletToRole() {
		return templetToRole;
	}

	public void setTempletToRole(Boolean templetToRole) {
		this.templetToRole = templetToRole;
	}

	public Boolean getTempletToPublic() {
		return templetToPublic;
	}

	public void setTempletToPublic(Boolean templetToPublic) {
		this.templetToPublic = templetToPublic;
	}

	public List<HashMap<String,String>> getTempltShowList() {
		return templtShowList;
	}

	public void setTempltShowList(List<HashMap<String,String>> templtShowList) {
		this.templtShowList = templtShowList;
	}

	public List<TableCol> getColList() {
		return colList;
	}

	public void setColList(List<TableCol> colList) {
		this.colList = colList;
	}

	private String templName;

	
	public String getColIsShows() {
		return colIsShows;
	}

	public void setColIsShows(String colIsShows) {
		this.colIsShows = colIsShows;
	}
	
	public String getColWidths() {
		return colWidths;
	}

	public void setColWidths(String colWidths) {
		this.colWidths = colWidths;
	}
	
	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}
	public String getTemplName() {
		return templName;
	}

	public void setTemplName(String templName) {
		this.templName = templName;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}
	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}
	
	public String getHiddens() {
		return hiddens;
	}

	public void setHiddens(String hiddens) {
		this.hiddens = hiddens;
	}

	public void setColShowManager(ColShowManager colShowManager) {
		this.colShowManager = colShowManager;
	}

	public List<ColShow> getColShows() {
		return colShows;
	}

	public void setColShows(List<ColShow> colShows) {
		this.colShows = colShows;
	}

	public ColShow getColShow() {
		return colShow;
	}

	public void setColShow(ColShow colShow) {
		this.colShow = colShow;
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
	
	public String colShowListPre(){
		try {
		User user = this.getSessionUser();
		List<User> dept_users = this.userManager.getUsersByDept(user.getPerson().getDepartment());
		List<User> role_users = this.userManager.getUsersByRoles(user.getRoles());
		String deptUsersStr = "" , roleUsersStr = "";
		LinkedList<HashMap<String, String>> templtShowLinkedList = new LinkedList<HashMap<String,String>>();
		for(int d=0;d<dept_users.size();d++){
			User duser = dept_users.get(d);
			if(duser.getId()==user.getId()){
				continue;
			}
			deptUsersStr += "'"+duser.getId()+"',";
		}
		for(int r=0;r<role_users.size();r++){
			User ruser = role_users.get(r);
			if(ruser.getId()==user.getId()){
				continue;
			}
			roleUsersStr += "'"+ruser.getId()+"',";
		}
		List<HashMap<String, String>> userTempletsHashMaps = colShowManager.getAllTempl(entityName,""+user.getId(),colshowType);
		List<HashMap<String, String>> deptTempletsHashMaps = null;
		List<HashMap<String, String>> roleTempletsHashMaps = null;
		List<HashMap<String, String>> publicTempletsHashMaps = colShowManager.getPublicTempl(entityName,colshowType);
		if(!"".equals(deptUsersStr)){
			deptUsersStr = "("+deptUsersStr.substring(0, deptUsersStr.length()-1)+")";
			deptTempletsHashMaps = colShowManager.getDeptTempl(entityName, deptUsersStr,colshowType);
		}
		if(!"".equals(roleUsersStr)){
			roleUsersStr = "("+roleUsersStr.substring(0, roleUsersStr.length()-1)+")";
			roleTempletsHashMaps = colShowManager.getRoleTempl(entityName, roleUsersStr,deptUsersStr,colshowType);
		}
		/*if(userTempletsHashMaps!=null&&userTempletsHashMaps.size()!=0){
			templtShowLinkedList.addAll(userTempletsHashMaps);
		}*/
		HashSet<String> templetNameSet = new HashSet<String>();
		for(int u=0;u<userTempletsHashMaps.size();u++){
			HashMap<String, String> templet = userTempletsHashMaps.get(u);
			String templetNameV = templet.get("templetNameV");
			templetNameV = templetNameV.split("_")[0];
			if(templetNameSet.contains(templetNameV)){
				continue;
			}else{
				templetNameSet.add(templetNameV);
				templtShowLinkedList.add(templet);
			}
		}
		/*if(deptTempletsHashMaps!=null&&deptTempletsHashMaps.size()!=0){
			templtShowSet.addAll(deptTempletsHashMaps);
		}*/
		if(deptTempletsHashMaps!=null){
			for(int d=0;d<deptTempletsHashMaps.size();d++){
				HashMap<String, String> templet = deptTempletsHashMaps.get(d);
				String templetNameV = templet.get("templetNameV");
				templetNameV = templetNameV.split("_")[0];
				if(templetNameSet.contains(templetNameV)){
					continue;
				}else{
					templetNameSet.add(templetNameV);
					templtShowLinkedList.add(templet);
				}
			}
		}
		/*if(roleTempletsHashMaps!=null&&roleTempletsHashMaps.size()!=0){
			templtShowSet.addAll(roleTempletsHashMaps);
		}*/
		if(roleTempletsHashMaps!=null){
			for(int r=0;r<roleTempletsHashMaps.size();r++){
				HashMap<String, String> templet = roleTempletsHashMaps.get(r);
				String templetNameV = templet.get("templetNameV");
				templetNameV = templetNameV.split("_")[0];
				if(templetNameSet.contains(templetNameV)){
					continue;
				}else{
					templetNameSet.add(templetNameV);
					templtShowLinkedList.add(templet);
				}
			}
		}
		/*if(publicTempletsHashMaps!=null&&publicTempletsHashMaps.size()!=0){
			templtShowSet.addAll(publicTempletsHashMaps);
		}*/
		for(int p=0;p<publicTempletsHashMaps.size();p++){
			HashMap<String, String> templet = publicTempletsHashMaps.get(p);
			String templetNameV = templet.get("templetNameV");
			templetNameV = templetNameV.split("_")[0];
			if(templetNameSet.contains(templetNameV)){
				continue;
			}else{
				templetNameSet.add(templetNameV);
				templtShowLinkedList.add(templet);
			}
		}
		//templtShowList = colShowManager.getAllTempl(entityName,""+user.getId());
		templtShowList = templtShowLinkedList;
		List<ColShow> colShowsTemp = null;
		/*if(templtShowList!=null&templtShowList.size()!=0){
			//templateName = templtShowList.get(0).get("templetName");
			//colShowsTemp = colShowManager.getByTemplName(templateName,entityName,""+user.getId());
		}*/
		if(cols!=null){
			String[] colArr = cols.split(",");
			String[] titleArr = titles.split(",");
			String[] hiddenArr = hiddens.split(",");
			String[] colWidthArr = colWidths.split(",");
			if(colShowsTemp==null||colShowsTemp.size()==0){
				colShows = new ArrayList<ColShow>();
				for(int colIndex=0;colIndex<colArr.length;colIndex++){
					ColShow colShowTemp = new ColShow();
					colShowTemp.setCol(colArr[colIndex]);
					colShowTemp.setLabel(titleArr[colIndex]);
					colShowTemp.setShow(!Boolean.parseBoolean(hiddenArr[colIndex]));
					String width = colWidthArr[colIndex];
					if(width!=null&&!"".equals(width)){
						colShowTemp.setColWidth(width);
					}else{
						colShowTemp.setColWidth("80");
					}
					
					colShows.add(colShowTemp);
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String colShowGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = colShowManager
					.getColShowCriteria(pagedRequests,filters);
			this.colShows = (List<ColShow>) pagedRequests.getList();
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
			colShowManager.save(colShow);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "colShow.added" : "colShow.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	colShow = colShowManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	colShow = new ColShow();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String colShowGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					ColShow colShow = colShowManager.get(new String(removeId));
					colShowManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("colShow.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkColShowGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (colShow == null) {
			return "Invalid colShow Data";
		}

		return SUCCESS;

	}
	
	public String highSearch(){
		colShows = colShowManager.getByEntityName(entityName);
		//colSearchs = colSearchManager.getByEntityName(entityName);
		return SUCCESS;
		
	}
	
	public String saveColShowTempl(){
		try {
			User user = this.getSessionUser();
			if(entityName.contains("_")){
				String[] templetNameArr = templName.split("_");
				entityName = templetNameArr[0];
			}
			colShowManager.delByTemplName(templName,entityName,""+user.getId(),colshowType);
			String[] colArr = cols.split(",");
			String[] colIsShowArr = colIsShows.split(",");
			String[] colWidthArr = colWidths.split(",");
			String[] titleArr = titles.split(",");
			//String[] valueArr = values.split(",");
			Calendar calendar = Calendar.getInstance();
			for(int colIndex=0;colIndex<colArr.length;colIndex++){
				
				ColShow colShow = new ColShow();
				colShow.setCol(colArr[colIndex]);
				colShow.setLabel(titleArr[colIndex]);
				colShow.setEntityName(entityName);
				colShow.setTempletName(templName);
				colShow.setEditTime(calendar.getTimeInMillis());
				colShow.setShow(Boolean.parseBoolean(colIsShowArr[colIndex]));
				colShow.setOrder(colIndex+1);
				colShow.setUserId(""+user.getId());
				colShow.setColWidth(colWidthArr[colIndex]);
				colShow.setTempletToDept(templetToDept);
				colShow.setTempletToRole(templetToRole);
				colShow.setTempletToPublic(templetToPublic);
				colShowManager.save(colShow);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getColShowByTempl(){
		try {
			User user = this.getSessionUser();
			String[] templetArr = null ;
			String templetType = "";
			if(templName!=null&&!"".equals(templName)){
				templetArr = templName.split("_");
			}
			if(templetArr!=null&&templetArr.length>0){
				templName = templetArr[0];
				templetType = templetArr[1];
			}
			List<ColShow> colShowsTemp = null;
			if(!"".equals(templetType)){
				if("0".equals(templetType)){
					colShowsTemp = colShowManager.getByTemplName(templName, entityName,""+user.getId(),colshowType);
				}else if("1".equals(templetType)){
					List<User> deptUsers = this.getUserManager().getUsersByDept(user.getPerson().getDepartment());
					String[] userIds = new String[deptUsers.size()];
					for(int d=0;d<deptUsers.size();d++){
						userIds[d] = ""+deptUsers.get(d).getId();
					}
					colShowsTemp = colShowManager.getDeptTempletByName(templName, entityName, userIds,colshowType);
				}else if("2".equals(templetType)){
					List<User> deptUsers = this.getUserManager().getUsersByRoles(user.getRoles());
					String[] userIds = new String[deptUsers.size()];
					for(int d=0;d<deptUsers.size();d++){
						userIds[d] = ""+deptUsers.get(d).getId();
					}
					colShowsTemp = colShowManager.getRoleTempletByName(templName, entityName, userIds,colshowType);
				}else if("3".equals(templetType)){
					colShowsTemp = colShowManager.getPublicTempletByName(templName, entityName,""+user.getId(),colshowType);
				}
			}
			//colShowManager.getAllTempl(entityName,""+user.getId());
			if(colShowsTemp.size()==0){
				String[] colArr = cols.split(",");
				String[] titleArr = titles.split(",");
				String[] hiddenArr = hiddens.split(",");
				String[] colWidthArr = colWidths.split(",");
				colShows = new ArrayList<ColShow>();
				for(int colIndex=0;colIndex<colArr.length;colIndex++){
					ColShow colShowTemp = new ColShow();
					colShowTemp.setCol(colArr[colIndex]);
					colShowTemp.setLabel(titleArr[colIndex]);
					colShowTemp.setShow(!Boolean.parseBoolean(hiddenArr[colIndex]));
					String width = colWidthArr[colIndex];
					if(width!=null&&!"".equals(width)){
						colShowTemp.setColWidth(width);
					}else{
						colShowTemp.setColWidth("80");
					}
					
					colShows.add(colShowTemp);
				}
			}else{
				colShows = colShowsTemp;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getColShowByEntity(){
		try {
			User user = this.getSessionUser();
			colShows = colShowManager.getByFirstTempl(entityName,""+user.getId(),colshowType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String delColShowTempl(){
		try {
			User user = this.getSessionUser();
			templName = templName.split("_")[0];
			colShowManager.delByTemplName(templName, entityName, ""+user.getId(),colshowType);
			templtShowList = colShowManager.getAllTempl(entityName,""+user.getId(),colshowType);
			List<ColShow> colShowsTemp = null;
			if(templtShowList!=null&&templtShowList.size()!=0){
				templName = templtShowList.get(0).get("templetName");
				colShowsTemp = colShowManager.getByTemplName(templName, entityName,""+user.getId(),colshowType);
			}
			String[] colArr = cols.split(",");
			String[] titleArr = titles.split(",");
			String[] hiddenArr = hiddens.split(",");
			String[] colWidthArr = colWidths.split(",");
			if(colShowsTemp==null||colShowsTemp.size()==0){
				colShows = new ArrayList<ColShow>();
				for(int colIndex=0;colIndex<colArr.length;colIndex++){
					ColShow colShowTemp = new ColShow();
					colShowTemp.setCol(colArr[colIndex]);
					colShowTemp.setLabel(titleArr[colIndex]);
					colShowTemp.setShow(!Boolean.parseBoolean(hiddenArr[colIndex]));
					String width = colWidthArr[colIndex];
					if(width!=null&&!"".equals(width)){
						colShowTemp.setColWidth(width);
					}else{
						colShowTemp.setColWidth("80");
					}
					
					colShows.add(colShowTemp);
				}
			}else{
				colShows = colShowsTemp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String customLayout;
	public String getCustomLayout() {
		return customLayout;
	}

	public void setCustomLayout(String customLayout) {
		this.customLayout = customLayout;
	}

	public String saveColShowTemplForGz(){
		try {
			User user = this.getSessionUser();
			colShowManager.delByTemplName(templName, entityName, ""+user.getId(),colshowType);
			Calendar calendar = Calendar.getInstance();
			ColShow colShow = new ColShow();
			colShow.setEntityName(entityName);
			colShow.setTempletName(templName);
			colShow.setEditTime(calendar.getTimeInMillis());
			colShow.setUserId(""+user.getId());
			colShow.setTempletToDept(templetToDept);
			colShow.setTempletToRole(templetToRole);
			colShow.setTempletToPublic(templetToPublic);
			colShow.setCustomLayout(customLayout);
			if(OtherUtil.measureNotNull(colshowType)){
				colShow.setColshowType(colshowType);
			}
			colShowManager.save(colShow);
			return ajaxForward(true, "保存成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！",false);
		}
	}
	public String delColShowTemplForGz(){
		try {
			User user = this.getSessionUser();
			String userId = user.getId()+"";
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_templetName",templName));
			filters.add(new PropertyFilter("EQS_entityName",entityName));
			if(OtherUtil.measureNotNull(colshowType)){
				filters.add(new PropertyFilter("EQS_colshowType",colshowType));
			}
			filters.add(new PropertyFilter("OAI_order",""));
			colShows = colShowManager.getByFilters(filters);
			List<String> removeIds = new ArrayList<String>();
			if(OtherUtil.measureNotNull(colShows)&&!colShows.isEmpty()){
				for(ColShow colShowTemp:colShows){
					String userIdTemp = colShowTemp.getUserId();
					if(userIdTemp.equals(userId)){
						removeIds.add(colShowTemp.getId());
					}else{
						return ajaxForward(false, "只能删除自己创建的格式！",false);
					}
				}
				for(String removeId:removeIds){
					colShowManager.remove(removeId);
				}
			}else{
				return ajaxForward(false, "只能删除自己创建的格式！",false);
			}
			//colShowManager.delByTemplName(templName, entityName, ""+user.getId());
			return ajaxForward(true, "删除成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "删除失败！",false);
		}
	}
	//格式页面
	public String colShowTemplForm(){
		try {
			User user = this.getSessionUser();
			List<User> dept_users = this.userManager.getUsersByDept(user.getPerson().getDepartment());
			List<User> role_users = this.userManager.getUsersByRoles(user.getRoles());
			String deptUsersStr = "" , roleUsersStr = "";
			LinkedList<HashMap<String, String>> templtShowLinkedList = new LinkedList<HashMap<String,String>>();
			for(int d=0;d<dept_users.size();d++){
				User duser = dept_users.get(d);
				if(duser.getId()==user.getId()){
					continue;
				}
				deptUsersStr += "'"+duser.getId()+"',";
			}
			for(int r=0;r<role_users.size();r++){
				User ruser = role_users.get(r);
				if(ruser.getId()==user.getId()){
					continue;
				}
				roleUsersStr += "'"+ruser.getId()+"',";
			}
			List<HashMap<String, String>> userTempletsHashMaps = colShowManager.getAllTempl(entityName,""+user.getId(),colshowType);
			List<HashMap<String, String>> deptTempletsHashMaps = null;
			List<HashMap<String, String>> roleTempletsHashMaps = null;
			List<HashMap<String, String>> publicTempletsHashMaps = colShowManager.getPublicTempl(entityName,colshowType);
			if(!"".equals(deptUsersStr)){
				deptUsersStr = "("+deptUsersStr.substring(0, deptUsersStr.length()-1)+")";
				deptTempletsHashMaps = colShowManager.getDeptTempl(entityName, deptUsersStr,colshowType);
			}
			if(!"".equals(roleUsersStr)){
				roleUsersStr = "("+roleUsersStr.substring(0, roleUsersStr.length()-1)+")";
				roleTempletsHashMaps = colShowManager.getRoleTempl(entityName, roleUsersStr,deptUsersStr,colshowType);
			}
			/*if(userTempletsHashMaps!=null&&userTempletsHashMaps.size()!=0){
				templtShowLinkedList.addAll(userTempletsHashMaps);
			}*/
			HashSet<String> templetNameSet = new HashSet<String>();
			for(int u=0;u<userTempletsHashMaps.size();u++){
				HashMap<String, String> templet = userTempletsHashMaps.get(u);
				String templetNameV = templet.get("templetNameV");
				templetNameV = templetNameV.split("_")[0];
				if(templetNameSet.contains(templetNameV)){
					continue;
				}else{
					templetNameSet.add(templetNameV);
					templtShowLinkedList.add(templet);
				}
			}
			/*if(deptTempletsHashMaps!=null&&deptTempletsHashMaps.size()!=0){
				templtShowSet.addAll(deptTempletsHashMaps);
			}*/
			if(deptTempletsHashMaps!=null){
				for(int d=0;d<deptTempletsHashMaps.size();d++){
					HashMap<String, String> templet = deptTempletsHashMaps.get(d);
					String templetNameV = templet.get("templetNameV");
					templetNameV = templetNameV.split("_")[0];
					if(templetNameSet.contains(templetNameV)){
						continue;
					}else{
						templetNameSet.add(templetNameV);
						templtShowLinkedList.add(templet);
					}
				}
			}
			/*if(roleTempletsHashMaps!=null&&roleTempletsHashMaps.size()!=0){
				templtShowSet.addAll(roleTempletsHashMaps);
			}*/
			if(roleTempletsHashMaps!=null){
				for(int r=0;r<roleTempletsHashMaps.size();r++){
					HashMap<String, String> templet = roleTempletsHashMaps.get(r);
					String templetNameV = templet.get("templetNameV");
					templetNameV = templetNameV.split("_")[0];
					if(templetNameSet.contains(templetNameV)){
						continue;
					}else{
						templetNameSet.add(templetNameV);
						templtShowLinkedList.add(templet);
					}
				}
			}
			/*if(publicTempletsHashMaps!=null&&publicTempletsHashMaps.size()!=0){
				templtShowSet.addAll(publicTempletsHashMaps);
			}*/
			for(int p=0;p<publicTempletsHashMaps.size();p++){
				HashMap<String, String> templet = publicTempletsHashMaps.get(p);
				String templetNameV = templet.get("templetNameV");
				templetNameV = templetNameV.split("_")[0];
				if(templetNameSet.contains(templetNameV)){
					continue;
				}else{
					templetNameSet.add(templetNameV);
					templtShowLinkedList.add(templet);
				}
			}
			//templtShowList = colShowManager.getAllTempl(entityName,""+user.getId());
			templtShowList = templtShowLinkedList;
			List<ColShow> colShowsTemp = null;
			/*if(templtShowList!=null&templtShowList.size()!=0){
				//templateName = templtShowList.get(0).get("templetName");
				//colShowsTemp = colShowManager.getByTemplName(templateName,entityName,""+user.getId());
			}*/
			if(cols!=null){
				String[] colArr = cols.split(",");
				String[] titleArr = titles.split(",");
				String[] hiddenArr = hiddens.split(",");
				String[] colWidthArr = colWidths.split(",");
				if(colShowsTemp==null||colShowsTemp.size()==0){
					colShows = new ArrayList<ColShow>();
					for(int colIndex=0;colIndex<colArr.length;colIndex++){
						ColShow colShowTemp = new ColShow();
						colShowTemp.setCol(colArr[colIndex]);
						colShowTemp.setLabel(titleArr[colIndex]);
						colShowTemp.setShow(!Boolean.parseBoolean(hiddenArr[colIndex]));
						String width = colWidthArr[colIndex];
						if(width!=null&&!"".equals(width)){
							colShowTemp.setColWidth(width);
						}else{
							colShowTemp.setColWidth("80");
						}
						
						colShows.add(colShowTemp);
					}
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		return SUCCESS;
	}

	public String getColshowType() {
		return colshowType;
	}

	public void setColshowType(String colshowType) {
		this.colshowType = colshowType;
	}
}

