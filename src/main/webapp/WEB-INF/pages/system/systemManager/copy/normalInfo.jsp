<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" style="width:100%;height:800px;background-color:white;position: relative">
				<div style="position:relative;border:1px;border-style:solid;height:50px;width:40%;float:left" >
					<div style="position:absolute;top: -10px;left:10px;background-color:white; ">
					<input type="checkbox" value=""/>:
					<span style="font-size: 15px">结账汇率设置</span>
					</div>
					<div style="position:relative;left:50px;top:18px;">
						<span style="font-size: 15px">汇率方式</span>
						<input type="radio" name="tory"/>固定汇率
						<input type="radio" name="tory"/>浮动汇率
					</div>
				</div>
				<div style="border:1px;border-style:solid;height:500px;width:50%;float:left;margin-left:10px;" >
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">是否预记账</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">反方向录入银行的对账单</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">按年结转期间收支</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">基础资料及凭证编辑模块详细登记操作日志</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">年结后自动清除借贷相等的科目年初余额</span>
					</div>
				</div>
				<div style="position:absolute;border:1px;border-style:solid;height:430px;width:40%;float:top;top:80px;left:3px;" >
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">余额方向与科目余额方向一致</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">明细账日记账每一行均有余额</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">不显示没有发生额的会计期间的合计数</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">不显示发生额和余额为零的项目</span>
					</div>
						<br/>
					<div><input type="checkbox" value=""/>:
					<span style="font-size: 15px">自动隐藏已冻结的基础资料</span>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>





