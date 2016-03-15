function toggleDisabledOrCount(option){
	var treeId = option.treeId
		,showCode = option.showCode
		,disabledDept = option.disabledDept
		,disabledPerson = option.disabledPerson
		,showCount = option.showCount
		,showIds = option.showIds
		,addFilters = option.addFilters;
	if(!treeId){
		return ;
	}
	if(!showCode){
		showCode = false;
	}else if(typeof showCode =='object'){
		showCode = showCode.checked?true:false;
	}
	if(!disabledDept){
		disabledDept = false;
	}else if(typeof disabledDept =='object'){
		disabledDept = disabledDept.checked?true:false;
	}
	if(!disabledPerson){
		disabledPerson = false;
	}else if(typeof disabledPerson =='object'){
		disabledPerson = disabledPerson.checked?true:false;
	}
	if(!showCount){
		showCount = false;
	}else if(typeof showCount =='object'){
		showCount = showCount.checked?true:false;
	}
	if(!addFilters){
		addFilters = false;
	}
	var hrDeptTreeObj = $.fn.zTree.getZTreeObj(treeId);
	var selectedNodes , selectedNodeId;
	if(!hrDeptTreeObj){
		return ;
	}
	selectedNodes = hrDeptTreeObj.getSelectedNodes();
	if(selectedNodes){
		var selectedNode = selectedNodes[0];
		if(selectedNode){
			selectedNodeId = selectedNode.id
		}
	}
	if(hrDeptTreeObj){
		var nodes = hrDeptTreeObj.transformToArray(hrDeptTreeObj.getNodes());
		for(index in nodes){
			var node = nodes[index];
			var subSysTem = node.subSysTem , actionUrl = node.actionUrl;
			// 处理编码显示
			if(subSysTem!="ALL"){
				node.name = node.nameWithoutPerson;
				if(showCode==true){
					if(node.code){
						node.name = node.code+' '+node.nameWithoutPerson;
					}
				}
			}
			// 处理人数显示
			if(showCount==true&&(subSysTem=='ORG'||subSysTem=='DEPT')){
				if(disabledDept==true){
					if(disabledPerson==true){
						node.name = node.name+"("+node.personCount+")";
					}else{
						node.name = node.name+"("+node.personCountP+")";
					}
				}else{
					if(disabledPerson==true){
						node.name = node.name+"("+node.personCountD+")";
					}else{
						node.name = node.name+"("+node.personCountDP+")";
					}
				}
			}
			// 处理停用node
			if(addFilters!=true&&(!showIds||showIds=='init'||showIds=='all')){
				if(actionUrl == '1'){
					if(subSysTem=='DEPT'){
						if(disabledDept==true){
							hrDeptTreeObj.showNode(node);
						}else{
							hrDeptTreeObj.hideNode(node);
						}
					}else if(subSysTem=='PERSON'){
						if(disabledPerson==true){
							hrDeptTreeObj.showNode(node);
						}else{
							hrDeptTreeObj.hideNode(node);
						}
					}
				}
			}
			/*if(showIds&&showIds!='all'&&subSysTem!="ALL"){
				hrDeptTreeObj.hideNode(node);
			}*/
			hrDeptTreeObj.updateNode(node);
		}
		if(showIds=='init'){
			setTimeout(function(){
				var initNodes = hrDeptTreeObj.getNodesByParam("isHidden", false, null);
				//initNodes = hrDeptTreeObj.transformToArray(initNodes);
				jQuery("#"+treeId).data('initNodes',initNodes);
			},100);
		}else if(showIds=='all'){
			var filtered = jQuery("#"+treeId).data('filtered');
			if(filtered=='true'){
				var initNodes = jQuery("#"+treeId).data('initNodes');
				hrDeptTreeObj.showNodes(initNodes);
			}
		}else if(addFilters==true&&showIds=='null'){
			var needHideNodes = hrDeptTreeObj.getNodesByParam("isHidden", false, null);
			hrDeptTreeObj.hideNodes(needHideNodes);
			var node = hrDeptTreeObj.getNodeByParam("subSysTem", "ALL", null);
			hrDeptTreeObj.showNode(node);
			jQuery("#"+treeId).data('filtered','true');
		}else if(addFilters==true&&showIds){
			jQuery("#"+treeId).data('filtered','true');
			var needHideNodes = hrDeptTreeObj.getNodesByParam("isHidden", false, null);
			hrDeptTreeObj.hideNodes(needHideNodes);
			var snIndex = 0 ,sn = 0 ,zeroNumStr = "" ;
			while(snIndex<showIds.length){
				if(showIds[snIndex]=='a'){
					var zeroNum = parseInt(zeroNumStr);
					if(!isNaN(zeroNum)){
						sn += zeroNum;
					}
					var node = hrDeptTreeObj.getNodeByParam("sn", sn+1, null);
					showParentNode(hrDeptTreeObj,node);
					zeroNumStr = "";
					sn++;
				}else{
					zeroNumStr += showIds[snIndex];
				}
				snIndex++;
			}
			if(!showIds||showIds.length==0){
				var node = hrDeptTreeObj.getNodeByParam("subSysTem", "ALL", null);
				hrDeptTreeObj.showNode(node);
			}
			/*$.each(showIds, function(){
				var node = hrDeptTreeObj.getNodeByParam("sn", this, null);
				showParentNode(hrDeptTreeObj,node);
			}); */ 
		}
		/*if(showIds&&showIds!='null'&&showIds!='all'){
			$.each(showIds, function(){
				var node = hrDeptTreeObj.getNodeByParam("id", this, null);
				showParentNode(hrDeptTreeObj,node);
			});  
		}*/
		//zTree.updateNode(node);
		var selectedNode = hrDeptTreeObj.getNodeByParam('id',selectedNodeId,null);
		var selectedNodes = hrDeptTreeObj.getSelectedNodes();
		if(selectedNodes!=null&&selectedNodes.length==1&&selectedNodes[0].id==selectedNodeId){
			return ;
		}
		if(selectedNode&&!selectedNode.isHidden){
			hrDeptTreeObj.selectNode(selectedNode,false);
		}
	}
}
function dealHrTreeNode(treeId,node,opt,type){
	var hrDeptTreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodeId = node.id;
	var oldNode = hrDeptTreeObj.getNodeByParam("id", nodeId, null);
	//console.log("nodeId:"+nodeId+" opt:"+opt);
	if(node){
		switch(opt)
		{
		case 'add':
			var parentNode = hrDeptTreeObj.getNodeByParam("id", node.pId, null);
			node = hrDeptTreeObj.addNodes(parentNode,node);
			var newNode = node[0];
			if(type=='person'){
				newNode.personCountAdd = 1 ;
				newNode.personCountPAdd = 1 ;
				newNode.personCountDAdd = 1 ;
				newNode.personCountDPAdd = 1 ;
				reComputePersonCount(hrDeptTreeObj,newNode);
			}else{
				newNode.personCountAdd = parseInt(newNode.personCount) ;
				newNode.personCountPAdd = parseInt(newNode.personCountP) ;
				newNode.personCountDAdd = parseInt(newNode.personCountD) ;
				newNode.personCountDPAdd = parseInt(newNode.personCountDP) ;
				reComputePersonCount(hrDeptTreeObj,newNode);
				hrDeptTreeObj.updateNode(newNode);
			}
			break;
		case 'change':
			if(oldNode){
				oldNode.nameWithoutPerson = node.name;
				hrDeptTreeObj.updateNode(oldNode);
			}
			break;
		case 'editPC':
			if(type=='person'){
				var parentNode = hrDeptTreeObj.getNodeByParam("id", node.pId, null);
				parentNode.personCountAdd = 1 ;
				parentNode.personCountPAdd = 1 ;
				parentNode.personCountDAdd = 1 ;
				parentNode.personCountDPAdd = 1 ;
				parentNode.personCount = parseInt(parentNode.personCount) + 1;
				parentNode.personCountP = parseInt(parentNode.personCountP) + 1;
				parentNode.personCountD = parseInt(parentNode.personCountD) + 1;
				parentNode.personCountDP = parseInt(parentNode.personCountDP) + 1;
				reComputePersonCount(hrDeptTreeObj,parentNode);
				hrDeptTreeObj.updateNode(parentNode);
			}
			if(oldNode){
				if(type=='dept'){
					oldNode.personCountAdd = parseInt(node.personCount) - parseInt(oldNode.personCount);
					oldNode.personCountPAdd = parseInt(node.personCountP) - parseInt(oldNode.personCountP);
					oldNode.personCountDAdd = parseInt(node.personCountD) - parseInt(oldNode.personCountD);
					oldNode.personCountDPAdd = parseInt(node.personCountDP) - parseInt(oldNode.personCountDP);
					oldNode.personCount = node.personCount;
					oldNode.personCountP = node.personCountP;
					oldNode.personCountD = node.personCountD;
					oldNode.personCountDP = node.personCountDP;
					hrDeptTreeObj.updateNode(oldNode);
					reComputePersonCount(hrDeptTreeObj,oldNode);
					//console.log("editPC success");
				}
			}
			break;
		case 'del':
			if(oldNode){
				if(type=='person'){
					oldNode.personCountAdd = -1 ;
					oldNode.personCountPAdd = -1 ;
					oldNode.personCountDAdd = -1 ;
					oldNode.personCountDPAdd = -1 ;
					
				}else if(type=='dept'){
					oldNode.personCountAdd = -parseInt(oldNode.personCount) ;
					oldNode.personCountPAdd = -parseInt(oldNode.personCountP) ;
					oldNode.personCountDAdd = -parseInt(oldNode.personCountD) ;
					oldNode.personCountDPAdd = -parseInt(oldNode.personCountDP) ;
				}
				reComputePersonCount(hrDeptTreeObj,oldNode);
				hrDeptTreeObj.removeNode(oldNode);
			}
			break;
		case 'delDisable':
			if(oldNode){
				if(type=='person'){
					oldNode.personCountAdd = -1 ;
					oldNode.personCountPAdd = 0 ;
					oldNode.personCountDAdd = -1 ;
					oldNode.personCountDPAdd = 0 ;
					
				}
				reComputePersonCount(hrDeptTreeObj,oldNode);
				hrDeptTreeObj.removeNode(oldNode);
			}
			break;
		case 'disable':
			if(oldNode){
				if(type=='person'){
					oldNode.personCountAdd = 0 ;
					oldNode.personCountPAdd = -1 ;
					oldNode.personCountDAdd = 0 ;
					oldNode.personCountDPAdd = -1 ;
				}else if(type=='dept'){
					oldNode.personCountAdd = 0 ;
					oldNode.personCountPAdd = 0 ;
					oldNode.personCountDAdd = -parseInt(oldNode.personCountD) ;
					oldNode.personCountDPAdd = 0 ;
				}
				oldNode.actionUrl = '1';
				reComputePersonCount(hrDeptTreeObj,oldNode);
				hrDeptTreeObj.updateNode(oldNode);
			}
			break;
		case 'rescind':
			if(oldNode){
				if(type=='dept'){
					oldNode.personCountAdd = -parseInt(oldNode.personCount) ;
					oldNode.personCountPAdd = -parseInt(oldNode.personCountP) ;
					oldNode.personCountDAdd = -parseInt(oldNode.personCountD) ;
					oldNode.personCountDPAdd = -parseInt(oldNode.personCountDP) ;
					oldNode.personCount = 0 ;
					oldNode.personCountP = 0 ;
					oldNode.personCountD = 0 ;
					oldNode.personCountDP = 0 ;
				}
				oldNode.actionUrl = '1';
				reComputePersonCount(hrDeptTreeObj,oldNode);
				hrDeptTreeObj.updateNode(oldNode);
			}
			break;
		case 'transfer':
			if(oldNode){
				if(type=='dept'){
					var toId = node.toId;
					var toNode = hrDeptTreeObj.getNodeByParam("id", toId, null);
					oldNode.personCountAdd = -parseInt(oldNode.personCount) ;
					oldNode.personCountPAdd = -parseInt(oldNode.personCountP) ;
					oldNode.personCountDAdd = -parseInt(oldNode.personCountD) ;
					oldNode.personCountDPAdd = -parseInt(oldNode.personCountDP) ;
					reComputePersonCount(hrDeptTreeObj,oldNode);
					hrDeptTreeObj.moveNode(toNode, oldNode, "inner");
					oldNode.personCountAdd = parseInt(oldNode.personCount) ;
					oldNode.personCountPAdd = parseInt(oldNode.personCountP) ;
					oldNode.personCountDAdd = parseInt(oldNode.personCountD) ;
					oldNode.personCountDPAdd = parseInt(oldNode.personCountDP) ;
					reComputePersonCount(hrDeptTreeObj,oldNode);
				}
			}
			break;
		case 'rescindParent':
			if(oldNode){
				if(type=='dept'){
					oldNode.personCount = 0 ;
					oldNode.personCountP = 0 ;
					oldNode.personCountD = 0 ;
					oldNode.personCountDP = 0 ;
				}
				oldNode.actionUrl = '1';
				hrDeptTreeObj.updateNode(oldNode);
			}
			break;
		case 'enable':
			if(oldNode){
				if(type=='person'){
					oldNode.personCountAdd = 0 ;
					oldNode.personCountPAdd = 1 ;
					oldNode.personCountDAdd = 0 ;
					oldNode.personCountDPAdd = 1 ;
				}else if(type=='dept'){
					oldNode.personCountAdd = 0 ;
					oldNode.personCountPAdd = 0 ;
					oldNode.personCountDAdd = parseInt(oldNode.personCountD) ;
					oldNode.personCountDPAdd = 0 ;
				}
				oldNode.actionUrl = '0';
				reComputePersonCount(hrDeptTreeObj,oldNode);
				hrDeptTreeObj.updateNode(oldNode);
			}
			break;
		}
	}
}
function reComputePersonCount(hrDeptTreeObj,node){
	var parentNode = node.getParentNode();
	if(!parentNode){
		return ;
	}
	if(node.personCountAdd||node.personCountAdd==0){
		parentNode.personCount = parseInt(parentNode.personCount) + parseInt(node.personCountAdd);
		parentNode.personCountAdd = node.personCountAdd;
	}
	if(node.personCountPAdd||node.personCountPAdd==0){
		parentNode.personCountP = parseInt(parentNode.personCountP) + parseInt(node.personCountPAdd);
		parentNode.personCountPAdd = node.personCountPAdd;
	}
	if(node.personCountDAdd||node.personCountDAdd==0){
		parentNode.personCountD = parseInt(parentNode.personCountD) + parseInt(node.personCountDAdd);
		parentNode.personCountDAdd = node.personCountDAdd;
	}
	if(node.personCountDPAdd||node.personCountDPAdd==0){
		parentNode.personCountDP = parseInt(parentNode.personCountDP) + parseInt(node.personCountDPAdd);
		parentNode.personCountDPAdd = node.personCountDPAdd;
	}
	hrDeptTreeObj.updateNode(parentNode);
	reComputePersonCount(hrDeptTreeObj,parentNode);
}
function searchTree(treeId,input){
	if(treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var nodes = null , showNodes = null;//zTree.transformToArray(zTree.getNodes());
		var initNodes = jQuery("#"+treeId).data('initNodes');
		var initNodesMap = jQuery("#"+treeId).data('initNodesMap');
		var treeChanged = jQuery("#"+treeId).data('treeChanged');
		if(!initNodes){
			initNodes = zTree.getNodesByParam("isHidden", false, null);
			initNodesMap = new Map();
			for(var nindex in initNodes){
				initNodesMap.put(initNodes[nindex].id,true);
			}
			jQuery("#"+treeId).data('initNodes',initNodes);
			jQuery("#"+treeId).data('initNodesMap',initNodesMap);
		}
		if(treeChanged=='true'&&initNodes){
			initNodesMap = new Map();
			for(var nindex in initNodes){
				initNodesMap.put(initNodes[nindex].id,true);
			}
			jQuery("#"+treeId).data('initNodesMap',initNodesMap);
			jQuery("#"+treeId).data('treeChanged','false');
		}
		var seearchValue = jQuery(input).val();
		if(seearchValue==""){
			//nodes = zTree.getNodesByParam("isHidden", true, null);
			zTree.showNodes(initNodes);
			zTree.checkAllNodes(false);
			jQuery(input).val("");
		}else{
			nodes = zTree.getNodesByParam("isHidden", false, null);
			zTree.hideNodes(nodes);
			var showNodes = zTree.getNodesByParamFuzzy('name',seearchValue,null);
			for(var nodeIndex=0;nodeIndex<showNodes.length;nodeIndex++){
				showParentNode(zTree,showNodes[nodeIndex],initNodesMap);
				//zTree.checkNode(nodes[i], true, true);
			}
		}
	}
}
function showParentNode(zTree,node,initNodesMap){
	if(!node){
		return ;
	}
	if(initNodesMap&&!initNodesMap.get(node.id)){
		return ;
	}
	if(!node.isHidden){
		return ;
	}
	zTree.showNode(node);
	//node.nocheck = false;
	//zTree.updateNode(node);
	if(node.getParentNode()!=null){
		showParentNode(zTree,node.getParentNode(),initNodesMap)
	}
}
/*
 * 显示人员数
 */
function toogleShowPersonCount(dObj,pObj,treeId){
	var hrDeptTreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = hrDeptTreeObj.transformToArray(hrDeptTreeObj.getNodes());
	var node;
	if(pObj.checked){
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem!="ALL"){
				if(dObj.checked){
					node.name = node.name+"("+node.personCount+")";
				}else{
					node.name = node.name+"("+node.personCountD+")";
				}
				hrDeptTreeObj.updateNode(node);
			}
		}
	}else{
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem!="ALL"){
				node.name = node.nameWithoutPerson;
				hrDeptTreeObj.updateNode(node);
			}
		}
	}
}
/**
 * 显示停用部门--针对单位部门树
 * @param obj
 * @param treeId
 */
function toogleShowDisabledDept(dObj,pObj,treeId){
	var hrDeptTreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = hrDeptTreeObj.transformToArray(hrDeptTreeObj.getNodes());
	var node;
	if(dObj.checked){
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem!="ALL"){
				if(pObj.checked){
					node.name = node.nameWithoutPerson+"("+node.personCount+")";
				}else{
					node.name = node.nameWithoutPerson;
				}
				hrDeptTreeObj.updateNode(node);
			}
			if(node.subSysTem=="DEPT" && node.actionUrl == '1' ){
				hrDeptTreeObj.showNode(node);
			}
		}
	}else{
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem!="ALL"){
				if(pObj.checked){
					node.name = node.nameWithoutPerson+"("+node.personCountD+")";
				}else{
					node.name = node.nameWithoutPerson;
				}
				hrDeptTreeObj.updateNode(node);
			}
			if(node.subSysTem=="DEPT" && node.actionUrl == '1'){
				node.name = node.nameWithoutPerson;
				hrDeptTreeObj.hideNode(node);
			}
		}
	}
}
/**
 * 显示停用部门--针对单位部门人员树
 * @param dObj
 * @param pObj
 * @param treeId
 */
function toogleShowDisabledDeptWithPerson(dObj,pObj,treeId){
	var hrPersonTreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = hrPersonTreeObj.transformToArray(hrPersonTreeObj.getNodes());
	var node;
	if(dObj.checked){//包含停用部门
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem=="ORG" || node.subSysTem=="DEPT"){
				if(pObj.checked){//包含停用人员
					node.name = node.nameWithoutPerson+"("+node.personCount+")";
				}else{//不包含停用人员
					node.name = node.nameWithoutPerson+"("+node.personCountP+")";
				}
				hrPersonTreeObj.updateNode(node);
			}
			if(node.subSysTem=="DEPT" && node.actionUrl == '1' ){
				hrPersonTreeObj.showNode(node);
			}
		}
	}else{
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem=="ORG" || node.subSysTem=="DEPT"){
				if(pObj.checked){//包含停用人员
					node.name = node.nameWithoutPerson+"("+node.personCountD+")";
				}else{//不包含停用人员
					node.name = node.nameWithoutPerson+"("+node.personCountDP+")";
				}
				hrPersonTreeObj.updateNode(node);
			}
			if(node.subSysTem=="DEPT" && node.actionUrl == '1' ){
				hrPersonTreeObj.hideNode(node);
			}
		}
	}
}
/**
 * 显示停用人员--针对单位部门人员树
 * @param dObj
 * @param pObj
 * @param treeId
 */
function toogleShowDisabledPerson(dObj,pObj,treeId){
	var hrPersonTreeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = hrPersonTreeObj.transformToArray(hrPersonTreeObj.getNodes());
	var node;
	if(pObj.checked){//包含停用人员
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem=="ORG" || node.subSysTem=="DEPT"){
				if(dObj.checked){//包含停用部门
					node.name = node.nameWithoutPerson+"("+node.personCount+")";
				}else{//不包含停用部门
					node.name = node.nameWithoutPerson+"("+node.personCountD+")";
				}
				hrPersonTreeObj.updateNode(node);
			}
			if(node.subSysTem=="PERSON" && node.actionUrl == '1' ){
				hrPersonTreeObj.showNode(node);
			}
		}
	}else{//不包含停用人员
		for(index in nodes){
			node = nodes[index];
			if(node.subSysTem=="ORG" || node.subSysTem=="DEPT"){
				if(dObj.checked){//包含停用部门
					node.name = node.nameWithoutPerson+"("+node.personCountP+")";
				}else{//不包含停用部门
					node.name = node.nameWithoutPerson+"("+node.personCountDP+")";
				}
				hrPersonTreeObj.updateNode(node);
			}
			if(node.subSysTem=="PERSON" && node.actionUrl == '1' ){
				hrPersonTreeObj.hideNode(node);
			}
		}
	}
}
/*
 * 设置左侧部门树的布局
 */
function setHrDeptLeftTreeLayout(container,gridtable,containerHeight){
	jQuery("#"+container).css("height",containerHeight);
	$('#'+container).layout({ 
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
}
/*
 * 查看同步信息超链接
 */
function viewSyncHrData(shapId,syncType,hr_snap_time){
	var url = "editSyncHrData?syncHrId="+shapId+"&oper=view&syncType="+syncType+"&hr_time="+hr_snap_time;
	if(syncType=='1'){
		$.pdialog.open(url,'viewSyncHrData_'+shapId,'查看从HR同步信息', {mask:true,width : 510,height : 280,maxable:false,resizable:false});
	}else{
		$.pdialog.open(url,'viewSyncHrData_'+shapId,'查看同步到HR信息', {mask:true,width : 510,height : 350,maxable:false,resizable:false});
	}
}
/*
 * 查看单位信息超链接
 */
function viewHrOrgRecord(snapId){
	var url = "editHrOrgSnap?snapId="+snapId+"&oper=view";
	$.pdialog.open(url,'viewHrOrgSnap_'+snapId,'查看单位信息', {mask:true,width : 650,height : 400,maxable:false,resizable:false});
}
/*
 * 查看部门信息超链接
 */
function viewHrDeptRecord(snapId,random){
	var url = "editHrDepartmentSnap?snapId="+snapId+"&oper=view&random="+random;
	$.pdialog.open(url,'viewHrDepartmentSnap_'+snapId,'查看部门信息', {mask:true,width : 680,height : 518,maxable:false,resizable:false});
}
/*
 * 查看人员信息超链接
 */
function viewHrPersonRecord(snapId){
	var url = "editHrPersonSnap?snapId="+snapId+"&oper=view";
	$.pdialog.open(url,'viewHrPersonSnap_'+snapId,'查看人员信息', {mask:true,width : 800,height : 600,maxable:false,resizable:false});
}
/*
 * 查看合同信息超链接
 */
function viewPactRecord(id,viewFrom){
	var url = "editPact?id="+id+"&oper=view";
	if(viewFrom){
		url += "&viewFrom="+viewFrom;
	}
	$.pdialog.open(url,'viewPact_'+id,'查看合同信息', {mask:true,width : 620,height : 420,maxable:false,resizable:false});
}
/*
 * 查看简历信息超链接
 */
function viewRecruitResumeRecordAfterSetCell(id){
	var url = "editRecruitResume?oper=viewResume&id="+id;
	$.pdialog.open(url,'viewRecruitResume_'+id,'查看简历信息', {mask:true,width : 700,height : 600,maxable:false,resizable:false});
}
/*
 * 只读模式查看表单
 */
function readOnlyForm(formId){
	jQuery("#"+formId+"SaveButton").hide();
	jQuery("#"+formId).find("input[type='text']").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true").removeAttr("onclick").removeAttr("onfocus");
	
	jQuery("#"+formId).find("textarea").attr("disabled","disabled").attr('readOnly',"true").removeClass('required');
	jQuery("#"+formId).find("textarea").each(function(){
//		this.cols += 2;
		var oldStyle = jQuery(this).attr("style");
		if(oldStyle){
			oldStyle = "BORDER:solid 1px #888888;overflow:hidden;background-color: #ffffff;resize:none;" + oldStyle;
		}else{
			oldStyle = "BORDER:solid 1px #888888;overflow:hidden;background-color: #ffffff;resize:none;";
		}
		jQuery(this).attr("style",oldStyle);
	});
	jQuery("#"+formId).find("input[type='checkbox']").each(function(){
		jQuery(this).bind('click',function(){return false;})
	});
	jQuery("#"+formId).find("select").each(function(){
		var options = this.options;
		var text = "";
		if(options && options.length > 0){
			text = options[this.selectedIndex].text;
		}
		var html = "<input type='text' value='"+text+"' class='lineInput' readonly='readonly'/>";
		jQuery(this).before(html);
		jQuery(this).remove();
	});
	//jQuery("#"+formId).find("input").attr("disabled","disabled");
}
/*
 * 展开\折叠树
 */
function toggleExpandHrTree(obj,treeId){
	var hrTree = $.fn.zTree.getZTreeObj(treeId); 
	var obj = jQuery(obj);
	var text = obj.html();
	if(text=='展开'){
		obj.html("折叠");
		var expendArr = new Array();
		expandHrNode(hrTree,hrTree.getNodes()[0],expendArr);
		/*for(var i=0;i<expendArr.length;i++){
			var node = expendArr[i];
			setTimeout(function(){
				hrTree.expandNode(node,true,false,true);
			},10);
		}*/
		var nodeIndex=0 , nodeLength=expendArr.length;
		var expendTimer = setInterval(function(){
			if(nodeIndex>nodeLength-1){
				clearInterval(expendTimer);
			}else{
				var node = expendArr[nodeIndex];
				if(node){
					hrTree.expandNode(node,true,false,false);
				}
				nodeIndex++;
			}
		},10);
		//hrTree.expandAll(true);
	}else{
		obj.html("展开");
		//zTree.expandAll(false);
		var allNodes = hrTree.transformToArray(hrTree.getNodes());
		for(var nodeIndex in allNodes){
			var node = allNodes[nodeIndex];
			if(node.subSysTem=='ORG'){
				hrTree.expandNode(node,false,true,false);
			}
		}
	}
}
function expandHrNode(hrTree,node,expendArr){
	if(!node){
		return ;
	}
	var childrenNodes = node.children;
	if(!childrenNodes||childrenNodes[0].subSysTem=='PERSON'){
		/*var pNode = node.getParentNode();
		if(pNode){
			removeArrNode(expendArr,pNode);
		}
		expendArr.push(node);*/
		return ;
	}else{
		var pNode = node.getParentNode();
		if(pNode){
			removeArrNode(expendArr,pNode);
		}
		expendArr.push(node);
		//hrTree.expandNode(node,true,false,true);
		for(var i=0;i<childrenNodes.length;i++){
			expandHrNode(hrTree,childrenNodes[i],expendArr);
		}
	}
}
function removeArrNode(arr,node){
	if(!arr){
		return ;
	}
	var arrlength = arr.length;
	for(var i=0;i<arrlength;i++){
		if(!arr[i]){
			continue;
		}
		if(arr[i].tId==node.tId){
			arr[i] = null;
			break;
		}
	}
}
function nodeExitInArr(arr,node){
	if(!arr){
		return false;
	}
	var arrlength = arr.length;
	for(var i=0;i<arrlength;i++){
		if(arr[i].tId==node.tId){
			return true;
		}
	}
}
