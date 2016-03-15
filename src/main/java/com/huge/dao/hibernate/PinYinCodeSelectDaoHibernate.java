package com.huge.dao.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.huge.dao.PinYinCodeSelectDao;
import com.huge.ihos.inout.model.ChargeItem;
import com.huge.ihos.inout.model.ChargeType;
import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.system.configuration.dictionary.model.Dictionary;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;

@Repository( "pinYinCodeSelectDao" )
public class PinYinCodeSelectDaoHibernate
    extends GenericDaoHibernate
    implements PinYinCodeSelectDao {

    public PinYinCodeSelectDaoHibernate() {
        super( PinYinCodeSelectDaoHibernate.class );
    }

    public String testPinYinFunction( String words ) {
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();

        String hql = "select dic.name as name ,dic.code as code from Dictionary as dic where dic.cnCode like '%o%'";
        Query sq = session.createQuery( hql );
        List l = sq.list();
        return "work" + words;
    }

    public Map getDictionaryByPinYinCode( String code ) {
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        String hql = "from Dictionary as dic where dic.cnCode like '%" + code + "%'";
        Query sq = session.createQuery( hql );
        List l = sq.list();
        //		if (l.size() == 0) {
        //			hql = "from Dictionary as dic where dic.name like '%" + code + "%'";
        //			sq = session.createQuery(hql);
        //			l = sq.list();
        //		}

        Map map = new HashMap();
        Iterator itr = l.iterator();
        while ( itr.hasNext() ) {
            Dictionary dic = (Dictionary) itr.next();
            map.put( dic.getCode(), dic.getName() );
        }

        return map;
    }

    public Map getAllDepartmentByPinYinCode( String code ) {
        String hql = "from Department as dept where dept.disabled=false and dic.cnCode like '%" + code + "%'";
        return this.getDeptByPinYin( hql, code );
    }

    public Map getDepartmentByPinYinCode( String code ) {
        String hql = "from Department as dept where dept.disabled=false and dept.leaf=true and dic.cnCode like '%" + code + "%'";
        return this.getDeptByPinYin( hql, code );
    }

    private Map getDeptByPinYin( String hql, String code ) {
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        //String hql = "from Department as dept where dept.enabled=true and dept.leaf=true and func_getpycodes(dept.name) like '%" + code + "%'";
        Query sq = session.createQuery( hql );
        List l = sq.list();
        //		if (l.size() == 0) {
        //			hql = "from Department as dept  where dept.enabled=true and dept.leaf=true and dept.name like '%" + code + "%'";
        //			sq = session.createQuery(hql);
        //			l = sq.list();
        //		}
        //		
        Map map = new HashMap();
        Iterator itr = l.iterator();
        while ( itr.hasNext() ) {
            Department dic = (Department) itr.next();
            map.put( dic.getDepartmentId(), dic.getDepartmentId() + " " + dic.getDeptCode() + " " + dic.getName() );
        }

        return map;
    }

    public Map getPersonByPinYinCode( String code ) {
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        String hql = "from Person as per where per.enabled=true  and func_getpycodes(per.name) like '%" + code + "%'";
        Query sq = session.createQuery( hql );
        List l = sq.list();
        //		if (l.size() == 0) {
        //			hql = "from Person as per  where per.enabled=true  and per.name like '%" + code + "%'";
        //			sq = session.createQuery(hql);
        //			l = sq.list();
        //		}

        Map map = new HashMap();
        Iterator itr = l.iterator();
        while ( itr.hasNext() ) {
            Person dic = (Person) itr.next();
            map.put( dic.getPersonId(), dic.getPersonId() + "   " + dic.getPersonCode() + "   " + dic.getName() );
        }

        return map;
    }

    public Map getChargeTypeByPinYinCode( String code ) {
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        String hql = "from ChargeType as per where per.disabled=false  and per.cnCode like '%" + code + "%'";
        Query sq = session.createQuery( hql );
        List l = sq.list();
        //		if (l.size() == 0) {
        //			hql = "from ChargeType as per  where per.disabled=false  and per.chargeTypeName like '%" + code + "%'";
        //			sq = session.createQuery(hql);
        //			l = sq.list();
        //		}

        Map map = new HashMap();
        Iterator itr = l.iterator();
        while ( itr.hasNext() ) {
            ChargeType dic = (ChargeType) itr.next();
            map.put( dic.getChargeTypeId(), dic.getChargeTypeId() + "   " + dic.getPayinItem().getPayinItemName() + "   " + dic.getChargeTypeName() );
            //map.put(dic.getChargeTypeId(), dic.getChargeTypeId()+"   "+dic.getChargeTypeName());
        }

        return map;
    }

    public Map getChargeItemByPinYinCodeAndChargeType( String code, String chargeType ) {
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        String hql =
            "from ChargeItem as per where per.disabled=false and per.chargeType.chargeTypeId='" + chargeType + "'  and per.pyCode like '%" + code
                + "%'";
        Query sq = session.createQuery( hql );
        List l = sq.list();
        //		if (l.size() == 0) {
        //			hql = "from ChargeItem as per  where per.disabled=false  and per.chargeItemName like '%" + code + "%'";
        //			sq = session.createQuery(hql);
        //			l = sq.list();
        //		}
        //		
        Map map = new HashMap();
        Iterator itr = l.iterator();
        while ( itr.hasNext() ) {
            ChargeItem dic = (ChargeItem) itr.next();
            map.put( dic.getChargeItemNo(), dic.getChargeItemId() + "   " + dic.getChargeItemName() );
        }

        return map;
    }

    public Map getCostItemByPinYinCode( String code ) {
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        String hql = "from CostItem as per where per.disabled=false  and per.cnCode like '%" + code + "%'";
        Query sq = session.createQuery( hql );
        List l = sq.list();
        //		if (l.size() == 0) {
        //			hql = "from CostItem as per  where per.disabled=false  and per.costItemName like '%" + code + "%'";
        //			sq = session.createQuery(hql);
        //			l = sq.list();
        //		}

        Map map = new HashMap();
        Iterator itr = l.iterator();
        while ( itr.hasNext() ) {
            CostItem dic = (CostItem) itr.next();
            map.put( dic.getCostItemId(), dic.getCostItemId() + "   " + dic.getCostItemName() );
        }

        return map;
    }
}
