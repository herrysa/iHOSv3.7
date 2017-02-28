
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() { 
		var bugetNormGridIdString="#bugetNorm_gridtable";
		var bugetNormGrid = jQuery(bugetNormGridIdString);
		var bugetNormFullSize = jQuery("#container").innerHeight()-56;
		setLeftTreeLayout('bugetNorm_container','bugetNorm_gridtable',bugetNormFullSize);
		var ztreesetting_bugetNorm = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : onbugetNormClick
				},
				data : {
					key : {
						name : "name"
					},
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pId"
					}
				}
		};
		function onbugetNormClick(event, treeId, treeNode, clickFlag) { 
			var nodeId = treeNode.id;
			var urlString = "bugetNormGridList";
			if(treeNode.id!="-1"){
				urlString=urlString+"?filter_EQS_deptId.departmentId="+treeNode.id;	
			}
		    urlString = encodeURI(urlString);
			jQuery(bugetNormGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		$.get("getBudgetDeptTree", {
			"_" : $.now()
		}, function(data) {
			var budgetDeptTreeNodes = data.budgetDeptTreeNodes;
			var bugetDeptTree = $.fn.zTree.init($("#bugetDeptTreeLeft"),
					ztreesetting_bugetNorm, budgetDeptTreeNodes);
			var nodes = bugetDeptTree.getNodes();
			bugetDeptTree.expandNode(nodes[0], true, false, true);
			bugetDeptTree.selectNode(nodes[0]);
			
		});
		jQuery("#bugetNorm_expandTree").text("展开");
    	bugetNormGrid.jqGrid({
    		url : "bugetNormGridList",
    		editurl:"bugetNormGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'normId',index:'normId',align:'center',label : '<s:text name="bugetNorm.normId" />',hidden:true,key:true},
			{name:'deptId.name',index:'deptId',align:'left',label : '<s:text name="bugetNorm.deptId" />',hidden:false,width:150},
			{name:'bmSubjId.bugetSubjCode',index:'bmSubjtId.bugetSubjCode',align:'left',label : '<s:text name="bugetNorm.bmSubjtCode" />',hidden:false,width:100},
			{name:'bmSubjId.bugetSubjName',index:'bmSubjtId.bugetSubjName',align:'left',label : '<s:text name="bugetNorm.bmSubjtName" />',hidden:false,width:150},
			{name:'norm',index:'norm',align:'right',label : '<s:text name="bugetNorm.de" />',hidden:false,editable:true,editype:'number',editoptions:{dataEvents:[{type:'blur',fn: function(e) { computeBmNormAmount(this,null); }}]},formatter:'number',width:70},
			{name:'rs',index:'rs',align:'right',label : '<s:text name="bugetNorm.rs" />',hidden:false,editable:true,editype:'integer',editoptions:{dataEvents:[{type:'blur',fn: function(e) { computeBmNormAmount(null,this); }}]},formatter:'integer',width:70},
			{name:'amount',index:'amount',align:'right',label : '<s:text name="bugetNorm.amount" />',hidden:false,formatter:'number',width:100}
			],
        	jsonReader : {
				root : "bugetNorms", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'normId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="bugetNormList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"bugetNorm_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(bugetNormGrid).jqGrid('bindKeys');
    jQuery("#bugetNorm_gridtable_add_c").click(function(){
    	var zTree = $.fn.zTree.getZTreeObj("bugetDeptTreeLeft"); 
        var nodes = zTree.getSelectedNodes();
        if(nodes.length==0||nodes[0].id==-1){
      		alertMsg.error("请选择预算责任中心。");
        	return;
     	}else if(nodes.length>1){
     		alertMsg.error("只能选择一个预算责任中心。");
        	return;
     	}
    	var url = "selectBugetSubjList?&navTabId=bugetNorm_gridtable&deptId="+nodes[0].id;
		var winTitle='选择预算科目';
		$.pdialog.open(url,'sellectBugetSubj',winTitle, {mask:true,width : 800,height : 500});
    });
    jQuery("#bugetNorm_gridtable_edit_c").click(function(){
    	fullGridEdit("#bugetNorm_gridtable");
    });
    jQuery("#bugetNorm_gridtable_canceledit").click(function(){
    	var currentPage = jQuery("#bugetNorm_gridtable").jqGrid('getGridParam', 'page');
		
		jQuery("#bugetNorm_gridtable").trigger('reloadGrid',
			[ {
					page : currentPage
				} ]);
    });
    
    jQuery("#bugetNorm_gridtable_save").click(function(){
    	var grid = jQuery("#bugetNorm_gridtable");
    	var rowNum = grid.getDataIDs().length;
		var ret = grid.jqGrid('getRowData');
		if(rowNum > 0){
	    	 var rowIds = grid.getDataIDs();
	    	 var i=0;
	    	 for (i=0;i<rowNum;i++){
	    		var id = rowIds[i];
	    		var rs = jQuery("#"+id).find("input[name=rs]").eq(0).val();
	    		var norm = jQuery("#"+id).find("input[name=norm]").eq(0).val();
	    		if(rs!=0&&!rs){
	    			rs = 0 ;
	    		}
	    		if(norm!=0&&!norm){
	    			norm = 0 ;
	    		}
	    		ret[i]["rs"] = rs;
	    		ret[i]["norm"] = norm;
	    	 }
		}
    	$.post("saveBugetNorm", {
    		"_" : $.now(),navTabId:'bugetNorm_gridtable',gridData:JSON.stringify(ret)
		}, function(data) {
			formCallBack(data);
		});
    });
  	});
	function computeBmNormAmount(obj,obj2){
		if(!numberValidate(obj)){
  			return;
  		}
		var norm = 0,rs = 0,thisTr,rowid;
		if(!obj2){
			thisTr = jQuery(obj).parent().parent();
			rowid = thisTr.attr("id");
			rs = jQuery(obj).parent().parent().find("input[name=rs]").eq(0).val();
			norm = jQuery(obj).val();
			jQuery(obj).val(toDecimal(norm,2));
		}else{
			thisTr = jQuery(obj2).parent().parent();
			rowid = thisTr.attr("id");
			norm = jQuery(obj2).parent().parent().find("input[name=norm]").eq(0).val();
			rs = jQuery(obj2).val();
			jQuery(obj2).val(toDecimal(rs,0));
		}
  		
  		var amount = norm*rs;
  		amount = toDecimal(amount);
  		//jQuery("#bugetNorm_gridtable").setCell(rowid,"rs",rs);
  		//jQuery("#bugetNorm_gridtable").setCell(rowid,"norm",norm);
  		jQuery("#bugetNorm_gridtable").setCell(rowid,"amount",amount);
	}
	function numberValidate(obj){
  		var objValue = jQuery(obj).val();
  		if(!isFloatOrNull(objValue)){
  			jQuery(obj).val("")
  			alertMsg.error("请输入数字！");
  			return false;
  		}else{
  			//jQuery(obj).val(toDecimal(objValue));
  			return true;
  		}
  	}
</script>

<div class="page">
	<%-- <div id="bugetNorm_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bugetNorm_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetNorm.normId'/>:
						<input type="text" name="filter_EQS_normId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetNorm.amount'/>:
						<input type="text" name="filter_EQS_amount"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetNorm.bmSubjtId'/>:
						<input type="text" name="filter_EQS_bmSubjtId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetNorm.de'/>:
						<input type="text" name="filter_EQS_de"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetNorm.deptId'/>:
						<input type="text" name="filter_EQS_deptId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetNorm.rs'/>:
						<input type="text" name="filter_EQS_rs"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(bugetNorm_search_form,bugetNorm_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(bugetNorm_search_form,bugetNorm_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="bugetNorm_gridtable_add_c" class="addbutton" href="javaScript:" ><span>选择预算科目</span></a></li>
				<li><a id="bugetNorm_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
				<li><a id="bugetNorm_gridtable_edit_c" class="changebutton"  href="javaScript:"><span>编辑</span></a></li>
				<li><a id="bugetNorm_gridtable_canceledit" class="canceleditbutton"  href="javaScript:"><span>取消编辑</span></a></li>
				<li><a id="bugetNorm_gridtable_save" class="savebutton"  href="javaScript:"><span>保存</span></a></li>
			
			</ul>
		</div>
		<div id="bugetNorm_container">
		<div id="bugetNorm_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
		<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('bugetDeptTreeLeft',this)"/>
					</span>
				</div>
			<a style="position: relative; float: right;top:5px" id="bugetNorm_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#bugetNorm_expandTree").click(function(){
					var thisText = jQuery(this).text();
					var bugetNormTee = $.fn.zTree.getZTreeObj("bugetDeptTreeLeft");
					if(thisText=="展开"){
						bugetNormTee.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						bugetNormTee.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="bugetDeptTreeLeft" class="ztree"></div>
		</div>
		<div id="bugetNorm_layout-center" class="pane ui-layout-center">
		<div id="bugetNorm_gridtable_div" layoutH="58" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bugetNorm_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bugetNorm_gridtable_addTile">
				<s:text name="bugetNormNew.title"/>
			</label> 
			<label style="display: none"
				id="bugetNorm_gridtable_editTile">
				<s:text name="bugetNormEdit.title"/>
			</label>
			<div id="load_bugetNorm_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bugetNorm_gridtable"></table>
			<!--<div id="bugetNormPager"></div>-->
		</div>
		
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bugetNorm_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bugetNorm_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bugetNorm_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	</div>
	</div>
</div>