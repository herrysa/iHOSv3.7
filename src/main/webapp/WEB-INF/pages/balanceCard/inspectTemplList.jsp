
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
	function inspectTemplGridReload(){
		var urlString = "inspectTemplGridList";
		var inspectTempl_inspectModelId_search = jQuery("#inspectTempl_inspectModelId_search").val();
		var inspectTempl_inspectModelNo_search = jQuery("#inspectTempl_inspectModelNo_search").val();
		var inspectTempl_inspectModelName_search = jQuery("#inspectTempl_inspectModelName_search").val();
		var inspectTempl_inspecttype_search = jQuery("#inspectTempl_inspecttype_search").val();
		var inspectTempl_jjdepttype_search = jQuery("#inspectTempl_jjdepttype_search").val();
		var inspectTempl_periodType_search = jQuery("#inspectTempl_periodType_search").val();
		var inspectTempl_disabled_search = jQuery("#inspectTempl_disabled_search").val();
		/* if(inspectTempl_disabled_search){
			inspectTempl_disabled_search = true;
		}else{
			inspectTempl_disabled_search = false;
		} */
	
		urlString=urlString+"?filter_LIKES_inspectModelId="+inspectTempl_inspectModelId_search+"&filter_LIKES_inspectModelNo="+inspectTempl_inspectModelNo_search+"&filter_LIKES_inspectModelName="+inspectTempl_inspectModelName_search+"&filter_EQS_inspecttype="+inspectTempl_inspecttype_search
							+"&filter_EQS_jjdepttype.khDeptTypeId="+inspectTempl_jjdepttype_search+"&filter_LIKES_periodType="+inspectTempl_periodType_search+"&filter_EQB_disabled="+inspectTempl_disabled_search;
		//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#inspectTempl_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var inspectTemplLayout;
	var inspectTemplGridIdString="#inspectTempl_gridtable";
	
	jQuery(document).ready(function() { 
		jQuery("#jjScoreDecimalPlaces").val(jjScoreDecimalPlaces);
		var inspectTemplChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				inspectTemplLayout.closeSouth();
				return;
			}
			var newSelectedSearchId = selectedSearchId[selectedSearchId.length-1];
			var showBSCUrl = "inspectBSC?inspectTemplId="+newSelectedSearchId;
			showBSCUrl = encodeURI(showBSCUrl);
    		jQuery("#balabceCardTempl").load(showBSCUrl);
    		$("#background,#progressBar").hide();
    		initParam();
    	};
    	
    	inspectTemplLayout = makeLayout({'baseName':'inspectTempl','tableIds':'inspectTempl_gridtable;inspectBSC_defTable','proportion':2,'key':'inspectModelId'},inspectTemplChangeData);
var inspectTemplGrid = jQuery(inspectTemplGridIdString);
    inspectTemplGrid.jqGrid({
    	url : "inspectTemplGridList",
    	editurl:"inspectTemplGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'inspectModelId',index:'inspectModelId',align:'left',label : '<s:text name="inspectTempl.inspectModelId" />',hidden:false,key:true,width:70},				
{name:'inspectModelNo',index:'inspectModelNo',align:'left',label : '<s:text name="inspectTempl.inspectModelNo" />',hidden:false,width:80},				
{name:'inspectModelName',index:'inspectModelName',align:'left',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false,width:150},				
{name:'inspecttype',index:'inspecttype',align:'center',label : '<s:text name="inspectTempl.inspecttype" />',hidden:false,width:70},				
{name:'jjdepttype.khDeptTypeName',index:'jjdepttype.khDeptTypeName',align:'center',label : '<s:text name="inspectTempl.jjdepttype" />',hidden:false,width:80},				
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="inspectTempl.periodType" />',hidden:false,width:70},				
{name:'departmentNames',index:'departmentNames',align:'left',label : '<s:text name="inspectTempl.departmentNames" />',hidden:false,sortable:false},				
/* {name:'personNames',index:'personNames',align:'center',label : '<s:text name="inspectTempl.personNames" />',hidden:false},				 */
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="inspectTempl.disabled" />',hidden:false,formatter:'checkbox',width:60},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="inspectTempl.remark" />',hidden:false,width:80,formatter:stringFormatter}				

        ],
        jsonReader : {
			root : "inspectTempls", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'inspectModelId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="inspectTemplList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		ondblClickRow:function(){
			inspectTemplLayout.optDblclick();
		},
		onSelectRow: function(rowid) {
        	setTimeout(function(){
        		inspectTemplLayout.optClick();
        	},100);
       	},
		 gridComplete:function(){
			 gridContainerResize("inspectTempl","div");
          /*  if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"inspectTempl_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("inspectTempl_gridtable");
       	} 

    });
    jQuery(inspectTemplGrid).jqGrid('bindKeys');
	
  	});
	function templDisable(){
		var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
		var templRow = jQuery("#inspectTempl_gridtable").jqGrid('getRowData',inspectTemplId);
		var disabled = templRow['disabled'];
		var passFlag = false;
		if(disabled=="Yes"){
			passFlag =  true;
			/* jQuery.ajax({
			    url: 'hasDeptInspect',
			    type: 'post',
			    data:{inspectModelId:inspectTemplId},
			    dataType: 'json',
			    async:false,
			    error: function(data){
			        
			    },
			    success: function(data){
			        // do something with xml
			    	if(data.message!="error"&&data.message=="1"){
						alertMsg.error("该模板下有考核表存在，不能修改！");
						passFlag =  false;
					}else if(data.message=="error"){
						alertMsg.error("数据错误！");
						passFlag =  false;
					}else{
						passFlag = true;
					}
			    }
			}); */
		}else{
			alertMsg.error("模板正在使用中，不能修改！");
			passFlag =  false;
		}
		return passFlag;
	}
	function editInspectTempl(){
		if(templDisable()){
			var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
			if(inspectTemplId==null || inspectTemplId.length ==0){
				alertMsg.error("请选择记录！");
				return;
			}
		    if(inspectTemplId.length>1){
				alertMsg.error("只能选择一条记录。");
				return;
			}else{
				var url = "editInspectTempl?popup=true&inspectModelId="+inspectTemplId+"&navTabId=inspectTempl_gridtable";
				
				var winTitle="<s:text name='inspectTemplEdit.title'/>";
				url = encodeURI(url);
				$.pdialog.open(url, 'editInspectTempl', winTitle, {mask:false,width:550,height:500});　
			}
		}
	}
	function selectKpiItem(){
		if(templDisable()){
			var sid = jQuery(inspectTemplGridIdString).jqGrid('getGridParam','selarrrow');
			if(sid.length>1){
				alertMsg.error("只能选择一条记录。");
				return;
			}else{
			var row = jQuery(inspectTemplGridIdString).jqGrid('getRowData',sid);
			var periodType = row['periodType'];
			var winTitle="选择指标";
			//alert(url);
			var url = "selectedKpis?periodType="+periodType+"&inspectTemplId="+sid;
			url = encodeURI(url);
			$.pdialog.open(url, 'selectKpiItem', winTitle, {mask:false,width:800,height:600});
			}
		}
	}
	
	
	function editBSC(){
		if(templDisable()){
		addDeptTree();
		jQuery("select[name=type]").each(function(){
			var typeVal = jQuery(this).parent().prev().val();
			jQuery(this).val(typeVal);
		});
		jQuery("span[name=tdEdit]").show();
		/* jQuery("td:has(.fullInputtext)").each(function(){
			jQuery(this).find(".fullInputtext:first").resize();
			//jQuery(this).find(".fullInputtext:first").width(jQuery(this).width()*0.90);
			//jQuery(this).find(".fullInputtext:first").height(35);
		}); */
		jQuery("span[name=tdShow]").hide();
		showSOrWEdit(true);
		rOrW = 'w';
		jQuery("#editSW").find("span").eq(0).text("编辑分值");
		jQuery("#computeSW").find("span").eq(0).text("计算分值");
		sOrW = "w";
		jQuery("td[name='rules']").unbind();
		}
	}
	function cancelBSC(){
		//jQuery("span[name=tdShow]").show();
		//jQuery("span[name=tdEdit]").hide();
		if(rOrW=='r'){
			alertMsg.error("当前未在编辑状态下！");
			return ;
		}
		rOrW = 'r';
		computeStatue = false;
		reloadInspectBSC('relaod');
	}
	var inspectBSCAttr = ["inspectContentId",
						  "weight1",
						  "weight2",
						  "kpiItem_id",
						  "weight3",
						  "score",
						  "rules",
						  "requirement",
						  "pattern",
						  "type",
						  "department_id",
						  "remark"
						  ];
	function saveBSC(){
		if(rOrW=='r'){
			alertMsg.error("请在编辑状态下保存！");
			return ;
		}
		if(!computeStatue){
			if(sOrW=="w"){
				alertMsg.error("请计算分值！");
			}else{
				alertMsg.error("请计算权重！");
			}
			return ;
		}else{
			if(sOrW=="w"&&!checkWeight()){
				return ;
			}else if(sOrW=="s"&&!checkScore()){
				return ;
			}
		}
		var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
		var inspectBSCsValue = "";
		var inspectBSCNum = jQuery("input[name=inspectContentId]").length;
		for(var ia=0;ia<inspectBSCAttr.length;ia++){
			inspectBSCsValue += inspectBSCAttr[ia]+"::";
			jQuery("input[name="+inspectBSCAttr[ia]+"]").each(function(){
				if(inspectBSCAttr[ia]=='type'&&!jQuery(this).val()){
					jQuery(this).val(jQuery(this).next().children().val());
				}
				inspectBSCsValue += jQuery(this).val()+",";
			});
			if(inspectBSCAttr[ia]=='requirement'||inspectBSCAttr[ia]=='pattern'||inspectBSCAttr[ia]=='remark'){
				jQuery("textarea[name='"+inspectBSCAttr[ia]+"']").each(function(){
					inspectBSCsValue += jQuery(this).val()+",";
				});
			}
			inspectBSCsValue += " ";
			inspectBSCsValue += "@";
		}
		$.ajax({
		    url: "saveInspectBSCs",
		    data:{inspectTemplId:inspectTemplId,inspectBSCNum:inspectBSCNum,inspectBSCsString:inspectBSCsValue},
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        cancelBSC();
		        alert(data.message);
		    }
		});
		/* jQuery("#bscForm").attr("action","saveInspectBSCs");
		jQuery("#bscForm").submit(); */
	}
	var sOrW = "w",
		rOrW = "r",
		computeStatue = false;
	function initParam(){
		sOrW = "w";
		rOrW = "r";
		computeStatue = false;
	}
	function editScoreOrWeight(){
		if(rOrW=='r'){
			alertMsg.error("请在编辑状态下编辑！");
			return ;
		}
		if(sOrW=="w"){
			jQuery("#editSW").find("span").eq(0).text("编辑权重");
			jQuery("#computeSW").find("span").eq(0).text("计算权重");
			sOrW = "s";
		}else{
			jQuery("#editSW").find("span").eq(0).text("编辑分值");
			jQuery("#computeSW").find("span").eq(0).text("计算分值");
			sOrW = "w";
		}
		showSOrWEdit(true);
		computeStatue = false;
	}
	function showSOrWEdit(showOrHide){
		if(showOrHide){
			if(sOrW=="w"){
				jQuery("td[name=weidus]").each(function(){
					jQuery(this).find("span").eq(2).show();
				});
				jQuery("td[name=fenlei]").each(function(){
					jQuery(this).find("span").eq(2).show();
				});
				jQuery("td[name=xiangmu]").each(function(){
					jQuery(this).find("span").eq(2).show();
				});
				jQuery("td[name=score]").each(function(){
					jQuery(this).find("span").eq(0).show();
				});
				
				jQuery("td[name=weidus]").each(function(){
					jQuery(this).find("span").eq(1).hide();
				});
				jQuery("td[name=fenlei]").each(function(){
					jQuery(this).find("span").eq(1).hide();
				});
				jQuery("td[name=xiangmu]").each(function(){
					jQuery(this).find("span").eq(1).hide();
				});
				jQuery("td[name=score]").each(function(){
					jQuery(this).find("span").eq(1).hide();
				});
				
			}else{
				jQuery("td[name=weidus]").each(function(){
					jQuery(this).find("span").eq(2).hide();
				});
				jQuery("td[name=fenlei]").each(function(){
					jQuery(this).find("span").eq(2).hide();
				});
				jQuery("td[name=xiangmu]").each(function(){
					jQuery(this).find("span").eq(2).hide();
				});
				jQuery("td[name=score]").each(function(){
					jQuery(this).find("span").eq(0).hide();
				});
				
				jQuery("td[name=weidus]").each(function(){
					jQuery(this).find("span").eq(1).show();
				});
				jQuery("td[name=fenlei]").each(function(){
					jQuery(this).find("span").eq(1).show();
				});
				jQuery("td[name=xiangmu]").each(function(){
					jQuery(this).find("span").eq(1).show();
				});
				jQuery("td[name=score]").each(function(){
					jQuery(this).find("span").eq(1).show();
				});
			}
		}else{
			
		}
		
	}
	function computeScoreOrWeight(){
		if(rOrW=='r'){
			alertMsg.error("请在编辑状态下计算！");
			return ;
		}
		if(sOrW=="w"){
			if(!checkWeight()){
				return ;
			}
			var totalScore = jQuery("#totalScore").text();
			jQuery("td[name=score]").each(function(){
				var weiduWeight = jQuery(this).parent().find("td[name=weidus]").find("input[name=weight1]").val();
				var feileiWeight = jQuery(this).parent().find("td[name=fenlei]").find("input[name=weight2]").val();
				var xiangmuWeight = jQuery(this).parent().find("td[name=xiangmu]").find("input[name=weight3]").val();
				var ts = parseFloat(totalScore)*parseFloat(weiduWeight)*parseFloat(feileiWeight)*parseFloat(xiangmuWeight)/1000000;
				ts = ts.toFixed(jjScoreDecimalPlaces);
				//console.log(weiduWeight+","+feileiWeight+","+xiangmuWeight+","+ts);
				jQuery(this).find("span").eq(0).text(ts);
				jQuery(this).find("span").eq(1).find("input[name=score]").eq(0).val(ts);
			});
			var totalScoreAll = 0;
			jQuery("#inspectBSC_defTable").find("input[name=score]").each(function(){
				var scoreValue = jQuery(this).val();
				totalScoreAll += parseFloat(scoreValue);
			});
			totalScoreAll = totalScoreAll.toFixed(jjScoreDecimalPlaces);
			//console.log("ScoreAll:"+totalScoreAll);
			if(totalScoreAll!=totalScore){
				var adjustValue = 0;
				adjustValue = totalScoreAll-totalScore;
				adjustValue = toDecimal(adjustValue,jjScoreDecimalPlaces);
				//console.log("adjustValue:"+adjustValue);
				var maxWeidusValue = 0;
				var maxWeidus;
				jQuery("td[name=weidus]:visible").each(function(){
					var thisWeidus = parseInt(jQuery(this).find("span").eq(1).text());
					if(maxWeidusValue<thisWeidus){
						maxWeidusValue = thisWeidus;
						maxWeidus = jQuery(this);
					}
				});
				var maxWeiduName = maxWeidus.attr("mergerName");
				//alert(maxWeiduName);
				var maxFenleiValue = 0;
				var maxFenlei;
				jQuery("td[group="+maxWeiduName+"_fenlei]:visible").each(function(){
					var thisFenlei = parseInt(jQuery(this).find("span").eq(1).text());
					if(maxFenleiValue<thisFenlei){
						maxFenleiValue = thisFenlei;
						maxFenlei = jQuery(this);
					}
				});
				var maxXiangmuName = maxFenlei.attr("mergerName");
				//alert(maxXiangmuName);
				var maxXiangmuValue = 0;
				var maxXiangmu;
				jQuery("td[group="+maxXiangmuName+"_xiangmu]:visible").each(function(){
					var thisXiangmu = parseInt(jQuery(this).find("span").eq(1).text());
					if(maxXiangmuValue<thisXiangmu){
						maxXiangmuValue = thisXiangmu;
						maxXiangmu = jQuery(this);
					}
				}); 
				//alert(maxXiangmu.attr("mergerName"));
				var adjustScore = parseFloat(maxXiangmu.parent().find("input[name=score]").eq(0).val());
				var rsScore = (adjustScore-adjustValue).toFixed(jjScoreDecimalPlaces);
				//console.log("adjustScore:"+adjustScore);
				maxXiangmu.parent().find("td[name=score]:first").find("span").eq(0).text(rsScore)
				maxXiangmu.parent().find("input[name=score]").eq(0).val(rsScore);
			}
		}else{
			if(!checkScore()){
				return ;
			}
			var totalScore = jQuery("#totalScore").text();
			jQuery("td[name=weidus]:visible").each(function(){
				var weidusScore = 0;
				var weidusRowspan = jQuery(this).attr("rowspan");
				if(isNaN(weidusRowspan)){
					weidusRowspan = 1;
				}
				var thisTrIndex = jQuery(this).parent().index();
				var weiduName = jQuery(this).attr("mergerName");
				for(var i=thisTrIndex;i<parseInt(thisTrIndex)+parseInt(weidusRowspan);i++){
					//alert(jQuery("#inspectBSC_defTable").find("tr").eq(i).find("input[name=score]").eq(0).val());
					weidusScore += parseFloat(jQuery("#inspectBSC_defTable").find("tr").eq(i).find("input[name=score]").eq(0).val());
				}
				var weight1rs = weidusScore/totalScore*100;
				jQuery(this).find("span").eq(1).text(toDecimal(weight1rs));
				jQuery(this).find("input[name=weight1]").val(toDecimal(weight1rs));
				jQuery(this).find("input[name=weight1]").trigger('blur');
				
				jQuery("td[group="+weiduName+"_fenlei]:visible").each(function(){
					var fenleiScore = 0;
					var fenleiRowspan = parseInt(jQuery(this).attr("rowspan"));
					if(isNaN(fenleiRowspan)){
						fenleiRowspan = 1;
					}
					var thisTrIndex1 = parseInt(jQuery(this).parent().index());
					var fenleiName = jQuery(this).attr("mergerName");
					for(var i=thisTrIndex1;i<thisTrIndex1+fenleiRowspan;i++){
						fenleiScore += parseFloat(jQuery("#inspectBSC_defTable").find("tr").eq(i).find("input[name=score]").eq(0).val());
					}
					var weight2rs = fenleiScore/weidusScore*100;
					jQuery(this).find("span").eq(1).text(weight2rs.toFixed(2));
					jQuery(this).find("input[name=weight2]").val(weight2rs.toFixed(2));
					jQuery(this).find("input[name=weight2]").trigger('blur');
					
					jQuery("td[group="+fenleiName+"_xiangmu]:visible").each(function(){
						var xiangmu = jQuery(this).parent().find("input[name=score]").eq(0).val();
						var weight3rs = xiangmu/fenleiScore*100;
						jQuery(this).find("span").eq(1).text(weight3rs.toFixed(2));
						jQuery(this).find("input[name=weight3]").val(weight3rs.toFixed(2));
					});
				});
			});
			fixWeight();
		}
		computeStatue = true;
	}
	
	function fixWeight(){
		var weiduValue = 0;
		var errorFlag = false;
		var weiduName = "";
		var weiduCha = 0;
		var maxweidu = null;
		jQuery("td[name=weidus]:visible").each(function(){
			if(!maxweidu){
				maxweidu = jQuery(this).find("input[name=weight1]").eq(0);
			}else if(parseFloat(jQuery(this).find("input[name=weight1]").eq(0).val())>parseFloat(maxweidu.find("input[name=weight1]").val())){
				maxweidu = jQuery(this).find("input[name=weight1]").eq(0);
			} 
			weiduName = jQuery(this).attr("mergerName");
			if(!jQuery(this).find("input[name=weight1]").val()){
				jQuery(this).find("input[name=weight1]").val('0.00');
			}else{
				weiduValue += parseFloat(jQuery(this).find("input[name=weight1]").val());
			}
			var fenleiValue = 0;
			var fenleiErrorFlag = false;
			var fenleiName = "";
			var fenleiCha = 0;
			var maxfenlei = null;
			jQuery("td[group="+weiduName+"_fenlei]:visible").each(function(){
				if(!maxfenlei){
					maxfenlei = jQuery(this).find("input[name=weight2]").eq(0);
				}else if(parseFloat(jQuery(this).find("input[name=weight2]").eq(0).val())>parseFloat(maxfenlei.find("input[name=weight2]").val())){
					maxfenlei = jQuery(this).find("input[name=weight2]").eq(0);
				}
				fenleiName = jQuery(this).attr("mergerName");
				if(!jQuery(this).find("input[name=weight2]").val()){
					jQuery(this).find("input[name=weight2]").val('0.00');
				}else{
					fenleiValue += parseFloat(jQuery(this).find("input[name=weight2]").val());
				}
				var xiangmuValue = 0;
				var xiangmuErrorFlag = false;
				var xiangmuName = "";
				var xiangmuCha = 0;
				var maxXiangmu = null;
				jQuery("td[group="+fenleiName+"_xiangmu]:visible").each(function(){
					if(!maxXiangmu){
						maxXiangmu = jQuery(this).find("input[name=weight3]").eq(0);
					}else if(parseFloat(jQuery(this).find("input[name=weight3]").eq(0).val())>parseFloat(maxXiangmu.find("input[name=weight3]").val())){
						maxXiangmu = jQuery(this).find("input[name=weight3]").eq(0);
					}
					xiangmuName = jQuery(this).attr("mergerName");
					if(!jQuery(this).find("input[name=weight3]").val()){
						jQuery(this).find("input[name=weight3]").val(0.00);
					}else{
						xiangmuValue += parseFloat(jQuery(this).find("input[name=weight3]").val());
					}
				});
				xiangmuValue = xiangmuValue.toFixed(2);
				if(xiangmuValue!=100){
					xiangmuCha = (xiangmuValue-100).toFixed(2);
					//alert("分类 "+fenleiName+" 的项目有误差 差值："+xiangmuCha);
					var actuVal = (parseFloat(maxXiangmu.val())-xiangmuCha).toFixed(2);
					maxXiangmu.val(actuVal);
					maxXiangmu.parent().prev().text(actuVal);
					/* actuVal = (parseFloat(jQuery(this).find("input[name=weight2]").val())-xiangmuCha).toFixed(2);
					jQuery(this).find("input[name=weight2]").val(actuVal);
					jQuery(this).find("span").eq(1).text(actuVal);
					fenleiValue -= xiangmuCha; */
				}
			});
			fenleiValue = fenleiValue.toFixed(2);
			if(fenleiValue!=100){
				fenleiCha = (fenleiValue-100).toFixed(2);
				//alert("维度  "+weiduName+"  的分类有误差 差值："+fenleiCha);
				var actuVal = (parseFloat(maxfenlei.val())-fenleiCha).toFixed(2);
				maxfenlei.val(actuVal);
				maxfenlei.parent().prev().text(actuVal);
				
				/* actuVal = (parseFloat(jQuery(this).find("input[name=weight1]").val())-fenleiCha).toFixed(2);
				jQuery(this).find("input[name=weight1]").val(actuVal);
				jQuery(this).find("span").eq(1).text(actuVal);
				weiduValue -= fenleiCha; */
			}
		});
		weiduValue = weiduValue.toFixed(2);
		if(weiduValue!=100){
			weiduCha = (weiduValue-100).toFixed(2);;
			//alert("总维度有误差 差值："+weiduCha);
			var actuVal = (parseFloat(maxweidu.val())-weiduCha).toFixed(2);
			maxweidu.val(actuVala);
			maxweidu.parent().prev().text(actuVal);
		}
	}
	
	function checkWeight(){
		var weiduValue = 0;
		var errorFlag = false;
		var weiduName = "";
		jQuery("td[name=weidus]:visible").each(function(){
			weiduName = jQuery(this).attr("mergerName");
			if(!jQuery(this).find("input[name=weight1]").val()){
				alert("一级指标'"+weiduName+"'权重不能为空！");
				jQuery(this).find("input[name=weight1]").focus();
				errorFlag = true;
				return ;
			}else{
				weiduValue += parseFloat(jQuery(this).find("input[name=weight1]").val());
			}
			var fenleiValue = 0;
			var fenleiErrorFlag = false;
			var fenleiName = "";
			jQuery("td[group="+weiduName+"_fenlei]:visible").each(function(){
				//alert(jQuery("td[group="+jQuery(this).find("span:first").text()+"]").length);
				fenleiName = jQuery(this).attr("mergerName");
				
				if(!jQuery(this).find("input[name=weight2]").val()){
					alert("二级指标'"+fenleiName+"'权重不能为空！");
					jQuery(this).find("input[name=weight2]").focus();
					errorFlag = true;
					fenleiErrorFlag = true;
					return ;
				}else{
					fenleiValue += parseFloat(jQuery(this).find("input[name=weight2]").val());
				}
				var xiangmuValue = 0;
				var xiangmuErrorFlag = false;
				var xiangmuName = "";
				jQuery("td[group="+fenleiName+"_xiangmu]:visible").each(function(){
					//alert(jQuery("td[group="+jQuery(this).find("span:first").text()+"]").length);
					xiangmuName = jQuery(this).attr("mergerName");
					if(!jQuery(this).find("input[name=weight3]").val()){
						alertMsg.error("三级指标'"+xiangmuName+"'权重不能为空！");
						jQuery(this).find("input[name=weight3]").focus();
						errorFlag = true;
						fenleiErrorFlag = true;
						xiangmuErrorFlag = true;
						return ;
					}else{
						xiangmuValue += parseFloat(jQuery(this).find("input[name=weight3]").val());
					}
				});
				if(!xiangmuErrorFlag){
					xiangmuValue = xiangmuValue.toFixed(2);
					if(xiangmuValue>100){
						alertMsg.error("二级指标'"+fenleiName+"'对应的三级指标权重超过100%");
						errorFlag = true;
						fenleiErrorFlag = true;
						return ;
					}else if(xiangmuValue<100){
						alertMsg.error("二级指标'"+fenleiName+"'对应的三级指标权重少于100%");
						errorFlag = true;
						fenleiErrorFlag = true;
						return ;
					}
					return true;
				}
			});
			if(!fenleiErrorFlag){
				fenleiValue = fenleiValue.toFixed(2);
				if(fenleiValue>100){
					alertMsg.error("一级级指标'"+weiduName+"'对应的二级指标权重超过100%");
					errorFlag = true;
					return ;
				}else if(fenleiValue<100){
					alertMsg.error("一级级指标'"+weiduName+"'对应的二级指标权重少于100%");
					errorFlag = true;
					return ;
				}
				return true;
			}
		});
		if(!errorFlag){
			weiduValue = weiduValue.toFixed(2);
			if(weiduValue>100){
				alertMsg.error("一级指标总权重超过100%");
				return ;
			}else if(weiduValue<100){
				alertMsg.error("一级指标总权重少于100%");
				return ;
			}
			return true;
		}
	}
	
	function checkScore(){
		var totalScoreText = parseFloat(jQuery("#totalScore").text());
		var totalScore = 0;
		var scoreFlag = false;
		var reNum =/^[0-9]+.?[0-9]*$/;
		jQuery("#inspectBSC_defTable").find("input[name=score]").each(function(){
			var scoreValue = jQuery(this).val();
			if(scoreValue==''){
				alertMsg.error("分值不能为空！");
				jQuery(this).focus();
				scoreFlag = true;
				return ;
			}else if(!reNum.test(scoreValue)){
				alertMsg.error("分值必须为数字！");
				jQuery(this).focus();
				jQuery(this).val("");
				scoreFlag = true;
				return ;
			}
			scoreValue = parseFloat(scoreValue);
			jQuery(this).val(scoreValue.toFixed(jjScoreDecimalPlaces));
			totalScore += scoreValue;
		});
		totalScore = totalScore.toFixed(jjScoreDecimalPlaces);
			//alert("after:"+totalScore);
		if(!scoreFlag){
			if(totalScore>totalScoreText){
				alertMsg.error("分值超过总分值！");
				return ;
			}else if(totalScore<totalScoreText){
				alertMsg.error("分值低于总分值！");
				return ;
			}
			return true;
		}
		
	}
	function delSpace(str){
		str = str.replace(/[ ]/g,""); 
		//alert(str);
		return str;
	}

	function showColumn(){
		var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
		var url = "getColumnInfo?inspectTemplId="+inspectTemplId;
		$.pdialog.open(url, 'bscColumn', "显示/隐藏列", {
			mask : false,
			width : 300,
			height : 500
		});
	}
	function enableOrDisableTempl(flag){
		var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
		if(inspectTemplId.length==0){
			alertMsg.error("请选择记录。");
		}
		$.ajax({
		    url: "enableOrDisableTempl",
		    data:{inspectModelId:inspectTemplId,enableType:flag,navTabId:"inspectTempl_gridtable"},
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        formCallBack(data);
		    }
		});
	}
</script>

<div class="page" id="inspectTempl_page">
<div id="inspectTempl_container">
			<div id="inspectTempl_layout-center"
				class="pane ui-layout-center">
	<div id="inspectTempl_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<label style="float:none;white-space:nowrap" >
						<s:text name='inspectTempl.inspectModelId'/>:
						 	<input type="text"	id="inspectTempl_inspectModelId_search" >
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='inspectTempl.inspectModelNo'/>:
						 	<input type="text"	id="inspectTempl_inspectModelNo_search" >
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='inspectTempl.inspectModelName'/>:
						 	<input type="text"	id="inspectTempl_inspectModelName_search" >
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='inspectTempl.inspecttype'/>:
						 	<s:select list="inspecttype" id="inspectTempl_inspecttype_search" style="font-size:12px" listKey="value" listValue="value" value="%{inspectTempl.inspecttype.dictionaryItemId}" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='inspectTempl.jjdepttype'/>:
						 	<s:select list="jjdepttype" id="inspectTempl_jjdepttype_search" style="font-size:12px" listKey="khDeptTypeId" listValue="khDeptTypeName" value="%{inspectTempl.jjdepttype.khDeptTypeId}" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:select id="inspectTempl_periodType_search" key="inspectTempl.periodType" name="inspectTempl.periodType" list="#{'月':'月', '季':'季','半年':'半年','年':'年'}" headerKey="" headerValue="--"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='inspectTempl.disabled'/>:
						<s:select id="inspectTempl_disabled_search" key="inspectTempl.disabled" style="font-size:12px" theme="simple" list="#{ '':'全部','true':'是','false':'否' }"></s:select>
						<%-- <s:checkbox style="vertical-align:middle" id="inspectTempl_disabled_search" key="inspectTempl.disabled" theme="simple"></s:checkbox> --%>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="inspectTemplGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>
					</div>
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="inspectTemplGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div> --%>
		</div>
	</div>
	<div class="pageContent">
		




<div class="panelBar" id="inspectTempl_buttonBar">
			<ul class="toolBar">
				<li><a id="inspectTempl_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="inspectTempl_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a class="changebutton"  href="javaScript:editInspectTempl()"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a class="particularbutton"
								external="true" href="javaScript:inspectTemplLayout.optDblclick();"><span>明细</span> </a></li>
				<li><a class="enablebutton"
								external="true" href="javaScript:enableOrDisableTempl('enable')"><span>启用</span> </a></li>
				<li><a class="disablebutton"
								external="true" href="javaScript:enableOrDisableTempl('disable')"><span>停用</span> </a></li>
			
			</ul>
		</div>
		<div id="inspectTempl_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:inspectModelId;width:550;height:500">
			<input type="hidden" id="inspectTempl_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="inspectTempl_gridtable_addTile">
				<s:text name="inspectTemplNew.title"/>
			</label> 
			<label style="display: none"
				id="inspectTempl_gridtable_editTile">
				<s:text name="inspectTemplEdit.title"/>
			</label>
			<label style="display: none"
				id="inspectTempl_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="inspectTempl_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<input type="hidden" id="inspectTempl_gridtable_isShowSouth"
							value="0"/>
			<input type="hidden" id="jjScoreDecimalPlaces" value="2"/>
<div id="load_inspectTempl_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="inspectTempl_gridtable"></table>
		<div id="inspectTemplPager"></div>
</div>
	</div>
	<div class="panelBar" id="inspectTempl_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="inspectTempl_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="inspectTempl_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="inspectTempl_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<div id="inspectTempl_layout-south" class="pane ui-layout-south"
					style="padding: 2px">

					<div class="panelBar">
						<ul class="toolBar">
							<li><a class="settingbutton"  href="javaScript:selectKpiItem()"><span>选择指标
							</span>
							</a>
							</li>
							<li><a class="changebutton"  href="javaScript:editBSC()"><span>编辑
							</span>
							</a>
							</li>
							<li><a class="canceleditbutton"  href="javaScript:cancelBSC()"><span>取消编辑
							</span>
							</a>
							</li>
							<li><a class="savebutton"  href="javaScript:saveBSC()"><span>保存编辑
							</span>
							</a>
							</li>
							<li><a id="editSW" class="editbutton"  href="javaScript:editScoreOrWeight()"><span>编辑分值
							</span>
							</a>
							</li>
							<li><a id="computeSW" class="zbcomputebutton"  href="javaScript:computeScoreOrWeight()"><span>计算分值
							</span>
							</a>
							</li>
							<li><a  class="changebutton"  href="javaScript:showColumn()"><span>显示/隐藏列
							</span>
							</a>
							</li>
							
							
							<li style="float: right;"><a id="inspectTempl_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="inspectTempl_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="inspectTempl_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
					<div id="balabceCardTempl">
					
					</div>
</div>
</div>
</div>