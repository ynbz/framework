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
						<i class="icon-remove close" ></i>
					</a>
				</li>
			</ul>
		</div>
		
		<div role="tabpanel" class="suredy-tabs" style="padding-top:60px;">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active">
					<a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">审批单 </a>
				</li>
				<li role="presentation">
					<a href="#sch" aria-controls="sch" role="tab" data-toggle="tab">计划表</a>
				</li>
				<li role="presentation">
					<a href="#logInfo" aria-controls="logInfo" role="tab" data-toggle="tab">流转记录</a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content" style="padding-top:10px">
				<div role="tabpanel" class="tab-pane active" id="tab1">
					<div class="row hidden" id="czbz">
						<form method="post" name="formPro" id="formPro" >
							<input type="hidden" name="ctrlInfo" id="ctrlInfo">
							<input type="hidden" name="ESID" id="ESID" value="${Session['flow_session'].sessionId}">
							<input type="hidden" name="processId" id="processId" value="${pro.processId}">
							<input type="hidden" name="id" id="id" value="${pro.id}">
							<input type="hidden" name="fileType" id="fileType" value="${pro.fileTypeCode}">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">计划名称</div>
										<input type="text" name=title id="title" value="${pro.title}" placeholder="最多100个汉字"  class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">计划部门</div>
										<input type="text" class="form-control" id="proUnit" name="proUnit" value="${pro.proUnit}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">计划人</div>
										<input type="text" class="form-control" id="proPerson" name="proPerson" value="${pro.proPerson}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">计划时间</div>
										<input type="text" class="form-control" id="createTime" name="createTime" value="${(pro.createTime?string('yyyy-MM-dd'))!''}"  readonly />
									</div>
								</div>
							</div>
					
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">发布时间</div>
										<input type="text" class="form-control" id="issueDate" name="issueDate" value="${(pro.issueDate?string('yyyy-MM-dd'))!''}"  readonly />
									</div>
								</div>
							</div>
							
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">备注</span>
										</div>
										<textarea id="comment" name="comment"  maxlength="5000" placeholder="&lt;最多5000字&gt;"   class="form-control"   rows="5">${pro.comment}</textarea>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="row "  id="czzd">
						<div class="col-md-12 col-xs-12">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon input-lable">计划名称</div>
									<input type="text" value="${pro.title}" readonly="readonly" placeholder="最多100个汉字"  class="form-control">
								</div>
							</div>
						</div>
						<div class="col-md-6 col-xs-6">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon input-lable">计划部门</div>
									<input type="text" class="form-control" readonly="readonly" value="${pro.proUnit}" />
								</div>
							</div>
						</div>
						<div class="col-md-6 col-xs-6">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon input-lable">计划人</div>
									<input type="text" class="form-control" readonly="readonly" value="${pro.proPerson}" />
								</div>
							</div>
						</div>
						<div class="col-md-6 col-xs-6">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon input-lable">计划时间</div>
									<input type="text" class="form-control" value="${(pro.createTime?string('yyyy-MM-dd'))!''}"  readonly="readonly" />
								</div>
							</div>
						</div>
				
						<div class="col-md-6 col-xs-6">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon input-lable">发布时间</div>
									<input type="text" class="form-control" value="${(pro.issueDate?string('yyyy-MM-dd'))!''}"  readonly="readonly" />
								</div>
							</div>
						</div>
						
						<div class="col-md-12 col-xs-12">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon input-lable">
										<span class="title-content">备注</span>
									</div>
									<textarea readonly="readonly"  class="form-control"   rows="5">${pro.comment}</textarea>
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
				</div>
				<div role="tabpanel" class="tab-pane" id="sch" style="padding-left: 15px;padding-right: 15px;">
					<div class="row" style="">
						<div class="form-inline hidden" id="czsch">
							<div class="btn btn-info" id="addsch">
								<i class="icon-plus"></i> 新增
							</div>
							<!-- <div class="btn btn-info" id="updatesch">
								<i class="icon-edit"></i> 修改
							</div>
							<div class="btn btn-danger" id="deletesch">
								<i class="icon-remove"></i> 删除
							</div>	 -->
						</div>
					</div>
					<div class="row" id="schList" style="padding-top: 5px;">
					
					</div>
					
				</div>
				<div role="tabpanel" class="tab-pane" id="logInfo">这里是流转记录</div>
			</div>
		</div>
	</div>
</@t.body> <@t.foot>
<script src="${request.contextPath}/core/js/flow-core.js"></script>
<script src="${request.contextPath}/js/flow-app.js"></script>
<script src="${request.contextPath}/js/suredy-doc.js"></script>
<script src="${request.contextPath}/component/flow/js/sender.js"></script>
<script src="${request.contextPath}/component/flow/js/comment.js"></script>
<script src="${request.contextPath}/app/project/addPro.js"></script>

<script type="text/javascript">
	$(function(){
		initForm();
		var ctrlInfo=$('#ctrlInfo').val();
		if(ctrlInfo.indexOf("editable")>0){//操作更换设备
			$('#czzd').addClass('hidden')
			$('#czbz').removeClass("hidden");
		}
		if(ctrlInfo.indexOf("czsch")>0){
			$('#czsch').removeClass("hidden");
		}
	});
</script>

</@t.foot>