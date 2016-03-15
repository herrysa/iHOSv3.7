package com.huge.common.query;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.huge.foundation.util.FormatUtil;
import com.huge.waf.util.Preference;

public class ResultMap extends HashMap
{
  private List _$1 = new ArrayList();

  public ResultMap()
  {
  }

  public ResultMap(int paramInt)
  {
    super(paramInt);
  }

  public String getFieldName(int paramInt)
  {
    Object localObject = this._$1.get(paramInt);
    if ((localObject instanceof String))
      return String.valueOf(localObject).toUpperCase();
    return String.valueOf(localObject);
  }

  public Object put(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 instanceof String))
      paramObject1 = String.valueOf(paramObject1).toUpperCase();
    this._$1.add(paramObject1);
    return super.put(paramObject1, paramObject2);
  }

  public Object[] getKeyFields()
  {
    return this._$1.toArray(new Object[size()]);
  }

  public Object get(Object paramObject)
  {
    if ((paramObject instanceof String))
    {
      String str1 = String.valueOf(paramObject);
      String str2;
      if (Preference.isSQLServer())
      {
        if (str1.toUpperCase().startsWith("A."))
          str2 = str1.substring(str1.indexOf(".") + 1);
        else if (str1.indexOf(".") == 1)
          str2 = str1.substring(str1.indexOf(".") + 1);
        else if (str1.indexOf(".") > 0)
          str2 = str1.substring(0, str1.indexOf("."));
        else
          str2 = str1;
      }
      else if (str1.indexOf(".") > 0)
        str2 = str1.substring(0, str1.indexOf("."));
      else
        str2 = str1;
      return super.get(str2.toUpperCase());
    }
    return super.get(paramObject);
  }

  public Object get(int paramInt)
  {
    return get(getFieldName(paramInt));
  }

  public boolean isNull(int paramInt)
  {
    return isNull(getFieldName(paramInt));
  }

  public boolean isNull(String paramString)
  {
    return get(paramString) == null;
  }

  public Object getObject(int paramInt)
  {
    return get(get(paramInt));
  }

  public String getString(int paramInt)
  {
    Object localObject1 = get(paramInt);
    Object localObject2;
    if ((localObject1 instanceof BigDecimal))
    {
      localObject2 = (BigDecimal)localObject1;
      return ((BigDecimal)localObject2).toString();
    }
    if ((localObject1 instanceof Boolean))
    {
      localObject2 = (Boolean)localObject1;
      if (((Boolean)localObject2).equals(Boolean.TRUE))
        return "1";
      return "0";
    }
    if ((localObject1 instanceof Integer))
      return "" + getInt(paramInt);
    return (String)OracleUtil.getString(localObject1);
  }

  public int getInt(int paramInt)
  {
    return getInt(getFieldName(paramInt));
  }

  public long getLong(int paramInt)
  {
    return getLong(getFieldName(paramInt));
  }

  public float getFloat(int paramInt)
  {
    return getFloat(getFieldName(paramInt));
  }

  public double getDouble(int paramInt)
  {
    return getDouble(getFieldName(paramInt));
  }

  public boolean getBoolean(int paramInt)
  {
    return getBoolean(getFieldName(paramInt));
  }

  public BigDecimal getBigDecimal(int paramInt)
  {
    return getBigDecimal(getFieldName(paramInt));
  }

  public java.sql.Date getDate(int paramInt)
  {
    return getDate(getFieldName(paramInt));
  }

  public Time getTime(int paramInt)
  {
    return getTime(getFieldName(paramInt));
  }

  public Timestamp getTimestamp(int paramInt)
  {
    return getTimestamp(getFieldName(paramInt));
  }

  public short getShort(int paramInt)
  {
    return getShort(getFieldName(paramInt));
  }

  public Object getObject(String paramString)
  {
    return get(paramString.toUpperCase());
  }

  public String getString(String paramString)
  {
    Object localObject1 = getObject(paramString);
    Object localObject2;
    if ((localObject1 instanceof BigDecimal))
    {
      localObject2 = (BigDecimal)localObject1;
      return ((BigDecimal)localObject2).toString();
    }
    if ((localObject1 instanceof Boolean))
    {
      localObject2 = (Boolean)localObject1;
      if (((Boolean)localObject2).equals(Boolean.TRUE))
        return "1";
      return "0";
    }
    if ((localObject1 instanceof Integer))
      return "" + getInt(paramString);
    return (String)OracleUtil.getString(localObject1);
  }

  private String _$1(String paramString)
  {
    if (get(paramString) == null)
      return null;
    String str = String.valueOf(getObject(paramString));
    if (str.equals(""))
      return null;
    return str;
  }

  public int getInt(String paramString)
  {
    if (isNull(paramString))
      return 0;
    return Integer.valueOf(_$1(paramString)).intValue();
  }

  public long getLong(String paramString)
  {
    if (isNull(paramString))
      return 0L;
    return Long.valueOf(_$1(paramString)).longValue();
  }

  public float getFloat(String paramString)
  {
    if (isNull(paramString))
      return 0.0F;
    String str = _$1(paramString);
    if (str == null)
      return 0.0F;
    return Float.valueOf(_$1(paramString)).floatValue();
  }

  public double getDouble(String paramString)
  {
    if (isNull(paramString))
      return 0.0D;
    String str = _$1(paramString);
    if (str == null)
      return 0.0D;
    return Double.valueOf(str).doubleValue();
  }

  public boolean getBoolean(String paramString)
  {
    if (isNull(paramString))
      return false;
    if (getString(paramString).equals("1"))
      return true;
    return getString(paramString).equals("是");
  }

  public String getBooleanFMT(String paramString)
  {
    boolean bool = getBoolean(paramString);
    if (bool)
      return "√";
    return "";
  }

  public BigDecimal getBigDecimal(String paramString)
  {
    if (get(paramString) == null)
      return null;
    if ("".equals(getString(paramString)))
      return null;
    return new BigDecimal(getString(paramString));
  }

  public java.sql.Date getDate(String paramString)
  {
    if (get(paramString) == null)
      return null;
    String str = getString(paramString);
    if ("".equals(str))
      return null;
    if (str.length() > 10)
      str = str.substring(0, 10);
    return java.sql.Date.valueOf(str);
  }

  public String getDateStr(String paramString)
  {
    String str = getString(paramString);
    if (str == null)
      return "";
    if (str.length() > 10)
      str = str.substring(0, 10);
    return str;
  }

  public String getDateStr(int paramInt)
  {
    return getDateStr(getFieldName(paramInt));
  }

  public Time getTime(String paramString)
  {
    if (get(paramString) == null)
      return null;
    return Time.valueOf(getString(paramString));
  }

  public Timestamp getTimestamp(String paramString)
  {
    if (get(paramString) == null)
      return null;
    return Timestamp.valueOf(getString(paramString));
  }

  public String getTimestampFMT(int paramInt)
  {
    return getTimestampFMT(getFieldName(paramInt));
  }

  public String getTimestampFMT(String paramString)
  {
    if (get(paramString) == null)
      return "";
    Timestamp localTimestamp = Timestamp.valueOf(getString(paramString));
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return localSimpleDateFormat.format(localTimestamp);
  }

  public String getMoney(String paramString)
  {
    if (isNull(paramString))
      return "0.00";
    String str = _$1(paramString);
    if (str == null)
      return "0.00";
    double d = Double.valueOf(_$1(paramString)).doubleValue();
    return FormatUtil.formatPrice(d);
  }

  public String getMoney(int paramInt)
  {
    return getMoney(getFieldName(paramInt));
  }

  public short getShort(String paramString)
  {
    if (isNull(paramString))
      return 0;
    return Short.valueOf(getString(paramString)).shortValue();
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this._$1.size(); i++)
      localStringBuffer.append(this._$1.get(i) + "=[" + get(this._$1.get(i)) + "]");
    return localStringBuffer.toString();
  }

  public String[] toArray()
  {
    int i = size();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++)
      arrayOfString[j] = getString(j);
    return arrayOfString;
  }

  public static void main(String[] paramArrayOfString)
  {
    java.util.Date localDate = new java.util.Date();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    System.out.println(localSimpleDateFormat.format(localDate));
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.common.query.ResultMap
 * JD-Core Version:    0.6.0
 */