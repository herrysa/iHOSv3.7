<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			var selectDate = jQuery("#nurseDayScore_scoredate_form").val();
			$.ajax({
			    url: 'checkPeriod',
			    type: 'post',
			    data:{selectDate:selectDate},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        //jQuery('#name').attr("value",data.responseText);
			    },
			    success: function(data){
					if(data.statusCode==300){
						alertMsg.error(data.message);
						jQuery(obj).val("");
						return;
					}else{
						var oldCheckPeriod = jQuery("#nurseDayScore_checkperiod").val();
						var newCheckPeriod = data.message;
						if(currentCheckPeriod!=newCheckPeriod){
							alertMsg.error("所选日期不再本核算期间内!");
							return;
						}else{
							jQuery("#nurseDayScoreForm").attr("action","saveNurseDayScore?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
							jQuery("#nurseDayScoreForm").submit();
						}
						
						/* if(oldCheckPeriod != newCheckPeriod){
							
							
						} */
					}
				}
			});
		});
	});
	var currentCheckPeriod = '${checkPeriod}';
	function checkPeriod(obj){
		var selectDate = jQuery(obj).val();
		if(selectDate==""){
			return;
		}
		//alert(jQuery("#nurseDayScore_checkperiod").val());
		$.ajax({
		    url: 'checkPeriod',
		    type: 'post',
		    data:{selectDate:selectDate},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
				if(data.statusCode==300){
					alertMsg.error(data.message);
					jQuery(obj).val("");
					return;
				}else{
					var oldCheckPeriod = jQuery("#nurseDayScore_checkperiod").val();
					var newCheckPeriod = data.message;
					if(currentCheckPeriod!=newCheckPeriod){
						alertMsg.error("所选日期不再本核算期间内!");
						return;
					}
					/* if(currentCheckPeriod>newCheckPeriod){
						alertMsg.error("所选日期不能小于当前核算期间!");
						return;
					}else if(currentCheckPeriod < newCheckPeriod){
						alertMsg.confirm("所选日期超出核算期间'"+jQuery("#nurseDayScore_checkperiod").val()+"',确认继续操作？", {
							okCall: function(){
								//jQuery("#nurseDayScore_checkperiod").val(newCheckPeriod);
								
							},
							cancelCall:function(){
								jQuery(obj).val("");
							}
						});
					} */
					
					/* if(oldCheckPeriod != newCheckPeriod){
						
						
					} */
				}
			}
		});
		
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="nurseDayScoreForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="nurseDayScore.dayScoreID" required="true" cssClass="validate[required]"/>
				</div>
				
				<%-- <div class="unit">
				<s:textfield id="nurseDayScore_billno" key="nurseDayScore.billno" name="nurseDayScore.billno" cssClass="" readonly="true"/>
				</div> --%>
				<div class="unit">
				<s:textfield id="nurseDayScore_checkperiod_form" key="nurseDayScore.checkperiod" name="nurseDayScore.checkperiod" cssClass="" readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield id="nurseDayScore_scoredate_form" key="nurseDayScore.scoredate" name="nurseDayScore.scoredate" cssClass="required" readonly="true" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onchange="checkPeriod(this)"/>
				</div>
				<div class="unit">
				<s:textfield id="nurseDayScore.operatorname" key="nurseDayScore.operatorname" name="nurseDayScore.operatorname" cssClass="" readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield id="nurseDayScore.operatedate" key="nurseDayScore.operatedate" name="nurseDayScore.operatedate" cssClass="" readonly="true"/>
				</div>
				<div class="unit">
				<label><s:text name="nurseDayScore.groupname"></s:text>:</label>
				<span style="float: left; width: 140px;line-height:21px"> 
				<s:select list="updataDepts" listKey="departmentId" listValue="name" key="nurseDayScore.groupid.departmentId" theme="simple"></s:select>
				</span>
				</div>
				<div class="unit">
				<label>状态标记:</label><span style="float: left; width: 140px;line-height:21px">新建</span>
				</div>
				<div class="unit">
				<s:textarea id="nurseDayScore.remark" key="nurseDayScore.remark" name="nurseDayScore.remark" cssClass="" cols="40" rows="3"/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





