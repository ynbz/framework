//选人发送接口实现
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
	var defSeletedParam='';
	var paramType = '';
	var eflowObj=this;
	require(["suredyModal"],function(Modal){
	Modal.showModal({
			title : "选人发送",
			uri : Suredy.ctxp+'/flow/sender.do',
			data:{xml:sendCtrl,paraType:paramType,para:defSeletedParam},
			okClick:function(){
				var s_id=$('#sendId').val();
				var re = '<SendResult>'+s_id+'</SendResult><'+s_id+'><U>';
				if($('.userRow').length>0){
				$('.userRow').each(function(index){
					if(index==0)
						re += $(this).attr('data-id');
					else
						re += ';'+ $(this).attr('data-id');
				})
				re+="</U></"+s_id+">";
				sendExc(sendCtrl,re,eflowObj);
				Modal.closeModal();
				}else{
					alert('请选择人员！');
				}
			}
		});
	});
}

function sendExc(sendCtrl,sendResult,eflowObj){
	var qsScript = new Array();
	var qsStr = Tools.getSingleXmlNodeTextByStrOperation(sendCtrl, "QSScript");
	if(!Tools.isStrNull(qsStr)) {
		var qs = true;
		try {
			qs = eval(qsStr+"()");
		} catch(err) {
			alert(err.description);
		}
		if(!qs) return false;
	}
	var r = Tools.actEx(eflowObj.flowEngineUrl, "SelSend",
		"esId=" + eflowObj.sessionId,
		"fpId=" + eflowObj.processId,
		"taskId=" + eflowObj.taskId,
		"sendSelResult=" + sendResult,
		"appData=" + eapi_getAppData());
	return eflowObj.handleActResult(r);
}