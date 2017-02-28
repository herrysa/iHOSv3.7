package com.huge.ihos.accounting.voucher.webapp.action;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.account.service.AccountManager;
import com.huge.ihos.accounting.voucher.model.Voucher;
import com.huge.ihos.accounting.voucher.model.VoucherDetail;
import com.huge.ihos.accounting.voucher.model.VoucherDetailAssist;
import com.huge.ihos.accounting.voucher.service.VoucherDetailAssistManager;
import com.huge.ihos.accounting.voucher.service.VoucherDetailManager;
import com.huge.ihos.accounting.voucher.service.VoucherManager;
import com.huge.ihos.accounting.voucherType.model.VoucherType;
import com.huge.ihos.accounting.voucherType.service.VoucherTypeManager;
import com.huge.ihos.system.configuration.AssistType.service.AssistTypeManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class VoucherDetailPagedAction extends JqGridBaseAction implements Preparable {

	private VoucherDetailManager voucherDetailManager;
	private VoucherManager voucherManager;
	private VoucherDetailAssistManager voucherDetailAssistManager;
	public VoucherDetailAssistManager getVoucherDetailAssistManager() {
		return voucherDetailAssistManager;
	}

	public void setVoucherDetailAssistManager(
			VoucherDetailAssistManager voucherDetailAssistManager) {
		this.voucherDetailAssistManager = voucherDetailAssistManager;
	}
	private AccountManager accountManager;
	private AssistTypeManager assistTypeManager;
	private List<VoucherDetail> voucherDetails;
	private VoucherDetail voucherDetail;
	private Voucher voucher;
	private String voucherDetailId;
	private VoucherTypeManager voucherTypeManager;
	private List<VoucherType> voucherTypes;
	private String voucherId;
	private String voucherType;
	private String voucherNo;
	private String state;
	private String period;
	private String optDate;
	private String billName;
	private String attachNum;
	private String kjManager;
	private String ts = "";
	private String isExistVoucherNo = "0";

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getIsExistVoucherNo() {
		return isExistVoucherNo;
	}

	public void setIsExistVoucherNo(String isExistVoucherNo) {
		this.isExistVoucherNo = isExistVoucherNo;
	}

	public String getKjManager() {
		return kjManager;
	}

	public void setKjManager(String kjManager) {
		this.kjManager = kjManager;
	}

	public String getOptDate() {
		return optDate;
	}

	public void setOptDate(String optDate) {
		this.optDate = optDate;
	}

	public String getAttachNum() {
		return attachNum;
	}

	public void setAttachNum(String attachNum) {
		this.attachNum = attachNum;
	}
	
	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public VoucherTypeManager getVoucherTypeManager() {
		return voucherTypeManager;
	}

	public void setVoucherTypeManager(VoucherTypeManager voucherTypeManager) {
		this.voucherTypeManager = voucherTypeManager;
	}

	public List<VoucherType> getVoucherTypes() {
		return voucherTypes;
	}

	public void setVoucherTypes(List<VoucherType> voucherTypes) {
		this.voucherTypes = voucherTypes;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public VoucherDetailManager getVoucherDetailManager() {
		return voucherDetailManager;
	}

	public void setVoucherDetailManager(VoucherDetailManager voucherDetailManager) {
		this.voucherDetailManager = voucherDetailManager;
	}
	
	public VoucherManager getVoucherManager() {
		return voucherManager;
	}

	public void setVoucherManager(VoucherManager voucherManager) {
		this.voucherManager = voucherManager;
	}
    
	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
	public AssistTypeManager getAssistTypeManager() {
		return assistTypeManager;
	}

	public void setAssistTypeManager(AssistTypeManager assistTypeManager) {
		this.assistTypeManager = assistTypeManager;
	}

	public List<VoucherDetail> getvoucherDetails() {
		return voucherDetails;
	}

	public void setVoucherDetails(List<VoucherDetail> voucherDetails) {
		this.voucherDetails = voucherDetails;
	}

	public VoucherDetail getVoucherDetail() {
		return voucherDetail;
	}

	public void setVoucherDetail(VoucherDetail voucherDetail) {
		this.voucherDetail = voucherDetail;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	
	public String getVoucherDetailId() {
		return voucherDetailId;
	}

	public void setVoucherDetailId(String voucherDetailId) {
        this.voucherDetailId = voucherDetailId;
    }
	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}
	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String voucherDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = voucherDetailManager
					.getVoucherDetailCriteria(pagedRequests,filters);
			this.voucherDetails = (List<VoucherDetail>) pagedRequests.getList();
			/*if(voucherDetails==null||voucherDetails.size()==0){
				for(int i =0;i》)
			}*/
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
			voucherDetailManager.save(voucherDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "voucherDetail.added" : "voucherDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (voucherDetailId != null) {
        	voucherDetail = voucherDetailManager.get(voucherDetailId);
        	this.setEntityIsNew(false);
        } else {
        	voucherDetail = new VoucherDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String voucherDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					VoucherDetail voucherDetail = voucherDetailManager.get(new String(removeId));
					voucherDetailManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("voucherDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkVoucherDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (voucherDetail == null) {
			return "Invalid voucherDetail Data";
		}

		return SUCCESS;

	}
	
	public String voucherDetailCardPre(){
		try {
			billName = this.getSessionUser().getPersonName();
			voucherTypes = voucherTypeManager.getAll();
			/*List<MoudlePeriod> moudlePeriods = moudlePeriodManager.getMoudlePeriod(systemVariable.getPeriodPlanId(),systemVariable.getPeriodSubjectId(),"09");
			MoudlePeriod prevMoudlePeriod = null;
			for(MoudlePeriod moudlePeriod : moudlePeriods){
				if(moudlePeriod.getMonth().equals(systemVariable.getPeriod())){
					if(moudlePeriod.getFlag()){
						//"已经结账"
						state = "0";
						this.setMessage("'"+moudlePeriod.getMonth()+"'已经结账,不能本期间编制凭证!");
					}
					if(prevMoudlePeriod!=null){
						if(!prevMoudlePeriod.getFlag()){
							//上月未结账
							state = "0";
							this.setMessage("'"+prevMoudlePeriod.getMonth()+"'未结账,不能本期间编制凭证!");
						}
					}
				}
				prevMoudlePeriod = moudlePeriod;
			}*/
			List<Voucher> vouchers = null;
			if(voucherId!=null&&!"".equals(voucherId)){
				voucher = voucherManager.get(voucherId);
				if(voucher.getStatus()==1){
					if(!"0".equals(state)){
						state="1";
					}
				}else {
					state = ""+voucher.getStatus();
				}
				preVoucher(voucher);
			}else if(voucherType!=null&&!"".equals(voucherType)){
				if("".equals(voucherNo)){
					voucherNo = null;
				}
				Integer vn = null;
				if(voucherNo!=null){
					vn = Integer.parseInt(voucherNo);
				}
				vouchers = voucherManager.getBysSysVariable(null, voucherType,vn);
				if(vouchers.size()>0){
					voucher = vouchers.get(0);
					if(voucher.getStatus()==1){
						if(!"0".equals(state)){
							state="1";
						}
					}else {
						state = ""+voucher.getStatus();
					}
					preVoucher(voucher);
				}else{
					voucherNo = "1";
					vouchers = voucherManager.getBysSysVariable(null, voucherType,vn);
					if(vouchers.size()>0){
						voucher = vouchers.get(0);
						if(voucher.getStatus()==1){
							if(!"0".equals(state)){
								state="1";
							}
						}else {
							state = ""+voucher.getStatus();
						}
						preVoucher(voucher);
					}else{
						voucherType = voucherTypes.get(0).getVouchertype();
						optDate = UserContextUtil.getUserContext().getBusinessDateStr();
						attachNum = "0";
						if(!"0".equals(state)){
							state="1";
						}
					}
				}
				
				//voucherData 
			}else{
				if(voucherTypes.size()>0){
					vouchers = voucherManager.getBysSysVariable(null, voucherTypes.get(0).getVouchertype(),null);
					if(vouchers.size()>0){
						voucherNo = ""+(vouchers.get(0).getVoucherNo()+1);
					}else{
						voucherNo = "1";
					}
					voucherType = voucherTypes.get(0).getVouchertype();
				}
				optDate = UserContextUtil.getUserContext().getBusinessDateStr();
				attachNum = "0";
				if(!"0".equals(state)){
					state="1";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void preVoucher(Voucher voucherCard) {
		voucherNo = ""+voucherCard.getVoucherNo();
		voucherType = voucherCard.getVoucherType();
		optDate = voucherCard.getVoucherDate();
		attachNum = ""+voucherCard.getAttachNum();
		state = ""+voucherCard.getStatus();
		JsonConfig jsonConfig = new JsonConfig(); 
		net.sf.json.util.PropertyFilter filter = new net.sf.json.util.PropertyFilter() {  
            public boolean apply(Object source, String name, Object value) {  
                if ("voucher".equals(name)) {  
                    return true;  
                }
                if ("voucherDetailAssists".equals(name)) {  
                    return true;  
                }
                if ("account".equals(name)) {  
                    return true;  
                }
                if ("voucherDetails".equals(name)) {  
                    return true;  
                }
                return false;  
            }  
        };  
        jsonConfig.setJsonPropertyFilter(filter);
        //Set<VoucherDetail> voucherDetails= voucher.getVoucherDetailData();
        //VoucherDetail voucherDetail = new VoucherDetail();
        //voucherDetail.setDetailNo(0);
        //voucherDetail.set
        //voucherDetails.add(voucherDetail);
        //voucher.setVoucherDetailData(voucherDetails);
		voucherData = JSONObject.fromObject( voucherCard,jsonConfig ).toString();
	}
	private String voucherDetailData;
	private String voucherData;
	
	public String getVoucherData() {
		return voucherData;
	}

	public void setVoucherData(String voucherData) {
		this.voucherData = voucherData;
	}

	public String getVoucherDetailData() {
		return voucherDetailData;
	}

	public void setVoucherDetailData(String voucherDetailData) {
		this.voucherDetailData = voucherDetailData;
	}

	public String saveVoucherDetailData(){
		try {
			if(voucherData!=null&!"".equals(voucherData)){
				JSONObject voucherDataJson = JSONObject.fromObject(voucherData);
				//voucherDetailDataJson.
				
				JSONObject voucherDetailDataJson=null; 
				
				Voucher voucher = null;
				if(voucherDataJson instanceof JSONObject){
					voucherType = voucherDataJson.getString("voucherType");
					voucherNo = ""+voucherDataJson.getInt("voucherNo");
					List<Voucher> vouchers = voucherManager.getBysSysVariable(null, voucherType, voucherDataJson.getInt("voucherNo"));
					if(vouchers!=null&&vouchers.size()>0){
						voucher = vouchers.get(0);
						//voucherManager.remove(voucher.getVoucherId());
						//voucher.setVoucherId(UUIDGenerator.getInstance().getNextValue());
						//voucher.getVoucherDetails().clear();
					}else{
						voucher = new Voucher();
						//voucher.setVoucherId(UUIDGenerator.getInstance().getNextValue());
						voucher.setVoucherType(voucherDataJson.getString("voucherType"));
						voucher.setVoucherNo(voucherDataJson.getInt("voucherNo"));
					}
					voucher.setAttachNum(voucherDataJson.getInt("attachNum"));
					voucher.setVoucherDate(voucherDataJson.getString("voucherDate"));
					voucher.setVoucherFromCode("GL");
					voucher.setOrgCode(UserContextUtil.getUserContext().getOrgCode());
            		voucher.setCopyCode("001");
            		voucher.setPeriodYear(UserContextUtil.getUserContext().getPeriodYear());
            		voucher.setPeriodMonth(UserContextUtil.getUserContext().getPeriodMonth());
            		Person person = this.getSessionUser().getPerson();
            		voucher.setBillId(person.getPersonId());
            		voucher.setBillName(person.getName());
            		voucher.setBillDate(DateUtil.convertDateToString("yyyy-MM-dd", Calendar.getInstance().getTime()));
					voucher.setStatus(1);
					voucher.setTs(Calendar.getInstance().getTime());
					//voucher.getVoucherDetails().clear();
					voucherDetailDataJson = voucherDataJson.getJSONObject("voucherDetailData");
				}
				Iterator<String> keys=voucherDetailDataJson.keys();
				JSONObject voucherDetailRow=null; 
				Object o;  
			    String key;
			    int i=0;
			    Set<VoucherDetail> voucherDetails = new HashSet<VoucherDetail>();
				while(keys.hasNext()){
					key=keys.next();  
		            o=voucherDetailDataJson.get(key);  
		            if(o instanceof JSONObject){
		            	voucherDetailRow=(JSONObject)o;
		            	if(i==0){
		            		voucher.setAbstractStr(voucherDetailRow.getString("abstractStr"));
		            	}
		            	Account account = accountManager.get(voucherDetailRow.getString("accountId"));
		            	VoucherDetail voucherDetail = new VoucherDetail();
		            	voucherDetail.setAbstractStr(voucherDetailRow.getString("abstractStr"));
		            	voucherDetail.setVoucher(voucher);
		            	//voucherDetail.setOrgCode(systemVariable.getOrgCode());
		            	//voucherDetail.setCopyCode(systemVariable.getCopyCode());
		            	//voucherDetail.setKjPeriod("201311");
		            	voucherDetail.setAccount(account);
		            	voucherDetail.setAcctcode(account.getAcctCode());
		            	//voucherDetail.setVoucherFromCode(voucher.getVoucherFromCode());
		            	voucherDetail.setDetailNo(Integer.parseInt(key));
		            	voucherDetail.setDirection(voucherDetailRow.getString("direction").equals("lend")?"借":"贷");
		            	voucherDetail.setMoney(new BigDecimal(voucherDetailRow.getString("money")));
		            	//voucherDetail.setVoucherDetailId(UUIDGenerator.getInstance().getNextValue());
		            	//voucherDetail.getVoucherDetailAssists().clear();
		            	voucherDetails.add(voucherDetail);
		            	//voucherDetail.getVoucherDetailAssists().clear();
		            	Set<VoucherDetailAssist> voucherDetailAssists = new HashSet<VoucherDetailAssist>();
		            	Object assistData = voucherDetailRow.get("assistData");
		            	if(assistData instanceof JSONObject){
		            		JSONObject assistRows=(JSONObject)assistData;
		            		Iterator<String> assistRowKeys=assistRows.keys();
		            		JSONObject assistRow=null; 
		            		Object oo;  
		            		String assistRowkey;
		            		int ii=0;
		            		while(assistRowKeys.hasNext()){
		            			assistRowkey=assistRowKeys.next();  
		            			oo=assistRows.get(assistRowkey);  
		            			if(oo instanceof JSONObject){
		            				assistRow=(JSONObject)oo;
		            				Iterator<String> assistKeys=assistRow.keys();
		            				JSONObject assist=null; 
		            				String ooo;  
		            				String assistkey;
		            				int iii=0;
		            				String assistsAbstract = assistRow.getString("assistsAbstract");
		            				String assistAbstract = "";
		            				if(assistsAbstract!=null){
		            					String[] assistAbstractArr = assistsAbstract.split(",");
		            					if(assistAbstractArr.length>0){
		            						assistAbstract = assistAbstractArr[0];
		            					}
		            				}
		            				//String assistAbstract = assistRow.getString("assistsAbstract").split(",")[0];
		            				String assistMoney = assistRow.getString("assistsMoney").split(",")[0];
		            				while(assistKeys.hasNext()){
		            					assistkey=assistKeys.next(); 
		            					if(!"assistsAbstract".equals(assistkey)&&!"assistsMoney".equals(assistkey)){
		            						ooo=assistRow.get(assistkey).toString();  
		            						//if(ooo instanceof JSONObject){
		            							//assist=(JSONObject)ooo;
		            							VoucherDetailAssist voucherDetailAssist = new VoucherDetailAssist();
		            							voucherDetailAssist.setAbstractStr(assistAbstract);
		            							voucherDetailAssist.setVoucherDetail(voucherDetail);
		            							voucherDetailAssist.setAssistNo(ii);
		            							voucherDetailAssist.setAssistType(assistTypeManager.get(assistkey));
		            							voucherDetailAssist.setAssistValue(ooo.split(",")[1]);
		            							voucherDetailAssist.setAssistName(ooo.split(",")[0]);
		            							voucherDetailAssist.setMoney(Double.parseDouble(assistMoney));
		            							voucherDetailAssists.add(voucherDetailAssist);
		            							//voucherDetailAssistManager.save(voucherDetailAssist);
		            						//}
		            					}
		            				}
		            			}
		            			ii++;
		            		}
		            	}
		            	voucherDetail.addVoucherDetailAssists(voucherDetailAssists);
		            	//voucherDetailManager.save(voucherDetail);
		            }
		            i++;
				}
				voucher.addVoucherDetails(voucherDetails);
				voucherManager.save(voucher);
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"保存失败！",false);
		}
		return ajaxForward(true,"保存成功！",false);
	}
	
	public String isExistVoucherNo() {
		try {
			List<Voucher> vouchers = voucherManager.getBysSysVariable(null, voucherType, Integer.parseInt(voucherNo));
			if(vouchers.size()>0){
				ts = DateUtil.convertDateToString("yyyy-MM-dd hh:mm:ss", vouchers.get(0).getTs());
				isExistVoucherNo = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}

