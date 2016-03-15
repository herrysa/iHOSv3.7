package com.huge.ihos.indicatoranalysis.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.indicatoranalysis.dao.IndicatorDao;
import com.huge.ihos.indicatoranalysis.model.Indicator;

@Repository("indicatorDao")
public class IndicatorDaoHibernate extends GenericDaoHibernate<Indicator, String> implements IndicatorDao {

    public IndicatorDaoHibernate() {
        super(Indicator.class);
    }
}
