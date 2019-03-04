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
					<a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">工作单 </a>
				</li>
				<li role="presentation">
					<a href="#logInfo" aria-controls="logInfo" role="tab" data-toggle="tab">流转记录</a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content" style="padding-top:10px">
				<div role="tabpanel" class="tab-pane active" id="tab1">
					<div class="row hidden" id="czbz">
						<form method="post" name="formDis" id="formDis" >
							<input type="hidden" name="ctrlInfo" id="ctrlInfo">
							<input type="hidden" name="ESID" id="ESID" value="${Session['flow_session'].sessionId}">
							<input type="hidden" name="processId" id="processId" value="${dism.processId}">
							<input type="hidden" name="id" id="id" value="${dism.id}">
							<input type="hidden" name="fileType" id="fileType" value="${dism.fileTypeCode}">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">发文题名
										</div>
										<input type="text" name="title" id="title" value="${dism.title}" placeholder="最多100个汉字"  class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">主送单位</span>
										</div>
										<input type="text" name="asUnit" id="asUnit" value="${dism.asUnit}" placeholder="最多100个汉字" class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">抄送单位</span>
										</div>
										<input type="text" name="ctUnit" id="ctUnit" value="${dism.ctUnit}" placeholder="最多100个汉字" class="form-control">
									</div>
								</div>
							</div>
						
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">主办部门</div>
										<input type="text" class="form-control" id="sDepartment" name="sDepartment" value="${dism.sDepartment}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">发文单位</div>
										<input type="text" class="form-control" id="disUnit" name="disUnit" value="${dism.disUnit}" />
									</div>
								</div>
							</div>
						
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">签发人</div>
										<input type="text" class="form-control" id="issuePerson" name="issuePerson" value="${dism.issuePerson}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">签发时间</div>
										<input type="text" class="form-control datetimepicker" id="issueDate" name="issueDate" value="${(dism.issueDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
					
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">拟稿人</div>
										<input type="text" class="form-control" id="drafter" name="drafter" value="${dism.drafter}" />
									</div>
								</div>
							</div>
							<div class="col-md-3 col-xs-3">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">拟稿时间</div>
										<input type="text" class="form-control datetimepicker" id="draftDate" name="draftDate" value="${(dism.draftDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-3 col-xs-3">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">联系电话</div>
										<input type="text" class="form-control" id="phone" name="phone" value="${dism.phone}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">发文类型</div>
										<input type="text" class="form-control" id="disType" name="disType" value="${dism.disType}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">发文字号</div>
										<input type="text" class="form-control" id="disWrodSize" name="disWrodSize" value="${dism.disWrodSize}" />
									</div>
								</div>
							</div>
						
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">紧急程度</div>
										<select class="form-control" name="degreeOfE" id="degreeOfE">
											<option value="0" ${((dism.degreeOfE==0)?string('selected',''))!''}>--请选择--</option>
											<option value="1" ${((dism.degreeOfE==1)?string('selected',''))!''}>紧急</option>
											<option value="2" ${((dism.degreeOfE==2)?string('selected',''))!''}>特急</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">印发数量</div>
										<input type="text" class="form-control" id="printNumber" name="printNumber"  value="${dism.printNumber}" />
									</div>
								</div>
							</div>
						
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">主题词</span>
										</div>
										<input type="text" name="subjectTerm" id="subjectTerm" value="${dism.subjectTerm}" class="form-control">
									</div>
								</div>
							</div>
						
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">备注</span>
										</div>
										<textarea id="comment" name="comment"  maxlength="5000" placeholder="&lt;最多5000字&gt;"   class="form-control"   rows="5">${dism.comment}</textarea>
									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="row "  id="czzd">
							<div class="row">
								<div class="col-md-12 col-xs-12">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">
												<span class="title-content">发文题名</span>
											</div>
											<input type="text" name="title" id="title" value="${dism.title}" readonly="readonly" placeholder="最多100个汉字"  class="form-control">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12 col-xs-12">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">
												<span class="title-content">主送单位</span>
											</div>
											<input type="text" name="asUnit" id="asUnit" value="${dism.asUnit}" readonly="readonly" placeholder="最多100个汉字" class="form-control">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12 col-xs-12">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">
												<span class="title-content">抄送单位</span>
											</div>
											<input type="text" name="ctUnit" id="ctUnit" value="${dism.ctUnit}" readonly="readonly" placeholder="最多100个汉字" class="form-control">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">主办部门</div>
											<input type="text" class="form-control" id="sDepartment" name="sDepartment" readonly="readonly" value="${dism.sDepartment}" />
										</div>
									</div>
								</div>
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">发文单位</div>
											<input type="text" class="form-control" id="disUnit" name="disUnit" readonly="readonly" value="${dism.disUnit}" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">签发人</div>
											<input type="text" class="form-control" id="issuePerson" name="issuePerson" readonly="readonly" value="${dism.issuePerson}" />
										</div>
									</div>
								</div>
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
												<div class="input-group-addon input-lable">签发时间
											</div>
											<input type="text" class="form-control" id="issueDate" readonly="readonly" name="issueDate" value="${(dism.issueDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">拟稿人</div>
											<input type="text" class="form-control" id="drafter" name="drafter" readonly="readonly" value="${dism.drafter}" />
										</div>
									</div>
								</div>
								<div class="col-md-3 col-xs-3">
									<div class="form-group">
										<div class="input-group">
												<div class="input-group-addon input-lable">拟稿时间
											</div>
											<input type="text" class="form-control" id="draftDate" readonly="readonly" name="draftDate" value="${(dism.draftDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-3 col-xs-3">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">联系电话</div>
											<input type="text" class="form-control" id="phone" name="phone" readonly="readonly" value="${dism.phone}" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">发文类型</div>
											<input type="text" class="form-control" id="disType" name="disType" readonly="readonly" value="${dism.disType}" />
										</div>
									</div>
								</div>
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">发文字号</div>
											<input type="text" class="form-control" id="disWrodSize" name="disWrodSize" readonly="readonly" value="${dism.disWrodSize}" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">紧急程度</div>
											<input type="text" class="form-control" id="degreeOfE" name="degreeOfE" readonly="readonly" value="${(dism.degreeOfE==0)?string('',(dism.degreeOfE==1)?string('紧急',(dism.degreeOfE==2)?string('特急','')))}" />
										</div>
									</div>
								</div>
								<div class="col-md-6 col-xs-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">印发数量</div>
											<input type="text" class="form-control" id="printNumber" name="printNumber" readonly="readonly" value="${dism.printNumber}" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12 col-xs-12">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">
												<span class="title-content">主题词</span>
											</div>
											<input type="text" name="subjectTerm" id="subjectTerm" readonly="readonly" value="${dism.subjectTerm}" class="form-control">
										</div>
									</div>
								</div>
							</div>
							
							
							<div class="row">
								<div class="col-md-12 col-xs-12">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group-addon input-lable">
												<span class="title-content">备注</span>
											</div>
											<textarea id="comment" name="comment"  maxlength="5000" readonly="readonly" placeholder="&lt;最多5000字&gt;"   class="form-control"   rows="5">${dism.comment}</textarea>
										</div>
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
<script src="${request.contextPath}/test/dismanage/addDis.js"></script>

<script type="text/javascript">
	$(function(){
		initForm();
		var ctrlInfo=$('#ctrlInfo').val();
		if(ctrlInfo.indexOf("editable")>0){//操作更换设备
			$('#czzd').addClass('hidden')
			$('#czbz').removeClass("hidden");
		}
	});
</script>

</@t.foot>