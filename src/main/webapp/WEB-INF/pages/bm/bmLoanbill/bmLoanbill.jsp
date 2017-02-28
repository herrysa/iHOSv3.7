
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var bmLoanbillFormDefine = {
		key:"bmLoanbillForm_${random}",
		main:{
			Build : 'getFormDesignerXml?formId=loanbill',
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
supcanGridMap.put('bmLoanbillForm_${random}',bmLoanbillFormDefine);
	jQuery(document).ready(function() {
		insertFreeFormToDiv('bmLoanbillForm_${random}_container','bmLoanbillForm_${random}', '');
	});
</script>

<div class="page" style="height: 100%;">
	<div class="pageContent" style="height: 100%;">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="bmLoanbill_gridtable_add_c" class="addbutton" href="javaScript:" ><span></span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
				<li><a id="bmLoanbill_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="bmLoanbillForm_${random}_container" layoutH="60" class="grid-wrapdiv">
			
		</div>
	</div>
</div>