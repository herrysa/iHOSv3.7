package com.huge.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * 计算算术表达式的值
 * 
 * @author Gaozhengyang
 * @date 2014年11月10日
 */
public class ComputeExpression {
	private String expression;

	public ComputeExpression(String expression) {
		super();
		this.expression = expression;
	}

	/**
	 * 转换为后缀表达式
	 * @param temp
	 * @return
	 */
	private String[] getPostfix(String[] temp) {
		ArrayList<String> arraylist = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
		stack.push(new String("#"));
		String tmp = null;
		for (int i = 0; i < temp.length; i++) {
			tmp = temp[i];
			if (tmp.charAt(0) >= '0' && tmp.charAt(0) <= '9') {
				arraylist.add(tmp);
			} else if (tmp.equals("(")) {
				stack.push(tmp);
			} else if (tmp.equals("*") || tmp.equals("/")) {
				while (((String) stack.peek()).equals("*")
						|| ((String) stack.peek()).equals("/")) {
					arraylist.add((String) stack.pop());
				}
				stack.push(tmp);
			} else if (tmp.equals("+") || tmp.equals("-")) {
				while (!((String) stack.peek()).equals("(")
						&& !((String) stack.peek()).equals("#")) {
					arraylist.add((String) stack.pop());
				}
				stack.push(tmp);
			} else if (tmp.equals(")")) {
				while (!((String) stack.peek()).equals("(")) {
					arraylist.add((String) stack.pop());
				}
				stack.pop();
			} else {
				break;
			}
		}
		while (!((String) stack.peek()).equals("#")) {
			arraylist.add((String) stack.pop());
		}
		String[] result = new String[arraylist.size()];
		Iterator<String> it = arraylist.iterator();
		int i = 0;
		while (it.hasNext()) {
			result[i++] = (String) it.next();
		}
		return result;
	}

	/**
	 * 根据后缀表达式计算值
	 * @param temp
	 * @return
	 */
	private String getValue(String[] temp) {
		Stack<String> stack = new Stack<String>();
		String tmp = null;
		for (int i = 0; i < temp.length; i++) {
			tmp = temp[i];
			if (tmp.charAt(0) >= '0' && tmp.charAt(0) <= '9') {
				stack.push(temp[i]);
			} else if (tmp.equals("+")) {
				stack.push(Double.toString(Double.parseDouble((String) stack
						.pop()) + Double.parseDouble((String) stack.pop())));
			} else if (tmp.equals("-")) {
				stack.push(Double.toString(-(Double.parseDouble((String) stack
						.pop()) - Double.parseDouble((String) stack.pop()))));
			} else if (tmp.equals("*")) {
				stack.push(Double.toString(Double.parseDouble((String) stack
						.pop()) * Double.parseDouble((String) stack.pop())));
			} else if (tmp.equals("/")) {
				try {
					Double chushu = Double.parseDouble((String) stack.pop());
					Double bchushu = Double.parseDouble((String) stack.pop());
					if(chushu==0 || bchushu==0){
						stack.push(Double.toString(0));
					}else{
						stack.push(Double.toString(bchushu/ chushu));
					}
				} catch (Exception ex) {
					throw new RuntimeException("除数不能为0");
				}
			}
		}
		return (String) stack.pop();
	}

	/**
	 * 拆分表达式为 操作数和操作符的数组
	 * @return
	 */
	private String[] convert() {
		ArrayList<String> arraylist = new ArrayList<String>();
		char charactor = Character.MAX_VALUE;
		for (int i = 0; i < expression.length(); i++) {
			charactor = expression.charAt(i);
			if (charactor >= '0' && charactor <= '9' || charactor == '.') {
				String bridge = new String("");
				for (; i < expression.length() && (expression.charAt(i) >= '0'
						&& expression.charAt(i) <= '9' || expression .charAt(i) == '.'); i++) {
					bridge = bridge + Character.toString(expression.charAt(i));
				}
				arraylist.add(bridge);
				i--;
			} else {
				arraylist.add(Character.toString(charactor));
			}
		}
		Iterator<String> it = arraylist.iterator();
		String[] temp = new String[arraylist.size()];
		int i = 0;
		while (it.hasNext())
			temp[i++] = (String) it.next();
		return temp;
	}

	public String getResult() {
		String[] temp = getPostfix(convert());
		String result = getValue(temp);
		return result;
	}
	public Double getDoubleResult(){
		return Double.parseDouble(getResult());
	}
	public static void main(String[] args) {
		String str = new String("4.5/1.5+5*(60-20)/(35.7-25.7)");
		ComputeExpression com = new ComputeExpression(str);
		System.out.println(Arrays.toString(com.getPostfix(com.convert())));
		System.out.println(str + " = " + com.getResult());
	}
}
