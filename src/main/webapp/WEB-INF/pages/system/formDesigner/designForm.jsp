<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
var formDesignerDefine = {
		key:"formDesigner_${random}",
		main:{
			Build : 'getFormDesignerXml?formId=${formId}',
			Load :''
		},
		event:{
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var formXml = grid.func("GetFileXML", "");
					$.ajax({
			            url: 'saveFormDesignerXml',
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
var formDesignerTreeDefine = {
		key:"formDesignerTree_${random}",
		main:{
			ReadXML : 'getFormDesignerTreeXml?formId=${formId}',
		},
		event:{
			BeginDrag:function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				var EditType = grid.func("GetXMLProp", p1 + "\r\n EditType");
				formDesigner_${random}.func("BeginDrag", EditType + "\r\n" + p3 + "\r\n" + p2);
			}
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				grid.func("ExpandAll","");
				//grid.func("callfunc", "102 \r\n id=Table1;asForm=true");
			}
		}
}
supcanGridMap.put('formDesigner_${random}',formDesignerDefine); 
supcanGridMap.put('formDesignerTree_${random}',formDesignerTreeDefine); 
	jQuery(document).ready(function() {
		//insertTreeToDiv('${random}_formDesigner_tree', 'dragAble=leaf');
		insertTreeToDiv('${random}_formDesignerTree_container','formDesignerTree_${random}', 'dragAble=leaf');
		insertFormDesignerToDiv('${random}_formDesigner_container','formDesigner_${random}', 'UserBar=5001,5002; tip.5001=自定义功能1;tip.5002=自定义功能2; image.5001=report/icon1.bmp; image.5002=report/icon2.bmp; imageTransparentColor=#C0C0C0');
	
		jQuery("#saveFormDesigner_${random}").click(function(){
			var grid = eval("(formDesigner_${random})");
			var formXml = grid.func("GetFileXML", "");
			$.ajax({
	            url: 'saveFormDesignerXml',
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
		
		jQuery("#setDetailStyle_${random}").click(function(){
			
			/* var objId = formDesigner_${random}.func("GetCurrentIDs","");
			if(objId){
				var aaaa = formDesigner_${random}.func(objId+".URL","");
				alert(aaaa);
				return; 
			} */
			var fullHeight = jQuery("#container").innerHeight();
        	var fullWidth = jQuery("#container").innerWidth();
            var url = "designFormDetail?formId=${formId}";
    		var winTitle='设置明细表格式';
    		$.pdialog.open(url,'setDetailStyle',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    		stopPropagation();
		});
		jQuery("#setPrintReport_${random}").click(function(){
			var fullHeight = jQuery("#container").innerHeight();
        	var fullWidth = jQuery("#container").innerWidth();
            var url = "designPrintReport?formId=${formId}";
    		var winTitle='设置打印报表';
    		$.pdialog.open(url,'setPrintReport',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    		stopPropagation();
		});
	});
</script>
<div class="page" style="height: 100%;">
	<div class="pageContent" style="height: 100%;">
	<div class="panelBar">
			<ul class="toolBar">
				<li><a id="saveFormDesigner_${random}" class="savebutton"  href="javaScript:"><span>保存</span></a></li>
				<li><a id="setDetailStyle_${random}" class="initbutton"  href="javaScript:"><span>设置明细表格式</span></a></li>
				<li><a id="setPrintReport_${random}" class="initbutton"  href="javaScript:"><span>设置打印报表</span></a></li>
			</ul>
	</div>
	<div layoutH=28 style="overflow: hidden;">
		<table cols=2 width="100%" height="100%" cellpadding=3 border=0>
			<colgroup>
			 <col width="200"/>
			 <col/>
			</colgroup>
			<tr>
				<td><div id="${random}_formDesignerTree_container" style="height:100%;overflow: hidden;"></div></td>
				<td><div id="${random}_formDesigner_container" style="height:100%;overflow: hidden;"></div></td>
			</tr>
		</table>
	</div>
	</div>
</div>