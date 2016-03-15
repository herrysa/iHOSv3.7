<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		
	});
	function confirmAsyncHrData(){
		var hisTime = jQuery("#asyncHrData_hisTime").val();
		if(hisTime){
			alertMsg.confirm("确认将组织机构和人员的数据同步至该时间点吗？", {
   				okCall : function() {
   					$.post("confirmAsyncHrData?hisTime="+hisTime,function(data) {
   						formCallBack(data);
   					});
   				}
   			});
		}else{
			alertMsg.error("请输入历史时间。");
			return;
		}
	}
</script>
<style type="text/css">
	.asyncDataTable{
		border:solid 0px black;
		position:relative;
		left:200px;
		margin-top:100px
	}
	.asyncDataTable td{
		padding:20px
	}
</style>
</head>

<div class="page" >
	<div layoutH="30" style="background:#eef4f5">
		<table class="asyncDataTable">
			<tr>
				<td>
					<label>
						<s:text name='历史时间点'/>:
						<input id="asyncHrData_hisTime" type="text" class="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</label>
				</td>
				<td>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="confirmAsyncHrData()"><s:text name='同步'/></button>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>





