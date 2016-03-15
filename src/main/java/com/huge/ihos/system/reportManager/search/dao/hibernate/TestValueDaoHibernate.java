package com.huge.ihos.system.reportManager.search.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.reportManager.search.dao.TestValueDao;
import com.huge.ihos.system.reportManager.search.model.TestValue;

@Repository( "testValueDao" )
public class TestValueDaoHibernate
    extends GenericDaoHibernate<TestValue, Long>
    implements TestValueDao {

    public TestValueDaoHibernate() {
        super( TestValue.class );
    }

    public List<TestValue> getAllTest() {
        String hql = "from TestValue t";
        List<TestValue> list = this.getHibernateTemplate().find( hql );
        return list;
    }
}
