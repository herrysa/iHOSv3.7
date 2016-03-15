package com.huge.ihos.system.importdata.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.datacollection.dao.InterLoggerDao;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.importdata.dao.ImportDataDefineDao;
import com.huge.ihos.system.importdata.model.ImportDataCheck;
import com.huge.ihos.system.importdata.model.ImportDataCheckMessage;
import com.huge.ihos.system.importdata.model.ImportDataDefine;
import com.huge.ihos.system.importdata.model.ImportDataDefineDetail;
import com.huge.ihos.system.importdata.service.ImportDataDefineManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;

@Service("importDataDefineManager")
public class ImportDataDefineManagerImpl extends GenericManagerImpl<ImportDataDefine, String> implements ImportDataDefineManager {
	private ImportDataDefineDao importDataDefineDao;
	private InterLoggerDao interLoggerDao;

	@Autowired
	public ImportDataDefineManagerImpl(ImportDataDefineDao importDataDefineDao, InterLoggerDao interLoggerDao) {
		super(importDataDefineDao);
		this.importDataDefineDao = importDataDefineDao;
		this.interLoggerDao = interLoggerDao;
	}

	public JQueryPager getImportDataDefineCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {
		return importDataDefineDao.getImportDataDefineCriteria(paginatedList, filters);
	}

	public ImportDataDefine findById(String interfaceId) {
		return this.importDataDefineDao.findById(interfaceId);
	}

	@Override
	public List<String> readImportFile(File file, String type, ImportDataDefine importDataDefine, String tableName, String subSystemCode) {
		if (type != null && !"".equals(type)) {
			Set<ImportDataCheckMessage> messagess = new HashSet<ImportDataCheckMessage>();
			if ("txt".equals(type)) {
				messagess = this.readTxtImportFile(file, importDataDefine, tableName);
			} else if ("excel".equals(type)) {
				messagess = this.readExcelImportFile(file, importDataDefine, tableName);
			} else {
				return null;
			}
			List<ImportDataCheckMessage> messages = new ArrayList<ImportDataCheckMessage>(messagess);
			Collections.sort(messages,new Comparator<ImportDataCheckMessage>() {

				@Override
				public int compare(ImportDataCheckMessage o1, ImportDataCheckMessage o2) {
					return o1.getColNum() - o2.getColNum();
				}
			});
			if (!messages.isEmpty() && messages != null) {
				List<String> errorList = new ArrayList<String>();
				for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
					ImportDataCheckMessage importDataCheckMessage = (ImportDataCheckMessage) iterator.next();
					if (!importDataCheckMessage.isSuccess()) {
						errorList.add(importDataCheckMessage.getMessage());
					}
				}
				if (errorList.isEmpty()) {
					List<String> successList = new ArrayList<String>();
					for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
						ImportDataCheckMessage importDataCheckMessage = (ImportDataCheckMessage) iterator.next();
						successList.add(importDataCheckMessage.getMessage());
					}
					InterLogger logger = new InterLogger();
					logger.setLogDateTime(new Date());
					logger.setLogMsg("检查通过！");
					logger.setPeriodCode(UserContextUtil.getUserContext().getPeriodMonth());
					logger.setLogFrom("检查通过");
					logger.setTaskInterId(importDataDefine.getInterfaceId());
					logger.setSubSystemCode(subSystemCode);
					interLoggerDao.save(logger);
					return successList;
				} else {

					String log = "";
					for (int i = 0; i < errorList.size(); i++) {
						log += errorList.get(i) + ";";
					}
					if (!"".equals(log)) {
						InterLogger logger = new InterLogger();
						logger.setLogDateTime(new Date());
						logger.setLogMsg(log);
						logger.setPeriodCode(UserContextUtil.getUserContext().getPeriodMonth());
						logger.setLogFrom("检查未通过");
						logger.setTaskInterId(importDataDefine.getInterfaceId());
						logger.setSubSystemCode(subSystemCode);
						interLoggerDao.save(logger);
					}
					return null;
				}
			} else {
				InterLogger logger = new InterLogger();
				logger.setLogDateTime(new Date());
				logger.setLogMsg("文件中没有数据！");
				logger.setPeriodCode(UserContextUtil.getUserContext().getPeriodMonth());
				logger.setLogFrom("检查通过");
				logger.setTaskInterId(importDataDefine.getInterfaceId());
				logger.setSubSystemCode(subSystemCode);
				interLoggerDao.save(logger);
				return null;
			}
		} else {
			return null;
		}

	}

	private List<ImportDataCheck> initImportDataCheck() {
		List<ImportDataCheck> checks = new ArrayList<ImportDataCheck>();
		ImportDataCheck check = new ImportDataCheck();
		check.setCheckType("1");
		check.setCheckContent("期间:%PERIOD_MONTH%");
		checks.add(check);
		ImportDataCheck check1 = new ImportDataCheck();
		check1.setCheckType("2");
		check1.setCheckContent("单位编码:orgCode");
		check1.setErrorMessage("单位编码不存在");
		checks.add(check1);
		ImportDataCheck check2 = new ImportDataCheck();
		check2.setCheckType("2");
		check2.setCheckContent("人员编码:personCode");
		check2.setErrorMessage("人员编码不存在");
		checks.add(check2);
		ImportDataCheck check3 = new ImportDataCheck();
		check3.setCheckType("2");
		check3.setCheckContent("人员编码,单位编码:personCode,orgCode");
		check3.setErrorMessage("该单位下不存在该人员");
		checks.add(check3);
		return checks;
	}

	private Map<String, List<String>> initCheckDatas() {
		String period = UserContextUtil.getUserContext().getPeriodMonth();
		List<Object[]> list = this.importDataDefineDao.getBySql("SELECT personCode,orgCode FROM gz_gzContent WHERE period='" + period + "'");
		List<String> datas = new ArrayList<String>();
		List<String> personCodes = new ArrayList<String>();
		List<String> orgCodes = new ArrayList<String>();
		Map<String, List<String>> checkDatas = new HashMap<String, List<String>>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			personCodes.add((String) objects[0]);
			orgCodes.add((String) objects[1]);
			datas.add((String) objects[0] + "," + (String) objects[1]);
		}
		checkDatas.put("personCodes", personCodes);
		checkDatas.put("orgCodes", orgCodes);
		checkDatas.put("datas", datas);
		return checkDatas;
	}

	private ImportDataCheckMessage customCheck(String option, String checkContent, Map<String, List<String>> checkDatas) {
		List<String> personCodes = checkDatas.get("personCodes");
		List<String> orgCodes = checkDatas.get("orgCodes");
		List<String> datas = checkDatas.get("datas");
		ImportDataCheckMessage message = null;
		if (checkContent.contains(",")) {
			if (!datas.contains(option)) {
				String[] options = option.split(",");
				message = new ImportDataCheckMessage(false, -1, "检查人员编码-人员'" + options[0] + "'不在单位'" + options[1] + "'内");
				return message;
			}
		} else {
			if ("orgCode".equals(checkContent)) {
				if (!orgCodes.contains(option)) {
					message = new ImportDataCheckMessage(false, -1, "检查单位编码-文件中存在未定义的【单位编码】'" + option + "'");
					return message;
				}
			} else if ("personCode".equals(checkContent)) {
				if (!personCodes.contains(option)) {
					message = new ImportDataCheckMessage(false, -1, "检查人员编码-【人员编码】'" + option + "'不存在");
					return message;
				}
			}
		}
		return null;
	}

	private Set<ImportDataCheckMessage> readTxtImportFile(File file, ImportDataDefine importDataDefine, String tableName) {
		Set<ImportDataCheckMessage> messages = new HashSet<ImportDataCheckMessage>();
		Set<ImportDataDefineDetail> details = importDataDefine.getImportDataDefineDetails();
		List<ImportDataCheck> checks = initImportDataCheck();
		Map<String, List<String>> checkDatas = initCheckDatas();
		//检查是否定义好检查规则
		if (checks.isEmpty()) {
			ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查-请定义检查规则！");
			messages.add(message);
			return messages;
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int line = 1;
			String lineText = "";
			//List<String> columnNameList = new ArrayList<String>();
			Map<String, Integer> columnNameMap = new HashMap<String, Integer>();
			List<String> checkRepeatList = new ArrayList<String>();
			outFor: while ((lineText = reader.readLine()) != null) {
				if ("".equals(lineText.trim())) {
					line++;
					continue;
				} else {
					String[] strings = lineText.trim().split("\\s+");
					if (columnNameMap.isEmpty()) {
						for (int i = 0; i < strings.length; i++) {
							//columnNameList.add(strings[i]);
							columnNameMap.put(strings[i], i);
						}
						//检查Txt文件中，标题行是否包含接口中定义的全部列信息
						for (Iterator iterator = details.iterator(); iterator.hasNext();) {
							ImportDataDefineDetail importDataDefineDetail = (ImportDataDefineDetail) iterator.next();
							String detailName = importDataDefineDetail.getDetailName();
							if (!columnNameMap.containsKey(detailName)) {
								ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查数据完整性-文件中缺少【" + detailName + "】列");
								messages.add(message);
							} else {
								continue;
							}
						}
						if (messages.isEmpty()) {
							line++;
							continue;
						} else {
							return messages;
						}
					} else {
						if (strings.length != columnNameMap.size()) {
							ImportDataCheckMessage message = new ImportDataCheckMessage(false, line, "检查数据完整性-文件中第" + line + "行的数据不完整");
							messages.add(message);
							line++;
							continue;
						} else {
							boolean flag = true;
							for (int k = 0; k < checks.size(); k++) {
								ImportDataCheck importDataCheck = (ImportDataCheck) checks.get(k);
								String checkContent = importDataCheck.getCheckContent();
								String[] stringArr = checkContent.split(":");
								if ("1".equals(importDataCheck.getCheckType())) {
									String unitCheck = UserContextUtil.getUserContext().getPeriodMonth(); //(String) UserContextUtil.getUserContext().getSysVars().get(stringArr[1]);
									int index = columnNameMap.get(stringArr[0]);
									if (unitCheck != null) {
										if (!unitCheck.equals(strings[index])) {
											ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查" + stringArr[0] + "-文件中【" + stringArr[0] + "】列数据应该为：" + unitCheck);
											messages.add(message);
											flag = false;
											break;
										}
									} else {
										ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查-出现未知错误，请联系管理员！");
										messages.add(message);
										return messages;
									}
								} else if ("2".equals(importDataCheck.getCheckType())) {
									String[] checkContents = stringArr[0].split(",");
									String option = "";
									ImportDataCheckMessage message = null;
									if (checkContents.length == 1) {
										int index = columnNameMap.get(checkContents[0]);
										option = strings[index];
									} else if (checkContents.length == 2) {
										int index1 = columnNameMap.get(checkContents[0]);
										int index2 = columnNameMap.get(checkContents[1]);
										option = strings[index1] + "," + strings[index2];
									}
									message = customCheck(option, stringArr[1], checkDatas);
									if (message != null) {
										messages.add(message);
										flag = false;
										break;
									}

								}
							}

							if (flag) {
								if(columnNameMap.containsKey("人员编码") && columnNameMap.containsKey("人员编码")) {
									String personCode = strings[columnNameMap.get("人员编码")];
									String orgCode = strings[columnNameMap.get("单位编码")];
									checkRepeatList.add(personCode + "," +orgCode);
								}
								String updateSql = "update " + tableName + " set ";
								String whereSql = " where 1=1 ";
								for (ImportDataDefineDetail importDataDefineDetail : details) {
									String detailName = importDataDefineDetail.getDetailName();
									int index = columnNameMap.get(detailName);
									if (importDataDefineDetail.getIsConstraint() == true) {
										whereSql += " and " + importDataDefineDetail.getEntityName() + " = '" + strings[index] + "'";
										continue;
									} else {
										if (importDataDefineDetail.getIsUpdate() == true) {
											if (importDataDefineDetail.getDetailType() == 0) {
												if (!this.isNumber(strings[index], 0)) {
													ImportDataCheckMessage message = new ImportDataCheckMessage(false, line, "检查数据格式-第" + line + "行【" + importDataDefineDetail.getDetailName() + "】列数据应该为小数或整数");
													messages.add(message);
													line++;
													continue outFor;
												}
											} else if (importDataDefineDetail.getDetailType() == 3) {
												if (!this.isNumber(strings[index], 3)) {
													ImportDataCheckMessage message = new ImportDataCheckMessage(false, line, "检查数据格式-第" + line + "行【" + importDataDefineDetail.getDetailName() + "】列数据应该为整数");
													messages.add(message);
													line++;
													continue outFor;
												}
											}
											updateSql += importDataDefineDetail.getEntityName() + " = '" + strings[index] + "',";
										}
										continue;
									}
								}
								updateSql = updateSql.substring(0, updateSql.length() - 1) + whereSql;
								ImportDataCheckMessage message = new ImportDataCheckMessage(true, line, updateSql);
								messages.add(message);
								line++;
								continue;
							} else {
								line++;
								continue;
							}
						}

					}
				}
			}
			if(!checkRepeatList.isEmpty()) {
				for (int i = 0; i < checkRepeatList.size(); i++) {
					String checkString1 = checkRepeatList.get(i);
					for (int j = 0; j < checkRepeatList.size(); j++) {
						if(i != j){
							String checkString2 = checkRepeatList.get(j);
							if(checkString1.equals(checkString2)){
								String[] stringArr = checkString1.split(",");
								ImportDataCheckMessage message = new ImportDataCheckMessage(false,-1,"检查联合主键-文件中存在【人员编码】'"+stringArr[0]+"'【单位编码】'"+stringArr[1]+"'相同的重复数据");
								messages.add(message);
							}
						}
						
					}
				}
			}
			return messages;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private Set<ImportDataCheckMessage> readExcelImportFile(File file, ImportDataDefine importDataDefine, String tableName) {
		try {
			Set<ImportDataCheckMessage> messages = new HashSet<ImportDataCheckMessage>();
			Set<ImportDataDefineDetail> details = importDataDefine.getImportDataDefineDetails();
			List<ImportDataCheck> checks = initImportDataCheck();
			Map<String, List<String>> checkDatas = initCheckDatas();
			//检查是否定义好检查规则
			if (checks.isEmpty()) {
				ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "请定义检查规则！");
				messages.add(message);
				return messages;
			}

			Workbook book = WorkbookFactory.create(file);
			//只取第一个sheet页中的内容
			Sheet sheet = book.getSheetAt(0);
			//List<String> columnNameList = new ArrayList<String>();
			Map<String, Integer> columnNameMap = new HashMap<String, Integer>();
			Row firstRow = sheet.getRow(0);
			for (int i = 0;; i++) {
				try {
					Cell cell = firstRow.getCell(i);
					if (cell != null) {
						String excelCellValue = cell.getStringCellValue();
						if ((excelCellValue != null) && (excelCellValue.trim().equals(""))) {
							break;
						}
						//columnNameList.add(excelCellValue);
						columnNameMap.put(excelCellValue, i);
					} else {
						break;
					}
				} catch (Exception e) {
					break;
				}
			}
			if (columnNameMap.isEmpty()) {
				ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查数据完整性-文件中标题行数据不能为空！");
				messages.add(message);
				return messages;
			}

			for (Iterator iterator = details.iterator(); iterator.hasNext();) {
				ImportDataDefineDetail importDataDefineDetail = (ImportDataDefineDetail) iterator.next();
				String detailName = importDataDefineDetail.getDetailName();
				if (!columnNameMap.containsKey(detailName)) {
					ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查数据完整性-文件中缺少【" + detailName + "】列");
					messages.add(message);
				}
			}
			if (!messages.isEmpty()) {
				return messages;
			}
			List<String> checkRepeatList = new ArrayList<String>();
			outFor: for (int i = 1;; i++) {
				List<String> columnValueList = new ArrayList<String>();
				try {
					Row nextRow = sheet.getRow(i);
					if (nextRow == null) {
						if(!checkRepeatList.isEmpty()) {
							for (int k = 0; k < checkRepeatList.size(); k++) {
								String checkString1 = checkRepeatList.get(k);
								for (int j = 0; j < checkRepeatList.size(); j++) {
									if(k != j){
										String checkString2 = checkRepeatList.get(j);
										if(checkString1.equals(checkString2)){
											String[] stringArr = checkString1.split(",");
											ImportDataCheckMessage message = new ImportDataCheckMessage(false,-1,"检查联合主键-文件中存在【人员编码】'"+stringArr[0]+"'【单位编码】'"+stringArr[1]+"'相同的重复数据");
											messages.add(message);
										}
									}
									
								}
							}
						}
						return messages;
					} else {
						for (int j = 0;; j++) {
							try {
								Cell cell = nextRow.getCell(j);
								if (cell != null) {
									String excelCellValue = this.getCellTypeValue(cell);
									if ((excelCellValue != null) && (excelCellValue.trim().equals(""))) {
										break;
									}
									columnValueList.add(excelCellValue);
								} else {
									break;
								}
							} catch (Exception e) {
								break;
							}
						}
					}
					if (columnValueList.size() != columnNameMap.size()) {
						ImportDataCheckMessage message = new ImportDataCheckMessage(false, i + 1, "检查数据完整性-文件中第" + (i + 1) + "行的数据不完整");
						messages.add(message);
						continue;
					} else {
						//检查开始
						boolean flag = true;
						for (int k = 0; k < checks.size(); k++) {
							ImportDataCheck importDataCheck = (ImportDataCheck) checks.get(k);
							String checkContent = importDataCheck.getCheckContent();
							String[] stringArr = checkContent.split(":");
							if ("1".equals(importDataCheck.getCheckType())) {
								//TODO
								String unitCheck = UserContextUtil.getUserContext().getPeriodMonth();//(String) UserContextUtil.getUserContext().getSysVars().get(stringArr[1]);
								int index = columnNameMap.get(stringArr[0]);
								if (unitCheck != null) {
									if (!unitCheck.equals(columnValueList.get(index))) {
										ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查" + stringArr[0] + "-文件中【" + stringArr[0] + "】列数据应该为：" + unitCheck);
										messages.add(message);
										flag = false;
										break;
									}
								} else {
									ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查-出现未知错误，请联系管理员！");
									messages.add(message);
									return messages;
								}
								continue;
							} else if ("2".equals(importDataCheck.getCheckType())) {
								String[] checkContents = stringArr[0].split(",");
								String option = "";
								ImportDataCheckMessage message = null;
								if (checkContents.length == 1) {
									int index = columnNameMap.get(checkContents[0]);
									option = columnValueList.get(index);
								} else if (checkContents.length == 2) {
									int index1 = columnNameMap.get(checkContents[0]);
									int index2 = columnNameMap.get(checkContents[1]);
									option = columnValueList.get(index1) + "," + columnValueList.get(index2);
								}
								message = customCheck(option, stringArr[1], checkDatas);
								if (message != null) {
									messages.add(message);
									flag = false;
									break;
								}
							}
						}

						if (flag) {
							if(columnNameMap.containsKey("人员编码") && columnNameMap.containsKey("人员编码")) {
								String personCode = columnValueList.get(columnNameMap.get("人员编码"));
								String orgCode = columnValueList.get(columnNameMap.get("单位编码"));
								checkRepeatList.add(personCode + "," +orgCode);
							}
							String updateSql = "update " + tableName + " set ";
							String whereSql = " where 1=1 ";
							for (ImportDataDefineDetail importDataDefineDetail : details) {
								String detailName = importDataDefineDetail.getDetailName();
								int index = columnNameMap.get(detailName);
								if (importDataDefineDetail.getIsConstraint() == true) {
									whereSql += " and " + importDataDefineDetail.getEntityName() + " = '" + columnValueList.get(index) + "'";
									continue;
								} else {
									if (importDataDefineDetail.getIsUpdate() == true) {
										if (importDataDefineDetail.getDetailType() == 0) {
											if (!this.isNumber(columnValueList.get(index), 0)) {
												ImportDataCheckMessage message = new ImportDataCheckMessage(false, (i + 1), "检查数据格式-第" + (i + 1) + "行【" + importDataDefineDetail.getDetailName() + "】列数据应该为小数或整数");
												messages.add(message);
												continue outFor;
											}
										} else if (importDataDefineDetail.getDetailType() == 3) {
											if (!this.isNumber(columnValueList.get(index), 3)) {
												ImportDataCheckMessage message = new ImportDataCheckMessage(false, (i + 1), "检查数据格式-第" + (i + 1) + "行【" + importDataDefineDetail.getDetailName() + "】列数据应该为整数");
												messages.add(message);
												continue outFor;
											}
										}
										updateSql += importDataDefineDetail.getEntityName() + " = '" + columnValueList.get(index) + "',";
									}
									continue;
								}
							}
							updateSql = updateSql.substring(0, updateSql.length() - 1) + whereSql;
							ImportDataCheckMessage message = new ImportDataCheckMessage(true, (i + 1), updateSql);
							messages.add(message);
							continue;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					ImportDataCheckMessage message = new ImportDataCheckMessage(false, -1, "检查-出现未知错误，请联系管理员！");
					messages.add(message);
					return messages;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 通过判断Cell的Type，返回不同的返回值
	 * @param cell
	 * @param type
	 * @return
	 */
	private String getCellTypeValue(Cell cell) {
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			long longVal = Math.round(cell.getNumericCellValue());
			double doubleVal = cell.getNumericCellValue();
			String inputValue = "";
			if (Double.parseDouble(longVal + ".0") == doubleVal)
				inputValue += longVal;
			else
				inputValue += doubleVal;
			return inputValue;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else {
			return "";
		}
	}

	private boolean isNumber(String data, int type) {
		try {
			if (type == 0) {
				double flag = Double.parseDouble(data);
			} else if (type == 3) {
				int flag = Integer.parseInt(data);
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public int importData(String interfaceId, String subSystemCode, long systemTime) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String fileName = interfaceId + systemTime + ".txt";
			String filePath = "/home/temporary/";
			ServletContext context = ServletActionContext.getServletContext();
			String realPath = context.getRealPath(filePath) + "/" + fileName;
			fis = new FileInputStream(realPath);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String lineText = "";
			DataSource dataSource = SpringContextHelper.getDataSource();
			JdbcTemplate jtl = new JdbcTemplate(dataSource);
			while ((lineText = br.readLine()) != null) {
				jtl.execute(lineText);
			}
			InterLogger logger = new InterLogger();
			logger.setLogDateTime(new Date());
			logger.setLogMsg("导入成功！");
			logger.setPeriodCode(UserContextUtil.getUserContext().getPeriodMonth());
			logger.setLogFrom("导入");
			logger.setTaskInterId(interfaceId);
			logger.setSubSystemCode(subSystemCode);
			interLoggerDao.save(logger);
			return 1;
		} catch (FileNotFoundException e) {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
					isr.close();
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 2;

	}

	@Override
	public JQueryPager getImportDataLoggerCriteria(JQueryPager paginatedList, String interLogId) {
		JQueryPager jQueryPager = interLoggerDao.getInterLoggerCriteria(paginatedList, interLogId);
		List<InterLogger> interLoggers = jQueryPager.getList();
		List<InterLogger> list = new ArrayList<InterLogger>();
		if (!interLoggers.isEmpty() && interLoggers != null) {
			for (Iterator iterator = interLoggers.iterator(); iterator.hasNext();) {
				InterLogger interLogger = (InterLogger) iterator.next();
				String logMsg = interLogger.getLogMsg();
				if (logMsg != null && !"".equals(logMsg)) {
					String[] strArr = logMsg.split(";");
					for (int i = 0; i < strArr.length; i++) {
						InterLogger logger = new InterLogger();
						logger.setLogDateTime(interLogger.getLogDateTime());
						logger.setLogId(interLogger.getLogId() + i + new Date().getTime());
						logger.setPeriodCode(interLogger.getPeriodCode());
						logger.setTaskInterId(interLogger.getTaskInterId());
						String string = strArr[i];
						String[] stringArr = string.split("-");
						if (stringArr.length > 1) {
							logger.setLogFrom(stringArr[0]);
							logger.setLogMsg(stringArr[1]);
						} else {
							logger.setLogFrom(interLogger.getLogFrom());
							logger.setLogMsg(string);
						}
						list.add(logger);
					}
				}
			}
			jQueryPager.setList(list);
			jQueryPager.setTotalNumberOfRows(list.size());
		}
		return jQueryPager;
	}

	@Override
	public void deleteImportDataLog(String interfaceId) {
		Criteria criteria = this.interLoggerDao.getCriteria();
		List<InterLogger> interLoggers = criteria.add(Restrictions.eq("taskInterId", interfaceId)).list();
		if (!interLoggers.isEmpty()) {
			this.interLoggerDao.deleteByTaskInterId(interfaceId);
		}
	}
}