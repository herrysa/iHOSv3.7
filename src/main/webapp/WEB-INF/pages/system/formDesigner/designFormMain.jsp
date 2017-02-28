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
	jQuery(document).ready(function(){
		insertTreeToDiv('${random}_formDesignerTree_container','formDesignerTree_${random}', 'dragAble=leaf');
		insertFormDesignerToDiv('${random}_formDesigner_container','formDesigner_${random}', 'UserBar=5001,5002; tip.5001=自定义功能1;tip.5002=自定义功能2; image.5001=report/icon1.bmp; image.5002=report/icon2.bmp; imageTransparentColor=#C0C0C0');
	
		jQuery("#setDetailStyle_${random}").click(function(){
			
			var objId = formDesigner_${random}.func("GetCurrentIDs","");
			if(objId){
				var aaaa = formDesigner_${random}.func(objId+".URL","");
				alert(aaaa);
				return; 
			}
			var fullHeight = jQuery("#container").innerHeight();
        	var fullWidth = jQuery("#container").innerWidth();
            var url = "setDetailStyle";
    		var winTitle='设置明细表格式';
    		$.pdialog.open(url,'setDetailStyle?formId=${formId}',winTitle, {mask:true,width : fullWidth,height : fullHeight});
		});
	});
</script>
<div class="page">
	<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click" id="formDesignerTabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="#"><span>主表单</span> </a></li>
					<li><a href="designFormDetail?formId=${formId }" class="j-ajax"><span>明细表</span> </a></li>
				</ul>
			</div>
		</div>
		<div id="formDesignerTabsContent" class="tabsContent" style="height: 100%;padding:0px">
			<div>
			
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
			<div></div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	<script>//tabResize();
	</script>
	</div>
</div>