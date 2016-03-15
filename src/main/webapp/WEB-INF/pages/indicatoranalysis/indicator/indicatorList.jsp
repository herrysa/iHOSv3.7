
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() { 
		var indicatorFullSize = jQuery("#container").innerHeight()-32;
		jQuery("#indicator_container").css("height",indicatorFullSize);
		$('#indicator_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 320,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小"
		});
		initIndicatorTree();
  	});
	function initIndicatorTree(obj) {
		var url = "makeIndicatorTree";
		if(arguments.length==1 && obj.value){
			url += "?indicatorTypeId="+obj.value;
			if(jQuery("#indicatorForm").length>0){
				jQuery("#indicatorForm").html("");
			}
		}
		$.get(url, {"_" : $.now()}, function(data) {
			var indicatorTreeData = data.indicatorNodes;
			var indicatorTree = $.fn.zTree.init($("#indicatorTree"),ztreesetting_indicatorTree, indicatorTreeData);
			var nodes = indicatorTree.getNodes();
			indicatorTree.expandAll(true);
		});
	}
	var ztreesetting_indicatorTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick : onModuleClick
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
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};
	function onModuleClick(event, treeId, treeNode, clickFlag) { 
	   var id = treeNode.id;
	   var indicatorTypeId = jQuery("#indicator_indicatorType").val();
	   if(indicatorTypeId && indicatorTypeId !=id){
		    var pNode = treeNode.getParentNode();
		    var parentId = indicatorTypeId;
		    if(pNode){
		    	parentId = pNode.id;
		    }
			var url = "${ctx}/editIndicator?parentId="+parentId+"&id="+id;
			reloadIndicatorForm(url);
	   }
	}
	function addIndicator(){
		var indicatorTypeId = jQuery("#indicator_indicatorType").val();
		if(!indicatorTypeId){
			alertMsg.error("请选择指标类型!");
			return;
		}
		var indicatoTreeObj = $.fn.zTree.getZTreeObj("indicatorTree");
		var nodes = indicatoTreeObj.getSelectedNodes();
		var parentId = indicatorTypeId;
		if(nodes && nodes.length==1){
			var node = nodes[0];
			if(node.isParent){
				parentId = nodes[0].id;
			}else{
				var pNode = node.getParentNode();
				parentId = pNode.id;
			}
		}
		var url = "${ctx}/editIndicator?parentId="+parentId+"&indicatorTypeId="+indicatorTypeId;
		reloadIndicatorForm(url);
	}
	function reloadIndicatorForm(url){
		url=encodeURI(url);
		jQuery('#indicatorDetail').load(url, function(response, status, xhr) {
		  	
		}); 
	}
	function delIndicator(){
		var indicatorTreeObj = $.fn.zTree.getZTreeObj("indicatorTree");
		var indicatorTypeId = jQuery("#indicator_indicatorType").val();
		var nodes = indicatorTreeObj.getSelectedNodes();
		if(nodes.length==0){
			alertMsg.error("请选择指标");
		    return;
		}else if(nodes.length==1 && nodes[0].id===indicatorTypeId){
			alertMsg.error("请选择指标");
		    return;
		}else{
			var delNode = nodes[0];
			if(delNode.children){
				alertMsg.error("不能删除非末级指标");
			    return;
			}
			var delId = nodes[0].id;
			var url = "${ctx}/delIndicator?id="+delId;
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
							formCallBack(data);
							var node = indicatorTreeObj.getNodeByParam("id", delId, null);
							indicatorTreeObj.removeNode(node);
							jQuery('#indicatorDetail').html("");
						}
					);
				}
			});
		}
	}
	function saveIndicator(){
		if(jQuery("#indicatorForm").length>0){
			jQuery("#indicatorForm").submit();
		}else{
			return;
		}
		
	}
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addIndicator();" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:delIndicator();"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="savebutton"  href="javaScript:saveIndicator();"><span><s:text name="button.save" /></span></a>
				</li>
			</ul>
		</div>
		<div id="indicator_container" >
			<div id="indicator_layout-center" class="pane ui-layout-center" style="padding: 2px">
				<div id="indicatorDetail"></div>
			</div>
			<div id="indicator_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:4px;margin-bottom:2px;"><label>指标类型:</label>
					<s:select id="indicator_indicatorType" key="indicator.indicatorType" list="indicatorTypeList" listKey="code" listValue="name"
							emptyOption="false" theme="simple" onchange="initIndicatorTree(this)">
					</s:select>
				</div>
				<div id="indicatorTree" class="ztree"></div>
			</div>
		</div>
	</div>
</div>