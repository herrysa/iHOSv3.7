<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
var formDesignerDefine = {
		key:"formDesigner_${random}",
		main:{
			Build : 'getFormDetailDesignerXml?formId=${formId}',
			Load :''
		},
		event:{
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var formXml = grid.func("GetFileXML", "");
					$.ajax({
			            url: 'saveFormDetailDesignerXml',
			            type: 'post',
			            dataType: 'json',
			            data :{formId:"${formId}",formXml:formXml},
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
				//grid.func("callfunc", "102 \r\n id=Table1;asForm=true");
			}
		}
}
supcanGridMap.put('formDesigner_${random}',formDesignerDefine); 

	jQuery(document).ready(function() {
		//insertTreeToDiv('${random}_formDesigner_tree', 'dragAble=leaf');
		insertFormDesignerToDiv('${random}_formDesigner_container','formDesigner_${random}', 'UserBar=5001,5002; tip.5001=自定义功能1;tip.5002=自定义功能2; image.5001=report/icon1.bmp; image.5002=report/icon2.bmp; imageTransparentColor=#C0C0C0');
	
		jQuery("#saveFormDesigner_${random}").click(function(){
			var grid = eval("(formDesigner_${random})");
			var formXml = grid.func("GetFileXML", "");
			$.ajax({
	            url: 'saveFormDetailDesignerXml',
	            type: 'post',
	            dataType: 'json',
	            data :{formId:"${formId}",formXml:formXml},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
		});
	});
</script>
<div class="page" style="height: 100%;">
	<div class="pageContent" style="height: 100%;">
	<div class="panelBar">
			<ul class="toolBar">
				<li><a id="saveFormDesigner_${random}" class="savebutton"  href="javaScript:"><span>保存</span></a></li>
			</ul>
	</div>
	<div layoutH=28 style="overflow: hidden;">
		<div id="${random}_formDesigner_container" style="height:100%;overflow: hidden;"></div>
	</div>
	</div>
</div>