package com.huge.foundation.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public final class StringUtil
{
  private static String[] _$1 = { "", "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000", "000000000", "0000000000", "00000000000", "000000000000", "0000000000000", "00000000000000", "000000000000000", "0000000000000000", "00000000000000000", "000000000000000000", "0000000000000000000", "00000000000000000000", "000000000000000000000", "0000000000000000000000", "00000000000000000000000", "000000000000000000000000", "0000000000000000000000000", "00000000000000000000000000", "000000000000000000000000000", "0000000000000000000000000000" };

  public static final String toPaddedString(long paramLong, int paramInt)
  {
    if ((paramInt > 19) || (paramInt < 1))
      paramInt = 19;
    String str = Long.toString(paramLong);
    return _$1[(paramInt - str.length())] + str;
  }

  public static final String toPaddedString(int paramInt1, int paramInt2)
  {
    if ((paramInt2 > 10) || (paramInt2 < 1))
      paramInt2 = 10;
    String str = Integer.toString(paramInt1);
    return _$1[(paramInt2 - str.length())] + str;
  }

  public static final String replaceString(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1 == null)
      return null;
    if ((paramString2 == null) || (paramString2.length() == 0))
      return paramString1;
    if (paramString3 == null)
      paramString3 = "";
    int i = paramString1.lastIndexOf(paramString2);
    if (i < 0)
      return paramString1;
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    while (i >= 0)
    {
      localStringBuffer.replace(i, i + paramString2.length(), paramString3);
      i = paramString1.lastIndexOf(paramString2, i - 1);
    }
    return localStringBuffer.toString();
  }

  public static final String removeSpaces(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString.length());
    for (int i = 0; i < paramString.length(); i++)
    {
      if (paramString.charAt(i) == ' ')
        continue;
      localStringBuffer.append(paramString.charAt(i));
    }
    return localStringBuffer.toString();
  }

  public static final List split(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = null;
    if (paramString1 == null)
      return localArrayList;
    if (paramString2 != null)
      localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    else
      localStringTokenizer = new StringTokenizer(paramString1);
    if ((localStringTokenizer != null) && (localStringTokenizer.hasMoreTokens()))
      while (localStringTokenizer.hasMoreTokens())
        localArrayList.add(localStringTokenizer.nextToken());
    return localArrayList;
  }

  public static final String[] strToArray(String paramString1, String paramString2)
  {
    List localList = split(paramString1, paramString2);
    if (localList == null)
      return null;
    return (String[])(String[])localList.toArray(new String[localList.size()]);
  }

  public static final Map strToMap(String paramString)
  {
    if (paramString == null)
      return null;
    HashMap localHashMap = new HashMap();
    List localList1 = split(paramString, "|");
    Iterator localIterator = localList1.iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      List localList2 = split(str1, "=");
      if (localList2.size() == 2)
      {
        String str2 = (String)localList2.get(0);
        String str3 = (String)localList2.get(1);
        localHashMap.put(URLDecoder.decode(str2), URLDecoder.decode(str3));
      }
    }
    return localHashMap;
  }

  public static final String mapToStr(Map paramMap)
  {
    if (paramMap == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    Set localSet = paramMap.keySet();
    Iterator localIterator = localSet.iterator();
    int i = 1;
    while (localIterator.hasNext())
    {
      Object localObject1 = localIterator.next();
      Object localObject2 = paramMap.get(localObject1);
      if (((localObject1 instanceof String)) && ((localObject2 instanceof String)))
      {
        String str1 = URLEncoder.encode((String)localObject1);
        String str2 = URLEncoder.encode((String)localObject2);
        if (i != 0)
          i = 0;
        else
          localStringBuffer.append("|");
        localStringBuffer.append(str1);
        localStringBuffer.append("=");
        localStringBuffer.append(str2);
      }
    }
    return localStringBuffer.toString();
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.foundation.util.StringUtil
 * JD-Core Version:    0.6.0
 */