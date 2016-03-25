package com.huge.ihos.accounting.voucher.dao.hibernate;



import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.voucher.dao.VoucherDao;
import com.huge.ihos.accounting.voucher.model.Voucher;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("voucherDao")
public class VoucherDaoHibernate extends GenericDaoHibernate<Voucher, String> implements VoucherDao {

    public VoucherDaoHibernate() {
        super(Voucher.class);
    }
    
    public JQueryPager getVoucherCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("voucherId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Voucher.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getVoucherCriteria", e);
			return paginatedList;
		}

	}

	@Override
	public List<Voucher> getBysSysVariable(SystemVariable systemVariable,String voucherType,Integer voucherNo) {
		//this.getHibernateTemplate().enableFilter( "sysVariable" ).setParameter( "orgCode", UserContextUtil.getUserContext().getOrgCode() ).setParameter( "copyCode", UserContextUtil.getUserContext().getCopyCode()).setParameter( "periodYear", UserContextUtil.getUserContext().getPeriodYear() ).setParameter( "periodMonth", systemVariable.getPeriod() );
		this.getHibernateTemplate().enableFilter( "sysVariable" ).setParameter( "orgCode", "T001" ).setParameter( "copyCode", "001").setParameter( "periodYear", UserContextUtil.getUserContext().getPeriodYear() ).setParameter( "periodMonth", UserContextUtil.getUserContext().getPeriodMonth() );
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("voucherType", voucherType));
		if(voucherNo!=null){
			criteria.add(Restrictions.eq("voucherNo", voucherNo));
		}
		criteria.addOrder(Order.desc("voucherNo"));
		return criteria.list();
	}

	@Override
	public List<Map<String, String>> getAccountCollect(Map<String, String> getParams) {
		
		String sql = "select acct.acctcode,vc.accountId,acct.acctName,sum(vc.jie) lendMoney,sum(vc.dai) loanMoney  from ("
					+"select  accountId,voucherId,'借' as directionJ, CASE direction when '借' then money else 0 end as jie,'贷' as directionD,  case direction when '贷' then money else 0 end as dai from GL_voucherDetail "
					+") vc inner join GL_account acct on vc.accountId = acct.acctId inner join GL_voucher v on v.voucherId = vc.voucherId ";
		sql += "where";
		if(getParams.get("beginDate")!=null){
			sql += " v.voucherDate >= '"+getParams.get("beginDate")+"' and v.voucherDate <= '"+getParams.get("endDate")+"'";
			sql += " and ";
		}else{
			sql += " v.periodMonth = '"+getParams.get("periodMonth")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherFrom")!=null){
			sql += " v.voucherFromCode = '"+getParams.get("voucherFrom")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherType")!=null){
			sql += " v.voucherType = '"+getParams.get("voucherType")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherNoBegin")!=null){
			sql += " v.voucherNo >= "+getParams.get("voucherNoBegin")+" and v.voucherDate <= "+getParams.get("voucherNoEnd")+" ";
			sql += " and ";
		}
		if(getParams.get("orgCode")!=null){
			sql += "v.orgCode='"+getParams.get("orgCode")+"' and v.copycode='"+getParams.get("copyCode")+"' and v.periodYear='"+getParams.get("periodYear")+"' ";
		}
		sql += "group by vc.accountId,acct.acctName,acct.acctcode "
					+"UNION SELECT '合计:','','',SUM(total.lendMoney),SUM(total.loanMoney) FROM (select acct.acctcode,vc.accountId,acct.acctName,sum(vc.jie) lendMoney,sum(vc.dai) loanMoney  from ("
					+"select  accountId,voucherId,'借' as directionJ, CASE direction when '借' then money else 0 end as jie,'贷' as directionD,  case direction when '贷' then money else 0 end as dai from GL_voucherDetail "
					+") vc inner join GL_account acct on vc.accountId = acct.acctId inner join GL_voucher v on v.voucherId = vc.voucherId ";
		sql += "where";
		if(getParams.get("beginDate")!=null){
			sql += " v.voucherDate >= '"+getParams.get("beginDate")+"' and v.voucherDate <= '"+getParams.get("endDate")+"'";
			sql += " and ";
		}else{
			sql += " v.periodMonth = '"+getParams.get("periodMonth")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherFrom")!=null){
			sql += " v.voucherFromCode = '"+getParams.get("voucherFrom")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherType")!=null){
			sql += " v.voucherType = '"+getParams.get("voucherType")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherNoBegin")!=null){
			sql += " v.voucherNo >= "+getParams.get("voucherNoBegin")+" and v.voucherDate <= "+getParams.get("voucherNoEnd")+" ";
			sql += " and ";
		}
		if(getParams.get("orgCode")!=null){
			sql += "v.orgCode='"+getParams.get("orgCode")+"' and v.copycode='"+getParams.get("copyCode")+"' and v.periodYear='"+getParams.get("periodYear")+"' ";
		}
		sql +=  "group by vc.accountId,acct.acctName,acct.acctcode) AS total";
		SQLQuery sqlQuery = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	@Override
	public List<Voucher> getByState(SystemVariable sysVariable, Integer state,
			String type) {
		this.getHibernateTemplate().enableFilter( "sysVariable" ).setParameter( "orgCode", UserContextUtil.getUserContext().getOrgCode() ).setParameter( "copyCode", UserContextUtil.getUserContext().getCopyCode()).setParameter( "periodYear", UserContextUtil.getUserContext().getPeriodYear() ).setParameter( "periodMonth", sysVariable.getPeriod() );
		Criteria criteria = this.getCriteria();
		if(OtherUtil.measureNotNull(type)){
			if("eq".equals(type)){
				criteria.add(Restrictions.eq("status", state));
			}else if("le".equals(type)){
				criteria.add(Restrictions.le("status", state));
			}else if("ge".equals(type)){
				criteria.add(Restrictions.ge("status", state));
			}
		}else{
			criteria.add(Restrictions.eq("status", state));
		}
		criteria.addOrder(Order.desc("voucherNo"));
		return criteria.list();
	}

	@Override
	public List<Map<String, String>> getAccountCollectBalance(
			Map<String, String> getParams) {
			
		String sql = "select fasheng.acctCode,fasheng.acctId,fasheng.acctName,qcye.initBalance,qcye.monthBalance,fasheng.jie as 'bqLend',fasheng.dai as 'bqLoan',ljfasheng.ljLend,ljfasheng.ljLoan, fasheng.jie-fasheng.dai+qcye.monthBalance AS 'ye' from "
					+"(select vc.accountId as acctId,acct.acctCode as acctCode,acct.acctName as acctName,sum(vc.jie) jie,sum(vc.dai) dai  from ("
					+"select  accountId,voucherId,'借' as directionJ, CASE direction when '借' then money else 0 end as jie,'贷' as directionD,  case direction when '贷' then money else 0 end as dai from GL_voucherDetail "
					+") vc inner join GL_account acct on vc.accountId = acct.acctId inner join GL_voucher v on v.voucherId = vc.voucherId  ";
		sql += " where ";
		if(getParams.get("orgCode")!=null){
			sql += " v.orgCode='"+getParams.get("orgCode")+"' and v.copycode='"+getParams.get("copyCode")+"' and v.periodYear='"+getParams.get("periodYear")+"' ";
			sql += " and ";
		}
		if(getParams.get("periodMonth")!=null){
			sql += " v.periodMonth = '"+getParams.get("periodMonth")+"' ";
		}
		sql += "group by vc.accountId,acct.acctName,acct.acctCode ) fasheng INNER JOIN "
				+"(select gb.initBalance,gb.acctId,gbp.monthBalance FROM GL_balance gb INNER JOIN GL_balance_period  gbp ON gb.balanceId=gbp.balanceId WHERE gbp.periodMonth='"+getParams.get("periodMonth")+"' AND orgCode='"+getParams.get("orgCode")+"' AND copyCode='"+getParams.get("copyCode")+"') qcye "
				+"on fasheng.acctId = qcye.acctId "
				+"INNER JOIN (select gb.acctId,sum(gbp.lend) ljLend,sum(gbp.loan) ljLoan FROM GL_balance gb INNER JOIN GL_balance_period  gbp ON gb.balanceId=gbp.balanceId WHERE gbp.periodMonth<='"+getParams.get("periodMonth")+"' AND orgCode='"+getParams.get("orgCode")+"' AND copyCode='"+getParams.get("copyCode")+"' GROUP BY gb.acctId) ljfasheng ON fasheng.acctId=ljfasheng.acctId ";

		/*if(getParams.get("beginDate")!=null){
			sql += " v.voucherDate >= '"+getParams.get("beginDate")+"' and v.voucherDate <= '"+getParams.get("endDate")+"'";
			sql += " and ";
		}else{
			sql += " v.periodMonth = '"+getParams.get("periodMonth")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherFrom")!=null){
			sql += " v.voucherFromCode = '"+getParams.get("voucherFrom")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherType")!=null){
			sql += " v.voucherType = '"+getParams.get("voucherType")+"'";
			sql += " and ";
		}
		if(getParams.get("voucherNoBegin")!=null){
			sql += " v.voucherNo >= "+getParams.get("voucherNoBegin")+" and v.voucherDate <= "+getParams.get("voucherNoEnd")+" ";
			sql += " and ";
		}*/
		
		SQLQuery sqlQuery = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	@Override
	public List<Map<String, String>> getAccountBalance(
			JQueryPager pagedRequests, Map<String, String> getParams) {
		String sql = "SELECT * FROM ( SELECT gb.kjYear 'periodMonth','1' 'num','期初余额' 'message',gb.beginJ 'lendMoney',gb.beginD 'loanMoney',gb.initBalance 'balance' FROM GL_balance_period gbp INNER JOIN GL_balance gb ON gbp.balanceId=gb.balanceId WHERE gb.orgCode='"+getParams.get("orgCode")+"' and gb.copyCode='"+getParams.get("copyCode")+"' AND gb.kjYear IN ("+getParams.get("periodYear")+") AND gb.acctCode='"+getParams.get("acctCode")+"' "
					+" UNION "
					+"SELECT gbp.periodMonth 'periodMonth','2' 'num','本月合计' 'message',gbp.lend 'lendMoney',gbp.loan 'loanMoney',gbp.lend-gbp.loan+gbp.monthBalance 'balance' FROM GL_balance_period gbp INNER JOIN GL_balance gb ON gbp.balanceId=gb.balanceId WHERE gb.orgCode='"+getParams.get("orgCode")+"' and gb.copyCode='"+getParams.get("copyCode")+"' AND gb.kjYear in ("+getParams.get("periodYear")+") AND gb.acctCode='"+getParams.get("acctCode")+"' AND gbp.periodMonth>='"+getParams.get("beginPeriod")+"' AND gbp.periodMonth<='"+getParams.get("endPeriod")+"' "
					+" UNION "
					+"SELECT gbp.periodMonth 'periodMonth','3' 'num','本年累计' 'message',(SELECT SUM(lend) FROM GL_balance_period gbpm INNER JOIN GL_balance gbm ON gbpm.balanceId=gbm.balanceId  WHERE gbpm.periodMonth<=gbp.periodMonth and gbm.orgCode='"+getParams.get("orgCode")+"' and gbm.copyCode='"+getParams.get("copyCode")+"' AND gbm.kjYear in ("+getParams.get("periodYear")+") AND gbm.acctCode='"+getParams.get("acctCode")+"' ) 'lendMoney', "
					+"(SELECT SUM(loan) FROM GL_balance_period gbpm INNER JOIN GL_balance gbm ON gbpm.balanceId=gbm.balanceId  WHERE gbpm.periodMonth<=gbp.periodMonth and gbm.orgCode='"+getParams.get("orgCode")+"' and gbm.copyCode='"+getParams.get("copyCode")+"' AND gbm.kjYear IN ("+getParams.get("periodYear")+") AND gbm.acctCode='"+getParams.get("acctCode")+"' ) 'loanMoney' "
					+",gbp.lend-gbp.loan+gbp.monthBalance 'balance' "
					+"FROM GL_balance_period gbp INNER JOIN GL_balance gb ON gbp.balanceId=gb.balanceId WHERE gb.orgCode='"+getParams.get("orgCode")+"' and gb.copyCode='"+getParams.get("copyCode")+"' AND gb.kjYear in ("+getParams.get("periodYear")+") AND gb.acctCode='"+getParams.get("acctCode")+"' AND gbp.periodMonth>='"+getParams.get("beginPeriod")+"' AND gbp.periodMonth<='"+getParams.get("endPeriod")+"') mm";
		SQLQuery sqlQuery = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		pagedRequests.setTotalNumberOfRows(sqlQuery.list().size());
		sqlQuery.setFirstResult(pagedRequests.getStart());
		sqlQuery.setMaxResults(pagedRequests.getPageSize());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	
	@Override
	public List<Map<String, String>> getAccountBalanceDetail(
			JQueryPager pagedRequests, Map<String, String> getParams) {
		String voucherNoSql ="SELECT voucherNo FROM GL_voucher GROUP BY voucherNo";
		SQLQuery sqlQueryNo = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(voucherNoSql);
		sqlQueryNo.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, String>> voucherNoList = sqlQueryNo.list();
		String name = "CONVERT(VARCHAR(3),b.voucherNo)+','+CONVERT(VARCHAR(3),c.voucherNo)";
		String leftJoins = "";
		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		char[] c = s.toCharArray();  
		for(Map<String, String> voucherNo :voucherNoList){
			Random random1 = new Random();  
			Random random2 = new Random();  
			Random random3 = new Random();  
			String vn = ""+c[random1.nextInt(c.length)]+c[random2.nextInt(c.length)]+c[random3.nextInt(c.length)];
			name += "CONVERT(VARCHAR(4),"+vn+".voucherNo)+','+";
			leftJoins += " LEFT JOIN (SELECT voucherDate,voucherNo FROM GL_voucher WHERE  voucherNo=1 AND orgCode='"+getParams.get("orgCode")+"' AND copyCode='"+getParams.get("copyCode")+"' AND periodYear='"+getParams.get("periodYear")+"' ) "+vn+" ON a.voucherDate="+vn+".voucherDate";
		}
		if("".equals(name)){
			name = name.substring(0,name.length()-5);
		}
		String sql = "SELECT a.voucherDate,a.voucherType+'凭证:'+"+name+" voucherNoStr,a.lend,a.loan,a.balance FROM ("
					+"select v.voucherDate as voucherDate,v.voucherType voucherType,sum(vc.jie) lend,sum(vc.dai) loan,("
					+"SELECT sum(jie)-sum(dai)+avg(monthBalance) monthBalance FROM "
					+"(SELECT periodMonth,voucherDate,'借' as directionJ, CASE gvd.direction when '借' then gvd.money else 0 end as jie,'贷' as directionD,  case gvd.direction when '贷' then gvd.money else 0 end as dai "
					+"FROM GL_voucher gv INNER JOIN GL_voucherDetail gvd ON gv.voucherId=gvd.voucherId WHERE gvd.accountId='008_t00101_2013_1001') gvv "
					+"INNER JOIN "
					+"(SELECT gbp.periodMonth,gbp.monthBalance FROM GL_balance gb INNER JOIN GL_balance_period gbp ON gb.balanceId=gbp.balanceId WHERE gb.orgCode='t00101' AND gb.copyCode='008' AND gb.kjYear='2013' AND gb.acctCode='1001' ) bal "
					+"ON gvv.periodMonth=bal.periodMonth WHERE gvv.voucherDate<=v.voucherDate AND gvv.periodMonth=v.periodMonth "
					+") balance  from ( "
					+"select  accountId,voucherId,'借' as directionJ, CASE direction when '借' then money else 0 end as jie,'贷' as directionD,  case direction when '贷' then money else 0 end as dai from GL_voucherDetail "
					+") vc inner join GL_voucher v on v.voucherId = vc.voucherId  WHERE v.orgCode='t00101' AND v.copyCode='008' AND v.periodYear='2013' AND vc.accountId='008_t00101_2013_1001' "
					+"group by v.voucherDate,v.voucherType,v.periodMonth "
					+" ) a "+leftJoins;
		
		SQLQuery sqlQuery = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		pagedRequests.setTotalNumberOfRows(sqlQuery.list().size());
		sqlQuery.setFirstResult(pagedRequests.getStart());
		sqlQuery.setMaxResults(pagedRequests.getPageSize());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}
}
