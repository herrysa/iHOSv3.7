function getXMLDoc(url)
{
	var agnt=navigator.userAgent.toLowerCase();
	var isChrome = false;
	var isIE = (agnt.indexOf("msie")>0 || agnt.indexOf("trident")>0) ? true : false;
	if(!isIE) {
		if(agnt.indexOf("chrome")>0) isChrome = true;
	}
	var xmlDoc;
	if(isChrome) {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", url, false); 
		xhr.send(null);
		xmlDoc = xhr.responseXML; 
	}
	else {
		if(isIE)
			xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		else
			xmlDoc=document.implementation.createDocument("","",null);
		xmlDoc.async=false;
		xmlDoc.load(url);
	}
	return xmlDoc;
}

function inserttable(funcname, usage, ret, memo, arrPara, example, illu, divFuncTable)
{
	if(illu != "") illu = '<img src="documentg.bmp" title="' +illu+ '">&nbsp;&nbsp;';
	var str = '<div id="' +divFuncTable+funcname+ '"></div><br>';
	str += '<table class="funcTable" width=98% cols=3 cellpadding=4 cellspacing=0 border=1 borderColorLight=#999999 borderColorDark=#999999 align="center">';
	str += '<col width=120><col><col width=64 align=right>';
	str += '<tr bgcolor=#d8d8f8><td style="border-right:0px;font-size:18px;color:#008899;font-weight:900" align=center>' +funcname+ '</td><td  style="border-right:0px;"><b>' +usage+ '</b></td>';
	str += '<td style="border-left:0px;">' +illu+  '<a href="#func_' +divFuncTable+funcname+ '"><img border=0 src="arrowup.gif" title="回到目录"/> </a> </td></tr>';

	var paras = arrPara.length;
	if(paras==0)
		str += '<tr><td align=right>参数</td><td colspan=2>(无)</td></tr>';
	else if(paras==1)
		str += '<tr><td align=right>参数</td><td colspan=2>' +arrPara[0]+ '</td></tr>';
	else {
		for(var i=1; i<=paras; i++) {
			str += '<tr><td align=right>参数' +i+ '</td><td colspan=2>' +arrPara[i-1]+ '</td></tr>';
		}
	}

	if(ret=="") ret = "(无)";
	str += '<tr><td align=right>返回值</td><td colspan=2>' +ret+ '</td></tr>';
	if(memo != "") str += '<tr><td align=right>备注</td><td colspan=2>' +memo+ '</td></tr>';
	if(example != "") str += '<tr bgColor=yellow><td align=right>示例</td><td colspan=2>' +example+ '</td></tr>';
	str += '</table><br>';
	return str;
}

function genfunctable(divFuncTable, divFunc, xmldoc)
{
	if(xmldoc==null) return;
	var root = xmldoc.documentElement;	if(root==null) return;
	var x=xmldoc.getElementsByTagName('category'); if(x==null) return;
	var cats = x.length;

	var str = '<table class="funcTable" width=94% cellpadding=2 cellspacing=0 border=1 borderColorLight=#999999 borderColorDark=#999999 align="center">';
	str += '<tr bgcolor=#eaeaea><th>分类</th><th>函数名</th><th>用途</th></tr>';
	for(var i=0; i<cats; i++) {
		var cat = x[i];
		var catName = cat.attributes.getNamedItem("name").nodeValue;
		var xx = cat.getElementsByTagName("function");
		var funcs = xx.length;
		for(var j=0; j<funcs; j++) {
			var func = xx[j];
			var funcname = func.attributes.getNamedItem("name").nodeValue;
			var usage = func.getElementsByTagName("usage")[0].childNodes[0].nodeValue;
			var s = '<tr>';
			if(j==0) s+= '<td rowspan=' +funcs+ '>' +catName+ '</td>';
			s +='<td><a href="#' +divFuncTable+funcname+ '"><div id="func_' +divFuncTable+funcname+ '"/>' +funcname+ '</a></td><td>' +usage+ '</td></tr>';
			str += s;
		}
	}
	document.getElementById(divFuncTable).innerHTML = str + '</table>';

	str=' 函数详解:';
	for(i=0; i<cats; i++) {
		var cat = x[i];
		var xx = cat.getElementsByTagName("function");
		var funcs = xx.length;
		for(var j=0; j<funcs; j++) {
			var paras=0;
			var usage="";
			var detail="";
			var ret="";
			var memo="";
			var example="";
			var illu="";
			var arrPara=new Array();
			var func = xx[j];
			var funcname = func.attributes.getNamedItem("name").nodeValue;

			var childs = func.childNodes.length;
			for(var k=0; k<childs; k++) {
				var xxx = func.childNodes[k];
				if(xxx.nodeType!=1) continue;

				var text = xxx.childNodes[0].nodeValue;
				if(xxx.nodeName == "usage") usage = text;
				else if(xxx.nodeName == "detail") detail = text;
				else if(xxx.nodeName == "return") ret = text;
				else if(xxx.nodeName == "memo") memo = text;
				else if(xxx.nodeName == "example") example = text;
				else if(xxx.nodeName == "illu") illu = text;
				else if(xxx.nodeName == "para") arrPara[paras++]=text;
			}
			if(detail != "") usage=detail;
			str += inserttable(funcname, usage, ret, memo, arrPara, example, illu, divFuncTable);
		}
	}
	document.getElementById(divFunc).innerHTML = str;
}




//Events
function getrowspan(start, end, arr)
{
 var rowspan = 0;
 for(var i=start; i<end; i++) rowspan += arr[i].getRows();
 return rowspan;
}
function findnext(fieldName, startRow, arr)
{
 if(startRow >= arr.length) return -1;
 var last = arr[startRow][fieldName];
 for(var row=startRow+1; row<arr.length; row++) {
  var s = arr[row][fieldName];
  if(s != last) return row;
 }
 return -1;
}
function adj(isGroup, arr)
{
 var fieldName = isGroup ? "m_group" : "m_name";
 for(var rowStart=0; ;) {
  var rowEnd = findnext(fieldName, rowStart, arr);
  var rowTo = rowEnd==-1 ? arr.length : rowEnd;
  var rowspan = getrowspan(rowStart, rowTo, arr);
  if(isGroup) arr[rowStart].m_rowspan_group = rowspan;
  else arr[rowStart].m_rowspan_name = rowspan;
  for(var i = rowStart + 1; i<rowTo; i++) {
	if(isGroup) arr[i].m_bInSpan_group = true;
	else arr[i].m_bInSpan_name = true;
  }
  if(rowEnd == -1) break;
  rowStart = rowEnd;
 }
}
function genetr(needGroup, arr)
{
 adj(false, arr);
 if(needGroup) adj(true, arr);
 var str = '';
 for(var i=0; i<arr.length; i++) {
	var o = arr[i];
	str += '<tr>';
	if(needGroup) str += o.writeGroup();
	str += o.writeName( ) + o.writeMeaning( ) +  '</tr>';
 }
 return str;
}
function CRecord( eventname ) {
	this.m_group="";
	this.m_name=eventname;
	this.m_meaning="";
	this.m_arrMemo = new Array(0);
	this.m_arrPara = new Array(0);
	this.m_rowspan_group = 1;
	this.m_rowspan_name = 1;
	this.m_bInSpan_group = false;
	this.m_bInSpan_name = false;
	this.getRows = function( ) {
		return 1 + this.m_arrMemo.length;
	}
	this.writeGroup = function( ) {
		if(this.m_bInSpan_group) return "";
		var str = '<td';
		if(this.m_rowspan_group > 1) str += ' rowspan="' +this.m_rowspan_group+ '"';
		str += '>' + this.m_group + '</td>';
		return str;
	}
	this.writeName = function( ) {
		if(this.m_bInSpan_name) return "";
		var str = '<td';
		if(this.m_rowspan_name > 1) str += ' rowspan="' +this.m_rowspan_name+ '"';
		else if(this.getRows( ) > 1) str += ' rowspan="' +this.getRows( )+ '"';
		str += '>' + this.m_name + '</td>';
		return str;
	}
	this.writeMeaning = function( ) {
		var str = '<td>' +this.m_meaning+ '</td>';
		for(var j=0; j<4; j++) {
			var s='&nbsp;';
			if(j < this.m_arrPara.length) s = this.m_arrPara[j];
			str += '<td>' + s + '</td>';
		}
		if(this.m_arrMemo.length > 0) {
			for(var k=0; k<this.m_arrMemo.length; k++) {
				var s = '备注';
				if(this.m_arrMemo.length>1) s += k+1;
				str += '</tr><tr bgColor=#ffffdd><td colspan="5">' +s+ '：' + this.m_arrMemo[k] + '</td>';
			}
		}
		return str;
	}
	return this;
}
function geneventtable(divTag, xmldoc, nodename, needGroup)
{
	if(xmldoc==null) return;
	var x=xmldoc.getElementsByTagName(nodename); if(x==null) return;

	var str = '<table width=98% cellpadding=4 cellspacing=0 border=1 borderColorLight=#999999 borderColorDark=#999999 align="center">';
	str += '<tr bgcolor=#eaeaea>';
	if(needGroup) str += '<td>分组</td>';
	str += '<td>Event</td><td>含义</td><td>p1</td><td>p2</td><td>p3</td><td>p4</td></tr>';

	var ele = x[0];
	var count = ele.childNodes.length;
	var arr = new Array(0);
	for(var i=0; i<count; i++) {
		var c = ele.childNodes[i];
		if(c.nodeType==3) continue;
		var o = new CRecord( c.nodeName );
		for(var j=0; j<c.childNodes.length; j++) {
			var d = c.childNodes[j];
			if(d.nodeName=="meaning")		o.m_meaning = d.childNodes[0].nodeValue;
			else if(d.nodeName=="Group")		o.m_group = d.childNodes[0].nodeValue;
			else if(d.nodeName=="memo")	o.m_arrMemo.push( d.childNodes[0].nodeValue );
			else if(d.nodeName=="p")		o.m_arrPara.push( d.childNodes[0].nodeValue );
		}
		arr.push(o);
	}
	str += genetr(needGroup, arr);
	str += '</table>';
	document.getElementById(divTag).innerHTML = str;
}
