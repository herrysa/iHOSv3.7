
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	//alert("${colSelectStr}");
	var searchGrid = "${grid}";
	var entityName = "${entityName}";
	var gridCols = "${cols}";
	var colArray = new Map();
	var extraCols = new Map();
	var inContrlCols = new Map();
	jQuery(document).ready(function() { 

	var gridColModel = jQuery("#"+searchGrid).jqGrid("getGridParam",'colModel');  
    for(var gci=0;gci<gridColModel.length;gci++){
    	if(gridColModel[gci]&&!gridColModel[gci].highsearch){
    		extraCols.put(gridColModel[gci].name,true);
    	}
    	colArray.put(gridColModel[gci].name,gci);
    	
    }
	    
    jQuery("input[name='columnCheckBox']").mousedown(function(e){
    	//alert();
    	e.stopPropagation();
    });
    jQuery("input[name='colWidth']").mousedown(function(e){
    	//alert();
    	e.stopPropagation();
    });
    jQuery("#colshowButton").click(function(){
    	var showChecked = jQuery("#colShowCheckbox").attr('checked');
    	var showName = jQuery("#colShowName").val();
    	if(showChecked=='checked'){
    		if(!showName){
    			alertMsg.error('请输入模板名称！');
    			return ;
    		}
    	}
    	var colWidthArr = new Array();
    	/* jQuery("input[name='colWidth']").each(function(){
    		var thisValue = jQuery(this).val();
    		if(thisValue&&!isNaN(thisValue)){
    			colWidthArr.push(parseInt(thisValue));
    		}
    	}); */
    	for(var gci=0;gci<gridColModel.length;gci++){
    		var colName = gridColModel[gci].name;
    		if(colName.indexOf(".")!=-1){
    			colName = colName.replace(/\./g,"_")
    		}
    		//alert(colName);
    		
    		var thisValue = jQuery("#"+colName+"_colWidth").val();
    		if(thisValue&&!isNaN(thisValue)){
    			colWidthArr[gci]=parseInt(thisValue);
    		}
    	}
    	var colCBI = 2,assisIndex=0,assisLength=0,newOrder=new Array(),oldOrder=new Array(),op="",cols = "",endStr = "",colIsShows="",colWidths="",titles="";
    	assisLength = jQuery("input[name='columnCheckBox']").length;
    	jQuery("input[name='columnCheckBox']").each(function(){
    		if(assisIndex==assisLength-1){
    			endStr = "";
    		}else{
    			endStr = ",";
    		}
    		var col = jQuery(this).attr("id");
    		inContrlCols.put(col,true);
    		if(jQuery(this).attr("checked")=='checked'){
    			jQuery("#"+searchGrid).jqGrid('showHideCol',col,"block");
    			colIsShows += "true"+endStr;
    			//console.log("col:"+col+",show:block");
    		}else{
    			jQuery("#"+searchGrid).jqGrid('showHideCol',col,"none");
    			colIsShows += "false"+endStr;
    			//console.log("col:"+col+",show:none");
    		}
    		titles += jQuery(this).next().text()+endStr;
    		//jQuery("#"+searchGrid).jqGrid('setColProp',col,{width:200});
    		//alert(jQuery("#"+searchGrid)[0].grid.headers[2].width);
    		var colIdx = null;
    		for(var gci=0;gci<gridColModel.length;gci++){
        		//newOrder.push(colArray.get(gridColModel[gci].name));
        		if(gridColModel[gci].name==col){
        			colIdx = gci;
        			jQuery("#"+searchGrid)[0].grid.resizing = { idx: colIdx};
        			break;
        		}
            }
    		if(colIdx!=null){
    			
    			//alert(colIdx+","+colWidthArr[colIdx]);
    			//jQuery("#"+searchGrid).jqGrid('setColProp',col,{width:colWidthArr[colIdx]});
    			var diff = colWidthArr[colIdx]-jQuery("#"+searchGrid)[0].grid.headers[colIdx].width;
    			jQuery("#"+searchGrid)[0].grid.newWidth = jQuery("#"+searchGrid)[0].p.tblwidth+diff;
    			jQuery("#"+searchGrid)[0].grid.headers[colIdx].newWidth = colWidthArr[colIdx];
    			//jQuery("#"+searchGrid)[0].grid.newWidth = jQuery("#"+searchGrid)[0].p.tblwidth
        		//alert(jQuery("#"+searchGrid)[0].grid.headers[4].width);
        		jQuery("#"+searchGrid)[0].grid.dragEnd();
        		//alert(jQuery("#"+searchGrid)[0].grid.hDiv.scrollLeft+","+jQuery("#"+searchGrid)[0].grid.bDiv.scrollLeft);
        		//alert(colWidthArr[colIdx]+","+jQuery("#"+searchGrid)[0].p.colModel[colIdx].width);
    		}
    		colWidths += colWidthArr[colIdx]+endStr;
    		cols += col+endStr;
    		/* for(var eci=0;eci<extraCols.length;eci++){
    			if(colArray.get(extraCols[eci])==colCBI){
    				colCBI++;
    			}
    		} */
        	var oldPosition = colArray.get(col);
        	if(oldPosition){
        		oldOrder.push(oldPosition);
        	}
    		//colArray.put(col,colCBI);
    		colCBI++;
    		assisIndex++;
    	});
    	//var mygrid=jQuery("#"+searchGrid).dragEnd;
    	//mygrid.updateColumns();
    	var showTempltName = jQuery("select[name='showTemplt']","#colShowForm").val();
    	var templetEdit = false;
    	if(showTempltName){
    		var templetNameArr = showTempltName.split("_");
    		if(templetNameArr.length>0){
    			var templetType = templetNameArr[1];
    			if(templetType=='0'){
    				templetEdit = true;
    			}
    		}
    	}
    	//if(showChecked=='checked'||templetEdit){
    	if(showChecked=='checked'){
    		if(showChecked!='checked'&&templetEdit&&!showName){
    			showName = showTempltName;
    		}
    		var templetToDept = jQuery("#templetToDept").attr('checked');
    		if(templetToDept=='checked'){
    			templetToDept = 'true';
    		}else{
    			templetToDept = 'false';
    		}
    		var templetToRole = jQuery("#templetToRole").attr('checked');
    		if(templetToRole=='checked'){
    			templetToRole = 'true';
    		}else{
    			templetToRole = 'false';
    		}
    		var templetToPublic = jQuery("#templetToPublic").attr('checked');
    		if(templetToPublic=='checked'){
    			templetToPublic = 'true';
    		}else{
    			templetToPublic = 'false';
    		}
    		$.ajax({
			    url: 'saveColShowTempl',
			    type: 'post',
			    data:{templName:showName,entityName:entityName,cols:cols,colIsShows:colIsShows,colWidths:colWidths,titles:titles,templetToDept:templetToDept,templetToRole:templetToRole,templetToPublic:templetToPublic},
			    dataType: 'json',
			    async:false,
			    error: function(data){
			    	 //jQuery('#ajaxError').html(data.responseText);
			    },
			    success: function(data){
			        // do something with xml
			        //alert(data);
			    }
			});
    	}
    	/* var mk = colArray.keys();
        alert(mk.length);
        for(var mki=0;mki<mk.length;mki++){
        	alert(colArray.get(mk[mki]));
        } */
        colCBI = 0;
    	for(var gci=0;gci<gridColModel.length;gci++){
    		var extraHighsearch = extraCols.get(gridColModel[gci].name);
    		var inContrl = inContrlCols.get(gridColModel[gci].name);
        	if(!extraHighsearch&&inContrl){
        		newOrder[gci]=oldOrder[colCBI];
        		colCBI++;
        	}else{
        		newOrder[gci]=gci;
        	}
        }
    	/* for(var gci=0;gci<newOrder.length;gci++){
    		//newOrder.push(colArray.get(gridColModel[gci].name));
    		console.log(newOrder[gci]);
        } */
    	jQuery("#"+searchGrid).jqGrid('remapColumns',newOrder,true);
    	jQuery("#"+searchGrid).jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
    	 $.pdialog.closeCurrent();
    	
    });
    
    jQuery("select[name='showTemplt']","#colShowForm").change(function(){
    	var templtName = jQuery(this).val();
    	if(templtName=="-1"){
    		return ;
    	}
    	$.ajax({
		    url: 'getColShowByTempl',
		    type: 'post',
		    data:{templName:templtName,entityName:entityName,cols:"${cols}",titles:"${titles}",hiddens:"${hiddens}",colWidths:"${colWidths}"},
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    	 jQuery('#ajaxError').html(data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        var colShows = data.colShows;
		        /* for(var ci in colList){
		        	alert(colList[ci].label);
		        	jQuery("#"+colList[ci].col)
		        } */
		        if(colShows.length>0){
		        	if(colShows[0].templetToDept){
		        		jQuery("#templetToDept").attr("checked","checked");
		        	}else{
		        		jQuery("#templetToDept").removeAttr("checked");
		        	}
		        	if(colShows[0].templetToRole){
		        		jQuery("#templetToRole").attr("checked","checked");
		        	}else{
		        		jQuery("#templetToRole").removeAttr("checked");
		        	}
		        	if(colShows[0].templetToPublic){
		        		jQuery("#templetToPublic").attr("checked","checked");
		        	}else{
		        		jQuery("#templetToPublic").removeAttr("checked");
		        	}
		        }
		        var ci = 0,inCtrl = new Map();
		        for(var cs in colShows){
		        	inCtrl.put(colShows[cs].col,true);
	        	}
		        jQuery("input[name='columnCheckBox']").each(function(){
		        	var thisCol = jQuery(this).attr('id');
		        	var isInCtrl = inCtrl.get(thisCol);
		        	if(isInCtrl){
		        		jQuery(this).attr('id',colShows[ci].col);
			        	jQuery(this).next().text(colShows[ci].label);
			        	jQuery(this).parent().find("input[name='colWidth']").attr("id",colShows[ci].colForId+"_colWidth");
			        	jQuery(this).parent().find("input[name='colWidth']").val(colShows[ci].colWidth);
			        	if(colShows[ci].show){
			        		jQuery(this).attr('checked','checked');
			        	}else{
			        		jQuery(this).removeAttr('checked');
			        	}
			        	ci++;
		        	}else{
		        		jQuery(this).removeAttr('checked');
		        	}
		        });
		    }
		});
    });
    jQuery("#templetToPublic").click(function(){
    	var publicChecked = jQuery(this).attr("checked");
    	if(publicChecked=="checked"){
    		jQuery("#templetToDept").removeAttr("checked");
    		jQuery("#templetToRole").removeAttr("checked");
    		jQuery("#templetToDept").attr("disabled","true");
    		jQuery("#templetToRole").attr("disabled","true");
    	}else{
    		jQuery("#templetToDept").removeAttr("disabled");
    		jQuery("#templetToRole").removeAttr("disabled");
    	}
    });
  	});
	
	function delShowTemplate(){
		var templtName = jQuery("select[name='showTemplt']","#colShowForm").val();
		if(templtName=="-1"){
    		return ;
    	}else{
    		var templetNameArr = templtName.split("_");
    		if(templetNameArr.length>0){
    			var templetType = templetNameArr[1];
    			if(templetType!='0'){
    				alertMsg.error("非用户自建模板不能删除！");
    				return ;
    			}
    		}
    	}
    	$.ajax({
		    url: 'delColShowTempl',
		    type: 'post',
		    data:{templName:templtName,entityName:entityName,cols:"${cols}",titles:"${titles}",hiddens:"${hiddens}",colWidths:"${colWidths}"},
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    	alertMsg.error("系统错误！");
				return ;
		    },
		    success: function(data){
		    	jQuery("select[name='showTemplt'] option[value='"+templtName+"']").remove(); 
		        var colShows = data.colShows;
		        var ci = 0;
		        jQuery("input[name='columnCheckBox']").each(function(){
		        	jQuery(this).attr('id',colShows[ci].col);
		        	jQuery(this).next().text(colShows[ci].label);
		        	jQuery(this).parent().find("input[name='colWidth']").attr("id",colShows[ci].colForId+"_colWidth");
		        	jQuery(this).parent().find("input[name='colWidth']").val(colShows[ci].colWidth);
		        	if(colShows[ci].show){
		        		jQuery(this).attr('checked','checked');
		        	}else{
		        		jQuery(this).removeAttr('checked');
		        	}
		        	ci++;
		        });
		    }
		});
	}
	function checkWidth(obj){
		
	}
//模版选择下拉框修改
function templtShowListChange(obj){
	var tempName = jQuery(obj).find("option:selected").text();
	jQuery("#colShowName").val(tempName);
}
</script>

<style>
.test {
    padding:10px;
    margin:10px;
    height:90%;
    color:#333; 
    border:rgb(49, 135, 221) solid 1px;
}
.test legend {
    color:#06c;
    padding:5px 10px;
    font-weight:normal !important; 
    border:0
    /*background:white;*/
} 
</style>

<div class="page">
	<div class="pageContent">
		<form id="colShowForm">
		<div layoutH="36">
		<fieldset class="test" style="width:300px;overflow:auto;" >
		<legend>显示项</legend>
					显示模板：<s:select list="templtShowList" listKey="templetNameV" listValue="templetName" headerKey="-1" headerValue="" name="showTemplt" onchange="templtShowListChange(this)"></s:select>
					<div class="buttonActive" style="float:right">
					<div class="buttonContent">
							<button type="button" onclick="delShowTemplate()">删除此模板</button>
						</div>
					</div>
					<div class="sortDrag" >
						<s:iterator value="colShows" status="it">
							
						<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">
						<input id="${col}" type="checkbox" name="columnCheckBox" <s:if test="show==true">checked</s:if> />
						<label><s:property value="label"/></label>
						<span style="margin-right:10px;float:right">列宽：<input style="float:none" id="${colForId}_colWidth"  name="colWidth" type="text" size="3" value="${colWidth }" onblur="checkWidth(this)"/></span>
						</div>
						</s:iterator>
				</div>
				模板名称：<input id="colShowName" size=10/> 保存模板<input id="colShowCheckbox" type="checkbox"/><br/>本部门可见<input id="templetToDept" name="templetToDept" type="checkbox"/>角色可见<input id="templetToRole" name="templetToRole" type="checkbox"/>公共<input id="templetToPublic" name="templetToPublic" type="checkbox"/>
		</fieldset>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button id="colshowButton" type="Button"><s:text name="button.confirm" /></button>
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
			</form>
	</div>
</div>