package com.huge.waf.util;

public class Preference
{
  private static String mappingXmlDir = "\\";
  private static String webappType;
  private static String databaseType;
  private static String _$6;
  private static String _$5;
  private static String serialNo = null;
  private static String cpuid;
  private static String checkPeriod = null;
  private static String interCheckPeriod = null;

  public static String getSerialNo()
  {
    return serialNo;
  }

  public static void setSerialNo(String paramString)
  {
    serialNo = paramString;
  }

  public static String getMappingXmlDir()
  {
    return mappingXmlDir + "/WEB-INF/mapping";
  }

  public static void setWebContainerDir(String paramString)
  {
    mappingXmlDir = paramString;
  }

  public static boolean isOracle()
  {
    return "ORACLE".equalsIgnoreCase(databaseType);
  }

  public static boolean isSQLServer()
  {
    return "SQLServer".equalsIgnoreCase(databaseType);
  }

  public static boolean isDB2()
  {
    return "DB2".equalsIgnoreCase(databaseType);
  }

  public static String getDatabaseType()
  {
    return databaseType;
  }

  public static String getWebContainerDir()
  {
    return mappingXmlDir;
  }

  public static void setDatabaseType(String paramString)
  {
    databaseType = paramString;
  }

  public static String getWebappType()
  {
    return webappType;
  }

  public static void setWebappType(String paramString)
  {
    webappType = paramString;
  }

  public static String getCpuid()
  {
    return cpuid;
  }

  public static void setCpuid(String paramString)
  {
    cpuid = paramString;
  }

  public static String getCheckPeriod()
  {
    return checkPeriod;
  }

  public static void setCheckPeriod(String paramString)
  {
    checkPeriod = paramString;
  }

  public static String getInterCheckPeriod()
  {
    return interCheckPeriod;
  }

  public static void setInterCheckPeriod(String paramString)
  {
    interCheckPeriod = paramString;
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.waf.util.Preference
 * JD-Core Version:    0.6.0
 */