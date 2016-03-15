
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">

function reloadInspectBSC(type){
	var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
	if(type=='init'){
		 var selectedKpi = jQuery("#selectedKpi").val();
		 $.ajax({
			    url: 'getSelectedKpiInfo?inspectTemplId='+inspectTemplId+'&kpiItemsString='+selectedKpi,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        //jQuery('#name').attr("value",data.responseText);
			    	 alert("error"+data);
			    },
			    success: function(data){
			    	jQuery("#balabceCardTempl").load("inspectBSC?inspectTemplId="+inspectTemplId);
			    	mergerTable();
			    	fillHideTd();
		    	}
		});
	}else{
		jQuery("#balabceCardTempl").load("inspectBSC?inspectTemplId="+inspectTemplId);
		mergerTable();
		fillHideTd();
	}
	initParam();
}

 function mergerTable(){
	 var gridName = "inspectBSC_defTable";
	 setTimeout(function(){
		 mergerTableCol(gridName, 'weidus');
		 mergerTableCol(gridName, 'fenlei');
		 
	 },100);
 }
 function fillHideTd(){
	 jQuery("#inspectBSC_defTable").find("input[name=weight1]").blur(function(){
		 
		 //var rowspan = jQuery(this).parent().parent().attr("rowspan");
		 var thisTd = jQuery(this);
		 var mergerName;
		 
		 while(true){
			 if(thisTd.parent().is("td")){
				 mergerName = thisTd.parent().attr("mergerName");
				 break;
			 }else{
				 thisTd = thisTd.parent();
			 }
		 }
		 //var mergerName = jQuery(this).parent().parent().parent().attr("mergerName");
		 var value = jQuery(this).val();
		 jQuery("#inspectBSC_defTable").find("td[mergerName="+mergerName+"]").each(function(){
			 jQuery(this).find("input[name=weight1]:first").val(value);
		 });
		/*  var thisIndex = jQuery(this).index();
		 if(rowspan>1){
			 for(var rs=1;rs<rowspan;rs++){
				 jQuery("input[name=weight1]").eq(thisIndex+rs).attr("value",jQuery(this).val());
			 }
		 } */
	 });
	 jQuery("#inspectBSC_defTable").find("input[name=weight2]").blur(function(){
		 var thisTd = jQuery(this);
		 var mergerName;
		 
		 while(true){
			 if(thisTd.parent().is("td")){
				 mergerName = thisTd.parent().attr("mergerName");
				 break;
			 }else{
				 thisTd = thisTd.parent();
			 }
		 }
		 //var mergerName = jQuery(this).parent().parent().parent().attr("mergerName");
		 var value = jQuery(this).val();
		 jQuery("td[mergerName="+mergerName+"]").each(function(){
			 jQuery(this).find("input[name=weight2]:first").val(value);
		 });
		 /* var rowspan = jQuery(this).parent().parent().attr("rowspan");
		 var thisIndex = jQuery(this).index();
		 alert(thisIndex);
		 if(rowspan>1){
			 for(var rs=1;rs<rowspan;rs++){
				 jQuery("input[name=weight2]").eq(thisIndex+rs).attr("value",jQuery(this).val());
			 }
		 } */
	 });
	 jQuery("select[name=type]").change(function(){
		 var typeSelected = jQuery(this).val();
		 jQuery(this).parent().prev().attr("value",typeSelected);
	 });
	 jQuery("#inspectBSC_defTable").find("input[name=weight1],input[name=weight2],input[name=weight3]").change(function(){
		var inputValue=jQuery(this).val();
		var paten = /^\d+(\.\d+)?$/;
		//jQuery(this).val(inputValue.replace(/\D/g,""));
		if(!paten.exec(inputValue)){
			jQuery(this).val("");
		}
	});

 }
 var tdArr = new Array();
 var editRow = "";
 jQuery(document).ready(function(){
	 setTimeout(function(){
		 mergerTable();
	 },10);
	 fillHideTd();
	 sortTable();
	 hideColumn();
	 jQuery("span[name=tdShow]").hover(function(e){
	 	var left = e.pageX;
	 	var top = e.pageY;
	 	jQuery('body').append("<div id='tdShowPop' style='text-align:left;word-wrap: break-word;word-break: normal;position:absolute;z-index:200;border:1px gray solid;padding:3px;max-width:300px;background-color:snow;border-radius: 3px;'>"+jQuery(this).text()+"</div>");
	 	jQuery("#tdShowPop").css({ top: top+10 ,left: left+10  });
	 },function(e){
	 	jQuery("#tdShowPop").remove();
	 });
	 
	 jQuery("span[name=zbShow]").hover(function(e){
		 	var left = e.pageX;
		 	var top = e.pageY;
		 	jQuery('body').append("<div id='tdShowPop' style='text-align:left;word-wrap: break-word;word-break: normal;position:absolute;z-index:200;border:1px gray solid;padding:3px;max-width:300px;background-color:snow;border-radius: 3px;'>"+jQuery(this).text()+"</div>");
		 	jQuery("#tdShowPop").css({ top: top+10 ,left: left+10  });
		 },function(e){
		 	jQuery("#tdShowPop").remove();
		 });
	 //addTableSelect();
	/*  jQuery("#bscTitle").mousedown(function(e){
		 if(e.which==3){
			 alert();
		 }
	 }); */
	 gridResize(null,null,'inspectBSC_defTable','single');
	 
	 jQuery("td[name=rules]").find("span[name=tdShow]").each(function(){
		 
		 var thisText = jQuery(this).text();
		 thisText = thisText.replace(/<[^>]+>/g,"");
		 thisText = thisText.replace(/&nbsp;/g,"");
		 thisText = thisText.replace(/\r\n/g, "").replace(/\n/g, "").replace(/\t/g, "").replace(/(^\s*)|(\s*$)/g, "");
		 jQuery(this).html("<NOBR>"+thisText+"</NOBR>");
		 jQuery(this).parent().find("textarea[name=rules_show]").val(thisText); 
	 });
	 var initTop = 0;
	 jQuery("#inspectBSC_defTable_pageContent").scroll(function(e){
			 var $thisInput = jQuery("#inspectBSC_defTable").find("textarea:focus");
			 var $thisName = $thisInput.attr("name");
			 var index = jQuery("#inspectBSC_defTable").find("textarea[name="+$thisName+"]").index($thisInput);
			 var initTopTemp = jQuery("#inspectBSC_defTable_pageContent").scrollTop();
			 if(initTopTemp>initTop){
				 index += 1;
			 }else{
				 index -= 1;
			 }
			 jQuery("#inspectBSC_defTable").find("textarea[name="+$thisName+"]").eq(index).focus();
			 setTimeout(function(){
				 initTop = jQuery("#inspectBSC_defTable_pageContent").scrollTop();
			 },100);
	 });
	 
	 jQuery("#confirmDetailButton").unbind("click").bind("click",function(){
		 
		 var sHtml = detailEditor.getSource();
		 sHtml = sHtml.replace(/\"/g,"&quot;");
		 /* alert(sHtml);
		 
		 sHtml = sHtml.replace(/\"/g,"&quot;");
		 alert(sHtml); */
		 var sText = sHtml.replace(/<[^>]+>/g,"");
		 sText = sText.replace(/&nbsp;/g,"");
		 sText = sText.replace(/\r\n/g, "").replace(/\n/g, "").replace(/\t/g, "");
		 
		 jQuery(jQuery("#htmlFiled").val()).val(sHtml);
		 jQuery(jQuery("#textFiled").val()).val(sText);
		 
		 jQuery("#htmlFiled").val("");
		 jQuery("#textFiled").val("");
		 
		 $.pdialog.closeCurrentDiv("editRuleDiv");
	 });
	 
	 var detailEditor = null;
	 var editRuleFuction = function($this){
		 var textId = $this.attr("name")+Math.floor(Math.random()*1000);
		 $this.attr("id",textId);
		 var htmlId = $this.next().attr("name")+Math.floor(Math.random()*1000);
		 $this.next().attr("id",htmlId);
		 //$.pdialog.open('editDetailMsg?htmlField='+htmlId+'.val&textField='+textId+'.text', 'editDetailMsg', "编辑详细信息", {mask:true,width:480,height:420});　
		 
		 jQuery("#htmlFiled").val("#"+htmlId);
		 jQuery("#textFiled").val("#"+textId);
		 
		 if(detailEditor!=null){
			 $('#detailEditor').xheditor(false);
		 }
		 
		 //detailEditor.setSource($this.find("span[name=tdEdit]").find("input[type=hidden]").val());
		 
		 var url = "#DIA_inline?inlineId=editRuleDiv";
		 $.pdialog.open(url, 'editDetailMsg', "编辑详细信息", {
				mask:true,width:480,height:420
		 });
		 detailEditor=$('#detailEditor').xheditor({tools:'full',skin:'vista'});
		 var $thisHtml = $this.next().val()
		 $thisHtml = $thisHtml.replace(/&quot;/g,"\"");
		 detailEditor.setSource($thisHtml);
		 
	 }
	 
	 var showRuleFuction = function($this){
		 var showValue = $this.find("span[name=tdEdit]").find("input[type=hidden]").val();
		 showValue = showValue.replace(/&quot;/g,"\"");
		 jQuery("#showRuleDiv").html("<div class='pageContent' layoutH=1>"+showValue+"</div>");
		 var url = "#DIA_inline?inlineId=showRuleDiv";
			$.pdialog.open(url, 'showDetailMsg', "显示详细信息", {
				mask:true,width:480,height:420
		 });
			
		 //$.pdialog.open('showDetailMsg?showValue='+showValue, 'showDetailMsg', "显示详细信息", {mask:true,width:480,height:420});　
	 }
	 jQuery("#inspectBSC_defTable").find("textarea[name='rules_show']").each(function(){
			jQuery(this).detailButton({
				type:"click",
				hostAction:"dblclick",
				dealFuction:editRuleFuction
			});
		});
	 jQuery("#inspectBSC_defTable").find("td[name='rules']").each(function(){
		jQuery(this).detailButton({
			type:"hover",
			dealFuction:showRuleFuction
		});
	});
	 var jjScoreDecimalPlaces = parseInt(jQuery("#jjScoreDecimalPlaces").val());
	jQuery("#inspectBSC_defTable").find("td[name='score']").each(function(){
		var textSpan = jQuery(this).find("span[name='tdShow']");
		var fixedValue = parseFloat(textSpan.text());
		textSpan.text(fixedValue.toFixed(jjScoreDecimalPlaces));
		jQuery(this).find("span[name='tdEdit']>input:first").val(fixedValue.toFixed(jjScoreDecimalPlaces));
	});
	 /* setTimeout(function(){
			jQuery("textarea[name='rules_show']").each(function(){
	  			jQuery(this).detailButton({
	  				type:"input",
	  				dealFuction:editRuleFuction
	  			});
	  		});
			jQuery("td[name='rules']").each(function(){
	  			jQuery(this).detailButton({
	  				type:"label",
	  				dealFuction:showRuleFuction
	  			});
	  		});
		},300); */
}); 

function sortTable(){
	jQuery("#inspectBSC_defTable").find("tr").each(function(){
		jQuery(this).find("td").each(function(){
			var tdOrder = jQuery(this).attr("order");
			if(tdOrder){
				//alert(tdArr);
				tdArr[parseInt(tdOrder)-1] = jQuery(this).clone(true);
			}
		});
		jQuery(this).empty();
		for(var tdi=0;tdi<tdArr.length;tdi++){
			jQuery(this).append(tdArr[tdi]);
		}
	});
}

function hideColumn(){
	jQuery("#inspectBSC_defTable").find("td[show=false]").hide();
}

function showDeptTree(obj){
	var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,internalCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
		sql += " ORDER BY orderCol ";
	jQuery(obj).treeselect({
		optType:"multi",
		dataType:"sql",
		sql:sql,
		exceptnullparent:true,
		lazy:true
	});
}
function addDeptTree(){
	/* jQuery("input[name=department_name]").each(function(){
		var nameId = jQuery(this).attr("id");
		var idId = "department_id_"+nameId.split("_")[2];
		addTreeSelect("list","sync",nameId,idId,"single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
	}); */
	jQuery("input[name=department_name]").click(function(){
		var thisId = jQuery(this).attr("id");
		
		if(jQuery("#"+thisId+'_treediv').length>0){
			jQuery("#"+thisId+'_treediv').remove();
		}
		var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,internalCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
			sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
			sql += " ORDER BY orderCol ";
		jQuery(this).treeselect({
			optType:"multi",
			dataType:"sql",
			sql:sql,
			exceptnullparent:true,
			lazy:false,
			showImmediate:true
		});
	});
	/*jQuery("input[name=department_name]").click(function(){
		//jQuery(this).attr("inited");
		//var nameId = jQuery(this).attr("id");
		//var idId = "department_id_"+nameId.split("_")[2];
		jQuery(this).treeselect({
			optType:"multi",
			sql:"SELECT personId id,name,dept_id parent, '-1' internalCode, '0' isparent FROM t_person where personId<>'XT' and disabled=0  UNION SELECT deptId id,name,'1' parentId ,internalCode, '1' isparent FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode ",
			exceptnullparent:true
		}).showTree();
		//addTreeSelect("list","sync",nameId,idId,"single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
	});*/
}
function addTableSelect(){
	var at=0;
	var clickBc;
	jQuery("#inspectBSC_defTable").find("tr").each(function(){
		//alert(at);
		if(at!=0){
			var bc = jQuery(this).css("background-color");
			jQuery(this).click(function(){
				var isClicked = jQuery(this).attr("isClicked");
				if(!isClicked){
					clickBc = jQuery(this).css("background-color");
					jQuery(this).css("background-color","#fbec84");
					jQuery(this).attr("isClicked","true");
					var trIndex = jQuery(this).index();
					jQuery("#inspectBSC_defTable").find("tr").each(function(){
						if(jQuery(this).index()!=trIndex){
							var isClicked = jQuery(this).attr("isClicked");
							if(isClicked){
								jQuery(this).css("background-color",clickBc);
								jQuery(this).removeAttr("isClicked");
							}
						}
					});
				}else{
					jQuery(this).css("background-color",bc);
					jQuery(this).removeAttr("isClicked");
				}
			});
			jQuery(this).mouseover(function(){
				var isClicked = jQuery(this).attr("isClicked");
				if(!isClicked){
					jQuery(this).css("background-color","#d3e6f5");
				}
			});
			jQuery(this).mouseout(function(){
				var isClicked = jQuery(this).attr("isClicked");
				if(!isClicked){
					jQuery(this).css("background-color",bc);
				}
			});
		}else{
			at++;
		}
	});
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
    padding: 0px;
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
</style>
<div id="inspectBSC_defTable_page" class="page">
	<div id="inspectBSC_defTable_pageContent" class="pageContent">
			<input type="hidden" id="selectedKpi"/>
			<div style="vertical-align: middle;text-align: center;margin-top:5px; ">
				<label style="font-size:16px;font-weight: bold;"><s:property value="inspectTempl.inspectModelName"/>（<s:property value="inspectBSCs.size"/>个指标）</label>
				<div style="margin-bottom:10px;margin-left:10px">
					<div style="">
						<label style="float: left;">考核类别：<s:property value="inspectTempl.inspecttype"/></label>
						<label style="float: left;margin-left:20px">绩效科室类别：<s:property value="inspectTempl.jjdepttype.khDeptTypeName"/></label>
					</div>
					<br/>
					<div style="margin-top:5px;margin-bottom:5px;">
						<label style="float: left">考核周期：<s:property value="inspectTempl.periodType"/></label>
						<label style="float: left;margin-left:30px">总分值：<span id="totalScore"><s:property value="inspectTempl.totalScore"/></span></label>
					</div>
				</div>
				<div id="inspectBSC_defTable_div" style="margin-top:20px;" tablecontainer="inspectTempl_layout-south" extraHeight=118 extraWidth=20>
				<form id="bscForm">
				<table class="bsc" id="inspectBSC_defTable" >
					<tr id="bscTitle" style="height:35px">
							<td class="bscTh" width="<s:property value="columnMap['bsc.weidu'].width"/>%" order="<s:property value="columnMap['bsc.weidu'].disOrder"/>" show="<s:property value="columnMap['bsc.weidu'].status"/>">
								<s:text name="bsc.weidu"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.fenlei'].width"/>%" order="<s:property value="columnMap['bsc.fenlei'].disOrder"/>" show="<s:property value="columnMap['bsc.fenlei'].status"/>">
								<s:text name="bsc.fenlei"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.xiangmu'].width"/>%" order="<s:property value="columnMap['bsc.xiangmu'].disOrder"/>" show="<s:property value="columnMap['bsc.xiangmu'].status"/>">
								<s:text name="bsc.xiangmu"></s:text>
							</td>
							<!-- <td width="8%">
								权重（%）
							</td> -->
							<td class="bscTh" width="<s:property value="columnMap['bsc.score'].width"/>%" order="<s:property value="columnMap['bsc.score'].disOrder"/>" show="<s:property value="columnMap['bsc.score'].status"/>">
								<s:text name="bsc.score"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.rules'].width"/>%" order="<s:property value="columnMap['bsc.rules'].disOrder"/>" show="<s:property value="columnMap['bsc.rules'].status"/>">
								<s:text name="bsc.rules"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.requirement'].width"/>%" order="<s:property value="columnMap['bsc.requirement'].disOrder"/>" show="<s:property value="columnMap['bsc.requirement'].status"/>">
								<s:text name="bsc.requirement"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.pattern'].width"/>%" order="<s:property value="columnMap['bsc.pattern'].disOrder"/>" show="<s:property value="columnMap['bsc.pattern'].status"/>">
								<s:text name="bsc.pattern"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.type'].width"/>%" order="<s:property value="columnMap['bsc.type'].disOrder"/>" show="<s:property value="columnMap['bsc.type'].status"/>">
								<s:text name="bsc.type"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.dept'].width"/>%" order="<s:property value="columnMap['bsc.dept'].disOrder"/>" show="<s:property value="columnMap['bsc.dept'].status"/>">
								<s:text name="bsc.dept"></s:text>
							</td>
							<td class="bscTh" width="<s:property value="columnMap['bsc.remark'].width"/>%" order="<s:property value="columnMap['bsc.remark'].disOrder"/>" show="<s:property value="columnMap['bsc.remark'].status"/>">
								<s:text name="bsc.remark"></s:text>
							</td>
							<!-- <td width="8%">
								停用
							</td> -->
						</tr>
						<s:iterator value="inspectBSCs" status="it">
						<s:if test="#it.odd==true">
							<tr>
						</s:if>
						<s:else>
							<tr class="evenTd">
						</s:else>
							<%-- <td  style='display:none'>
								<span name='tdShow'>
									<s:property value='inspectContentId'/>
								</span>
								<span name='tdEdit' style='display:none'>
									<input name='inspectContentId' class='fullInput' value='<s:property value='inspectContentId'/>'/>
								</span>
							</td> --%>
		        			<td style="background-color: white;color: #01619c" name='weidus' group='<s:property value='weidus'/>' mergerName='<s:property value='weidus'/>' order="<s:property value="columnMap['bsc.weidu'].disOrder"/>" show="<s:property value="columnMap['bsc.weidu'].status"/>">
		        				<input type="hidden" name='inspectContentId' class='fullInput' value='<s:property value='inspectContentId'/>'/>
		        				<span name="zbShow" style="overflow: hidden; text-overflow:ellipsis;display:block;line-height: 20px">
		        					<s:property value='weidus'/>
		        				</span>
		        				<br/>
		        				<label style="color: #e22c2c">(
		        				<span name='tdShow'>
		        					<s:property value='weight1'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input name='weight1' style='width:32px' value='<s:property value='weight1'/>'/>
		        				</span>%)
		        				</label>
		        			</td>
		        			<td style="background-color: white;color: #048b01" name='fenlei' group='<s:property value='weidus'/>_fenlei' mergerName='<s:property value='fenlei'/>' order="<s:property value="columnMap['bsc.fenlei'].disOrder"/>" show="<s:property value="columnMap['bsc.fenlei'].status"/>">
		        				<span name="zbShow" style="overflow: hidden; text-overflow:ellipsis;display:block;line-height: 20px">
		        					<s:property value='fenlei'/>
		        				</span>
		        				<br/>
		        				<label style="color: #e22c2c">(
		        				<span name='tdShow'>
		        					<s:property value='weight2'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input name='weight2' style='width:32px' value='<s:property value='weight2'/>'/>
		        				</span>%)
		        				</label>
		        			</td>
		        			<%-- <td style='display:none'>
		        				<input name='kpiItem_id' size='1' value='<s:property value='kpiItem.id'/>'/>
		        			</td> --%>
		        			<td name="xiangmu" group='<s:property value='fenlei'/>_xiangmu' mergerName='<s:property value='kpiItem.keyName'/>' order="<s:property value="columnMap['bsc.xiangmu'].disOrder"/>" show="<s:property value="columnMap['bsc.xiangmu'].status"/>">
		        				<input type="hidden" name='kpiItem_id' size='1' value='<s:property value='kpiItem.id'/>'/>
		        				<span name="zbShow" style="overflow: hidden; text-overflow:ellipsis;display:block;line-height: 20px">
		        					<NOBR><s:property value='kpiItem.keyName'/></NOBR>
		        				</span>
		        				<label style="color: #e22c2c">(
		        				<span name='tdShow'>
		        					<s:property value='weight3'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input name='weight3' style='width:32px' value='<s:property value='weight3'/>'/>
		        				</span>%)
		        				</label>
		        			</td>
		        			<td align="right" name="score" style="padding-right:2px;color: #e22c2c" name='score' order="<s:property value="columnMap['bsc.score'].disOrder"/>" show="<s:property value="columnMap['bsc.score'].status"/>">
		        				<span name='tdShow'>
		        					<s:property value='score'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input name='score' class='fullInput' value='<s:property value='score'/>'/>
		        				</span>
		        			</td>
		        			<td align="left" name="rules" order="<s:property value="columnMap['bsc.rules'].disOrder"/>" style="font-weight: normal;" show="<s:property value="columnMap['bsc.rules'].status"/>">
		        				<span name='tdShow' style="overflow: hidden; text-overflow:ellipsis;display:block">
		        					<NOBR><s:property escapeHtml="true" value='rules'/></NOBR>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<textarea class='fullInputtext needsave' name='rules_show' readonly="readonly"><s:property escapeHtml="false" value='rules'/></textarea>
		        					<input type="hidden" name="rules" value='<s:property value="rules"/>'/>
		        				</span>
		        			</td>
		        			<td align="left" order="<s:property value="columnMap['bsc.requirement'].disOrder"/>" style="font-weight: normal;" show="<s:property value="columnMap['bsc.requirement'].status"/>">
		        				<span name='tdShow' style="overflow: hidden; text-overflow:ellipsis;display:block">
		        					<NOBR><s:property value='requirement'/></NOBR>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<s:textarea name='requirement' cssClass='fullInputtext' theme="simple">
		        					</s:textarea>
		        				</span>
		        			</td>
		        			<td align="left" order="<s:property value="columnMap['bsc.pattern'].disOrder"/>" style="font-weight: normal;" show="<s:property value="columnMap['bsc.pattern'].status"/>">
		        				<span name='tdShow' style="overflow: hidden; text-overflow:ellipsis;display:block">
		        					<NOBR><s:property value='pattern'/></NOBR>
		        				</span><span name='tdEdit' style='display:none'>
		        					<s:textarea name='pattern' cssClass='fullInputtext' theme="simple">
		        					</s:textarea>
		        				</span>
		        			</td>
		        			<td order="<s:property value="columnMap['bsc.type'].disOrder"/>" show="<s:property value="columnMap['bsc.type'].status"/>">
		        				<span name='tdShow'>
									<s:property value='type'/>
		        				</span>
		        				<input type='hidden' name='type' value='<s:property value='type'/>'/>
		        				<span name='tdEdit' style='display:none'>
		        					<select name='type'>
		        						<option value='计算'>计算</option>
		        						<option value='手工'>手工</option>
		        					</select>
		        				</span>
		        			</td>
		        			<%-- <td style='display:none'>
		        				<input name='department_id' class='fullInput' value='<s:property value='department.id'/>'/>
							</td> --%>
		        			<td order="<s:property value="columnMap['bsc.dept'].disOrder"/>" show="<s:property value="columnMap['bsc.dept'].status"/>">
		        				<input type="hidden" id="department_name_<s:property value='#it.index'/>_id" name='department_id' class='fullInput' value='<s:property value='department.departmentId'/>'/>
		        				<span name='tdShow'>
		        					<s:property value='department.name'/>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<input id="department_name_<s:property value='#it.index'/>" name="department_name" class='fullInputtext' value='<s:property value='department.name'/>'/>
		        				</span>
		        			</td>
		        			<td align="left" name='remark' order="<s:property value="columnMap['bsc.remark'].disOrder"/>" style="font-weight: normal;"show="<s:property value="columnMap['bsc.remark'].status"/>">
		        				<span name='tdShow' style="overflow: hidden; text-overflow:ellipsis;display:block">
		        					<NOBR><s:property value='remark'/></NOBR>
		        				</span>
		        				<span name='tdEdit' style='display:none'>
		        					<s:textarea name='remark' cssClass='fullInputtext'>
		        					</s:textarea>
		        				</span>
		        			</td>
		        		</tr>
					</s:iterator>	
				</table>
			</form>
			</div>
		</div>

<div id="editRuleDiv" style="display: none">
	<div class="pageContent">
			<div class="pageFormContent"  layoutH="56">
					<input type="hidden" id="htmlFiled"/>
					<input type="hidden" id="textFiled"/>
					<div style="margin:0;">
						<s:textarea id="detailEditor" name="byLaw.body" rows="20" cols="60"></s:textarea>
					</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="confirmDetailButton">确定</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" class="close"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	</div>
</div>	
<div id="showRuleDiv" style="display: none">

</div>
	</div>
</div>
<%-- <div id="bscColumnDiv" style="display:none" class="page">
			<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
				<li><a  class="addbutton" href="javaScript:selectStatus()" ><span>选择
					</span>
				</a>
				</li>
				<li><a class="delbutton"  href="javaScript:sortStatus()"><span>排序</span>
				</a>
				</li>
			
			</ul>
			</div>
				<form id="inspectTemplForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
					<div class="pageFormContent sortDrag" layoutH="80">
					<s:iterator value="columnMap">
						<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">
						<input id="${key}" onclick="checkboxSelect()" type="checkbox" name="columnCheckBox" <s:if test="value==true">checked</s:if>/>
						<s:text name="%{key}"></s:text><br/>
						</div>
					</s:iterator>
				</div>
				</form>
				<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveColumnSet"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			</div>
</div> --%>