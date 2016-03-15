package com.huge.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.huge.webapp.pagers.JQueryPager;

public class OtherUtil {
    /**
     * 提取的判断非空方法
     * @param o
     * @return
     */
    public static boolean measureNotNull( Object o ) {
        return o != null && !o.equals( "" ) && !o.equals( "undefined" );
    }

    public static boolean measureNull( Object o ) {
        return o == null || o.equals( "" );
    }

    /**
     * 格式化数字
     * @param o
     * @return
     */
    public static BigDecimal formatBigDecimal( Object o ) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern( "0.00" );
        return new BigDecimal( df.format( o ) );
    }

    public static String formatString( Object o ) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern( "0.00" );
        return df.format( o );
    }

    public static String numberBigDecimal( Object o ) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits( 2 );
        nf.setMaximumFractionDigits( 2 );
        return nf.format( o );
    }

    /**
     * 判断“|”在字符串中出现个数
     * @param str
     * @return
     */
    public static int numCharString( String str ) {
        int num = 0;
        Pattern pt = Pattern.compile( "\\|", Pattern.CASE_INSENSITIVE );
        Matcher mc = pt.matcher( str );
        while ( mc.find() )
            num++;
        return num;
    }

    /**
     * 找到指定字符在字符串中第二次出现位置
     * @param str
     * @param location
     * @return
     */
    public static int indexStr( String str, int location ) {
        return str.indexOf( "|", location + 1 );
    }
    
    /**
     * 去掉字符串末尾的指定字符
     * @param str
     * @param contantStr
     * @return
     */
    public static String subStrEnd(String str,String contantStr){
    	if(measureNull(str)||!str.contains(contantStr)){
    		return str;
    	}
    	return str.substring(0, str.lastIndexOf(contantStr));
    }
    /**
     * 截取字符串，并加单引号拼接在一起
     * @param str
     * @param contantStr
     * @return
     */
    public static String splitStrAddQuotes(String str,String splitStr){
    	if(measureNull(str)){
    		return str;
    	}
    	String[] strArr = str.split(splitStr);
    	String result = "";
    	for(String strTemp:strArr){
    		result += "'" + strTemp + "',";
    	}
    	return result.substring(0, result.lastIndexOf(","));
    }
    /**
     * 随机获取32位不含'-'的UUID串
     * @return
     */
    public static String getRandomUUID(){
    	UUID uuid = UUID.randomUUID();
		String s = uuid.toString(); 
		s =  s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
		return s;
    }
    /**
     * List转字符串
     * @param strList
     * @return
     */
    public static String transferListToString(List<String> strList){
    	String strs = "" ;
    	if(strList!=null && !strList.isEmpty()){
    		for(String str:strList){
    			strs += str+",";
    		}
    		strs = subStrEnd(strs, ",");
    	}
    	return strs;
    }
    /**
     * 数组转字符串
     * @param strArr
     * @return
     */
    public static String transferArrToString(String[] strArr){
    	String strs = "" ;
    	if(strArr!=null && strArr.length>0){
    		for(String str:strArr){
    			strs += str+",";
    		}
    		strs = subStrEnd(strs, ",");
    	}
    	return strs;
    }
    
    /**
     * 解析期间字符串，数据库-->页面
     * @param period
     * @return
     */
    public static String parsePeriod(String period){
    	if(measureNull(period)){
    		return null;
    	}
    	if(period.length()==4){
			period = period+"年";
		}else if(period.indexOf('-')>0){
			period = period.substring(0, 4)+"年"+period.substring(5, 6)+"季度";
		}
    	return period;
    }
    /**
     * 获取上个月的期间
     * @param period
     * @return
     */
    public static String lastMonthPeriod(String period){
    	if(OtherUtil.measureNull(period)||period.length()!=6){
    		return null;
    	}
    	String yearstr = period.substring(0, 4);
    	String monthstr = period.substring(4, 6);
    	int year = Integer.parseInt(yearstr);
    	int month = Integer.parseInt(monthstr);
    	if(month==1){
    		period = year - 1 + "12";
    	}else{
    		month = month - 1;
    		if(month<10){
    			period = year+"0"+month;
    		}else{
    			period = year+""+month;
    		}
    	}
    	return period;
    }
   
    public static void main( String[] args ) {
        String ss = "asdf|asdfa|wert|er|";
        /*int index = ss.indexOf( "|" );
        int second = indexStr( ss, index );*/
        int index=ss.lastIndexOf("|"); 
        ss=ss.substring(0, index-1);
        System.out.println( ss );
    }
    public static  Object[] splitArray(Object[] ary, int subSize) {
		int count = ary.length % subSize == 0 ? ary.length / subSize
				: ary.length / subSize + 1;

		List<List<Object>> subAryList = new ArrayList<List<Object>>();

		for (int i = 0; i < count; i++) {
			int index = i * subSize;

			List<Object> list = new ArrayList<Object>();
			int j = 0;
			while (j < subSize && index < ary.length) {
				list.add(ary[index++]);
				j++;
			}

			subAryList.add(list);
		}

		Object[] subAry = new Object[subAryList.size()];

		for (int i = 0; i < subAryList.size(); i++) {
			List<Object> subList = subAryList.get(i);

			Object[] subAryItem = new Object[subList.size()];
			for (int j = 0; j < subList.size(); j++) {
				subAryItem[j] = subList.get(j);
			}

			subAry[i] = subAryItem;
		}

		return subAry;
	}
    /**
     * 获取request中的排序
     * @param request
     * @return sortFilter
     */
    public static String getSortFilterFromRequest(HttpServletRequest request){
    	String sortFilter = null;
    	JQueryPager jqp = new JQueryPager();
		String sortCriterion = request.getParameter(jqp.getRequestValueSort());
		String sordOrder = request.getParameter(jqp.getRequestValueDirection());
		if(OtherUtil.measureNotNull(sortCriterion)&&OtherUtil.measureNotNull(sordOrder)){
			if("asc".equals(sordOrder)){
				sortFilter = "OAS_"+sortCriterion;
			}else{
				sortFilter = "ODS_"+sortCriterion;
			}
		}
		return sortFilter;
    }
    
    public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
}
