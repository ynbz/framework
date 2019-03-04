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
					<div class="row hidden" id="czbz">
						<form method="post" name="formIncDis" id="formIncDis" >
							<input type="hidden" name="ctrlInfo" id="ctrlInfo">
							<input type="hidden" name="ESID" id="ESID" value="${Session['flow_session'].sessionId}">
							<input type="hidden" name="processId" id="processId" value="${incdism.processId}">
							<input type="hidden" name="id" id="id" value="${incdism.id}">
							<input type="hidden" name="fileType" id="fileType" value="swlc">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">发文题名</span>
										</div>
										<input type="text" name="title" id="title" value="${incdism.title}" placeholder="最多100个汉字"  class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content"> 收文单位</span>
										</div>
										<input type="text" name="idUnit" id="idUnit" value="${incdism.idUnit}" placeholder="最多100个汉字" class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">来文单位</span>
										</div>
										<input type="text" name="rootInUnit" id="rootInUnit" value="${incdism.rootInUnit}" placeholder="最多100个汉字" class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">原文文号</div>
										<input type="text" class="form-control" id="originalNumber" name="originalNumber" value="${incdism.originalNumber}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">收文编号</div>
										<input type="text" class="form-control" id="originalNumber" name="originalNumber" value="${incdism.originalNumber}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">原文日期</div>
										<input type="text" class="form-control datetimepicker" id="originalDate" name="originalDate" value="${(incdism.originalDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">收文日期</div>
										<input type="text" class="form-control datetimepicker" id="incDisDate" name="incDisDate" value="${(incdism.incDisDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">限办日期</div>
										<input type="text" class="form-control datetimepicker" id="limitDate" name="limitDate" value="${(incdism.limitDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">总序号</div>
										<input type="text" class="form-control" id="countCode" name="countCode" value="${incdism.countCode}" />
									</div>
								</div>
							</div>
							
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">主办部门</div>
										<input type="text" class="form-control" id="sDepartment" name="sDepartment" value="${incdism.sDepartment}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">协办部门</div>
										<input type="text" class="form-control" id="coOrganizer" name="coOrganizer" value="${incdism.coOrganizer}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">阅读部门</div>
										<input type="text" class="form-control" id="readUnit" name="readUnit" value="${incdism.readUnit}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">阅办部门</div>
										<input type="text" class="form-control" id="readDoUnit" name="readDoUnit" value="${incdism.readDoUnit}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">文件密级</div>
										<select class="form-control" name="fileSecCla" id="fileSecCla">
											<option value="0" ${((incdism.fileSecCla==0)?string('selected',''))!''}>--请选择--</option>
											<option value="1" ${((incdism.fileSecCla==1)?string('selected',''))!''}>秘密</option>
											<option value="2" ${((incdism.fileSecCla==2)?string('selected',''))!''}>机密</option>
											<option value="3" ${((incdism.fileSecCla==3)?string('selected',''))!''}>绝密</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">保密期限</div>
										<input type="text" class="form-control" id="secrecyDateLimit" name="secrecyDateLimit" value="${incdism.secrecyDateLimit}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">紧急程度</div>
										<select class="form-control" name="degreeOfE" id="degreeOfE">
											<option value="0" ${((incdism.degreeOfE==0)?string('selected',''))!''}>--请选择--</option>
											<option value="1" ${((incdism.degreeOfE==1)?string('selected',''))!''}>紧急</option>
											<option value="2" ${((incdism.degreeOfE==2)?string('selected',''))!''}>特急</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">页数</div>
										<input type="text" class="form-control" id="pageNumber" name="pageNumber" value="${incdism.pageNumber}" />
									</div>
								</div>
							</div>
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">备注</span>
										</div>
										<textarea id="comment" name="comment"  maxlength="5000" placeholder="&lt;最多5000字&gt;"   class="form-control"   rows="5">${incdism.comment}</textarea>
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
										<input type="text" name="title" id="title" value="" placeholder="最多100个汉字" readonly="readonly"  class="form-control">
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content"> 收文单位</span>
										</div>
										<input type="text" name="idUnit" id="idUnit" value="${incdism.idUnit}" placeholder="最多100个汉字" readonly="readonly" class="form-control">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">
											<span class="title-content">来文单位</span>
										</div>
										<input type="text" name="rootInUnit" id="rootInUnit" value="${incdism.rootInUnit}" readonly="readonly" placeholder="最多100个汉字" class="form-control">
									</div>
								</div>
							</div>
						</div>
						
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">原文文号</div>
										<input type="text" class="form-control" id="originalNumber" name="originalNumber" readonly="readonly" value="${incdism.originalNumber}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">收文编号</div>
										<input type="text" class="form-control" id="originalNumber" name="originalNumber" readonly="readonly" value="${incdism.originalNumber}" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">原文日期</div>
										<input type="text" class="form-control" id="originalDate" name="originalDate" readonly="readonly" value="${(incdism.originalDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">收文日期</div>
										<input type="text" class="form-control" id="incDisDate" name="incDisDate" value="${(incdism.incDisDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">限办日期</div>
										<input type="text" class="form-control" id="limitDate" name="limitDate" value="${(incdism.limitDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">总序号</div>
										<input type="text" class="form-control" id="countCode" name="countCode" value="${incdism.countCode}" />
									</div>
								</div>
							</div>
							
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">主办部门</div>
										<input type="text" class="form-control" id="sDepartment" readonly="readonly" name="sDepartment" value="${incdism.sDepartment}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">协办部门</div>
										<input type="text" class="form-control" id="coOrganizer" readonly="readonly" name="coOrganizer" value="${incdism.coOrganizer}" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">阅读部门</div>
										<input type="text" class="form-control" id="readUnit" readonly="readonly" name="readUnit" value="${incdism.readUnit}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">阅办部门</div>
										<input type="text" class="form-control" id="readDoUnit" readonly="readonly" name="readDoUnit" value="${incdism.readDoUnit}" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">文件密级</div>
										<input type="text" class="form-control" id="fileSecCla" name="fileSecCla" readonly="readonly" value="${(incdism.fileSecCla==0)?string('',(incdism.fileSecCla==1)?string('秘密',(incdism.fileSecCla==2)?string('机密',(incdism.fileSecCla==3)?string('绝密',''))))}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">保密期限</div>
										<input type="text" class="form-control" id="secrecyDateLimit" readonly="readonly" name="secrecyDateLimit" value="${incdism.secrecyDateLimit}" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">紧急程度</div>
										<input type="text" class="form-control" id="degreeOfE" name="degreeOfE" readonly="readonly" value="${(incdism.degreeOfE==0)?string('',(incdism.degreeOfE==1)?string('紧急',(incdism.degreeOfE==2)?string('特急','')))}" />
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon input-lable">页数</div>
										<input type="text" class="form-control" id="pageNumber" readonly="readonly" name="pageNumber" value="${incdism.pageNumber}" />
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
										<textarea id="comment" name="comment"  maxlength="5000" placeholder="&lt;最多5000字&gt;" readonly="readonly"   class="form-control"   rows="5">${incdism.comment}</textarea>
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
	<script src="${request.contextPath}/test/incdismanage/addIncDis.js"></script>
	
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