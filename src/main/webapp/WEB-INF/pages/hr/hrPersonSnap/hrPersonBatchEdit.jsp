<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	var hrPersonBatchEditGridIdString="#hrPersonBatchEdit_gridtable";
	var treeSqlArr = new Array();
	var treeIdArr = new Array();
	jQuery(document).ready(function() {
		var hrPersonBatchEditGrid = jQuery(hrPersonBatchEditGridIdString);
		hrPersonBatchEditGrid.jqGrid({
	    	url : "getTableStructureList?tableCode=v_hr_person_current",
	    	editurl:"",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'fieldInfo.fieldCode',index:'fieldInfo.fieldCode',width : 100,align:'left',label : '字段',hidden:true,key:true},
				{name:'fieldInfo.dataType',index:'fieldInfo.dataType',width : 100,align:'left',label : '数据类型',hidden:true},
				{name:'fieldInfo.dataLength',index:'fieldInfo.dataLength',width:50,align:'right',label:'数据长度',hidden:true},
				{name:'fieldInfo.userTag',index:'fieldInfo.userTag',width:50,align:'right',label:'自定义标签',hidden:true},
				{name:'fieldInfo.parameter1',index:'fieldInfo.parameter1',width : 100,align:'left',label : '参数',hidden:true},
				{name:'fieldInfo.parameter2',index:'fieldInfo.parameter2',width : 100,align:'left',label : '参数名称',hidden:true},
				{name:'sn',index:'sn',width : 100,align:'left',label : '序号',hidden:true},
				{name:'name',index:'name',width : 100,align:'left',label : '名称',hidden:false,sortable:false},
				{name:'editvalue',index:'name',width : 400,align:'left',label : '修改值',hidden:false,formatter:batchEditFormatter,sortable:false}
	        ],
	        jsonReader : {
				root : "sysTableStructures", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'sn',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="sysTableKindList.title" />',
	        height:300,
	        rowNum:10000,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
	        onSelectRow: function(rowid) {
	       
	       	},
			gridComplete:function(){
			   if(treeIdArr){
				   $.each(treeIdArr, function(key,val){      
						 jQuery("#"+val).treeselect({
							   dataType:"sql",
							   optType:"single",
							   sql:treeSqlArr[key],
							   exceptnullparent:false,
							   selectParent:false,
							   lazy:false
							});
					  }); 
			   }
	           var dataTest = {"id":"hrPersonBatchEdit_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	       	} 
	    });
	    jQuery(hrPersonBatchEditGrid).jqGrid('bindKeys');
	    
	    /*-----------------------load hrPerson subSet structures--------------------------*/
		var mainTable = "v_hr_person_current";
	   	jQuery.ajax({
           url: 'getTableContentList',
           data: {mainTable:mainTable},
           type: 'post',
           dataType: 'json',
           async:false,
           error: function(data){
           },
           success: function(data){
        	 for(var num = 0;num< data.sysTableContents.length;num++){
        		jQuery("#hrPersonSelectMenuList").get(0).options.add(new Option(data.sysTableContents[num].name,data.sysTableContents[num].bdinfo.tableName));
        	 } 
           }
       	});
	});
	
	function batchEditFormatter(cellvalue, options, rowObject){
		var dataType = rowObject['fieldInfo']['dataType'];
		var code = rowObject['fieldInfo']['fieldCode'];
		var userTag = rowObject['fieldInfo']['userTag'];
		var parameter = rowObject['fieldInfo']['parameter1'];
		var parameterName = rowObject['fieldInfo']['parameter2'];
		var dataLength = rowObject['fieldInfo']['dataLength'];
		var editvalue;
		var mydate = new Date();
		var inputId="batchEdit"+mydate.getTime()+code;
		switch(dataType){
			case '1':
				if(userTag == 'select'){
					editvalue='<select   class="select">';
					editvalue+='<option value =""></option>';
					jQuery.ajax({
 	    		    	url: 'getFiledDataList',
 	    		        data: {code:parameterName},
 	    		        type: 'post',
 	    		        dataType: 'json',
 	    		        async:false,
 	    		        error: function(data){
 	    		        },
 	    		        success: function(data){ 
 	    		        	for(var optionItem = 0; optionItem<data.filedDataByCode.length;optionItem++){
 	    		        		editvalue+=' <option value ='+data.filedDataByCode[optionItem].value+'>'+data.filedDataByCode[optionItem].value+'</option>';
 	    		        	}
    		        	}
 	    		    });
					editvalue += '</select>';
				}else if(userTag == 'treeSelect'){
					editvalue = '<input type="text"  id="'+inputId+'"></input>';
					treeIdArr.push(inputId);
					treeSqlArr.push(parameter);
					inputId += "_id";
					editvalue += '<input type="hidden"  id="'+inputId+'"></input>';
				}else{
					if(!dataLength){
						dataLength=50;
					}
					editvalue = '<input type="text" maxlength="'+dataLength+'" id="'+inputId+'" onblur="lengthCheckForBatchEdit(this,\''+dataLength+'\')"></input>';
				}
				
				break;
			case '2':
				editvalue = '<input id="'+inputId+'"  type="text"  onblur="numberCheckForBatchEdit(this)"></input>';
				break;
			case '5':
				editvalue = '<input id="'+inputId+'" type="text"  onblur="digitCheckForBatchEdit(this)"></input>';
				break;
			case '6':
				editvalue = '<input id="'+inputId+'" type="text"  onblur="digitCheckForBatchEdit(this)"></input>';
				break;
			case '7':
				editvalue = '<input id="'+inputId+'" maxlength="50" type="text"  onblur="lengthCheckForBatchEdit(this,50)"></input>';
				break;
			case '3':
				editvalue = '<input  type="checkbox" name="'+code+'"></input>';
				break;
			case '4':
				editvalue = '<input  style="height:15px" class="Wdate input-small" onClick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" onFocus="WdatePicker({skin:\'ext\',isShowClear:true,readOnly:true})" type="text" ></input>';
				break;
			default:
				editvalue="";
		}
		return editvalue;
	}
	
	function menuListChange(value){
		var urlString = "getTableStructureList";
		urlString = urlString+"?tableCode="+value;
		urlString = encodeURI(urlString);
		jQuery("#hrPersonBatchEdit_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function saveHrPersonBatchEdit(){
		var tableCode = jQuery("#hrPersonSelectMenuList").val();
		var url = "saveSysTableData?oper=batchupdate&tableCode="+tableCode+"&id=${id}&navTabId=hrPersonCurrent_gridtable";
	    var sid = jQuery("#hrPersonBatchEdit_gridtable").jqGrid('getGridParam', 'selarrrow');
	    if(sid==null|| sid.length == 0){       	
			alertMsg.error("请选择需要保存记录。");
			return;
		}else{
			var code;
			var dataType;
			var userTag;
			var curInputObj;
			var jsonStr = "{";
			var gridAllfileds = "";
			for(var i = 0;i < sid.length; i++){
 				var rowId = sid[i];
 				var row = jQuery("#hrPersonBatchEdit_gridtable").jqGrid('getRowData',rowId);
 				code = row['fieldInfo.fieldCode'];
 				if(i==0){
 					gridAllfileds = code;
 				}else{
 					gridAllfileds += ","+ code;
 					
 				}
 		        dataType = row['fieldInfo.dataType'];
 		        userTag = row['fieldInfo.userTag'];
 		        if(i>0){
 		        	jsonStr += ",";
 		        }
 		        switch(dataType){
 					case '1':
 						if(userTag == 'select'){
 							curInputObj = jQuery("#"+sid[i]).find(".select");
 						}else if(userTag == 'treeSelect'){
 							curInputObj = jQuery("#"+sid[i]).find("input[type=hidden]");
 						}else{
 							curInputObj = jQuery("#"+sid[i]).find("input[type=text]");
 						}
 						jsonStr += '"'+code+'"'+":"+'"'+curInputObj.val()+'"';
 						break;
 					case '2':
 					case '5':
 					case '6':
 					case '7':
 					case '4':
 						curInputObj = jQuery("#"+sid[i]).find("input[type=text]");
 						jsonStr += '"'+code+'"'+":"+'"'+curInputObj.val()+'"';
 						break;
 					case '3':
 						curInputObj = jQuery("#"+sid[i]).find("input[name="+code+"]");
 						if(curInputObj.attr("checked")=="checked"){
 							jsonStr += '"'+code+'"'+":"+'"'+1+'"';
 						}else{
 							jsonStr += '"'+code+'"'+":"+'"'+0+'"';
 						}
 						break;
 					default:
 					}
				}
			}
			jsonStr += "}";
			jsonStr = "{"+'"'+"edit"+'"'+":[" +jsonStr+"]}";
			jQuery("#hrPersonBatchEdit_gridAllDatas").val(jsonStr);
			jQuery("#hrPersonBatchEdit_gridAllfileds").val(gridAllfileds);
	        jQuery("#hrPersonBatchEdit_search_form").attr("action",url);
			jQuery("#hrPersonBatchEdit_search_form").submit(); 
	}
	function lengthCheckForBatchEdit(obj,maxlength){
		var value = obj.value;
		var id = obj.id;
		if(value&&value.length>maxlength){
			value = value.substr(0,maxlength);
			jQuery("#"+id).val(value);
			alertMsg.error("输入的内容过长,最大长度为"+maxlength);
		}
	}
	function numberCheckForBatchEdit(obj){
		var value = obj.value;
		var id = obj.id;
		if(value&&parseInt(value)!=value){
			jQuery("#"+id).val("0");
			alertMsg.error("请输入整数");
		}
	}
	function digitCheckForBatchEdit(obj){
		var value = obj.value;
		var id = obj.id;
		if(value&&isNaN(value)){
			jQuery("#"+id).val("0");
			alertMsg.error("请输入数字");
		}
	}
</script>
<div class="page">
	<form id="hrPersonBatchEdit_search_form" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
		<s:hidden name="gridAllDatas" id="hrPersonBatchEdit_gridAllDatas"/>
		<s:hidden name="gridAllfileds" id="hrPersonBatchEdit_gridAllfileds"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<select id="hrPersonSelectMenuList" onchange=menuListChange(this.value)><option value ="v_hr_person_current">基本信息</option></select>
				</li>
				<li>
					<a class="savebutton" href="javaScript:saveHrPersonBatchEdit()" ><span><fmt:message key="button.save" /></span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:$.pdialog.closeCurrent();" ><span><fmt:message key="button.close" /></span></a>
				</li>
			</ul>
		</div>
		<div id="hrPersonBatchEdit_gridtable_div" layoutH="40" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
		<div id="load_hrPersonBatchEdit_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="hrPersonBatchEdit_gridtable"></table>
		</div>
	</div>
<!-- 	<div class="panelBar"> -->
<!-- 		<div class="pages"> -->
<%-- 			<span><s:text name="pager.perPage" /></span> <select id="hrPersonBatchEdit_gridtable_numPerPage"> --%>
<!-- 				<option value="20">20</option> -->
<!-- 				<option value="50">50</option> -->
<!-- 				<option value="100">100</option> -->
<!-- 				<option value="200">200</option> -->
<%-- 			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrPersonBatchEdit_gridtable_totals"></label><s:text name="pager.items" /></span> --%>
<!-- 		</div> -->

<!-- 		<div id="hrPersonBatchEdit_gridtable_pagination" class="pagination" -->
<!-- 			targetType="navTab" totalCount="200" numPerPage="20" -->
<!-- 			pageNumShown="10" currentPage="1"> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>
