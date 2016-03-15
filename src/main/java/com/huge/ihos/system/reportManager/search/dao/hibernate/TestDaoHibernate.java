package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.search.dao.TestDao;
import com.huge.ihos.system.reportManager.search.model.Test;

@Repository( "testDao" )
public class TestDaoHibernate
    extends GenericDaoHibernate<Test, Long>
    implements TestDao {

    public TestDaoHibernate() {
        super( Test.class );
    }

    public List<Test> getAllTest() {
        String hql = "from Test t";
        List<Test> list = this.getHibernateTemplate().find( hql );
        return list;
    }
}
