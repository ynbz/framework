
function FlowLogObj(){
	this.flowLogString="";
}
var g_EProcess = null;
var flowLogObj = new FlowLogObj();
var flowUrl="/eAdmWeb/servlet/EFlowClient";
var isEidtble = false;


function getEProcess() { 
	if (g_EProcess == null) {
		g_EProcess = new EFlowProcess(flowUrl, $("#processId").val(), $("#ESID").val());
	}

	return g_EProcess;
}

function initForm() {
	if (getEProcess().loadProcess()) {
		genActButton();
		genComment();
		flowLog();
		
	}
}

//生成操作按钮
function genActButton() {
	
	var eProcess = getEProcess();
	if (Tools.isStrNull(eProcess)) {
		return;
	}
									
	var btnTemplate = "<li><a href='#' onclick='{Script}'><i class='{Style}'></i>{Name}</a></li>" ;
	var ctrlVal ='' ;
	var type, name, styleClass, scriptStr;
	var btnHtml = "", returnValue = "";
	var btnCount = eProcess.actionList.length;

	for (var i = 0; i < btnCount; i++) {
		//首先调用isActHidden（操作是否隐藏）来进一步确认操作能够被显示
		var actId = Tools.getChildXmlNodeValue(eProcess.actionList[i], "id");
		var actUN = Tools.getChildXmlNodeValue(eProcess.actionList[i], "un");
		
		//if(isActHidden(actId, actUN)) continue;
		type = Tools.getChildXmlNodeValue(eProcess.actionList[i], "type");
		
		if (!Tools.isStrNull(type)) {
			if ("Hidden" != type && "Flag" != type) {
				btnHtml = btnTemplate;
				name = Tools.getChildXmlNodeValue(eProcess.actionList[i], "name");
				styleClass = Tools.getChildXmlNodeValue(eProcess.actionList[i], "Style");
				scriptStr = Tools.getChildXmlNodeValue(eProcess.actionList[i], "Script");
				
				btnHtml = btnHtml.replace(/{Name}/g, name);
				if(styleClass=='')
					btnHtml = btnHtml.replace(/{Style}/g, 'def');
				btnHtml = btnHtml.replace(/{Style}/g, styleClass);
				btnHtml = btnHtml.replace(/{Script}/g, scriptStr);
				returnValue += btnHtml;
			}else{
				name = Tools.getChildXmlNodeValue(eProcess.actionList[i], "un");
				if($('#ctrlInfo').length>0)
					$('#ctrlInfo').val($('#ctrlInfo').val()+','+name);
			}
		}
	}
	if(returnValue=="")returnValue="&nbsp;";
	$("#btList").html(returnValue);
}

//生成意见栏
function genComment() {
	var eProcess = getEProcess();
	if (Tools.isStrNull(eProcess)) {
		return;
	}
	var cmtTemplate = "<tr><td style='width:160px;' align='center' valign='middle'>{Title}</td><td>{Content}</td></tr>";
	var conTemplate = "<div>&nbsp;{CmtCon}</div><div class='yj-date'>&nbsp;{CmtSign}&nbsp;&nbsp;{CmtDate}</div>";
	var cmtCount = eProcess.commentColumns.length;

	var title,sbyjz="", hqyjz="";
	var sbCon = hqCon ="";
	var cmtHtml = "", conHtml= "";
	for (var i = 0; i < cmtCount; i++) {
		cmtHtml = cmtTemplate;
		title = eProcess.commentColumns[i].title;
		if(title.indexOf("/")>0)
		title=title.split("/")[1];
		cmtHtml = cmtHtml.replace(/{Title}/g, title);
			var comRecords = eProcess.commentColumns[i].commentRecords;
			if(comRecords!=null && comRecords.length>0){
			    for(var x=0;x< comRecords.length; x++){
			    	if(comRecords[x]){
				    	conHtml = conTemplate;
				    	conHtml = conHtml.replace(/{CmtSign}/g, comRecords[x].sign);
				    	conHtml = conHtml.replace(/{CmtDate}/g, comRecords[x].date);
				    	conHtml = conHtml.replace(/{CmtCon}/g, comRecords[x].content);
				    	sbCon  +=  conHtml
				    }
			    }
		    }
			sbyjz += cmtHtml.replace(/{Content}/g, sbCon);
			sbCon = hqCon ="";
			
	}
	$("#comment-content").html("<table class='table table-bordered'>"+sbyjz+"</table>");
}

function flowLog() {
	var eProcess = getEProcess();
	if (Tools.isStrNull(eProcess)) {
		return;
	}
	var logPrefix = "<table class='table table-bordered'>"
				   	+"<tr>" 
				    +"	<td class='td-name'>当前状态</td>"
					+"	<td>{Step}</td>"
				   	+"</tr>"
				    +"<tr>"
				    +"	<td class='td-name'>当前办理人</td>"
					+"	<td>{Editor}</td>"
					+"</tr>";
	var logSuffix   ="</table>";
	
	
	
	var actionPrefix="<tr>"
				    +"  <td class='td-name'>当前状态</td>"
				    +"  <td>"
				    +"     <table  width='100%'  border='0' cellspacing='0' cellpadding='0' style='border:0px;'>";
	var actionTemplate ="      <tr>" 
				       +"	       <td nowrap>{ActionTime}</td>"
					   +"	       <td nowrap>{ActionUser}</td>"
					   +"	       <td>{ActionName}{AcionTo}</td>"
				   	   +"      </tr>";
	var actionSuffix ="    </table>"
					 +"  </td>"
					 +"</tr>";
	
	
	
	var logHtml = "",editors = "",actions="";
	if(eProcess.getProcessSimpleInfo()){
		var action = Tools.getXmlNodeValueByStrOperation(eProcess.processSimpleInfo, "name");
		var editorList = Tools.getXmlData("ProcessInfo/Editors/U", eProcess.processSimpleInfo, true);
		var actionList = Tools.getXmlData("ProcessInfo/Actions/A", eProcess.processSimpleInfo, true);
			logPrefix   = logPrefix.replace(/{Step}/g, action);

			for (var i = 0; i < editorList.length; i++) {
				var editor = Tools.getChildXmlNodeValue(editorList[i], "n")+"/"+Tools.getChildXmlNodeValue(editorList[i], "ou");
				editors += (editor+"<br>");
			}	
			logPrefix   = logPrefix.replace(/{Step}/g, action).replace(/{Editor}/g, editors);
			
			for (var i = 0; i < actionList.length; i++) {
				logHtml = actionTemplate;
				var actionTime = Tools.getChildXmlNodeValue(actionList[i], "t");
				var actionName = Tools.getChildXmlNodeValue(actionList[i], "n");
				var actionUser = Tools.getChildXmlNodeValue(actionList[i], "u")+"/"+Tools.getChildXmlNodeValue(actionList[i], "o");
				var acionTo = Tools.getChildXmlNodeValue(actionList[i], "r");
				if(acionTo!="")acionTo = "("+acionTo+")";
				logHtml = logHtml.replace(/{ActionTime}/g, actionTime);
				logHtml = logHtml.replace(/{ActionName}/g, actionName);
				logHtml = logHtml.replace(/{ActionUser}/g, actionUser);
				logHtml = logHtml.replace(/{AcionTo}/g, acionTo);
				actions += logHtml;
			}
			actions = actionPrefix + actions + actionSuffix;			
		
	}else{
		ogPrefix   = logPrefix.replace(/{Step}/g, "&nbsp;").replace(/{Editor}/g, "&nbsp;");
	}
	
	flowLogObj.flowLogString = logPrefix + actions + logSuffix;
	$("#logInfo").html(flowLogObj.flowLogString);
}

//---------------------实现的接口--------------------------//
function eapi_getAppData(){
	var appData = "id="+$("#id").val() + ";fileType=" + $("#fileType").val();
	return appData;
}

function eapi_reload() {
	initForm();
}

function eapi_close(){
	window.close();
}

function eapi_showDebug(r){
	//alert(r);
}	

