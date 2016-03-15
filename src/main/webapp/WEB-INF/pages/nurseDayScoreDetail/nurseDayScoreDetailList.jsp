
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	function nurseDayScoreDetailGridReload(){
		var urlString = "nurseDayScoreDetailGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#nurseDayScoreDetail_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var nurseDayScoreDetailLayout;
			  var nurseDayScoreDetailGridIdString="#nurseDayScoreDetail_gridtable";
	var dayScoreID = '${requestScope.dayScoreID}';
	jQuery(document).ready(function() { 
		/* nurseDayScoreDetailLayout = makeLayout({
			baseName: 'nurseDayScoreDetail', 
			tableIds: 'nurseDayScoreDetail_gridtable'
		}, null); */
var nurseDayScoreDetailGrid = jQuery(nurseDayScoreDetailGridIdString);
    nurseDayScoreDetailGrid.jqGrid({
    	url : "nurseDayScoreDetailGridList?dayScoreID="+dayScoreID,
    	editurl:"nurseDayScoreDetailGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'dayScoreDetailID',index:'dayScoreDetailID',align:'center',label : '<s:text name="nurseDayScoreDetail.dayScoreDetailID" />',hidden:true,key:true,formatter:'integer'},				
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="nurseDayScoreDetail.checkperiod" />',hidden:false,width:80},				
{name:'scoredate',index:'scoredate',align:'center',label : '<s:text name="nurseDayScoreDetail.scoredate" />',hidden:false,formatter:'date', formatoptions:{ newformat: 'Y-m-d'},width:80},				
{name:'groupname',index:'groupname',align:'center',label : '<s:text name="nurseDayScoreDetail.groupname" />',hidden:false,width:80},				
{name:'code',index:'code',align:'center',label : '<s:text name="nurseDayScoreDetail.code" />',hidden:false,width:80},				
{name:'name',index:'name',align:'center',label : '<s:text name="nurseDayScoreDetail.name" />',hidden:false,width:80},				
{name:'yearname',index:'yearname',align:'center',label : '<s:text name="nurseDayScoreDetail.yearname" />',hidden:false,width:80},			
{name:'postcode.code',index:'postcode',align:'center',label : '<s:text name="nurseDayScoreDetail.postcode" />',hidden:false,width:80,edittype:"select",editrules:{number:true},editable:true,editoptions:{value:'${postSelect}'}},								
{name:'shiftcode.code',index:'shiftcode',align:'center',label : '<s:text name="nurseDayScoreDetail.shiftname" />',hidden:false,width:80,edittype:"select",editrules:{number:true},editable:true,editoptions:{value:'${shiftSelect}'}},				
{name:'isWork',index:'isWork',align:'center',label : '<s:text name="nurseDayScoreDetail.isWork" />',hidden:false,width:80,formatter:'checkbox',edittype:"checkbox",editable:true,editoptions:{value:"Yes:No",dataEvents:[{type:'click',fn: function(e) { isWork(this); }}]}},				
{name:'days',index:'days',align:'center',label : '<s:text name="nurseDayScoreDetail.days" />',hidden:false,width:80,width:80,formatter:'number',formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1, defaultValue: '1.0'},edittype:"text",editrules:{number:true},editable:true,
	editoptions:{dataEvents:[{type:'blur',fn: function(e) { toDecimalDays(this); }}]}},				
{name:'score',index:'score',align:'center',label : '<s:text name="nurseDayScoreDetail.score" />',hidden:false,width:80,formatter:'number',edittype:"text",editrules:{number:true},editable:false},				
{name:'state',index:'state',align:'center',label : '<s:text name="nurseDayScoreDetail.state" />',hidden:false,width:80,formatter:'select',edittype:"select",editoptions:{value:"0:新建;1:已审核"}},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="nurseDayScoreDetail.remark" />',hidden:false,width:80,edittype:"text",editrules:{number:true},editable:true}				
/* {name:'postcode',index:'postcode',align:'center',label : '<s:text name="nurseDayScoreDetail.postcode" />',hidden:false,edittype:"text",editrules:{number:true},editable:true},				
{name:'shiftcode',index:'shiftcode',align:'center',label : '<s:text name="nurseDayScoreDetail.shiftcode" />',hidden:false},				
{name:'yearcode',index:'yearcode',align:'center',label : '<s:text name="nurseDayScoreDetail.yearcode" />',hidden:false}				 */

        ],
        jsonReader : {
			root : "nurseDayScoreDetails", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'dayScoreDetailID',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="nurseDayScoreDetailList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           fullGridEdit(nurseDayScoreDetailGridIdString);
           var dataTest = {"id":"nurseDayScoreDetail_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("nurseDayScoreDetail_gridtable");
       	} 

    });
    jQuery(nurseDayScoreDetailGrid).jqGrid('bindKeys');
    
    jQuery(nurseDayScoreDetailGrid).unbind("keydown").bind("keydown",function(e){
		chargeKeyPress(nurseDayScoreDetailGrid,e);
	});
	
	
	//nurseDayScoreDetailLayout.resizeAll();
  	});
	var needSaveCol = ["postcode.code,select","shiftcode.code,select","isWork,checkbox","days,text","remark,text"];
	
	function toDecimalDays(obj){
		obj.value = toDecimal(obj.value);
		if(obj.value==0){
			jQuery(obj).parent().parent().find(":checkbox[name='isWork']:first").removeAttr("checked");
		}else{
			jQuery(obj).parent().parent().find(":checkbox[name='isWork']:first").attr("checked","checked");
		}
	}
	
	function isWork(obj){
		//alert(obj.checked);
		if(obj.checked==true){
			jQuery(obj).parent().parent().find("input[name='days']:first").val(1);
		}else{
			jQuery(obj).parent().parent().find("input[name='days']:first").val(0);
		}
	}
	
	function saveNurseDayScore(){
		if(isHaveRight=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		var sid = jQuery("#nurseDayScore_gridtable").jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}else{
			for(var i=0;i<sid.length;i++){
				var row = jQuery("#nurseDayScore_gridtable").jqGrid('getRowData',sid[i]);
				var state = row['state'];
				var scoredate = row['scoredate'];
				var dept = row['groupname'];
				if(state==1){
					alertMsg.error(dept+" "+scoredate+" 的考勤记录已审核！");
					return;
				}
			}
		}
		var editData = "id=";
		var url = "saveNurseDayScoreDetailInfo";
		var ids = jQuery(nurseDayScoreDetailGridIdString).jqGrid('getDataIDs');
		for(var idI=0;idI<ids.length;idI++){
			editData += ids[idI]+",";
		}
		editData += "end@";
		for(var i=0;i<needSaveCol.length;i++){
			if(needSaveCol[i].split(",")[1]=="text"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery("input[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					editData += jQuery(this).val()+",";
				});
				editData += "end@";
			}else
			if(needSaveCol[i].split(",")[1]=="checkbox"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery(":checkbox[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					var checked = jQuery(this).attr("checked");
					if(checked=="checked"){
						editData += 1+",";
					}else{
						editData += 0+",";
					}
				});
				editData += "end@";
			}else if(needSaveCol[i].split(",")[1]=="textarea"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery("textarea[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					editData += jQuery(this).val()+",";
				});
				editData += "end@";
			}else if(needSaveCol[i].split(",")[1]=="select"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery("select[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					editData += jQuery(this).val()+",";
				});
				editData += "end@";
			}
		}
		$.ajax({
		    url: url,
		    type: 'post',
		    data:{defData_editData:editData},
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
	
	function addNurseDayScoreDetail(){
		if(isHaveRight=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		
		var url = "editNurseDayScoreDetail?popup=true&navTabId=nurseDayScoreDetail_gridtable";
		var fatherGrid ='nurseDayScore_gridtable';
		var extraParam ='dayScoreID';
		if(fatherGrid!=null&&fatherGrid!=""&&extraParam!=null&&extraParam!=""){
			var fatherGridId = jQuery("#"+fatherGrid).jqGrid('getGridParam','selarrrow');
			if(fatherGridId==null || fatherGridId.length ==0){
				alertMsg.error('请选择记录。');
				return;
			}
		    if(fatherGridId.length>1){
				alertMsg.error('只能选择一条记录。');
				return;
			}else{
				for(var i=0;i<fatherGridId.length;i++){
					var row = jQuery("#nurseDayScore_gridtable").jqGrid('getRowData',fatherGridId[i]);
					var state = row['state'];
					var scoredate = row['scoredate'];
					var dept = row['groupname'];
					if(state==1){
						alertMsg.error(dept+" "+scoredate+" 的考勤记录已审核！");
						return;
					}
				}
				url += "&"+extraParam+"="+fatherGridId;
			}
		}
		
		
		var winTitle="添加个人考勤记录";
		//alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, 'editNurseDayScoreDetail', winTitle, {mask:false,width:500,height:350});
	}
 	function delNurseDayScoreDetail(){
 		if(isHaveRight=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		
		var sid = jQuery("#nurseDayScoreDetail_gridtable").jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#nurseDayScoreDetail_gridtable").jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId=nurseDayScoreDetail_gridtable&oper=del";
		editUrl = encodeURI(editUrl);
	    if(sid==null || sid.length ==0){
				alertMsg.error("请选择记录。");
				return;
		}else{
			//jQuery("#"+tableId).jqGrid('delGridRow',sid,{reloadAfterSubmit:false,left:300,top:150});
			for(var i=0;i<sid.length;i++){
				var row = jQuery("#nurseDayScoreDetail_gridtable").jqGrid('getRowData',sid[i]);
				var state = row['state'];
				var scoredate = row['scoredate'];
				var dept = row['groupname'];
				if(state==1){
					alertMsg.error(dept+" "+scoredate+" 的考勤记录已审核！");
					return;
				}
			}
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
					
				}
			});
		}
 	}
</script>

<div class="page">
<!-- <div id="nurseDayScoreDetail_container">
			<div id="nurseDayScoreDetail_layout-center"
				class="pane ui-layout-center"> -->
	<%-- <div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='nurseDayScoreDetail.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='nurseDayScoreDetail.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='nurseDayScoreDetail.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='nurseDayScoreDetail.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="nurseDayScoreDetailGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">





<%-- <div class="panelBar">
			<ul class="toolBar">
				<li><a id="nurseDayScoreDetail_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="nurseDayScoreDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="nurseDayScoreDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div> --%>
		<div id="nurseDayScoreDetail_gridtable_div" layoutH=54
			class="grid-wrapdiv"
			buttonBar="fatherGrid:nurseDayScore_gridtable;extraParam:dayScoreID;optId:dayScoreDetailID;width:500;height:300">
			<input type="hidden" id="nurseDayScoreDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="nurseDayScoreDetail_gridtable_addTile">
				<s:text name="nurseDayScoreDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="nurseDayScoreDetail_gridtable_editTile">
				<s:text name="nurseDayScoreDetailEdit.title"/>
			</label>
			<label style="display: none"
				id="nurseDayScoreDetail_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="nurseDayScoreDetail_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_nurseDayScoreDetail_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 <table id="nurseDayScoreDetail_gridtable"></table>
		<div id="nurseDayScoreDetailPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="nurseDayScoreDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="nurseDayScoreDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="nurseDayScoreDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->