<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
</script>
<style>
.docInputArea {
   float:none;
   white-space:nowrap;
   display:inline-block;
   margin-top:2px;
   margin-bottom:2px;
   width:250px
}
.acvLable{
	display:inline-block;
	width:80px;
	text-align: right
}
</style>
<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="${random}_loan_toolBar">
				<li>
					<a id="${random}_loan_new" class="addbutton" href="javaScript:"><span>新单</span> </a>
				</li>
				<li>
					<a id="${random}_loanSave" class="savebutton" href="javaScript:saveloan()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_loanAudit" class="checkbutton" href="javaScript:auditloan()"><span><s:text name="button.check" /></span> </a>
				</li>
				<li>
					<a id="${random}_loanAntiAudit" class="delallbutton" href="javaScript:antiAuditMainIn()"><span><s:text name="button.cancelCheck" /></span> </a>
				</li>
				<li>
					<a id="${random}_loanPrint" class="printbutton" href="javaScript:printloan()"><span>打印</span> </a>
				</li>
				<li>
					<a id="${random}_loanPrintSet" class="searchbutton" href="javaScript:printSetloan()"><span>打印预览</span> </a>
				</li>
				<li>
					<a id="${random}_loanPrintSet" class="settlebutton" href="javaScript:printSetloan()"><span>借款凭证</span> </a>
				</li>
				<li>
					<a id="${random}_loanPrintSet" class="settlebutton" href="javaScript:printSetloan()"><span>报账凭证</span> </a>
				</li>
				<li>
					<a id="${random}_loanClose" class="closebutton" href="javaScript:loanClose()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_loanCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_loanForm">
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
				    借款单
					<hr style="color:rgb(24, 127, 207);margin-top:0px"/>
					<hr style="color:rgb(24, 127, 207);margin-top:-11px"/>
				</div>
				<div style="border:0;width:100%;margin-top:-10px;table-layout:fixed;">
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">申请单号:</label>
							<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">申请日期:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">项目名称:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">附件张数:</label>
						<input type="text" value="" />
					</span><br/>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">申请人:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">经费开支部门:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">经费归口部门:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">付款单号:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">借款凭证号:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">报账凭证号:</label>
						<input type="text" value="" />
					</span><br/>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">备注:</label>
						<input type="text" size="80" value="" />
					</span>
				</div>
			</form>
			<div id="${random}_loanTable_div" class="zhGrid_div">
				<table class="table" width="100%" layoutH="258">
		<thead>
			<tr>
				<th rowspan="1" width="60" align="center" valign="middle">序号</th>
				<th rowspan="1" width="120" align="center">用途</th>
				<th rowspan="1" width="100" align="center" >预算科目</th>
				<th width="100" align="center">总预算</th>
				<th width="100" align="center">可用</th>
				<th width="150" align="center">申请</th>
				<th width="80" align="center">金额</th>
				<th rowspan="1" width="150" align="center">支付方式</th>
				<th rowspan="1" width="80" align="center">票据编号</th>
			</tr>
		</thead>
		<tbody>
			<tr target="sid_user" rel="1">
				<td width="60">1</td>
				<td width="120" style="text-align: left">差旅</td>
				<td width="100"style="text-align: left">差旅费</td>
				<td style="text-align: right">50000</td>
				<td style="text-align: right">40000</td>
				<td style="text-align: right">20000</td>
				<td style="text-align: right">20000</td>
				<td width="150" style="text-align: left">现金</td>
				<td width="80"></td>
			</tr>
			<tr target="sid_user" rel="2">
				<td width="60">2</td>
				<td width="120" style="text-align: left">会议</td>
				<td width="100"style="text-align: left">会议费</td>
				<td style="text-align: right">20000</td>
				<td style="text-align: right">15000</td>
				<td style="text-align: right">5000</td>
				<td style="text-align: right">10000</td>
				<td width="150" style="text-align: left">支票</td>
				<td width="80"  style="text-align: left">1234</td>
			</tr>
			<tr target="sid_user" rel="3">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="4">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="5">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="6">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="7">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="8">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="9">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="10">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			</tbody>
			</table>
			</div>
			<div style="height:26px" id="${random}_loanForm_foot">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<li style="float:left;width:300px;text-align: left" >
						<label>金额大写:</label>
						<input class="lineInput" size="28" value="" />
					</li>
					<li style="float:left;width:300px;text-align: left">
						<label>金额合计:</label>
						<input class="lineInput" size="28" value="" />
					</li>
				</ul>
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<li style="float:left;width:300px;text-align: left" >
						<label>科室审核:</label>
						<input class="lineInput" size="28" value="" />
					</li>
					<li style="float:left;width:300px;text-align: left">
						<label>财务审核:</label>
						<input class="lineInput" size="28" value="" />
					</li>
					<li style="float:left;">
						<label>出纳:</label>
						<input class="lineInput" size="28" value="" />
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>