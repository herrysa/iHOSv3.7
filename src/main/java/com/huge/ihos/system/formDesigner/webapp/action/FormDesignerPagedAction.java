package com.huge.ihos.system.formDesigner.webapp.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.formDesigner.model.FormDesigner;
import com.huge.ihos.system.formDesigner.service.FormDesignerManager;
import com.huge.ihos.system.reportManager.report.model.UserReportDefine;
import com.huge.ihos.system.reportManager.report.service.UserReportDefineManager;
import com.huge.util.UUIDGenerator;
import com.huge.util.XMLUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class FormDesignerPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3475230866212925700L;
	private FormDesignerManager formDesignerManager;
	private List<FormDesigner> formDesigners;
	private FormDesigner formDesigner;
	private String formId;
	private String formXml;
	
	private BdInfoManager bdInfoManager;

	public BdInfoManager getBdInfoManager() {
		return bdInfoManager;
	}

	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}

	public String getFormXml() {
		return formXml;
	}

	public void setFormXml(String formXml) {
		this.formXml = formXml;
	}

	public FormDesignerManager getFormDesignerManager() {
		return formDesignerManager;
	}

	public void setFormDesignerManager(FormDesignerManager formDesignerManager) {
		this.formDesignerManager = formDesignerManager;
	}

	public List<FormDesigner> getformDesigners() {
		return formDesigners;
	}

	public void setFormDesigners(List<FormDesigner> formDesigners) {
		this.formDesigners = formDesigners;
	}

	public FormDesigner getFormDesigner() {
		return formDesigner;
	}

	public void setFormDesigner(FormDesigner formDesigner) {
		this.formDesigner = formDesigner;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
        this.formId = formId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String formDesignerGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = formDesignerManager
					.getFormDesignerCriteria(pagedRequests,filters);
			this.formDesigners = (List<FormDesigner>) pagedRequests.getList();
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
			if(!this.isEntityIsNew()){
				FormDesigner formDesignerOld = formDesignerManager.get(formDesigner.getFormId());
				formDesigner.setFormXml(formDesignerOld.getFormXml());
			}
			formDesignerManager.save(formDesigner);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "formDesigner.added" : "formDesigner.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (formId != null) {
        	formDesigner = formDesignerManager.get(formId);
        	this.setEntityIsNew(false);
        } else {
        	formDesigner = new FormDesigner();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String formDesignerGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					FormDesigner formDesigner = formDesignerManager.get(new String(removeId));
					formDesignerManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("formDesigner.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkFormDesignerGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (formDesigner == null) {
			return "Invalid formDesigner Data";
		}

		return SUCCESS;

	}
	public String getFormDesignerXml(){
		try {
			if (formId != null) {
				formDesigner = formDesignerManager.get(formId);
				formXml = formDesigner.getFormXml();
	        }
			if(formXml==null){
				formXml = "";
        	}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getFormDetailDesignerXml(){
		try {
			if (formId != null) {
				formDesigner = formDesignerManager.get(formId);
				formXml = formDesigner.getDetailXml();
				if(formXml==null){
					formXml = getTreeListByBdInfo(formDesigner.getDetailBdinfoId());
				}
	        }
			if(formXml==null){
				formXml = "";
        	}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getFormDesignerTreeXml(){
		try {
			if (formId != null) {
				formDesigner = formDesignerManager.get(formId);
				String bdinfoStr = formDesigner.getBdInfoId();
				if(StringUtils.isNotEmpty(bdinfoStr)){
					Document document = XMLUtil.createDocument();
					Element Data = document.addElement("Data");
					Element items = Data.addElement("items");
					String[] bdinfoArr = bdinfoStr.split(",");
					for(String bdinfoId : bdinfoArr){
						BdInfo  bdInfo = bdInfoManager.get(bdinfoId);
						Element itemParent = items.addElement("item");
						itemParent.addAttribute("ID",bdInfo.getBdInfoId());
						itemParent.addAttribute("str",bdInfo.getBdInfoName());
						Set<FieldInfo> fieldInfos = bdInfo.getFieldInfos();
						for(FieldInfo fieldInfo : fieldInfos){
							Element item = items.addElement("item");
							item.addAttribute("ID",fieldInfo.getFieldCode());
							item.addAttribute("PID",bdInfo.getBdInfoId());
							item.addAttribute("str",fieldInfo.getFieldName());
							item.addAttribute("editType","edit");
							item.addAttribute("para","id="+fieldInfo.getFieldCode()+";leftText="+fieldInfo.getFieldName());
						}
					}
					formXml = XMLUtil.xmltoString(document);
				}
	        }
			if(formXml==null){
				formXml = "";
        	}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String saveFormDesignerXml(){
		try {
			if (formId != null) {
				if(formXml!=null&&!"".equals(formXml)){
					formDesigner = formDesignerManager.get(formId);
					formDesigner.setFormXml(formXml);
					formDesignerManager.save(formDesigner);
					return ajaxForward(true, "保存成功！",false);
				}else{
					return ajaxForward(false, "保存失败！",false);
				}
			}else{
				return ajaxForward(false, "保存失败！",false);
			}
			} catch (Exception e) {
				e.printStackTrace();
				return ajaxForward(false, "保存失败！",false);
			}
	}
	
	public String saveFormDetailDesignerXml(){
		try {
			if (formId != null) {
				if(formXml!=null&&!"".equals(formXml)){
					formDesigner = formDesignerManager.get(formId);
					formDesigner.setDetailXml(formXml);
					formDesignerManager.save(formDesigner);
					return ajaxForward(true, "保存成功！",false);
				}else{
					return ajaxForward(false, "保存失败！",false);
				}
			}else{
				return ajaxForward(false, "保存失败！",false);
			}
			} catch (Exception e) {
				e.printStackTrace();
				return ajaxForward(false, "保存失败！",false);
			}
	}
	
	public String getTreeListBySearch(){
		try {
			formXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><!--By Supcan TreeList --><TreeList><Properties Title=\"订单明细表\" editAble=\"true\" HeaderFontIndex=\"2\"><backGround bgColor=\"#EEEEEE\"/></Properties><Fonts><Font faceName=\"Consolas\"/><Font /><Font faceName=\"微软雅黑\" bold=\"1\"/><Font bold=\"true\" /></Fonts><Cols><Col name=\"Country\"      width=\"100\">国家</Col><Col name=\"OrderID\"      width=\"70\" align=\"center\">订单号</Col><Col name=\"CustomerID\"   width=\"70\">客户号</Col><Col name=\"OrderDate\"    width=\"90\" datatype=\"date\">销售日期</Col><Col name=\"RequiredDate\" width=\"90\" datatype=\"date\">接单日期</Col><Col name=\"Freight\"      width=\"80\" datatype=\"double\" decimal=\"2\">货重</Col><Col name=\"ShipName\"     width=\"210\">船名</Col><Col name=\"ShipCity\"     width=\"130\">城市</Col><Col name=\"ShipAddress\"  width=\"340\">地址</Col></Cols></TreeList>";
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	String bdInfoId;
	public String getBdInfoId() {
		return bdInfoId;
	}

	public void setBdInfoId(String bdInfoId) {
		this.bdInfoId = bdInfoId;
	}

	public String getTreeListByBdInfo(String bdId){
		try {
			if(StringUtils.isNotEmpty(bdId)){
				BdInfo bdInfo = bdInfoManager.get(bdId);
				Document document = XMLUtil.createDocument();
				Element TreeList = document.addElement("TreeList");
				Element Properties = TreeList.addElement("Properties");
				Properties.addAttribute("HeaderFontIndex", "1");
				Properties.addAttribute("editAble", "true");
				Element Fonts = TreeList.addElement("Fonts");
				Element Font0 = Fonts.addElement("Font");
				Font0.addAttribute("faceName", "Consolas");
				Element Font1 = Fonts.addElement("Font");
				Element Font2 = Fonts.addElement("Font");
				Font2.addAttribute("faceName", "微软雅黑");
				Font2.addAttribute("bold", "1");
				Element Font3 = Fonts.addElement("Font");
				Font3.addAttribute("bold", "true");
				
				Element Cols = TreeList.addElement("Cols");
				
				Set<FieldInfo> fieldInfos = bdInfo.getFieldInfos();
				for(FieldInfo fieldInfo : fieldInfos){
					Element col = Cols.addElement("Col");
					col.addAttribute("name",fieldInfo.getFieldCode());
					//col.addAttribute("PID",bdInfo.getBdInfoId());
					//col.addAttribute("str",fieldInfo.getFieldName());
					col.addAttribute("editAble","true");
					col.addText(fieldInfo.getFieldName());
				}
				formXml = XMLUtil.xmltoString(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formXml;
	}
	
	public String getTreeListByBdInfo(){
		try {
			if(StringUtils.isNotEmpty(bdInfoId)){
				BdInfo bdInfo = bdInfoManager.get(bdInfoId);
				Document document = XMLUtil.createDocument();
				Element TreeList = document.addElement("TreeList");
				Element Properties = TreeList.addElement("Properties");
				Properties.addAttribute("HeaderFontIndex", "2");
				Element Fonts = TreeList.addElement("Fonts");
				Element Font0 = Fonts.addElement("Font");
				Font0.addAttribute("faceName", "Consolas");
				Element Font1 = Fonts.addElement("Font");
				Element Font2 = Fonts.addElement("Font");
				Font2.addAttribute("faceName", "微软雅黑");
				Font2.addAttribute("bold", "1");
				Element Font3 = Fonts.addElement("Font");
				Font3.addAttribute("bold", "true");
				
				Element Cols = TreeList.addElement("Cols");
				
				Set<FieldInfo> fieldInfos = bdInfo.getFieldInfos();
				for(FieldInfo fieldInfo : fieldInfos){
					Element col = Cols.addElement("Col");
					col.addAttribute("name",fieldInfo.getFieldCode());
					//col.addAttribute("PID",bdInfo.getBdInfoId());
					//col.addAttribute("str",fieldInfo.getFieldName());
					col.addAttribute("editAble","true");
					col.addText(fieldInfo.getFieldName());
				}
				formXml = XMLUtil.xmltoString(document);
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	UserReportDefineManager userReportDefineManager;
	
	public void setUserReportDefineManager(
			UserReportDefineManager userReportDefineManager) {
		this.userReportDefineManager = userReportDefineManager;
	}

	public String getPrintReportXml(){
		try {
			if (formId != null) {
				formDesigner = formDesignerManager.get(formId);
				if("user".equals(from)){
					String userId = UserContextUtil.getLoginUserId();
					List<PropertyFilter> userReportFilters = new ArrayList<PropertyFilter>();
					userReportFilters.add(new PropertyFilter("EQS_userId",userId));
					userReportFilters.add(new PropertyFilter("EQS_defineClass",formId));
					List<UserReportDefine> userReportDefines = userReportDefineManager.getByFilters(userReportFilters);
					if(userReportDefines!=null&&!userReportDefines.isEmpty()){
						formXml = userReportDefines.get(0).getReportXml();
					}
				}
				if(formXml==null){
					formXml = formDesigner.getPrintReportXml();
				}
				if(formXml==null){
					HttpServletRequest request = getRequest();
		        	HttpSession session = request.getSession();
		        	String blankPath = session.getServletContext().getRealPath("/home/supcan/userdefine/blank.xml");
		        	File blank = new File(blankPath);
		        	formXml = XMLUtil.xmltoString(XMLUtil.read(blank, "UTF-8"));
	        	}
	        }
			if(formXml==null){
				formXml = "";
        	}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String from;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String savePrintReportXml(){
		try {
			if (formId != null) {
				if(formXml!=null&&!"".equals(formXml)){
					formDesigner = formDesignerManager.get(formId);
					if("user".equals(from)){
						String userId = UserContextUtil.getLoginUserId();
						String delSql = "delete from sy_userReportDefine where userId='"+userId+"' and defineClass='"+formId+"'";
						formDesignerManager.executeSql(delSql);
						String insertSql = "insert into sy_userReportDefine (defineId,userId,defineClass,reportXml) values ('"+UUIDGenerator.getInstance().getNextValue()+"','"+userId+"','"+formId+"','"+formXml+"')";
						formDesignerManager.executeSql(insertSql);
					}else{
						formDesigner.setPrintReportXml(formXml);
						formDesignerManager.save(formDesigner);
					}
					return ajaxForward(true, "保存成功！",false);
				}else{
					return ajaxForward(false, "保存失败！",false);
				}
			}else{
				return ajaxForward(false, "保存失败！",false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！",false);
		}
	}
	
	public String createPrintReport(){
		try {
			if (formId != null) {
				if(formXml!=null&&!"".equals(formXml)){
					formDesigner = formDesignerManager.get(formId);
					formXml = formDesigner.getFormXml();
					if(formXml!=null){
						Document formDocument = XMLUtil.stringToXml(formXml);
						Document reportDocument = XMLUtil.createDocument();
						Element freeform = formDocument.getRootElement();
						Element report = reportDocument.addElement("Report");
						
						Element formProperties = freeform.element("Properties");
						Element workSheet = report.addElement("WorkSheet");
						workSheet.addAttribute("name", "sheet1");
						workSheet.addAttribute("isDefaultPrint", "true");
						Element tableProperties = workSheet.addElement("Properties");
						Element formBackGround = formProperties.element("BackGround");
						Element reportBackGround = (Element)formBackGround.clone();
						tableProperties.add(reportBackGround);
						
						Element DefaultTD = tableProperties.element("DefaultTD");
						Element DefaultTD_TD = DefaultTD.element("TD");
						DefaultTD_TD.addAttribute("fontIndex", "0");
						DefaultTD_TD.addAttribute("textColor", "#000000");
						DefaultTD_TD.addAttribute("transparent", "true");
						DefaultTD_TD.addAttribute("leftBorder", "0");
						DefaultTD_TD.addAttribute("topBorder", "0");
						DefaultTD_TD.addAttribute("leftBorderColor", "#C0C0C0");
						DefaultTD_TD.addAttribute("leftBorderStyle", "solid");
						DefaultTD_TD.addAttribute("topBorderColor", "#C0C0C0");
						DefaultTD_TD.addAttribute("topBorderStyle", "solid");
						DefaultTD_TD.addAttribute("decimal", "2");
						DefaultTD_TD.addAttribute("align", "center");
						DefaultTD_TD.addAttribute("vAlign", "middle");
						DefaultTD_TD.addAttribute("isDataSource", "false");
						DefaultTD_TD.addAttribute("isThousandSeparat", "true");
						DefaultTD_TD.addAttribute("isRound", "true");
						DefaultTD_TD.addAttribute("isPrint", "true");
						
						Element Other = tableProperties.element("Other");
						Other.addAttribute("isShowZero", "true");
						Other.addAttribute("LineDistance", "0");
						Other.addAttribute("isRowHeightAutoExtendAble", "true");
						
						Element formFonts = formProperties.element("Fonts");
						Element reportFonts = tableProperties.element("Other");
						Iterator<Element> fontIt = formFonts.elementIterator();
						while(fontIt.hasNext()){
							Element font = fontIt.next();
							reportFonts.add((Element)font.clone());
						}
						
						Element formObjects = freeform.element("Objects");
						Element TableLayout = formObjects.element("TableLayout");
						Element reportTable = workSheet.addElement("table");
						Element GraphicObjects = workSheet.addElement("GraphicObjects");
						
						Iterator<Element> formColIt = TableLayout.elementIterator("col");
						Map<String, Integer> colWidthMap = new HashMap<String, Integer>();
						int itIndex = 0;
						while(formColIt.hasNext()){
							Element col = formColIt.next();
							Element reportCol = reportTable.addElement("Col");
							String width = col.attributeValue("width");
							if(StringUtils.isEmpty(width)||"0.10".equals(width)){
								width = "24";
							}
							int w = Integer.parseInt(width);
							colWidthMap.put(""+itIndex, w);
							reportCol.addAttribute("width", width);
							itIndex++;
						}
						itIndex = 0;
						
						Iterator<Element> formTrIt = TableLayout.elementIterator("tr");
						while(formTrIt.hasNext()){
							Element formTr = formColIt.next();
							String trHeight = formTr.attributeValue("height");
							if(StringUtils.isEmpty(trHeight)||"0.10".equals(trHeight)){
								trHeight = "24";
							}
							Integer h = Integer.parseInt(trHeight);
							Element reportTr = reportTable.element("TR");
							Iterator<Element> formTdIt = formTr.elementIterator("td");
							List<Element> tdlist = new ArrayList<Element>();
							while(formTdIt.hasNext()){
								int itIndex2 = 0;
								Element formTd = formTdIt.next();
								Iterator<Element> inputIt = formTd.elementIterator();
								while(inputIt.hasNext()){
									Element formInput = inputIt.next();
									String inputName = formInput.getName();
									if("Text".equals(inputName)){
										String fontIndex = formInput.attributeValue("fontIndex");
										String text = formInput.attributeValue("text");
										String align = formInput.attributeValue("align");
										String vAlign = formInput.attributeValue("vAlign");
										String width = formInput.attributeValue("width");
										
										Element TextBox = GraphicObjects.element("TextBox");
										TextBox.addAttribute("fontIndex", fontIndex);
										TextBox.addAttribute("text", text);
										TextBox.addAttribute("align", align);
										TextBox.addAttribute("vAlign", vAlign);
										
										Element Rect = TextBox.element("Rect");
										
										
									}else if("Input".equals(inputName)){
										
									}else if("Line".equals(inputName)){
										
									}
								}
							}
						}
						
					}
					return ajaxForward(true, "保存成功！",false);
				}else{
					return ajaxForward(false, "保存失败！",false);
				}
			}else{
				return ajaxForward(false, "保存失败！",false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！",false);
		}
	}
	
	public String getPrintReportDataSourceXml(){
		try {
			if (formId != null) {
				formDesigner = formDesignerManager.get(formId);
				String formTable = formDesigner.getBdInfoId();
				String detailTable = formDesigner.getDetailBdinfoId();
				
				Document document = XMLUtil.createDocument();
				Element category = document.addElement("Catalog");
				Element Project = category.addElement("Project");
				Project.addAttribute("text", "表单数据");
				if(StringUtils.isNotEmpty(formTable)){
					String dsSql = "select fieldCode code,fieldName name from t_fieldinfo where bdInfoId='"+formTable+"' order by sn asc";
					String dataSql = "select * from "+formTable+" where 1=1";
					Element formDs = Project.addElement("ds");
					formDs.setText("主表数据");
					formDs.addAttribute("id", "form");
					formDs.addAttribute("descURL", "getDataXml?dataType=col&sql="+dsSql);
					formDs.addAttribute("dataURL", "getDataSourceBySql?sql="+dataSql);
				}
				if(StringUtils.isNotEmpty(detailTable)){
					String dsSql = "select fieldCode code,fieldName name from t_fieldinfo where bdInfoId='"+detailTable+"' order by sn asc";
					String dataSql = "select * from "+detailTable+" where 1=1";
					Element detailDs = Project.addElement("ds");
					detailDs.setText("明细数据");
					detailDs.addAttribute("id", "detail");
					detailDs.addAttribute("descURL", "getDataXml?dataType=col&sql="+dsSql);
					detailDs.addAttribute("dataURL", "getDataSourceBySql?sql="+dataSql);
				}
				
				formXml = XMLUtil.xmltoString(document);
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPrintReportFormDataSourceXml(){
		try {
			if (formId != null) {
				formDesigner = formDesignerManager.get(formId);
				String formTable = formDesigner.getBdInfoId();
				
				Document document = XMLUtil.createDocument();
				Element Data = document.addElement("Data");
				Element ID = Data.addElement("ID");
				ID.setText("form");
				Element Version = Data.addElement("Version");
				Version.setText("2");
				Element Type = Data.addElement("Type");
				Type.setText("0");
				Element TypeMeaning = Data.addElement("TypeMeaning");
				TypeMeaning.setText("XML");
				Element Source = Data.addElement("Source");
				Source.setText("");
				Element XML_RecordAble_Nodes = Data.addElement("XML_RecordAble_Nodes");
				Element Node = XML_RecordAble_Nodes.addElement("Node");
				Element node_name = Node.addElement("name");
				node_name.setText("row");
				Element Columns = Data.addElement("Columns");
				
				if(StringUtils.isNotEmpty(formTable)){
					String dsSql = "select fieldCode code,fieldName name from t_fieldinfo where bdInfoId='"+formTable+"' order by sn asc";
					List<Map<String, Object>> datas = formDesignerManager.getBySqlToMap(dsSql);
					String dataSql = "select * from "+formTable+" where 1=1";
					Source.setText("getDataSourceBySql?sql="+dataSql);
					for(Map<String, Object> data : datas){
						String name = data.get("name").toString();
						String code = data.get("code").toString();
						//String datatype = data.get("datatype").toString();
						Element Column = Columns.addElement("Column");
						Element col_name = Column.addElement("name");
						col_name.setText(code);
						Element col_text = Column.addElement("text");
						col_text.setText(name);
						/*Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");*/
						//col.addAttribute("datatype", datatype);
					}
				}
				formXml = XMLUtil.xmltoString(document);
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPrintReportDetailDataSourceXml(){
		try {
			if (formId != null) {
				formDesigner = formDesignerManager.get(formId);
				String detailTable = formDesigner.getDetailBdinfoId();
				
				Document document = XMLUtil.createDocument();
				Element Data = document.addElement("Data");
				Element ID = Data.addElement("ID");
				ID.setText("detail");
				Element Version = Data.addElement("Version");
				Version.setText("2");
				Element Type = Data.addElement("Type");
				Type.setText("0");
				Element TypeMeaning = Data.addElement("TypeMeaning");
				TypeMeaning.setText("XML");
				Element Source = Data.addElement("Source");
				Source.setText("");
				Element XML_RecordAble_Nodes = Data.addElement("XML_RecordAble_Nodes");
				Element Node = XML_RecordAble_Nodes.addElement("Node");
				Element node_name = Node.addElement("name");
				node_name.setText("row");
				Element Columns = Data.addElement("Columns");
				if(StringUtils.isNotEmpty(detailTable)){
					String dsSql = "select fieldCode code,fieldName name from t_fieldinfo where bdInfoId='"+detailTable+"' order by sn asc";
					List<Map<String, Object>> datas = formDesignerManager.getBySqlToMap(dsSql);
					String dataSql = "select * from "+detailTable+" where 1=1";
					Source.setText("getDataSourceBySql?sql="+dataSql);
					for(Map<String, Object> data : datas){
						String name = data.get("name").toString();
						String code = data.get("code").toString();
						//String datatype = data.get("datatype").toString();
						Element Column = Columns.addElement("Column");
						Element col_name = Column.addElement("name");
						col_name.setText(code);
						Element col_text = Column.addElement("text");
						col_text.setText(name);
						/*Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");
						Element col_name = col.addElement("name");*/
						//col.addAttribute("datatype", datatype);
					}
				}
				
				formXml = XMLUtil.xmltoString(document);
			}
			HttpServletResponse response = getResponse();  
			//设置编码  
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/xml;charset=utf-8");  
			response.setHeader("Cache-Control", "no-cache");  
			PrintWriter out = response.getWriter();  
			out.write(formXml);  
			out.flush();  
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

