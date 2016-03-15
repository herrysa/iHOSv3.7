package com.huge.ihos.hr.sysTables.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.sysTables.dao.SysTableContentDao;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.system.configuration.bdinfo.dao.BdInfoDao;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("sysTableContentDao")
public class SysTableContentDaoHibernate extends
		GenericDaoHibernate<SysTableContent, String> implements
		SysTableContentDao {

	private BdInfoDao bdInfoDao;

	@Autowired
	public void setBdInfoDao(BdInfoDao bdInfoDao) {
		this.bdInfoDao = bdInfoDao;
	}

	public SysTableContentDaoHibernate() {
		super(SysTableContent.class);
	}

	public JQueryPager getSysTableContentCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, SysTableContent.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSysTableContentCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public void createTable(String code) {
		SysTableContent sysTableContent = this.get(code);
		String foreignTablePK = sysTableContent.getTableKind().getMainTablePK();
		int foreignTablePKLength = sysTableContent.getTableKind()
				.getMainTablePKLength();
		String sql = "";
		sql = "create table " + sysTableContent.getBdinfo().getTableName()
				+ " (code varchar(32) primary key,snapCode varchar(14),"
				+ foreignTablePK + " varchar(" + foreignTablePKLength
				+ ") not null)";
		excuteSql(sql);
	}

	@Override
	public void deleteTable(String code) {
		SysTableContent sysTableContent = this.get(code);
		String sql = "drop table " + sysTableContent.getBdinfo().getTableName();
		excuteSql(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTableContent> getFullSysTableContent(String orgCode,
			String tableKindCode) {
		// String hql = "from " + this.getPersistentClass().getSimpleName() +
		// " where orgCode=? and tableKind.id = ?";
		String hql = "from "
				+ this.getPersistentClass().getSimpleName()
				+ " where  tableKind.id = ?  and bdinfo.tableName <> 'v_hr_person_current'";
		hql += " ORDER BY sn ASC";
		List<SysTableContent> sysTableContents = this.getHibernateTemplate()
				.find(hql, tableKindCode);
		return sysTableContents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysTableContent> getTableContentByMainTable(String mainTable) {
		String hql = "from "
				+ this.getPersistentClass().getSimpleName()
				+ " where  tableKind.mainTable = ? and bdinfo.tableName <> 'v_hr_person_current'";
		hql += " ORDER BY sn ASC";
		List<SysTableContent> sysTableContents = this.getHibernateTemplate()
				.find(hql, mainTable);
		return sysTableContents;
	}

	@Override
	public List<BdInfo> getSingleFieldTableList(String statisticsCode) {
		List<BdInfo> bdInfos = new ArrayList<BdInfo>();
		String sql = "select bdInfoId,subset,subsetKindId,deptFK from sy_statistics_detail where parentCode ='"
				+ statisticsCode + "' and bdInfoId is not null";
		List<Map<String, Object>> queryDataList = new ArrayList<Map<String, Object>>();
		queryDataList = getBySqlToMap(sql);
		List<String> bdInfoIds = new ArrayList<String>();
		if (queryDataList != null && queryDataList.size() > 0) {
			for (Map<String, Object> dataMapTemp : queryDataList) {
				String bdInfoId = dataMapTemp.get("bdInfoId").toString();
				String subset = dataMapTemp.get("subset").toString();
				String subsetKindId = dataMapTemp.get("subsetKindId") == null ? ""
						: dataMapTemp.get("subsetKindId").toString();
				String deptFK = dataMapTemp.get("deptFK") == null ? ""
						: dataMapTemp.get("deptFK").toString();
				if (subset.equals("1")) {
					String hql = "from "
							+ this.getPersistentClass().getSimpleName()
							+ " where  tableKind.id = ?";
					hql += " ORDER BY sn ASC";
					List<SysTableContent> sysTableContents = new ArrayList<SysTableContent>();
					sysTableContents = this.getHibernateTemplate().find(hql,
							subsetKindId);
					if (sysTableContents != null && sysTableContents.size() > 0) {
						for (SysTableContent sysTableContent : sysTableContents) {
							BdInfo bdInfo = new BdInfo();
							bdInfo = sysTableContent.getBdinfo();
							String bdInfoIdTemp = bdInfo.getBdInfoId();
							if (!bdInfoIds.contains(bdInfoIdTemp)) {
								Boolean statisticsSingle = bdInfo
										.getStatisticsSingle();
								if (statisticsSingle != null
										&& statisticsSingle) {
									bdInfo.setBdInfoId(sysTableContent.getId());
									bdInfo.setBdInfoName(sysTableContent
											.getName());
									bdInfo.setTableAlias(subset);
									bdInfo.setRemark(deptFK);
									bdInfo.setEntityName(bdInfoId);
									bdInfos.add(bdInfo);
									bdInfoIds.add(bdInfoIdTemp);
								}
							}
						}
					}
				} else {
					BdInfo bdInfo = new BdInfo();
					if (!bdInfoIds.contains(bdInfoId)) {
						bdInfo = bdInfoDao.get(bdInfoId);
						Boolean statisticsSingle = bdInfo.getStatisticsSingle();
						if (statisticsSingle != null && statisticsSingle) {
							bdInfo.setTableAlias(subset);
							bdInfo.setRemark(deptFK);
							bdInfo.setEntityName(bdInfoId);
							bdInfos.add(bdInfo);
							bdInfoIds.add(bdInfoId);
						}
					}
				}
			}
		}
		return bdInfos;
	}

	public void deleteAllSubSetDataByFK(String mainTable, String foreignId) {
		String hql = "from " + this.getPersistentClass().getSimpleName()
				+ " where  tableKind.mainTable = ? and bdinfo.tableName <> ?";
		hql += " ORDER BY sn ASC";
		List<SysTableContent> sysTableContents = this.getHibernateTemplate()
				.find(hql, mainTable, mainTable);
		if (sysTableContents != null && sysTableContents.size() > 0) {
			for (SysTableContent sysTableContent : sysTableContents) {
				String foreignKey = sysTableContent.getTableKind()
						.getMainTablePK();
				String tableCode = sysTableContent.getBdinfo().getTableName();
				String sql = "delete from " + tableCode + " where "
						+ foreignKey + " ='" + foreignId + "'";
				excuteSql(sql);
			}
		}

	}
}
