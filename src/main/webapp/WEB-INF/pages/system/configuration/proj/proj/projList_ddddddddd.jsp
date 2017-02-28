
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var projLayout;
	var projGridIdString="#proj_gridtable";
	
	jQuery(document).ready(function() { 
		var projFullSize = jQuery("#container").innerHeight()-60;
		jQuery("#proj_gridtable_container").css("height",projFullSize);
		$('#proj_gridtable_container').layout({
			applyDefaultStyles: false ,
			west__size : 280,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",//鼠标移到边框时，提示语
			onresize_end : function(paneName,element,state,options,layoutName){
				if("center" == paneName){
					gridResize(null,null,"proj_gridtable","single");
				}
			}
		});
		//refreshProjTree();
		jQuery("#projAccountList").load("projAccountList");
	});
	var ztreesetting_proj = {
			view : {
				showLine : true,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick: projTreeReload
			}
		};
	
	
	function projTreeReload(){
		alert();
		
	}
	
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="proj_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a></li>
				<li><a id="proj_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
				<li><a id="proj_gridtable_edit" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a></li>
			</ul>
		</div>
		<div id="proj_gridtable_container">
			<div id="proj_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<DIV id="projTree" class="ztree"></DIV>
			</div>
			<div id="proj_layout-center" class="pane ui-layout-center">
				<div id="proj_layout-south" class="pane ui-layout-south" style="display: block; overflow: auto; height:300px">
					<div class="pageFormContent">
						<div class="unit">
						<s:textfield key="proj.projId" required="true" cssClass="validate[required]"/>
						</div>
						<div class="unit">
						<s:textfield id="proj_beginDate" key="proj.beginDate" name="proj.beginDate" cssClass="				
						
						       "/>
						</div>
						<div class="unit">
						<s:textfield id="proj_cnCode" key="proj.cnCode" name="proj.cnCode" cssClass="				
						
						       "/>
						</div>
						<div class="unit">
						<s:textfield id="proj_copyCode" key="proj.copyCode" name="proj.copyCode" cssClass="				
						
						       "/>
						</div>	
					</div>
				</div>
				<div id="projAccount_layout-south" class="pane ui-layout-south"
							style="padding:2px" >
					<div id="projAccountList">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
		