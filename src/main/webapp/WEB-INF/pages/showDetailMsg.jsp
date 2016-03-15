<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<div class="page">
	<div class="pageContent">
		<form id="byLawForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return iframeCallback(this,uploadCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<div style="margin-left:10px;margin-right:10px;">
				${param.showValue}
				</div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="confirmDetailButton">确定</button>
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





