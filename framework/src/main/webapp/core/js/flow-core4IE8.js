

var g_EProcess;
var g_SelectSendObj = "";
var g_CommentObj = "";

/*--------------用于调试的函数--------------------*/
function e_showDebug(r) {
	try {
		eapi_showDebug(r);
	} catch(err) {
	}
}

/*-------------- 类定义 --------------------------*/
//工具类 
var Tools = 
{
	eSessionId:"ESessionId",
	xmlData:"",
	xmlDomObj:null,
	isStrNull:function(str) {
		if(str==null
			||str==undefined
			||""==str
			||"null"==str
			||"undefined"==str
			){
			return true;
		}else{
			return false;
		}
	},
	//判断一个字符串是否代表真
	isStrTrue:function(str) {
		if(Tools.isStrNull(str)) return false;
		var s = trim(str);
		if(!isNaN(s)) {
			if(s == "0") return false;
			return true;
		}
		s = str.toLowerCase();
		if(s == "y" || s == "true" || s == "yes") return true;
		return false;
	},
	//escape编码
	escapeX:function(str)
	{
		var r = escape(str); 
		r = r.replace(/[*]/g, '%2A');
		r = r.replace(/[%]/g, '*'); 
		r = r.replace(/[+]/g, '*2B'); 
		return "S."+r; 
	},

	//xmlhttp调用
	act:function(url,command,arg)
	{
		if(Tools.isStrNull(url)){	
			alert("请指定url!");
			return;
		}
		if(Tools.isStrNull(command)){
			alert("请指定命令!");	
			return;
		}
		var rand = Math.floor(Math.random()*10000+1)
		var params = "command="+command + "&" + arg+"&rand="+rand;
		var retval;
		$.support.cors = true;
		$.ajax({ 
			url:url, 
			type:'post', //数据发送方式 
			async:false,
			data:params, //要传递的数据 
			error: function(XMLHttpRequest, textStatus, errorThrown){ //失败 
				alert("系统错误，请重试！"); 
			}, 
			success: function(msg){ //成功 
			retval=msg;
			} 
			}); 
		return retval;
	},
	actEx:function(url, command) {
		var paraStr = "";
		var first = true;
		for(var i = 2; i < arguments.length; i++) {
			var a = arguments[i];
        		var p = a.indexOf("=");
        		if(p > 0) {
        			var pName = a.substring(0, p);
        			var pValue = a.substring(p + 1, a.length);
        			if(first) {
        				paraStr = pName + "=" + Tools.escapeX(pValue);
        				first = false;
        			} else {
        				paraStr = paraStr + "&" + pName + "=" + Tools.escapeX(pValue);
        			}
        		}
    	}
    	return Tools.act(url, command, paraStr);
	},
	//设置Cookie
	setCookie:function(name, value){
		var expiration_date = new Date ();
		expiration_date.setYear (expiration_date . getYear () + 1);
		expiration_date = expiration_date . toGMTString ();
		var cookie_string = name + "=" + value +
			"; expires=" + expiration_date+";path=/";
		if(name!="")
			document.cookie = cookie_string;
	},
	//删除Cookie
	deleteCookie:function(name,path,domain) {
		if (getCookie(name)){
			document.cookie = name + "=" +
			  ((path) ? "; path=" + path : "") +
			  ((domain) ? "; domain=" + domain : "") +
			  "; expires=Thu, 01-Jan-70 00:00:01 GMT";
		}
	},
	//取出Cookie
	getCookie:function(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg) {
		  return Tools.getCookieVal(j);
		}
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0) break;
		}
		return null;
	},
	getCookieVal:function (offset) {
		var endstr = document.cookie.indexOf (";", offset);
		if (endstr == -1) {
			endstr = document.cookie.length;
		}
		return unescape(document.cookie.substring(offset, endstr));
	},
	//判断一个字符串是否为真
	isTrueStr:function(str) {
		if(Tools.isStrNull(str)) return false;
		var s = str.toLowerCase();
		if(s == "y" || s == "1" || s == "true") return true;
		return false;
	},
	//判断是否操作成功
	isSuccess:function(returnInfo){
		if(Tools.isStrNull(returnInfo)){
			alert("操作失败！\n未得到返回数据!");
			return false;
		}
		var str = Tools.getXmlNodeValueByStrOperation(returnInfo, "Success");
		if(!Tools.isTrueStr(str)){
			var errorInfo = Tools.getXmlNodeValueByStrOperation(returnInfo, "ErrMsg");
			alert("操作失败！\n" + errorInfo);
			return false;
		}
		return true;
	},

	//通过字符串操作的方式获取xml中某第一层节点的内容
	getSingleXmlNodeTextByStrOperation : function(xmlData, nodeName) {
		var str1 = "<" + nodeName + ">";
		var str2 = "</" + nodeName + ">";
		var p1 = xmlData.indexOf(str1);
		if(p1 == -1) return "";
		
		var l = str1.length;
		p1 = p1 + l;	
		var p2 = xmlData.indexOf(str2, p1);
		if(p2 == -1) return "";
		return xmlData.substring(p1, p2);
	},
	
	//通过字符串操作的方式获取xml中节点的内容
	getXmlNodeValueByStrOperation : function(xmlData, nodeName) {
		var rStr = Tools.getSingleXmlNodeTextByStrOperation(xmlData, nodeName);
		for(var i = 2; i < arguments.length; i++) {
		var a = arguments[i];
			rStr = Tools.getSingleXmlNodeTextByStrOperation(rStr, a);
    }
    return rStr;
	},
	
	//得到子节点列表
	getChildXmlNodeList : function(xmlNodeObj, childNodeName) {
		result = new Array();
		if(xmlNodeObj == null) return result;
		
		try {
			result = xmlNodeObj.selectNodes(childNodeName);
		} catch(e) {
		}
		
		return result;
	},
	
	//得到子节点的值
	getChildXmlNodeValue : function(xmlNodeObj,childNodeName) {
		if(xmlNodeObj == null)return;
		if(Tools.isStrNull(childNodeName))return;
		var node = xmlNodeObj.selectSingleNode(childNodeName);
		if(node == null) return "";
		return node.text;
	},
	//得到节点列表或节点的文本
	getXmlData:function(xPath,xmlData,ele,isPrefix){
		var reValue;
		var prefix="";
		if(!isPrefix)prefix="/Return/";
		try{
			if(Tools.xmlDomObj==null)
				Tools.xmlDomObj = new ActiveXObject("Microsoft.XMLDOM");
			if(!Tools.isStrNull(xmlData))Tools.xmlData =xmlData;
			Tools.xmlDomObj.loadXML(Tools.xmlData); 
			if(Tools.isStrNull(ele)){
				reValue = Tools.xmlDomObj.selectSingleNode(prefix+xPath).text;
		    }else{
				reValue = Tools.xmlDomObj.selectNodes(prefix+xPath);
			}
		}catch(e){
			reValue = "";
			//alert("操作失败！\n未找到元素!");
		}
		return reValue;
	},
	//得到节点列表
	getXmlNodeList:function(xPath, xmlData, isPrefix){
		var result;
		var prefix="";
		if(!isPrefix) prefix="/Return/";
		try {
			if(Tools.xmlDomObj==null) Tools.xmlDomObj = new ActiveXObject("Microsoft.XMLDOM");			
			if(!Tools.isStrNull(xmlData)) Tools.xmlData =xmlData;
			
			Tools.xmlDomObj.loadXML(Tools.xmlData); 
			result = reValue = Tools.xmlDomObj.selectNodes(prefix + xPath);
		} catch(e) {
			result = new Array();
		}
		
		return result;
	}
};

//流程类
function EFlow(flowEngineUrl, userUN, sysUN) {
	this.flowEngineUrl = flowEngineUrl;			//流程引擎url
	this.userUN = userUN;						//用户名
	this.sysUN = sysUN;							//系统名
	this.sessionId = "";
	this.ctrlInfo = "";
	
	this.newProcessId = "";
	this.newStepCode = "";
	this.newStepName = "";

	this.getSysCtrlInfo = getSysCtrlInfo;		//得到系统控制信息
	this.getTodoList = getTodoList;				//得到待办事项列表
	this.launchFlowProcess = launchFlowProcess;	//发起流程
}

//意见栏类
function EFlowCommentColumn(un, title, style, commentRecodeNodes) {
	this.un = un;
	this.title = title;
	this.style = style;
	
	this.commentRecords = new Array();
	var n = commentRecodeNodes.length;
	var i = 0;
	for(i = 0; i < n; i++) {
		var node = commentRecodeNodes[i];
		var content = Tools.getChildXmlNodeValue(node, "txt");
		var sign = Tools.getChildXmlNodeValue(node, "sign");
		var date = Tools.getChildXmlNodeValue(node, "date");
		var cr = new EFlowCommentRecord(content, sign, date);
		this.commentRecords[i] = cr;
	}
}

//意见记录类
function EFlowCommentRecord(content, sign, date) {
	this.content = content;
	this.date = date;
	this.sign = sign;
}

//流程过程类
function EFlowProcess(flowEngineUrl,processId,sessionId) {   
	this.actionList;
	this.flowEngineUrl = flowEngineUrl;
	if(Tools.isStrNull(sessionId)){
		this.sessionId = Tools.getCookie(Tools.eSessionId);
	}else{
		this.sessionId = sessionId;
	}
	this.processId = processId;
	this.processCtrlStr = "";
	this.processSimpleInfo = "";
	this.editable = false;
	this.taskId = "";
	this.commentColumns = new Array();
	
	this.currentAction = null;

	this.getProcessInfo = getProcessInfo;		//流程信息
	this.getAllComment = getAllComment;			//获取意见信息
	this.getMyComment = getMyComment;			//得到用户当前的意见信息
	this.loadProcess = loadProcess;				//加载进程
	this.parseProcessCtrl = parseProcessCtrl; //解析流程控制信息
	this.validateAct = validateAct;       //执行操作确认Script
	this.visitEFlowPreAct = visitEFlowPreAct; //在操作前访问EFlow
	this.visitEFlowPostAct = visitEFlowPostAct; //在操作后访问EFlow
	this.executeAction = executeAction;   //执行一个操作
	this.send = send;  //发送
	this.autoSend = autoSend; //自动发送
	this.comment = comment; //填写意见
	this.agentComment = agentComment;//代填意见
	this.takebackAuto = takebackAuto; //自动收回
	this.takebackSel = takebackSel; //选人收回
	this.goBack = goBack;//收回(重新办理)
	this.resend = resend; //再次发送
	this.handleActResult = handleActResult; //处理操作结果
	this.confirmMsg = confirmMsg; //消息确认
	this.setProcessTitle = setProcessTitle;//设置流程过程标题
	
	this.getProcessSimpleInfo = getProcessSimpleInfo; //获取简单流程记录信息

	this.isValueExist = isValueExist;           //判断指定元素的值是否存在
	this.isActThere = isActThere;               //判断操作列表中是否包含操作
	this.getFindValueById = getFindValueById;   //根据id得到Action中元素的值
	this.getActionType = getActionType;			//根据id得到Action的Type
	this.getActionCode = getActionCode;			//根据id得到Action的Code
	this.getActionName = getActionName;			//根据id得到Action的Name
	this.getActionScript = getActionScript;		//根据id得到Action的Script
	this.getActionVScript = getActionVScript;		//根据id得到Action的VScript(确认Script)
	this.getActionStyle = getActionStyle;		//根据id得到Action的Style
	this.getActionScript_O = getActionScript_O;  //根据id得到Action的本来的Script
	
	this.getActionPara = getActionPara;
}

function setProcessTitle(title) {
	var r = Tools.actEx(this.flowEngineUrl,"SetProcessTitle",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"title=" + title);
	if(!Tools.isSuccess(r)) return false;
	return true;
}

function getProcessSimpleInfo() {
		var newId=this.processId;
		if(newId.indexOf("@")>0){
			newId=newId.split("@")[1]
		}
		var r = Tools.actEx(this.flowEngineUrl,"GetProcessSimpleInfo",
		"esId=" + this.sessionId,
		"fpId=" + newId);
		
		if(!Tools.isSuccess(r)) return false;
		this.processSimpleInfo = r;
		return true;
}

/*-------------- 流程类方法 --------------------------*/
//处理操作结果（关闭、重新加载、激发其他操作……）
function handleActResult(result,tab) {
	if(!Tools.isSuccess(result)) return false;
	var postMsg = Tools.getXmlNodeValueByStrOperation(result, "postMsg");
	if(!Tools.isStrNull(postMsg)) alert(postMsg);
	
	var act = Tools.getSingleXmlNodeTextByStrOperation(result, "type");
	if("Close" == act) {
		eapi_close();
	} else if("Reload" == act) {
		eapi_reload();
	} else if("AnotherAct" == act) {
		var actId = Tools.getSingleXmlNodeTextByStrOperation(result, "actId");
		var script = this.getActionScript(anotherActId);
		if(!Tools.isStrNull(script)) {
			eval(script);
		}
	}	
	
	/*if(tab == 4 || tab == 5){
		refreshComment(tab);
	}
	刷新OA端的代办事项
	if(document.getElementById('pTarget').value!=null && document.getElementById('pTarget').value!=""){
		try{
			var msgSender	= new ActiveXObject("YHHTMLMessager.YHHTMLMessageSender.1");
			msgSender.SendMessage(document.getElementById('pTarget').value, "htTodoFlush");
		}catch(e){}
	}*/
}

//再次发送
function resend(id) {
	if(!this.validateAct(id)) return false;
	
	var sendCtrl = Tools.actEx(this.flowEngineUrl,"GetResendListCtrl",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"actId=" + id,
		"appData=" + eapi_getAppData());
	e_showDebug(sendCtrl);
	if(!Tools.isSuccess(sendCtrl)) return false;
	
	var sendOcx = new ActiveXObject("EFlowPicker.Picker");
	sendOcx.OULevel = 1;
	sendOcx.SendCtrlXML = sendCtrl;
	sendOcx.commentURL = "";
	sendOcx.specialOU = "";
	if(!sendOcx.run()) {
		return false;
	}
	var sendResult = sendOcx.SendResult;
	
	//检测发送控制中是否有QuerySendScript
	var qsId = new Array();
	var qsScript = new Array();
	var qsCount = 0;
	var qsStr = Tools.getSingleXmlNodeTextByStrOperation(sendCtrl, "QSScript");
	if(!Tools.isStrNull(qsStr)) {
		var resultIds = Tools.getSingleXmlNodeTextByStrOperation(sendResult, "SendResult");
		var idArray = resultIds.split(";");
		var n = idArray.length;
		for(i = 0; i < n; i++) {
			var str = idArray[i];
			var qs = Tools.getSingleXmlNodeTextByStrOperation(qsStr, str);
			if(!Tools.isStrNull(qs)) {
				qsId[qsCount] = str;
				qsScript[qsCount] = qs;
				qsCount++;
			}
		}
	}
	
	if(qsCount > 0) {
		var rUN = sendOcx.ResultUN;
		var rFN = sendOcx.ResultFN;
		var rDN = sendOcx.ResultDN;
			
		for(i = 0; i <= qsCount; i++) {
			var un = Tools.getXmlNodeValueByStrOperation(rUN, qsId[i]);
			var fn = Tools.getXmlNodeValueByStrOperation(rFN, qsId[i]);
			var dn = Tools.getXmlNodeValueByStrOperation(rDN, qsId[i]);
			var para = Tools.getXmlNodeValueByStrOperation(sendResult, qsId[i], "Para");
			qs = qsScript[i] + "('" + qsId[i] + "','" + un + "','" + fn + "','" + dn + "','" + para + "')";
			if(!eval(qs)) return false;
		}
	}

	var r = Tools.actEx(this.flowEngineUrl, "Resend",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"sendSelResult=" + sendResult,
		"appData=" + eapi_getAppData());
	return this.handleActResult(r);
}

//重新办理
function goBack(id) {
	if(!this.validateAct(id)) return false;
	var r = Tools.actEx(this.flowEngineUrl,"GetGobackAutoConfirm",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"actId=" + id);
	if(!this.confirmMsg(r)) return false;
	
	r = Tools.actEx(this.flowEngineUrl,"GoBack",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"actId=" + id,
		"appData=" + eapi_getAppData());
	return this.handleActResult(r);
}

//选人收回
function takebackSel(id) {
	if(!this.validateAct(id)) return false;
	var ctrlStr = Tools.actEx(this.flowEngineUrl,"GetTakebackSelCtrl",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"actId=" + id);

	//document.all.remark.value=ctrlStr;
	if(!Tools.isSuccess(ctrlStr)) return false;
	e_showDebug(ctrlStr);
	
	//$.post(url,params,function(data){retval=data});
	var ocx = new ActiveXObject("EFlowTBPicker.Suggest");
	ocx.SeneCtrlXML = ctrlStr;
	if(!ocx.RUN()) return false;
	
	var script = this.getFindValueById(id,"QueryTakebackScript");
	if(!Tools.isStrNull(script)) {
		var para = Tools.getXmlNodeValueByStrOperation(this.processCtrlStr, id, "Para");
		var qs = script + "('" + id + "','" + ocx.ResultUN + "','" + ocx.ResultFN + "','" + ocx.ResultDN + "','" + para + "')";
	}
	ctrlStr = Tools.actEx(this.flowEngineUrl,"SelTakeback",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"actId=" + id,
		"userList=" + ocx.ResultUN,
		"appData=" + eapi_getAppData());
	return this.handleActResult(ctrlStr);
}

//自动收回
function takebackAuto(id) {
	if(!this.validateAct(id)) return false;
	var r = Tools.actEx(this.flowEngineUrl,"GetGobackAutoConfirm",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"actId=" + id);
	if(!this.confirmMsg(r)) return false;
	
	r = Tools.actEx(this.flowEngineUrl,"AutoTakeback",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"actId=" + id,
		"appData=" + eapi_getAppData());
	return this.handleActResult(r);
}

//得到系统控制信息
function getSysCtrlInfo()
{
	var returnData="";
	returnData = Tools.actEx(this.flowEngineUrl, "GetSysCtrlInfo", "userUN=" + this.userUN, "sysUN=" + this.sysUN);
	if(!Tools.isSuccess(returnData))return;
	this.ctrlInfo = returnData;
	this.sessionId = Tools.getXmlData("ESessionId", returnData);
	Tools.setCookie(Tools.eSessionId, this.sessionId);
}

//得到待办事项列表
function getTodoList()
{
	
}

//发起流程
function launchFlowProcess(flowUN,sessionId)
{
	var currentAction="";
	var returnData="";
	if(sessionId ==null){
		 sessionId = Tools.getCookie(Tools.eSessionId);
	}
	alert("launchFlowProcess(a,a)");
	returnData = Tools.actEx(this.flowEngineUrl,"LaunchFlowProcess",
		"esId="+sessionId,
		"flowUN="+flowUN);		
	if(!Tools.isSuccess(returnData))return;
	Tools.xmlData = returnData;
	
	this.newProcessId = Tools.getXmlNodeValueByStrOperation(returnData, "Process", "id");
	var stepStr = Tools.getXmlNodeValueByStrOperation(returnData, "Step");
	this.newStepCode = Tools.getXmlNodeValueByStrOperation(stepStr, "code");
	this.newStepName = Tools.getXmlNodeValueByStrOperation(stepStr, "name");
	
	return returnData;
}


/*-------------- 流程类方法 --------------------------*/
function loadProcess() {
		var newId=this.processId;
		if(newId.indexOf("@")>0){
			newId=newId.split("@")[1]
		}
	var returnData = Tools.actEx(this.flowEngineUrl,"LoadProcess",
		"esId=" + this.sessionId,
		"fpId=" + newId,
		"title=" + document.all.title.value,
		"appData=" + eapi_getAppData());
	return this.parseProcessCtrl(returnData);

}

//解析流程控制信息
function parseProcessCtrl(processCtrl) {
	if(!Tools.isSuccess(processCtrl)) return false;
	this.processCtrlStr = processCtrl;
	
	this.processId = Tools.getXmlNodeValueByStrOperation(processCtrl, "Process", "id");
	this.taskId = Tools.getXmlNodeValueByStrOperation(processCtrl, "Task", "id");
	this.editable = Tools.isTrueStr(Tools.getXmlNodeValueByStrOperation(processCtrl, "Task", "editable"));	
	this.actionList = Tools.getXmlData("ActArray/Action", processCtrl, true);
	var commentList = Tools.getXmlNodeList("CG/CC", processCtrl, false);
	var n = commentList.length;
	var i = 0;
	for(i = 0; i < n; i++) {
		var node = commentList[i];
		var title = Tools.getChildXmlNodeValue(node, "title");
		var style = Tools.getChildXmlNodeValue(node, "style");
		var un = Tools.getChildXmlNodeValue(node, "un");
		
		var recordNodes = Tools.getChildXmlNodeList(node, "C");
		var comment = new EFlowCommentColumn(un,title, style, recordNodes);
		this.commentColumns[i] = comment;
	}
	return true;
}

//执行操作确认
function validateAct(id) {
	var vScript = this.getActionVScript(id);
	
	//首先如果该操作有确认Script，则先执行确认Script，如果返回真则继续
	if(!Tools.isStrNull(vScript)) {
		try {
			var r = eval(vScript);
		} catch(err) {
			alert(err);
			return false;
		}
		
		if(r == false)	return false;
	}
	return true;
}

//执行操作前访问EFlow
function visitEFlowPreAct(id) {
	var r = Tools.actEx(this.flowEngineUrl, "VisitEFlowPreAct",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id,
		"appData=" + eapi_getAppData());
	return Tools.isSuccess(r);
}

//执行操作后访问EFlow，直接返回VisitEFlowPostAct接口返回的字符串
function visitEFlowPostAct(id) {
	var r = Tools.actEx(this.flowEngineUrl, "VisitEFlowPostAct",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id,
		"appData=" + eapi_getAppData());
	return r;
}

//执行一个操作，参数：操作的ID
function executeAction(id, vPre, vPost, followAct, anotherActId) {
	if(!this.validateAct(id)) return false;

	var r = false;	
	if(vPre) {
		if(this.visitEFlowPreAct(id) == false) return false;
	}
	
	var script = this.getActionScript_O(id);
	if(!Tools.isStrNull(script)) {
		var newScript = script + "('" + id + "')";
		try {
			r = eval(newScript);
		} catch(err) {
			alert("执行“" + script + "”失败：" + err.message);
			return false;
		}
		if(r == false) return false;
	}
	
	if(vPost) {
		r = this.visitEFlowPostAct(id);
		return this.handleActResult(r);
	} else {
		if("Close" == followAct) {
			eapi_close();
		} else if("Reload" == followAct) {
			eapi_reload();
		} else if("AnotherAct" == followAct) {
			var script = this.getActionScript(anotherActId);
			if(!Tools.isStrNull(script)) {
				eval(script);
			}
		}
	}
}

//填写意见
function comment(id) {
	if(!this.validateAct(id)) return false;
	
	var r = Tools.actEx(this.flowEngineUrl, "GetPutCommentCtrl",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id);
	e_showDebug(r);
	var obj = this;
	if(!Tools.isSuccess(r)) return false;
	
	/*var commentOcx = new ActiveXObject("EFlowWriter.Suggest");
	commentOcx.EditCtrlXML = r;
	commentOcx.ESID = this.sessionId;
	commentOcx.AddUCUrl = this.flowEngineUrl;
	if(!commentOcx.RUN()) return false;*/
	$('#commentGrid').datagrid({
		data:[{userComment:"同意。"},{userComment:"阅。"},{userComment:"已阅。"},{userComment:"请送"}],
		singleSelect:true,
		onClickRow:function(i,data){
			$('commentValue').value+=data.userComment;
		}
		
	});
	$('#writeCommentDlg').dialog({
		closed:false,
		buttons:[{
			text:'确定',
			handler:function(){
				if($('commentValue').value!=""){
					r = Tools.actEx(obj.flowEngineUrl, "PutComment",
							"esId=" + obj.sessionId,
							"fpId=" + obj.processId,
							"taskId=" + obj.taskId,
							"actId=" + id,
							"comment=" + $('commentValue').value,
							"appData=" + eapi_getAppData());	
						$('#writeCommentDlg').dialog('close');
					return obj.handleActResult(r);
				}
				else{
					alert('请填写意见内容！');
				}
				}
			},
			{
				text:'取消',
				handler:function(){
					$('#writeCommentDlg').dialog('close');
			}
		}]
	});
	if(r.lastIndexOf('</Comment>')+19==r.length)
		$("commentValue").value=r.substring(r.lastIndexOf('<Comment>')+9,r.lastIndexOf('</Comment>'))
}


//代填意见
function agentComment(id) {
	if(!this.validateAct(id)) return false;
	var r = Tools.actEx(this.flowEngineUrl, "GetPutCommentCtrl",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id);
	e_showDebug(r);
	if(!Tools.isSuccess(r)) return false;
	
	var commentOcx = new ActiveXObject("EFlowWriter.Suggest");
	commentOcx.EditCtrlXML = r;
	commentOcx.ESID = this.sessionId;
	commentOcx.AddUCUrl = this.flowEngineUrl;
	if(!commentOcx.SelectAgenter()) return false;
	var actUser = commentOcx.AgentUser;
	
	r = Tools.actEx(this.flowEngineUrl, "GetCurComment",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id,
		"actUser=" + actUser);
	if(!Tools.isSuccess(r)) return false;
	
	var oldComment = Tools.getXmlData("Comment", r);
	commentOcx.Comment = oldComment;
	if(!commentOcx.RUN()) return false;
	var comment = commentOcx.Comment;
	
	if(Tools.isStrNull(oldComment) && Tools.isStrNull(comment)) return false;
	
	r = Tools.actEx(this.flowEngineUrl, "PutComment",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id,
		"comment=" + comment,
		"actUser=" + actUser,
		"commentDate=" + commentOcx.AgentDate,
		"appData=" + eapi_getAppData());
	return this.handleActResult(r);
}

function send(id) {
	if(!this.validateAct(id)) return false;
	var sendCtrl = Tools.actEx(this.flowEngineUrl, "GetSelSendCtrl",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id,
		"appData=" + eapi_getAppData());
	
	e_showDebug(sendCtrl);
	if(!Tools.isSuccess(sendCtrl)) return false;
	
	var rand = Math.floor(Math.random()*10000+1)
	//默认选中参数值
	var defSeletedParam="";
	var paramType = "";
	var isAuto=false;
	var params = 'xml='+sendCtrl+"&paraType="+paramType+"&para="+defSeletedParam+"&rand="+rand;
	//-------------------------
	var retval;
	var s_id = "";
	var s_name="";
	var s_style="";
	var s_obj = "";
	var s_topLayer="";
	var s_title="";
	var curObj = this;
	$.ajax({
		url:"${request.contextPath}/core/EFlowLoad?&rand="+rand,
		type:'post', 
		data:encodeURI(encodeURI(params)),
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType:'json',
		async:false,
		success:function(result){
			retval=result.users;
			s_id=result.id;
			
			s_style=result.style;
			if(s_style=='Single')
				s_title=result.name+"[发送单人]";
			else
				s_title=result.name+"[发送单人/多人]";
			s_obj=result.obj;
			s_topLayer = result.topLoyer;
	  }
	});
	
	$('#sendDlg').dialog({
		title:s_title,
		closed:isAuto,
		buttons:[{
			text:'确定',
			handler:function(){
				var rows = $('#selUserGrid').datagrid('getRows');
				if(rows.length<=0){
					alert('请选择要接收的用户！')
					return;
				}
				var re = "<SendResult>"+s_id+"</SendResult><"+s_id+"><U>"
				for(var i=0; i<rows.length; i++){
					var row = rows[i];
					if(i==0)
					re+=row.id;
					else
						re+=";"+row.id;
				}
				re+="</U></"+s_id+">";
				sendExc(sendCtrl,re,curObj);
				$('#sendDlg').dialog('close');
			}
		},{
			text:'取消',
			handler:function(){
				$('#sendDlg').dialog('close');
			}
		}]
	});
	var isMulti=true;
	if(s_style=="Multi")
		isMulti=true;
	else if(s_style=="Single")
		isMulti=false;
	$('#sendSelTree').tree({
		data:retval,
		lines:true,
		checkbox:isMulti,
		cascadeCheck:true,
		onlyLeafCheck:false,
		onClick: function(node){
				if(!isMulti){
					if($('#selUserGrid').datagrid('getRows').length>0)
						$('#selUserGrid').datagrid('deleteRow',0);
					if(node.unitName)
					$('#selUserGrid').datagrid('appendRow',{id:node.id,userName:node.text,unitName:node.unitName})
				}else{
					if(node.checked){
						$('#sendSelTree').tree('uncheck',node.target);
					}
					else{
						$('#sendSelTree').tree('check',node.target);
					}
				}
			},
		onCheck:function(node,checked) {
				if(checked){
					if(node.unitName){
						$('#selUserGrid').datagrid('appendRow',{id:node.id,userName:node.text,unitName:node.unitName})
					}else{
						var cnode;
						var flag;
						var row;
						var rows = $('#selUserGrid').datagrid('getRows');
						for(var i=0;i<node.children.length;i++){
							cnode = node.children[i];
							flag=false;
							for(var j=0;j<rows.length;j++){
								row=rows[j];
								if(row.id==cnode.id)
									flag=true;
							}
							if(!flag){
								$('#selUserGrid').datagrid('appendRow',{id:cnode.id,userName:cnode.text,unitName:cnode.unitName})
							}
						}
					}
				}
				else{
					if(node.unitName){
						var rows = $('#selUserGrid').datagrid('getRows');
						var row;
						for(var i=0;i<rows.length;i++){
							row=rows[i];
							if(row.id==node.id){
								$('#selUserGrid').datagrid('deleteRow',i);
							}
						}
					}else{
						var cnode;
						var row;
						var rows = $('#selUserGrid').datagrid('getRows');
						for(var i=0;i<node.children.length;i++){
							cnode = node.children[i];
							for(var j=0;j<rows.length;j++){
								row=rows[j];
								if(row.id==cnode.id){
									$('#selUserGrid').datagrid('deleteRow',j);
								}
							}
						}
						
					}
				}
			}
		});
	$('#selUserGrid').datagrid({
		data:[],
		rownumbers:true,
		/*onLoadSuccess:function(){
			$('#sendSelTree').tree('getChecked').each(function(node){
				if(node.unitName){
					$('#selUserGrid').datagrid('appendRow',{id:node.id,userName:node.text,unitName:node.unitName});
				}
			});
		},*/
		onClickRow:function (index,data){
			if(s_obj=="User"){
				if(isMulti)
					$('#sendSelTree').tree('uncheck',$('#sendSelTree').tree('find',data.id).target);
				else
					$('#selUserGrid').datagrid('deleteRow',index)
			}
		
		}
		
	});	
	
	if(isAuto){
		
		if(confirm("是否确认发送！")){
			var rows = $('#selUserGrid').datagrid('getRows');
			var re = "<SendResult>"+s_id+"</SendResult><"+s_id+"><U>"
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				if(i==0)
				re+=row.id;
				else
					re+=";"+row.id;
			}
			re+="</U></"+s_id+">";
			sendExc(sendCtrl,re,curObj);
		}
	}
}


function sendExc(sendCtrl,sendResult,obj){
	//检测发送控制中是否有QuerySendScript
	var qsId = new Array();
	var qsScript = new Array();
	var qsCount = 0;
	var qsStr = Tools.getSingleXmlNodeTextByStrOperation(sendCtrl, "QSScript");
	if(!Tools.isStrNull(qsStr)) {
		var resultIds = Tools.getSingleXmlNodeTextByStrOperation(sendResult, "SendResult");
		var idArray = resultIds.split(";");
		var n = idArray.length;
		for(i = 0; i < n; i++) {
			var str = idArray[i];
			var qs = Tools.getSingleXmlNodeTextByStrOperation(qsStr, str);
			if(!Tools.isStrNull(qs)) {
				qsId[qsCount] = str;
				qsScript[qsCount] = qs;
				qsCount++;
			}
		}
	}
	
	if(qsCount > 0) {
		for(i = 0; i < qsCount; i++) {
			if(!eval(qs)) return false;
		}
	}

	var r = Tools.actEx(obj.flowEngineUrl, "SelSend",
		"esId=" + obj.sessionId,
		"fpId=" + obj.processId,
		"taskId=" + obj.taskId,
		"sendSelResult=" + sendResult,
		"appData=" + eapi_getAppData());
	return obj.handleActResult(r);
}

//消息确认
function confirmMsg(data) {
	if(!Tools.isSuccess(data)) return false;
	var msg = Tools.getSingleXmlNodeTextByStrOperation(data, "Confirm");
	if(!Tools.isStrNull(msg)) {
		if(!confirm(msg)) return false;
	}
	return true;
}


//自动发送
function autoSend(id, qsScript) {
	//确认消息的获取
	if(!this.validateAct(id)) return false;
	var r = Tools.actEx(this.flowEngineUrl, "GetAutoSendConfirm",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id);
	if(!this.confirmMsg(r)) return false;
	r = Tools.actEx(this.flowEngineUrl, "AutoSend",
		"esId=" + this.sessionId,
		"fpId=" + this.processId,
		"taskId=" + this.taskId,
		"actId=" + id,
		"appData=" + eapi_getAppData());
	if(!Tools.isStrNull(qsScript)) {
		var qs = true;
		try {
			qs = eval(qsScript+"()");
		} catch(err) {
		alert(err.description)
		}
		if(!qs) return false;
	}
	return this.handleActResult(r);
}


//在操作列表中判断指定元素的值是否存在
function isValueExist(key,value){
	var reValue = false;
	for (var i=0;i<this.actionList.length;i++)
	{
		var nodeId = this.actionList(i);
		var node = nodeId.selectSingleNode(key);
		if(node == null) return false;
		if(node.text == value){
			reValue = true;
			break;
		}
	}
	return reValue;
}

//判断是否包含操作actUN
function isActThere(actUN) {
	return this.isValueExist("un", actUN);
}

//根据id得到Action中元素的值
function getFindValueById(id,key){
	if(Tools.isStrNull(id)) return "";
	
	if(this.currentAction == null || this.currentAction.selectSingleNode("id").text != id){
		this.currentAction = null;
		for (var i=0;i<this.actionList.length;i++)
		{
			var nodeId = this.actionList(i);
			if(nodeId.selectSingleNode("id").text == id){
				this.currentAction = nodeId;
				break;
			}
		}
	}

	if(this.currentAction!=null){
		var node = this.currentAction.selectSingleNode(key);
		if(node == null) return "";
		return node.text;
	}else{
		return "";
	}
}

//根据id得到Action的Type
function getActionType(id){
	return this.getFindValueById(id,"type");
}

//根据id得到Action的Code
function getActionCode(id){
	return this.getFindValueById(id,"code");
}

//根据id得到Action的Name
function getActionName(id){
	return this.getFindValueById(id,"name");
}

//根据id得到Action的Name
function getActionPara(id){
	return this.getFindValueById(id,"Para");
}

//根据id得到Action的Script
function getActionScript(id){
	return this.getFindValueById(id,"Script");
}

//根据id得到Action的Stype
function getActionStyle(id){
	return this.getFindValueById(id,"Style");
}

//根据id得到Action的Script_O，即原来的Script
function getActionScript_O(id){
	return this.getFindValueById(id,"Script_O");
}

//根据ID得到Action的VScript（操作前确认Script）
function getActionVScript(id) {
	return this.getFindValueById(id,"VScript");
}

//流程信息
function getProcessInfo() {
	
}

//获取意见信息
function getAllComment() {
}

//得到用户当前的意见信息
function getMyComment() {
}