package com.huge.ihos.cb.exinterface;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huge.ihos.inout.dao.SourcecostDao;
import com.huge.ihos.inout.dao.SourcepayinDao;
import com.huge.ihos.inout.dao.SpecialSourceDao;
import com.huge.ihos.inout.model.Sourcecost;
import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.ihos.inout.model.SpecialSource;
import com.huge.ihos.system.exinterface.GetCBResource;

@Component
public class RealizeGetCBResource extends GetCBResource {
	private SourcepayinDao sourcepayinDao;
	private SourcecostDao sourcecostDao;
	private SpecialSourceDao specialSourceDao;

	@Autowired
	public void setSourcepayinDao(SourcepayinDao sourcepayinDao) {
		this.sourcepayinDao = sourcepayinDao;
	}

	@Autowired
	public void setSourcecostDao(SourcecostDao sourcecostDao) {
		this.sourcecostDao = sourcecostDao;
	}

	@Autowired
	public void setSpecialSourceDao(SpecialSourceDao specialSourceDao) {
		this.specialSourceDao = specialSourceDao;
	}

	@Override
	public Boolean cbModelStatusClose(String period) {
		try {
			Criteria criteria = null;
			criteria = sourcepayinDao.getCriteria();
			List<Sourcepayin> sourcepayins = criteria.add(Restrictions.eq("checkPeriod", period)).list();
			if (sourcepayins != null && !sourcepayins.isEmpty()) {
				for (Sourcepayin sourcepayin : sourcepayins) {
					sourcepayin.setStatus("1");
					sourcepayinDao.save(sourcepayin);
				}
			}
			criteria = sourcecostDao.getCriteria();
			List<Sourcecost> sourcecosts = criteria.add(Restrictions.eq("checkPeriod", period)).list();
			if (sourcecosts != null && !sourcecosts.isEmpty()) {
				for (Sourcecost sourcecost : sourcecosts) {
					sourcecost.setStatus("1");
					sourcecostDao.save(sourcecost);
				}
			}
			criteria = specialSourceDao.getCriteria();
			List<SpecialSource> specialSources = criteria.add(Restrictions.eq("checkPeriod", period)).list();
			if (specialSources != null && !specialSources.isEmpty()) {
				for (SpecialSource specialSource : specialSources) {
					specialSource.setStatus("1");
					specialSourceDao.save(specialSource);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public Boolean cbModelStatusAntiClose(String period) {
		try {
			Criteria criteria = null;
			criteria = sourcepayinDao.getCriteria();
			List<Sourcepayin> sourcepayins = criteria.add(Restrictions.eq("checkPeriod", period)).list();
			if (sourcepayins != null && !sourcepayins.isEmpty()) {
				for (Sourcepayin sourcepayin : sourcepayins) {
					sourcepayin.setStatus("0");
					sourcepayinDao.save(sourcepayin);
				}
			}
			criteria = sourcecostDao.getCriteria();
			List<Sourcecost> sourcecosts = criteria.add(Restrictions.eq("checkPeriod", period)).list();
			if (sourcecosts != null && !sourcecosts.isEmpty()) {
				for (Sourcecost sourcecost : sourcecosts) {
					sourcecost.setStatus("0");
					sourcecostDao.save(sourcecost);
				}
			}
			criteria = specialSourceDao.getCriteria();
			List<SpecialSource> specialSources = criteria.add(Restrictions.eq("checkPeriod", period)).list();
			if (specialSources != null && !specialSources.isEmpty()) {
				for (SpecialSource specialSource : specialSources) {
					specialSource.setStatus("0");
					specialSourceDao.save(specialSource);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
