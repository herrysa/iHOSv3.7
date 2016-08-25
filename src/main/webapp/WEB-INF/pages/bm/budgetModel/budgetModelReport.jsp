<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var budgetReportDefine = {
		key:"${random}_budgetReport_gridtable",
		main:{
			SetSource : 'getBmReportDataSourceXml?modelId=${modelId}',
			//Build : '${ctx}/home/supcan/userdefine/blank.xml',
			Build : 'getBmReportXml?modelId=${modelId}',
			Load :''
		},
		event:{
			"Load":function( id,p1, p2, p3, p4){
			},
			"Opened":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				grid.func("AddUserFunctions", "getBmReportFunctionXml");
				grid.func("SetAutoCalc","0");
				grid.func("CallFunc","2");
				grid.func("SetBatchFunctionURL","batchFunc \r\n functions=10000;timeout=9999 \r\n user=normal");
				
				resetGridStatus(id);
			},
			"EditChanged":function( id,p1, p2, p3, p4){
				var grid = eval("(budgetReport_gridtable_${random})");
				var deptCol = grid.func("GetMemo"," \r\n deptCol");
				if(deptCol.indexOf("r_")!=-1){
					var row = deptCol.replace("r_","");
					if(row==p1){
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n Arrow \r\n blue");
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n hasArrow \r\n 1");
					}
				}else if(deptCol.indexOf("c_")!=-1){
					var col = deptCol.replace("c_","");
					if(col==p2){
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n Arrow \r\n blue");
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n hasArrow \r\n 1");
					}
				}
				var indexCode = grid.func("GetMemo"," \r\n indexCol");
				if(indexCode.indexOf("r_")!=-1){
					var row = indexCode.replace("r_","");
					if(row==p1){
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n Arrow \r\n red");
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n hasArrow \r\n 1");
					}
				}else if(indexCode.indexOf("c_")!=-1){
					var col = indexCode.replace("c_","");
					if(col==p2){
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n Arrow \r\n red");
						grid.func("SetCellProp",p1+" \r\n "+p2+" \r\n hasArrow \r\n 1");
					}
				}
			},
			"Calced" :function( id,p1, p2, p3, p4){
				var grid = eval("(budgetReport_gridtable_${random})");
				var deptCol = grid.func("GetMemo"," \r\n deptCol");
				if(deptCol){
					grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
					if(deptCol.indexOf("r_")!=-1){
						deptCol = deptCol.replace("r_","");
	 					var cols = grid.func("GetCols","");
	 					for(var c = 0; c<cols ;c++){
	 						var cellText = grid.func("GetCellText",deptCol+" \r\n "+c);
	 						if(cellText){
	 							grid.func("SetCellProp",deptCol+" \r\n "+c+" \r\n Arrow \r\n blue");
	 							grid.func("SetCellProp",deptCol+" \r\n "+c+" \r\n hasArrow \r\n 2");
	 						}
	 					}
	 				}else if(deptCol.indexOf("c_")!=-1){
	 					deptCol = deptCol.replace("c_","");
	 					var rows = grid.func("GetRows","");
	 					for(var r = 0; r<rows ;r++){
	 						var cellText = grid.func("GetCellText",r+" \r\n "+deptCol);
	 						if(cellText){
		 						grid.func("SetCellProp",r+" \r\n "+deptCol+" \r\n Arrow \r\n blue");
	 							grid.func("SetCellProp",r+" \r\n "+deptCol+" \r\n hasArrow \r\n 2");
	 						}
	 					}
	 				}
					grid.func("GrayWindow",'0');//遮罩/还原的动作
				}
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var reportXml = grid.func("GetFileXML", "");
					$.ajax({
			            url: 'saveBmReportXml',
			            type: 'post',
			            dataType: 'json',
			            data :{modelId:${modelId},reportXml:reportXml},
			            async:false,
			            error: function(data){
			            alertMsg.error("系统错误！");
			            },
			            success: function(data){
			            	formCallBack(data);
			            }
			        });
					grid.func("CancelEvent", "");
				}
			}
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				
				grid.func("SetItemLibraryURL", "getBmReportIndexXml");
				grid.func("SubscribeEvent", "EditChanged");
				
				/* var deptCol = grid.func("GetMemo"," \r\n deptCol");
				var indexCode = grid.func("GetMemo"," \r\n indexCol");
				var dataCellStr = grid.func("GetMemo"," \r\n dataCell");
	 			var dataCell = {};
	 			if(dataCellStr){
	 				dataCell = eval("("+dataCellStr+")");
	 			} */
				/* var taborderStr = grid.func("GetMemo"," \r\n taborder");
	 			if(taborderStr){
	 				var taborder = eval("("+taborderStr+")");
	 				for(var i in taborder){
	 					var tab = taborder[i];
	 					var tabArr = i.split("");
	 					grid.func("SetCellProp",tabArr[0]+" \r\n "+tabArr[1]+" \r\n taborder \r\n "+tab);
	 				}
	 			} */
			}
		}
	}; 
	
    supcanGridMap.put('budgetReport_gridtable_${random}',budgetReportDefine); 
 	jQuery(document).ready(function(){
 		//reportPlanDefine.main.Build = initreportColModel();
 		//alert(reportPlanDefine.main.Build);
 		var bmReportFullSize = jQuery("#${random}_bmReportContent").innerHeight()-28;
 		jQuery("#${random}_budgetReport_container").css("height",bmReportFullSize);
		var bmReportLayout = $('#${random}_budgetReport_container').layout({ 
			applyDefaultStyles: false ,
			east:{
				size : 290,
				initClosed:true
			},
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",
			onresize_end : function(paneName,element,state,options,layoutName){
				if("center" == paneName){
					//gridResize(null,null,gridtable,"single");
				}
			}
		});
 		tabResize();
 		
 		//uploadDesigntime、uploadRuntime
 		insertReportToDiv("${random}_budgetReport_gridtable_container","budgetReport_gridtable_${random}","workmode=uploadDesigntime","100%");
 		
 		var grid = eval("(budgetReport_gridtable_${random})");
 		var ztreesetting_bmReport = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : selectIndexToCell
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
 		var cellIndexMap = {};
 		function selectIndexToCell(event, treeId, treeNode, clickFlag) { 
			var grid = eval("(budgetReport_gridtable_${random})");
			var subIndexs = treeNode.children;
			if(subIndexs!=null&&subIndexs.length!=0){
				for(var i=0;i<subIndexs.length;i++){
					var childNode = subIndexs[i];
					var cell = grid.func("GetCurrentCell",""+i);
					grid.func("SetCellData",cell+" \r\n{index."+childNode.name+"}");
					cellIndexMap[cell] = childNode.id;
				}
			}else{
				var cell = grid.func("GetCurrentCell",""+0);
				grid.func("SetCellData",cell+" \r\n{index."+treeNode.name+"}");
				cellIndexMap[cell] = treeNode.id;
			}
			
 		}
 		$.get("getBudgetIndexTree", {
			"_" : $.now()
		}, function(data) {
			var budgetIndexTreeData = data.budgetIndexTreeNodes;
			var budgetIndexTree = $.fn.zTree.init($("#bmReportIndexTree"),
					ztreesetting_bmReport, budgetIndexTreeData);
			var nodes = budgetIndexTree.getNodes();
			budgetIndexTree.expandNode(nodes[0], true, false, true);
			budgetIndexTree.selectNode(nodes[0]);
			
		});
 		setTimeout(function(){
 		//grid.func("build",reportDefineReportXml);
 			//jQuery("#${random}_budgetReport_gridtable_container").css("overflow","hidden");
 		},200);
 		
 		jQuery("#${random}_editBudgetModelReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 	 		grid.func("SetAutoCalc","0");
 			grid.func("CallFunc","2");
 			grid.func("CallFunc","143");
 		});
 		var cellVarMap = {};
 		jQuery("#${random}_showBudgetModelReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var rows = grid.func("GetRows");
 			var cols = grid.func("GetCols");
 			for(var row=0;row<rows;row++){
 				for(var col=0;col<cols;col++){
 					//var cell = grid.func("GetCellName",row+"\r\n"+col);
 					var cellData = grid.func("getCellData",row+"\r\n"+col);
 					if(cellData.indexOf("{VAR.")!=-1){
 						var bmrSysVarTree = $.fn.zTree.getZTreeObj("${random}_bmrSysVarTree");
 						var pattern = new RegExp("\\{(.| )+?\\}","igm");
 						var matchStrArr = cellData.match(pattern);
 						for(var strIndex=0;strIndex<matchStrArr.length;strIndex++){
 							var matchStr = matchStrArr[strIndex];
 							var s = matchStr.replace("{VAR.","");
 							s = s.replace("}","");
 							var sysVarNode = bmrSysVarTree.getNodeByParam("name",s,null);
 							var vv = sysVarNode.value;
 							cellData = cellData.replace(matchStr,vv);
 						}
						grid.func("SetCellData",row+"\r\n"+col+"\r\n"+cellData);
 						
 					}
					if(cellData.indexOf("{INDEX.")!=-1){
						grid.func("SetCellData",row+"\r\n"+col+"\r\n");
 					}
 				}
 			}
 			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
			setTimeout(function(){
				
			},200);
			
 		});
 		jQuery("#${random}_selectIndexToCell").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			grid.func("GrayWindow",'1 \r\n 255');
 		});
 		jQuery("#${random}_saveBudgetModelReport").click(function(){
 			var reportXml = getBmModelXml("budgetReport_gridtable_${random}");
 			var cellIndexStr = JSON.stringify(cellIndexMap);
			$.ajax({
	            url: 'saveBmReportXml',
	            type: 'post',
	            dataType: 'json',
	            data :{modelId:"${modelId}",cellIndex:cellIndexStr,reportXml:reportXml},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	setTimeout(function(){
		            	formCallBack(data);
            		},200);
	            }
	        });
 		});
 		jQuery("#${random}_submitReport").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var sss = grid.func("GetUploadXML");
 			console.log(sss);
 		});
 		jQuery("#${random}_setDeptCol").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var currentCell = grid.func("GetCurrentCells","");
 			var currentCellArr = currentCell.split(":");
 			if(currentCellArr[0]==currentCellArr[1]){
 				alertMsg.error("请选择两个单元格以上！");
 				return ;
 			}
 			var rowBegin = grid.func("GetCellRow", currentCellArr[0]);
 			var colBegin = grid.func("GetCellCol", currentCellArr[0]);
 			var rowEnd = grid.func("GetCellRow", currentCellArr[1]);
 			var colEnd = grid.func("GetCellCol", currentCellArr[1]);
 			var srow = 0,scol = 0;
 			if(colBegin==colEnd){
 				scol = 1;
 			}
 			if(rowBegin==rowEnd){
 				srow = 1;
 			}
 			if(srow==1&&scol==1){
 				alertMsg.error("请唯一行或列！");
 				return ;
 			}else{
 				var oldDeptCol = grid.func("GetMemo"," \r\n deptCol");
 				grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
 				var oldDeptCell = grid.func("FindCell", "hasArrow=2"); 
 				var oldDeptCellArr = oldDeptCell.split(",");
 				for(var ci=0;ci<oldDeptCellArr.length;ci++){
 					var cell = oldDeptCellArr[ci];
 					grid.func("SetCellProp",cell+" \r\n Arrow \r\n ");
 					grid.func("SetCellProp",cell+" \r\n hasArrow \r\n ");
 				}
 				if(srow==1){
 					grid.func("SetMemo"," \r\n deptCol \r\n r_"+rowBegin);
 					var cols = grid.func("GetCols","");
 					for(var c = 0; c<cols ;c++){
 						var cellText = grid.func("GetCellText",rowBegin+" \r\n "+c);
 						if(cellText){
 							grid.func("SetCellProp",rowBegin+" \r\n "+c+" \r\n Arrow \r\n blue");
 							grid.func("SetCellProp",rowBegin+" \r\n "+c+" \r\n hasArrow \r\n 2");
 						}
 					}
 				}else if(scol==1){
 					grid.func("SetMemo"," \r\n deptCol \r\n c_"+colBegin);
 					var rows = grid.func("GetRows","");
 					for(var r = 0; r<rows ;r++){
 						var cellText = grid.func("GetCellText",r+" \r\n "+colBegin);
 						if(cellText){
	 						grid.func("SetCellProp",r+" \r\n "+colBegin+" \r\n Arrow \r\n blue");
 							grid.func("SetCellProp",r+" \r\n "+colBegin+" \r\n hasArrow \r\n 2");
 						}
 					}
 				}
				grid.func("GrayWindow","0");//遮罩/还原的动作
 			}
 			
 		});
 		jQuery("#${random}_setIndexCol").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var currentCell = grid.func("GetCurrentCells","");
 			var currentCellArr = currentCell.split(":");
 			if(currentCellArr[0]==currentCellArr[1]){
 				alertMsg.error("请选择两个单元格以上！");
 				return ;
 			}
 			var rowBegin = grid.func("GetCellRow", currentCellArr[0]);
 			var colBegin = grid.func("GetCellCol", currentCellArr[0]);
 			var rowEnd = grid.func("GetCellRow", currentCellArr[1]);
 			var colEnd = grid.func("GetCellCol", currentCellArr[1]);
 			var srow = 0,scol = 0;
 			if(colBegin==colEnd){
 				scol = 1;
 			}
 			if(rowBegin==rowEnd){
 				srow = 1;
 			}
 			if(srow==1&&scol==1){
 				alertMsg.error("请唯一行或列！");
 				return ;
 			}else{
 				var oldIndexCode = grid.func("GetMemo"," \r\n indexCol");
 				grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
 				var oldIndexCell = grid.func("FindCell", "hasArrow=1"); 
 				var oldIndexCellArr = oldIndexCell.split(",");
 				for(var ci=0;ci<oldIndexCellArr.length;ci++){
 					var cell = oldIndexCellArr[ci];
 					grid.func("SetCellProp",cell+" \r\n Arrow \r\n ");
 					grid.func("SetCellProp",cell+" \r\n hasArrow \r\n ");
 				}
 				if(srow==1){
 					grid.func("SetMemo"," \r\n indexCol \r\n r_"+rowBegin);
 					var cols = grid.func("GetCols","");
 					for(var c = 0; c<cols ;c++){
 						var cellText = grid.func("GetCellText",rowBegin+" \r\n "+c);
 						if(cellText){
	 						grid.func("SetCellProp",rowBegin+" \r\n "+c+" \r\n Arrow \r\n red");
	 						grid.func("SetCellProp",rowBegin+" \r\n "+c+" \r\n hasArrow \r\n 1");
 						}
 					}
 				}else if(scol==1){
 					grid.func("SetMemo"," \r\n indexCol \r\n c_"+colBegin);
 					var rows = grid.func("GetRows","");
 					for(var r = 0; r<rows ;r++){
 						var cellText = grid.func("GetCellText",r+" \r\n "+colBegin);
 						if(cellText){
 							grid.func("SetCellProp",r+" \r\n "+colBegin+" \r\n Arrow \r\n red");
 							grid.func("SetCellProp",r+" \r\n "+colBegin+" \r\n hasArrow \r\n 1");
	 					}
 					}
 				}
				grid.func("GrayWindow","0");//遮罩/还原的动作
 			}
 			
 		});
 		jQuery("#${random}_setDataCell").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var currentCell = grid.func("GetCurrentCells","");
 			var currentCellArr = currentCell.split(":");
 			var deptCol = grid.func("GetMemo"," \r\n deptCol");
 			var indexCol = grid.func("GetMemo"," \r\n indexCol");
 			var cell1 = currentCellArr[0],cell2 = currentCellArr[1];
 			var rowBegin = grid.func("GetCellRow", cell1);
 			var colBegin = grid.func("GetCellCol", cell1);
 			var rowEnd = grid.func("GetCellRow", cell2);
 			var colEnd = grid.func("GetCellCol", cell2);
 			var dataCellStr = grid.func("GetMemo"," \r\n dataCell");
 			var dataCell = {};
 			if(dataCellStr){
 				dataCell = eval("("+dataCellStr+")");
 			}
 			grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
 			for(var i=rowBegin;i<=rowEnd;i++){
 				for(var j=colBegin;j<=colEnd;j++){
					var deptId = "" , indexCode = "";
					if(deptCol.indexOf("r_")!=-1){
						var deptColTemp = deptCol.replace("r_","");
						deptId = grid.func("GetCellData",deptColTemp+" \r\n "+j);
					}else if(deptCol.indexOf("c_")!=-1){
						var deptColTemp = deptCol.replace("c_","");
						deptId = grid.func("GetCellData",i+" \r\n "+deptColTemp);
					}
					if(indexCol.indexOf("r_")!=-1){
						var indexColTemp = indexCol.replace("r_","");
						//indexCode = grid.func("GetCellData",indexCol+" \r\n "+col);
						indexCode = grid.func("GetCellProp",indexColTemp+" \r\n "+j+" \r\n Alias");
					}else if(indexCol.indexOf("c_")!=-1){
						var indexColTemp = indexCol.replace("c_","");
						//indexCode = grid.func("SetCellProp",indexCol+" \r\n "+col+" \r\n Alias");
						indexCode = grid.func("GetCellProp",i+" \r\n "+indexColTemp+" \r\n Alias");
					}	
					grid.func("SetCellProp",i+" \r\n "+j+" \r\n Alias \r\n "+deptId+"@"+indexCode);
					grid.func("SetCellProp",i+" \r\n "+j+" \r\n isProtected  \r\n 1");
 					//grid.func("SetCellProp",row+" \r\n "+col+" \r\n "+Alias+" \r\n "+);
					dataCell[i+""+j] = deptId+"@"+indexCode;
 				}
 			}
 			grid.func("GrayWindow",'0');//遮罩/还原的动作
 			grid.func("SetMemo"," \r\n dataCell \r\n "+JSON.stringify(dataCell));
 		});
 		jQuery("#${random}_cancelDataCell").click(function(){
 			var grid = eval("(budgetReport_gridtable_${random})");
 			var currentCell = grid.func("GetCurrentCells","");
 			var currentCellArr = currentCell.split(":");
 			var cell1 = currentCellArr[0],cell2 = currentCellArr[1];
 			var rowBegin = grid.func("GetCellRow", cell1);
 			var colBegin = grid.func("GetCellCol", cell1);
 			var rowEnd = grid.func("GetCellRow", cell2);
 			var colEnd = grid.func("GetCellCol", cell2);
 			var dataCellStr = grid.func("GetMemo"," \r\n dataCell");
 			var dataCell = {};
 			if(dataCellStr){
 				dataCell = eval("("+dataCellStr+")");
 			}
 			grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
 			for(var i=rowBegin;i<=rowEnd;i++){
 				for(var j=colBegin;j<=colEnd;j++){
 					grid.func("SetCellProp",i+" \r\n "+j+" \r\n Alias \r\n ");
 					grid.func("SetCellProp",i+" \r\n "+j+" \r\n isProtected  \r\n 0");
 					delete dataCell[i+""+j];
 				}
 			}
 			grid.func("GrayWindow",'0');//遮罩/还原的动作
 			grid.func("SetMemo"," \r\n dataCell \r\n "+JSON.stringify(dataCell));
 		});
 	});
 	
 	function getBmModelXml(id){
		var grid = eval("("+id+")");
		grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
 		var cells = grid.func("FindCell", "left(formula,8)='=datarow'"); 
		var cellArr = cells.split(",");
		for(var i in cellArr){
			var cell = cellArr[i];
			var cellTxt = grid.func("GetCellText", cell+"");
			grid.func("SetCellData", cell+"\r\n");
			grid.func("SetCellData", cell+"\r\n"+cellTxt);
		}
		var rows = grid.func("GetRows","");
		for(var row=0;row<rows;row++){
			var isds = grid.func("GetRowProp",row+"\r\nds");
			if(isds==1){
				grid.func("SetRowProp",row+"\r\nds\r\n0");
			}
		}
		var deptCol = grid.func("GetMemo"," \r\n deptCol");
		var indexCode = grid.func("GetMemo"," \r\n indexCol");
		var dataCellStr = grid.func("GetMemo"," \r\n dataCell");
		var dataCell = {};
		if(dataCellStr){
			dataCell = eval("("+dataCellStr+")");
		} 
		var oldDeptCell = grid.func("FindCell", "hasArrow=2"); 
		var oldDeptCellArr = oldDeptCell.split(",");
		for(var ci=0;ci<oldDeptCellArr.length;ci++){
			var cell = oldDeptCellArr[ci];
			grid.func("SetCellProp",cell+" \r\n Arrow \r\n ");
		}
		var oldIndexCell = grid.func("FindCell", "hasArrow=1"); 
		var oldIndexCellArr = oldIndexCell.split(",");
		for(var ci=0;ci<oldIndexCellArr.length;ci++){
			var cell = oldIndexCellArr[ci];
			grid.func("SetCellProp",cell+" \r\n Arrow \r\n ");
		}
		for(var i in dataCell){
			var alias = dataCell[i];
			var ij = i.split("");
			grid.func("SetCellProp",ij[0]+" \r\n "+ij[1]+" \r\n Alias \r\n "+alias);
			grid.func("SetCellProp",ij[0]+" \r\n "+ij[1]+" \r\n isProtected \r\n");
		}
		var reportXml = grid.func("GetFileXML", "isSaveCalculateResult=true"); 
		var deptCol = grid.func("GetMemo"," \r\n deptCol");
		var indexCode = grid.func("GetMemo"," \r\n indexCol");
		var dataCellStr = grid.func("GetMemo"," \r\n dataCell");
			var dataCell = {};
			if(dataCellStr){
				dataCell = eval("("+dataCellStr+")");
			} 
			var oldDeptCell = grid.func("FindCell", "hasArrow=2"); 
		var oldDeptCellArr = oldDeptCell.split(",");
		for(var ci=0;ci<oldDeptCellArr.length;ci++){
			var cell = oldDeptCellArr[ci];
			grid.func("SetCellProp",cell+" \r\n Arrow \r\n blue");
		}
		var oldIndexCell = grid.func("FindCell", "hasArrow=1"); 
		var oldIndexCellArr = oldIndexCell.split(",");
		for(var ci=0;ci<oldIndexCellArr.length;ci++){
			var cell = oldIndexCellArr[ci];
			grid.func("SetCellProp",cell+" \r\n Arrow \r\n red");
		}
		for(var i in dataCell){
			var alias = dataCell[i];
			var ij = i.split("");
			grid.func("SetCellProp",ij[0]+" \r\n "+ij[1]+" \r\n isProtected \r\n 1");
		}
		grid.func("GrayWindow",'0');//遮罩/还原的动作
		return reportXml;
 	}
 	
 	function resetGridStatus(id){
		var grid = eval("("+id+")");
		grid.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
		var deptCol = grid.func("GetMemo"," \r\n deptCol");
		var indexCode = grid.func("GetMemo"," \r\n indexCol");
		var dataCellStr = grid.func("GetMemo"," \r\n dataCell");
			var dataCell = {};
			if(dataCellStr){
				dataCell = eval("("+dataCellStr+")");
			} 
			var oldDeptCell = grid.func("FindCell", "hasArrow=2"); 
		var oldDeptCellArr = oldDeptCell.split(",");
		for(var ci=0;ci<oldDeptCellArr.length;ci++){
			var cell = oldDeptCellArr[ci];
			grid.func("SetCellProp",cell+" \r\n Arrow \r\n blue");
		}
		var oldIndexCell = grid.func("FindCell", "hasArrow=1"); 
		var oldIndexCellArr = oldIndexCell.split(",");
		for(var ci=0;ci<oldIndexCellArr.length;ci++){
			var cell = oldIndexCellArr[ci];
			grid.func("SetCellProp",cell+" \r\n Arrow \r\n red");
		}
		for(var i in dataCell){
			var alias = dataCell[i];
			var ij = i.split("");
			grid.func("SetCellProp",ij[0]+" \r\n "+ij[1]+" \r\n isProtected \r\n 1");
		}
		grid.func("GrayWindow",'0');//遮罩/还原的动作
	}
 	function sourcepayinSum1(checkperiod1,checkperiod2,deptId,chargeType){
 		//return checkperiod1;
 		console.log(chargeType);
 		var sum;
 		var sql = "select sum(amount) from v_sourcepayin where checkPeriod BETWEEN '"+checkperiod1+"' and '"+checkperiod2+"' and kdDeptId='"+deptId+"'";
 		$.ajax({
            url: 'getBySql?sql='+sql,
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            alertMsg.error("系统错误！");
            },
            success: function(data){
            	console.log(data.sqlResult);
            	sum = data.sqlResult;
            }
        });
 		
 		return sum;
 	}
 	
 	function sv(str){
 		var sysVarStr = '${fns:getAllVariableStr()}';
 		var sysVarJson = eval("("+sysVarStr+")");
 		var varIndex = 0;
 		for(var vName in sysVarJson){
 			var vValue = sysVarJson[vName];
 			str = str.replace(vName,vValue);
 		}
 		return str;
 	}
 </script>
 <div class="page" style="height: 100%;">
	<div id="${random}_bmReportContent" class="pageContent" style="height: 100%;">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="${random}_editBudgetModelReport" class="changebutton"  href="javaScript:"
					><span>编辑模板
					</span>
				</a>
				</li>
				<li><a id="${random}_showBudgetModelReport" class="previewbutton"  href="javaScript:"
					><span>预览
					</span>
				</a>
				</li>
				<li><a id="${random}_setDeptCol" class="settlebutton"  href="javaScript:"
					><span>设为部门行/列
					</span>
				</a>
				</li>
				<li><a id="${random}_setIndexCol" class="settlebutton"  href="javaScript:"
					><span>设为指标行/列
					</span>
				</a>
				</li>
				<li><a id="${random}_setDataCell" class="editbutton"  href="javaScript:"
					><span>设为数据项
					</span>
				</a>
				</li>
				<li><a id="${random}_cancelDataCell" class="canceleditbutton"  href="javaScript:"
					><span>取消数据项
					</span>
				</a>
				</li>
				<li><a id="${random}_saveBudgetModelReport" class="savebutton"  href="javaScript:"
					><span>保存
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="${random}_budgetReport_container">
			<div id="${random}_budgetReport_layout-center" class="pane ui-layout-center" style="display: block; overflow: hidden;">
				<div id="${random}_budgetReport_gridtable_container" style="height:100%;overflow: hidden;"></div>
			</div>
			<div id="${random}_budgetReport_layout-east" class="pane ui-layout-east" style="padding:0px;display: block; overflow: hidden;">
				
				<div class="tabs" currentIndex="0" eventType="click" id="${random}_bmRightTabs" tabcontainer="${random}_budgetReport_layout-east" extraHeight=0 extraWidth=10>
						<div class="tabsHeader">
							<div class="tabsHeaderContent">
								<ul>
									<li><a href="javascript:;"><span>系统变量</span> </a></li>
									<li><a href="javascript:;"><span>预算指标</span> </a></li>
								</ul>
							</div>
						</div>
						<div id="${random}_bmRightTabsContent" class="tabsContent"
							style="height: 250px;">
							<div id="${random}_systemVarTree" style="padding:5px">
								<%-- <a style="position: relative; float: right;top:5px" id="${random}_bmReportSysVar_expandTree" href="javaScript:">展开</a> --%>
								<div style="marigin:10px">查询：<input /></div>
								<script>
									/* jQuery("#${random}_bmReportSysVar_expandTree").click(function(){
										var thisText = jQuery(this).text();
										var budgetSysVarTee = $.fn.zTree.getZTreeObj("${random}_bmrSysVarTree");
										if(thisText=="展开"){
											budgetSysVarTee.expandAll(true);
											jQuery(this).text("折叠");
										}else{
											budgetSysVarTee.expandAll(false);
											jQuery(this).text("展开");
										}
									}); */
									var setting_bmSysVar = {
											view : {
												dblClickExpand : false,
												showLine : true,
												selectedMulti : false
											},
											callback : {
												onClick : selectVarToCell
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
							 		function selectVarToCell(event, treeId, treeNode, clickFlag) { 
										var grid = eval("(budgetReport_gridtable_${random})");
										var subIndexs = treeNode.children;
										if(subIndexs!=null&&subIndexs.length!=0){
											for(var i=0;i<subIndexs.length;i++){
												var childNode = subIndexs[i];
												var cell = grid.func("GetCurrentCell",""+i);
												grid.func("SetCellData",cell+" \r\n{VAR."+childNode.name+"}");
											}
										}else{
											var cell = grid.func("GetCurrentCell",""+0);
											grid.func("SetCellData",cell+" \r\n{VAR."+treeNode.name+"}");
										}
										
							 		}
							 		var bmrSysvarTree = $.fn.zTree.init($("#${random}_bmrSysVarTree"),setting_bmSysVar);
							 		var sysVarStr = '${fns:getAllVariableStr()}';
							 		var sysVarJson = eval("("+sysVarStr+")");
							 		var varIndex = 0;
							 		for(var vName in sysVarJson){
							 			var vValue = sysVarJson[vName];
							 			var svNode = {id:varIndex,name:vName,value:vValue};
							 			bmrSysvarTree.addNodes(null, svNode);
							 		}
							 		/* $.get("getSystemTree", {
										"_" : $.now()
									}, function(data) {
										var budgetIndexTreeData = data.budgetIndexTreeNodes;
										var budgetIndexTree = $.fn.zTree.init($("#bmReportIndexTree"),
												ztreesetting_bmReport, budgetIndexTreeData);
										var nodes = budgetIndexTree.getNodes();
										budgetIndexTree.expandNode(nodes[0], true, false, true);
										budgetIndexTree.selectNode(nodes[0]);
										
									}); */
								</script>
								<div id="${random}_bmrSysVarTree" class="ztree"></div>
							</div>
							<div id="${random}_bmIndexTree">
								<a style="position: relative; float: right;top:5px" id="bmReportIndex_expandTree" href="javaScript:">展开</a>
								<script>
									jQuery("#bmReportIndex_expandTree").click(function(){
										var thisText = jQuery(this).text();
										var budgetIndexTee = $.fn.zTree.getZTreeObj("bmReportIndexTree");
										if(thisText=="展开"){
											budgetIndexTee.expandAll(true);
											jQuery(this).text("折叠");
										}else{
											budgetIndexTee.expandAll(false);
											jQuery(this).text("展开");
										}
									});
								</script>
								<div id="bmReportIndexTree" class="ztree"></div>
							</div>
						</div>
						<div class="tabsFooter">
							<div class="tabsFooterContent"></div>
						</div>
					</div>
			</div>
		</div>
	
	</div> 
 </div>