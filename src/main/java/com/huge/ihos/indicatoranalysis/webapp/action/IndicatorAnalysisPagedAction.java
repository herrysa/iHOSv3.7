package com.huge.ihos.indicatoranalysis.webapp.action;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;

import com.huge.ihos.indicatoranalysis.model.Indicator;
import com.huge.ihos.indicatoranalysis.model.IndicatorAnalysis;
import com.huge.ihos.indicatoranalysis.service.IndicatorAnalysisManager;
import com.huge.ihos.indicatoranalysis.service.IndicatorManager;
import com.huge.ihos.indicatoranalysis.service.IndicatorTypeManager;
import com.huge.util.ConvertUtil;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class IndicatorAnalysisPagedAction extends JqGridBaseAction implements Preparable {

	private IndicatorAnalysisManager indicatorAnalysisManager;
	private List<IndicatorAnalysis> indicatorAnalysiss;
	private IndicatorAnalysis indicatorAnalysis;
	private String id;
	private String indicatorTypeCode;
	private String checkPeriod;
	private IndicatorManager indicatorManager;
	
	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public void setIndicatorManager(IndicatorManager indicatorManager) {
		this.indicatorManager = indicatorManager;
	}

	public void setIndicatorTypeCode(String indicatorTypeCode) {
		this.indicatorTypeCode = indicatorTypeCode;
	}

	public String getIndicatorTypeCode() {
		return indicatorTypeCode;
	}

	public void setIndicatorAnalysisManager(IndicatorAnalysisManager indicatorAnalysisManager) {
		this.indicatorAnalysisManager = indicatorAnalysisManager;
	}

	public List<IndicatorAnalysis> getIndicatorAnalysiss() {
		return indicatorAnalysiss;
	}

	public IndicatorAnalysis getIndicatorAnalysis() {
		return indicatorAnalysis;
	}

	public void setIndicatorAnalysis(IndicatorAnalysis indicatorAnalysis) {
		this.indicatorAnalysis = indicatorAnalysis;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		if(checkPeriod==null){
			checkPeriod = this.getLoginPeriod();
		}
		this.clearSessionMessages();
	}
	
	public String indicatorAnalysisList(){
		String defaultShow = this.getRequest().getParameter("defaultShow");
		if(defaultShow==null){
			defaultShow = "grid";
		}
		return defaultShow;
	}
	@SuppressWarnings("unchecked")
	public String indicatorAnalysisGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			PropertyFilter pf = null;
			Iterator<PropertyFilter> ite = filters.iterator();
			String indicatorIds = "";
			Integer level = -1;
			while(ite.hasNext()){
				pf= ite.next();
				String pfV = ConvertUtil.convert(pf.getMatchValue());
				if("indicator.id".equals(pf.getPropertyName())){
					indicatorIds = "";
					List<Indicator> indicators = this.indicatorManager.getChildrenByParentId(pfV);
					if(indicators!=null && indicators.size()>0){
						for(Indicator indicator:indicators){
							indicatorIds += indicator.getId()+",";
						}
						indicatorIds += pfV;
						ite.remove();
					}
					break;
				}
				if("indicator.level".equals(pf.getPropertyName())){
					level = Integer.parseInt(pfV);
					break;
				}
			}
			if(!"".equals(indicatorIds)){
				filters.add(new PropertyFilter("INS_indicator.id",indicatorIds));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = indicatorAnalysisManager.getIndicatorAnalysisCriteria(pagedRequests,filters);
			this.indicatorAnalysiss = (List<IndicatorAnalysis>) pagedRequests.getList();
			if(indicatorAnalysiss!=null && indicatorAnalysiss.size()>0){
				if(level!=-1){
					for(IndicatorAnalysis indicatorAnalysis:indicatorAnalysiss){
						if(indicatorAnalysis.getIndicator().getLevel()==level || indicatorAnalysis.getIndicator().getLeaf()){
							indicatorAnalysis.setIsLeaf(true);
						}
					}
				}else{
					for(IndicatorAnalysis indicatorAnalysis:indicatorAnalysiss){
						indicatorAnalysis.setIsLeaf(indicatorAnalysis.getIndicator().getLeaf());
					}
				}
			}
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	private String indicatorId;
	private String indicatorLevel;
	private String indicatorName;
	
	public String getIndicatorName() {
		return indicatorName;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public String getIndicatorLevel() {
		return indicatorLevel;
	}

	public String switchGraphAndGrid(){
		HttpServletRequest req = this.getRequest();
		String redirectType = req.getParameter("redirectType");
		indicatorId = req.getParameter("indicatorId");
		if(OtherUtil.measureNotNull(indicatorId)){
			indicatorName = this.indicatorManager.get(indicatorId).getName();
		}
		indicatorLevel = req.getParameter("indicatorLevel");
		return redirectType;
	}
	public String initIndicatorValue(){
		try {
			indicatorAnalysisManager.initIndicatorValue(checkPeriod,indicatorTypeCode);
			return ajaxForward(true, "初始化完成!", false);
		} catch (Exception e) {
			log.error("initIndicatorValue Error", e);
			return ajaxForward(false, "初始化失败", false);
		}
	}
	public String executeIndicatorSp(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_checkPeriod",checkPeriod));
			filters.add(new PropertyFilter("EQS_indicator.indicatorType.code",indicatorTypeCode));
			List<IndicatorAnalysis> indicatorAnalysisList = this.indicatorAnalysisManager.getByFilters(filters);
			if(indicatorAnalysisList==null || indicatorAnalysisList.isEmpty()){
				return ajaxForward(false, "本期间数据尚未初始化！", false);
			}
			String taskName = this.getRequest().getParameter("spName");
			indicatorAnalysisManager.exeSp(checkPeriod, indicatorTypeCode, taskName);
			return ajaxForward(true, "计算完成!", false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "计算失败", false);
		}
	}
	public String calculateIndicatorValue(){
		try {
			
			String taskName = this.getRequest().getParameter("spName");
			indicatorAnalysisManager.calculateIndicatorValue(checkPeriod,indicatorTypeCode,taskName);
			return ajaxForward(true, "计算完成!", false);
		} catch (Exception e) {
			log.error("calculateIndicatorValue Error", e);
			return ajaxForward(false, "计算失败", false);
		}
	}

	
	String condition;
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}



	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public void setIndicatorLevel(String indicatorLevel) {
		this.indicatorLevel = indicatorLevel;
	}
	List<Map<String, String>> indicatorAnalysisList ;
	public List<Map<String, String>> getIndicatorAnalysisList() {
		return indicatorAnalysisList;
	}

	public void setIndicatorAnalysisList(
			List<Map<String, String>> indicatorAnalysisList) {
		this.indicatorAnalysisList = indicatorAnalysisList;
	}

	public String getIndicatorForGraph() {
		log.debug("enter list method!");
		try {
			List<IndicatorAnalysis> indicatorAnalysis ;
			if("id".equals(condition)){
				if(indicatorId==null||"".equals(indicatorId)){
					indicatorAnalysis = indicatorAnalysisManager.getByTypeCode(checkPeriod, indicatorTypeCode);
				}else{
					indicatorAnalysis = indicatorAnalysisManager.getById(checkPeriod, indicatorId);
				}
			}else{
				if(indicatorLevel==null||"".equals(indicatorLevel)){
					indicatorAnalysis = indicatorAnalysisManager.getByTypeCode(checkPeriod, indicatorTypeCode);
				}else{
					indicatorAnalysis = indicatorAnalysisManager.getByLevel(checkPeriod, indicatorTypeCode, Integer.parseInt(indicatorLevel), false);
				}
			}
			indicatorAnalysisList = new ArrayList<Map<String,String>>();
			for(IndicatorAnalysis indicatorAnalysis2 : indicatorAnalysis){
				Map<String, String> indicatorAnalysisMap = new HashMap<String, String>();
				Indicator indicator = indicatorAnalysis2.getIndicator();
				indicatorAnalysisMap.put("id", indicator.getCode());
				indicatorAnalysisMap.put("name", indicator.getName());
				indicatorAnalysisMap.put("level", ""+indicator.getLevel());
				indicatorAnalysisMap.put("seq", ""+indicator.getSeq());
				indicatorAnalysisMap.put("unit", ""+indicator.getUnit());
				indicatorAnalysisMap.put("toPercent", ""+indicator.getToPercent());
				indicatorAnalysisMap.put("needSeparator", ""+indicator.getNeedSeparator());
				indicatorAnalysisMap.put("left_oper", ""+indicator.getLeftOper());
				indicatorAnalysisMap.put("right_oper", ""+indicator.getRightOper());
				Indicator parent = indicator.getParent();
				if(parent!=null){
					indicatorAnalysisMap.put("parent", ""+parent.getCode());
				}
				Integer precision = indicatorAnalysis2.getIndicator().getPrecision();
				if(precision==null){
					precision = 2;
				}
				indicatorAnalysisMap.put("precision", ""+precision);
				indicatorAnalysisMap.put("value", ""+indicatorAnalysis2.getValue());
				indicatorAnalysisList.add(indicatorAnalysisMap);
			}
			
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	private IndicatorTypeManager indicatorTypeManager;
	
	public void setIndicatorTypeManager(IndicatorTypeManager indicatorTypeManager) {
		this.indicatorTypeManager = indicatorTypeManager;
	}

	public String downloadAnalysisGraph(){
		String imgFullPath = "";
		String fileName = "杜邦分析";
		fileName = indicatorTypeManager.get(indicatorTypeCode).getName();
		try {
			HttpServletRequest request = this.getRequest();
			String graphStr = request.getParameter("graphStr");
			BASE64Decoder decoder = new BASE64Decoder();
			// Base64解码
			byte[] b = decoder.decodeBuffer(graphStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			imgFullPath = request.getRealPath( "//home//temporary//");
			imgFullPath += "//"+fileName+DateUtil.convertDateToString("yyyyMMddhhmmss",Calendar.getInstance().getTime())+".png";
			OutputStream out = new FileOutputStream(imgFullPath);
			out.write(b);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxForward(imgFullPath+"@@"+fileName+".png");
	}
	
	public boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
		return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
		// Base64解码
		byte[] b = decoder.decodeBuffer(imgStr);
		for (int i = 0; i < b.length; ++i) {
		if (b[i] < 0) {// 调整异常数据
		b[i] += 256;
		}
		}
		// 生成jpeg图片
		String imgFilePath = imgFile;// 新生成的图片
		OutputStream out = new FileOutputStream(imgFilePath);
		out.write(b);
		out.flush();
		out.close();
		return true;
		} catch (Exception e) {
		throw e;
		}
		}
	
	private String isValid() {
		if (indicatorAnalysis == null) {
			return "Invalid indicatorAnalysis Data";
		}
		return SUCCESS;
	}
	
	public static void main(String[] args) {
		BigDecimal vBigDecimal = new BigDecimal(new Double(2.03652).toString());
		vBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
		System.out.println(vBigDecimal.toString());
	}
}

