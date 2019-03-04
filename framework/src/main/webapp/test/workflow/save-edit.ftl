<@t.header> 
<style>
.input-lable {
	min-width: 95px;
}

</style>
</@t.header> 
<@t.body>
	<div class="container suredy-form">
		<div class="navbar navbar-default navbar-fixed-top">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">
					<i class="icon-flag" style="color:blue;"></i>
					${fileTypeName}
				</a>
			</div>
			<ul class="nav navbar-nav suredy-form-bar" id="btList">
				
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="#" class="suredy-close-form">
						<i class="icon-remove close" style="margin-right: 10px;"></i>
					</a>
				</li>
			</ul>
		</div>
		
		<div role="tabpanel" class="suredy-tabs" style="padding-top:60px;padding-bottom:50px">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active">
					<a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">工作单 </a>
				</li>
				<li role="presentation">
					<a href="#logInfo" aria-controls="logInfo" role="tab" data-toggle="tab">流转记录</a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content" style="padding-top:10px">
				<div role="tabpanel" class="tab-pane active" id="tab1">
					<form method="post" name="form" id="form" >
						<input type="hidden" name="ctrlInfo" id="ctrlInfo">
						<input type="hidden" name="ESID" id="ESID" value="${Session['flow_session'].sessionId}">
						<input type="hidden" name="processId" id="processId" value="${wf.processId}">
						<input type="hidden" name="id" id="id" value="${wf.id}">
						<input type="hidden" name="fileType" id="fileType" value="gzlc">
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">工作摘要</span>
										</div>
										<input type="text" name="title" id="title" value="${wf.title}" readonly="readonly"   placeholder="最多100个汉字" class="form-control">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">派单人</div>
										<input type="text" class="form-control" id="createName" name="createName" readonly="readonly" value="${wf.createName}" />
										<input type="hidden" class="form-control" id="createNameId" name="createNameId" value="${wf.createNameId}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">执行人</div>
										<input type="hidden" class="form-control" id="executeNameId" name="executeNameId" value="${wf.executeNameId}" />
										<input type="text" class="form-control"  maxlength="50" placeholder="&lt;最多50字&gt;" readonly="readonly"  id="executeName"   name="executeName" value="${wf.executeName}" />	
										<div class="input-group-addon btn btn-default hidden" id="selectorExecute">选择...</div>					
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
											<div class="input-group-addon input-lable">开始时间
										</div>
										<input type="text" class="form-control" id="startTime" name="startTime" value="${wf.startTime}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
											<div class="input-group-addon input-lable">完成时间
										</div>
										<input type="text" class="form-control" id="endTime" name="endTime" value="${wf.endTime}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">工作内容</span>
										</div>
										<textarea id="content" name="content" readonly="readonly" maxlength="5000" placeholder="&lt;最多5000字&gt;"   class="form-control"   rows="5">${wf.content}</textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group"  id="comment-content">

								</div>
							</div>
						</div>
					</form>
				</div>
				<div role="tabpanel" class="tab-pane" id="logInfo">这里是流转记录</div>
			</div>
		</div>
	</div>
</@t.body> <@t.foot>
	<script src="${request.contextPath}/core/js/flow-core.js"></script>
	<script src="${request.contextPath}/js/flow-app.js"></script>
	<script src="${request.contextPath}/component/flow/js/sender.js"></script>
	<script src="${request.contextPath}/component/flow/js/comment.js"></script>
	<script src="${request.contextPath}/test/workflow/workfolw.js"></script>
	
	<script type="text/javascript">
		$(function(){
			initForm();
			var ctrlInfo=$('#ctrlInfo').val();
			if(ctrlInfo.indexOf("editable")>0){//操作更换设备
				$('#startTime').addClass('datetimepicker');
				$('#endTime').addClass('datetimepicker');
				$('#selectorExecute').removeClass('hidden');
				$('#executeName').removeAttr("readonly");
				$('#content').removeAttr("readonly");
			}
		});
	</script>
	
</@t.foot>