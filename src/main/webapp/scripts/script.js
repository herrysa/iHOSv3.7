
// This function is used by the login screen to validate user/pass
// are entered.
function validateRequired(form) {
    var bValid = true;
    var focusField = null;
    var i = 0;
    var fields = new Array();
    oRequired = new required();

    for (x in oRequired) {
        if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
           if (i == 0)
              focusField = form[oRequired[x][0]];

           fields[i++] = oRequired[x][1];

           bValid = false;
        }
    }

    if (fields.length > 0) {
       focusField.focus();
       alert(fields.join('\n'));
    }
    return bValid;
}

// This function is a generic function to create form elements
function createFormElement(element, type, name, id, value, parent) {
    var e = document.createElement(element);
    e.setAttribute("name", name);
    e.setAttribute("type", type);
    e.setAttribute("id", id);
    e.setAttribute("value", value);
    parent.appendChild(e);
}

function confirmDelete(obj) {
    var msg = "Are you sure you want to delete this " + obj + "?";
    ans = confirm(msg);
    return ans;
}
/* This function is used to open a pop-up window */
function openWindow(url, winTitle, winParams) {
    winName = window.open(url, winTitle, winParams);
    winName.focus();
}
/* pop window with default params */
function popUpWindow(url, winTitle, cusWinParams) {
	var winParams;
	var screenWidth = parseInt(screen.availWidth);
	var screenHeight = parseInt(screen.availHeight);

	winParams = "width=" + screenWidth/2 + ",height="
			+ screenHeight/2+ ",left=" + screenWidth/4 + ",top="+screenHeight/4;
	winParams += ",channelmode=no,menubar=no,location=no,toolbar=no,scrollbars=yes,resizable=yes,status=no";
	if (cusWinParams == null || cusWinParams == "") {
		/*
		 * var screenWidth = parseInt(screen.availWidth); var screenHeight =
		 * parseInt(screen.availHeight);
		 * 
		 * winParams = "width=" + screenWidth/2 + ",height=" + screenHeight/2+
		 * ",left=" + screenWidth/4 + ",top="+screenHeight/4; winParams +=
		 * ",channelmode=yes,location=no,toolbar=no,scrollbars=no,resizable=no,status=no";
		 */
	} else {
		winParams =winParams+","+cusWinParams;
	}

	openWindow(url, winTitle, winParams);
}

//	deal grid after load,e:page,resize
jQuery.subscribe('onLoadSelect', function(event, data) {
	if(data.id){
		var dataType = jQuery("#"+data.id).jqGrid('getGridParam', 'datatype');
		if(dataType == 'local'){
			setTimeout(function(){
				jQuery('#'+data.id).jqGrid('setGridParam', {
					datatype : 'json'
				}).trigger("reloadGrid");
				$("#background,#progressBar").hide();
			},50);
		}else{
		var baseName = data.id.split("_")[0];
		//jQuery("#"+baseName+"_pageHeader").find(".subBar").show();
		//jQuery("#"+baseName+"_pageHeader").find(".searchContent").find(".buttonActive").show();
		//var header = jQuery("#"+baseName+"_pageHeader").outerHeight(true);
		/*if(header&&header!=0){
			jQuery("#"+baseName+"_pageHeader").find(".subBar").remove();
			
			var valve = 63;
			if(header>valve){
				jQuery("#"+baseName+"_pageHeader").find(".subBar").hide();
			}else{
				jQuery("#"+baseName+"_pageHeader").find(".searchContent").find(".buttonActive").hide();
			}
		}*/
	jQuery("#gview_"+data.id).find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
		jQuery(this).find("th").each(function(){
			jQuery(this).find("div").eq(0).css("line-height","18px");
		});
	}); 
	makepager(data.id);
	addGridButton(data.id);
	tabResize();
	gridResize(null,null,data.id,"single");
	}
	}
});

// make grid pager
function makepager(tableId){
	var currentPage = jQuery("#"+tableId).jqGrid('getGridParam', 'page');
	var records = jQuery("#"+tableId).jqGrid('getGridParam', 'records');
	var total = jQuery("#"+tableId).jqGrid('getGridParam', 'total');
	var rowNum = jQuery("#"+tableId).jqGrid('getGridParam', 'rowNum');
	
	if(total==null){
		total = Math.ceil(records/rowNum);
	}
	if(total==0){
		total = 1;
	}
	jQuery("#"+tableId+"_totals").text(records);
	
	$("#"+tableId+"_pagination").pagination({
		targetType:'navTab',
		rel:'',
		totalCount:records,
		numPerPage:rowNum,
		pageNumShown:10,
		currentPage:currentPage
	});
	 
		jQuery("#"+tableId+"_pagination").find("li").each(function(){
			
			var li = jQuery(this);
			var liText = li.find("a").eq(0).text();
			
			if(liText>total){
				// li.remove();
			}else{
			jQuery(this).find("a").eq(0).unbind("click").bind("click",function(){
				currentPage = jQuery("#"+tableId).jqGrid('getGridParam', 'page');
				var toPage = jQuery(this).text();
				if(toPage=="下一页"){
					toPage = currentPage+1;
				}else if(toPage=="上一页"){
					toPage = currentPage-1;
				}else if(toPage=="末页"){
					toPage = total;
				}else if(toPage=="首页"){
					toPage = 1;
				}
				
				jQuery('#'+tableId).jqGrid('setGridParam',{page:toPage}).trigger("reloadGrid");
				/*jQuery('#'+tableId).trigger('reloadGrid', [ {
					page : toPage
				} ]);*/
			});
			}
		});
		jQuery("#"+tableId+"_pagination").find(".jumpto").eq(0).find("input").eq(1).unbind("click").bind("click",function(){
			var toPage = jQuery("#"+tableId+"_pagination").find(".jumpto").eq(0).find("input").eq(0).val();
			jQuery('#'+tableId).trigger('reloadGrid', [ {
				page : toPage
			} ]);
		});
		jQuery("#"+tableId+"_numPerPage").empty();
		jQuery("#"+tableId+"_numPerPage").append("<option value='20'>20</option>");
		jQuery("#"+tableId+"_numPerPage").append("<option value='50'>50</option>");
		jQuery("#"+tableId+"_numPerPage").append("<option value='100'>100</option>");
		jQuery("#"+tableId+"_numPerPage").append("<option value='200'>200</option>");
		jQuery("#"+tableId+"_numPerPage").append("<option value='300'>300</option>");
		jQuery("#"+tableId+"_numPerPage").append("<option value='400'>400</option>");
		jQuery("#"+tableId+"_numPerPage").append("<option value='500'>500</option>");
		jQuery("#"+tableId+"_numPerPage").append("<option value='1000'>1000</option>");
		jQuery("#"+tableId+"_numPerPage").find("option").each(function(){
			if(jQuery(this).attr("value")==rowNum){
				jQuery(this).attr("selected","true");
			}
		});
		jQuery("#"+tableId+"_numPerPage").unbind("click").bind("change",function(){
			var rowNum = jQuery(this).val();
			jQuery('#'+tableId).jqGrid('setGridParam',{rowNum:rowNum,page:1}).trigger("reloadGrid");
			// (tableId);
		});
}

// add grid button. e:add,edit,delete
function addGridButton(tableId){
	// alert("addGridButton"+tableId);
	var baseName = tableId.substring(0,tableId.indexOf("_"));
	var buttonBar ;
	var flag = false;
	if(typeof(jQuery("#"+tableId+"_div").attr("buttonBar"))!="undefined"){
		buttonBar = jQuery("#"+tableId+"_div").attr("buttonBar");
		flag = true;
	}
	
	var width = getParam('width',buttonBar);
	var height = getParam('height',buttonBar);
	var base_URL = getParam('base_URL',buttonBar);
	var optId = getParam('optId',buttonBar);
	var fatherGrid = getParam('fatherGrid',buttonBar);
	var extraParam = getParam('extraParam',buttonBar);
	var layout = getParam('layout',buttonBar);
	var proportion = getParam('proportion',buttonBar);
	var selectNone = jQuery("#"+tableId+"_selectNone").text();
	var selectMore = jQuery("#"+tableId+"_selectMore").text();
	
	var addTitle=jQuery("#"+tableId+"_addTile").text();
	var editTitle=jQuery("#"+tableId+"_editTile").text();
	
	var navTabId = jQuery("#"+tableId+"_navTabId").attr("value");
	
	var edit_URL;
	var del_URL;
	
	var upperBaseName = baseName.substring(0,1).toUpperCase()+baseName.substring(1);
	if(width==null||width==""){
		width=600;
	}
	if(height==null||height==""){
		height=600;
	}
	if(base_URL!=null&&base_URL!=""){
		if(base_URL.indexOf("||")!=-1){
			edit_URL = base_URL.split("||")[0];
			del_URL = base_URL.split("||")[1];
		}else{
			edit_URL = base_URL
		}
		// edit_URL = "edit"+upperBaseName+"?popup=true";
	}else{
		edit_URL = "edit"+upperBaseName;
		del_URL = "del"+upperBaseName;
	}
	if(optId==null||optId==""){
		optId = baseName+"Id";
	}
	if(selectNone==null||selectNone==""){
		selectNone = "请选择记录。";
	}
	if(selectMore==null||selectMore==""){
		selectMore = "只能选择一条记录。";
	}
	if(addTitle==null||addTitle==""){
		addTitle = "New "+upperBaseName;
	}
	if(editTitle==null||editTitle==""){
		editTitle = "Edit "+upperBaseName;
	}
	/*
	 * if(layout=="true"){ makeLayout(baseName,tableId,layout,proportion); }
	 */
	jQuery("#"+tableId+"_add").unbind( 'click' ).bind("click",function(){
		var url = edit_URL+"?popup=true&navTabId="+tableId;
		if(fatherGrid!=null&&fatherGrid!=""&&extraParam!=null&&extraParam!=""){
			var fatherGridId = jQuery("#"+fatherGrid).jqGrid('getGridParam','selarrrow');
			if(fatherGridId==null || fatherGridId.length ==0){
				alertMsg.error(selectNone);
				return;
			}
		    if(fatherGridId.length>1){
				alertMsg.error(selectMore);
				return;
			}else{
				url += "&"+extraParam+"="+fatherGridId;
			}
		}
		var winTitle=addTitle;
		// alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, 'add'+upperBaseName, winTitle, {mask:false,width:width,height:height});　
	});
	jQuery("#"+tableId+"_edit").unbind( 'click' ).bind("click",function(){
		var sid = jQuery("#"+tableId).jqGrid('getGridParam','selarrrow');
	    if(sid==null || sid.length ==0){
			alertMsg.error(selectNone);
			return;
		}
	    if(sid.length>1){
			alertMsg.error(selectMore);
			return;
		}else{
			var url = edit_URL +"?popup=true&"+optId+"="+ sid+"&navTabId="+tableId;
			if(fatherGrid!=null&&fatherGrid!=""&&extraParam!=null&&extraParam!=""){
				var fatherGridId = jQuery("#"+fatherGrid).jqGrid('getGridParam','selarrrow');
				if(fatherGridId==null || fatherGridId.length ==0){
					alertMsg.error(selectNone);
					return;
				}
			    if(fatherGridId.length>1){
					alertMsg.error(selectMore);
					return;
				}else{
					url += "&"+extraParam+"="+fatherGridId;
				}
			}
			var winTitle=editTitle;
			// alert(url);
			url = encodeURI(url);
			$.pdialog.open(url, 'edit'+upperBaseName, winTitle, {mask:false,width:width,height:height});　
		}
		
	});
	jQuery("#"+tableId+"_del").unbind( 'click' ).bind("click",function(){
		var sid = jQuery("#"+tableId).jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#"+tableId).jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId="+tableId+"&oper=del";
		if(fatherGrid!=null&&fatherGrid!=""&&extraParam!=null&&extraParam!=""){
			var fatherGridId = jQuery("#"+fatherGrid).jqGrid('getGridParam','selarrrow');
			editUrl += "&"+extraParam+"="+fatherGridId;
		}
		editUrl = encodeURI(editUrl);
	    if(sid==null || sid.length ==0){
				alertMsg.error(selectNone);
				return;
		}else{
			// jQuery("#"+tableId).jqGrid('delGridRow',sid,{reloadAfterSubmit:false,left:300,top:150});
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
					
				}
			});
		}
		
	});
	
}

// assistant function ,get param from grid-div`s buttonbar
function getParam(paramName,buttonBar){
	if((buttonBar!=null&&buttonBar!=""&&buttonBar!="undefined")&&buttonBar.indexOf(";")!=-1){
		var paramArr = buttonBar.split(";");
		if(paramArr!=null){
			for(var p=0;p<paramArr.length;p++){
				if(paramArr[p].indexOf(":")!=-1&&paramArr[p].split(":")[0]==paramName){
					return paramArr[p].split(":")[1];
				}
			}
		}
	}
}

/*
 * 创建页面布局
 */
function makeLayout(params,changeData){
	var layoutObject = new Object();
	var proportion = params.proportion;
	var baseName = params.baseName;
	var activeGridTable = params.activeGridTable;// gzy add
	var tableIds= params.tableIds;
	var key = params.key;
	var differ = params.differ;
	var fullSize = params.fullSize;
	if(!fullSize){
		fullSize = containerHeight();
	}else{
		fullSize = jQuery("#container").innerHeight()-fullSize;
	}
	var southSize = fullSize / proportion;
	//zzhJsTest.debug("southSize:"+southSize);
	if(differ!="undefined"&&differ!=null&&differ!=""){
		fullSize = fullSize - differ;
	}
	var layoutSettings = {
			applyDefaultStyles : true // basic styling for testing & demo purposes
			,
			south__spacing_open : 0 // no resizer-bar when open (zero height)
			,
			south__spacing_closed : 0 // big resizer-bar when open (zero height)
			,
			south__maxSize : fullSize // 1/2 screen height
			,
			south__closable : true
			,
			south__size : southSize
			,
			/*south__onopen_start : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("southOpen:1"+layoutName);
				gridResize();
				jQuery("#"+tableIds.split(";")[0]+"_isShowSouth").val(1);
			},
			south__onopen_end : function(paneName,element,state,options,layoutName){
				tabResize();
				gridResize();
			},
			south__onclose_start : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("southOpen:0");
				jQuery("#"+tableIds.split(";")[0]+"_isShowSouth").val(0);
			},*/
			onresize_end : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("resize:"+paneName);
				/*var tableIdsArr = tableIds.split(";");
				var width = jLayout.state.center.innerWidth;
				var isShowSubGrid = jQuery("#"+tableIdsArr[0]+"_isShowSouth").val();
				var appendixWidth;
				if(isShowSubGrid==1){
					appendixWidth = -20;
				}
				if("center" == paneName){
					gridResize(null,null,tableIdsArr[0],"single",appendixWidth);
				}*/
				jQuery(window).trigger("tabResize");
				jQuery(window).trigger("layoutResize");
				jQuery(window).trigger("gridResize");
			}
			,
			south__initHidden:true
		};
	jQuery("#"+baseName+"_container").css("height",fullSize);
	// alert(jQuery("#"+baseName+"_container").css("height"));
	var jLayout = jQuery("#"+baseName+"_container").layout(layoutSettings);
	// layoutObj = jLayout;
	jQuery("#"+baseName+"_layout-center").css("padding",0);
	jQuery("#"+baseName+"_layout-south").css("padding",0);
	var pane = "south";
	// setTimeout("togglePane("+jLayout+")","50");
	
	layoutObject.jLayout = jLayout;
	
	var selectedSearchId = null;
	
	layoutObject.optClick = function(){
		if(jQuery("#"+tableIds.split(";")[1]+":visible").length>0){
			setTimeout(function(){
				if(!activeGridTable){
					selectedSearchId = jQuery("#"+baseName+"_gridtable").jqGrid('getGridParam','selarrrow');
				}else{// gzy add
					selectedSearchId = jQuery("#"+activeGridTable).jqGrid('getGridParam','selarrrow');
				}
				if(typeof(changeData)=='function'){
					changeData(selectedSearchId);
				}
			},100);
		}
	}
	
	layoutObject.optDblclick = function(){
		setTimeout(function(){
			if(!activeGridTable){
				selectedSearchId = jQuery("#"+baseName+"_gridtable").jqGrid('getGridParam','selarrrow');
			}else{// gzy add
				selectedSearchId = jQuery("#"+activeGridTable).jqGrid('getGridParam','selarrrow');
			}
		if(selectedSearchId==null||selectedSearchId.length==0){
			alertMsg.error("请选择记录！");
		}else{
			if(jQuery("#"+tableIds.split(";")[1]+":visible").length==0){
				if(typeof(changeData)=='function'){
					changeData(selectedSearchId);
				}
			}
			layoutObject.toggleSouth();
			layoutObject.resizeAll();
		}
		},100);
	}
	layoutObject.resizeAll = function(){
		this.jLayout.resizeAll();
	}
	layoutObject.toggleSouth = function(){
		this.jLayout.toggle('south');
	}
	layoutObject.closeSouth = function(){
		this.jLayout.close('south');
	}
	layoutObject.sizePane = function(block,size){
		this.jLayout.sizePane(block,size);
	}
	jLayout.resizeAll();
	
	jQuery("#"+baseName+"_unfold").unbind( 'click' ).bind("click",function(){
		layoutObject.sizePane('south',fullSize);
	});
	jQuery("#"+baseName+"_fold").unbind( 'click' ).bind("click",function(){
		layoutObject.sizePane('south',southSize);
	});
	jQuery("#"+baseName+"_close").unbind( 'click' ).bind("click",function(){
		layoutObject.sizePane("south",southSize);
		layoutObject.toggleSouth();
	});
	
	clearGarbage();
	return layoutObject;
}

function formCallBack(json){
	if (json.fieldErrors != null && json.fieldErrors.length !== 0) {
		alertMsg.error(json2str(json.fieldErrors));
	}else{
		ajaxDoneExtend(json);
		if ( json.statusCode != DWZ.statusCode.error && json.statusCode == DWZ.statusCode.ok || json.statusCode > 0){
			if (json.navTabId){
				// navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
				jQuery('#'+json.navTabId).trigger("reloadGrid");
			} else if (json.rel) {
				navTabPageBreak({}, json.rel);
			}
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrent();
			}
		}
	}
	// jQuery('#infodialog').html(json.message);
	// jQuery('#infodialog').dialog('open');
	
}
function formCallBackForInlineDiv(json){
	if (json.fieldErrors != null && json.fieldErrors.length !== 0) {
		alertMsg.error(json2str(json.fieldErrors));
	}else{
		ajaxDoneExtend(json);
		if ( json.statusCode != DWZ.statusCode.error && json.statusCode == DWZ.statusCode.ok ){
			if (json.navTabId){
				jQuery('#'+json.navTabId).trigger("reloadGrid");
			}
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrentDiv(json.dialogId);
			}
		}
	}
	// jQuery('#infodialog').html(json.message);
	// jQuery('#infodialog').dialog('open');
	
}
function optCallBack(json){
	alertMsg.info(json.message);
	if (json.navTabId){
		jQuery('#'+json.navTabId).trigger("reloadGrid");
	}
}
// 获取iframe大小
function getWidthAndHeigh(resize) {
	 var iframeHeight = jQuery(document).height();
	var iframeWidth = document.body.clientWidth;
	// firefox
	if (jQuery.browser.mozilla) {
		iframeHeight -= 130;
		if (resize) {
			iframeHeight -= 130;
		}
	} 
	// IE
	else {
		iframeHeight -= 130;
		if (resize) {
			iframeHeight -= 130;
		}
	}
	return {width: iframeWidth, height: iframeHeight}; 
} 

function setDefaultValue(obj,obj2){
	if(obj&&!obj.value&&obj2){
		obj2.val("");
	}
	if(/^\s*$/.test(obj.value)){
		if(obj2){
			obj2.attr("value","");
		}
		if(obj.defaultValue=='拼音/汉字/编码'){
			obj.value=obj.defaultValue;
			obj.style.color='#aaa'
		}
		}
	}
function setTextColor(obj){obj.style.color='#333'}

function checkId(obj,entityName,searchItem,returnMessage){
	// var url =
	// 'checkId?entityName='+entityName+'&searchItem='+searchItem+'&searchValue='+obj.value+;
	var url = 'checkId';
	url = encodeURI(url);
	$.ajax({
	    url: url,
	    type: 'post',
	    data:{entityName:entityName,searchItem:searchItem,searchValue:obj.value,returnMessage:returnMessage},
	    dataType: 'json',
	    aysnc:false,
	    error: function(data){
	        
	    },
	    success: function(data){
	        // do something with xml
	        if(data!=null){
	        	 alertMsg.error(data.message);
			     obj.value="";
	        }
	    }
	});
}
/**
 * 行编辑START
 */
/*var cancelSel;
var editDataId;
var editRowCount = 0;*/
function gridEditRow(grid) {
	var editDataId = grid.jqGrid('getGridParam','selrow');
	if(editDataId){
		grid.jqGrid('editRow',editDataId,true);
	}else{
		alertMsg.error("请选择一条记录！");
	}
	/*if(editRowCount<1&&editDataId){
		grid.jqGrid('editRow',editDataId,true);
		cancelSel=editDataId;
		editRowCount++;
	}else{
		alertMsg.info("一次只能编辑一条数据或者您还没有选择数据!");
	}*/
}
function gridRestore(grid){
	var editDataId = grid.jqGrid('getGridParam','selrow');
	var cancelSel ;
	if(editDataId){
		cancelSel = editDataId;
	}else{
		cancelSel = "new_row";
	}
	grid.jqGrid('restoreRow',cancelSel);
	//editDataId="";
	//cancelSel="";
	//editRowCount=0;
	}
function gridAddRow(grid){
	try{
	//if(editRowCount>=1){
		//alertMsg.warn('一次只能添加一行!')
	//}else{
		var editing = jQuery("#new_row",grid);
		if(editing.length==0){
			grid.jqGrid('addRow');
		}else{
			alertMsg.error('一次只能添加一行!');
		}
		//grid.jqGrid('addRow');
		//editDataId="new_row";
		//cancelSel="new_row";
		//editRowCount++;
	//}
	}catch(e){
		alertMsg.error(e.name + ":" + e.message);
	}
}
function gridSaveRow(grid){
	// var ids = jQuery("#acctcostmap_gridtable").jqGrid('getDataIDs');
	try{
		var currentPage = grid.jqGrid('getGridParam', 'page'); 
		var editDataId = grid.jqGrid('getGridParam','selrow');
		if(!editDataId){
			editDataId = "new_row";
		}
		grid.jqGrid('saveRow',editDataId, checksave);
		
		grid.trigger('reloadGrid', [{page: currentPage }]);;
		//editRowCount=0;
		//editDataId="";
		//cancelSel="";
	}catch(e){
		alertMsg.error(e.name + ":" + e.message);
	}
}
function checksave(result) {
	var data=result.responseText;
 	if(parseInt(data.indexOf("300"))>0){
		alertMsg.error("保存失败，原因：代码重复，请重试！");
	}
	if (result.responseText=="") {alertMsg.error("保存失败!"); return false;}
	return true; 
}
/**
 * 行编辑END
 */

function resizeWindow(){
	jQuery(window).trigger("resize");
}

function uploadCallback(data){
	if(typeof(data)=='object'){
		formCallBack(data);
	}else{
		var jsonData = data.substring(data.indexOf("{"),data.indexOf("}")+1);
		if(jQuery.browser.msie){
			jsonData = eval("(" +jsonData+ ")");
	    }else{
	    	jsonData = JSON.parse(jsonData);
	    }
		formCallBack(jsonData);
	}
}
function uploadCallbackForInlineDiv(data){
	if(typeof(data)=='object'){
		formCallBackForInlineDiv(data);
	}else{
		var jsonData = data.substring(data.indexOf("{"),data.indexOf("}")+1);
		if(jQuery.browser.msie){
			jsonData = eval("(" +jsonData+ ")");
	    }else{
	    	jsonData = JSON.parse(jsonData);
	    }
		formCallBackForInlineDiv(jsonData);
	}
	
}
function toggleFilterArea(baseName,type,buttonId){
	if(jQuery("#"+baseName+"_pageHeader").is(':visible')){
		jQuery("#"+baseName+"_pageHeader").hide();
		if(buttonId&&jQuery("#"+buttonId).length>0){
			jQuery("#"+buttonId).find("span:first").text("显示查询区");
		}
	}else{
		jQuery("#"+baseName+"_pageHeader").show();
		if(buttonId&&jQuery("#"+buttonId).length>0){
			jQuery("#"+buttonId).find("span:first").text("关闭查询区");
		}
	}
	gridContainerResize(baseName,type);
}
function containerHeight(){
	return (jQuery("#container").innerHeight() - jQuery("div.tabsPageHeader").outerHeight());
}
function workAreaHeight(baseName){
	var filterAreaH = 0 , buttonBarAreaH = 0 ; 
	if(jQuery("#"+baseName+"_pageHeader").length>0&&jQuery("#"+baseName+"_pageHeader").is(':visible')){
		filterAreaH = jQuery("#"+baseName+"_pageHeader").outerHeight();
	}
	if(jQuery("#"+baseName+"_buttonBar").length>0){
		buttonBarAreaH = jQuery("#"+baseName+"_buttonBar").outerHeight();
	}
	
	return (jQuery("#container").innerHeight() - jQuery("div.tabsPageHeader").outerHeight() - filterAreaH - buttonBarAreaH);
}
function gridContainerResize(baseName,type,appendixWidth,appendixHeight){
	var h = workAreaHeight(baseName);
	appendixWidth = appendixWidth?appendixWidth:0;
	appendixHeight = appendixHeight?appendixHeight:0;
	var pageBarAreaH = 0;
	if(jQuery("#"+baseName+"_pageBar").length>0){
		pageBarAreaH = jQuery("#"+baseName+"_pageBar").outerHeight();
	}
	if(type=="div"){
		jQuery("#"+baseName+"_gridtable_div").height(h - pageBarAreaH - 4 - appendixHeight);
	}else if(type=="layout"){
		jQuery("#"+baseName+"_container").height(h-1- appendixHeight);
		jQuery("#"+baseName+"_gridtable_div").height(h - pageBarAreaH - 6 - appendixHeight);
		var jLayout = jQuery("#"+baseName+"_container").data('Instance');
		jLayout.resizeAll();
	}else if(type=="fullLayout"){
		var fullWorkSpace = jQuery("#container").innerHeight() - jQuery("div.tabsPageHeader").outerHeight();
		jQuery("#"+baseName+"_container").height(fullWorkSpace-1- appendixHeight);
		jQuery("#"+baseName+"_gridtable_div").height(h - pageBarAreaH - 6 - appendixHeight);
		var jLayout = jQuery("#"+baseName+"_container").data('Instance');
		jLayout.resizeAll();
	}
}
function gridResize(e,tablecontainer,tableId,type,appendixWidth,appendixHeight){
	var gridW = 0,gridH = 0;
	if(appendixWidth){
		gridW += appendixWidth;
	}
	if (appendixHeight) {
		gridH += appendixHeight;
	}
	if("single"==type){
		if(!tableId) return;
		if(!tablecontainer){
			tablecontainer = jQuery("#"+tableId+"_div").attr("tablecontainer");
			if(!tablecontainer){
				tablecontainer = tableId+"_div";
			}
		}
		if(tableId&&tableId.indexOf('_gridtable')!=-1){
			gridW = jQuery("#"+tablecontainer).width();
			gridH = jQuery("#"+tablecontainer).height();
			var extraHeight = jQuery("#"+tableId+"_div").attr("extraHeight");
			if(!extraHeight){
				gridH -= 28;
			}
			//zzhJsTest.debug("singleGrid:"+tablecontainer);
			resizeGridSize(tableId,gridW,gridH);
		}else if(tableId&&tableId.indexOf('_defTable')!=-1){
			//tablecontainer = jQuery("#"+tableId+"_div").attr("tablecontainer");
			if(tablecontainer){
				gridW = jQuery("#"+tablecontainer).width();
				gridH = jQuery("#"+tablecontainer).height();
				var extraHeight = jQuery("#"+tableId+"_div").attr("extraHeight");
				var extraWidth = jQuery("#"+tableId+"_div").attr("extraWidth");
				if(extraHeight){
					gridH -= extraHeight;
				}
				if(extraWidth){
					gridW -= extraWidth;
				}
				jQuery("#"+tableId+"_div").height(gridH);
				jQuery("#"+tableId+"_div").width(gridW);
				//zzhJsTest.debug("allGridWithContainer:"+tablecontainer);
			}
		}
	}else{
			jQuery("table[id$='_gridtable']:visible").each(function(){
				//zzhJsTest.debug("al");
				tableId = jQuery(this).attr("id");
				//if(tableId&&tableId.indexOf('_gridtable')!=-1){
					tablecontainer = jQuery("#"+tableId+"_div").attr("tablecontainer");
					if(tablecontainer){
						gridW = jQuery("#"+tablecontainer).width();
						gridH = jQuery("#"+tablecontainer).height();
						//zzhJsTest.debug("allGridWithContainer:"+tablecontainer);
					}else{
						gridW = jQuery("#"+tableId+"_div").width();
						gridH = jQuery("#"+tableId+"_div").height();
						var extraHeight = jQuery("#"+tableId+"_div").attr("extraHeight");
						if(!extraHeight){
							gridH -= 28;
						}

						//zzhJsTest.debug("normalGrid:"+tableId);
					}
					resizeGridSize(tableId,gridW,gridH);
				//}
			});
			jQuery("table[id$='_defTable']:visible").each(function(){
				tableId = jQuery(this).attr("id");
				//if(tableId&&tableId.indexOf('_defTable')!=-1){
					tablecontainer = jQuery("#"+tableId+"_div").attr("tablecontainer");
					if(tablecontainer){
						gridW = jQuery("#"+tablecontainer).width();
						gridH = jQuery("#"+tablecontainer).height();
						var extraHeight = jQuery("#"+tableId+"_div").attr("extraHeight");
						var extraWidth = jQuery("#"+tableId+"_div").attr("extraWidth");
						if(extraHeight){
							gridH -= extraHeight;
						}
						if(extraWidth){
							gridW -= extraWidth;
						}
						jQuery("#"+tableId+"_div").height(gridH);
						jQuery("#"+tableId+"_div").width(gridW);
						//zzhJsTest.debug("allGridWithContainer:"+tablecontainer);
					}
				//}
			});
		//});
	}
	clearGarbage();
}

function resizeGridSize(tableId,gridW,gridH){
	var extraWidth = 0,extraHeight = 0;
	extraHeight = jQuery("#"+tableId+"_div").attr("extraHeight");
	extraWidth = jQuery("#"+tableId+"_div").attr("extraWidth");
	if(!extraHeight)  extraHeight = 0;
	if(!extraWidth)  extraWidth = 4;
	gridH -= parseInt(extraHeight);
	gridW -= parseInt(extraWidth);
	//zzhJsTest.debug("resizeGridSize:"+extraHeight+" gridH:"+gridH);
	jQuery("#"+tableId).jqGrid('setGridHeight',gridH);
	jQuery("#"+tableId).jqGrid('setGridWidth',gridW);
	//resizeCol(tableId,gridW,gridH);
	if(!(jQuery.browser.msie||jQuery.browser.mozilla)){
		jQuery("#gview_"+tableId).find(".ui-jqgrid-htable:first").css({'overflow-x':'hidden'});
		jQuery("#"+tableId).css({'overflow-x':'hidden'});
	}
	extraWidth = null,extraHeight = null,tableId = null,gridW = null,gridH = null;
}

/*function resizeCol(tableId,gridW,gridH){
	var remainWidth = gridW - jQuery("#"+tableId).width();
	if(jQuery("#"+tableId).height()>gridH){
		if(remainWidth&&remainWidth>0) remainWidth -= 20;
		else if (remainWidth&&remainWidth<0) remainWidth -= 20;
	}
	if(remainWidth){
		var thLength = jQuery("#gview_"+tableId).find(".ui-jqgrid-htable:first").find("th:visible").length;
		var excludeColNum = 0,extendWidth = 0;
		var rnColuId = jQuery("#gview_"+tableId).find(".ui-jqgrid-htable:first").find("th:visible").eq(0).attr("id");
		var cbColumId = jQuery("#gview_"+tableId).find(".ui-jqgrid-htable:first").find("th:visible").eq(1).attr("id");
		if(rnColuId && -1 != rnColuId.indexOf("_rn")){
			excludeColNum++;
		}
		if(cbColumId && -1 != cbColumId.indexOf("_cb")){
			excludeColNum++;
		}
		extendWidth = remainWidth/(thLength-excludeColNum);
		extendWidth = Math.floor(extendWidth);
		var remedyWidth = remainWidth - extendWidth*(thLength-excludeColNum);
		remedyWidth = Math.abs(remedyWidth);
		//zzhJsTest.debug("resizeCol:"+tableId+" extendWidth:"+extendWidth+" remedyWidth:"+remedyWidth);
		var c= 0;
		jQuery("#gview_"+tableId).find(".ui-jqgrid-htable:first").find("th:visible").each(function(){
			c++;
			var thId = jQuery(this).attr("id");
			if(c==thLength-1){
				jQuery(this).width(jQuery(this).width()+extendWidth+remedyWidth);
			}else if (thId.indexOf("_rn")==-1&&thId.indexOf("_cb")==-1) {
				jQuery(this).width(jQuery(this).width()+extendWidth);
			};
		});
		c = 0;
		jQuery("#"+tableId).find(".jqgfirstrow:first").find("td").each(function(){
			c++;
			if (c == thLength-1) {
				jQuery(this).width(jQuery(this).width()+extendWidth+remedyWidth);
			}else if (excludeColNum<c) {
				jQuery(this).width(jQuery(this).width()+extendWidth);
			};
		});
	}
}*/
 function tabResize(){
 	var gridW,gridH,extraWidth = 0,extraHeight = 0,tabcontainer;
 	jQuery(".tabs:visible").each(function(){
		extraHeight = jQuery(this).attr("extraHeight");
		extraWidth = jQuery(this).attr("extraWidth");
		if(!extraHeight)  extraHeight = 0;
		if(!extraWidth)  extraWidth = 0;
 		tabcontainer = jQuery(this).attr("tabcontainer");
 		if(tabcontainer){
 			gridW = jQuery("#"+tabcontainer).width();
 			gridH = jQuery("#"+tabcontainer).height();
 			gridH -= parseInt(extraHeight);
 			gridW -= parseInt(extraWidth);
 			jQuery(this).width(gridW);
 			jQuery(this).height(gridH);
 			jQuery(this).find(".tabsContent:first").width(gridW-2);
 			jQuery(this).find(".tabsContent:first").height(gridH-33);
 		}
 	});
 }
 
 function layoutResize(type,container,appendixWidth,appendixHeight){
	 if("single"==type){
		 
	 }else{
		 jQuery(".ui-layout-container").each(function(){
			 var container = jQuery(this).attr("layoutcontainer");
			 if(container){
				 var fullSize = jQuery("#"+container).innerHeight();
				 jQuery(this).css("height",fullSize);
				 var layoutObj = jQuery(this).data("layoutObj");
				 if(layoutObj){
					 layoutObj.resizeAll();
				 }
			 }
		 });
	 }
	 
 }

jQuery(window).unbind("gridResize").bind("gridResize",gridResize);
jQuery(window).unbind("tabResize").bind("tabResize",tabResize);
jQuery(window).unbind("layoutResize").bind("layoutResize",layoutResize);
jQuery(window).resize(function(e) {
	if(e.target==window){
		setTimeout(function(){
			jQuery(window).trigger("tabResize");
			jQuery(window).trigger("gridResize");
		},100);
	}
	});

function resizeWorkSpace(){
	jQuery(window).trigger("tabResize");
	jQuery(window).trigger("gridResize");
}
//$( window ).wresize( resizeWorkSpace ); 

var currentCheckPeriod = jQuery("#curPeriodInMain").text().replace("核算期间：","");
var currentPerson = jQuery("#curPerson").text();
function checkPriviliage(checkPeriod,person){
	currentCheckPeriod = jQuery("#curPeriodInMain").text().replace("核算期间：","");
	currentPerson = jQuery("#curPerson").text();
	if((currentCheckPeriod === checkPeriod)&&(person===currentPerson||person==="")){
		return true;
	}else{
		return false;
	}
}

jQuery.unsubscribe('chargeValidate');
jQuery.subscribe('chargeValidate', function(event, data) {
	// ii++;
	// jQuery("#testss").text(ii);
	var s = jQuery("#"+data.id).jqGrid('getGridParam','selarrrow');
	for(var si=0;si<s.length;si++){
		var row = jQuery("#"+data.id).jqGrid('getRowData',s[si]);
		var selectOper = row['operator.personId'];
		var checkPeriod = row['checkPeriod'];
		
		var priviliage = checkPriviliage(checkPeriod,selectOper);
		if(!priviliage){
			jQuery("#"+data.id).jqGrid('setSelection',s[si]);
		}
	}
	
});

jQuery.unsubscribe('chargeValidateStatu');
jQuery.subscribe('chargeValidateStatu', function(event, data) {
	var s = jQuery("#"+data.id).jqGrid('getGridParam','selarrrow');
	for(var si=0;si<s.length;si++){
		var row = jQuery("#"+data.id).jqGrid('getRowData',s[si]);
		var checkPeriod = row['checkPeriod'];
		var priviliage = checkPriviliaged(checkPeriod);
		if(priviliage){
			jQuery("#"+data.id).jqGrid('setSelection',s[si]);
		}
	}
	
});

function checkPriviliaged(checkPeriod){
	currentCheckPeriod = jQuery("#curPeriodInMain").text().replace("核算期间：","");
	var cbStatus="0";
	jQuery.ajax({
		dataType : 'json',
		url:'getCBStatus?pageCheckPeriod='+checkPeriod,
	    type: 'POST',
	    async:false,
	    error: function(data){
	    	alert(":error");
	    },success: function(data){
	    	cbStatus = data["statuCB"];
	    	//alert(cbStatus);
	    }
	});
	//alert("currentCheckPeriod:"+currentCheckPeriod+",checkPeriod:"+checkPeriod+",cbStatus="+cbStatus);
	if(cbStatus=="1"){
		return true;
	}else{
		return false;
	}
}

function fullGridEdit(gridId){
	try{
	var ids = jQuery(gridId).jqGrid('getDataIDs');
for(var i=0;i < ids.length;i++){
	var cl = ids[i];
	jQuery(gridId).editRow(cl);
}	
	}catch(err){
		alert(err);
	}
}
function executeSp(spName,tableId){
	var taskName =spName;
	// var proArgsStr ="${checkPeriod},${personId}";
	var exeCallback;
	var comfirmMsg = "确认执行？";
	var url = 'executeSpNoParam';
	if(arguments.length>2){
		if(arguments[2]){
			comfirmMsg = arguments[2];
		}
	}
	if(arguments.length>3){
		var waittingMsg = arguments[3];
		if(waittingMsg){
			jQuery("#progressBar").html(arguments[3]);
		}
	}
	if(arguments.length>4){
		url = arguments[4];
	}
	if(arguments.length==6){
		exeCallback = arguments[5];
	}
	if (url.indexOf('taskName')==-1) {
		if (url.indexOf('?')==-1) {
			url += '?taskName='+taskName;
		}else{
			url += '&taskName='+taskName;
		}
	}
	url = encodeURI(url);
	alertMsg.confirm(comfirmMsg, {
		okCall: function(){
			$.ajax({
			    url: url,
			    type: 'post',
			    //data:{taskName:taskName},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        jQuery("#progressBar").html("数据加载中，请稍等...");
			    },
			    success: function(data){
			    	jQuery("#progressBar").html("数据加载中，请稍等...");
			    	if(tableId){
			    		data.navTabId = tableId
			    	}
			    	formCallBack(data);
			    	if(typeof(exeCallback)=='function'){
			    		exeCallback();
			    	}
			    }
			});
		},
		cancelCall: function(){
			jQuery("#progressBar").html("数据加载中，请稍等...");
		}
	});
}
function executeSpU(spName,tableId){
	var taskName =spName;
	// var proArgsStr ="${checkPeriod},${personId}";
	var exeCallback;
	var comfirmMsg = "确认执行？";
	var url = 'executeSpNoParamU';
	if(arguments.length>2){
		if(arguments[2]){
			comfirmMsg = arguments[2];
		}
	}
	if(arguments.length>3){
		var waittingMsg = arguments[3];
		if(waittingMsg){
			jQuery("#progressBar").html(arguments[3]);
		}
	}
	if(arguments.length>4){
		url = arguments[4];
	}
	if(arguments.length==6){
		exeCallback = arguments[5];
	}
	if (url.indexOf('taskName')==-1) {
		if (url.indexOf('?')==-1) {
			url += '?taskName='+taskName;
		}else{
			url += '&taskName='+taskName;
		}
	}
	url = encodeURI(url);
	alertMsg.confirm(comfirmMsg, {
		okCall: function(){
			$.ajax({
			    url: url,
			    type: 'post',
			    //data:{taskName:taskName},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        jQuery("#progressBar").html("数据加载中，请稍等...");
			    },
			    success: function(data){
			    	jQuery("#progressBar").html("数据加载中，请稍等...");
			    	if(tableId){
			    		data.navTabId = tableId
			    	}
			    	formCallBack(data);
			    	if(typeof(exeCallback)=='function'){
			    		exeCallback();
			    	}
			    }
			});
		},
		cancelCall: function(){
			jQuery("#progressBar").html("数据加载中，请稍等...");
		}
	});
}
function ajaxDoneExtend(json){
	if(json.statusCode===undefined&&json.message===undefined){
		if(alertMsg)return alertMsg.error(json);
		else return alert(json);
	}
	if(json.statusCode==DWZ.statusCode.error){
		if(json.message&&alertMsg)alertMsg.error(json.message);
	}else if(json.statusCode==DWZ.statusCode.timeout){
		if(alertMsg)alertMsg.error(json.message||DWZ.msg("sessionTimout"),{okCall:DWZ.loadLogin});
		else DWZ.loadLogin();
	}else if (json.statusCode == 1) {
		if(json.message&&alertMsg) alertMsg.warn(json.message);
	}
	else if(json.statusCode > 1 && json.statusCode!=200){
		if(json.message&&alertMsg) alertMsg.correctUnclose(json.message);
	}else{
		if(json.message&&alertMsg)alertMsg.correct(json.message);
	}
}

function propertyFilterSearch(searchAreaId,gridId,noReload) {
	try{
		var sdata =$('#'+searchAreaId).serializeObject();
		//console.log(sdata);
		var postData = $("#"+gridId).jqGrid("getGridParam", "postData");
		$.extend(postData, sdata);
		//console.log(postData);
		$("#"+gridId).setGridParam({
			postData : postData
		});
		if(!noReload){
			$("#"+gridId).jqGrid("setGridParam", {
				search : true
			}).trigger("reloadGrid", [ {
					page : 1
			}]);
		}
	}catch(e){
		alert(e.message);
	}
}
$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
	//	console.log(a);
		var addFilters = false;
		$.each(a, function() {
			if(this.name.indexOf('_exclude_')==-1){
				var name = this.name ,v ;
				if(!this.value){
					v = '';
				}else{
					v = this.value;
					if(name.indexOf('_notInFilter')!=-1){
						name = name.replace('_notInFilter','');
					}else if(name.indexOf('{')!=-1){
						var nameRex = matchRegExp(name);
						if(nameRex){
							var n = nameRex[0].substring(1,nameRex[0].length-1);
							var vArr = v.split(',');
							var vStr = "";
							for(var vIdnex = 0;vIdnex<vArr.length;vIdnex++){
								var vTemp = vArr[vIdnex];
								vStr += "'"+vTemp+"',";
							}
							if(vStr){
								vStr = vStr.substring(0,vStr.length-1);
								v = n + " in ("+vStr+")";
								addFilters = true;
							}
						}
					}else{
						addFilters = true;
					}
				}
				if (o[name]) {
					if (!o[name].push) {
						o[name] = [ o[name] ];
					}
					o[name].push(v);
				} else {
					o[name] = v;
				}
			}
		});
		if (o['addFilters']) {
			if (!o['addFilters'].push) {
				o['addFilters'] = [ o['addFilters'] ];
			}
			o['addFilters'].push(addFilters);
		} else {
			o['addFilters'] = addFilters;
		}
		return o;
}
/**
 * 自动补全相关 start
 * 
 * @param x
 * @returns
 */
function getId(s) {
	var s = "" + s;
	var p = s.lastIndexOf(":");
	if (p > -1) {
		var t = s.substring(p + 1);
		return t;
	}
	return s;
}
function dropId(s) {
	// alert(s)
	var s = "" + s;
	var p = s.lastIndexOf(":");
	if (p > -1) {
		var t = s.substring(0, p);
		var t = s.substring(s.indexOf(",") + 1, p);
		return t;
	}
	return s;
}

function clearInput(o,o2) {
	if (o.value == '拼音/汉字/编码') {
		o.value = "";
	}else if(o.value=="" || o.value==null){
		// jQuery("#costItemId").attr("value","");
		o.attr("value","");
		if(o2){
			o2.attr("value","");
		}
	}
}

function formatNum(val){   
	if(val!=''&&!isNaN(val)){   
	re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;   
	n=val.replace(re,"$1,");   
	return n;}else return "error";   
} 

function toDecimal(x,place) {    
    var f = parseFloat(x);  
    if (isNaN(f)) {  
        return;  
    }
    if(!place&&place!=0){
    	f = Math.round(x*100)/100;  
    	f = parseFloat(f.toFixed(2));
    }else if(place==4){
    	f = Math.round(x*10000)/10000;
    	f = parseFloat(f.toFixed(4));
    }else if(place==3){
    	f = Math.round(x*1000)/1000;
    	f = parseFloat(f.toFixed(3));
    }else if(place==2){
    	f = Math.round(x*100)/100;
    	f = parseFloat(f.toFixed(2));
    }else if(place==1){
    	f = Math.round(x*10)/10;
    	f = parseFloat(f.toFixed(1));
    }else{
    	f = Math.round(x);
    }
    return f;  
}
function json2str(o) {
	var arr = [];
 	var fmt = function(s) {
	if (typeof s == 'object' && s != null) return json2str(s);
 		return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
 	}
 	for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));
 		return '{' + arr.join(',') + '}';
}

function changeSelectRow(grid,type){
	var sid = jQuery(grid).jqGrid('getGridParam','selarrrow');
	//DWZ.debug(jQuery(grid).attr("id"));
	if(sid.length>1){
		jQuery(grid).jqGrid('resetSelection');
		jQuery(grid).jqGrid('setSelection',sid[0]);
	}else{
		var ids = jQuery(grid).jqGrid('getDataIDs');
		var sId;
		var focusInput = jQuery(grid).find("input:focus");
		var hasInput = false;
		var focusIndex;
		var toFocusInput;
		if(focusInput.length>0){
			focusIndex = jQuery(focusInput).parent().index();
			hasInput = true;
		}
		for(var idindex=0;idindex<ids.length;idindex++){
			if(ids[idindex]==sid){
				if(type=='down'&&idindex==ids.length-1){
					sId = ids[0];
				}else if(type=='up'&&idindex==0){
					sId = ids[ids.length-1];
				}else if(type=='up'){
					sId = ids[idindex-1];
				}else{
					sId = ids[idindex+1];
				}
				break;
			}
		}
		jQuery(grid).jqGrid('resetSelection');
		jQuery(grid).jqGrid('setSelection',sId);
		jQuery(grid).data('selectId',sId);
		if(hasInput){
			toFocusInput = jQuery("#"+sId).find("td").eq(focusIndex).find("input:first");
			jQuery(toFocusInput).focus();
			var sIdContent = jQuery(toFocusInput).val();
			jQuery(toFocusInput).setSelectionForInput(0,sIdContent.length);
		}
		
	}
}

function chargeKeyPress(grid,e){
	var changeSelect = jQuery(grid).data("changeSelect");
	if(changeSelect=='false'){
		return ;
	}
	var keyValue = (e.keyCode) || (e.which) || (e.charCode);
	if(keyValue=='38'){
		changeSelectRow(grid,'up');
	}else if(keyValue=='40'){
		changeSelectRow(grid,'down');
	}
}
//codeRule START
function isMatch(obj,codeRule){
	if(obj instanceof Object){
		obj = obj.value;
	}
	var rules = codeRule.split('-');
	var num = 0;
	for(index in rules){
		num += Number(rules[index]);
		if(obj.length<num){
			break;
		}
		if(obj.length==num){
			return true;
		}
	}
	return false;
}

function getFather(obj,codeRule){
	if(obj instanceof Object){
		obj = obj.value;
	}
	var rules = codeRule.split("-");
	for(var i=1,j=Number(rules[0]);i<=rules.length;j+=Number(rules[i]),i++){
		if(obj.length==j){
			if(i==1){
				return obj;
			}else{
				return obj.substr(0,obj.length-Number(rules[i-1]));
			}
		}
		if(i==obj.length){
			break;
		}
	}
	return null;
}	
//codeRule END
//get first month day of date
function getFirstDayOfMonth(periodt){
	var period = periodt+"";
	var date = period.substr(0,4)+"-"+period.substr(4,6)+"-01";
	return date;
}
//higher search
function highSearch(grid,entityName){
	var cols="",titles="",hiddens="";
	var colModel = jQuery("#"+grid).jqGrid("getGridParam",'colModel');  
	for(var iCol=0;iCol<colModel.length;iCol++){
		if(colModel[iCol].highsearch){
			if(iCol==colModel.length-1){
				cols += colModel[iCol].name;
				titles += colModel[iCol].label;
				hiddens += colModel[iCol].hidden;
			}else{
				cols += colModel[iCol].name+",";
				titles += colModel[iCol].label+",";
				hiddens += colModel[iCol].hidden+",";
			}
		}
	}
	var url = "colSearchList?entityName="+entityName+"&grid="+grid+"&cols="+cols+"&titles="+titles+"&hiddens="+hiddens;
	url = encodeURI(url);
	$.pdialog.open(url, 'highSearch', "高级查询", {
		mask : true,
		maxable : false,
		resizable : false,
		width : 360,
		height : 500
	});
}
function setColShow(grid,entityName){
	var cols="",titles="",hiddens="",colWidths="";
	var colModel = jQuery("#"+grid).jqGrid("getGridParam",'colModel');  
	for(var iCol=0;iCol<colModel.length;iCol++){
		if(colModel[iCol]&&colModel[iCol].highsearch){
			if(iCol==colModel.length-1){
				cols += colModel[iCol].name;
				titles += colModel[iCol].label;
				hiddens += colModel[iCol].hidden;
			}else{
				cols += colModel[iCol].name+",";
				titles += colModel[iCol].label+",";
				hiddens += colModel[iCol].hidden+",";
			}
			if(colModel[iCol].width){
				colWidths += colModel[iCol].width+",";
			}else{
				colWidths += 80+",";
			}
			
		}
	}
	var url = "colShowList?entityName="+entityName+"&grid="+grid+"&cols="+cols+"&titles="+titles+"&hiddens="+hiddens+"&colWidths="+colWidths;
	url = encodeURI(url);
	$.pdialog.open(url, 'setColShow', "设置表格列", {
		mask : true,
		maxable : false,
		resizable : false,
		width : 350,
		height : 500
	});
}

function initColumn(grid,entityName,initFlag){
	if(initFlag==1){
		return 1;
	}
	$.ajax({
	    url: 'getColShowByEntity',
	    type: 'post',
	    data:{entityName:entityName},
	    dataType: 'json',
	    async:false,
	    error: function(data){
	    	 jQuery('#ajaxError').html(data.responseText);
	    },
	    success: function(data){
	        var colShows = data.colShows;
	        var gridColModel = jQuery("#"+grid).jqGrid("getGridParam",'colModel');  
			var extraCols = new Map();
			var inContrlCols = new Map();
			var oldOrder=new Array();
			var newOrder=new Array();
			var colArray = new Map();
		    for(var gci=0;gci<gridColModel.length;gci++){
		    	if(!gridColModel[gci].highsearch){
		    		extraCols.put(gridColModel[gci].name,true);
		    	}
		    	colArray.put(gridColModel[gci].name,gci);
		    	
		    }
		    var colCBI = 2;
	        for(var ci in colShows){
	        	inContrlCols.put(colShows[ci].col,true);
	        	var extraHighsearch = extraCols.get(colShows[ci].col);
	        	if(extraHighsearch){
	        		continue;
	        	}
	        	var oldPosition = colArray.get(colShows[ci].col);
	        	if(oldPosition){
	        		oldOrder.push(oldPosition);
	        		//colArray.put(colShows[ci].col,'r');
	        		//newOrder[colCBI] = oldPosition;
		        	if(colShows[ci].show){
		        		jQuery("#"+grid).jqGrid('showHideCol',colShows[ci].col,"block");
		        	}else{
		        		jQuery("#"+grid).jqGrid('showHideCol',colShows[ci].col,"none");
		        	}
		        	var colIdx = null;
		    		for(var gci=0;gci<gridColModel.length;gci++){
		        		if(gridColModel[gci].name==colShows[ci].col){
		        			colIdx = gci;
		        			jQuery("#"+grid)[0].grid.resizing = { idx: colIdx};
		        			break;
		        		}
		            }
		    		if(colIdx!=null){
		    			var diff = parseInt(colShows[ci].colWidth)-jQuery("#"+grid)[0].grid.headers[colIdx].width;
		    			jQuery("#"+grid)[0].grid.newWidth = jQuery("#"+grid)[0].p.tblwidth+diff;
		    			jQuery("#"+grid)[0].grid.headers[colIdx].newWidth = parseInt(colShows[ci].colWidth);
		        		jQuery("#"+grid)[0].grid.dragEnd();
		    		}
	        	}
	        	colCBI++;
	        } 
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
		    	/*if(colArray.get(gridColModel[gci].name)=='r'){
		    		newOrder[colArray.get(gridColModel[gci].name)]=gci;
		    	}else{
		    		newOrder[colArray.get(gridColModel[gci].name)]=gci;
		    	}*/
	        }
		    //console.log(newOrder);
		    jQuery("#"+grid).jqGrid('remapColumns',newOrder,true);
	    }
	});
	
	return 1;
}
//higher search end
jQuery.fn.setSelectionForInput = function(selectionStart, selectionEnd) {
	if(this.lengh == 0) return this;
	input = this[0];
	if (input.createTextRange) {
			var range = input.createTextRange();
			//range.collapse(true);
			range.moveStart('character', selectionStart);
			range.moveEnd('character', selectionEnd);
			range.select();
	} else if (input.setSelectionRange) {
			input.focus();
			input.setSelectionRange(selectionStart, selectionEnd);
	}

	return this;
}
function clearGarbage(){
	if (jQuery.browser.msie) {
		window.setTimeout("CollectGarbage();", 10);
	}
}
if(!String.prototype.trim){ 
	String.prototype.trim = function(){ 
	return this.replace(/^\s+|\s+$/g, ''); 
} 
}
if(String.prototype.replaceAll){
	String.prototype.replaceAll = function(oldStr,newStr){
		return this.replace(new RegExp(oldStr,"gm"),newStr); 
	}
}

//jqgrid util function
function stringFormatter (cellvalue, options, rowObject)	{
	if(!cellvalue){
		return "";
	}
	try
		{cellvalue= cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
		}
		catch(err)
		{
			return cellvalue;
		}
	return cellvalue;
}

function htmlStringFormatter(cellvalue, options, rowObject){
	try
	{
		cellvalue = cellvalue.replace(/<[^>]+>/g,"");
		cellvalue = cellvalue.replace(/&nbsp;/g,"");
		cellvalue = cellvalue.replace(/\r\n/g, "").replace(/\n/g, "").replace(/\t/g, "").replace(/(^\s*)|(\s*$)/g, "");
	}
	catch(err)
	{
		return cellvalue;
	}
	return cellvalue;
}
function htmlFormatter(cellvalue, options, rowObject){
	try
	{
		cellvalue = cellvalue.replace(/&quot;/g,"\"");
	}catch(err)
	{
		return cellvalue;
	}
	return cellvalue;
}
// set grid column color
function setCellText(grid,rowid,colName,cellTxt){
	 var  tr,cm = grid.p.colModel, iCol = 0, cCol = cm.length;
     for (; iCol<cCol; iCol++) {
         if (cm[iCol].name === colName) {
             tr = grid.rows.namedItem(rowid);
             if (tr) {
                jQuery(tr.cells[iCol]).html(cellTxt);
             }
             break;
         }
     }
}
function getScrollWidth() {
	  var noScroll, scroll, oDiv = document.createElement("DIV");
	  oDiv.style.cssText = "position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
	  noScroll = document.body.appendChild(oDiv).clientWidth;
	  oDiv.style.overflowY = "scroll";
	  scroll = oDiv.clientWidth;
	  document.body.removeChild(oDiv);
	  return noScroll-scroll;
}
function arrayToJson(array) {
	var output = [];
	if (!$.isPlainObject(array) && $.isArray(array)) {
		for ( var i = 0; i < array.length; i++) {
			output.push(objToJson(array[i]));
		}
	}
	return output;
}

function recurFunc(arr, val) {
	if (arr.length <= 0) {
		return val;
	}
	var first = arr[0];
	var rest = arr.slice(1);
	var result = {};
	if (typeof result[first] === "undefined") {
		result[first] = {};
	}
	var temp = recurFunc(rest, val);
	result[first] = temp;
	return result;
}

function objToJson(data) {
	var output = {};
	if ($.isPlainObject(data) && !$.isArray(data)) {
		for ( var item in data) {
			if (data.hasOwnProperty(item)) {
				var iArray = item.split(".");
				var value = data[item];
				$.extend(true, output, recurFunc(iArray, value));
			}
		}
	} else {
		alert(false);
	}
	return output;
}
function disableLink(links){
	for(var l in links){
		jQuery("#"+links[l]).css("color","#808080");
		jQuery("#"+links[l]).attr("href","javaScript:");
		jQuery("#"+links[l]).parent().unbind("hover");
	}
}

function mergerTableCol(gridName, CellName) {
	var tds = $("#" + gridName + "").find("td[name='"+CellName+"']");
    //当前显示多少条
    var length = tds.length;
    for (var i = 0; i < length; i++) {
    	//console.log("m");
        //从上到下获取一条信息
        var before = tds[i];
        //定义合并行数
        var rowSpanTaxCount = 1;
        for (j = i + 1; j <= length; j++) {
            //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
            var end = tds[j]
            if ($(before).find("span").eq(0).text() == $(end).find("span").eq(0).text()) {
                rowSpanTaxCount++;
                $(end).hide();
            } else {
                break;
            }
            
        } 
        i += rowSpanTaxCount-1;
    	$(before).attr("rowspan", rowSpanTaxCount);
        rowSpanTaxCount = 1;
    } 
}

function addDetailButtonForGrid(gridId,colShow,colHtml,id){
		var showRuleFuction = function($this){
		 var showValue = $this.parent().find("td["+id+"='"+gridId+"_"+colHtml+"']").html();
		 showValue = showValue.replace(/&quot;/g,"\"");
		 if(jQuery("#showDetailsDiv").length==0){
			 jQuery('body').append("<div id='showDetailsDiv' style='display:none'></div>");
		 }
		 jQuery("#showDetailsDiv").html("<div class='pageContent' layoutH=1>"+showValue+"</div>");
		 var url = "#DIA_inline?inlineId=showDetailsDiv";
			$.pdialog.open(url, 'showDetailMsg', "显示详细信息", {
				mask:true,width:480,height:420
		 });
			
		 //$.pdialog.open('showDetailMsg?showValue='+showValue, 'showDetailMsg', "显示详细信息", {mask:true,width:480,height:420});　
	 }
    jQuery("#"+gridId).find("td["+id+"='"+gridId+"_"+colShow+"']").each(function(){
		jQuery(this).detailButton({
			type:"hover",
			dealFuction:showRuleFuction
		});
	});
	}
function makeUserTag(containerId,userTag,field,initValueString,title,validate,readOnly,required,rules,length,form,random,searchName,param1,param2,type,tableFieldNameValue){
	var tagStr = "";
	if(validate!='true'){
		validate = "_exclude_";
	}else{
		validate = "";
	}
	if(readOnly=='true'){
		readOnly = "readOnly";
	}else{
		readOnly = "";
	}
	if(required=='true'){
		required = "required";
	}else{
		required = "";
	}
	if(length){
		size = length;
	}
	if(userTag=='yearMonth'){
		var size = '10';
		if(length){
			size = length;
		}
		var click = "onClick=\"WdatePicker({skin:'ext',dateFmt:'yyyyMM'})\"";
		if(readOnly=="readOnly"){
			click = "";
		}
		tagStr = "<input type='text' name='"+field+validate+"' value='"+initValueString+"' "+click+" "+readOnly
		             +" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
	}else if(userTag=='yearMonthDate'){
		var size = '10';
		if(length){
			size = length;
		}
		var click = "onClick=\"WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})\"";
		if(readOnly=="readOnly"){
			click = "";
		}
		tagStr = "<input type='text' name='"+field+validate+"' value='"+initValueString+"' "+click+" "+readOnly
		             +" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
	}else if(userTag=='yearMonthDateSecond'){
		var size = '10';
		if(length){
			size = length;
		}
		tagStr = "<input type='text' name='"+field+validate+"' value='"+initValueString+"' onClick=\"WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})\" "+readOnly
		             +" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
	}else if(userTag=='yearQuarter'){
		var y=0,m=0;
		if(initValueString&&initValueString.indexOf("-")!=-1){
			var ivArr = initValueString.split("-");
			y = ivArr[0];
			m = ivArr[1];
		}else{
			initValueString = "";
		}
		var cDate = new Date();
		var cy = cDate.getFullYear();
		cy -= 4;
		var yStr = "<select name='"+field+"_y_exclude_' onchange=\"fillYQ('"+field+"','"+field+"_yearQuarter','"+form+"')\"><option value=''></option>";
		for(var i=0;i<9;i++){
			yStr += "<option value='"+cy+"'>"+cy+"</option>";
			cy++;
		}
		yStr += "</select>";
		var qStr = "<select name='"+field+"_q_exclude_' style='float:none' onchange=\"fillYQ('"+field+"','"+field+"_yearQuarter','"+form+"')\">"
					+"<option value=''></option><option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option></select>";
		
		tagStr = "<input type='hidden' id='"+field+"_yearQuarter' name='"+field+validate+"' value='"+initValueString+"'/>"
		         +yStr
		         +"<label style='float:none;line-height:15px;padding: 0 0 0 0 '>年</label>"
		         +qStr
		         +"<label style='float:none;line-height:15px;padding: 0 0 0 0'>季度</label>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		if(y&&y!=0){
			jQuery("select[name='"+field+"_y_exclude_']","#"+form).val(y);
		}
		if(m&&m!=0){
			jQuery("select[name='"+field+"_q_exclude_']","#"+form).val(m);
		}
	}else if(userTag=='checkbox'){
		if(readOnly=='readOnly'){
			readOnly = 'disabled=true';
		}
		var p1 = param1;
		var pArr = p1.split(";");
		var c = "";
		if(pArr[0]!=initValueString||pArr[1]!=initValueString){
			initValueString = pArr[0];
		}
		if(initValueString==pArr[0]){
			c = "checked";
		}
		tagStr = "<input type='checkbox' id='"+random+"_"+searchName+"_"+field+"' name='"+field+"_exclude_' "+c+" value='"+initValueString+"' values='"+param1+"' "+readOnly+"/>" +
				"<input type='hidden' id='"+random+"_"+searchName+"_"+field+"_h' name='"+field+"' value='"+initValueString+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		jQuery("#"+random+"_"+searchName+"_"+field,"#"+form).click(function(){
			var cvs = jQuery(this).attr("values");
			var checked = jQuery(this).attr("checked");
			var cvsArr = "",v = "";
			if(cvs){
				cvsArr = cvs.split(";");
			}
			if(checked=='checked'){
				if(cvsArr.length>0){
					v = cvsArr[0];
				}else{
					v = true;
				}
			}else{
				if(cvsArr.length>1){
					v = cvsArr[1];
				}else{
					v = false;
				}
			}
			jQuery(this).val(v);
			jQuery("#"+random+"_"+searchName+"_"+field+"_h","#"+form).val(v);
		});
	}else if(userTag=='checkboxGroupH'){
		var p1 = param1;
		var ps = p1.split(";");
		var cStr = "<span>";
		for(var i=0;i<ps.length;i++){
			var pArr = ps[i].split(",");
			cStr += "<input type='checkbox' name='"+field+"' value='"+pArr[1]+"'/>"+pArr[0];
		}
		cStr += "</span>";
		//jQuery("#"+field+"_checkGroupH","#"+form).append(cStr);
		var w = jQuery("#"+field+"_checkGroupH","#"+form).find("span:first").width();
		//if(w<200){ w=200;}else{ w+=10;}
		var s = "margin-left: 80px;min-width:200px";
		if(type!='form'){
			s = "";
		}
		tagStr = "<fieldset id='"+field+"_checkGroupH' style='display:inline;font-weight: normal;"+s+"'><legend>"+title+"</legend>"
				+cStr+
				"</fieldset>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		//jQuery("#${htmlField }_checkGroupH","#searchFilterForm").width(w);
	}else if(userTag=='checkboxGroupV'){
		var p1 = param1;
		var ps = p1.split(";");
		var cStr = "<span>";
		for(var i=0;i<ps.length;i++){
			var pArr = ps[i].split(",");
			cStr += "<span style='display:block;'><input type='checkbox' name='"+field+"' value='"+pArr[1]+"'/>"+pArr[0]+"</span>";
		}
		cStr += "</span>";
		//jQuery("#"+field+"_checkGroupH","#"+form).append(cStr);
		//var w = jQuery("#"+field+"_checkGroupH","#"+form).find("span:first").width();
		//if(w<200){ w=200;}else{ w+=10;}
		var s = "margin-left: 80px;min-width:200px";
		if(type!='form'){
			s = "";
		}
		tagStr = "<fieldset id='"+field+"_checkGroupV' style='display:inline;font-weight: normal;"+s+"'><legend>"+title+"</legend>"
				+cStr+
				"</fieldset>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
	}else if(userTag=='radioGroupH'){
		var p1 = param1;
		var ps = p1.split(";");
		var cStr = "<span>";
		for(var i=0;i<ps.length;i++){
			var pArr = ps[i].split(",");
			cStr += "<input type='radio' name='"+field+"' value='"+pArr[1]+"'/>"+pArr[0];
		}
		cStr += "</span>";
		//jQuery("#"+field+"_checkGroupH","#"+form).append(cStr);
		//var w = jQuery("#"+field+"_radioGroupH","#"+form).find("span:first").width();
		//if(w<200){ w=200;}else{ w+=10;}
		var s = "margin-left: 80px;min-width:200px";
		if(type!='form'){
			s = "";
		}
		tagStr = "<fieldset id='"+field+"_radioGroupH' style='display:inline;font-weight: normal;"+s+"'><legend>"+title+"</legend>"
				+cStr+
				"</fieldset>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
	}else if(userTag=='radioGroupV'){
		var p1 = param1;
		var ps = p1.split(";");
		var cStr = "<span>";
		for(var i=0;i<ps.length;i++){
			var pArr = ps[i].split(",");
			cStr += "<span style='display:block;'><input type='radio' name='"+field+"' value='"+pArr[1]+"'/>"+pArr[0]+"</span>";
		}
		cStr += "</span>";
		//jQuery("#"+field+"_checkGroupH","#"+form).append(cStr);
		//var w = jQuery("#"+field+"_checkGroupH","#"+form).find("span:first").width();
		//if(w<200){ w=200;}else{ w+=10;}
		var s = "margin-left: 80px;min-width:200px";
		if(type!='form'){
			s = "";
		}
		tagStr = "<fieldset id='"+field+"_radioGroupV' style='display:inline;font-weight: normal;"+s+"'><legend>"+title+"</legend>"
				+cStr+
				"</fieldset>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
	}else if(userTag=='stringSelect'||userTag=='stringSelectR'){
		//tagStr = "<appfuse:stringSelect htmlFieldName='"+field+validate+"' paraString='"+param1+"' initValue='"+initValueString+"' required='false'></appfuse:stringSelect>";
		if(readOnly=='readOnly'){
			readOnly = 'disabled=true';
		}
		var p1 = param1;
		var pArr = p1.split(";");
		tagStr = "<input type='hidden' name='"+field+"'/><select name='"+field+"_exclude_' class='textInput "+required+" "+rules+"' "+readOnly+">";
		if(userTag=='stringSelectR'){
			required = "required";
		}else{
			tagStr += "<option></option>";
		}
		for(var i=0;i<pArr.length;i++){
			var v = '',s = "";
			var sv = pArr[i].split(",");
			if(sv.length>1){
				v = sv[0];
				s = sv[1];
			}else{
				v = sv;
				s = sv;
			}
			if(v==initValueString){
				tagStr += "<option value='"+v+"' selected>"+s+"</option>";
			}else{
				tagStr += "<option value='"+v+"'>"+s+"</option>";
			}
		}
		tagStr += "</select>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		if(userTag=='stringSelectR'){
			jQuery("input[name='"+field+"'","#"+form).val(jQuery("select[name='"+field+"_exclude_']","#"+form).val());
		}
		jQuery("select[name='"+field+"_exclude_']","#"+form).change(function(){
			jQuery("input[name='"+field+"']","#"+form).val(jQuery(this).val());
		});
		
	}else if(userTag=='sqlSelect'||userTag=='sqlSelectR'){
		if(readOnly=='readOnly'){
			readOnly = 'disabled=true';
		}
		$.ajax({
		    url: 'getSelectStrBySql',
		    type: 'post',
		    data:{selectTreeSql:param1},
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		    	var p1 = data.rsStr;
				var pArr = p1.split(";");
				tagStr = "<input type='hidden' name='"+field+"'/><select name='"+field+"_exclude_' class='textInput "+required+" "+rules+"' "+readOnly+">";
				if(userTag=='sqlSelectR'){
					required = "required";
				}else{
					tagStr += "<option></option>";
				}
				for(var i=0;i<pArr.length;i++){
					var v = '',s = "";
					var sv = pArr[i].split(",");
					if(sv.length>1){
						v = sv[0];
						s = sv[1];
					}else{
						v = sv;
						s = sv;
					}
					if(v==initValueString){
						tagStr += "<option value='"+v+"' selected>"+s+"</option>";
					}else{
						tagStr += "<option value='"+v+"'>"+s+"</option>";
					}
				}
				tagStr += "</select>";
				jQuery("#"+containerId,"#"+form).append(tagStr);
				if(userTag=='sqlSelectR'){
					jQuery("input[name='"+field+"'","#"+form).val(jQuery("select[name='"+field+"_exclude_']","#"+form).val());
				}
				jQuery("select[name='"+field+"_exclude_']","#"+form).change(function(){
					jQuery("input[name='"+field+"']","#"+form).val(jQuery(this).val());
				});
		    }
		});
	}else if(userTag=='dicSelect'){
		if(readOnly=='readOnly'){
			readOnly = 'disabled=true';
		}
		$.ajax({
		    url: 'getSelectStrBySql',
		    type: 'post',
		    data:{selectTreeSql:"select [value],displayContent from t_dictionary_items item INNER JOIN t_dictionary dic ON item.dictionary_id=dic.dictionaryId where dic.code = '"+param1+"' order by orderNo asc"},
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		    	var p1 = data.rsStr;
				var pArr = p1.split(";");
				tagStr = "<input type='hidden' name='"+field+"'/><select name='"+field+"_exclude_' class='textInput "+required+" "+rules+"' "+readOnly+">";
				for(var i=0;i<pArr.length;i++){
					var v = '',s = "";
					var sv = pArr[i].split(",");
					if(sv.length>1){
						v = sv[0];
						s = sv[1];
					}else{
						v = sv;
						s = sv;
					}
					if(v==initValueString){
						tagStr += "<option value='"+v+"' selected>"+s+"</option>";
					}else{
						tagStr += "<option value='"+v+"'>"+s+"</option>";
					}
				}
				tagStr += "</select>";
				jQuery("#"+containerId,"#"+form).append(tagStr);
				jQuery("select[name='"+field+"_exclude_']","#"+form).change(function(){
					jQuery("input[name='"+field+"']","#"+form).val(jQuery(this).val());
				});
		    }
		});
	}else if(userTag=='deptTreeS'||userTag=='deptTreeM'){
		var optType = "single";
		if(userTag=='deptTreeM'){
			optType = 'multi';
		}
		tagStr = "<input id='"+random+"_"+searchName+"_"+field+"' name='"+field+validate+"' type='hidden' value='"+initValueString+"'/>"
				+"<input id='"+random+"_"+searchName+"_"+field+"_name'" +readOnly
		        +" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		if(readOnly=='readOnly'){
			return ;
		}
		jQuery("#"+random+"_"+searchName+"_"+field+"_name").treeselect({
			dataType:"sql",
			optType:optType,
			sql:"select deptId id,name,parentDept_id parent from t_department where deptId<>'XT' order by internalCode asc",
			idId:random+"_"+searchName+"_"+field,
			exceptnullparent:false,
			rebuildByClick:false,
			initSelect:initValueString,
			lazy:false,
			callback : {}
		});
	}else if(userTag=='personTreeS'||userTag=='personTreeM'){
		var optType = "single";
		if(userTag=='personTreeM'){
			optType = 'multi';
		}
		tagStr = "<input id='"+random+"_"+searchName+"_"+field+"' name='"+field+validate+"' type='hidden' value='"+initValueString+"'/>"
				+"<input id='"+random+"_"+searchName+"_"+field+"_name'" +readOnly
				+" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		if(readOnly=='readOnly'){
			return ;
		}
		jQuery("#"+random+"_"+searchName+"_"+field+"_name").treeselect({
			dataType:"sql",
			optType:optType,
			sql:"select personid id,name name,dept_id parent ,'1' deptCode,0 isParent from t_person where disabled=0 and personid <>'XT' UNION SELECT deptId id , name ,parentDept_id parent , deptCode,1 isParent FROM t_department WHERE disabled=0 AND deptId<>'XT' ORDER BY deptCode",
			idId:random+"_"+searchName+"_"+field,
			exceptnullparent:true,
			rebuildByClick:false,
			initSelect:initValueString,
			lazy:false,
			callback : {}
		});
	}else if(userTag=='costitemTreeS'||userTag=='costitemTreeM'){
		var optType = "single";
		if(userTag=='costitemTreeM'){
			optType = 'multi';
		}
		tagStr = "<input id='"+random+"_"+searchName+"_"+field+"' name='"+field+validate+"' type='hidden' value='"+initValueString+"'/>"
				+"<input id='"+random+"_"+searchName+"_"+field+"_name'" +readOnly
				+" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		if(readOnly=='readOnly'){
			return ;
		}
		jQuery("#"+random+"_"+searchName+"_"+field+"_name").treeselect({
			dataType:"sql",
			optType:optType,
			sql:"select costitemid id,costitem name,isnull(parentid,'1') parent from t_costitem where disabled=0 order by costitemid",
			idId:random+"_"+searchName+"_"+field,
			exceptnullparent:false,
			rebuildByClick:false,
			initSelect:initValueString,
			lazy:false,
			callback : {}
		});
	}else if(userTag=='deptFormulaSelect'||userTag=='orgFormulaSelect'){
		var formulaEntityId=2;
		if(userTag=='orgFormulaSelect'){
			formulaEntityId = 1;
		}
		tagStr = "<input id='"+random+"_"+searchName+"_"+field+"' name='"+field+validate+"' type='hidden' value='"+initValueString+"'/>"
				+"<input id='"+random+"_"+searchName+"_"+field+"_name'" +readOnly
				+" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		if(readOnly=='readOnly'){
			return ;
		}
		jQuery("#"+random+"_"+searchName+"_"+field+"_name").treeselect({
			dataType:"sql",
			optType:"multi",
			optType:"multi",
			sql:"select fieldName id,fieldTitle name,fieldTitle parent from t_formulaField where formulaEntityId="+formulaEntityId,
			idId:random+"_"+searchName+"_"+field,
			exceptnullparent:false,
			rebuildByClick:false,
			initSelect:initValueString,
			lazy:false,
			callback : {}
		});
	}else if(userTag=='treeSelectS'||userTag=='treeSelectM'){
		var optType = "single";
		if(userTag=='treeSelectM'){
			optType = 'multi';
		}
		tagStr = "<input id='"+random+"_"+searchName+"_"+field+"' name='"+field+validate+"' type='hidden' value='"+initValueString+"'/>"
				+"<input id='"+random+"_"+searchName+"_"+field+"_name'" +readOnly
				+" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		if(readOnly=='readOnly'){
			return ;
		}
		var lazy = true;
		if(type=='form'){
			lazy = false;
		}
		jQuery("#"+random+"_"+searchName+"_"+field+"_name").treeselect({
			dataType:"sql",
			optType:optType,
			sql:param1,
			idId:random+"_"+searchName+"_"+field,
			relateParam:param2,
			relatePrefix:random+"_"+searchName+"_",
			exceptnullparent:false,
			rebuildByClick:true,
			initSelect:initValueString,
			lazy:lazy,
			callback : {}
		});
	}else if(userTag=='autocomplete'){
		var nameValue = tableFieldNameValue;
		if(!nameValue){
			nameValue = "拼音/汉字/编码";
		}
		tagStr = "<input id='"+random+"_"+searchName+"_"+field+"' name='"+field+validate+"' type='hidden' value='"+initValueString+"'/>"
		+"<input id='"+random+"_"+searchName+"_"+field+"_name'" +readOnly
		+" class='defaultValueStyle textInput "+required+" "+rules+"' size='"+size+"' value='"+nameValue+"' onfocus=\"clearInput(this,jQuery('#chargeTypeIdC3'))\" onblur=\"setDefaultValue(this,jQuery('#chargeTypeIdC3'))\" onkeydown=\"setTextColor(this)\"/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
		
		jQuery("#"+random+"_"+searchName+"_"+field+"_name").autocomplete("autocompleteBySql",
				{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						var data = test.result;
						if (data == null || data == "") {
							var rows = [];
							rows[0] = {
								data : "没有结果",
								value : "",
								result : ""
							};
							return rows;
						} else {
							var rows = [];
							for ( var i = 0; i < data.length; i++) {
								rows[rows.length] = {
									data : {
										showValue : data[i].showValue,
										id : data[i].id,
										name : data[i].name
									},
									value : data[i].id,
									result : data[i].name
								};
							}
							return rows;
						}
					},
					extraParams : {
						sql : param2
					},
					formatItem : function(row) {
						if(typeof(row)=='string'){
							return row
						}else{
							return row.showValue;
						}
						//return dropId(row);
					},
					formatResult : function(row) {
						return row.showValue;
						//return dropId(row);
					}
				});
		jQuery("#"+random+"_"+searchName+"_"+field+"_name").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			//alert(row.id);
			jQuery("#"+random+"_"+searchName+"_"+field).attr("value", row.id);
			//alert(jQuery("#chargeTypeId").attr("value"));
		});
	}else{
		tagStr = "<input type='text' id='"+random+"_"+searchName+"_"+field+"' name='"+field+validate+"' value='"+initValueString+"' "+readOnly
        			+" class='textInput "+required+" "+rules+"' size='"+size+"'/>";
		jQuery("#"+containerId,"#"+form).append(tagStr);
	}
}

function exportToExcel(gridId,entityName,title,outPutType){
	var url = jQuery("#"+gridId).jqGrid('getGridParam','url');
	var formData = jQuery("#"+gridId).jqGrid('getGridParam','formData');
	var param = url.split("?")[1];
	//alert(json2str(jQuery("#sourcepayin_gridtable")[0].p.colModel));
	//return ;
	var colModel = jQuery("#"+gridId).jqGrid('getGridParam','colModel');
	var colDefine = new Array();
	var colDefineIndex = 0;
	for(var mi=0;mi<colModel.length;mi++){
		var col = colModel[mi];
		if(col.name!="rn"&&col.name!="cb"&&!col.hidden){
			var label = col.label?col.label:col.name;
			var type = col.formatter?col.formatter:1;
			var align = col.align?col.align:"left";
			var width = col.width?parseInt(col.width)*20:50*20;
			colDefine[colDefineIndex] = {name:col.name,type:type,align:align,width:width,label:label};
			colDefineIndex++;
		}
	}
	var colDefineStr = json2str(colDefine);
	var page=1,pageSize=20,sortname,sortorder;
	page = jQuery("#"+gridId).jqGrid('getGridParam','page');
	pageSize = jQuery("#"+gridId).jqGrid('getGridParam','rowNum');
	
	sortname = jQuery("#"+gridId).jqGrid('getGridParam','sortname');
	sortorder = jQuery("#"+gridId).jqGrid('getGridParam','sortorder');
	var u =  'outPutExcel?'+param+"&entityName="+entityName;
	$.ajax({
		url: u,
		type: 'post',
		data:{entityName:entityName,title:title,outPutType:outPutType,page:page,pageSize:pageSize,sortname:sortname,sortorder:sortorder,colDefineStr:colDefineStr},
		dataType: 'json',
		async:false,
		error: function(data){
			alertMsg.error("系统错误！");
		},
		success: function(data){
			var downLoadFileName = data["message"];
			var filePathAndName = downLoadFileName.split("@@");
			var url = "${ctx}/downLoadExel?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
	 		//url=encodeURI(url);
	 		location.href=url; 
		}
	}); 
}

var zzhUtil = {};
zzhUtil.DecodeURI = function(zipStr,isCusEncode){
	if(isCusEncode){
        var zipArray = zipStr.split("N");
        var zipSrcStr = zipArray[0];
        var zipLens;
        if(zipArray[1]){
            zipLens = zipArray[1].split("O");    
        }else{
            zipLens.length = 0;
        }
        
        var uzipStr = "";
        
        for(var j=0;j<zipLens.length;j++){
            var charLen = parseInt(zipLens[j]);
            uzipStr+= String.fromCharCode(zipSrcStr.substr(0,charLen));
            zipSrcStr = zipSrcStr.slice(charLen,zipSrcStr.length);
        }        
        return uzipStr;
    }else{
        //return decodeURI(zipStr);
        var uzipStr=""; 
        for(var i=0;i<zipStr.length;i++){ 
            var chr = zipStr.charAt(i); 
            if(chr == "+"){ 
                 uzipStr+=" "; 
            }else if(chr=="%"){ 
                 var asc = zipStr.substring(i+1,i+3); 
                 if(parseInt("0x"+asc)>0x7f){ 
                     uzipStr+=decodeURI("%"+asc.toString()+zipStr.substring(i+3,i+9).toString()); ; 
                     i+=8; 
                 }else{ 
                     uzipStr+=zzhUtil.AsciiToString(parseInt("0x"+asc)); 
                     i+=2; 
                 } 
            }else{ 
                 uzipStr+= chr; 
            } 
        } 
        return uzipStr;
    }
}

zzhUtil.StringToAscii = function(str){
	return str.charCodeAt(0).toString(16);
}
zzhUtil.AsciiToString = function(asccode){
    return String.fromCharCode(asccode);
}

zzhUtil.offset = function (elem) {
	var offsetLeft = elem.offsetLeft
		,offsetTop = elem.offsetTop
		,lastElem = elem;

	while (elem = elem.offsetParent) {
		if (elem === document.body) { //from my observation, document.body always has scrollLeft/scrollTop == 0
			break;
		}
		offsetLeft += elem.offsetLeft;
		offsetTop += elem.offsetTop;
		lastElem = elem;
	}

	if (lastElem && lastElem.style.position === 'fixed') { //slow - http://jsperf.com/offset-vs-getboundingclientrect/6
		//if(lastElem !== document.body) { //faster but does gives false positive in Firefox
		offsetLeft += window.pageXOffset || document.documentElement.scrollLeft;
		offsetTop += window.pageYOffset || document.documentElement.scrollTop;
	}

	return {
		left: offsetLeft,
		top: offsetTop
	};
};

function addSelectButton(buttonId,container,buttons){
	var selectButtonId = "selectButton_"+buttonId;
	var selectButton = jQuery("#"+selectButtonId);
	if(selectButton.length>0){
		var isShow = selectButton.css("display");
		if(isShow=="block"){
			jQuery("#"+selectButtonId).hide("fold");
		}else{
			jQuery("#"+selectButtonId).show("fold");
		}
	}else{
		var liStr = "";
		for(var a=0;a<buttons.length;a++){
			var b = buttons[a];
			liStr += "<li><a id='"+b.id+"' class='"+b.className+"' href='javaScript:'><span id='span_"+b.id+"'>"+b.name+"</span></a></li>";
		}
		jQuery("body").append("<div id='"+selectButtonId+"' class='selectButton' style='display:none;position:absolute;background: white;z-index:1000'><ul class='toolBar'>"+liStr+"</li></ul></div>");
		jQuery("#"+selectButtonId).on("click","a",function(e){
			var thisId = jQuery(this).attr("id");
			for(var a=0;a<buttons.length;a++){
				var b = buttons[a];
				if(b.id==thisId){
					var callBodyParam ,beforeCallParam;
					if(b.param){
						callBodyParam = b.param.callBodyParam;
						beforeCallParam = b.param.beforeCallParam;
					}
					if(b.callback){
						if(typeof(b.callback.beforeCall)=='function'){
							var pass = b.beforeCall(e,jQuery(this),beforeCallParam);
							if(!pass){
								break;
							}
						}
					}
					if(typeof(b.callBody)=='function'){
						b.callBody(e,jQuery(this),callBodyParam);
					}
					break;
				}
			}
		});
		var buttonOffset = zzhUtil.offset(jQuery("#"+buttonId)[0]);
    	var left = buttonOffset.left,top = buttonOffset.top;
    	jQuery("#"+selectButtonId).css({left:left + "px", top:top+jQuery("#"+buttonId).height() + "px"});
    	jQuery("#"+selectButtonId+">ul>li").hover(function(){
    		jQuery(this).addClass('hover');
		},function(){
			jQuery(this).removeClass('hover');
		});
    	jQuery("#"+selectButtonId).show("fold");
	}
	function onBodyDownHideSb(e){
		if (!(e.target.id == "span_"+buttonId)){
			jQuery("#"+selectButtonId).hide("fold");
			$(document).unbind("click", onBodyDownHideSb);
		}
	}
	$(document).bind("click", onBodyDownHideSb);
}

/*
 * 设置左侧部门树的布局
 */
function setLeftTreeLayout(container,gridtable,containerHeight){
	jQuery("#"+container).css("height",containerHeight);
	var jLayout = $('#'+container).layout({ 
		applyDefaultStyles: false ,
		west__size : 290,
		spacing_open:5,//边框的间隙  
		spacing_closed:5,//关闭时边框的间隙 
		resizable :true,
		resizerClass :"ui-layout-resizer-blue",
		slidable :true,
		resizerDragOpacity :1, 
		resizerTip:"可调整大小",
		onresize_end : function(paneName,element,state,options,layoutName){
			if("center" == paneName){
				gridResize(null,null,gridtable,"single");
			}
		}
	});
	return jLayout;
}
/*
 * 展开\折叠树
 */
function toggleExpandTree(obj,treeId){
	var zTree = $.fn.zTree.getZTreeObj(treeId); 
	var obj = jQuery(obj);
	var text = obj.html();
	if(text=='展开'){
		obj.html("折叠");
		zTree.expandAll(true);
	}else{
		obj.html("展开");
		//zTree.expandAll(false);
		var allNodes = zTree.transformToArray(zTree.getNodes());
		for(var nodeIndex in allNodes){
			var node = allNodes[nodeIndex];
			if(node.subSysTem=='ORG'){
				zTree.expandNode(node,false,true,true);
			}
		}
	}
}
/*
 * 展开\折叠树
 */
function toggleExpandTreeWithShowFlag(obj,treeId,showFlag){
	var zTree = $.fn.zTree.getZTreeObj(treeId); 
	var obj = jQuery(obj);
	var text = obj.html();
	if(text=='展开'){
		obj.html("折叠");
		zTree.expandAll(true);
	}else{
		obj.html("展开");
		//zTree.expandAll(false);
		var allNodes = zTree.transformToArray(zTree.getNodes());
		for(var nodeIndex in allNodes){
			var node = allNodes[nodeIndex];
			zTree.expandNode(node,false,true,true);
		}
		if(showFlag&&allNodes&&allNodes[0]){
			zTree.expandNode(allNodes[0], true, false, true);
		}
	}
}

/*
 * 设置停用部门的样式
 */
function setDisabledDeptFontCss(treeId, treeNode) {
	var color;
	if(treeNode.actionUrl == '1') {
		color = {
			color : "black",
			'font-style' : 'italic',
			'text-decoration' : 'line-through'
		};
	}else{
		color = {
			color : "black",
			'font-style' : 'normal',
			'text-decoration' : 'none'
		};
	}
	return color;
};
//隐藏树节点(该节点及其下级节点)
function hideZTreeNodes(updateNode,treeObjId){
	var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
	treeObj.hideNode(updateNode);
	if(updateNode.isParent){
		var childNodes=updateNode.children;
		if(childNodes){
			jQuery.each(childNodes, function() { 
				hideZTreeNodes(this,treeObjId);  
    		 });
		}
	}
}
//获取所有树某节点的所有子节点
function getAllChildrenZTreeNodes(searchNode,treeObjId,nodeArr){
	var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
	if(searchNode.isParent){
		var childNodes = searchNode.children;
		if(childNodes){
			jQuery.each(childNodes, function(index,childNode){
				nodeArr.push(childNode);
				getAllChildrenZTreeNodes(childNode,treeObjId,nodeArr);  
    		 });
		}
	}
}
//显示树节点(该节点及其下级节点)
function showAllTreeNodes(updateNode,treeObjId,showDisabledDept){
	var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
	if(!showDisabledDept && updateNode.subSysTem=="DEPT" && updateNode.actionUrl == '1'){
		treeObj.hideNode(updateNode);
	}else{
		treeObj.showNode(updateNode);
	}
	if(updateNode.isParent){
		var childNodes=updateNode.children;
		if(childNodes){
			jQuery.each(childNodes, function() {
				showAllTreeNodes(this,treeObjId,showDisabledDept);  
    		 });
		}
	}
}
//显示树节点(该节点及其上级节点)
function showZTreeNodes(updateNode,treeObjId){
	var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
	if(updateNode.isHidden){
		treeObj.showNode(updateNode);
	}
	if(updateNode.getParentNode()){
		showZTreeNodes(updateNode.getParentNode(),treeObjId);
	}
}
//根据rownumber 返回row  
function getRowIdByRownum(grid, number) {
	var ids = jQuery("#"+grid).jqGrid('getDataIDs');
	for ( var i = 0; i <= ids.length; i++) {
		if (ids[i] == number) {
			return i;
		}
	}
}
//调整form里select对齐
function adjustFormInput(formId){
	setTimeout(function(){
		var standardFormSpanWidth = jQuery("input[type=text]:first","#"+formId).width();
		jQuery(".formspan","#"+formId).width(standardFormSpanWidth+6);
	},100);

}
//公用方法用来处理日期的前后判断问题  @param : text1 ,text2(两个比较text的 id),Posation（用来确定是前后text:0是前 1是后 ）,hide1,hide2(hide1,hide2为隐藏域id)
function checkQueryDate(item1,item2,posation,hide1,hide2){
		if(posation==0){
		//先获取连个text的value
		var value1 = $("#"+item1).val();
		var value2 = $("#"+item2).val();
		if(value2==""||value1==""){  //若存在一个text无值的情况不进行判断 
			if(value2==""){
				$("#"+hide1).attr("value",value1 + " 00:00:00");
				$("#"+hide2).attr("value","");
			}
		    if(value1==""){
		    	$("#"+hide1).attr("value","");
		    }
			return;      
		}else{
		//将两个字符串转换为日期比较
	      var date1 = new Date(value1.replace(/-/g,"/"));
	      $("#"+hide1).attr("value",value1 + " 00:00:00");
	      var date2 = new Date(value2.replace(/-/g,"/"));
	      $("#"+hide2).attr("value",value2 + " 23:59:59");
		}
		if(date1>date2){ //存在异常情况
			//清除掉当前值
			$("#"+item1).attr("value","");
			$("#"+hide1).attr("value","");
			alertMsg.error("开始时间不得大于结束时间!");
		}
	 }else{
		//先获取连个text的value
		   var value1 = $("#"+item1).val();
		   var value2 = $("#"+item2).val();
			if(value2==""||value1==""){ //若存在一个text无值的情况不进行判断
				if(value1==""){
					$("#"+hide2).attr("value",value2 + " 23:59:59");
					$("#"+hide1).attr("value","");
				}
			    if(value2==""){
			    	$("#"+hide2).attr("value","");
			    }
				return;
			}else{
			//将两个字符串转换为日期比较
		       var date1 = new Date(value1.replace(/-/g,"/"));
		       $("#"+hide1).attr("value",value1 + " 00:00:00");
	           var date2 = new Date(value2.replace(/-/g,"/"));
	           $("#"+hide2).attr("value",value2 + " 23:59:59");
			}
			if(date1>date2){ //存在异常情况
				$("#"+item2).attr("value","");
				$("#"+hide2).attr("value","");
				alertMsg.error("结束时间不得小于开始时间!");
			}
	    }
 }

function getJsonLength(obj){
	var count = 0;
	for(var i in obj){
		count++;
	}
	return count;
}

function dealTreeNode(treeId,node,opt){
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodeId = node.id;
	var oldNode = treeObj.getNodeByParam("id", nodeId, null);
	if(node){
		switch(opt)
		{
		case 'add':
			var parentNode = treeObj.getNodeByParam("id", node.pId, null);
			treeObj.addNodes(parentNode,node);
			break;
		case 'change':
			if(oldNode){
				treeObj.updateNode(node);
			}
			break;
		case 'del':
			if(oldNode){
				treeObj.removeNode(oldNode);
			}
			break;
		case 'disable':
			if(oldNode){
				oldNode.actionUrl = '1';
				treeObj.updateNode(oldNode);
			}
			break;
		case 'enable':
			if(oldNode){
				oldNode.actionUrl = '0';
				treeObj.updateNode(oldNode);
			}
			break;
		}
	}
}
function changeSysTimeType(entity,name1,name2,name3){
	//首先获得下拉框的时间 
	var value = jQuery("#"+entity+"_searchTime").val();
	if(value=="0"){   //为0时是执行时间
		jQuery("#"+entity+"_beginDate").attr("name","filter_GET_"+name1);
		jQuery("#"+entity+"_endDate").attr("name","filter_LET_"+name1);
	}else if (value=="1"){  //为1是申请时间
		jQuery("#"+entity+"_beginDate").attr("name","filter_GET_"+name2);
		jQuery("#"+entity+"_endDate").attr("name","filter_LET_"+name2);
	}else{                //审核时间
		jQuery("#"+entity+"_beginDate").attr("name","filter_GET_"+name3);
		jQuery("#"+entity+"_endDate").attr("name","filter_LET_"+name3);
	}
	WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'});
}
//深层复制（一层一层往下复制直到最底层）
function cloneObj(obj){
	return jQuery.extend(true, {}, obj);
}

function matchRegExp(str){
    pattern =new RegExp("\{(.| )+?\}","igm");
    return str.match(pattern);
}

function bgiframe(dom,e,w,h,t,l,hide){
	var id = jQuery(dom).attr('id'),ifrId;
	if(!id){
		id = 'dom'+Math.floor(Math.random()*10000);
		jQuery(dom).attr('id',id);
	}
	ifrId = id+'_bgiframe';
	var bgiframe = jQuery('#'+ifrId);
	if(hide=='none'){
		bgiframe.hide();
	}else if(hide=='del'){
		bgiframe.remove();
	}else{
		if(bgiframe.length==0){
			dom.after('<iframe id="'+ifrId+'" class="dialog_ifr" frameborder="0" style="position:absolute;"></iframe>');
			bgiframe = jQuery('#'+ifrId);
		}
		var top = $(dom).css("top") 
		, left = $(dom).css("left")
		,margin_left = $(dom).css("margin-left")
		,margin_top = $(dom).css("margin-top")
		,z_index = $(dom).css("z-index");
		if(z_index){
			z_index--;
		}
		bgiframe.css({
			top: top,
			left:left,
			'margin-left':margin_left,
			'margin-top':margin_top,
			'z-index':z_index
		});
		bgiframe.outerWidth($(dom).outerWidth()+1);
		bgiframe.outerHeight($(dom).outerHeight());
		
		bgiframe.show();
	}
}
var isWindow = function(obj){ 
	return obj.window === obj.window.window 
}

function openProgressBar(id,message,ifr){
	var background = '<div id="'+id+'_background" class="background" style="display: none;"></div>';
	var progressBar = jQuery('<div id="'+id+'_progressBar" class="progressBar" style="display: none;">'+message+'</div>');
	jQuery("#progressBar").after(progressBar);
	jQuery("#background").show();
	progressBar.show();
	if(ifr){
		bgiframe(progressBar,null,0,0,0,0,'block');
	}
}
function closeProgressBar(id,message,ifr){
	jQuery("#background").hide();
	if(ifr){
		bgiframe(jQuery("#"+id+"_progressBar"),null,0,0,0,0,'del');
	}
	jQuery("#"+id+"_progressBar").remove();
}

function stopPropagation(){
	if( window.event && window.event.stopPropagation ) {
		window.event.stopPropagation();
	}else { 
		window.event.cancelBubble = true;
	}
}
/*==========jqgrid单元格修改样式start================*/
//创建一个input输入框(整数)
function jqGridIntElem (value, options) {
  var el = document.createElement("input");
  el.type="text";
  el.value = value;
  el.onblur = function(){
	var valueTest=el.value;
	if(valueTest&&parseInt(valueTest)!=valueTest){
			el.value=0;
			alertMsg.error("请输入整数！");
		}
	}
  return el;
}
//创建一个input输入框(数字)
function jqGridNaNElem (value, options) {
    var el = document.createElement("input");
    el.type="text";
    el.value = value;
    el.onblur = function(){
  	var valueTest=el.value;
  	if(valueTest&&isNaN(valueTest)){
  			el.value=0;
				alertMsg.error("请输入数字!");
			}
		}
    return el;
  }
//创建一个颜色选择器
function jqGridColorPickerElem (value, options) {
	  var el = document.createElement("input");
	  el.type="text";
	  el.value = value;
	  jQuery(el).colorpicker({
		    fillcolor:true,
		    ifr:true
		});
	  el.readOnly = true;
	  return el;
}
//创建一个input输入框(日历)
function jqGridcalendarElem (value, options) {
	  var el = document.createElement("input");
	  el.type="text";
	  el.value = value;
	  el.onclick = function(){ WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'});};
	  el.readOnly = true;
	  return el;
}
//获取值
function jqGridElemValue(elem) {
  return $(elem).val();
}
//获取日历控件的值
function jqGridCalendarValue(elem, operation, value) {
    if(operation === 'get') {
       return $(elem).val();
    } else if(operation === 'set') {
       $('input',elem).val(value);
    }
}
/*==========jqgrid单元格修改样式end================*/

function formatDate(now,type){
	var year=now.getYear();
	var month=now.getMonth()+1;
	var date=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var second=now.getSeconds();
	var dateStr = year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
	if(type==1){
		dateStr = year+month+date+hour+minute+second;
	}
	return dateStr;
}

function getTimestamp(now){
	if(!now){
		now = new Date();
	}
	var year=now.getFullYear();
	var month=("0" + (now.getMonth() + 1)).slice(-2);
	var date=("0" + now.getDate()).slice(-2);
	var hour=("0" + now.getHours()).slice(-2);
	var minute=("0" + now.getMinutes()).slice(-2);
	var second=("0" + now.getSeconds()).slice(-2);
	var milliSecond=now.getMilliseconds();
	var dateStr = year+month+date+hour+minute+second+milliSecond;
	return dateStr;
}
function exportToExcelByAction(gridId,entityName,title,outPutType){
	var url = jQuery("#"+gridId).jqGrid('getGridParam','url');
	var formData = jQuery("#"+gridId).jqGrid('getGridParam','formData');
	//var param = url.split("?")[1];
	//alert(json2str(jQuery("#sourcepayin_gridtable")[0].p.colModel));
	//return ;
	var colModel = jQuery("#"+gridId).jqGrid('getGridParam','colModel');
	var colDefine = new Array();
	var colDefineIndex = 0;
	for(var mi=0;mi<colModel.length;mi++){
		var col = colModel[mi];
		if(col.name!="rn"&&col.name!="cb"&&!col.hidden){
			var label = col.label?col.label:col.name;
			var type = col.formatter?typeof(col.formatter)=='function'?1:col.formatter:1;
			var align = col.align?col.align:"left";
			var width = col.width?parseInt(col.width)*20:50*20;
			colDefine[colDefineIndex] = {name:col.name,type:type,align:align,width:width,label:label};
			colDefineIndex++;
		}
	}
	var colDefineStr = json2str(colDefine);
	var page=1,pageSize=20,sortname,sortorder;
	page = jQuery("#"+gridId).jqGrid('getGridParam','page');
	pageSize = jQuery("#"+gridId).jqGrid('getGridParam','rowNum');
	if(outPutType=='all'){
		pageSize = 100000;
		page = 1;
	}
	sortname = jQuery("#"+gridId).jqGrid('getGridParam','sortname');
	sortorder = jQuery("#"+gridId).jqGrid('getGridParam','sortorder');
	var searchParam = "&rows="+pageSize+"&page="+page+"&sidx="+sortname+"&sord="+sortorder;
	url += searchParam;
	//var u =  'outPutExcel?'+param+"&entityName="+entityName;
	$.ajax({
		url: url,
		type: 'post',
		data:{outputExcel:true,title:title,colDefineStr:colDefineStr},
		dataType: 'json',
		async:false,
		error: function(data){
			alertMsg.error("系统错误！");
		},
		success: function(data){
			var downLoadFileName = data["userdata"]["excelFullPath"];
			var filePathAndName = downLoadFileName.split("@@");
			var url = "${ctx}/downLoadExel?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
	 		//url=encodeURI(url);
	 		location.href=url;
		}
	}); 
}
function upDigit(n)   
{  
    var fraction = ['角', '分'];  
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];  
    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];  
    var head = n < 0? '欠': '';  
    n = Math.abs(n);  

    var s = '';  

    for (var i = 0; i < fraction.length; i++)   
    {  
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');  
    }  
    s = s || '整';  
    n = Math.floor(n);  

    for (var i = 0; i < unit[0].length && n > 0; i++)   
    {  
        var p = '';  
        for (var j = 0; j < unit[1].length && n > 0; j++)   
        {  
            p = digit[n % 10] + unit[1][j] + p;  
            n = Math.floor(n / 10);  
        }  
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;  
    }  
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');  
} 

//获取混合字符串的长度
function getMixStrlen(s) { 
	var l = 0; 
	var a = s.split(""); 
	for (var i=0;i<a.length;i++) { 
		if (a[i].charCodeAt(0)<299) { 
			l++; 
		} else { 
			l+=2; 
		} 
	} 
	return l; 
}
