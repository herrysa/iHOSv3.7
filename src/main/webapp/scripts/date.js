Date.prototype.format = function (formatStr) {    
    var date = this;    
    /*   
	    函数：填充0字符   
	    参数：value-需要填充的字符串, length-总长度   
	    返回：填充后的字符串   
    */   
    var zeroize = function (value, length) {    
        if (!length) {    
            length = 2;    
        }    
        value = new String(value);    
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {    
            zeros += '0';    
        }    
            return zeros + value;    
    };    
    return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {    
        switch ($0) {    
            case 'd': return date.getDate();    
            case 'dd': return zeroize(date.getDate());    
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];    
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];    
            case 'M': return date.getMonth() + 1;    
            case 'MM': return zeroize(date.getMonth() + 1);    
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];    
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];    
            case 'yy': return new String(date.getFullYear()).substr(2);    
            case 'yyyy': return date.getFullYear();    
            case 'h': return date.getHours() % 12 || 12;    
            case 'hh': return zeroize(date.getHours() % 12 || 12);    
            case 'H': return date.getHours();    
            case 'HH': return zeroize(date.getHours());    
            case 'm': return date.getMinutes();    
            case 'mm': return zeroize(date.getMinutes());    
            case 's': return date.getSeconds();    
            case 'ss': return zeroize(date.getSeconds());    
            case 'l': return date.getMilliseconds();    
            case 'll': return zeroize(date.getMilliseconds());    
            case 'tt': return date.getHours() < 12 ? 'am' : 'pm';    
            case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';    
        }    
    });    
}  

Date.prototype.DateAdd = function(strInterval, Number) {   
    var dtTmp = this;  
    switch (strInterval) {   
        case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number));  
        case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number));  
        case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number));  
        case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number));  
        case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));  
        case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
        case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
        case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
    }  
} 

//---------------------------------------------------  
//判断闰年  
//---------------------------------------------------  
Date.prototype.isLeapYear = function(){   
 return (0==this.getYear()%4&&((this.getYear()%100!=0)||(this.getYear()%400==0)));   
}  

//+---------------------------------------------------  
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd   
//+---------------------------------------------------  
function daysBetween(DateOne,DateTwo){   
  var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
  var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
  var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  

  var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
  var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
  var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  

  var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
  return Math.abs(cha);  
}  
//+---------------------------------------------------  
//| 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串  
//+---------------------------------------------------  
Date.prototype.DateDiff = function(strInterval, dtEnd) {   
  var dtStart = this;  
  if (typeof dtEnd == 'string' )//如果是字符串转换为日期型  
  {   
      dtEnd = StringToDate(dtEnd);  
  }  
  switch (strInterval) {   
      case 's' :return parseInt((dtEnd - dtStart) / 1000);  
      case 'n' :return parseInt((dtEnd - dtStart) / 60000);  
      case 'h' :return parseInt((dtEnd - dtStart) / 3600000);  
      case 'd' :return parseInt((dtEnd - dtStart) / 86400000);  
      case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));  
      case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);  
      case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();  
  }  
}  
//+---------------------------------------------------  
//| 字符串转成日期类型   
//| 格式 MM/dd/YYYY MM-dd-YYYY YYYY/MM/dd YYYY-MM-dd  
//+---------------------------------------------------  
function StringToDate(DateStr)  {   
  var converted = Date.parse(DateStr);  
  var myDate = new Date(converted);  
  if (isNaN(myDate)){   
      var arys= DateStr.split('-');  
      myDate = new Date(arys[0],--arys[1],arys[2]);  
  }  
  return myDate;  
}  