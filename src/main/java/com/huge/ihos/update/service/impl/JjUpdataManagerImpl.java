package com.huge.ihos.update.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.update.dao.JjUpdataDao;
import com.huge.ihos.update.model.JjUpdata;
import com.huge.ihos.update.service.JjAllotManager;
import com.huge.ihos.update.service.JjUpdataManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;





@Service("jjUpdataManager")
public class JjUpdataManagerImpl extends GenericManagerImpl<JjUpdata, Long> implements JjUpdataManager {
    private JjUpdataDao jjUpdataDao;
    private JjAllotManager jjAllotManager;
    
    @Autowired
    public void setJjAllotManager(JjAllotManager jjAllotManager) {
		this.jjAllotManager = jjAllotManager;
	}

	@Autowired
    public JjUpdataManagerImpl(JjUpdataDao jjUpdataDao) {
        super(jjUpdataDao);
        this.jjUpdataDao = jjUpdataDao;
    }
    
    public JQueryPager getJjUpdataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return jjUpdataDao.getJjUpdataCriteria(paginatedList,filters);
	}

	@Override
	public boolean isHaveUpdateRight(String personId) {
		return jjUpdataDao.isHaveUpdateRight(personId);
	}

	@Override
	public boolean isUpdataed(String checkPeriod, String deptId) {
		return jjUpdataDao.isUpdataed(checkPeriod, deptId);
	}

	@Override
	public boolean isExistUpdataed(String checkPeriod, String deptId) {
		return jjUpdataDao.isExistUpdataed(checkPeriod, deptId);
	}

	@Override
	public List<JjUpdata> findByDept(String checkPeriod, String deptId) {
		return jjUpdataDao.findByDept(checkPeriod, deptId);
	}

	@Override
	public Boolean inheritJiUpData(String hisPeriod, String checkPeriod,String deptId, String defColumnNames, String itemName,Person person,Boolean amount) throws Exception{
		/**
		 * 继承上报数据的过程
		 * 1：查找出历史数据(过滤掉已经停用的人员的数据)
		 * 2：查找出本期间数据(由初始化得来)
		 * 3：根据历史数据，对比本期间数据，存在对应关系，则更新本期间数据，不存在对应关系，则插入该条数据到本期间
		 * 4：最终的结果是历史数据与本期间数据的并集
		 */
		JdbcTemplate jtl = new JdbcTemplate( SpringContextHelper.getDataSource());
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_department.departmentId",deptId));
		filters.add(new PropertyFilter("EQB_person.disable","0"));
		filters.add(new PropertyFilter("EQS_checkperiod",hisPeriod));
		filters.add(new PropertyFilter("EQS_itemName",itemName));
		filters.add(new PropertyFilter("EQI_state","3"));
		List<JjUpdata> hisUpdatas = this.getByFilters(filters);
		JjUpdata jjUpdata = null;
		BigDecimal zjj = null;
		String sql = "";
		if(hisUpdatas==null || hisUpdatas.size()==0){
			return null;
		}
		try {
			for(JjUpdata hisUpdata:hisUpdatas){
				jjUpdata = getJjUpData(hisUpdata.getDepartment().getDepartmentId(),hisUpdata.getPerson().getPersonId(),checkPeriod,itemName);
				if(jjUpdata==null){ 
					jjUpdata = new JjUpdata();
					jjUpdata.setCheckperiod(checkPeriod);
					jjUpdata.setItemName(itemName);
					zjj = jjAllotManager.getRealDeptAmount(deptId, checkPeriod);
					jjUpdata.setZjj(zjj==null?new BigDecimal(0):zjj);
					jjUpdata.setDepartment(hisUpdata.getDepartment());
					jjUpdata.setOwnerdept(hisUpdata.getOwnerdept());
					jjUpdata.setPerson(hisUpdata.getPerson());
					jjUpdata.setState(0);
				}
				jjUpdata.setAmount(amount?hisUpdata.getAmount():new BigDecimal(0));
				jjUpdata.setOperator(person);
				jjUpdata.setOperatorName(person.getName());
				jjUpdata.setOperateDate(new Date());
				jjUpdata = this.save(jjUpdata);
				
				if(defColumnNames!=null){
					String sqlString = "select "+defColumnNames+" from jj_t_Updata where updataId='"+hisUpdata.getUpdataId()+"'";
					List<Map<String,Object>> list = jtl.queryForList( sqlString );
					Map<String,Object> defColumn = list.get(0);
					String defColmn = "";
					Iterator<Entry<String,Object>> ite = defColumn.entrySet().iterator();
					while(ite.hasNext()){
						Entry<String,Object> entry = ite.next();
						defColmn += entry.getKey() +" = "+entry.getValue() + ",";
					}
					if(!defColmn.equals("")){
						defColmn = OtherUtil.subStrEnd(defColmn, ",");
					}
					defColmn = "update jj_t_Updata set "+defColmn+" where updataId= '"+jjUpdata.getUpdataId()+"';";
					sql += defColmn;
				}
			}
			if(!sql.equals("")){
				this.jjUpdataDao.executeInheritSql(sql);
			}
			return true;
		} catch (DataAccessException e) {
			return false;
		}
	}
	
	private JjUpdata getJjUpData(String deptId,String personId,String checkPeriod,String itemName){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_department.departmentId",deptId));
		filters.add(new PropertyFilter("EQS_person.personId",personId));
		filters.add(new PropertyFilter("EQS_checkperiod",checkPeriod));
		filters.add(new PropertyFilter("EQS_itemName",itemName));
		filters.add(new PropertyFilter("EQI_state","0"));
		List<JjUpdata> jjUpdatas = this.getByFilters(filters);
		if(jjUpdatas!=null && jjUpdatas.size()==1){
			return jjUpdatas.get(0);
		}
		return null;
	}
}