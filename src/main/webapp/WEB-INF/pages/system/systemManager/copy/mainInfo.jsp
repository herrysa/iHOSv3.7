<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
	
</script>

<div class="page">
	<div class="pageContent">
		<form method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent">
			<br/>
			<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:<span style="margin-left:10px;font-size:15px;color:blue">${copy.org.orgCode}&nbsp;&nbsp;${copy.org.orgname }</span></span>
				</div>
				<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;套:<span style="margin-left:10px;font-size:15px;color:blue">${copy.copycode}&nbsp;&nbsp;${copy.copyname }</span></span>
				</div>
				<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">本&nbsp;&nbsp;位&nbsp;&nbsp;币:<span style="margin-left:10px;font-size:15px;color:blue">人民币</span></span>
				</div>
				<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">财务主管:<span style="margin-left:10px;font-size:15px;color:blue">${copy.cwmanager}</span></span>
				</div>
				<br/>
				<hr>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">会计期间</span>
				</div>
				<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">会计年度:<span style="margin-left:10px;font-size:15px;color:blue">${periodYear.periodYearCode}</span></span>
				</div>
				<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">期间个数:<span style="margin-left:10px;font-size:15px;color:blue">${periodYear.periodNum}</span></span>
				</div>
				<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">开始时间:<span style="margin-left:10px;font-size:15px;color:blue"><s:date format="yyyy-MM-dd" name="periodYear.beginDate"/></span></span>
				</div>
				<br/>
				<div class="unit">
				<span style="margin-left:128px;font-size:15px;">结束时间:<span style="margin-left:10px;font-size:15px;color:blue"><s:date format="yyyy-MM-dd" name="periodYear.endDate"/></span></span>
				</div>
			</div>
		</form>
	</div>
</div>





