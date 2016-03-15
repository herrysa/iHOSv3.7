package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.search.dao.TestDateDao;
import com.huge.ihos.system.reportManager.search.model.TestDate;

@Repository( "testDateDao" )
public class TestDateDaoHibernate
    extends GenericDaoHibernate<TestDate, Long>
    implements TestDateDao {

    public TestDateDaoHibernate() {
        super( TestDate.class );
    }

    public List<TestDate> getAllTest() {
        String hql = "from TestDate t";
        //List<Test> list=this.getHibernateTemplate().find(hql);
        return this.getHibernateTemplate().find( hql );
    }
}
