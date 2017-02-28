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
	width:85px;
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
					<a id="${random}_loanClose" class="closebutton" href="javaScript:loanClose()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_loanCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_loanForm">
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
				 报销单
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
						<label class="acvLable">单位:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">部门:</label>
						<input type="text" value="" />
					</span><br/>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">报销人:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">招行一卡通账号:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">费用承担部门:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">预算项目:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">邮件:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">手机:</label>
						<input type="text" value="" />
					</span>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable">办公电话:</label>
						<input type="text" value="" />
					</span>
					<br/>
					<span class="docInputArea">&nbsp;&nbsp;&nbsp;
						<label class="acvLable" style="vertical-align: top;">业务内容说明:</label>
						<textarea rows="2" cols="80"></textarea>
					</span>
				</div>
			</form>
			<div id="${random}_loanTable_div" class="zhGrid_div">
			<div class="tabs" currentIndex="0" eventType="click" >
				<div class="tabsHeader">
					<div class="tabsHeaderContent">
						<ul>
							<li><a href="#"><span>往返交通费 </span> </a></li>
							<li><a href="#"><span>目的地费用</span> </a></li>
							<li><a href="#"><span>出差补贴</span> </a></li>
						</ul>
					</div>
				</div>
				<div class="tabsContent" style="height: 250px;padding:0px">
					<div>
						<table class="table" width="100%" layoutH="330">
		<thead>
			<tr>
				<th rowspan="1" width="60" align="center" valign="middle">序号</th>
				<th rowspan="1" width="120" align="center">事由</th>
				<th rowspan="1" width="100" align="center" >出发日期</th>
				<th width="100" align="center">出发地点</th>
				<th width="100" align="center">到达日期</th>
				<th width="150" align="center">到达地点</th>
				<th width="80" align="center">交通工具</th>
				<th width="80" align="center">申请报销金额</th>
				<th width="80" align="center">预算余额</th>
			</tr>
		</thead>
		<tbody>
			<tr target="sid_user" rel="1">
				<td width="60">1</td>
				<td width="120" style="text-align: left">青岛会议</td>
				<td width="100"style="text-align: left">2016-10-01</td>
				<td style="text-align: left">北京 </td>
				<td style="text-align: left">2016-10-01</td>
				<td style="text-align: left">青岛</td>
				<td style="text-align: left">火车</td>
				<td style="text-align: right">300</td>
				<td style="text-align: right">5000</td>
			</tr>
			<tr target="sid_user" rel="2">
				<td width="60">2</td>
				<td width="120" style="text-align: left">四川会议</td>
				<td width="100"style="text-align: left">2016-10-21</td>
				<td style="text-align: left">北京</td>
				<td style="text-align: left">2016-10-21</td>
				<td style="text-align: left">四川</td>
				<td style="text-align: left">飞机</td>
				<td style="text-align: right">1500</td>
				<td style="text-align: right">3500</td>
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
			</tbody>
			</table>
					</div>
					<div></div>
					<div></div>
				</div>
				<div class="tabsFooter">
					<div class="tabsFooterContent"></div>
				</div>
			</div>
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
						<label>录入人:</label>
						<input class="lineInput" size="28" value="" />
					</li>
					<li style="float:left;width:300px;text-align: left">
						<label>最终修改人:</label>
						<input class="lineInput" size="28" value="" />
					</li>
					<li style="float:left;">
						<label>审批人:</label>
						<input class="lineInput" size="28" value="" />
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>