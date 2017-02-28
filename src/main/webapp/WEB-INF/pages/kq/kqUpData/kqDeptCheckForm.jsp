
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqDeptCheckFormGridDefine = {
		key : "kqDeptCheckForm_gridtable",
		main : {
			Build : '',
			Load : '',
		},
		event : { 
			//单元格数据修改
			"EditChanged" : function(id, p1, p2, p3, p4) {
				/*  var v = eval("("+id+")").func("GetCellData",p1+"\r\n"+p2);
				var kqIdTemp =  eval("("+id+")").func("GetCellData",p1+"\r\n kqId");
				var rowIndex = kqIdMDObj[kqIdTemp];
				if(rowIndex){
					kqMonthDatas[rowIndex][p2] = v;
					kqMonthDatas[rowIndex]["isEdit"] = '1';
					kqMonthDataCalculate(rowIndex,p1);//计算
				}  */
			},
			//单元格双击
			"DblClicked" : function(id, p1, p2, p3, p4) {
			}
		},
		callback : {
			onComplete : function(id) {
				var grid = eval("(" + id + ")");
				grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
				var kqCustomLayout = jQuery("#kqDeptCheckForm_kqCustomLayout").val();
				if(kqCustomLayout){
					//kqCustomLayout = kqDayDataColSetting(grid,kqCustomLayout);
					grid.func("setCustom", kqCustomLayout);
				}
			}
		}
	};
	supcanGridMap.put("kqDeptCheckForm_gridtable", kqDeptCheckFormGridDefine);
	var kqTypeIdDC;//考勤类别
	var kqMonthDatasDC;
	var curDeptIdDC;//当前部门
	var curPeriod;//当前期间
	var curPeriodWeek = "";//当前期间星期
	var lastPeriod;//t_monthperson取值期间
	var kqMothDataGridColumnsDC;
	jQuery(document).ready(function() {
		kqTypeIdDC = "${kqTypeId}";
		curDeptIdDC = "${curDeptId}";
		curPeriod = "${curPeriod}";//当前期间
		lastPeriod = "${lastPeriod}";//t_monthperson取值期间
		curPeriodWeek = jQuery("#kqDeptCheckForm_curPeriodWeek").val();//当前期间星期
		if(curPeriodWeek){
			curPeriodWeek = JSON.parse(curPeriodWeek);
		}
		initKqDeptCheckFormGrid();
		/*查询框初始化*/
	/* 	jQuery("#kqDeptCheckForm_personType").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent:false,
			selectParent:false,
			ifr:true,
			lazy:false
		}); */
		jQuery("#kqDeptCheckDetail_settle").click(function(){
			var url = "colShowTemplForm?entityName=com.huge.ihos.kq.kqUpData.model.KqDeptCheck";
			url += "&navTabId=kqDeptCheckForm_gridtable&oper=supcan";
			url += "&colshowType=${kqUpDataType}";
			url = encodeURI(url);
			$.pdialog.open(url, 'setColShow22', "格式", {
				ifr:true,hasSupcan:"kqDeptCheckForm_gridtable",mask:true,resizable:false,maxable:false,
				width : 500,
				height : 300
			});
			stopPropagation();
		});
	});

	function initKqDeptCheckFormGrid(reLoad) {
		jQuery.ajax({
			url: 'kqDayDataColumnInfo',
			data: {kqTypeId:kqTypeIdDC,curPeriod:curPeriod,curDeptId:curDeptIdDC,kqUpDataType:"${kqUpDataType}"},
			type : 'post',
			dataType : 'json',
			async : true,
			error : function(data) {
			},
			success : function(data) {
				var kqItems = data.kqUpItems;
				var columns ="";
	        	for(var itemIndex in kqItems){
	        		var itemCode = kqItems[itemIndex]["itemCode"];
	        		columns += "kq."+itemCode+" "+itemCode+",";
	    		}
	        	if(kqItems&&kqItems.length>0){
	        		columns = columns.substring(0,columns.length-1);
	        	}
	        	kqMothDataGridColumnsDC = columns;
				var colModelDatas = initKqDeptCheckFormColModel(kqItems);
				initKqDeptCheckFormGridScript(colModelDatas, columns,reLoad);
			}
		});
	}
	function initKqDeptCheckFormGridScript(colModelDatas, columns,reLoad) {
		var kqDeptCheckFormGrid = cloneObj(supCanTreeListGrid);
		kqDeptCheckFormGrid.Cols = colModelDatas;
		kqDeptCheckFormGrid.Properties.Title = "月考勤上报表";
		var buildStr = JSON.stringify(kqDeptCheckFormGrid);
		/*硕正配置JSON转换*/
		buildStr = parseBuildString(buildStr);
		if(reLoad){
			kqDeptCheckForm_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
			kqDeptCheckForm_gridtable.func("build", buildStr);
			kqDeptCheckForm_gridtable.func("GrayWindow",'0');
			kqDeptCheckFormGridTableload(reLoad);
		}else{
			kqDeptCheckFormGridDefine.main.Build = buildStr;
			kqDeptCheckFormGridTableload();
		}
	}
	/*列初始化*/
	function initKqDeptCheckFormColModel(data) {
		var colModelDatas = [
				{name : 'kqId',align : 'center',text : '<s:text name="kqMonthData.kqId" />',width : 80,isHide : "absHide",editable : false,dataType : 'string'},
				{name : 'status',align : 'center',text : '<s:text name="kqMonthData.status" />',width : 80,isHide : "absHide",editable : false,dataType : 'string'}, 
				{name : 'period',align : 'center',text : '<s:text name="kqMonthData.period" />',width : 80,isHide : false,editable : false,dataType : 'string',totalExpress:"合计",totalAlign:"center"},
				{name : 'maker',align : 'left',text : '<s:text name="kqMonthData.maker" />',width : 80,isHide : true,editable : false,dataType : 'string'}, 
				{name : 'makeDate',width : '80px',align : 'center',text : '<s:text name="kqMonthData.makeDate" />',isHide : true,dataType : 'date',editable : false},
				{name:'sex',align:'center',text : '<s:text name="kqMonthData.sex" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
				{name:'birthday',width:'80px',align:'center',text : '<s:text name="kqMonthData.birthday" />',isHide:"absHide",editable:false,dataType:'date'},
				{name:'duty',width:'80px',align:'left',text : '<s:text name="kqMonthData.duty" />',isHide:"absHide",editable:false,dataType:'string'},
				{name:'educationalLevel',width:'80px',align:'left',text : '<s:text name="kqMonthData.educationalLevel" />',isHide:"absHide",editable:false,dataType:'string'},
				{name:'salaryNumber',width:'80px',align:'left',text : '<s:text name="kqMonthData.salaryNumber" />',isHide:"absHide",editable:false,dataType:'string'},
				{name:'idNumber',width:'80px',align:'left',text : '<s:text name="kqMonthData.idNumber" />',isHide:"absHide",editable:false,dataType:'string'},
				{name:'jobTitle',width:'80px',align:'left',text : '<s:text name="kqMonthData.jobTitle" />',isHide:"absHide",editable:false,dataType:'string'},
				{name:'postType',width:'80px',align:'left',text : '<s:text name="kqMonthData.postType" />',isHide:"absHide",editable:false,dataType:'string'},
				{name:'ratio',width:'80px',align:'left',text : '<s:text name="kqMonthData.ratio" />',isHide:"absHide",editable:false,dataType:'double'},
				{name:'disabled',width:'80px',align:'left',text : '<s:text name="kqMonthData.disabled" />',isHide:"absHide",editable:false,dataType:'string'},
				{name:'workBegin',width:'80px',align:'left',text : '<s:text name="kqMonthData.workBegin" />',isHide:"absHide",editable:false,dataType:'date'}
		];
		var curPeriodWeekObj = {};
		if(curPeriodWeek){
			for(var index in curPeriodWeek){
				var curPeriodWeekTemp = curPeriodWeek[index];
				curPeriodWeekObj[curPeriodWeekTemp["code"]] = curPeriodWeekTemp;
			}
		}
		if(data){
			for(var index in data){
				var row = data[index];
				var frequency = row.frequency;
		 		var calculateType = row.calculateType;
		 		var kqUpDataHide = row.kqUpDataHide;
		 		var editable = calculateType=='0'?true:false;
		 		var showType = row.showType;//day,kqItem,XT
		 		editable = false;
		 		var colModelData = {  
		            name :  row.itemCode,  
		 		    text : row.itemName,
		 		    align : supCanParseAlign(row.itemType), 
		 		  	editAble:editable,
		 		    width : 80  
		 		}
		 		if(editable){
		 			colModelData.headerTextColor = "#0000FF";
		 		}
		 		//0000FF #2e6e9e
		 		if(kqUpDataHide){
		 			colModelData.isHide = true;
		 		}
		 		colModelData = supCanAddToEditOption(colModelData,row);
		 		
		 		if("day" == showType){
					var curPeriodWeekTemp = curPeriodWeekObj[row.itemCode];
					if(curPeriodWeekTemp){
		 				var groupText = curPeriodWeekTemp["name"];
		 				var isHoliday = curPeriodWeekTemp["isHoliday"];
		 				var jsonLength = getJsonLength(colModelDatas);
		 				var gropName = "group_" + jsonLength + "_group";
		 				var groupObj = {name : groupText,jsonArray : [colModelData]};
		 				if(isHoliday == "true"){
		 					groupObj["textColor"] = "#f94a52";
		 				}
		 				colModelDatas.push(groupObj);
					}
		 		}else if(frequency && "kqItem" == showType){
		 			var groupText = row.itemName;
		 			colModelData["text"] = frequency;
	 				var jsonLength = getJsonLength(colModelDatas);
	 				var gropName = "group_" + jsonLength + "_group";
	 				var groupObj = {name : groupText,jsonArray : [colModelData],id:row.itemCode+"_group"};
	 				if(editable){
	 					groupObj["textColor"] = "#0000FF";
	 				}
	 				colModelDatas.push(groupObj);
		 		}else{
		 			var jsonLength = getJsonLength(colModelDatas);
					var ColName = "Col_" + jsonLength + "_Col";
					colModelDatas.push(colModelData);
		 		}
			}
		}
		var colModelDataChecker = {
			name : "checker",
			index : "checker",
			text : "审核人",
			align : "left",
			dataType : "string",
			editAble : false,
			isHide : true,
			width : 80
		};
		var colModelDataCheckDate = {
			name : "checkDate",
			index : "checkDate",
			text : "审核日期",
			align : "center",
			dataType : "date",
			editAble : false,
			isHide : true,
			width : 80
		};
		var colModelDataSubmiter = {
			name : "submiter",
			index : "submiter",
			text : "提交人",
			align : "left",
			dataType : "string",
			editAble : false,
			isHide : true,
			width : 80
		};
		var colModelDataSubmitDate = {
			name : "submitDate",
			index : "submitDate",
			text : "提交日期",
			align : "center",
			dataType : "date",
			editAble : false,
			isHide : true,
			width : 80
		};
		var jsonLength = getJsonLength(colModelDatas);
		var colName = "Col_" + jsonLength + "_Col";
		colModelDatas.push(colModelDataChecker);
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas.push(colModelDataCheckDate);
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas.push(colModelDataSubmiter);
		jsonLength = getJsonLength(colModelDatas);
		colName = "Col_" + jsonLength + "_Col";
		colModelDatas.push(colModelDataSubmitDate);
		return colModelDatas;
	}
	function kqDeptCheckFormGridTableload(reLoad) {
		jQuery.ajax({
			url : 'kqDayDataGridList',
			data : {columns:kqMothDataGridColumnsDC,kqTypeId:kqTypeIdDC,curDeptId:curDeptIdDC,
				curPeriod:curPeriod,lastPeriod:lastPeriod},
			type : 'post',
			dataType : 'json',
			async : true,
			error : function(data) {
			},
			success : function(data) {
				kqMonthDatasDC = data.kqDayDataSets;
				var kqMonthDataGridData = {};
				kqMonthDataGridData.Record = kqMonthDatasDC;
				if(reLoad){
					kqDeptCheckForm_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					kqDeptCheckForm_gridtable.func("load", JSON.stringify(kqMonthDataGridData));
					kqDeptCheckForm_gridtable.func("GrayWindow",'0');
					kqDeptCheckFormSearchFormReLoad();
				}else{
					kqDeptCheckFormGridDefine.main.Load = JSON.stringify(kqMonthDataGridData);
					insertTreeListToDiv("kqDeptCheckForm_gridtable_div","kqDeptCheckForm_gridtable", "", "100%");	
				}
			}
		});
	}
	//查询
	function kqDeptCheckFormSearchFormReLoad(){
			var personName = jQuery("#kqDeptCheckForm_personName").val();
			var filterStr = "1==1 ";
			if(personName){
				if(personName.indexOf("*") == -1){
					filterStr += " and personName == '"+personName+"' ";
				}else{
					personName = personName.replaceAll("\\*","");
					filterStr += " and indexOf(personName, '"+personName+"')>=0 ";
				}
			}
			filterStr = filterStr.trim();
			kqDeptCheckForm_gridtable.func("Filter", filterStr);
		
	}
	</script>

<div class="page">
	<div id="kqDeptCheckForm_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
			<input type="hidden" id="kqDeptCheckForm_curPeriodWeek" value='<s:property value="curPeriodWeek" escapeHtml="false"/>'>
				<form id="kqDeptCheckForm_search_form">
					<label style="float:none;white-space:nowrap" >
       					<s:text name='kqMonthData.personName'/>:
      					<input type="text" id="kqDeptCheckForm_personName" name="personName" style="width:120px"/>
					 </label>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button"
								onclick="kqDeptCheckFormSearchFormReLoad()">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="kqDayData_toolbuttonbar">
			<li>
				<a id="kqDeptCheckDetail_settle" class="settlebutton" ><span>格式</span></a>
			</li>
			</ul>
		</div>
		<input type="hidden" id="kqDeptCheckForm_kqCustomLayout" value='<s:property value="kqCustomLayout" escapeHtml="false"/>'>
		<div id="kqDeptCheckForm_gridtable_div" layoutH="65" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<div id="load_kqDeptCheckForm_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
		</div>
	</div>
</div>