/**
 * Created by QYT on 2015/6/8 0008.
 */

var gzFuncs = 'abs();curmthdays();max(,);min(,);pow(,);round(,);trunc(,)';
var funcsNote = [/* abs() */
		'<p>abs(x) 返回数的绝对值<p><p>其中：</p><p>x 待处理的数值</p><p>示例：</p>'
				+ '<p>abs(-7.25)=7.25</p><p>abs(7.25-10)=2.75</p>',
		/* curmthdays() */
		'<p>curmthdays() 取当月的天数<p>示例：</p>'
				+ '<p>curmthdays()=30 假如今天是2015年6月</p>',
		/* max(,) */
		'<p>max(x,y) 返回两个指定的数中较大的值<p><p>其中：</p><p>x,y 待做比较处理的数值</p><p>示例：</p>'
				+ '<p>max(5,7)=7</p><p>max(7.25,7.30)=7.30</p>',
		/* min(,) */
		'<p>min(x,y) 返回两个指定的数中较小的值<p><p>其中：</p><p>x,y 待做比较处理的数值</p><p>示例：</p>'
				+ '<p>min(5,7)=5</p><p>min(7.25,7.30)=7.25</p>',
		/* pow(,) */
		'<p>pow(x,y) 返回 x的 y次幂的值<p><p>其中：</p><p>x 底数,必须是数字</p><p>y 幂数,必须是数字</p><p>示例：</p>'
				+ '<p>pow(2,3)=8</p><p>pow(-2,4)=16</p>',
		/* round() */
		'<p>round(x,y) 把一个数字四舍五入为指定小数位数的数字。<p><p>其中：</p><p>x 待处理的数值,y 规定小数的位数，是 0~4之间的值,为空时为2,超出范围时为0</p><p>示例：</p>'
				+ '<p>round(13.37,1)=13.4</p><p>round(4.567,2)=4.57</p>',
		/* trunc(,) */
		'<p>trunc(number[,decimals]) 截取</p><p>其中：</p><p>number 待做截取处理的数值</p>'
				+ '<p>decimals 指明需保留小数点后面的位数。可选项，忽略它则截去所有的小数部分。</p><p>示例：</p>'
				+ '<p>trunc(89.985,2)=89.98</p><p>trunc(89.985)=89 (即取整)</p><p>trunc(89.985,-1)=80</p>' ];
/**
 * 求算术表达式的绝对值
 * 
 * @param value
 * @returns {number}
 */
function abs(value) {
	return Math.abs(value);
}
/**
 * 求最大值
 * 
 * @param x
 * @param y
 */
function max(x, y) {
	return Math.max(x, y);
}
/**
 * 求最小值
 * 
 * @param x
 * @param y
 */
function min(x, y) {
	return Math.min(x, y)
}
/**
 * x 的 y 次幂的值
 * 
 * @param x
 * @param y
 */
function pow(x, y) {
	return Math.pow(x, y);
}
/**
 * 把一个数字舍入为最接近的整数
 * 
 * @param x
 * @param y
 */
function round(x, place) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return;
	}
	if (!place && place != 0) {
		place = 2;
	} else if (place == 1 || place == 2 || place == 3 || place == 4) {
	} else {
		return Math.round(x);
	}
	x = parseFloat(x);
	var str = x.toString(10);
	var reg = new RegExp("\\.(\\d{" + place + "})");
	if (/[^\-\.\d]/.test(str) || !reg.test(str))
		return x;
	str = str.replace(reg, "$1.");
	f = Math.round(parseFloat(str)) / Math.pow(10, place);
	return f;
}
function roundToFixed(x, place) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return;
	}
	if (!place && place != 0) {
		place = 2;
	} else if (place == 1 || place == 2 || place == 3 || place == 4) {
	} else {
		return Math.round(x);
	}
	x = f.toFixed(6);
	x = parseFloat(x);
	var str = x.toString(10);
	var reg = new RegExp("\\.(\\d{" + place + "})");
	if (/[^\-\.\d]/.test(str) || !reg.test(str))
		return x;
	str = str.replace(reg, "$1.");
	f = Math.round(parseFloat(str)) / Math.pow(10, place);
	return f.toFixed(place);
}
/**
 * 取当月的天数
 * 
 * @returns {number}
 */
function curmthdays() {
	var date = new Date();
	return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
}