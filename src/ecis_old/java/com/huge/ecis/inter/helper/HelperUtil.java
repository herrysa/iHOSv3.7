package com.huge.ecis.inter.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.huge.foundation.util.StringUtil;

public class HelperUtil
{
	/**
	 * @TODO 加入并确认常量的保存与初始化，加入dept的id与name
	 * @param paramString
	 * @param paroTable
	 * @return
	 */

  public static String realSQL(String paramString, Hashtable paroTable)
  {
    String str = paramString;
    if (str.indexOf("%HSQJ%") >= 0)
      str = StringUtil.replaceString(str, "%HSQJ%",(String)paroTable.get("HSQJ"));
/*    if (str.indexOf("%TODAY%") >= 0)
      str = StringUtil.replaceString(str, "%TODAY%", getDateFromat(0, "yyyy-MM-dd"));
    if (str.indexOf("%PRE_DAY%") >= 0)
      str = StringUtil.replaceString(str, "%PRE_DAY%", getDateFromat(-1, "yyyy-MM-dd"));
    if (str.indexOf("%NEXT_DAY%") >= 0)
      str = StringUtil.replaceString(str, "%NEXT_DAY%", getDateFromat(1, "yyyy-MM-dd"));
    if (str.indexOf("%USER_CODE%") >= 0)
      str = StringUtil.replaceString(str, "%USER_CODE%", paramUserProp.getCode());*/
    if (str.indexOf("%USER_NAME%") >= 0)
      str = StringUtil.replaceString(str, "%USER_NAME%", (String)paroTable.get("USER_NAME"));
    if (str.indexOf("%USER_ID%") >= 0)
      str = StringUtil.replaceString(str, "%USER_ID%", (String)paroTable.get("USER_ID"));
/*    if (str.indexOf("%DEPT_CODE%") >= 0)
      str = StringUtil.replaceString(str, "%DEPT_CODE%", paramUserProp.getDept().getDeptCode());
    if (str.indexOf("%DEPT_ID%") >= 0)
      str = StringUtil.replaceString(str, "%DEPT_ID%", paramUserProp.getDept().getId());
    if (str.indexOf("%DEPT_NAME%") >= 0)
      str = StringUtil.replaceString(str, "%DEPT_NAME%", paramUserProp.getDept().getName());
    if (str.indexOf("%ORG_CODE%") >= 0)
      str = StringUtil.replaceString(str, "%ORG_CODE%", paramUserProp.getOrg().getOrgCode());
    if (str.indexOf("%ORG_NAME%") >= 0)
      str = StringUtil.replaceString(str, "%ORG_NAME%", paramUserProp.getOrg().getName());
    if (str.indexOf("%ORG_ID%") >= 0)
      str = StringUtil.replaceString(str, "%ORG_ID%", paramUserProp.getOrg().getId());*/
   /* if (str.indexOf("%NONO_OLD%") >= 0)
      str = StringUtil.replaceString(str, "%NONO_OLD%", (String)paroTable.get("NONO_OLD"));
    if (str.indexOf("%NONO_NEW%") >= 0)
      str = StringUtil.replaceString(str, "%NONO_NEW%", (String)paroTable.get("NONO_NEW"));
*/
    return str;
  }
  public static String getDateFromat(int paramInt, String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    return localSimpleDateFormat.format(new Date(System.currentTimeMillis() + paramInt * 24 * 60 * 60 * 1000));
  }
  public static String noRecSQL(String paramString)
  {
    return paramString;
  }
}

/* Location:           D:\EclipseWorkspaces\OldWorkspace\ecis2.5_20110809\WebRoot\WEB-INF\lib\inter-model.jar
 * Qualified Name:     com.huge.ecis.inter.helper.HelperUtil
 * JD-Core Version:    0.6.0
 */