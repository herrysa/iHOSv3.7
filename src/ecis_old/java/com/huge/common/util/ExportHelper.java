package com.huge.common.util;

import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.util.OtherUtil;

public class ExportHelper
{
  public static ColumnDef[] getColumnDefs(JdbcTemplate jdbcTemplate, String paramString)
  {
   // PreparedStatement localPreparedStatement = null;
    SqlRowSet localResultSet = null;
    ColumnDef[] arrayOfColumnDef1 = (ColumnDef[])null;
 
//      localPreparedStatement = paramConnection.prepareStatement(paramString);
//      localResultSet = localPreparedStatement.executeQuery();
      localResultSet= jdbcTemplate.queryForRowSet(paramString);
      
      SqlRowSetMetaData localResultSetMetaData = localResultSet.getMetaData();
      arrayOfColumnDef1 = new ColumnDef[localResultSetMetaData.getColumnCount()];
      for (int i = 0; i < localResultSetMetaData.getColumnCount(); i++)
        arrayOfColumnDef1[i] = new ColumnDef(localResultSetMetaData.getColumnName(i + 1), localResultSetMetaData.getColumnType(i + 1),null,null);
      ColumnDef[] arrayOfColumnDef2 = arrayOfColumnDef1;
      ColumnDef[] arrayOfColumnDef3 = arrayOfColumnDef2;
      return arrayOfColumnDef3;

  }
  public static ColumnDef[] getColumnDefs(SearchOption[] ops){
	  ColumnDef[] cds=new ColumnDef[getNoOutputLength(ops)];
	  int u=0;
	for (int i = 0; i < ops.length; i++) {
		if(!ops[i].isPrintable())
			  continue;
		ColumnDef cd = new ColumnDef();
		cd.setFieldName(ops[i].getFieldName().toUpperCase());
		cd.setType(paramType(ops[i]));
		cds[u]=cd;
		u++;
	}
	  return cds;
  }
  /**
   * 二级标题输出
   * @param ops
   * @return
   */
  public static ColumnDef[] getColumnDefChieseName(SearchOption[] ops){
	  
	  ColumnDef[] cds=new ColumnDef[getNoOutputLength(ops)];
	  ArrayList<String> titleZiList=new ArrayList<String>();
	  
	  //int k=0;//记录相同子节点个数
	  int u=0;//记录返回数组长度
	  boolean p=false;//标记在子节点判断中是否是第一次进入
	  String souJ="";//记录i值是否出现过(在记录子节点个数判断中)
	  for (int i = 0; i < ops.length; i++) {
		  if(!ops[i].isPrintable()) continue;
		  ColumnDef cd = new ColumnDef();
		  if(p&&!souJ.contains(String.valueOf(i))){
			  if(ops[i].getTitle().contains("|")||ops[i-1].getTitle().contains("|")){
				  
				 int wei=ops[i].getTitle().indexOf("|");
				 int wei2=ops[i-1].getTitle().indexOf("|");
				 String titleStr1=ops[i].getTitle().substring(0, wei<=0?0:wei);
				 String titleStr2 =ops[i-1].getTitle().substring(0,wei2);
				  
				  if(titleStr1.equals(titleStr2)){
					  titleZiList.add(ops[i].getTitle().substring(wei+1));
				  }else{
					  
					  int size=titleZiList.size();
					  int zi=i-size;//子节点开头位置
					  cd.setTitle(titleStr2);//父节点标题

					  ColumnDef[] cdZis=new ColumnDef[size];//声明子节点数组
					  for (int j = 0; j < size; j++) {
						  
						ColumnDef cdZi = new ColumnDef();
						cdZi.setTitle(titleZiList.get(j));
						cdZi.setFieldName(ops[zi+j].getFieldName().toUpperCase());
						cdZi.setType(paramType(ops[zi+j]));
						cdZis[j]=cdZi;
					  }
					  cd.setTitleZi(cdZis);
					  cds[u]=cd;
					  titleZiList.removeAll(titleZiList);
					  u=u+size;p=false;
					  souJ+=i;i--;//当前i是该集合的后一项，不属于该集合，要重新取
				  }
				  continue;
			  }
		  }else{
			  if(ops[i].getTitle().contains("|")){
				int wei=ops[i].getTitle().indexOf("|");
				titleZiList.add(ops[i].getTitle().substring(wei+1));
				p=true;
				continue;
			  }
		  }
		  cd.setFieldName(ops[i].getFieldName().toUpperCase());
		  cd.setTitle(ops[i].getTitle());
		  cd.setType(paramType(ops[i]));
		  cds[u]=cd;
		  u++;
	  }
	  
	  return cds;
  }
  /**
   * 多级标题输出
   * @param ops
   * @return
   */  
  public static String[] getTitleData(SearchOption[] ops){
	  String[] titles = new String[getNoOutputLength(ops)];
	  int maxLength=maxLengthSearchOption(ops);
	  int u=0;
	  for (int i = 0; i < ops.length; i++) {
		  if(!ops[i].isPrintable()) continue;
		  int num=OtherUtil.numCharString(ops[i].getTitle());
		  String title=ops[i].getTitle();
		  for (int j = 0; j < maxLength-num; j++) {
			title+="|@";
		  }
		titles[u]=title;
		u++;
	}
	  return titles;
  }
 /**
  * 找出含有最多“|”的标题
  * @param ops
  * @return
  */
  public static int maxLengthSearchOption(SearchOption[] ops){
	  int maxLength=0;//找出含有最多“|”的标题
	  for (int i = 0; i < ops.length; i++) {
		  int length=OtherUtil.numCharString(ops[i].getTitle());
		  if(!ops[i].isPrintable()) continue;
		  else if(maxLength<=length)
			  maxLength=length;
	  }
	  return maxLength;
  }
  
  private static int paramType(SearchOption ops){
	  int type=8;
	  if(ops.getFieldType().equals("String")) type=1;
	  
	  else if(ops.getFieldType().equals("Integer")) type=2;
	  
	  else if(ops.getFieldType().equals("Number")) type=3;
	  
	  else if(ops.getFieldType().equals("Percent")) type=4;
	  
	  else if(ops.getFieldType().equals("Boolean")) type=5;
	  
	  else if(ops.getFieldType().equals("Money")) type=6;
	  
	  else if(ops.getFieldType().equals("Date")) type=7;
	  
	  return type;
  }
  /**
   * 得到非输出字段的个数
   * @param ops
   * @return
   */
  private static int getNoOutputLength(SearchOption[] ops){
	  int columnLen=0;
	  for (int i = 0; i < ops.length; i++) {
		  if(!ops[i].isPrintable())
			  columnLen++;
	}
	  return (ops.length-columnLen);
  }
}

