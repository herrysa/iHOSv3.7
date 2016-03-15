package com.huge.ecis.inter.helper;

import java.util.ArrayList;
import java.util.List;

public class XMLData
{
  private String _$3;
  private List _$2 = new ArrayList();
  private List _$1 = new ArrayList();

  public List getFields()
  {
    return this._$1;
  }

  public void setFields(List paramList)
  {
    this._$1 = paramList;
  }

  public List getList()
  {
    return this._$2;
  }

  public void setList(List paramList)
  {
    this._$2 = paramList;
  }

  public String getTableName()
  {
    return this._$3;
  }

  public void setTableName(String paramString)
  {
    this._$3 = paramString;
  }

  public XMLData(String paramString, List paramList1, List paramList2)
  {
    this._$3 = paramString;
    this._$2 = paramList1;
    this._$1 = paramList2;
  }
}

/* Location:           D:\EclipseWorkspaces\OldWorkspace\ecis2.5_20110809\WebRoot\WEB-INF\lib\inter-model.jar
 * Qualified Name:     com.huge.ecis.inter.helper.XMLData
 * JD-Core Version:    0.6.0
 */