package com.huge.webapp.action;

import java.math.BigDecimal;
import java.util.List;

import com.huge.ihos.formula.model.FormulaField;
import com.huge.ihos.formula.service.FormulaFieldManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.ihos.system.reportManager.chart.service.MccKeyManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;

public class BaseMccKeyAction
    extends JqGridBaseAction {
    protected MccKeyManager mccKeyManager;

    protected PeriodManager periodManager;

    protected DepartmentManager departmentManager;

    protected FormulaFieldManager formulaFieldManager;

    /**
     * 数据准备
     */
    protected List mccKeys;

    protected List<Department> departments;

    protected List<FormulaField> deptKeys;

    protected List<FormulaField> orgKeys;

    protected List allKeys;

    protected MccKey mccKey;

    protected List fontType;

    /**
     * 返回页面处理数据
     */
    protected String[] chartStr;

    protected String[] biaoTiS;

    protected String[] biaoZhuS;

    protected Boolean[] warningS;

    protected String[] c3dS;

    protected BigDecimal[] maxValueS;

    protected String[] currentValueS;

    protected String[] chaXunS;

    protected Object[] bingDataList;

    /**
     * 菜单URL需配置参数
     */
    protected String ckey;//图形ckey

    protected String type;//图形类型

    protected String resultPage;

    protected String chaXun;

    protected String orgDisplay;//单位查询框是否显示
    
    protected String branchDisplay; //院区查询框是否显示
    /**
     * 图形查询条件
     */
    protected String deptId;//部门ID

    protected String deptKey;//指标

    protected String conditionO;//期间 1

    protected String conditionT;//期间 2

    protected String orgCode;//期间
    
    protected String branchCode;
    /**
     * 快速搜索
     */
    /*protected String quickDeptStr;//(科室)

    protected String quickKeyStr;//(指标)
*/
    /*protected boolean meaSure;*/

    protected String deptString = "";

    protected String deptKeyStr = "";

    /**
     * 仅为保存搜索框值
     */
    protected String deptName;

    protected String deptKeyName;

    protected String currentPeriod1;

    protected String alDeptName = "";

    protected String alDeptKey = "";

    /**
     * 本量利参数
     */
    protected String picName;

    protected String gdParam;

    protected String str;

    protected String xyName;

    protected String biaoZhu;

    protected String dataMsg;

    protected String biaoTi;

    protected String bllPath;

    /**
     * MCCKEY编辑保存时单独声明参数
     */
    protected boolean disShowValue;//是否显示数值

    protected String chartType;//图表类型

    protected String mccKeyId;

    protected String isValid() {
        if ( mccKey == null ) {
            return "Invalid mccKey Data";
        }
        return SUCCESS;
    }

    public List getFontType() {
        return fontType;
    }

    public void setFontType( List fontType ) {
        this.fontType = fontType;
    }

    public void setMccKeyManager( MccKeyManager mccKeyManager ) {
        this.mccKeyManager = mccKeyManager;
    }

    public List getMccKeys() {
        return mccKeys;
    }

    public void setMccKeyId( String mccKeyId ) {
        this.mccKeyId = mccKeyId;
    }

    public MccKey getMccKey() {
        return mccKey;
    }

    public void setMccKey( MccKey mccKey ) {
        this.mccKey = mccKey;
    }

    public String getChartType() {
        return chartType;
    }

    public String getCkey() {
        return ckey;
    }

    /*public boolean isMeaSure() {
        return meaSure;
    }

    public void setMeaSure( boolean meaSure ) {
        this.meaSure = meaSure;
    }*/

    public void setCkey( String ckey ) {
        this.ckey = ckey;
    }

    public String[] getC3dS() {
        return c3dS;
    }

    public void setC3dS( String[] c3dS ) {
        this.c3dS = c3dS;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String[] getChartStr() {
        return chartStr;
    }

    public String[] getBiaoTiS() {
        return biaoTiS;
    }

    public String[] getBiaoZhuS() {
        return biaoZhuS;
    }

    public BigDecimal[] getMaxValueS() {
        return maxValueS;
    }

    public String getConditionO() {
        return conditionO;
    }

    public String[] getCurrentValueS() {
        return currentValueS;
    }

    public void setConditionO( String conditionO ) {
        this.conditionO = conditionO;
    }

    public void setDepartmentManager( DepartmentManager departmentManager ) {
        this.departmentManager = departmentManager;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setFormulaFieldManager( FormulaFieldManager formulaFieldManager ) {
        this.formulaFieldManager = formulaFieldManager;
    }

    public List<FormulaField> getDeptKeys() {
        return deptKeys;
    }

    public List<FormulaField> getOrgKeys() {
        return orgKeys;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId( String detpId ) {
        this.deptId = detpId;
    }

    public String getDeptKey() {
        return deptKey;
    }

    public void setDeptKey( String deptKey ) {
        this.deptKey = deptKey;
    }

    public String getConditionT() {
        return conditionT;
    }

    public void setConditionT( String conditionT ) {
        this.conditionT = conditionT;
    }

   /* public String getQuickDeptStr() {
        return quickDeptStr;
    }

    public void setQuickDeptStr( String quickDeptStr ) {
        this.quickDeptStr = quickDeptStr;
    }*/

    public List getAllKeys() {
        return allKeys;
    }

    public String getDeptString() {
        return deptString;
    }

    public void setDeptString( String deptString ) {
        this.deptString = deptString;
    }

    /*public String getQuickKeyStr() {
        return quickKeyStr;
    }

    public void setQuickKeyStr( String quickKeyStr ) {
        this.quickKeyStr = quickKeyStr;
    }*/

    public String getDeptKeyStr() {
        return deptKeyStr;
    }

    public void setDeptKeyStr( String deptKeyStr ) {
        this.deptKeyStr = deptKeyStr;
    }

    public void setPicName( String picName ) {
        this.picName = picName;
    }

    public void setGdParam( String gdParam ) {
        this.gdParam = gdParam;
    }

    public String getStr() {
        return str;
    }

    public void setXyName( String xyName ) {
        this.xyName = xyName;
    }

    public void setBiaoZhu( String biaoZhu ) {
        this.biaoZhu = biaoZhu;
    }

    public void setDataMsg( String dataMsg ) {
        this.dataMsg = dataMsg;
    }

    public void setBiaoTi( String biaoTi ) {
        this.biaoTi = biaoTi;
    }

    public void setPeriodManager( PeriodManager periodManager ) {
        this.periodManager = periodManager;
    }

    public String getCurrentPeriod1() {
        return currentPeriod1;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName( String deptName ) {
        this.deptName = deptName;
    }

    public String getDeptKeyName() {
        return deptKeyName;
    }

    public void setDeptKeyName( String deptKeyName ) {
        this.deptKeyName = deptKeyName;
    }

    protected String random = "" + Math.round( Math.random() * 10000 );

    public String getRandom() {
        return random;
    }

    public void setRandom( String random ) {
        this.random = random;
    }

    public String getResultPage() {
        return resultPage;
    }

    public void setResultPage( String resultPage ) {
        this.resultPage = resultPage;
    }

    public String[] getChaXunS() {
        return chaXunS;
    }

    public void setChaXunS( String[] chaXunS ) {
        this.chaXunS = chaXunS;
    }

    public void setChaXun( String chaXun ) {
        String[] chaXunS = chaXun.split( "," );
        this.chaXunS = chaXunS;
    }

    public String getChaXun() {
        return chaXun;
    }

    public Boolean[] getWarningS() {
        return warningS;
    }

    public void setWarningS( Boolean[] warningS ) {
        this.warningS = warningS;
    }

    public Object[] getBingDataList() {
        return bingDataList;
    }

    public void setBingDataList( Object[] bingDataList ) {
        this.bingDataList = bingDataList;
    }

    public boolean isDisShowValue() {
        return disShowValue;
    }

    public void setDisShowValue( boolean disShowValue ) {
        this.disShowValue = disShowValue;
    }

    public String getBllPath() {
        return bllPath;
    }

    public void setBllPath( String bllPath ) {
        this.bllPath = bllPath;
    }

    public String getAlDeptName() {
        return alDeptName;
    }

    public void setAlDeptName( String alDeptName ) {
        this.alDeptName = alDeptName;
    }

    public String getAlDeptKey() {
        return alDeptKey;
    }

    public void setAlDeptKey( String alDeptKey ) {
        this.alDeptKey = alDeptKey;
    }

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgDisplay() {
		return orgDisplay;
	}

	public void setOrgDisplay(String orgDisplay) {
		this.orgDisplay = orgDisplay;
	}

	public String getBranchDisplay() {
		return branchDisplay;
	}

	public void setBranchDisplay(String branchDisplay) {
		this.branchDisplay = branchDisplay;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}