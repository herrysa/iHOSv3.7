package com.huge.ihos.accounting.voucher.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.util.AccountUtil;
import com.huge.ihos.accounting.voucher.model.Voucher;
import com.huge.ihos.accounting.voucher.service.VoucherManager;
import com.huge.ihos.accounting.voucherFrom.model.VoucherFrom;
import com.huge.ihos.accounting.voucherFrom.service.VoucherFromManager;
import com.huge.ihos.accounting.voucherType.model.VoucherType;
import com.huge.ihos.accounting.voucherType.service.VoucherTypeManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.service.MoudlePeriodManager;
import com.huge.ihos.system.systemManager.period.service.PeriodMonthManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class VoucherPagedAction extends JqGridBaseAction implements Preparable {

	private VoucherManager voucherManager;
	private List<Voucher> vouchers;
	private Voucher voucher;
	private String voucherId;
	private String voucherIds;
	private List<PeriodMonth> periodMonths;
	private List<VoucherType> voucherTypes;
	private List<VoucherFrom> voucherFroms;
	private VoucherTypeManager voucherTypeManager;
	private PeriodMonthManager periodMonthManager;
	private VoucherFromManager voucherFromManager;
	private CopyManager copyManager;
	private MoudlePeriodManager moudlePeriodManager;

	private List<Map<String, String>> voucherCollects;
	
	private String periodMonth;
	private String beginDate;
	private String endDate;
	private String voucherFrom;
	private String voucherType;
	private String voucherNo;
	private String state;
	private String accountLeve;
	
	private String acctCode;
	private String beginPeriod;
	private String endPeriod;
	
	public List<PeriodMonth> getPeriodMonths() {
		return periodMonths;
	}

	public void setPeriodMonths(List<PeriodMonth> periodMonths) {
		this.periodMonths = periodMonths;
	}
	public List<VoucherFrom> getVoucherFroms() {
		return voucherFroms;
	}

	public void setVoucherFroms(List<VoucherFrom> voucherFroms) {
		this.voucherFroms = voucherFroms;
	}

	public List<VoucherType> getVoucherTypes() {
		return voucherTypes;
	}

	public void setVoucherTypes(List<VoucherType> voucherTypes) {
		this.voucherTypes = voucherTypes;
	}
	public PeriodMonthManager getPeriodMonthManager() {
		return periodMonthManager;
	}

	public void setPeriodMonthManager(PeriodMonthManager periodMonthManager) {
		this.periodMonthManager = periodMonthManager;
	}
	public VoucherFromManager getVoucherFromManager() {
		return voucherFromManager;
	}

	public void setVoucherFromManager(VoucherFromManager voucherFromManager) {
		this.voucherFromManager = voucherFromManager;
	}

	public CopyManager getCopyManager() {
		return copyManager;
	}

	public void setCopyManager(CopyManager copyManager) {
		this.copyManager = copyManager;
	}
	public VoucherTypeManager getVoucherTypeManager() {
		return voucherTypeManager;
	}

	public void setVoucherTypeManager(VoucherTypeManager voucherTypeManager) {
		this.voucherTypeManager = voucherTypeManager;
	}

	public List<Map<String, String>> getVoucherCollects() {
		return voucherCollects;
	}

	public void setVoucherCollects(List<Map<String, String>> voucherCollects) {
		this.voucherCollects = voucherCollects;
	}

	public String getVoucherIds() {
		return voucherIds;
	}

	public void setVoucherIds(String voucherIds) {
		this.voucherIds = voucherIds;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private List<Map<String, String>> voucherStatus;
	

	public List<Map<String, String>> getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(List<Map<String, String>> voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public VoucherManager getVoucherManager() {
		return voucherManager;
	}

	public void setVoucherManager(VoucherManager voucherManager) {
		this.voucherManager = voucherManager;
	}
	
	public MoudlePeriodManager getMoudlePeriodManager() {
		return moudlePeriodManager;
	}

	public void setMoudlePeriodManager(MoudlePeriodManager moudlePeriodManager) {
		this.moudlePeriodManager = moudlePeriodManager;
	}

	public List<Voucher> getvouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getVoucherFrom() {
		return voucherFrom;
	}

	public void setVoucherFrom(String voucherFrom) {
		this.voucherFrom = voucherFrom;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getAccountLeve() {
		return accountLeve;
	}

	public void setAccountLeve(String accountLeve) {
		this.accountLeve = accountLeve;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public String getBeginPeriod() {
		return beginPeriod;
	}

	public void setBeginPeriod(String beginPeriod) {
		this.beginPeriod = beginPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String voucherGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(!state.equals("0")){
				PropertyFilter statePropertyFilter = new PropertyFilter("EQI_status",state);
				filters.add(statePropertyFilter);
			}
			//AccountUtil.addContextToFilter(filters);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = voucherManager
					.getVoucherCriteria(pagedRequests,filters);
			this.vouchers = (List<Voucher>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			voucherManager.save(voucher);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "voucher.added" : "voucher.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (voucherId != null) {
        	voucher = voucherManager.get(voucherId);
        	this.setEntityIsNew(false);
        } else {
        	voucher = new Voucher();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String voucherGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					Voucher voucher = voucherManager.get(new String(removeId));
					voucherManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("voucher.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVoucherGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (voucher == null) {
			return "Invalid voucher Data";
		}

		return SUCCESS;

	}
	
	public String voucherBox(){
		try {
			voucherStatus = new ArrayList<Map<String,String>>();
			Map<String, String> voucherState = new HashMap<String, String>();
			voucherState.put("state", "0");
			voucherState.put("stateName", this.getText("voucher.state.all"));
			voucherStatus.add(voucherState);
			voucherState = new HashMap<String, String>();
			voucherState.put("state", "1");
			voucherState.put("stateName", this.getText("voucher.state.uncheck"));
			voucherStatus.add(voucherState);
			voucherState = new HashMap<String, String>();
			voucherState.put("state", "2");
			voucherState.put("stateName", this.getText("voucher.state.checked"));
			voucherStatus.add(voucherState);
			voucherState = new HashMap<String, String>();
			voucherState.put("state", "3");
			voucherState.put("stateName", this.getText("voucher.state.accounted"));
			voucherStatus.add(voucherState);
			voucherState = new HashMap<String, String>();
			voucherState.put("state", "4");
			voucherState.put("stateName", this.getText("voucher.state.invalid"));
			voucherStatus.add(voucherState);
			voucherState = new HashMap<String, String>();
			voucherState.put("state", "5");
			voucherState.put("stateName", this.getText("voucher.state.error"));
			voucherStatus.add(voucherState);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String chanegVoucherState(){
		String changeType = "";
		try {
			String[] voucherIdArr = voucherIds.split(",");
			for(String vId : voucherIdArr){
				Voucher voucher = voucherManager.get(vId);
				voucher.setStatus(Integer.parseInt(state));
				Person person = this.getSessionUser().getPerson();
				if(state.equals("2")){
					changeType = "审核";
					voucher.setCheckDate(DateUtil.convertDateToString("yyyy-MM-dd", Calendar.getInstance().getTime()));
					voucher.setCheckId(person.getPersonId());
					voucher.setCheckName(person.getName());
				}else if(state.equals("3")){
					changeType = "记账";
					voucher.setAuditDate(DateUtil.convertDateToString("yyyy-MM-dd", Calendar.getInstance().getTime()));
					voucher.setAuditId(person.getPersonId());
					voucher.setAuditName(person.getName());
				}else if(state.equals("4")){
					changeType = "作废";
				}else if(state.equals("5")){
					changeType = "标志错误";
				}
				voucherManager.save(voucher);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, changeType+"失败！", false);
		}
		return ajaxForward(true, changeType+"成功！", false);
	}
	
	public String closeAccountMonth(){
		/*try {
			List<Voucher> vouchers = voucherManager.getByState(null, 2, "le");
			if(vouchers.size()>0){
				return ajaxForward(false,"存在未记账凭证,期间'"+systemVariable.getPeriod()+"'不能结账",false);
			}else{
				vouchers = voucherManager.getByState(null, 3, "eq");
				Map<String, Map<String, Double>> balancePeriodMoney = new HashMap<String, Map<String,Double>>();
				for(Voucher voucher : vouchers){
					Set<VoucherDetail> voucherDetails = voucher.getVoucherDetails();
					Iterator<VoucherDetail> vdIt = voucherDetails.iterator();
					while (vdIt.hasNext()) {
						VoucherDetail voucherDetail = vdIt.next();
						Map<String, Double> accountMap = balancePeriodMoney.get(voucherDetail.getAccount().getAcctId());
						Double lend = 0d;
						Double loan = 0d;
						if(accountMap==null){
							accountMap = new HashMap<String, Double>();
						}else{
							lend = accountMap.get("lend");
							loan = accountMap.get("loan");
						}
						if("借".equals(voucherDetail.getDirection())){
							lend += voucherDetail.getMoney().doubleValue();
						}else{
							loan += voucherDetail.getMoney().doubleValue();
						}
						accountMap.put("lend", lend);
						accountMap.put("loan", loan);
						balancePeriodMoney.put(voucherDetail.getAccount().getAcctId(), accountMap);
					}
				}
				HashMap<String, String> environment = new HashMap<String, String>();
				environment.put("orgCode", systemVariable.getOrgCode());
				environment.put("copyCode", systemVariable.getCopyCode());
				environment.put("periodYear", systemVariable.getKjYear());
				environment.put("periodMonth", systemVariable.getPeriod());
				List<BalancePeriod> balancePeriods = balancePeriodManager.getBalancePeriodByEnvironment(environment);
				for(BalancePeriod balancePeriod : balancePeriods){
					String accountId = balancePeriod.getBalance().getAccount().getAcctId();
					Map<String, Double> accountMap = balancePeriodMoney.get(accountId);
					if(accountMap!=null){
						Double lend = accountMap.get("lend");
						Double loan = accountMap.get("loan");
						balancePeriod.setLend(lend);
						balancePeriod.setLoan(loan);
						balancePeriod.setHasAccrual(true);
						balancePeriodManager.save(balancePeriod);
						Integer nextPeriod = Integer.parseInt(systemVariable.getPeriod())+1;
						HashMap<String, String> condition = new HashMap<String, String>();
						condition.put("periodId", systemVariable.getPeriodSubjectId());
						condition.put("planId", systemVariable.getPeriodPlanId());
						PeriodMonth periodMonthFind = new PeriodMonth();
						periodMonthFind.getPeriodYear().getPlan().setPlanId(systemVariable.getPeriodPlanId());
						periodMonthFind.getPeriodYear().setPeriodYearId(systemVariable.getPeriodSubjectId());
						boolean isexist = periodMonthManager.existByExample(periodMonthFind);
						//boolean isexist = periodMonthManager.existCode("t_periodMonth", condition);
						if(isexist){
							HashMap<String, String> environment2 = new HashMap<String, String>();
							environment2.put("orgCode", systemVariable.getOrgCode());
							environment2.put("copyCode", systemVariable.getCopyCode());
							environment2.put("periodYear", systemVariable.getKjYear());
							environment2.put("periodMonth", ""+nextPeriod);
							environment2.put("accountId", accountId);
							List<BalancePeriod> balancePeriodNexts = balancePeriodManager.getBalancePeriodByEnvironment(environment2);
							if(balancePeriodNexts.size()>0){
								BalancePeriod balancePeriodNext = balancePeriodNexts.get(0);
								balancePeriodNext.setMonthBalance(lend-loan+balancePeriod.getMonthBalance());
								balancePeriodManager.save(balancePeriodNext);
							}
						}
					}
				}
				List<MoudlePeriod> moudlePeriods = moudlePeriodManager.getMoudlePeriod(systemVariable.getPeriodPlanId(), systemVariable.getPeriodSubjectId(), "09");
				for(MoudlePeriod moudlePeriod : moudlePeriods){
					if(moudlePeriod.getMonth().equals(systemVariable.getPeriod())){
						moudlePeriod.setFlag(true);
						moudlePeriodManager.save(moudlePeriod);
						break;
					}
				}
					
				return ajaxForward(true,"结账成功！",false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return SUCCESS;
	}
	
	public String voucherGridCollect(){
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			/*pagedRequests = voucherManager
					.getVoucherCriteria(pagedRequests,filters);*/
			Map<String, String> getParams = new HashMap<String, String>();
			getParams.put("orgCode", UserContextUtil.getUserContext().getOrgCode());
			getParams.put("copyCode", UserContextUtil.getUserContext().getCopyCode());
			getParams.put("periodYear", UserContextUtil.getUserContext().getPeriodYear());
			getParams.put("periodMonth", periodMonth);
			if(OtherUtil.measureNotNull(beginDate)){
				getParams.put("beginDate", beginDate);
				getParams.put("endDate", endDate);
			}
			if(OtherUtil.measureNotNull(voucherFrom)){
				getParams.put("voucherFrom", voucherFrom);
			}
			if(OtherUtil.measureNotNull(voucherType)){
				getParams.put("voucherType", voucherType);
			}
			if(OtherUtil.measureNotNull(voucherNo)){
				getParams.put("voucherNoBegin", voucherNo.split(",")[0]);
				getParams.put("voucherNoEnd", voucherNo.split(",")[1]);
			}
			if(OtherUtil.measureNotNull(state)){
				
			}
			voucherCollects = voucherManager.getAccountCollect(getParams);
			//this.vouchers = (List<Voucher>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String voucherGridCollectBalance(){
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = voucherManager
					.getVoucherCriteria(pagedRequests,filters);
			Map<String, String> getParams = new HashMap<String, String>();
			getParams.put("orgCode", UserContextUtil.getUserContext().getOrgCode());
			getParams.put("copyCode", UserContextUtil.getUserContext().getCopyCode());
			getParams.put("periodYear", UserContextUtil.getUserContext().getPeriodYear());
			getParams.put("periodMonth", periodMonth);
			if(OtherUtil.measureNotNull(beginDate)){
				getParams.put("beginDate", beginDate);
				getParams.put("endDate", endDate);
			}
			if(OtherUtil.measureNotNull(voucherFrom)){
				getParams.put("voucherFrom", voucherFrom);
			}
			if(OtherUtil.measureNotNull(voucherType)){
				getParams.put("voucherType", voucherType);
			}
			if(OtherUtil.measureNotNull(voucherNo)){
				getParams.put("voucherNoBegin", voucherNo.split(",")[0]);
				getParams.put("voucherNoEnd", voucherNo.split(",")[1]);
			}
			if(OtherUtil.measureNotNull(state)){
				
			}
			voucherCollects = voucherManager.getAccountCollectBalance(getParams);
			//this.vouchers = (List<Voucher>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String voucherCollectPre() {
		try {
			periodMonth = UserContextUtil.getLoginPeriod();
			Copy copy = copyManager.get(UserContextUtil.getUserContext().getCopyCode());
			periodMonths = periodMonthManager.getMonthByPlanAndYear(copy.getPeriodPlan().getPlanId(), UserContextUtil.getUserContext().getPeriodYear());
			PeriodMonth periodMonth = new PeriodMonth();
			periodMonth.setPeriodMonthCode("自定义");
			periodMonths.add(periodMonth);
			voucherTypes = voucherTypeManager.getAll();
			voucherFroms = voucherFromManager.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String voucherCollectBalancePre() {
		try {
			periodMonth = UserContextUtil.getLoginPeriod();
			Copy copy = copyManager.get(UserContextUtil.getUserContext().getCopyCode());
			periodMonths = periodMonthManager.getMonthByPlanAndYear(copy.getPeriodPlan().getPlanId(), UserContextUtil.getUserContext().getPeriodYear());
			PeriodMonth periodMonth = new PeriodMonth();
			periodMonth.setPeriodMonthCode("自定义");
			periodMonths.add(periodMonth);
			voucherTypes = voucherTypeManager.getAll();
			voucherFroms = voucherFromManager.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	public String accountBalanceGrid(){
		try {
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			periodMonth = periodMonthManager.getMonthsBetweenPeriods(beginPeriod, endPeriod, UserContextUtil.getUserContext().getPeriodPlanCode());
			HashMap<String, String> environment = new HashMap<String, String>();
			environment.put("orgCode", UserContextUtil.getUserContext().getOrgCode());
			environment.put("copyCode", UserContextUtil.getUserContext().getCopyCode());
			String beginYear = beginPeriod.substring(0,4);
			String endYear = endPeriod.substring(0,4);
			if(beginYear.equals(endYear)){
				environment.put("periodYear", "'"+beginYear+"'");
			}else{
				environment.put("periodYear", "'"+beginYear+"','"+endYear+"'");
			}
			
			//environment.put("periodMonth", periodMonth);
			environment.put("acctCode", acctCode);
			environment.put("beginPeriod", beginPeriod);
			environment.put("endPeriod", endPeriod);
			voucherCollects = voucherManager.getAccountBalance(pagedRequests, environment);
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}

