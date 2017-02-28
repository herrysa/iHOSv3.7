package com.huge.ihos.bm.loanBill.webapp.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.huge.ihos.bm.loanBill.model.BmLoanbill;
import com.huge.ihos.bm.loanBill.service.BmLoanbillManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.util.SupcanUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BmLoanbillPagedAction extends JqGridBaseAction implements Preparable {

	private BmLoanbillManager bmLoanbillManager;
	private List<BmLoanbill> bmLoanbills;
	private BmLoanbill bmLoanbill;
	private String billId;
	private String billCode;
	private String formDataXml;
	private String gridDataXml;
	private BillNumberManager billNumberManager;
	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

	public String getFormDataXml() {
		return formDataXml;
	}

	public void setFormDataXml(String formDataXml) {
		this.formDataXml = formDataXml;
	}

	public String getGridDataXml() {
		return gridDataXml;
	}

	public void setGridDataXml(String gridDataXml) {
		this.gridDataXml = gridDataXml;
	}

	public void setBmLoanbillManager(BmLoanbillManager bmLoanbillManager) {
		this.bmLoanbillManager = bmLoanbillManager;
	}

	public List<BmLoanbill> getbmLoanbills() {
		return bmLoanbills;
	}

	public void setBmLoanbills(List<BmLoanbill> bmLoanbills) {
		this.bmLoanbills = bmLoanbills;
	}

	public BmLoanbill getBmLoanbill() {
		return bmLoanbill;
	}

	public void setBmLoanbill(BmLoanbill bmLoanbill) {
		this.bmLoanbill = bmLoanbill;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
        this.billId = billId;
    }
	
	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bmLoanbillGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bmLoanbillManager
					.getBmLoanbillCriteria(pagedRequests,filters);
			this.bmLoanbills = (List<BmLoanbill>) pagedRequests.getList();
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
			bmLoanbillManager.save(bmLoanbill);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bmLoanbill.added" : "bmLoanbill.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (billId != null) {
        	bmLoanbill = bmLoanbillManager.get(billId);
        	this.setEntityIsNew(false);
        } else {
        	bmLoanbill = new BmLoanbill();
        	billCode = billNumberManager.createNextBillNumber(BillNumberConstants.SUBSYSTEM_BM,BillNumberConstants.BM_LOAN_BILL,true,BillNumberConstants.SUBSYSTEM_BM,BillNumberConstants.SUBSYSTEM_BM,UserContextUtil.getLoginPeriod());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bmLoanbillGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BmLoanbill bmLoanbill = bmLoanbillManager.get(new String(removeId));
					bmLoanbillManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("bmLoanbill.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBmLoanbillGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bmLoanbill == null) {
			return "Invalid bmLoanbill Data";
		}

		return SUCCESS;

	}
	
	public String saveBmLoanbill(){
		try {
			Map<String, String> makeParam = new HashMap<String, String>();
			List<String> sqlList = new ArrayList<String>();
			if(StringUtils.isNotEmpty(formDataXml)){
				makeParam.put("dataXml", formDataXml);
				makeParam.put("tableName", "bm_loanbill");
				makeParam.put("pkId", "billId");
				if(this.isEntityIsNew()){
					makeParam.put("isNew", "1");
				}else{
					makeParam.put("isNew", "0");
				}
				makeParam.put("uuid", "1");
				sqlList.addAll(SupcanUtil.makeSqlList(makeParam));
			}
			if(StringUtils.isNotEmpty(gridDataXml)){
				makeParam.put("dataXml", gridDataXml);
				makeParam.put("tableName", "bm_loanbill_detail");
				makeParam.put("pkId", "billdetailId");
				makeParam.put("isNew", "1");
				makeParam.put("uuid", "1");
				
				makeParam.put("parentCol","billId");
				String parentId = makeParam.get("parentId");
				
				String delSql = "delete from bm_loanbill_detail where billId='"+parentId+"'";
				bmLoanbillManager.executeSql(delSql);
				sqlList.addAll(SupcanUtil.makeSqlList(makeParam));
			}
			bmLoanbillManager.executeSqlList(sqlList);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"保存失败！",false);
		}
		return ajaxForward("保存成功！");
	}
	
	public String getBmLoanBillForm(){
		try {
			String xml = "";
			if(StringUtils.isNotEmpty(billId)){
				String sql = "select * from bm_loanbill where billId='"+billId+"'";
				List<Map<String, Object>> bills = bmLoanbillManager.getBySqlToMap(sql);
				xml = SupcanUtil.makeDataXml(bills);
				
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(xml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getBmLoanBillDetail(){
		try {
			String xml = "";
			if(StringUtils.isNotEmpty(billId)){
				String sql = "select * from bm_loanbill_detail where billId='"+billId+"'";
				List<Map<String, Object>> bills = bmLoanbillManager.getBySqlToMap(sql);
				xml = SupcanUtil.makeDataXml(bills);
				
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(xml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

