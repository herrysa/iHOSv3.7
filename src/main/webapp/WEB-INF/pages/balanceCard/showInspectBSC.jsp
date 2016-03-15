<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>

<script>
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);

jQuery(document).ready(function(){
	var gridName = "inspectBSCTableRead";
	mergerTableCol(gridName, 'weidus');
	mergerTableCol(gridName, 'fenlei');
    var dscoreTotal = 0;
    /* jQuery("#inspectBSCTableRead").find("td[name=score]").each(function(){
    	dscoreTotal += parseInt(jQuery(this).text());
    }); */
    jQuery("#realTotalScore").text(dscoreTotal);
    jQuery("#inspectBSCTableRead").find("td[name='score']").each(function(){
		var fixedValue = parseFloat(jQuery(this).text());
		jQuery(this).text(fixedValue.toFixed(jjScoreDecimalPlaces));
	});
    var tscore=0,tmoney1=0,tmoney2=0,tdscore=0;
    jQuery("#inspectBSCTableRead").find("tr").each(function(){
    	jQuery(this).find("td:visible").each(function(){
    		if(jQuery(this).attr("name")=="score"){
    			tscore+=parseFloat(jQuery(this).text());
    		}else
    		if(jQuery(this).attr("name")=="money1"){
    			tmoney1+=parseFloat(jQuery(this).text());
    		}else
    		if(jQuery(this).attr("name")=="money2"){
    			tmoney2+=parseFloat(jQuery(this).text());
    		}else
    		if(jQuery(this).attr("name")=="dscore"){
    			tdscore+=parseFloat(jQuery(this).text());
    		}
    		
    	});
    });
    var cloneTr = jQuery("#inspectBSCTableRead").find("tr:last").clone();
    if("bscTitle"!=cloneTr.attr("id")){
	    cloneTr.find("td").each(function(){
	    	jQuery(this).show();
	    	if(jQuery(this).attr("name")=="weidus"){
				jQuery(this).text("小计");
			}else
			if(jQuery(this).attr("name")=="score"){
				jQuery(this).text(toDecimal(tscore,jjScoreDecimalPlaces));
				jQuery("#realTotalScore").text(toDecimal(tscore,jjScoreDecimalPlaces));
			}else
			if(jQuery(this).attr("name")=="money1"){
				jQuery(this).text(toDecimal(tmoney1));
			}else
			if(jQuery(this).attr("name")=="money2"){
				jQuery(this).text(toDecimal(tmoney2));
			}else
			if(jQuery(this).attr("name")=="dscore"){
				//jQuery(this).text(toDecimal(tdscore));
				jQuery(this).html("");
			}else{
				jQuery(this).html("");
			}
		});
	    jQuery("#inspectBSCTableRead").append(cloneTr);
    }
    var thisDialog = jQuery("#bscCardContent").parent();
    while(thisDialog.attr("class").indexOf('dialogContent')==-1){
    	thisDialog = thisDialog.parent();
    }
    jQuery(thisDialog).css("overflow","auto");
    //jQuery("#bscCardContent").height(thisDialog.height()-75);
    //jQuery(".dialogContent").css("overflow":"scroll");
    jQuery("span[name=shengluespan]","#inspectBSCTableRead").hover(function(e){
	 	var left = e.pageX;
	 	var top = e.pageY;
	 	jQuery('body').append("<div id='tdShowPop' style='text-align:left;word-wrap: break-word;word-break: normal;position:absolute;z-index:1000;border:1px gray solid;padding:3px;max-width:300px;background-color:snow;border-radius: 3px;'>"+jQuery(this).text()+"</div>");
	 	jQuery("#tdShowPop").css({ top: top+10 ,left: left+10  });
	 },function(e){
	 	jQuery("#tdShowPop").remove();
	 });
});
//去除空格 
function clearSpace(str) 
{ 
  return str.replace(/\s+/g, ""); 
} 
//去除换行 
function clearBr(key) 
{ 
    key = key.replace(/<\/?.+?>/g,""); 
    key = key.replace(/[\r\n\t]/g, ""); 
    return key; 
}
function saveAuditRemark(){
	if(reportbsceditsate==0){
		alertMsg.error("请在编辑下保存！");
		return;
	}
	var deptInspectId = "",remark3="";
	jQuery("input[name=deptinspectId]").each(function(){
		deptInspectId += jQuery(this).val()+",";
	});
	deptInspectId += " ";
	jQuery("textarea[name=remark3]").each(function(){
		remark3 += jQuery(this).val()+",";
	});
	remark3 += " ";
	var url = 'saveAuditRemark?deptinspectId='+deptInspectId+'&remark3='+remark3;
	url=encodeURI(url);
	$.ajax({
	    url: url,
	    type: 'post',
	    dataType: 'json',
	    aysnc:false,
	    error: function(data){
	        jQuery('#name').attr("value",data.responseText);
	    },
	    success: function(data){
	        // do something with xml
	        alertMsg.correct(data.message);
	        jQuery("span[name=toEdit]","#bscCardContent").each(function(){
	        	jQuery(this).prev().text(jQuery(this).find(".fullInputtext:first").val());
	    	});
	        cancelEditAuditRemark();
	    }
	});
}
var reportbsceditsate = 0;
function exportBscToExcel(){
	var title = jQuery("#BSCInstance_title").text();
    var inspecttype = jQuery("#BSCInstance_inspecttype").text();
    var jjdepttype = jQuery("#BSCInstance_jjdepttype").text();
    var periodType = jQuery("#BSCInstance_periodType").text();
    var totalScore = jQuery("#BSCInstance_totalScore").text();
    var checkPeriod = jQuery("#BSCInstance_checkPeriod").text();
    var visableNum = jQuery("#inspectBSCTableRead").find("tr:first").find("td:visible").length;
    
    var tableData = "";
    jQuery("#inspectBSCTableRead").find("tr:visible").each(function(){
    	jQuery(this).find("td").each(function(){
    		var textTemp = "";
    		if(jQuery(this).css('display')=='none'){
    			textTemp = '$merge$';
    		}else{
    			if(jQuery(this).find("textarea").length>0){
    				textTemp = jQuery(this).find("textarea:first").val();
    			}else{
    				textTemp = jQuery(this).text();
    			}
    			
        		textTemp = clearSpace(textTemp);
        		textTemp = clearBr(textTemp);
    		}
    		/* if(textTemp==""){
    			textTemp = " ";
    		} */
    		tableData += textTemp+",";
    	});
    	tableData = tableData.substring(0,tableData.length-1);
    	tableData += "@";
    });
    //var url = 'creatBSCExcel?tableData='+tableData+'&visableNum='+visableNum+'&title='+title+'&inspecttype='+inspecttype+'&jjdepttype='+jjdepttype+'&periodType='+periodType+'&totalScore='+totalScore+'&checkPeriod='+checkPeriod;
    //url = encodeURI(url);
    //window.location.href = url;
    jQuery("#tableData").val(tableData);
    jQuery("#visableNum").val(visableNum);
    jQuery("#title").val(title);
    jQuery("#inspecttype").val(inspecttype);
    jQuery("#jjdepttype").val(jjdepttype);
    jQuery("#periodType").val(periodType);
    jQuery("input[name=totalScore]:first").val(totalScore);
    jQuery("#checkPeriod").val(checkPeriod);
    jQuery("#creatExcelForm").submit();
    /* $.ajax({
	    url: 'creatBSCExcel',
	    type: 'post',
	    data:{tableData:tableData,visableNum:visableNum,title:title,inspecttype:inspecttype,jjdepttype:jjdepttype,periodType:periodType,totalScore:totalScore,checkPeriod:checkPeriod},
	    dataType: 'json',
	    aysnc:false,
	    error: function(data){
	        //jQuery('#name').attr("value",data.responseText);
	    },
	    success: function(data){
	        // do something with xml
	        //alert(data);
	    }
	}); */
}
function printBSC(){
	//$.printBox("bscCardContent");
	jQuery('body').find('.dialog').each(function(){
		jQuery(this).addClass('Noprint');
	});
	var print_bscCardContent = jQuery('#bscCardContent').clone(true);
	print_bscCardContent.attr("id","print_bscCardContent");
	jQuery('body').prepend(print_bscCardContent);
	window.print();
	print_bscCardContent.remove();
}
function editAuditRemark(){
	jQuery("span[name=toEdit]","#bscCardContent").each(function(){
		jQuery(this).prev().hide();
		jQuery(this).show();
	});
	jQuery("td[name=toEdit]","#bscCardContent").each(function(){
		jQuery(this).find(".fullInputtext:first").width(jQuery(this).width()*0.90);
		jQuery(this).find(".fullInputtext:first").height(35);
	});
	reportbsceditsate=1;
}
function cancelEditAuditRemark(){
	jQuery("span[name=toEdit]","#bscCardContent").each(function(){
		jQuery(this).prev().show();
	});
	jQuery("span[name=toEdit]","#bscCardContent").hide();
	reportbsceditsate=0;
}
</script>
<style>
.bsc
{
	border-color: #40afde;
    border-width: 0 0 1px 1px;
 	border-style: solid;
 	margin:5px;
 	width:99%;
 	table-layout:fixed;
} 
 
.bsc td
{
    border-color: #40afde;
    border-width: 1px 1px 0 0;
    border-style: solid;
    margin: 0;
    padding: 5px;
    font-weight: bold;
    height:25px;
    
}
.bscTh {
	background-image: url("${ctx}/styles/themes/rzlt_theme/ihos_images/bscTh.png");
	background-repeat: repeat-x;
	color: white;
}
.evenTd {
	background-color: #bce4f3;
}
.fullInput
{
	width:99%;
}
.fullInputtext{
		padding:0;
		width:100%;
		height:100%
}
</style>
<form id="creatExcelForm" method="post" action="creatBSCExcel">
<input type="hidden" id="tableData" name="tableData"/>
<input type="hidden" id="visableNum" name="visableNum"/>
<input type="hidden" id="title" name="title"/>
<input type="hidden" id="inspecttype" name="inspecttype"/>
<input type="hidden" id="jjdepttype" name="jjdepttype"/>
<input type="hidden" id="periodType" name="periodType"/>
<input type="hidden" name="totalScore"/>
<input type="hidden" id="checkPeriod" name="checkPeriod"/>
</form>
<div class="page">
<div class="panelBar">
						<ul class="toolBar">
							
							<li><a class="changebutton"
								href="javaScript:editAuditRemark()"><span>编辑备注
								</span>
							</a></li>
							<li><a class="canceleditbutton"
								href="javaScript:cancelEditAuditRemark()"><span>取消编辑
								</span>
							</a></li>
							<li><a class="addbutton"
								href="javaScript:saveAuditRemark()"><span><fmt:message
											key="button.save" />
								</span>
							</a></li>
							<li style="float: right;"><a class="printbutton"
								href="javaScript:printBSC()"><span>打印
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a class=excelbutton
								href="javaScript:exportBscToExcel()"><span>输出Excel
								</span>
							</a></li>
						</ul>
					</div>
	<div class="pageContent">
			<input type="hidden" id="selectedKpi"/>
			<div id="bscCardContent" style="vertical-align: middle;text-align: center;margin-top:5px;">
				<label id="BSCInstance_title" style="font-size:16px;font-weight: bold;"><s:property value="targetDept"/>平衡计分卡（<s:property value="deptInspects.size"/>个指标）</label>
				<div style="margin-bottom:10px;margin-left:10px">
					<div style="">
						<label id="BSCInstance_inspecttype" style="float: left;">考核类别：<s:property value="inspectTempl.inspecttype"/></label>
						<label id="BSCInstance_jjdepttype" style="float: left;margin-left:20px">绩效科室类别：<s:property value="inspectTempl.jjdepttype.khDeptTypeName"/></label>
					</div>
					<br/>
					<div style="margin-top:5px;margin-bottom:5px;">
						<label id="BSCInstance_periodType" style="float: left">考核周期：<s:property value="inspectTempl.periodType"/></label>
						<label id="BSCInstance_totalScore" style="float: left;margin-left:30px">总得分：<span id="realTotalScore"><s:property value="inspectTempl.totalScore"/></span></label>
						<label id="BSCInstance_checkPeriod" style="float: left;margin-left:30px">考核期间：<span id=""><s:property value="checkPeriod"/></span></label>
					</div>
				</div>
				<div style="margin-top:20px;">
				<form id="bscForm">
				<table class="bsc" id="inspectBSCTableRead" >
					<tr id="bscTitle">
							<td class="bscTh">
								<s:text name="bsc.weidu"></s:text>
							</td>
							<td class="bscTh">
								<s:text name="bsc.fenlei"></s:text>
							</td>
							<td class="bscTh">
								<s:text name="bsc.xiangmu"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.score"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.money1"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.money2"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.dscore"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.operator"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.remark"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.operator1"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.remark2"></s:text>
							</td>
							<td class="bscTh" >
								<s:text name="deptInspect.remark3"></s:text>
							</td>
						</tr>
						<s:iterator value="deptInspects" status="it">
						<s:if test="#it.odd==true">
							<tr>
						</s:if>
						<s:else>
							<tr class="evenTd">
						</s:else>
		        			<td style="background-color: white;color: #01619c" name='weidus' group='<s:property value='weidus'/>' mergerName='<s:property value='weidus'/>' order="<s:property value="columnMap['bsc.weidu'].disOrder"/>">
		        				<input type="hidden" name='deptinspectId' class='fullInput' value='<s:property value='deptinspectId'/>'/>
		        				<span>
		        					<s:property value='inspectBSC.weidus'/>
		        				</span>
		        				<br/>
		        				<label style="color: #e22c2c">(
		        				<span name='tdShow'>
		        					<s:property value='inspectBSC.weight1'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input name='weight1' style='width:25px' value='<s:property value='inspectBSC.weight1'/>'/>
		        				</span>%)
		        				</label>
		        			</td><td style="background-color: white;color: #048b01" name='fenlei' group='<s:property value='inspectBSC.weidus'/>_fenlei' mergerName='<s:property value='fenlei'/>' order="<s:property value="columnMap['bsc.fenlei'].disOrder"/>">
		        				<span>
		        					<s:property value='inspectBSC.fenlei'/>
		        				</span>
		        				<br/>
		        				<label style="color: #e22c2c">(
		        				<span name='tdShow'>
		        					<s:property value='inspectBSC.weight2'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input name='weight2' style='width:25px' value='<s:property value='weight2'/>'/>
		        				</span>%)
		        				</label>
		        			</td><td  align="left"  name="xiangmu" group='<s:property value='fenlei'/>_xiangmu' mergerName='<s:property value='inspectBSC.kpiItem.keyName'/>' order="<s:property value="columnMap['bsc.xiangmu'].disOrder"/>">
		        				<input type="hidden" name='kpiItem_id' size='1' value='<s:property value='inspectBSC.kpiItem.id'/>'/>
		        				<span>
		        					<s:property value='inspectBSC.kpiItem.keyName'/>
		        				</span>
		        				<label style="color: #e22c2c">(
		        				<span name='tdShow'>
		        					<s:property value='inspectBSC.weight3'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input name='weight3' style='width:25px' value='<s:property value='inspectBSC.weight3'/>'/>
		        				</span>%)
		        				</label>
		        			</td><td align="right" name="score">
		        				<s:property value='score'/>
		        			</td>
		        			<td align="right" name="money1">
		        				<s:property value='money1'/>
		        			</td><td align="right" name="money2">
		        				<s:property value='money2'/>
		        			</td>
		        			<td align="right" name="dscore">
		        				<s:property value='dscore'/>
		        			</td><td style="font-weight: normal">
		        				<s:property value='operator.name'/><br>
		        				<s:date name="operateDate" format="yyyy-MM-dd hh:mm:ss"/>
		        			</td><td align="left" style="font-weight: normal">
		        			<span name="shengluespan" style="overflow: hidden; text-overflow:ellipsis;display:block">
		        					<NOBR>
		        				<s:property value='remark'/>
		        				</NOBR>
		        			</span>
		        			</td><td style="font-weight: normal">
		        				<s:property value='operator1.name'/>
		        				<br>
		        				<s:date name="operateDate1" format="yyyy-MM-dd hh:mm:ss"/>
		        			</td><td align="left" style="font-weight: normal">
		        				<span name="shengluespan" style="overflow: hidden; text-overflow:ellipsis;display:block">
		        					<NOBR><s:property value='remark2'/></NOBR>
		        				</span>
		        			</td><td align="left" name="toEdit">
		        				<span name="shengluespan" style="overflow: hidden; text-overflow:ellipsis;display:block">
		        					<NOBR><s:property value='remark3'/></NOBR>
		        				</span>
		        				<span name="toEdit" style="display:none">
		        					<s:textarea cssClass="fullInputtext" name="remark3" value="%{remark3}"></s:textarea>
		        				</span>
		        			</td>
		        		</tr>
					</s:iterator>	
				</table>
			</form>
			</div>
		</div>
		
	</div>
</div>