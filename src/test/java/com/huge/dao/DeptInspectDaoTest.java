package com.huge.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.huge.ihos.kaohe.dao.DeptInspectDao;
import com.huge.ihos.kaohe.model.DeptInspect;
import com.huge.ihos.kaohe.model.KpiConstants;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
/**
 * 
SET IDENTITY_INSERT dbo.KH_deptinspect ON;
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (1, '201206', '临床手术有病房', '月', null, null, null, 100000.00, 1.20, null, null, '', '', '', 0, 'D0000909912', 'D00900700', null, null, null, null, null, null, '112', '55');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (2, '201206', '临床手术有病房', '月', null, null, null, 450000.00, 5.40, null, null, '', '', '', 0, 'D0000909912', 'D00900700', null, null, null, null, null, null, '112', '54');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (3, '201206', '临床手术有病房', '月', null, null, null, 50000.00, 50.00, null, null, '', '', '', 0, 'D00110600', 'D00900800', null, null, null, null, null, null, 'sdsds', '89');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (4, '201206', '临床手术有病房', '月', null, null, null, 450000.00, 450.00, null, null, '', '', '', 0, 'D00110600', 'D00900800', null, null, null, null, null, null, 'sdsds', '87');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (5, '201206', '临床手术有病房', '月', null, null, null, 450000.00, 450.00, null, null, '', '', '', 2, 'D00110600', 'D00900800', null, null, null, null, null, null, 'sdsds', '84');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (6, '201206', '临床手术有病房', '月', null, null, null, 50000.00, 50.00, null, null, '', '', '', 2, 'D00110600', 'D00900800', null, null, null, null, null, null, 'sdsds', '83');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (7, '201206', '临床手术有病房', '月', null, null, null, 50000.00, 50.00, null, null, '', '', '', 0, 'D00110500', 'D00900800', null, null, null, null, null, null, 'sdsds', '89');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (8, '201206', '临床手术有病房', '月', null, null, null, 450000.00, 450.00, null, null, '', '', '', 0, 'D00110500', 'D00900800', null, null, null, null, null, null, 'sdsds', '87');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (9, '201206', '临床手术有病房', '月', null, null, null, 450000.00, 450.00, null, null, '', '', '', 2, 'D00110500', 'D00900800', null, null, null, null, null, null, 'sdsds', '84');
Insert into dbo.KH_deptinspect(deptinspectId, checkperiod, jjdepttypeid, periodType, tValue, aValue, dscore, weight, score, money1, money2, remark, remark2, remark3, state, deptid, inspectdeptid, operatorId, operatorId1, operatorId2, operateDate, operateDate1, operateDate2, inspectmodelID, KPIID) values 
 (10, '201206', '临床手术有病房', '月', null, null, null, 50000.00, 50.00, null, null, '', '', '', 2, 'D00110500', 'D00900800', null, null, null, null, null, null, 'sdsds', '83');
SET IDENTITY_INSERT dbo.KH_deptinspect OFF;
UPDATE KH_deptinspect SET state=0; 
UPDATE KH_deptinspect SET state=2 where deptinspectId IN (5,6,9,10);
 
 
 
 
 
 *
 */
public class DeptInspectDaoTest
    extends BaseDaoTestCase {
    
    String deptId="D00200900", period="201207";
    @Autowired
    private DeptInspectDao deptInspectDao;
    
   // @Test
    public void testGetShouldConfirmInspectCount(){
        //SELECT COUNT(a.deptinspectId)  from KH_deptinspect  a  where a.checkperiod='201207' and a.inspectdeptid='D00900800'   8
       int count= this.deptInspectDao.getShouldConfirmedInspectCount( deptId, period );
        Assert.assertEquals( 10, count );
      /*   count= this.deptInspectDao.getShouldConfirmedInspectCount( "D00900700", period );
        Assert.assertEquals( 2, count );*/
    }
  //  @Test
    public void testGetConfirmedInstectCount(){
        int count= this.deptInspectDao.getConfirmedInstectCount(deptId, period );
        Assert.assertEquals( 4, count );
    }
  //  @Test
    public void testGetShouldConfirmdItemCount(){
        int count= this.deptInspectDao.getShouldConfirmdItemCount( deptId, period );
        Assert.assertEquals( 4, count );
    }
    
  //  @Test
    public void testgetConfirmedItemCount(){
        int count= this.deptInspectDao.getConfirmedItemCount(  deptId, period );
        Assert.assertEquals( 2, count ); 
    }
  //  @Test
    public void testCheckInspects(){
        
        Long[] inIds={6l,7l,3l,4l,5l};
       this.deptInspectDao.checkInspects( inIds );
       
       
       int c = this.deptInspectDao.getInspectCountByPeriodAndDeptAndState(  period ,  deptId, KpiConstants.BSC_STATE_CHECKED);
       Assert.assertEquals( 5, c );
       c = this.deptInspectDao.getInspectCountByPeriodAndDeptAndState(  period ,  deptId, KpiConstants.BSC_STATE_NEW);
       Assert.assertEquals( 1, c );
       c = this.deptInspectDao.getInspectCountByPeriodAndDeptAndState(  period ,  deptId, KpiConstants.BSC_STATE_CONFIRMED);
       Assert.assertEquals( 2, c );
    }
    
   // @Test
    public void testSaveCheckRemark(){
        this.deptInspectDao.saveCheckRemark( 1l, "test" );
        DeptInspect di =this.deptInspectDao.get( 1l);
        
        Assert.assertEquals( "test", di.getRemark2() );
    }
    
   // @Test
    public void testRejectFromCheck(){
        Long[] itemIds={83l,84l};
        
        this.deptInspectDao.rejectFromCheck( deptId, period, itemIds);
        int c = this.deptInspectDao.getInspectCountByPeriodAndDeptAndState(  period ,  deptId, KpiConstants.BSC_STATE_USED);
        Assert.assertEquals( 4, c );
        
    }
    
    @Test
    public void testGetShouldCheckItemList(){
      List l=  this.deptInspectDao.getShouldCheckItemList( deptId, period );
      
      Assert.assertNotNull( l );
    }
    
    @Test
    public void testgetDeptInspectCheckCriteria(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        JQueryPager pagedRequests = null;
        PagerFactory pagerFactory = new PagerFactory();
        pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, request);
        
        JQueryPager jp= this.deptInspectDao.getDeptInspectCheckCriteria( pagedRequests, filters, deptId, period, 84l );
        Assert.assertNotNull( jp);
    }
}
