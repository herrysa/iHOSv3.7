package com.huge.ihos.hr.webapp.ation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.huge.ihos.hr.pact.model.Pact;
import com.huge.ihos.hr.pact.service.PactManager;
import com.huge.ihos.hr.query.model.QueryCommon;
import com.huge.ihos.hr.query.service.QueryCommonManager;
import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.ihos.hr.statistics.service.StatisticsItemManager;
import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.oa.bulletin.service.BulletinManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class HrMainPageAction extends JqGridBaseAction implements Preparable {

	private PactManager pactManager;
	private StatisticsItemManager statisticsItemManager;
	private QueryCommonManager queryCommonManager;
	private BulletinManager bulletinManager;
	private List<Pact> pacts;
	private List<StatisticsItem> statisticsItems;
	private List<QueryCommon> queryCommons;
	private List<Bulletin> bulletins;
	private String pactExpireDayS;
	
	public void setPactManager(PactManager pactManager) {
		this.pactManager = pactManager;
	}

	public void setStatisticsItemManager(StatisticsItemManager statisticsItemManager) {
		this.statisticsItemManager = statisticsItemManager;
	}

	public void setQueryCommonManager(QueryCommonManager queryCommonManager) {
		this.queryCommonManager = queryCommonManager;
	}
	
	public void setBulletinManager(BulletinManager bulletinManager) {
		this.bulletinManager = bulletinManager;
	}

	public List<StatisticsItem> getStatisticsItems() {
		return statisticsItems;
	}

	public void setStatisticsItems(List<StatisticsItem> statisticsItems) {
		this.statisticsItems = statisticsItems;
	}

	public List<QueryCommon> getQueryCommons() {
		return queryCommons;
	}

	public void setQueryCommons(List<QueryCommon> queryCommons) {
		this.queryCommons = queryCommons;
	}

	public List<Pact> getPacts() {
		return pacts;
	}

	public void setPacts(List<Pact> pacts) {
		this.pacts = pacts;
	}

	public List<Bulletin> getBulletins() {
		return bulletins;
	}

	public void setBulletins(List<Bulletin> bulletins) {
		this.bulletins = bulletins;
	}

	public String getPactExpireDayS() {
		return pactExpireDayS;
	}

	public void setPactExpireDayS(String pactExpireDayS) {
		this.pactExpireDayS = pactExpireDayS;
	}
	
	public String hrMainPageInfo(){
		try {
			pacts = getExpirePacts();
			statisticsItems = getCommonStatic();
			queryCommons = getCommonQuery();
			this.currentUser = this.getSessionUser();
			bulletins = bulletinManager.getBulletinsByUser( currentUser );
		} catch (Exception e) {
			log.error("pactExpireGridList Error", e);
		}
		return SUCCESS;
	}
	
	public List<Pact> getExpirePacts(){
		List<Pact> expirePacts = null;
		try {
			Calendar cal = Calendar.getInstance();
			String nowDate = DateUtil.getDateNow(cal.getTime());
			if(OtherUtil.measureNull(pactExpireDayS)){
				pactExpireDayS = "30";
			}
			Integer pactExpireDay = Integer.parseInt(pactExpireDayS);
			cal.add(Calendar.DAY_OF_YEAR, pactExpireDay);
			String expireDate = DateUtil.getDateNow(cal.getTime());
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQI_signState","3"));
			filters.add(new PropertyFilter("EQB_hrPerson.disable","0"));
			filters.add(new PropertyFilter("LED_endDate",expireDate));
			filters.add(new PropertyFilter("GED_endDate",nowDate));
			expirePacts = pactManager.getByFilters(filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expirePacts;
	}
	
	public List<StatisticsItem> getCommonStatic(){
		List<StatisticsItem> commonStatics = null;
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQB_disabled","false"));
			filters.add(new PropertyFilter("ODS_code",""));
			commonStatics = statisticsItemManager.getByFilters(filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonStatics;
	}
	
	public List<QueryCommon> getCommonQuery(){
		List<QueryCommon> commonQuerys = null;
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQB_disabled","false"));
			filters.add(new PropertyFilter("ODS_sn",""));
			commonQuerys = queryCommonManager.getByFilters(filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonQuerys;
	}
	
	
}
