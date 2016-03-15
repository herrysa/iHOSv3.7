package com.huge.common.query;

import java.sql.SQLException;
import java.sql.Timestamp;

import oracle.sql.CLOB;
import oracle.sql.TIMESTAMP;

public class OracleUtil
{
  public static String getString(Object paramObject)
  {
    if (paramObject == null)
      return "";
    Object localObject;
    if ((paramObject instanceof CLOB))
    {
      localObject = (CLOB)paramObject;
      try
      {
        return ((CLOB)localObject).getSubString(1L, (int)((CLOB)localObject).getLength());
      }
      catch (SQLException localSQLException1)
      {
        localSQLException1.printStackTrace();
      }
    }
    else if ((paramObject instanceof TIMESTAMP))
    {
      localObject = (TIMESTAMP)paramObject;
      try
      {
        return ((TIMESTAMP)localObject).timestampValue().toString();
      }
      catch (SQLException localSQLException2)
      {
        localSQLException2.printStackTrace();
      }
    }
    else if ((paramObject instanceof Timestamp))
    {
      localObject = (Timestamp)paramObject;
      return ((Timestamp)localObject).toString();
    }
    return (String)String.valueOf(paramObject);
  }
}

/* Location:           D:\Java_Working\EclipseWorkSpaces\OldSystemWorkSpace\ecis_lib\foundation2.0.0\
 * Qualified Name:     com.huge.common.query.OracleUtil
 * JD-Core Version:    0.6.0
 */