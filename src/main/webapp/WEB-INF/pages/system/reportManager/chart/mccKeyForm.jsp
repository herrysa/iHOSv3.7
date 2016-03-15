<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="mccKeyDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='mccKeyDetail.heading'/>"/>
    <script>
    $(document).ready(function() {
    	var checkValue=jQuery("#showPercentValues").is (":checked");
		if(checkValue&&${disShowValue}){
			jQuery("#disValueShow").append("<input type='checkbox' id='displayZhiValue' name='disShowValue' checked><span for='displayZhiValue'><fmt:message key='mccKey.disValue'/></span></input>");
		}else if(checkValue&&${!disShowValue}){
			jQuery("#disValueShow").append("<input type='checkbox' id='displayZhiValue' name='disShowValue'><span for='displayZhiValue'><fmt:message key='mccKey.disValue'/></span></input>");
		}else{
			jQuery("#displayZhiValue").remove();
			jQuery("span[for='displayZhiValue']").remove();
		}
    	var selectValue='${chartType}';
    	var content1 = document.getElementById("content1");
		var content2 = document.getElementById("content2");
		var content3 = document.getElementById("content3");
		var content4 = document.getElementById("content4");
		var content5 = document.getElementById("content5");
		var content6 = document.getElementById("content6");
		if(selectValue=="饼图"){
			content1.style.display ="block";
			content5.style.display ="block";
			content6.style.display ="none";
			content2.style.display ="none";
			content3.style.display ="none";
			content4.style.display ="none";
			
		}else if(selectValue=="柱形图"){
			  content2.style.display ="block";
			  content5.style.display ="block";
			  content6.style.display ="block";
			  content1.style.display ="none";
			  content3.style.display ="none";
			  content4.style.display ="none";
		}else if(selectValue=="折线图"){
			  content3.style.display ="block";
			  content5.style.display ="block";
			  content6.style.display ="block";
			  content2.style.display ="none";
			  content1.style.display ="none";
			  content4.style.display ="none";
		}else if(selectValue=="表盘"){
			  content4.style.display ="block";
			  content1.style.display ="none";
			  content5.style.display ="none";
			  content6.style.display ="none";
			  content3.style.display ="none";
			  content2.style.display ="none";
		}else if(selectValue=="雷达图"){
			  content3.style.display ="none";
			  content5.style.display ="block";
			  content6.style.display ="none";
			  content2.style.display ="none";
			  content1.style.display ="none";
			  content4.style.display ="none";
		}
		
		
		jQuery("#txtBackgroundColor").colorpicker({
		    fillcolor:true
		});
		jQuery("#v2").colorpicker({
		    fillcolor:true
		});
		jQuery("#bgColor").colorpicker({
		    fillcolor:true
		});
		jQuery("#bgColor2").colorpicker({
		    fillcolor:true
		});
		jQuery("#dlc").colorpicker({
		    fillcolor:true
		});
//     	ShowColorControl("txtBackgroundColor");
//     	ShowColorControl("v2");
//     	ShowColorControl("bgColor");
    	
//     	ShowColorControl("bgColor2");
//     	ShowColorControl("dlc");
    	
    	
    	jQuery('#${random}savelink').click(
				function() {
    				var disShowValue=jQuery("#displayZhiValue").is (":checked");
					jQuery("#mccKeyForm").attr("action","saveMccKey?popup=true&disShowValue="+disShowValue+"&navTabId="+'${navTabId}');
					jQuery("#mccKeyForm").submit();
				});
    });

 
    function vaildateNull(){
    	var v1=$("#v1").val();
    	var v2=$("#v2").val();
    	if(v1>0){
    		$("#v2").attr("disabled",false);
    	}else
    		$("#v2").attr("disabled",true);
    }
</script>
</head>
<style>
#mccKeyForm .pageFormContent label{
	width:80px;
}
#mccKeyForm .pageFormContent .unit{
	height:21px;
	margin-left:30px;
}

</style>
<body >
<div class="page">
		<div class="pageContent">
<form id="mccKeyForm" action="saveMccKey?popup=true" method="post" validate="false" cssClass="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
		<div class="pageFormContent" layoutH="56">
		<div class="unit">
			<s:if test="%{mccKey.mccKeyId!=null}">
				<s:textfield key="mccKey.mccKeyId" id="mccKeyId" required="true"  cssClass="required"   readonly="true"/>
			</s:if>
			<s:else>
				<s:textfield key="mccKey.mccKeyId" id="mccKeyId" required="true"  cssClass="required" onblur="checkId(this,'MccKey','MccKeyId')"/>
			</s:else>
	 		<s:select id="chartTypeSelect" key="mccKey.chartType" style="width:133px;" list='#{"饼图":"饼图","柱形图":"柱形图","折线图":"折线图","雷达图":"雷达图","表盘":"表盘"}' cssClass="required"  onchange="changeSelect()" />
	 		<s:textfield key="mccKey.ckey" required="false"   cssClass="required" />
	 	</div>
	 	<div class="unit">
 			<s:textfield key="mccKey.caption" required="false"    />
 			<s:textfield key="mccKey.subCaption" required="false"  />
	 		<s:textfield key="mccKey.keyField" required="false"    cssClass="required" />
 		</div>
 		<div class="unit">
	 		<s:textfield key="mccKey.numberPrefix" required="false"   />
	 		<s:textfield key="mccKey.numberSuffix" required="false" />
 		</div>
 		
 		<!-- 饼图专有 -->
 		<div id="content1" class="pageFormContent"  style="clear:both;border-collapse: collapse;display:block; ">
	 		<div class="unit">
		        <s:textfield key="mccKey.sheDingZhi" id="v1" required="false" title='设定饼图的数值在大于某值时会有特殊处理'  onblur="vaildateNull()" />
		        <s:textfield key="mccKey.color" id="v2" required="false" title='设定饼图在大于某值时显示的颜色'  disabled="true"  readonly="true"/>
		        <s:textfield key="mccKey.kanZhi"  required="false" title='设置坎值(饼图专用)-大于这个百分比的才可以显示 小于该值则放于其他内'  />
	        </div>
	 		<div class="unit" id="disValueShow">
	 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:checkbox id="showPercentValues" key="mccKey.showPercentValues" theme="simple" onclick="disValue()"></s:checkbox><fmt:message key="mccKey.showPercentValues" />
				<script type="text/javascript">
				function disValue(){
						var value=jQuery("#showPercentValues").is(":checked");
						if(value){
							jQuery("#disValueShow").append("<input type='checkbox' id='displayZhiValue' name='disShowValue' checked><span for='displayZhiValue'><fmt:message key='mccKey.disValue'/></span></input>");
						}else{
							jQuery("#displayZhiValue").remove();
							jQuery("span[for='displayZhiValue']").remove();
						}
					}
				</script>
	        </div>
	 	</div>
	 	<!-- 柱形专有 -->
	 	<div id="content2" class="pageFormContent"  style="display:none; ">
	 	
	 		
	 	
	 	</div>
	 	<!-- 折线专有 -->
		<div id="content3" class="pageFormContent"  style="clear:both;border-collapse: collapse;display:none; ">
	 		<div class="unit">
		 		<%-- <s:textfield key="mccKey.zheXianCount" required="false"   /> --%>
		 		<s:textfield key="mccKey.lineThickness" required="false"   />
		 		<s:select id="zxZhiBaseFontSize" key="mccKey.zxZhiBaseFontSize" style="width:133px;" list='#{"5":"5pt","6":"6pt","7":"7pt","8":"8pt","9":"9pt","10":"10pt","11":"11pt","12":"12pt","13":"13pt","14":"14pt"}'/>
		 		<s:select id="outCnvbaseFontSize" key="mccKey.outCnvbaseFontSize" style="width:133px;" list='#{"5":"5pt","6":"6pt","7":"7pt","8":"8pt","9":"9pt","10":"10pt","11":"11pt","12":"12pt","13":"13pt","14":"14pt","15":"15pt","16":"16pt"}'/>
 			</div>
	 		<div class="unit">
		 		<s:textfield key="mccKey.anchorRadius" required="false"   />
		 		<s:textfield key="mccKey.divLineColor" id="dlc" required="false"    readonly="true"/>
		 		<s:textfield key="mccKey.numvdivlines" required="false"   />
	 		</div>
	 		<div class="unit">
	 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 			<s:checkbox key="mccKey.divLineIsDashed" theme="simple"></s:checkbox><fmt:message key="mccKey.divLineIsDashed"/>
	 		</div>
	 		
	 	</div>
	 	
	 	
	 	<!-- 表盘专有 -->
 		<div id="content4" class="pageFormContent"  style="clear:both;border-collapse: collapse;display:none; ">
	 		<div class="unit">
		 		<%-- <s:textfield key="mccKey.Tagging" required="false"   />
		 		<s:textfield key="mccKey.TitieName" required="false"   /> --%>
		 		<s:textfield key="mccKey.gaugeOuterRadius" required="false"    title="(注:单位为像素)"/>
 			</div>
	 	</div>
	 	<!-- 折线和柱形图时显示 -->
	 	<div id="content6" class="pageFormContent"  style="clear:both;border-collapse: collapse;display:none; ">
	 		<div class="unit">
		 		<s:textfield key="mccKey.yAxisName" required="false"   />
			 	<s:textfield key="mccKey.xAxisName" required="false"   />
			 	<s:textfield key="mccKey.pieRadius" required="false"   />
	 		</div>
		 	<div class="unit">
		 		<s:textfield key="mccKey.bgColor2" id="bgColor2"  required="false" title='仅需要背景渐变色时选择'    readonly="true"/>
			 	<s:textfield key="mccKey.chartLeftMargin" required="false"   />
			 	<s:textfield key="mccKey.chartRightMargin" required="false"   />
		 	</div>
		 	<div class="unit">
			 	<s:textfield key="mccKey.labelStepNumber" required="false"   />
			 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 	<s:checkbox key="mccKey.rotateNames" theme="simple"></s:checkbox><fmt:message key="mccKey.rotateNames"/>&nbsp;&nbsp;&nbsp;&nbsp;
			 	<s:checkbox key="mccKey.formatNumberScale" theme="simple"></s:checkbox><fmt:message key="mccKey.formatNumberScale"/>&nbsp;&nbsp;&nbsp;&nbsp;
		 		<s:checkbox key="mccKey.formatNumber" id="formatNumber" theme="simple"></s:checkbox><fmt:message key="mccKey.formatNumber"/>&nbsp;&nbsp;&nbsp;&nbsp;
		 		<s:checkbox key="mccKey.showValues" theme="simple"></s:checkbox><fmt:message key="mccKey.showValues"/>&nbsp;&nbsp;&nbsp;&nbsp;
		 	</div>
	 	</div>
	 	<!-- 折线，柱形图，雷达图时显示 -->
 		<div id="content5" class="pageFormContent"  style="clear:both;border-collapse: collapse;display:block; ">
	 		<div class="unit">
		 		<s:select id="baseFontSize" key="mccKey.baseFontSize" style="width:133px;" list='#{"5":"5pt","6":"6pt","7":"7pt","8":"8pt","9":"9pt","10":"10pt","11":"11pt","12":"12pt","13":"13pt","14":"14pt","15":"15pt","16":"16pt"}'/>
		 		<s:select id="baseFont"  key="mccKey.baseFont" list="fontType" style="width:133px;" value="baseFont"/>
		 		<s:textfield key="mccKey.decimalPrecision" required="false"    title='例:0.00保留小数后两位'/>
 			</div>
	 		<div class="unit">
		 		<s:textfield key="mccKey.baseFontColor" id="txtBackgroundColor" required="false"    readonly="true"/>
		 		<s:textfield key="mccKey.label" required="false"   />
		 		<s:textfield key="mccKey.thousandSeparator" required="false"   />
	 		</div>
	 		<div class="unit">
		        <s:textfield key="mccKey.bgColor" id="bgColor"  required="false" title='图背景颜色'    readonly="true"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<s:checkbox id="c3d" key="mccKey.c3d" theme="simple"></s:checkbox><fmt:message key="mccKey.c3d"/>&nbsp;&nbsp;&nbsp;&nbsp;
	 		</div>
	 	</div>
	 	
 		<div class="unit">
 			<s:textarea key="mccKey.mysql" required="false" cols="90" rows="5"  cssClass="required" />
        </div>
			

      </div>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="${random}savelink">保存</button>
								</div>
							</div></li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>

			</form>
</div>
</div>

<script type="text/javascript">
	function changeSelect(){
		
		var selectValue=$("#chartTypeSelect").attr("value");
		//alert(selectValue);
		 var content1 = document.getElementById("content1");
		var content2 = document.getElementById("content2");
		var content3 = document.getElementById("content3");
		var content4 = document.getElementById("content4");
		var content5 = document.getElementById("content5");
		var content6 = document.getElementById("content6");
		if(selectValue=="饼图"){
			content1.style.display ="block";
			content5.style.display ="block";
			content6.style.display ="none";
			content2.style.display ="none";
			content3.style.display ="none";
			content4.style.display ="none";
			jQuery("#c3d").attr("disabled",false);
			
		}else if(selectValue=="柱形图"){
			  content2.style.display ="block";
			  content5.style.display ="block";
			  content6.style.display ="block";
			  content1.style.display ="none";
			  content3.style.display ="none";
			  content4.style.display ="none";
			  jQuery("#c3d").attr("disabled",false);
		}else if(selectValue=="折线图"){
			  content3.style.display ="block";
			  content5.style.display ="block";
			  content6.style.display ="block";
			  content2.style.display ="none";
			  content1.style.display ="none";
			  content4.style.display ="none";
			  jQuery("#c3d").attr("disabled",true);
		}else if(selectValue=="表盘"){
			  content4.style.display ="block";
			  content1.style.display ="none";
			  content5.style.display ="none";
			  content6.style.display ="none";
			  content3.style.display ="none";
			  content2.style.display ="none";
		}
		else if(selectValue=="雷达图"){
			  content3.style.display ="none";
			  content5.style.display ="block";
			  content6.style.display ="none";
			  content2.style.display ="none";
			  content1.style.display ="none";
			  content4.style.display ="none";
			  jQuery("#c3d").attr("disabled",true);
		}
	}
</script>
</body>