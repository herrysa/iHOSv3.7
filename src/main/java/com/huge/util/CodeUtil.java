package com.huge.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
/**
 * 编码规则的工具类
 * @author Administrator
 * @date 2013-9-3
 */
public class CodeUtil {
	/**
	 * 判断目标字符串是否是当前规则下的顶级字符串
	 * @param codeRule
	 * @param targetCode
	 * @return true：是顶级；false：非顶级
	 */
	public static Boolean isRoot(String codeRule,String targetCode){
		String[] strs = codeRule.split("-");
		if(targetCode.length()==Integer.parseInt(strs[0])){
			return true;
		}
		return false;
	}
	/**
	 * 根据当前编码规则获取目标字符串的直接父级字符串
	 * @param codeRule
	 * @param targetCode
	 * @return
	 */
	public static String getFather(String codeRule,String targetCode){
		String father = null;
		StringTokenizer token = new StringTokenizer(codeRule,"-");
		int num = 0,temp = 0;
		while(token.hasMoreTokens()){
			temp = Integer.parseInt(token.nextToken());
			num += temp;
			if(targetCode.length()==num){
				if(num==temp){
					father =  targetCode;
				}else{
					father =  targetCode.substring(0,targetCode.length()-temp);
				}
			}
		}
		return father;
	}
	/**
	 * 将当前编码规则的第index位替换为目标值targetNum；index从0开始
	 * @param codeRule
	 * @param index
	 * @param targetNum
	 * @return 替换后的新规则
	 */
	public static String updateCodeRule(String codeRule,int index,int targetNum){
		String[] rules = codeRule.split("-");
		rules[index] = String.valueOf(targetNum);
		codeRule = "";
		for(String rule:rules){
			codeRule += rule+"-";
		}
		codeRule = codeRule.substring(0,codeRule.length()-1);
		return codeRule;
	}
	/**
	 * 判断目标字符串是否匹配给定的编码方案
	 * @param codeRule
	 * @param targetCode
	 * @return true:匹配；false：不匹配
	 */
	public static Boolean isMatch(String codeRule,String targetCode){
		Boolean isMatch = false;
		StringTokenizer token = new StringTokenizer(codeRule,"-");
		int num = 0,count = 0;
		while(token.hasMoreTokens()){
			count++;
			num += Integer.parseInt(token.nextToken());
			if(targetCode.length()<num){
				isMatch = false;
				break;
			}
			if(targetCode.length()==num){
				isMatch =  true;
			}
		}
		return isMatch;
	}
	/**
	 * 根据当前编码规则获取目标字符串所处的等级
	 * @param codeRule
	 * @param targetCode
	 * @return -1:不匹配;1：是顶级；2：二级；...
	 */
	public static int getLevel(String codeRule,String targetCode){
		int level = -1;
		StringTokenizer token = new StringTokenizer(codeRule,"-");
		int num = 0,count = 0;
		while(token.hasMoreTokens()){
			count++;
			num += Integer.parseInt(token.nextToken());
			if(targetCode.length()<num){
				break;
			}
			if(targetCode.length()==num){
				level = count;
			}
		}
		return level;
	}
	/**
	 * 返回能够匹配当前编码规则的可能的字符串的长度集合
	 * @param codeRule
	 * @return 例如规则为"4-3-2"，则返回集合元素为{4,7,9}
	 */
	public static List<Integer> getLengths(String codeRule){
		List<Integer> lengths = new ArrayList<Integer>();
		StringTokenizer token = new StringTokenizer(codeRule,"-");
		Integer length = 0;
		while(token.hasMoreTokens()){
			length += Integer.parseInt(token.nextToken());
			lengths.add(length);
		}
		return lengths;
	}
}
