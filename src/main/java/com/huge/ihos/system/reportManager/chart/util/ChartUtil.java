package com.huge.ihos.system.reportManager.chart.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.huge.ihos.system.reportManager.chart.model.IntegrationData;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.util.OtherUtil;

public class ChartUtil {
	/**
	 * 拼接SQL
	 * 
	 * @param conditionMap
	 * @return
	 */
	public static String joinSql(Map<String, String> conditionMap) {

		String conditionO = null;
		String conditionT = null;
		String deptKey = null;
		String deptId = null;
		String orgCode = null;
		String branchCode = null;

		if (OtherUtil.measureNotNull(conditionMap.get("conditionO"))) {
			conditionO = conditionMap.get("conditionO");
		}
		if (OtherUtil.measureNotNull(conditionMap.get("conditionT"))) {
			conditionT = conditionMap.get("conditionT");
		}
		if (OtherUtil.measureNotNull(conditionMap.get("deptKey"))) {
			deptKey = conditionMap.get("deptKey");
		}
		if (OtherUtil.measureNotNull(conditionMap.get("deptId"))) {
			deptId = conditionMap.get("deptId");
		}
		if (OtherUtil.measureNotNull(conditionMap.get("orgCode"))) {
			orgCode = conditionMap.get("orgCode");
		}
		if(OtherUtil.measureNotNull(conditionMap.get("branchCode"))) {
			branchCode = conditionMap.get("branchCode");
		}
		String sql = "";

		/* 拼接期间 */
		if (OtherUtil.measureNotNull(conditionO)
				&& OtherUtil.measureNull(conditionT))
			sql += " checkperiod between '" + conditionO + "' and '"
					+ conditionO + "'";
		else if (OtherUtil.measureNotNull(conditionO)
				&& OtherUtil.measureNotNull(conditionT))
			sql += " checkperiod between '" + conditionO + "' and '"
					+ conditionT + "'";

		/* 拼接科室 */
		if (OtherUtil.measureNotNull(deptId)) {
			deptId = OtherUtil.splitStrAddQuotes(deptId, ",");
			sql += " and deptId in (" + deptId + ") ";
		}
		/* 拼接单位 */
		if (OtherUtil.measureNotNull(orgCode)) {
			orgCode = OtherUtil.splitStrAddQuotes(orgCode, ",");
			sql += " and orgCode in (" + orgCode + ") ";
		}
		/* 拼接院区 */
		if(OtherUtil.measureNotNull(branchCode)) {
			branchCode = OtherUtil.splitStrAddQuotes(branchCode, ",");
			sql += " and branchCode in (" + branchCode + ") ";
		}
		/* 拼接指标 */
		if (OtherUtil.measureNotNull(deptKey)) {
			deptKey = OtherUtil.splitStrAddQuotes(deptKey, ",");
			sql += " and KEYFIELDNAME in (" + deptKey + ") ";
		}
		return sql;
	}

	/**
	 * 将饼图数据整合为一个集合
	 * 
	 * @param listTitle
	 * @param listValue
	 * @param listPercent
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List IntegrationData(Object[] listTitle, Object[] listValue,
			Object[] listPercent) {
		List<IntegrationData> listIntegration = new ArrayList<IntegrationData>();
		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < listPercent.length; i++) {
			IntegrationData iData = new IntegrationData();
			iData.setDataName(OtherUtil.measureNull(listTitle[i]) ? ""
					: listTitle[i]);
			iData.setDataValue(OtherUtil.measureNull(listValue[i]) ? ""
					: listValue[i]);
			iData.setDataStringValue(OtherUtil.measureNull(listValue[i]) ? ""
					: OtherUtil.numberBigDecimal(listValue[i]));
			iData.setPercent(OtherUtil.measureNull(listPercent[i]) ? ""
					: OtherUtil.numberBigDecimal(listPercent[i]));
			listIntegration.add(iData);
			total = total.add((BigDecimal) listValue[i]);
		}

		IntegrationData iData = new IntegrationData();
		Collections.sort(listIntegration, iData);

		IntegrationData iDataTotal = new IntegrationData();
		iDataTotal.setDataName("合计：");
		iDataTotal.setDataStringValue(OtherUtil.numberBigDecimal(total));
		iDataTotal.setPercent(OtherUtil.numberBigDecimal(100));
		listIntegration.add(iDataTotal);
		return listIntegration;
	}

	/**
	 * 将查询数据整理为三个集合(饼图柱形图)
	 * 
	 * @param map
	 *            规则:
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object[] dataArrangement(Map<String, Object[]> map,
			MccKey mccKey) {
		Object[] myData = new Object[3];
		// 主显字段
		String keyField = mccKey.getKeyField();
		String[] keyFileds = keyField.split(",");
		Object[] listX = map.get(keyFileds[0]);
		Object[] listTitle = map.get(keyFileds[1]);
		Object[] listValue = map.get(keyFileds[2]);

		// 整理X轴数据
		List listXs = new ArrayList<Object>();

		for (Object object : listX) {
			if (!listXs.contains(object)) {
				listXs.add(object);
			}
		}

		// 整理X轴下方标题数据
		List listTs = new ArrayList<Object>();
		for (Object object : listTitle) {
			if (!listTs.contains(object)) {
				listTs.add(object);
			}
		}

		// 整理折线数据

		Map<Object, Map<Object, Object>> mapVs = new HashMap<Object, Map<Object, Object>>();
		/* 得到折线所有数据 */
		for (int i = 0; i < listTs.size(); i++) {
			Object object = listTs.get(i);

			/* 对应出X轴下方标题和X轴,折线值的对应关系 */
			Map<Object, Object> mapZVs = new HashMap<Object, Object>();
			for (int j = 0; j < listXs.size(); j++) {
				Object objectXs = listXs.get(j);

				/* 对应出X轴和折线值的对应关系 */
				for (int k = 0; k < listValue.length; k++) {
					if ((listTitle[k].toString() + listX[k].toString())
							.equals(object.toString() + objectXs.toString())) {
						mapZVs.put(objectXs, listValue[k]);
						break;
					}
				}
			}
			mapVs.put(object, mapZVs);
		}
		myData[0] = listXs;
		myData[1] = mapVs;
		myData[2] = listTs;
		return myData;
	}

	public static Object[] dataArrangement(List list) {
		TreeSet<Integer> checkperiod = new TreeSet<Integer>();
		LinkedHashSet<String> keyName = new LinkedHashSet<String>();
		HashMap<String, String> keyValue = new HashMap<String, String>();
		String deptName = "";

		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			checkperiod
					.add(Integer.parseInt(map.get("CHECKPERIOD").toString()));
			keyName.add(map.get("KEYNAME").toString());
			keyValue.put(map.get("KEYNAME").toString()
					+ map.get("CHECKPERIOD").toString(), map.get("KEYVALUE")
					.toString());
			deptName = map.get("DEPTNAME").toString();
		}
		Object[] myData = new Object[4];
		myData[0] = checkperiod;
		myData[1] = keyValue;
		myData[2] = keyName;
		myData[3] = deptName;
		return myData;
	}

	protected class DeptName implements Comparable<DeptName> {

		private Integer id;
		private String deptName;

		public DeptName(Integer id, String deptName) {
			this.id = id;
			this.deptName = deptName;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

		@Override
		public int compareTo(DeptName arg0) {
			if (arg0.id == null) {
				return 0;
			}
			if (id <= arg0.id)
				return 0;
			else if (id > arg0.id)
				return 1;
			else
				return -1;
		}

	}

	public static int zBool(boolean b) {
		return b ? 1 : 0;
	}

	/**
	 * 拼接字符串（表盘）
	 * 
	 * @param mk
	 * @return
	 */
	public static String[] biaoPanStr(MccKey mk) {
		String[] ms = new String[2];
		String ms1 = "";
		if (OtherUtil.measureNotNull(mk.getGaugeOuterRadius()))
			ms1 += " gaugeOuterRadius='" + mk.getGaugeOuterRadius() + "'";
		if (OtherUtil.measureNotNull(mk.getGaugeOriginX()))
			ms1 += " gaugeOriginX='" + mk.getGaugeOriginX() + "'";
		if (OtherUtil.measureNotNull(mk.getGaugeOriginY()))
			ms1 += " gaugeOriginY='" + mk.getGaugeOriginY() + "'";
		if (OtherUtil.measureNotNull(mk.getPivotRadius()))
			ms1 += " pivotRadius='" + mk.getPivotRadius() + "'";
		if (OtherUtil.measureNotNull(mk.getDisplayValueDistance()))
			ms1 += " displayValueDistance='" + mk.getDisplayValueDistance()
					+ "'";
		if (OtherUtil.measureNotNull(mk.getNumberPrefix()))
			ms1 += " numberPrefix='" + mk.getNumberPrefix() + "'";
		if (OtherUtil.measureNotNull(mk.getNumberSuffix()))
			ms1 += " numberSuffix='" + mk.getNumberSuffix() + "'";
		ms1 += " formatNumberScale='" + zBool(mk.getFormatNumberScale()) + "'";
		String ms2 = "";
		if (OtherUtil.measureNotNull(mk.getRadius()))
			ms1 += " radius='" + mk.getRadius() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseWidth()))
			ms1 += " baseWidth='" + mk.getBaseWidth() + "'";

		ms[0] = ms1;
		ms[1] = ms2;
		return ms;
	}

	/**
	 * 计算得出其他(饼图专用)
	 * 
	 * @param listValue
	 * @return
	 */
	public static Double calcOther(Object[] listValue, double kanZhi) {
		double v = 0;
		for (int i = 0; i < listValue.length; i++) {
			if (OtherUtil.measureNotNull(listValue[i]))
				v += Double.parseDouble(listValue[i].toString());
		}
		double vs = v / 100;
		double calcOther = vs * kanZhi;
		return calcOther;
	}

	/**
	 * 拼接字符串（折线柱形）
	 * 
	 * @param mk
	 * @return
	 */
	public static String mosaicStr(MccKey mk) {
		String ms = "";
		if (OtherUtil.measureNotNull(mk.getCaption()))
			ms += " caption='" + mk.getCaption() + "'";
		if (OtherUtil.measureNotNull(mk.getSubCaption()))
			ms += " subCaption='" + mk.getSubCaption() + "'";
		ms += " showValues='" + zBool(mk.getShowValues()) + "'";
		if (OtherUtil.measureNotNull(mk.getNumberPrefix()))
			ms += " numberPrefix='" + mk.getNumberPrefix() + "'";
		if (OtherUtil.measureNotNull(mk.getNumberSuffix()))
			ms += " numberSuffix='" + mk.getNumberSuffix() + "'";

		ms += " formatNumber='" + zBool(mk.getFormatNumber()) + "'";
		ms += " rotateNames='" + zBool(mk.getRotateNames()) + "'";
		ms += " formatNumberScale='" + zBool(mk.getFormatNumberScale()) + "'";

		if (OtherUtil.measureNotNull(mk.getThousandSeparator()))
			ms += " thousandSeparator='" + mk.getThousandSeparator() + "'";
		if (OtherUtil.measureNotNull(mk.getChartLeftMargin()))
			ms += " chartLeftMargin='" + mk.getChartLeftMargin() + "'";
		if (OtherUtil.measureNotNull(mk.getChartRightMargin()))
			ms += " chartRightMargin='" + mk.getChartRightMargin() + "'";
		if (OtherUtil.measureNotNull(mk.getxAxisName()))
			ms += " xAxisName='" + mk.getxAxisName() + "'";
		if (OtherUtil.measureNotNull(mk.getYAxisName()))
			ms += " yAxisName='" + mk.getYAxisName() + "' rotateYAxisName='"
					+ zBool(mk.getRotateYAxisName()) + "'";
		if (OtherUtil.measureNotNull(mk.getLabelStep()))
			ms += " labelStep='" + mk.getLabelStep() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFont()))
			ms += " baseFont='" + mk.getBaseFont() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFontSize()))
			ms += " baseFontSize='" + mk.getBaseFontSize() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFontColor()))
			ms += " baseFontColor='" + mk.getBaseFontColor() + "'";
		if (OtherUtil.measureNotNull(mk.getDecimalPrecision()))
			ms += " decimalPrecision='" + mk.getDecimalPrecision() + "'";
		if (OtherUtil.measureNotNull(mk.getPieRadius()))
			ms += " pieRadius='" + mk.getPieRadius() + "'";
		if (OtherUtil.measureNotNull(mk.getBgColor()))
			ms += " bgColor='" + mk.getBgColor() + "'";

		if (OtherUtil.measureNotNull(mk.getZxZhiBaseFontSize()))
			ms += " zxZhiBaseFontSize='" + mk.getZxZhiBaseFontSize() + "'";
		if (OtherUtil.measureNotNull(mk.getBgAlpha()))
			ms += " bgAlpha='" + mk.getBgAlpha() + "'";
		if (OtherUtil.measureNotNull(mk.getOutCnvbaseFontSize()))
			ms += " outCnvbaseFontSize='" + mk.getOutCnvbaseFontSize() + "'";
		if (OtherUtil.measureNotNull(mk.getLineThickness()))
			ms += " lineThickness='" + mk.getLineThickness() + "'";
		if (OtherUtil.measureNotNull(mk.getAnchorRadius()))
			ms += " anchorRadius='" + mk.getAnchorRadius() + "'";
		if (OtherUtil.measureNotNull(mk.getDivLineAlpha()))
			ms += " divLineAlpha='" + mk.getDivLineAlpha() + "'";
		if (OtherUtil.measureNotNull(mk.getDivLineColor())
				&& !mk.getChartType().equals("柱形图"))
			ms += " divLineColor='" + mk.getDivLineColor() + "'";
		if (OtherUtil.measureNotNull(mk.getNumvdivlines()))
			ms += " numvdivlines='" + mk.getNumvdivlines() + "'";

		ms += " divLineIsDashed='" + zBool(mk.getDivLineIsDashed()) + "'";
		ms += " showAlternateHGridColor='"
				+ zBool(mk.getShowAlternateHGridColor()) + "'";

		if (OtherUtil.measureNotNull(mk.getBgColor())
				&& OtherUtil.measureNotNull(mk.getBgColor2()))
			ms += " bgColor='" + mk.getBgColor() + "," + mk.getBgColor2() + "'";
		if (OtherUtil.measureNotNull(mk.getYAxisNameWidth()))
			ms += " yAxisNameWidth='" + mk.getYAxisNameWidth() + "'";
		return ms;
	}

	/**
	 * 拼接字符串（雷达图）
	 * 
	 * @param mk
	 * @return
	 */
	public static String masaicRadarStr(MccKey mk) {
		String ms = "";
		if (OtherUtil.measureNotNull(mk.getCaption()))
			ms += " caption='" + mk.getCaption() + "'";
		if (OtherUtil.measureNotNull(mk.getSubCaption()))
			ms += " subCaption='" + mk.getSubCaption() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFontSize()))
			ms += " baseFontSize='" + mk.getBaseFontSize() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFont()))
			ms += " baseFont='" + mk.getBaseFont() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFontColor()))
			ms += " baseFontColor='" + mk.getBaseFontColor() + "'";
		if (OtherUtil.measureNotNull(mk.getThousandSeparator()))
			ms += " thousandSeparator='" + mk.getThousandSeparator() + "'";
		if (OtherUtil.measureNotNull(mk.getBgColor()))
			ms += " bgColor='" + mk.getBgColor() + "'";
		if (OtherUtil.measureNotNull(mk.getPlotFillAlpha()))
			ms += " plotFillAlpha='" + mk.getPlotFillAlpha() + "'";
		if (OtherUtil.measureNotNull(mk.getNumberPrefix()))
			ms += " numberPrefix='" + mk.getNumberPrefix() + "'";
		if (OtherUtil.measureNotNull(mk.getNumDivLines()))
			ms += " numDivLines='" + mk.getNumDivLines() + "'";
		if (OtherUtil.measureNotNull(mk.getLegendPosition()))
			ms += " legendPosition='" + mk.getLegendPosition() + "'";
		return ms;
	}

	/**
	 * 拼接字符串（饼形）
	 * 
	 * @param mk
	 * @return
	 */
	public static String mosaicBingStr(MccKey mk) {
		String ms = "";
		if (OtherUtil.measureNotNull(mk.getCaption()))
			ms += " caption='" + mk.getCaption() + "'";
		if (OtherUtil.measureNotNull(mk.getSubCaption()))
			ms += " subCaption='" + mk.getSubCaption() + "'";

		if (mk.isDisShowValue() == true) {
			ms += " showValues='1'";
		} else {
			ms += " showValues='" + zBool(mk.getShowValues()) + "'";
		}
		if ("饼图".equals(mk.getChartType())) {
			ms += " formatNumber='1'";
		} else {
			ms += " formatNumber='" + zBool(mk.getFormatNumber()) + "'";
		}
		ms += " formatNumberScale='" + zBool(mk.getFormatNumberScale()) + "'";

		if (OtherUtil.measureNotNull(mk.getNumberPrefix()))
			ms += " numberPrefix='" + mk.getNumberPrefix() + "'";
		if (OtherUtil.measureNotNull(mk.getNumberSuffix()))
			ms += " numberSuffix='" + mk.getNumberSuffix() + "'";
		if (OtherUtil.measureNotNull(mk.getThousandSeparator()))
			ms += " thousandSeparator='" + mk.getThousandSeparator() + "'";
		if (OtherUtil.measureNotNull(mk.getLabelStep()))
			ms += " labelStep='" + mk.getLabelStep() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFont()))
			ms += " baseFont='" + mk.getBaseFont() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFontSize()))
			ms += " baseFontSize='" + mk.getBaseFontSize() + "'";
		if (OtherUtil.measureNotNull(mk.getBaseFontColor()))
			ms += " baseFontColor='" + mk.getBaseFontColor() + "'";
		if (OtherUtil.measureNotNull(mk.getDecimalPrecision()))
			ms += " decimalPrecision='" + mk.getDecimalPrecision() + "'";
		if (OtherUtil.measureNotNull(mk.getBgColor()))
			ms += " bgColor='" + mk.getBgColor() + "'";

		ms += " showPercentValues='" + zBool(mk.getShowPercentValues()) + "'";

		if (OtherUtil.measureNotNull(mk.getBgAlpha()))
			ms += " bgAlpha='" + mk.getBgAlpha() + "'";

		// ms += " decimalPrecision='2'";
		return ms;
	}

	public static boolean vBiaoP(MccKey mk) {
		return mk.getChartType().equals("表盘");
	}
}
