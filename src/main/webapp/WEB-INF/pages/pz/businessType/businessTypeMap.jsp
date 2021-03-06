<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script>
	/* var pzMapGridDefine = {
		key:"pzMap_gridtable",
		main:{
			Build : '',
			Load :'',
		}
	};
	supcanGridMap.put("pzMap_gridtable",pzMapGridDefine); */
	var changeData${random}${type} = {};
	var sqlMap${random}${type};
	var colParam${random}${type};
	var type = "${type}";
	jQuery(document).ready(function(){
		//initPzMapGrid();
		var businessId = "${businessId}";
		if(!businessId) {
			jQuery("#${random}${type}pzMap_page").hide();
		}
		var type = "${type}";
		//alert(businessId);
		//gridResize(null,'${random}businessAccount_tab','${random}${type}pzMap_gridtable','single',20,30);
		/* var zhGridSetting_pzMap={
				url : "businessAccountMapGrid",//+businessId,
				datatype : "local",
				mtype : "GET",
				formId:'',
				paramName:'businessAccountMapData',
				saveUrl:"saveBusinessAccountMap?businessId=${businessId}&type=${type}",
				initColumn:"",
				afterSaveFun : function(data) {
					
				},
				jsonReader : {
					root : "rowInfo", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
				viewrecords : true,
				height : 300,
				width : 100,
				gridview : true,
				rowNum:500,
				rownumbers : true,
				loadui : "disable",
				multiselect : true,
				multiboxonly : true,
				shrinkToFit : false,
				autowidth : false,
				cellEdit : true,
				cellsubmit : 'clientArray',
				footerrow : false,
				onSelectRow : function(rowid, status, e) {
				},

				gridComplete : function() {
				}
			}//end setting */
			jQuery.ajax({
		    	url: 'businessAccountMapGrid',
		        data: {businessId:businessId,type:type},
		        type: 'post',
		        dataType: 'json',
		        async:false,
		        error: function(data){
		        },
		        success: function(data){
		        	/* var zTree = $.fn.zTree.getZTreeObj("${random}businessAccountLeftTree");
		        	var nodes = zTree.getSelectedNodes();
		        	if(nodes == null || nodes.length <= 0 || nodes[0].children != null) {
		        		jQuery("#${random}pzMap_gridtable_div").hide();
		        	} */
		        	var colInfo = data.colInfo;
		        	var rowInfo = data.rowInfo;
		        	sqlMap${random}${type} = data.colSql;
		        	colParam${random}${type} = data.colParam;
		        	if(businessId) {
		        		var url = "reloadPzMapData?businessId="+businessId+"&type="+type;
		        		initPzMapGridTable(data.colInfo,url);
		        	}
		        	//console.log(colInfos);
		        	//zhGridSetting_pzMap.colModel = colInfo;
		        	//zhGridSetting_pzMap.dataSource = rowInfo;
					//jQuery("#${random}${type}pzMap_gridtable").zhGrid(zhGridSetting_pzMap);
					//jQuery("#${random}pzMap_gridtable").addLocalData(rowInfo);
					//jQuery("#${random}${type}pzMap_gridtable").fullEdit();
		        }
		    });
		
		jQuery("#${random}${type}pzMap_gridtable").on("keyup","tr",function(evt){
			var keyCode = evt.keyCode;
			//alert(keyCode);
			if(keyCode=='13'){
				editAcct();
			}else if(keyCode=='40'||keyCode=='38'){
				chargeKeyPress(jQuery("#${random}${type}pzMap_gridtable"),evt);
				stopPropagation();
				return ;
			}else if(keyCode=='27'){
				var cancelSel = jQuery("#${random}${type}pzMap_gridtable").data("editting");
				jQuery("#${random}${type}pzMap_gridtable").jqGrid('restoreRow',cancelSel);
				jQuery("#${random}${type}pzMap_gridtable").removeData("editting");
				jQuery("#${random}${type}pzMap_gridtable").removeData("changeSelect");
				delete changeData${random}${type}[cancelSel+"_temp"];
				jQuery("#"+cancelSel,"#${random}${type}pzMap_gridtable").focus();
			}
		});
		
		//刷新	
		jQuery("#${random}${type}initPzMap").click(function() {
			jQuery.ajax({
				url : 'initPzMapData',
				dataType : 'json',
				type : 'post',
				data : {businessId:businessId,type:type},
				async:false,
				error: function(data){
				},
				success: function(data){
					//需要刷新页面
					if(data.statusCode == 200) {
						alertMsg.correct(data.message);
					}
					var currentPage = jQuery("#${random}${type}pzMap_gridtable").jqGrid('getGridParam', 'page');
					jQuery("#${random}${type}pzMap_gridtable").trigger('reloadGrid',[{page : currentPage}]);
				}
			});
		});
		
		//保存
		jQuery("#${random}${type}savePzMap").click(function(){
			//console.log(json2str(changeData${random}${type}));
			if(jQuery.isEmptyObject(changeData${random}${type})){
				alertMsg.error("没有需要保存的数据！");
				return ;
			}
			var edittingId = jQuery("#${random}${type}pzMap_gridtable").data("editting");
			if(edittingId){
				changeData${random}${type}[edittingId] = changeData${random}${type}[edittingId+"_temp"];
				delete changeData${random}${type}[edittingId+"_temp"];
			}
			var changedStr = JSON.stringify(changeData${random}${type});
			jQuery.ajax({
				url : 'saveBusinessAccountMap',
				dataType : 'json',
				type : 'post',
				data : {businessAccountMapData:changedStr,businessId:businessId,type:type},
				async:false,
				error: function(data){
				},
				success: function(data){
					formCallBack(data);
					jQuery("#${random}${type}pzMap_gridtable").removeData("editting");
					var currentPage = jQuery("#${random}${type}pzMap_gridtable").jqGrid('getGridParam', 'page');
					jQuery("#${random}${type}pzMap_gridtable").trigger('reloadGrid',[{page : currentPage}]);
				}
			});
			//jQuery("#${random}${type}pzMap_gridtable").saveGrid();
			//jQuery("#${random}${type}pzMap_gridtable").
		});
		/* jQuery("#${random}delPzMap").click(function(){
			
			jQuery("#${random}pzMap_gridtable").emptyRow();
		}); */
		//修改
		jQuery("#${random}${type}changePzMap").click(function(){
			editAcct();
		});
		jQuery("#${random}${type}cancelchangePzMap").click(function(){
			var cancelSel = jQuery("#${random}${type}pzMap_gridtable").data("editting");
			jQuery("#${random}${type}pzMap_gridtable").jqGrid('restoreRow',cancelSel);
			jQuery("#${random}${type}pzMap_gridtable").removeData("editting");
			delete changeData${random}${type}[cancelSel+"_temp"];
			jQuery("#"+cancelSel,"#${random}${type}pzMap_gridtable").focus();
		});
		
		jQuery("#${random}${type}editPzMap").click(function(){
			fullGridEdit('#${random}${type}pzMap_gridtable');
			jQuery("#${random}${type}pzMap_gridtable").on("keydown","input[type='text']",function(evt){
				var ctrlKey = evt.ctrlKey;
		    	var keyCode = evt.keyCode;
		    	var funcKey = "";
		    	if(ctrlKey&&keyCode=='40'){
		    		addComboGrid(thisInput);
		    		chargeKeyPress(jQuery("#${random}${type}pzMap_gridtable"),evt);
		    	}else{
		    		addComboGrid(jQuery(this));
		    	}
			});
		});
		
		//取消编辑
		jQuery("#${random}${type}cancelEditPzMap").click(function() {
			var currentPage = jQuery("#${random}${type}pzMap_gridtable").jqGrid('getGridParam', 'page');
			jQuery("#${random}${type}pzMap_gridtable").trigger('reloadGrid',[{page : currentPage}]);
		});
		
		jQuery.ajax({
			url : 'getPzMapSearchContent',
			dataType : 'json',
			type : 'post',
			data : {businessId:businessId,type:type},
			async:false,
			error: function(data){
			},
			success: function(data){
				var searchContents = eval(data.searchContents);
				if(searchContents) {
					jQuery.each(searchContents,function(n,value) {
						jQuery("#${random}${type}pzMap_search_form").append("<label class='queryarea-label' >");
						jQuery("#${random}${type}pzMap_search_form").append(value.label+":");
						jQuery("#${random}${type}pzMap_search_form").append("<input type='text' class='pzMapTreeSelect' id='${random}${type}pzMap_"+value.key+"' name='"+value.key+"_name'/>");
						//jQuery("#${random}${type}pzMap_search_form").append("<input type='hidden' id='${random}${type}pzMap_"+value.key+"_sql' value='"+value.sql+"' />");
						//jQuery("#${random}${type}pzMap_search_form").append("<input type='hidden' id='${random}${type}pzMap_"+value.key+"_id' name='"+value.key+"'/>");
						jQuery("#${random}${type}pzMap_search_form").append("</label>");
					});
					jQuery("#${random}${type}pzMap_search_form").append("<div class='buttonActive' style='float:right'><div class='buttonContent'><button type='button' id='${random}${type}pzMap_buttonReload'><s:text name='button.search'/></button></div></div>");
				}
				
			}
		});
		
		/* jQuery(".pzMapTreeSelect").each(function() {
			var id = jQuery(this).attr("id");
			var sql = jQuery("#" +id + "_sql").val();
			jQuery("#"+id).treeselect({
				optType : "multi",
				dataType : 'sql',
				sql : sql,
				exceptnullparent : true,
				lazy : false,
				minWidth : '220px',
				selectParent : false
			});
			
		}); */
		jQuery("#${random}${type}pzMap_buttonReload").click(function() {
			var datas = [];
			jQuery("input[type='text']","#${random}${type}pzMap_search_form").each(function(){
				var id = jQuery(this).attr("id");
				var name = jQuery(this).attr("name")
				var value = jQuery("#" + id).val();
				if(value) {
					var data = {
							name : name,
							value : value
					};
					datas.push(data);
				}
			});
			var dataStr = JSON.stringify(datas);
			var url = "reloadPzMapData?businessId="+businessId+"&type="+type+"&datas="+dataStr;
			url = encodeURI(url);
			jQuery("#${random}${type}pzMap_gridtable").jqGrid('setGridParam', {
				url : url,
				page : 1
			}).trigger("reloadGrid");
		});
		
		//批量修改
		jQuery("#${random}${type}batchEdit").click(function() {
			var sid = jQuery("#${random}${type}pzMap_gridtable").jqGrid("getGridParam","selarrrow");
   	        if(sid==null|| sid.length ==0){
   	        	alertMsg.error("请选择记录。");
   	 			return;
 			}
   	 		var winTitle='批量修改';
   	 		var url = "pzMapBatchEdit?id="+sid+"&businessId=${businessId}&type=${type}&navTabId=${random}${type}pzMap_gridtable";
   	 		$.pdialog.open(url,'batchEditPzMap',winTitle, {mask:true,width : 650,height : 480});
		});
		
		
	});
	function editAcct(){
		var sid = jQuery("#${random}${type}pzMap_gridtable").jqGrid("getGridParam","selarrrow");
		if (sid == null || sid.length != 1) {
				alertMsg.error("请选择一条记录");
				return;
		}
		var edittingId = jQuery("#${random}${type}pzMap_gridtable").data("editting");
		if(edittingId==sid){
			return;
		}
		if(edittingId){
			alertMsg.error("每次只能编辑一条记录!");
			return;
		}
		//jQuery("#${random}${type}pzMap_gridtable").editRow(sid[0]);
		//var rowIndex = jQuery(this).parent().parent().index();
		//var colIndex = jQuery(this).parent().index();
		jQuery("#${random}${type}pzMap_gridtable").editRow(sid);
		jQuery("#${random}${type}pzMap_gridtable").data("editting",sid);
		var acctTreeFuction = function($this){
			var url = "#DIA_inline?inlineId=${random}${type}acctTreeDiv";
			$.pdialog.open(url, 'acctTree', "选择会计科目", {
				mask:true,width:480,height:420
			});
			makeAcctTree($this);
		}
		jQuery("#${random}${type}pzMap_gridtable").find("input").each(function(){
			jQuery(this).detailButton({
				type:"click",
				hostAction:"dblclick",
				dealFuction:acctTreeFuction
			});
			addComboGrid(jQuery(this));
			var $this = jQuery(this);
			setTimeout(function(){
				$this.bind("keyup",function(e){
					var keyCode = e.keyCode;
					if(keyCode=='13'){
						var edited = jQuery(this).attr("edited");
						if(edited=='true'){
							var thisRow = jQuery(this).parent().parent();
							var rowId = thisRow.attr("id");
							var rowInputs = thisRow.find("input[type='text']:visible");
							var thisIndex = rowInputs.index(jQuery(this));
							if(thisIndex==rowInputs.length-1){
								changeData${random}${type}[rowId] = changeData${random}${type}[rowId+"_temp"];
								delete changeData${random}${type}[rowId+"_temp"];
								jQuery("#${random}${type}pzMap_gridtable").saveRow(rowId,null,'clientArray');
								jQuery("#${random}${type}pzMap_gridtable").removeData("editting");
								jQuery("#${random}${type}pzMap_gridtable").data("changeSelect","true");
								thisRow.find('td').css("background-color", "#00CED1");
								jQuery(this).attr("edited","false");
								jQuery("#"+rowId,"#${random}${type}pzMap_gridtable").focus();
							}else{
								rowInputs.eq(thisIndex+1).focus();
							}
							//return ;
							//var rowIndex = jQuery(this).parent().parent().index();
							//var colIndex = jQuery(this).parent().index();
							//jQuery("#${random}${type}pzMap_gridtable").saveCell(rowIndex-1,colIndex);
							
						}
						stopPropagation();
					}
				});
			},1000);
			
			jQuery(this).blur(function() {
				var val = jQuery(this).val();
				var id = jQuery(this).attr("id");
				var rowId = id.substr(0,id.indexOf("_"));
				var rowChangedData = changeData${random}${type}[rowId];
				if(rowChangedData==null){
					rowChangedData = {};
				}
				var elem = id.substring(id.indexOf("_")+1);
				if(!val) {
					rowChangedData[elem] = "";
					elem = elem.replace("_name","");
					rowChangedData[elem] = "";
					changeData${random}${type}[rowId+"_temp"] = rowChangedData;
				}
			});
		});
	}
	/* function initPzMapGrid(){
		jQuery.ajax({
	    	url: 'businessAccountMapGrid',
	        data: {businessId:''},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){   
	        	var colInfo = data.colInfo;
	        	var rowInfo = data.rowInfo;
	        	//console.log(colInfos);
	        	var pzMapGrid = cloneObj(supCanTreeListGrid);
	        	pzMapGrid.Cols = colInfo;
	    		pzMapGridDefine.main.Build = JSON.stringify(pzMapGrid);
	    		var pzMapGridData = {};
	    		pzMapGridData.Record = rowInfo;
				pzMapGridDefine.main.Load = JSON.stringify(pzMapGridData);
	        	//pzMapGridDefine.main.Build = "[{name:'gzId',index:'gzId',align:'center',text : '11',width:80,editable:false,dataType:'string'}]";
	        	insertTreeListToDiv("pzMap_gridtable_div","pzMap_gridtable","","100%");
	        }
	    });
	} */
	
	function initPzMapGridTable(colModelDatas,url) {
		/* for(var modelIndex in colModelDatas){
			var model = colModelDatas[modelIndex];
			if(model.editable=='true'){
				model.editable = true;
			}
			colModelDatas[modelIndex] = model;
		} */
		var gridString = "#${random}${type}pzMap_gridtable";
		var gridObject = jQuery(gridString);
		gridObject.jqGrid({
			url : url,//+businessId,
			datatype : "json",
			mtype : "GET",
			colModel : colModelDatas,
			//saveUrl:"saveBusinessAccountMap?businessId=${businessId}&type=${type}",
			jsonReader : {
				root : "rowInfo", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
			sortname: "",
			sortorder: 'desc',
			viewrecords : true,
			height : 300,
			gridview : true,
			rowNum:500,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : true,
			shrinkToFit : false,
			autowidth : false,
			cellEdit : false,
			cellsubmit : 'clientArray',
			onCellSelect : function(rowid,iCol,cellcontent,e){
				
			},
			onSelectRow : function(rowid, status, e) {
			},
			afterEditCell : function(rowid, cellname, value, iRow, iCol){
				var thisInput = jQuery("#"+rowid).find("input[name='"+cellname+"']").eq(0);
				//thisInput.attr("id",cellname+rowid);
				if(thisInput.length>0){
					addComboGrid(thisInput);
				}
			},
			afterSaveCell : function(rowid,name,val,iRow,iCol) {
			},
			gridComplete : function() {
				jQuery("#${random}${type}pzMap_gridtable").removeData("changeSelect");
				jQuery(".ui-jqgrid-bdiv","#${random}${type}pzMap_gridtable").keypress(function(){
					alert();
					return false;
				});
				var rowNum = jQuery("#${random}${type}pzMap_gridtable").getDataIDs().length;
				if(rowNum<=0){
					var tw = jQuery("#${random}${type}pzMap_gridtable").outerWidth();
					jQuery("#${random}${type}pzMap_gridtable").parent().width(tw);
					jQuery("#${random}${type}pzMap_gridtable").parent().height(1);
				}else{
					var selectId = jQuery("#${random}${type}pzMap_gridtable").data("selectId");
					var editId = jQuery("#${random}${type}pzMap_gridtable").data("editting");
					if(editId){
						jQuery("#${random}${type}pzMap_gridtable").jqGrid('setSelection',editId);
						jQuery("#"+editId,"#${random}${type}pzMap_gridtable").focus();
					}else if(selectId){
						jQuery("#${random}${type}pzMap_gridtable").jqGrid('setSelection',selectId);
						jQuery("#"+selectId,"#${random}${type}pzMap_gridtable").focus();
					}else{
						var firstId = jQuery("#${random}${type}pzMap_gridtable").getDataIDs()[0];
						jQuery("#${random}${type}pzMap_gridtable").jqGrid('setSelection',firstId);
						jQuery("#"+firstId,"#${random}${type}pzMap_gridtable").focus();
					}
				}
				var dataTest = {"id":"${random}${type}pzMap_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
			}
		});
		//jQuery("#keynav").jqGrid('navGrid','#pkeynav',{edit:false,add:false,del:false});
		//jQuery("#${random}${type}pzMap_gridtable").jqGrid('bindKeys', {"onEnter":function( rowid ) { alert("You enter a row with id:"+rowid)} } );
	}
	
	function addComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","\.");
		jQuery(elem).attr("id",elemId);
		var elemName = jQuery(elem).attr("name");
		var rowId = jQuery(elem).parent().parent().attr("id");//replace(elemName,"");
		var sql = sqlMap${random}${type}[elemName];
		var param = colParam${random}${type}[elemName];
		//console.log(sql);
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList', 
			queryParams : {
				sql:sql,
				cloumns : param
			},
			autoFocus : false,
			showOn : false, 
			rows:10,
			width:600,
			sidx:"id",
			colModel : [ {
				'columnName' : 'ID',
				'width' : '40',
				'label' : 'id',
				'align' : 'left',
				hidden : true
			}, {
				'columnName' : 'NAME',
				'width' : '40',
				'align' : 'left',
				'label' : '名称'
			}
			],
			beforeMove :function(){
				jQuery("#${random}${type}pzMap_gridtable").data("changeSelect","false");
				return true;
			},
			_create: function( event, item ) {
				alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
				//alert(ui.item.NAME);
				//alert(rowId);
				//alert(elemName);
				//jQuery("#${random}${type}pzMap_gridtable").setCell(rowId,elemName,ui.item.NAME);
				//jQuery(elem).val(ui.item.ID)
				jQuery(elem).val(ui.item.NAME);
				var rowChangedData = changeData${random}${type}[rowId+"_temp"];
				if(rowChangedData==null){
					rowChangedData = {};
				}
				rowChangedData[elemName] = ui.item.NAME;
				elemName = elemName.replace("_name","");
				rowChangedData[elemName] = ui.item.ID;
				changeData${random}${type}[rowId+"_temp"] = rowChangedData;
				setTimeout(function(){
					jQuery(elem).attr("edited","true");
				},200);
				//var thisTr = jQuery(rowId);//jQuery("#${random}${type}pzMap_gridtable").getTr($(elem));
				//thisTr.find("input[name='"+elemName+"']").val(ui.item.ID).blur();
				//var elemCode = elemName.substring(0,elemName.lastIndexOf("_name"));
				//thisTr.find("input[name='"+elemCode+"']").val(ui.item.ID).blur();
				//thisTr.find("input[name='"+elemName+"']").val(ui.item.NAME).blur();
				//thisTr.attr("used","true");
				return false;
			}
		});
	}

</script>

<style>


 
</style>
<div class="page" id="${random}${type}pzMap_page">

	<div id="${random }pzMapData" name="pzMapData" style="display:none">${voucherData}</div>
	<div id="${random}${type}pzMap_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="${random}${type}pzMap_search_form" class="queryarea-form" >
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="${random}${type}pzMap_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="${random}${type}initPzMap" class="initbutton" href="javaScript:" >
					<span><s:text
								name="刷新" />
					</span>
					</a>
				</li>
				<li>
					<a id="${random}${type}changePzMap" class="edit" href="javaScript:" >
					<span><s:text name="button.edit" />
					</span>
					</a>
				</li>
				<li>
					<a id="${random}${type}cancelchangePzMap" class="canceleditbutton" href="javaScript:" >
					<span>取消修改
					</span>
					</a>
				</li>
				<li><a id="${random}${type}batchEdit" class="getdatabutton" href="javaScript:" >
					<span><s:text name="批量修改" /></span>
					</a>
				</li>
				<%-- <li>
					<a id="${random}${type}editPzMap" class="editbutton" href="javaScript:" >
					<span>编辑
					</span>
					</a>
				</li>
				<li>
					<a id="${random}${type}cancelEditPzMap" class="canceleditbutton" href="javaScript:" >
					<span>取消编辑
					</span>
					</a >
				</li>--%>
				<li><a id="${random}${type}savePzMap" class="addbutton" href="javaScript:" >
					<span><fmt:message
								key="button.save" />
					</span>
					</a>
				</li>
				<li><a id="${random}${type}colShow" class="settlebutton" href="javaScript:setColShow('${random}${type}pzMap_gridtable','${businessId}')" >
					<span><s:text name="button.setColShow" /></span>
					</a>
				</li>
				<%-- <li><a id="${random}delPzMap" class="addbutton" href="javaScript:" >
					<span><fmt:message
								key="button.del" />
					</span>
					</a>
				</li> --%>
			</ul>
		</div>
		<%-- <div id="pzMap_gridtable_div"  class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="pzMap_gridtable_navTabId" value="${sessionScope.navTabId}">
		</div> --%>
		<div id="${random}${type}pzMap_gridtable_div"  tablecontainer="${random}businessAccount_tab"  extraHeight="116" extraWidth="2">
			<table id="${random}${type}pzMap_gridtable"></table>
		</div>
		<div class="panelBar" id="${random}${type}pzMap_gridtable_pageBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="${random}${type}pzMap_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}${type}pzMap_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="${random}${type}pzMap_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
		<%-- <div style="margin:5px 10px 5px 5px">
			<div id="${random}pzMapTable_div" class="voucherCard_div" layoutH=140>
				<table id="${random}pzMapTable_head" class="list voucherCard_head">
					<thead>
					<tr>
						<s:iterator value="colInfo" status="ci">
							<th name="${fieldName}" align="left" perWidth="30" class="pzCell_color">${colName}</th>
						</s:iterator>
						<th name="scrollbar" style="display:none" class="firstTr"></th>
					</tr>
					</thead>
				</table>
			<div id="${random}pzMapTableDiv" style="overflow: auto;">
				<table id="${random}pzMapTableTable" class="voucherCard list" targetType="navTab" asc="asc" desc="desc">
				<tbody>
				<tr class="firstTr">
					<s:iterator value="colInfo" status="ci">
						<th name="${fieldName}" class="firstTr">
						</th>
					</s:iterator>
				</tr>
				<c:forEach  items="${rowInfo }" var="row" varStatus="ristatus">
				<tr>
					<c:forEach  items="${colInfo }" var="col" varStatus="cistatus">
						<td name="abstract" style="border-right: solid 1px #E0D6D6; ">
						<span name="toShow" class="pzCell_color">
							${row[col.fieldName] }
						</span>
						<span name="toEdit" style="display:none">
							<input name=${fieldName} type="text"/>
						</span>
						</td>
					</c:forEach>
				</tr>
				</c:forEach>
			
		</tbody>
	</table>
	</div>
	</div>
	</div> --%>
	<div id="${random}${type}acctTreeDiv" style="display:none">
	<div class="page">
		<div style="margin: 5px;padding: 5px;">
			<label style="cursor: pointer;position: absolute;right: 50px;text-decoration: underline;" id="${random}${type}acctTree_expandTree" onclick="toggleExpandAccTree(this,'${random}${type}acctTree')">展开</label>
			<label>查找会计科目:</label>
			<input type="text" id="${random}${type}acctInput" >
			<input type="hidden" id="${random}${type}acctTree_cellId">
			<div style="margin-top: 10px;height: 300px;overflow: auto;">
				<div id="${random}${type}acctTree" class="ztree"></div>
			</div>
		</div>
	</div>
	<script>
	function focusAcctInput() {
		jQuery("#${random}${type}acctInput").get(0).focus();
	}
	jQuery("#${random}${type}acctTreeDiv").ready(function() {
		jQuery("#${random}${type}acctInput").keyup(function(evt) {
			var keyCode = evt.keyCode;//up:38;down:40;enter:13
			if(keyCode != "13" && keyCode != "38" && keyCode != "40") {
				searchTree('${random}${type}acctTree',this);
			} else {
				var acctTree = $.fn.zTree.getZTreeObj("${random}${type}acctTree");
				var sNodes = acctTree.getSelectedNodes();
				var node,rNode;
				if (sNodes.length > 0) {
					node = sNodes[0];
				} else {
					node = acctTree.getNodes()[0];
				}
				if(keyCode == "38") {
					if(node) {
						var isParent = node.isParent;
						if(isParent) {
							rNode = getCNode0Pre(node);
						} else {
							rNode = getPreNode0(node);
						}
						if(!rNode) {
							focusAcctInput();
							return;
						}
					}
				} else if(keyCode == "40") {
					if(node) {
						var isParent = node.isParent;
						if(isParent) {
							rNode = getCNode0(node);
						} else {
							rNode = getNextNode0(node);
						}
						if(!rNode) {
							focusAcctInput();
							return;
						}
					}
					
				} else {
					chooseSelectNode(node);
					return;
				}
				acctTree.selectNode(rNode,false);
				focusAcctInput();
			}
		});
		jQuery("#${random}${type}acctTreeDiv").children().each(function() {
			jQuery(this).click(function() {
				focusAcctInput();
			});
		});
	});
	
	function getPPNode0(node) {
		var parentNode = node.getParentNode();
		if(parentNode) {
			if(parentNode.isFirstNode) {
				return getPPNode0(parentNode);
			} else {
				return getCNode0Pre(parentNode.getPreNode());
			}
		} else {
			return node;
		}
	}
	
	function getPreNode0(node) {
		if(node.isFirstNode) {
			var parentNode = node.getParentNode();
			if(parentNode.isFirstNode) {
				return getPPNode0(parentNode);
			} else {
				return getPreNode0(parentNode);
			}
		} else {
			var preNode = node.getPreNode();
			return getCNode0Pre(preNode);
		}
	}
	
	function getCNode0Pre(node) {
		var isParent = node.isParent;
		if(isParent) {
			var children = node.children;
			return getCNode0Pre(children[children.length - 1]);
		} else {
			return node;
		}
	}
	
	
	function getCNode0(node) {
		var isParent = node.isParent;
		if(isParent) {
			return getCNode0(node.children[0]);
		} else {
			return node;
		}
	}
	
	function getPNNode0(node) {
		var parentNode = node.getParentNode();
		if(parentNode) {
			if(parentNode.isLastNode) {
				return getPNNode0(parentNode);
			} else {
				return getCNode0(parentNode.getNextNode());
			}
		} else {
			return node;
		}
	}
	
	function getNextNode0(node) {
		if(node.isLastNode) {
			var parentNode = node.getParentNode();
			if(parentNode.isLastNode) {
				return getPNNode0(parentNode);
			} else {
				return getNextNode0(parentNode);
			}
		} else {
			var nextNode = node.getNextNode();
			return getCNode0(nextNode);
		}
	}
	
	function toggleExpandAccTree(obj,treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId); 
		var obj = jQuery(obj);
		var text = obj.html();
		if(text=='展开'){
			obj.html("折叠");
			zTree.expandAll(true);
		}else{
			obj.html("展开");
			var nodes = zTree.getNodes();
			zTree.expandAll(false);
			zTree.expandNode(nodes[0], true, false, true);
		}
	}
		var ztreesetting_${random}${type}acctTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag : function() {
					return false;
				},
				onClick : function(event, treeId, treeNode, clickFlag) {
					focusAcctInput();
				},
				onDblClick : function(event, treeId, treeNode, clickFlag) {
					chooseSelectNode(treeNode);
				}
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
		
		function chooseSelectNode(treeNode) {
			var isParent = treeNode.isParent;
			if(!isParent) {
				var id = treeNode.id;
				var name = treeNode.name;
				var cellId = jQuery("#${random}${type}acctTree_cellId").val();
				if(cellId) {
					jQuery("#"+cellId).val(name);
					var rowId = cellId.substr(0,cellId.indexOf("_"));
					var rowChangedData = changeData${random}${type}[rowId];
					if(rowChangedData==null){
						rowChangedData = {};
					}
					var elem = cellId.substring(cellId.indexOf("_")+1);
					rowChangedData[elem] = name;
					elem = elem.replace("_name","");
					rowChangedData[elem] = id;
					changeData${random}${type}[rowId+"_temp"] = rowChangedData;
				}
				jQuery("#${random}${type}acctTree_cellId").val("");
				$.pdialog.closeCurrentDiv("${random}${type}acctTreeDiv");
			}
		}
		
		function makeAcctTree(obj) {
			//保存input的id
			var cellId = jQuery(obj).attr("id");
			var rowId = jQuery(obj).parent().parent().attr("id");
			var rowData = jQuery("#${random}${type}pzMap_gridtable").jqGrid('getRowData',rowId);
			var nameId = cellId.replace("_name","");
			nameId = nameId.replace(rowId+"_","");
			var acctId = rowData[nameId];
			var cellValue = jQuery(obj).val();
			jQuery("#${random}${type}acctTree_cellId").val(cellId);
			var url = "makeAcctTree";
			$.get(url, {
				"_" : $.now()
			}, function(data) {
				var acctTreeData = data.acctTreeDatas;
				var acctTree = $.fn.zTree.init(
						$("#${random}${type}acctTree"),
						ztreesetting_${random}${type}acctTree, acctTreeData);
				var nodes = acctTree.getNodes();
				acctTree.expandNode(nodes[0], true, false, true);
				var snode;
				if(cellValue){
					snode = acctTree.getNodeByParam('id',acctId);
				}
				if(snode!=null){
					acctTree.selectNode(snode);
				}else{
					acctTree.selectNode(nodes[0]);
				}
			});
			jQuery("#${random}${type}acctTree_expandTree").text("展开");
			
			//使input获取焦点
			setTimeout(function() {
				focusAcctInput();
			},100);
		}
		
	</script>
	</div>
	</div>
</div>

