package com.huge.ihos.system.importdata.webapp.action;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;

import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.importdata.model.ImportDataDefine;
import com.huge.ihos.system.importdata.model.ImportDataDefineDetail;
import com.huge.ihos.system.importdata.service.ImportDataDefineManager;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class ImportDataDefinePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4011226660113096425L;
	private ImportDataDefineManager importDataDefineManager;
	private List<ImportDataDefine> importDataDefines;
	private List<ImportDataDefineDetail> details;
	private ImportDataDefine importDataDefine;
	private String interfaceId;
	private File excelFile;
	private String tableName;
	private String fileName;
	private InputStream excelStream;
	private String interfaceName;
	private String subSystemCode;//子系统代码
	private String callBackFunc;//导入成功回调函数
	private long systemTime;
	
	private String whereSql;

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	public long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(long systemTime) {
		this.systemTime = systemTime;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<ImportDataDefineDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ImportDataDefineDetail> details) {
		this.details = details;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public ImportDataDefineManager getImportDataDefineManager() {
		return importDataDefineManager;
	}

	public void setImportDataDefineManager(ImportDataDefineManager importDataDefineManager) {
		this.importDataDefineManager = importDataDefineManager;
	}

	public List<ImportDataDefine> getImportDataDefines() {
		return importDataDefines;
	}

	public List<ImportDataDefine> getimportDataDefines() {
		return importDataDefines;
	}

	public void setImportDataDefines(List<ImportDataDefine> importDataDefines) {
		this.importDataDefines = importDataDefines;
	}

	public ImportDataDefine getImportDataDefine() {
		return importDataDefine;
	}

	public void setImportDataDefine(ImportDataDefine importDataDefine) {
		this.importDataDefine = importDataDefine;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String importDataDefineGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = importDataDefineManager.getImportDataDefineCriteria(pagedRequests, filters);
			this.importDataDefines = (List<ImportDataDefine>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			importDataDefineManager.save(importDataDefine);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "importDataDefine.added" : "importDataDefine.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (interfaceId != null) {
			importDataDefine = importDataDefineManager.get(interfaceId);
			this.setEntityIsNew(false);
		} else {
			importDataDefine = new ImportDataDefine();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String importDataDefineGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					//Long removeId = Long.parseLong(ids.nextToken());
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//ImportDataDefine importDataDefine = importDataDefineManager.get(new String(removeId));
					importDataDefineManager.remove(removeId);

				}
				gridOperationMessage = this.getText("importDataDefine.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkImportDataDefineGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String importResult;

	public String getImportResult() {
		return importResult;
	}

	public void setImportResult(String importResult) {
		this.importResult = importResult;
	}

	/**外部数据导入*/
	public String importExcelDataDefine() {
		try {
			if (OtherUtil.measureNotNull(interfaceId)) {
				int flag = this.importDataDefineManager.importData(interfaceId, subSystemCode,systemTime);
				if (flag == 1) {
					String fileName = interfaceId + systemTime + ".txt";
					String filePath = "/home/temporary/";
					ServletContext context = ServletActionContext.getServletContext();
					String realPath = context.getRealPath(filePath) + "/" + fileName;
					File file = new File(realPath);
					file.delete();
					return ajaxForwardSuccess("导入成功！");
				} else if (flag == 0) {
					return ajaxForwardError("请先【检查】后再导入！");
				} else {
					return ajaxForwardError("导入失败！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxForwardError("导入失败！");
	}

	/**
	 * 唯一性验证
	 * @return
	 */
	public String checkUniqueName() {
		String interfaceName = getRequest().getParameter("interfaceName");
		if (OtherUtil.measureNotNull(interfaceName)) {
			PropertyFilter filter = new PropertyFilter("EQS_interfaceName", interfaceName);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(filter);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = importDataDefineManager.getImportDataDefineCriteria(pagedRequests, filters);
			this.importDataDefines = (List<ImportDataDefine>) pagedRequests.getList();
			if (this.importDataDefines.size() != 0) {
				return ajaxForwardError("接口名已存在！");
			}
		}
		return SUCCESS;
	}

	/**
	 * Excel模板导出
	 * @return
	 */
	public String OutputImportDataExcel() {
		String temp = getRequest().getParameter("temp");
		if (OtherUtil.measureNotNull(interfaceId)) {
			try {
				ImportDataDefine define = importDataDefineManager.get(interfaceId);
				Set<ImportDataDefineDetail> details = define.getImportDataDefineDetails();
				List<ImportDataDefineDetail> list = new ArrayList<ImportDataDefineDetail>(details);
				//按照sn排序
				Collections.sort(list, new Comparator<ImportDataDefineDetail>() {
					@Override
					public int compare(ImportDataDefineDetail o1, ImportDataDefineDetail o2) {
						return o1.getSn() - o2.getSn();
					}
				});
				if ("excel".equals(temp)) {
					fileName = define.getInterfaceName() + ".xls";
					fileName = new String(this.fileName.getBytes("GBK"), "ISO-8859-1");
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					this.exportExcel(list, out);
					byte[] fileContent = out.toByteArray();
					ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
					excelStream = is;
				} else if ("txt".equals(temp)) {
					String exportStr = exportTxt(list);
					excelStream = new ByteArrayInputStream(exportStr.getBytes());
					fileName = define.getInterfaceName() + ".txt";
					fileName = new String(this.fileName.getBytes("GBK"), "ISO-8859-1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	/**
	 * Txt模板导出细节
	 * @param list
	 * @return
	 */
	private String exportTxt(List<ImportDataDefineDetail> list) {
		String exportStr = "";
		for (int i = 0; i < list.size(); i++) {
			String detailName = list.get(i).getDetailName();
			exportStr += detailName + "     ";
		}
		return exportStr;
	}

	/**
	 * Excel模版导出细节
	 * @param list
	 * @param out
	 */
	private void exportExcel(List<ImportDataDefineDetail> list, OutputStream out) {
		// 声明一个工作薄  
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格  
		HSSFSheet sheet = workbook.createSheet("数据");
		// 设置表格默认列宽度为15个字节  
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式  
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式  
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体  
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式  
		style.setFont(font);
		// 产生表格标题行  
		HSSFRow row = sheet.createRow(0);

		for (int i = 0; i < list.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(list.get(i).getDetailName());
			cell.setCellValue(text);
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public String showImportDataLog() {
		return SUCCESS;
	}

	public String importDataLogGridList() {
		try {
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = importDataDefineManager.getImportDataLoggerCriteria(pagedRequests, this.taskInterId);
			this.interLoggers = (List<InterLogger>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("importDataLogGridList Error", e);
		}
		return SUCCESS;

	}

	private String taskInterId;
	private List<InterLogger> interLoggers;

	public List<InterLogger> getInterLoggers() {
		return interLoggers;
	}

	public void setInterLoggers(List<InterLogger> interLoggers) {
		this.interLoggers = interLoggers;
	}

	public String getTaskInterId() {
		return taskInterId;
	}

	public void setTaskInterId(String taskInterId) {
		this.taskInterId = taskInterId;
	}

	public String findById() {
		ImportDataDefine define = importDataDefineManager.get(interfaceId);
		interfaceName = define.getInterfaceName();
		Set<ImportDataDefineDetail> importDataDefineDetails = define.getImportDataDefineDetails();
		details = new ArrayList<ImportDataDefineDetail>(importDataDefineDetails);
		Collections.sort(details, new Comparator<ImportDataDefineDetail>() {

			@Override
			public int compare(ImportDataDefineDetail o1, ImportDataDefineDetail o2) {

				return o1.getSn() - o2.getSn();
			}
		});
		return SUCCESS;
	}

	public String findAll() {
		importDataDefines = importDataDefineManager.getAll();
		return SUCCESS;
	}

	private String isValid() {
		if (importDataDefine == null) {
			return "Invalid importDataDefine Data";
		}
		return SUCCESS;
	}

	public String checkImportFile() {
		try {
			String excelFileName = getRequest().getParameter("excelFileName");
			if (OtherUtil.measureNotNull(interfaceId)) {
				this.importDataDefineManager.deleteImportDataLog(interfaceId);
				importDataDefine = this.importDataDefineManager.get(interfaceId);
			}
			List<String> list = null;
			if(whereSql==null){
				whereSql = "";
			}
			if (excelFileName.endsWith(".xls")) {
				list = this.importDataDefineManager.readImportFile(excelFile, "excel", importDataDefine, tableName, subSystemCode,whereSql);
			} else if (excelFileName.endsWith(".txt")) {
				list = this.importDataDefineManager.readImportFile(excelFile, "txt", importDataDefine, tableName, subSystemCode,whereSql);
			} else {
				return ajaxForwardError("请选择正确的文件格式，Excel或者文本文件！");
			}
			if (list != null && !list.isEmpty()) {
				systemTime = new Date().getTime();
				String fileName = importDataDefine.getInterfaceId() + systemTime + ".txt";
				String filePath = "/home/temporary/";
				ServletContext context = ServletActionContext.getServletContext();
				String realPath = context.getRealPath(filePath) + "/" + fileName;
				OptFile.mkParent(realPath);
				writeDataToFile(list, realPath);
				return ajaxForwardSuccess("检查通过！");
			} else {
				return ajaxForwardError("检查未通过，细节请查看日志！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForwardError("检查未通过，细节请查看日志！");
		}
	}

	private void writeDataToFile(List<String> list, String filePath) {
		String txtString = "";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			txtString += string + "\r\n";
		}
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(filePath);
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);
			bw.write(txtString);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				osw.close();
				bw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public String getSubSystemCode() {
		return subSystemCode;
	}

	public void setSubSystemCode(String subSystemCode) {
		this.subSystemCode = subSystemCode;
	}

	public String getCallBackFunc() {
		return callBackFunc;
	}

	public void setCallBackFunc(String callBackFunc) {
		this.callBackFunc = callBackFunc;
	}
}
