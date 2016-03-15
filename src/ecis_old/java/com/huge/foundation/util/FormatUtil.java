package com.huge.foundation.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;

public class FormatUtil
{
  private static DecimalFormat _$4 = new DecimalFormat("#,##0.00");
  private static DecimalFormat _$3 = new DecimalFormat("##0.##%");
  private static DecimalFormat _$2 = new DecimalFormat("#,##0.###");
  private static DecimalFormat _$1 = new DecimalFormat("#,##0.000000");

  public static String formatPrice(Double paramDouble)
  {
    if (paramDouble == null)
      return "";
    return formatPrice(paramDouble.doubleValue());
  }

  public static String formatPrice(double paramDouble)
  {
    return _$4.format(paramDouble);
  }

  public static String formatUnitPrice(Double paramDouble)
  {
    if (paramDouble == null)
      return "";
    return formatUnitPrice(paramDouble.doubleValue());
  }

  public static String formatUnitPrice(double paramDouble)
  {
    return _$1.format(paramDouble);
  }

  public static String formatPercentage(Double paramDouble)
  {
    if (paramDouble == null)
      return "";
    return formatPercentage(paramDouble.doubleValue());
  }

  public static String format(double paramDouble, String paramString)
  {
    return new DecimalFormat(paramString).format(paramDouble);
  }

  public static String format(BigDecimal paramBigDecimal, String paramString)
  {
    if (paramBigDecimal == null)
      return "";
    return new DecimalFormat(paramString).format(paramBigDecimal);
  }

  public static String formatPercentage(double paramDouble)
  {
    return _$3.format(paramDouble);
  }

  public static String formatQuantity(Long paramLong)
  {
    if (paramLong == null)
      return "";
    return formatQuantity(paramLong.doubleValue());
  }

  public static String formatQuantity(long paramLong)
  {
    return formatQuantity(paramLong);
  }

  public static String formatQuantity(Integer paramInteger)
  {
    if (paramInteger == null)
      return "";
    return formatQuantity(paramInteger.doubleValue());
  }

  public static String formatQuantity(int paramInt)
  {
    return formatQuantity(paramInt);
  }

  public static String formatQuantity(Float paramFloat)
  {
    if (paramFloat == null)
      return "";
    return formatQuantity(paramFloat.doubleValue());
  }

  public static String formatQuantity(float paramFloat)
  {
    return formatQuantity(paramFloat);
  }

  public static String formatQuantity(Double paramDouble)
  {
    if (paramDouble == null)
      return "";
    return formatQuantity(paramDouble.doubleValue());
  }

  public static String formatQuantity(double paramDouble)
  {
    return _$2.format(paramDouble);
  }

  public static String formatDate(Timestamp paramTimestamp)
  {
    if (paramTimestamp == null)
      return "";
    DateFormat localDateFormat = DateFormat.getDateTimeInstance(2, 2);
    Timestamp localTimestamp = paramTimestamp;
    return localDateFormat.format(localTimestamp);
  }

  public static String makeString(Object paramObject)
  {
    if (paramObject != null)
      return paramObject.toString();
    return "";
  }

  public static String checkNull(String paramString)
  {
    if (paramString != null)
      return paramString;
    return "";
  }

  public static String checkNull(String paramString1, String paramString2)
  {
    if (paramString1 != null)
      return paramString1;
    if (paramString2 != null)
      return paramString2;
    return "";
  }

  public static String checkNull(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1 != null)
      return paramString1;
    if (paramString2 != null)
      return paramString2;
    if (paramString3 != null)
      return paramString3;
    return "";
  }

  public static String checkNull(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (paramString1 != null)
      return paramString1;
    if (paramString2 != null)
      return paramString2;
    if (paramString3 != null)
      return paramString3;
    if (paramString4 != null)
      return paramString4;
    return "";
  }

  public static String ifNotEmpty(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramString1.length() > 0))
      return paramString2 + paramString1 + paramString3;
    return "";
  }

  public static String checkEmpty(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString1.length() > 0))
      return paramString1;
    if ((paramString2 != null) && (paramString2.length() > 0))
      return paramString2;
    return "";
  }

  public static String checkEmpty(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramString1.length() > 0))
      return paramString1;
    if ((paramString2 != null) && (paramString2.length() > 0))
      return paramString2;
    if ((paramString3 != null) && (paramString3.length() > 0))
      return paramString3;
    return "";
  }

  public static String encodeQuery(String paramString)
  {
    String str = replaceString(paramString, "%", "%25");
    str = replaceString(str, " ", "%20");
    str = replaceString(str, "&", "%26");
    str = replaceString(str, "?", "%3F");
    str = replaceString(str, "=", "%3D");
    str = replaceString(str, "+", "%2B");
    return str;
  }

  public static String encodeQueryValue(String paramString)
  {
    String str = replaceString(paramString, "%", "%25");
    str = replaceString(str, " ", "%20");
    str = replaceString(str, "&", "%26");
    str = replaceString(str, "?", "%3F");
    str = replaceString(str, "=", "%3D");
    str = replaceString(str, "+", "%2B");
    return str;
  }

  public static String replaceString(String paramString1, String paramString2, String paramString3)
  {
    return StringUtil.replaceString(paramString1, paramString2, paramString3);
  }

  public static String decodeQueryValue(String paramString)
  {
    String str = replaceString(paramString, "%25", "%");
    str = replaceString(str, "%20", " ");
    str = replaceString(str, "%26", "&");
    str = replaceString(str, "%3F", "?");
    str = replaceString(str, "%3D", "=");
    str = replaceString(str, "%2B", "+");
    return str;
  }

  public static String encodeXmlValue(String paramString)
  {
    String str = paramString;
    str = StringUtil.replaceString(str, "&", "&amp;");
    str = StringUtil.replaceString(str, "<", "&lt;");
    str = StringUtil.replaceString(str, ">", "&gt;");
    str = StringUtil.replaceString(str, "\"", "&quot;");
    str = StringUtil.replaceString(str, "'", "&apos;");
    return str;
  }

  public static String encode(String paramString)
  {
    String str = paramString;
    str = StringUtil.replaceString(str, "&", "&amp;");
    str = StringUtil.replaceString(str, "<", "&lt;");
    str = StringUtil.replaceString(str, ">", "&gt;");
    str = StringUtil.replaceString(str, "\"", "&quot;");
    str = StringUtil.replaceString(str, "'", "&#039;");
    str = StringUtil.replaceString(str, " ", "&nbsp;");
    return str;
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.foundation.util.FormatUtil
 * JD-Core Version:    0.6.0
 */