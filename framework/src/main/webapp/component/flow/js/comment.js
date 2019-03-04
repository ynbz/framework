var hisComment = "";
function comment(id) {
	
		if (!this.validateAct(id))
			return false;

		var r = Tools.actEx(this.flowEngineUrl, "GetPutCommentCtrl", "esId="
				+ this.sessionId, "fpId=" + this.processId, "taskId="
				+ this.taskId, "actId=" + id);
		e_showDebug(r);
		var obj = this;
		if (!Tools.isSuccess(r))
			return false;
		if (r.lastIndexOf('</Comment>') + 19 == r.length)
			hisComment = r.substring(r.lastIndexOf('<Comment>') + 9, r
					.lastIndexOf('</Comment>'));
		require([ "suredyModal" ], function(Modal) {
		Modal.showModal({
			uri : Suredy.contextPath + '/flow/comment.do',
			title : '填写意见',
			data : hisComment,
			okClick : function() {
				if ($('commentValue').value != "") {
					r = Tools.actEx(obj.flowEngineUrl, "PutComment", "esId="
							+ obj.sessionId, "fpId=" + obj.processId, "taskId="
							+ obj.taskId, "actId=" + id, "comment="
							+ $('#commentValue').val(), "appData="
							+ eapi_getAppData());
					Modal.closeModal();
					return obj.handleActResult(r);
				} else {
					alert('请填写意见内容！');
				}
			}
		});
	});

}
