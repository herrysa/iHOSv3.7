/*
 * @author zzh
 * @description make treeselect with ztree
 */
/**
 * 树属性说明:optType:节点选择模式(可选single或者multi);dataType:(可选sql,url,nodes);
 * 节点属性说明:id;name:显示名;icon:节点图片;parent:父级ID;isparent:是否父级(可选0和1);
 * actionUrl是否停用(可选0和1);disCheckAble:该节点选中后是否填入到查询框中(可选0和1)
 */
(function($){
	$.fn.treeselect = function(opts){
	var nameField = $(this).attr("id");
	var setting = {
		nameObj : "#"+nameField,
		idObj : "#"+nameField+"_id",
		treediv : "#"+nameField+"_treediv",
		tree : "#"+nameField+"_tree",
		treeId : nameField+"_tree",
		opts : opts
	};
	if(opts.idId){
		setting.idObj = "#"+opts.idId;
	}
	var ztreesetting = {
		check: {
			enable: true,
			chkStyle:'checkbox',
			chkboxType: {"Y":"ps", "N":"ps"}
		},
		view: {
			dblClickExpand: false,
			fontCss : setDisabledFontCss
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

	/*
	 * 设置停用部门的样式
	 */
	function setDisabledFontCss(treeId, treeNode) {
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
	
	function beforeClick(treeId, treeNode) {
		var check = (treeNode && (setting.opts.selectParent||!treeNode.isParent));
		if(setting.opts.relateDown){
			var dArr = setting.opts.relateDown.split(",");
			for(var di=0;di<dArr.length;di++){
				jQuery("#"+dArr[di]).val("");
				jQuery("#"+dArr[di]+"_name").val("");
			}
		}
		return check;
	}
		
	function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj(treeId),
			nodes = zTree.getCheckedNodes(true),
			v = "",
			vi = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].disCheckAble){
					continue;
				}
				if(setting.opts.selectParent||!nodes[i].isParent){
					v += nodes[i].name.trim() + ",";
					vi += nodes[i].id.trim() + ",";
				}
				
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (vi.length > 0 ) vi = vi.substring(0, vi.length-1);
			//var nameObj = $(setting.nameObj);
			//if(nameObj){
				$(setting.nameObj).attr("value", v);
			//}
				$(setting.idObj).attr("value", vi);
			
			/*var idObj = $("#"+treeId.split("FENGE")[1]);
			if(idObj){
				idObj.attr("value", vi);
			}*/
			if(setting.opts.callback!=null&&typeof(setting.opts.callback.afterCheck)=='function'){
				setting.opts.callback.afterCheck(treeId,vi,v);
			}
			
		}
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getSelectedNodes(),
		v = "",
		vi = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			if(setting.opts.selectParent||!nodes[i].isParent){
				v += nodes[i].name.trim() + ",";
				vi += nodes[i].id.trim() + ",";
				if(setting.opts.relateDown){
					var dArr = setting.opts.relateDown.split(",");
					for(var di=0;di<dArr.length;di++){
						var htmlName = dArr[di];
						//var hvp=/\(.*?\)/;
						//var parr = hvp.exec(htmlName);
						var vn = "",hv="",hn=htmlName;
						if(htmlName.indexOf(":")!=-1){
							hn = htmlName.split(":")[0];
							vn = htmlName.split(":")[1];
							hv = nodes[i][vn];
							//alert(hv);
							if(!hv){
								hv = "";
							}
							jQuery("#"+htmlName.split(":")[0]).val(hv);
						}
						if(setting.opts.relatePrefix){
							htmlName = setting.opts.relatePrefix+hn;
						}
						jQuery("#"+htmlName).val(hv);
						/*if(parr&&parr.length>0){
							vn = htmlName.substring(parr.index);
							vn = vn.substring(1,vn.length-1);
							alert(vn);
							hv = nodes[i][vn];
							if(setting.opts.relatePrefix){
								htmlName = setting.opts.relatePrefix+dArr[di];
							}
							if(!hv){
								hv = "";
							}
							jQuery("#"+htmlName).val(hv);
							//jQuery("#"+htmlName+"_name").val(hv);
						}*/
					}
				}
			}
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (vi.length > 0 ) vi = vi.substring(0, vi.length-1);

		$(setting.nameObj).attr("value", v);
			//}
		$(setting.idObj).attr("value", vi);

		/*var nameObj = $("#"+treeId.split("FENGE")[0]);
		if(nameObj){
			nameObj.attr("value", v);
		}
		var idObj = $("#"+treeId.split("FENGE")[1]);
		if(idObj){
			idObj.attr("value", vi);
		}*/
		hideTree();
		if(setting.opts.callback!=null&&typeof(setting.opts.callback.afterClick)=='function'){
			setting.opts.callback.afterClick(treeId,vi,v);
		}
	}

	function hideTree() {
		var idValue = $(setting.idObj).val();
		if(idValue!==0&&!idValue){
			$(setting.nameObj).val("");
			//changeTree(ts);
			var zTree = $.fn.zTree.getZTreeObj(setting.treeId);
			if(zTree!=null){
				var nodes = null;//zTree.transformToArray(zTree.getNodes());
				nodes = zTree.getNodesByParam("isHidden", true, null);
				zTree.showNodes(nodes);
				zTree.checkAllNodes(false);
			}
		}
		$(setting.treediv).fadeOut("fast");
		/*var allHide = false;
		for(var i=0;i<treeSelects.length;i++){
			allHide = treeSelects[i].isShow;
			if(allHide){
				break;
			}
		}
		if(!allHide){
			$("body").unbind("mousedown", onBodyDown);
		}*/
	}
	function onBodyDown(event){
		//zzhJsTest.debug(event.target.id);
		//zzhJsTest.debug($(event.target).parents(setting.treediv).length);
		if (!( event.target.id == "menuBtn"||event.target.id == nameField|| event.target.id == nameField+"_treediv" ||$(event.target).parents(setting.treediv).length>0 ) ){
				hideTree();
				$(document).unbind("click", onBodyDown);
		}
		/*for(var i=0;i<treeSelects.length;i++){
			var nameField = treeSelects[i].nameField;
			var valueField = treeSelects[i].valueField;
			if (!(event.target.id == "menuBtn" || event.target.id == nameField|| event.target.id == valueField || event.target.id == nameField+"FENGE"+valueField+"FENGEtreeContent" || $(event.target).parents("#"+nameField+"FENGE"+valueField+"FENGEtreeContent").length>0)) {
				treeSelects[i].isShow = false;
				hideTree(nameField,valueField);
			}
		}*/
	}
	function showTree(){
		var zTree = $.fn.zTree.getZTreeObj(setting.treeId);
		if(zTree){
			var selectId = $(setting.idObj).val().split(",");
			for(var si=0;si<selectId.length;si++){
				var node = zTree.getNodeByParam("id",selectId[si]);
				if(node!=null){
					zTree.checkNode(node, true, true);
				}
			}
		}
			var nameObj = $(setting.nameObj);
			var nameOffset = $(setting.nameObj).offset();
			var containerH = jQuery("#container").height();
			var left = nameOffset.left,top = nameOffset.top;
			/*if(containerH - nameOffset.top <300){
			top-=300;
		}*/
			var showDiv = $(setting.treediv).css({left:left + "px", top:top + nameObj.outerHeight() + "px"});
			if(jQuery.browser.msie){
				showDiv.show();
			}else{
				showDiv.slideDown("fast");
			}
			$(setting.tree).css("width",$(setting.nameObj).innerWidth()+"px");
			$(document).bind("click", onBodyDown);
		
		//treeSelect.isShow = true;
		//$("body").bind("mousedown", onBodyDown);
	}
	function changeTree(ts){
		var nameObj = $(setting.nameObj);
		var nameOffset = $(setting.nameObj).offset();
		var treeContainer = $(setting.treediv)
		var nameObjValue = nameObj.val();
		
		var zTree = $.fn.zTree.getZTreeObj(setting.treeId);
			//zTree.expandAll(true);
		var nodes = null;//zTree.transformToArray(zTree.getNodes());
		if(nameObjValue==""){
			nodes = zTree.getNodesByParam("isHidden", true, null);
			zTree.showNodes(nodes);
			zTree.checkAllNodes(false);
			/*for(var nodeIndex=0;nodeIndex<nodes.length;nodeIndex++){
				zTree.showNode(nodes[nodeIndex]);
				nodes[nodeIndex].nocheck = false;
				zTree.updateNode(nodes[nodeIndex]);
			}*/
				jQuery(setting.idObj).val("");
		}else{
				nodes = zTree.getNodesByParam("isHidden", false, null);
				zTree.hideNodes(nodes);
				var showNodes = zTree.getNodesByParamFuzzy('name',nameObjValue,null);
				for(var nodeIndex=0;nodeIndex<showNodes.length;nodeIndex++){
					showParentNode(zTree,showNodes[nodeIndex]);
					//zTree.checkNode(nodes[i], true, true);
				}
				//zTree.showNodes(showNodes);
		}
		if(ts.opts.exceptnullparent){
		    	hideNullClass();
		}
	}

	function showParentNode(zTree,node){
		zTree.showNode(node);
		//node.nocheck = false;
		//zTree.updateNode(node);
		if(node.getParentNode()!=null){
			showParentNode(zTree,node.getParentNode())
		}
	}

	function hideNullClass(){
		var zTree = $.fn.zTree.getZTreeObj(setting.treeId);
		var nodes = zTree.transformToArray(zTree.getNodes());
		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].isParent){
				var children = nodes[i].children;
				if(!(children&&children.length>0))
				zTree.hideNode(nodes[i]);
			}
		}
	}

	/*$.fn.showTree = function(){
		alert();
			showTree();
			}*/

	return this.each(function(){
		var $this = $(this);
		var ts = new treeselect(opts);

		function generateSelectTree(ts){
			if(!nameField){
				return;
			}
			var theSql = "";
			if("sql"==ts.opts.dataType){
				theSql = ts.opts.sql;
				theSql = theSql.replace( /&#039;/g, "'" );
				theSql = theSql.replace( /&#034;/g, "'" );
				theSql = theSql.replace( /&lt;/g, "<" );
				theSql = theSql.replace( /&gt;/g, ">" );
			}
			if(ts.opts.relateParam){
				var rpArr = ts.opts.relateParam.split(";");
				ts.opts.relateDown = rpArr[0];
				ts.opts.relateUp = rpArr[1];
			}
			if(ts.opts.relateDown){
				var dArr = ts.opts.relateDown.split(",");
				for(var di=0;di<dArr.length;di++){
					var htmlName = dArr[di];
					if(htmlName.indexOf(":")!=-1){
						htmlName = htmlName.split(":")[0];
					}
					if(ts.opts.relatePrefix){
						htmlName = ts.opts.relatePrefix+htmlName;
					}
					jQuery("#"+htmlName).val("");
					jQuery("#"+htmlName+"_name").val("");
				}
			}
			if(ts.opts.relateUp){
				var uArr = ts.opts.relateUp.split(",");
				for(var di=0;di<uArr.length;di++){
					var htmlName = uArr[di];
					if(ts.opts.relatePrefix){
						htmlName = ts.opts.relatePrefix+uArr[di];
					}
					var uV = jQuery("#"+htmlName).val();
					if(!uV){
						theSql = theSql.replace("%"+uArr[di]+"%","%&delparam&%");
						continue;
					}
					if("sql"==ts.opts.dataType){
						theSql = theSql.replace("%"+uArr[di]+"%",uV);
					}
				}
				var wherPatrn = /where/;
				var whereArr = wherPatrn.exec(theSql);
				var whereStr = theSql.substring(whereArr.index);
				var whereStrT = whereStr;
				var pArr = whereStr.split(" ");
				for(var pi=0;pi<pArr.length;pi++){
					var p = pArr[pi];
					if(p.indexOf("%&delparam&%")!=-1){
						whereStrT = whereStrT.replace(p,"");
					}
				}
				if(whereStrT.trim()=='where'){
					whereStrT = "";
				}
				theSql = theSql.replace(whereStr,whereStrT);
				//console.log(theSql);
			}
			if("sql"==ts.opts.dataType){
				if(ts.opts.sql){
					$.ajax({
				    url: 'treeSelectNodes',
				    type: 'post',
				    data: {selectTreeSql:theSql},
				    dataType: 'json',
				    async:false,
				    success: function(data){
				    	ts.opts.zNodes = data.nodes;
				    }
					});
				}
			}else if("url"==ts.opts.dataType){
				if(ts.opts.url){
					$.ajax({
				    url: ts.opts.url,
				    type: 'post',
				    data: {params:ts.opts.params},
				    dataType: 'json',
				    async:false,
				    success: function(data){
				    	ts.opts.zNodes = data.nodes;
				    }
					});
				}
			}else if("nodes"==ts.opts.dataType){
				//ts.opts.zNodes = ts.opts.nodes;
			}else{
				$.ajax({
			    url: 'treeSelectNodes',
			    type: 'post',
			    data: {tableName:ts.opts.tableName,treeId:ts.opts.treeId,treeName:ts.opts.treeName,parentId:ts.opts.parentId,order:ts.opts.order,filter:ts.opts.filter,
			    	   classTable:ts.opts.classTable,classTreeId:ts.opts.classTreeId,classTreeName:ts.opts.classTreeName,classOrder:ts.opts.classOrder,classFilter:ts.opts.classFilter},
			    dataType: 'json',
			    async:false,
			    success: function(data){
			    	ts.opts.zNodes = data.nodes;
			    }
				});
			}
			var nameFieldWeigth = $(setting.nameObj).innerWidth();
			var minWidth = nameFieldWeigth;
			if(ts.opts.minWidth){
				minWidth = ts.opts.minWidth;
			}
			//var treeDiv = $("<div></div>");
			if(jQuery("#"+nameField+"_treediv")){
				jQuery("#"+nameField+"_treediv").remove();
			}
			
		    $('body').append("<div id='"+nameField+"_treediv' class='menuContent' style='max-height:300px;border:1px solid;border-color: #A2BAC0 #99BBE8 #99BBE8 #A2BAC0;display:none; position: absolute;z-index:2000'><ul id='"+nameField+"_tree' class='ztree' style='min-width:"+minWidth+";max-height:295px;margin-top:2px; width:"+nameFieldWeigth+"px; '></ul></div>");
		    if(ts.opts.ifr){
		    	$('#'+nameField+'_treediv').resize(function(e,w,h,t,l,hide){
		    		bgiframe($('#'+nameField+'_treediv'),e,w,h,t,l,hide);
		    	});
		    }
		    if(ts.opts.optType=="single"){
		    	delete ztreesetting.check ;
		    	delete ztreesetting.callback.onCheck ;
		    	ztreesetting.callback.onClick = onClick;
		    }else{
		    	ztreesetting.check = {
		    			enable: true,
		    			chkStyle:'checkbox',
		    			chkboxType: {"Y":"ps", "N":"ps"}
		    		};
		    	if(ts.opts.chkboxType){
		    		ztreesetting.check.chkboxType = ts.opts.chkboxType;
		    	}
		    	ztreesetting.callback.onCheck = onCheck;
		    	delete ztreesetting.callback.onClick;
		    }
		    	//treeSelect.localSetting = localSetting;
		    	//makeList(treeSelect,treeSelect.zNodes);
		    	var zTree;
		    	if(!ts.opts.zNodes||(ts.opts.zNodes&&ts.opts.zNodes.length==0)){
		    		jQuery(setting.treediv).html("<div class='ztree' style='background: white;margin-top:2px;width:"+nameFieldWeigth+"px; '>无数据</div>");
		    		return;
		    	}else{
		    		zTree = $.fn.zTree.init($(setting.tree), ztreesetting, ts.opts.zNodes);
		    	}
		    	
		    	
		    	if(ts.opts.initSelect){
		    		var selectId = ts.opts.initSelect.split(",");
		    		for(var si=0;si<selectId.length;si++){
		    			var node = zTree.getNodeByParam("id",selectId[si]);
		    			if(node!=null){
		    				if(ts.opts.optType=="single"){
		    					zTree.selectNode(node, true);
		    					$(setting.nameObj).val(node.name.trim());
		    				}else{
		    					zTree.checkNode(node, true, true);
		    					var $tname = $(setting.nameObj).val();
		    					var newName = $tname ;
		    					if(!newName){
		    						newName = node.name.trim();
		    					}else{
		    						newName += ","+node.name.trim();
		    					}
		    					$(setting.nameObj).val(newName);
		    				}
			    		}
		    		}
		    	}
		    	if(ts.opts.disabledSelect){
		    		var selectDisabled = ts.opts.disabledSelect.split(",");
		    		for(var sd=0;sd<selectDisabled.length;sd++){
		    			var node = zTree.getNodeByParam("id",selectDisabled[sd]);
		    			if(node!=null){
		    				zTree.setChkDisabled(node, true);
			    		}
		    		}
		    	}
		    	if(ts.opts.exceptnullparent){
		    		hideNullClass();
		    	}
		    ts.opts.inited = true;
		    setting.opts = ts.opts ;
		}

		if(!ts.opts.lazy){
			generateSelectTree(ts);
		}

		if(ts.opts.showOpt=='click'){
			$this.unbind("click").bind("click",function(){
				var gen = !ts.opts.inited||ts.opts.rebuildByClick;
				if(gen){
					if(setting.opts.callback!=null&&typeof(setting.opts.callback.beforeBuild)=='function'){
						ts = setting.opts.callback.beforeBuild(ts,$(this));
					}
					generateSelectTree(ts);
				}
				showTree();
			});
		}else if(ts.opts.showOpt=='immediate'){
			var gen = !ts.opts.inited||ts.opts.rebuildByClick;
			if(gen){
				generateSelectTree(ts);
			}
			showTree();
		}
		
		/*$this.unbind("blur").bind("blur",function(){
			var idValue = $(setting.idObj).val();
			if(idValue!==0&&!idValue){
				$(setting.nameObj).val("");
				changeTree(ts);
			}
		});*/

		$this.keyup(function(){
			var inputValue = ts.opts.vl;
			var v = $(setting.nameObj).val();
			if(inputValue!=v){
				ts.opts.vl = v;
				changeTree(ts);
			}
		});
		//$("body").bind("mousedown", onBodyDown);
		
	});

	}

	$.fn.treeselectHide = function(hideNodes){
		var treeId = $(this).attr("id")+"_tree";
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		for(var nodeIndex in hideNodes){
			zTree.hideNode(hideNodes[nodeIndex]);
		}
		/*var nodes = zTree.transformToArray(zTree.getNodes());
		if(nameObjValue==""){
			for(var nodeIndex=0;nodeIndex<nodes.length;nodeIndex++){
				zTree.showNode(nodes[nodeIndex]);
				nodes[nodeIndex].nocheck = false;
				zTree.updateNode(nodes[nodeIndex]);
			}
				jQuery(setting.idObj).val("");
		}else{
				zTree.hideNodes(nodes);
				var showNodes = zTree.getNodesByParamFuzzy('name',nameObjValue,null);
				for(var nodeIndex=0;nodeIndex<showNodes.length;nodeIndex++){
					showParentNode(zTree,showNodes[nodeIndex]);
				}
				//zTree.showNodes(showNodes);
		}
		if(ts.opts.exceptnullparent){
		    	hideNullClass();
		}*/
	}

	var treeselect = function(opts) {
		this.opts = $.extend({
			inited:false,
			optType:"single",
			zNodes:null,
			sql:null,
			idId:null,
			relateParam:null,
			relateDown:null,
			relateUp:null,
			relatePrefix:null,
			exceptnullparent:false,
			rebuildByClick:false,
			lazy:false,
			selectParent:false,
			callback:null,
			showOpt:"click",
			minWidth:null,
			chkboxType:null,
			ifr:false
		}, opts);
	}

})(jQuery);