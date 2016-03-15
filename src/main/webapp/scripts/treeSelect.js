var treeSelects = new Array();
/*
 * selectType select类型：list,tree,class
 * async      同步 ：sync，async
 * nameField  显示inputId
 * valueField 存Id inputId
 * optType    操作类型 single,multi
 * params     取数据参数
 * {
 *   tableName 数据表名
 *   treeId    数据表对应树ID
 *   treeName  数据表对应树name
 *   parentId  数据表对应树父节点ID
 *   order     排序字段
 *   filter    过滤条件
 *   
 *   classTable     类型表名
 *   classTreeId    类型数据表对应树ID
 *   classTreeName  类型数据表对应树name
 *   classOrder     类型排序字段
 *   classFilter    类型过滤条件
 * }
 */
function addTreeSelect(selectType,async,nameField,valueField,optType,params){
	try{
		var treeSelect = new TreeSelect();
		treeSelects.push(treeSelect);
		treeSelect.async = async;
		treeSelect.selectType = selectType;
		treeSelect.nameField = nameField;
		treeSelect.valueField = valueField;
		treeSelect.optType = optType;
		var tableName = params.tableName;
		var treeId = params.treeId;
		var treeName = params.treeName;
		var parentId = params.parentId;
		var order = params.order?params.order:"";
		var filter = params.filter?params.filter:"";
		var initSelect = params.initSelect?params.initSelect:"";
		var disabledSelect = params.disabledSelect?params.disabledSelect:"";
		
		var classTable = params.classTable;
		var classTreeId = params.classTreeId;
		var classTreeName = params.classTreeName;
		var classOrder = params.classOrder?params.classOrder:"";
		var classFilter = params.classFilter;
		
		treeSelect.tableName = tableName;
		treeSelect.treeId = treeId;
		treeSelect.treeName = treeName;
		treeSelect.parentId = parentId;
		treeSelect.filter = filter;
		
		var localSetting = ztreesetting;
		
		jQuery("#"+nameField).unbind("click").bind("click",function(){
			showTree(treeSelect);
		});
		jQuery("#"+nameField).unbind("keyup").bind("keyup",function(){
			changeTree(treeSelect);
		});
		var nameFieldDiv = jQuery("#"+nameField+"FENGE"+valueField+"FENGEtreeContent");
		if(nameFieldDiv.length>0){
			/*if(optType=='multi'){
				var zTree = $.fn.zTree.getZTreeObj(nameField+"FENGE"+valueField+"FENGEtree");
				zTree.checkAllNodes(false);
			}
			return;*/
			jQuery(nameFieldDiv).remove();
		}
		$.ajax({
		    url: 'treeSelectNodes',
		    type: 'post',
		    data: {tableName:tableName,treeId:treeId,treeName:treeName,parentId:parentId,order:order,filter:filter,
		    	   classTable:classTable,classTreeId:classTreeId,classTreeName:classTreeName,classOrder:classOrder,classFilter:classFilter},
		    dataType: 'json',
		    aysnc:false,
		    success: function(data){
		    	treeSelect.zNodes = data.nodes;
		    	var nameFieldWeigth = jQuery("#"+nameField).innerWidth();
		    	$('body').append("<div id='"+nameField+"FENGE"+valueField+"FENGEtreeContent' class='menuContent' style='max-height:300px;border:1px solid;border-color: #A2BAC0 #99BBE8 #99BBE8 #A2BAC0;display:none; position: absolute;z-index:200'><ul id='"+nameField+"FENGE"+valueField+"FENGEtree' class='ztree' style='max-height:295px;margin-top:2px; width:"+nameFieldWeigth+"px; '></ul></div>");
		    	if(optType=="single"){
		    		delete localSetting.check ;
		    		delete localSetting.callback.onCheck ;
		    		localSetting.callback.onClick = onClick;
		    	}else{
		    		localSetting.check = {
		    				enable: true,
		    				chkStyle:'checkbox',
		    				chkboxType: {"Y":"ps", "N":"ps"}
		    			};
		    		localSetting.callback.onCheck = onCheck;
		    		delete localSetting.callback.onClick;
		    	}
		    	treeSelect.localSetting = localSetting;
		    	//makeList(treeSelect,treeSelect.zNodes);
		    	var zTree;
		    	if(treeSelect.zNodes.length==0){
		    		jQuery("#"+nameField+"FENGE"+valueField+"FENGEtreeContent").html("<div class='ztree' style='background: white;margin-top:2px;width:"+nameFieldWeigth+"px; '>无数据</div>");
		    		return;
		    	}else{
		    		zTree = $.fn.zTree.init($("#"+nameField+"FENGE"+valueField+"FENGEtree"), localSetting, treeSelect.zNodes);
		    	}
		    	
		    	
		    	if(initSelect){
		    		var selectId = initSelect.split(",");
		    		for(var si=0;si<selectId.length;si++){
		    			var node = zTree.getNodeByParam("id",selectId[si]);
		    			if(node!=null){
		    				zTree.checkNode(node, true, true);
			    		}
		    		}
		    		
			    	/*var nodes = zTree.getNodes();
			    	alert(nodes.length);
			    	for(var ni=0;ni<nodes.length;ni++){
			    		var node = nodes[ni];
			    		var selectId = initSelect.split(",");
			    		for(var si=0;si<selectId.length;si++){
			    			if(node.id==selectId[si]){
			    				zTree.checkNode(node, true, true);
			    				break;
				    		}
			    		}
			    		
			    	}*/
		    	}
		    	if(disabledSelect){
		    		var selectDisabled = disabledSelect.split(",");
		    		for(var sd=0;sd<selectDisabled.length;sd++){
		    			var node = zTree.getNodeByParam("id",selectDisabled[sd]);
		    			if(node!=null){
		    				zTree.setChkDisabled(node, true);
			    		}
		    		}
		    	}
		    	if(treeSelect.selectType=="class"){
		    		hideNullClass(treeSelect);
		    	}
		    }
		});
	}catch(e){
		alert(e.message);
	}
	return treeSelect;
}

/*var ztreesetting = {
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick: onClick
		}
		
	};*/
var ztreesetting = {
		check: {
			enable: true,
			chkStyle:'checkbox',
			chkboxType: {"Y":"ps", "N":"ps"}
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onCheck: onCheck
		}
		
	};
var TreeSelect = function(){
	this.selectType = null;
	this.zNodes = null;
	this.nameField = null;
	this.valueField = null;
	this.isShow = false;
	this.async = null;
	this.optType = null;
	
	this.tableName = null;
	this.treeId = null;
	this.treeName = null;
	this.parentId = null;
	this.filter = null;
	
	this.localSetting = null;
}

function beforeClick(treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	//if (!check) alert("只能选择城市...");
	return check;
}
	
function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true),
		v = "",
		vi = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			if(!nodes[i].isParent){
				v += nodes[i].name + ",";
				vi += nodes[i].id + ",";
			}
			
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (vi.length > 0 ) vi = vi.substring(0, vi.length-1);
		var nameObj = $("#"+treeId.split("FENGE")[0]);
		if(nameObj){
			nameObj.attr("value", v);
		}
		var idObj = $("#"+treeId.split("FENGE")[1]);
		if(idObj){
			idObj.attr("value", vi);
		}
		
	}
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId),
	nodes = zTree.getSelectedNodes(),
	v = "",
	vi = "";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		if(!nodes[i].isParent){
			v += nodes[i].name + ",";
			vi += nodes[i].id + ",";
		}
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (vi.length > 0 ) vi = vi.substring(0, vi.length-1);
	var nameObj = $("#"+treeId.split("FENGE")[0]);
	if(nameObj){
		nameObj.attr("value", v);
	}
	var idObj = $("#"+treeId.split("FENGE")[1]);
	if(idObj){
		idObj.attr("value", vi);
	}
	hideTree(treeId.split("FENGE")[0],treeId.split("FENGE")[1]);
}
function hideTree(nameField,valueField) {
	$("#"+nameField+"FENGE"+valueField+"FENGEtreeContent").fadeOut("fast");
	var allHide = false;
	for(var i=0;i<treeSelects.length;i++){
		allHide = treeSelects[i].isShow;
		if(allHide){
			break;
		}
	}
	if(!allHide){
		$("body").unbind("mousedown", onBodyDown);
	}
}
function onBodyDown(event){
	for(var i=0;i<treeSelects.length;i++){
		var nameField = treeSelects[i].nameField;
		var valueField = treeSelects[i].valueField;
		if (!(event.target.id == "menuBtn" || event.target.id == nameField|| event.target.id == valueField || event.target.id == nameField+"FENGE"+valueField+"FENGEtreeContent" || $(event.target).parents("#"+nameField+"FENGE"+valueField+"FENGEtreeContent").length>0)) {
			treeSelects[i].isShow = false;
			hideTree(nameField,valueField);
		}
	}
}
function showTree(treeSelect){
	var nameObj = $("#"+treeSelect.nameField);
	var nameOffset = $("#"+treeSelect.nameField).offset();
	$("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent").css({left:nameOffset.left + "px", top:nameOffset.top + nameObj.outerHeight() + "px"}).slideDown("fast");
	$("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree").css("width",$("#"+treeSelect.nameField).innerWidth()+"px");
	treeSelect.isShow = true;
	$("body").bind("mousedown", onBodyDown);
}
function changeTree(treeSelect){
	var nameObj = $("#"+treeSelect.nameField);
	var nameOffset = $("#"+treeSelect.nameField).offset();
	var treeContainer = $("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent");
	var nameObjValue = nameObj.val();
	
	if(treeSelect.async=="async"){
		var url =  'treeSelectNodes?tableName='+treeSelect.tableName+'&treeId='+treeSelect.treeId+'&treeName='+treeSelect.treeName+'&parentId='+treeSelect.parentId+'&filter='+treeSelect.filter+" and "+treeSelect.treeName+" like '%"+nameObjValue+"%'";
		url = encodeURI(url);
		$.ajax({
		    url: url,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    success: function(data){
		    	treeSelect.zNodes = data.nodes;
		    	//$('body').append("<div id='"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent' class='menuContent' style='display:none; position: absolute;z-index:200'><ul id='"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree' class='ztree' style='margin-top:0; width:180px; height: 300px;'></ul></div>");
		    	$.fn.zTree.init($("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree"), treeSelect.localSetting, treeSelect.zNodes);
		    	showTree(treeSelect);
		    }
		});
	}else{
		var zTree = $.fn.zTree.getZTreeObj(treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree");
		//zTree.expandAll(true);
		var nodes = zTree.transformToArray(zTree.getNodes());
		if(nameObjValue==""){
			/*$("li",treeContainer).each(function(){
				$(this).show();
			});*/
			//alert(nodes.length);
			//zTree.showNodes(nodes);
			for(var nodeIndex=0;nodeIndex<nodes.length;nodeIndex++){
				zTree.showNode(nodes[nodeIndex]);
				nodes[nodeIndex].nocheck = false;
				zTree.updateNode(nodes[nodeIndex]);
			}
			if(treeSelect.selectType=="class"){
	    		hideNullClass(treeSelect);
	    	}
			jQuery("#"+treeSelect.valueField).val("");
		}else{
			/*$("li",treeContainer).each(function(){
				if($(this).find('ul').length==0&&$(this).text().indexOf(nameObjValue)==-1){
					$(this).hide();
				}else{
					$(this).show();
				}
			});*/
			/*for (var i=0, l=nodes.length; i<l; i++) {
				if(!nodes[i].isParent&&nodes[i].name.indexOf(nameObjValue)==-1){
					zTree.hideNode(nodes[i]);
				}else{
					zTree.showNode(nodes[i]);
				}
			}*/
			zTree.hideNodes(nodes);
			var showNodes = zTree.getNodesByParamFuzzy('name',nameObjValue,null);
			for(var nodeIndex=0;nodeIndex<showNodes.length;nodeIndex++){
				showParentNode(zTree,showNodes[nodeIndex]);
			}
			//zTree.showNodes(showNodes);
		}
		
		/*if(treeSelect.selectType=="tree"){
			nodes = nodes.reverse();
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].isParent){
					var isNull = false;
					var childrenNodes = nodes[i].children;
					for(var j=0,jl=childrenNodes.length;j<jl;j++){
						if(!childrenNodes[j].isHidden){
							isNull = true;
							break;
						}
					}
					if(!isNull){
						zTree.hideNode(nodes[i]);
					}
				}
			}
			nodes = nodes.reverse();
			for (var i=0, l=nodes.length; i<l; i++) {
				if(!nodes[i].isHidden){
					zTree.showNode(nodes[i]);
				}
			}
		}else{
			for (var i=0, l=nodes.length; i<l; i++) {
				if(!nodes[i].isHidden){
					zTree.showNode(nodes[i]);
				}
			}
		}*/
	}
	
	
	
	/*$("ul",treeContainer).each(function(){
		if($(this).find('li').length==0){
			$(this).hide();
		}
	});*/
	//$("#"+treeSelect.nameField+"FENGE"+valueField+"FENGEtreeContent").hide();
	//$("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent").css({left:nameOffset.left + "px", top:nameOffset.top + nameObj.outerHeight() + "px"}).show();
}

/*function isEmpty(node){
	var childrenNodes = node.children;
	
	if(){
		
	}
}*/

function showParentNode(zTree,node){
	zTree.showNode(node);
	node.nocheck = false;
	zTree.updateNode(node);
	if(node.getParentNode()!=null){
		showParentNode(zTree,node.getParentNode())
	}
}

function hideNullClass(treeSelect){
	var zTree = $.fn.zTree.getZTreeObj(treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree");
	var nodes = zTree.transformToArray(zTree.getNodes());
	for (var i=0, l=nodes.length; i<l; i++) {
		if(!nodes[i].isParent&&nodes[i].level=="0"){
			zTree.hideNode(nodes[i]);
		}
	}
}


function changeTree11(treeSelect){
	var nameObj = $("#"+treeSelect.nameField);
	var nameOffset = $("#"+treeSelect.nameField).offset();
	var treeContainer = $("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent");
	var nameObjValue = nameObj.val();
	var url =  'treeSelectNodes?tableName='+treeSelect.tableName+'&treeId='+treeSelect.treeId+'&treeName='+treeSelect.treeName+'&parentId='+treeSelect.parentId+'&filter='+treeSelect.filter+" and "+treeSelect.treeName+" like '%"+nameObjValue+"%'";
	url = encodeURI(url);
	$.ajax({
	    url: url,
	    type: 'post',
	    dataType: 'json',
	    aysnc:false,
	    success: function(data){
	    	$("#background,#progressBar").hide();
	    	treeSelect.zNodes = data.nodes;
	    	//$('body').append("<div id='"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent' class='menuContent' style='display:none; position: absolute;z-index:200'><ul id='"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree' class='ztree' style='margin-top:0; width:180px; height: 300px;'></ul></div>");
	    	$.fn.zTree.init($("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree"), treeSelect.localSetting, treeSelect.zNodes);
	    	showTree(treeSelect);
	    }
	});
}

function makeList(treeSelect,jasonData){
	var $ul = jQuery("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtree");
	for(var o in jasonData){
		$ul.append("<li style='line-height:14px'><input type='checkbox' id='"+jasonData[o].id+"'/>"+jasonData[o].name+"</li>");
	}
}

function resizeTree(treeSelect){
	var treeHeight = jQuery("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent").height();
	if(parseInt(treeHeight)>300){
		jQuery("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent").height(300);
	}else{
		//jQuery("#"+treeSelect.nameField+"FENGE"+treeSelect.valueField+"FENGEtreeContent").css
	}
}