
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var radom = "${random}"
	jQuery(document).ready(function() { 
	refreshAccountTree();
	var edit_URL = "editAccount" , tableId = "account_gridtable" , width=660 , height = 470;
	jQuery("#"+tableId+"_addlocal").unbind( 'click' ).bind("click",function(){
		if(selectId==-1){
			selectId="";
		}
		var url = edit_URL+"?popup=true&acountTypeId="+acountTypeId+"&accountCode="+selectId+"&navTabId="+tableId;
		var winTitle="<s:text name='accountNew.title'/>";
		// alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, 'addAccount', winTitle, {mask:false,width:width,height:height,resizable:false,maxable:false});　
	});
	jQuery("#${random}accountTree").parent().unbind();
	jQuery("#${random}accountTree").unbind();
	jQuery("#${random}accountTree").parent().scroll(function(e){
		e.stopPropagation();
		return false;
		/* var thisTop = jQuery(this).scrollTop();
		alert(thisTop);
		jQuery(this).scrollTop(thisTop/2) */
	});
	jQuery("#${random}accountTree").parent().parent().unbind('keydown').bind('keydown',function(e){
		
		e.preventDefault();
	});
	jQuery("#${random}accountTree").unbind('keydown').bind('keydown',function(e){
		
		e.preventDefault();
	});
	jQuery("#${random}accountTree").trigger("click");
	jQuery("input[name=filter_EQS_acctCode]:first").trigger("click");
	jQuery("input[name=filter_EQS_acctCode]:first").focus(function(){
		alert();
	});
	dealKeyEvent();
	
  	});
	function refreshAccountTree(){
		jQuery.ajax({
		    url: 'makeAccountTreeWithCode',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		        alert(data);
		    },
		    success: function(data){
		        //alert(data.ztreeList);
		        setTimeout(function(){
		        	accountTree = jQuery.fn.zTree.init(jQuery("#${random}accountTree"), ztreesetting_account,data.nodes);
		        	var rootnode = accountTree.getNodeByParam("id","-1",null);
		        	accountTree.selectNode(rootnode);
		        	accountTree.expandAll(true);
					
		        },100);
		    }
		});
	}
	var ztreesetting_account = {
			view : {
				showLine : true,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick: checkAssistBox,
				onDblClick:dealAccountDbClick
			}
			
		};
	var selectId = "" , acountTypeId = "";
	function reloadAccountGrid(e, treeId, treeNode){
		var treeId = treeNode.id;
		if(treeId.indexOf("_")!=-1){
			var tpArr = treeId.split("_");
			acountTypeId = treeId;
			treeId = tpArr[tpArr.length-1];
		}else{
			acountTypeId = "";
		}
		selectId = treeId;
		urlString = "accountGridList";
		if(treeId!='-1'){
			urlString += "?filter_LIKES_acctCode="+treeId+"*";
		}
		//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#account_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function checkAssistBox(e, treeId, treeNode){
		var assistTypes = treeNode.assistTypes;
		if(assistTypes){
			var assistTypeArr = assistTypes.split(",");
			jQuery("input[name=typeName]").each(function(){
				var thisValue = jQuery(this).val();
				var flag = false;
				for(var ati=0;ati<assistTypeArr.length;ati++){
					if(thisValue.trim()==assistTypeArr[ati].trim()){
						flag = true;
					}
				}
				if(flag){
					jQuery(this).attr("checked","checked");
				}else{
					jQuery(this).removeAttr("checked");
				}
			});
		}
		//alert(treeId);
	}
	
	function propertyFilterSearchInAccountList(searchAreaId,gridId){
		var originalStr = jQuery("#account_assistTypes_id").val();
		var newStr = originalStr.trim();
		if(originalStr!='' && originalStr.charAt(0)!='*'){
			newStr = "*,"+originalStr+",*";
		}
		jQuery("#account_assistTypes_id").val(newStr);
		propertyFilterSearch(searchAreaId,gridId);
	}

	function refreshTree(data){
		refreshAccountTree();
		formCallBack(data);
	}
	
	function accountDel(tableId){
		var buttonBar ;
		if(typeof(jQuery("#"+tableId+"_div").attr("buttonBar"))!="undefined"){
			buttonBar = jQuery("#"+tableId+"_div").attr("buttonBar");
		}
		var fatherGrid = getParam('fatherGrid',buttonBar);
		var extraParam = getParam('extraParam',buttonBar);
		var selectNone = jQuery("#"+tableId+"_selectNone").text();
		var sid = jQuery("#"+tableId).jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#"+tableId).jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId="+tableId+"&oper=del";
		if(fatherGrid!=null&&fatherGrid!=""&&extraParam!=null&&extraParam!=""){
			var fatherGridId = jQuery("#"+fatherGrid).jqGrid('getGridParam','selarrrow');
			editUrl += "&"+extraParam+"="+fatherGridId;
		}
		editUrl = encodeURI(editUrl);
	    if(sid==null || sid.length ==0){
				alertMsg.error(selectNone);
				return;
		}else{
			// jQuery("#"+tableId).jqGrid('delGridRow',sid,{reloadAfterSubmit:false,left:300,top:150});
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, refreshTree, "json");
					
				}
			});
		}
	}
	function asdf(){
		jQuery("#${random}accountTree").trigger("click");
		dealKeyEvent();
	}
	function dealAccountDbClick(event, treeId, treeNode){
		var currentRowNum =  jQuery("#${random}accountTree").parent().find("input[name=currentRowNum]").val();
		var thisInput = jQuery("#${random}voucherCardTable").find("input[name=account]").eq(currentRowNum);
		thisInput.val(treeNode.acctFullname);
		thisInput.next().val(treeNode.acctId);
		thisInput.trigger("blur");
		$.pdialog.closeCurrentDiv("${random}accountTreeDialog");
		thisInput.parent().parent().trigger("click");
		var e = jQuery.Event("keydown");
		e.keyCode = 13;
		thisInput.trigger(e);
	}
	
	function dealKeyEvent(){
		jQuery("#${random}accountTreeContainer").unbind('keydown').bind('keydown',function(e){
			
			e.preventDefault();
			var keyValue = (e.keyCode) || (e.which) || (e.charCode);
			var accountTree = $.fn.zTree.getZTreeObj("${random}accountTree");
			if(keyValue==40){
				var selectNode_a = jQuery("#${random}accountTree").find("a.curSelectedNode");
				var thisId = selectNode_a.parent().attr("id");
				var thisIdArr = thisId.split("_");
				var thisIndex = thisIdArr[thisIdArr.length-1];
				thisId = thisId.replace("_"+thisIndex,"_"+(parseInt(thisIndex)+1))
				var selectNode = accountTree.getNodeByTId(thisId);
				accountTree.selectNode(selectNode);
				jQuery("#${random}accountTreeContainer").focus();
				checkAssistBox(null, null, selectNode)
				return false;
				/* var nodes = accountTree.getSelectedNodes();
				if(nodes.length>0){
					var thisIndex = accountTree.getNodeIndex(nodes[0]);
					var selectNode = accountTree.getNodeByParam("index", thisIndex+1, null);
					alert(accountTree.getNodeIndex(selectNode[0]));
					accountTree.selectNode(selectNode);

				} */
			}else if(keyValue==13){
				var accountTree = $.fn.zTree.getZTreeObj("${random}accountTree");
				var treeNodes = accountTree.getSelectedNodes();
				if(treeNodes.length>0){
					dealAccountDbClick(null,null,treeNodes[0]);
				}
			}
		});
		/* jQuery("#${random}accountTreeContainer").blur(function(){
			alert();
		}); */
	}
	function enterEvent(){
		/* var e = jQuery.Event("keydown");
		e.keyCode = 13;
		thisInput.trigger(e); */
	}
</script>
<div id="${random}accountTreeContainer" class="page" tabindex='-1'>
<input type="hidden" id="${random}accountTreeFocus"/>
	<div class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="account_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.acctCode'/>:
						<input type="text" name="filter_EQS_acctCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.acctname'/>:
						<input type="text" name="filter_LIKES_acctname"/>
					</label>&nbsp;&nbsp;
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='account.cnCode'/>:
						<input id="account_cnCode" type="text"  value="拼音/汉字/编码" size="14"
						class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#account_cnCodeToSend'))" 
						onfocus="clearInput(this,jQuery('#account_cnCodeToSend'))" onkeyDown="setTextColor(this)"/>
						<s:hidden id="account_cnCodeToSend" name="filter_EQS_cnCode"/>
					</label>&nbsp;&nbsp; --%>
				</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearchInAccountList('account_search_form','account_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="account_gridtable_addlocal" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a class="delbutton" onclick="accountDel('account_gridtable')" ><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="account_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<%-- <li><a id="" class="changebutton"  href="javaScript:asdf()"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
				<%-- <li><a class="changebutton"  href="javaScript:remapColumns()"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
		<div style="width:400px;float: left; display: block; overflow: auto;" layoutH=96>
			<input type="hidden" name="currentRowNum"/>
			<DIV id="${random}accountTree" class="ztree"></DIV>

		</div>
		<div style="width:180px;border:1px solid blue;float: right; display: block; overflow: auto;" layoutH=96>
			<div style="margin:5px">辅助核算:
				<div style="margin-left:20px">
				<s:iterator var="asst" value="assistTypes" status="it">
						<div style="margin:3px">
						<span style="width:30px;">
						<input type="checkbox" name="typeName" value="<s:property value='typeCode' escapeHtml='false' /> " disabled="disabled"/>
						</span>
						<label><s:property value="typeName"/>   </label>
						</div>
				</s:iterator>
				</div>
			</div>
		</div>
</div>
</div>