<@t.header> </@t.header> <@t.body> <#if invalid?? && invalid>
<div class="container">
	<div class="alert alert-warning alert-dismissible" role="alert">
		<strong>错误！</strong>数据无效！
	</div>
</div>
<#else>
<div class="container suredy-form">
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<div class=" btn navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-collapse" aria-expanded="false">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</div>
				<div class="navbar-brand" href="#">
					<i class="icon-bookmark-empty"></i>
					${data.fileTypeName}
				</div>
			</div>
			<div class="collapse navbar-collapse" id="menu-collapse">
				<ul class="nav navbar-nav" id="btList"></ul>
			</div>
		</div>
	</div>

	<div role="tabpanel" class="suredy-tabs" style="padding-top: 60px; padding-bottom: 50px">
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
		<div class="tab-content" style="padding: 10px; background-color: #fff; border: 1px solid #ddd; border-top: none;">
			<div role="tabpanel" class="tab-pane active" id="tab1">
				<input type="hidden" name="ctrlInfo" id="ctrlInfo">
				<input type="hidden" name="ESID" id="ESID" value="${Session['flow_session'].sessionId}">
				<input type="hidden" name="fileType" id="fileType" value="${data.flowFileType}">

				<form method="post" id="live-form">
					<input type="hidden" name="id" id="id" value="${data.id}">
					<input type="hidden" name="processId" id="processId" value="${data.processId}">
					<input type="hidden" name="formEntryId" value="${formEntryId}">
					<div class="row">
						<div class="form-group col-md-12">
							<div class="input-group">
								<label class="input-group-addon">标题</label>
								<input class="form-control" type="text" name="title" value="${data.title}">
							</div>
						</div>
						<div class="form-group col-md-6">
							<div class="input-group">
								<label class="input-group-addon">起草人</label>
								<input class="form-control" type="text" value="${data.creatorFullName}" readonly>
							</div>
						</div>
						<div class="form-group col-md-6">
							<div class="input-group">
								<label class="input-group-addon">起草时间</label>
								<input class="form-control" type="text" value="${data.createTime}" readonly>
							</div>
						</div>
					</div>
					<div class="row live-form"></div>
				</form>
			</div>
			<div role="tabpanel" class="tab-pane" id="logInfo">这里是流转记录</div>
		</div>
	</div>
</div>
</#if> </@t.body> <@t.foot> <#if !invalid?? || !invalid>
<script src="${request.contextPath}/core/js/flow-core.js"></script>
<script src="${request.contextPath}/js/flow-app.js"></script>
<script src="${request.contextPath}/component/flow/js/sender.js"></script>
<script src="${request.contextPath}/component/flow/js/comment.js"></script>

<script type="text/javascript">
	require([ 'formRender', 'jqueryForm' ], function() {
		initForm();

		$.ajax({
			url : '${request.contextPath}/live-form/load-data/${data.id}',
			type : 'post',
			dataType : 'text',
			success : function(data) {
				if (data)
					$('div.live-form').formRender({
						formData : data,
						dataType : 'json',
					});

				// 初始化列宽
				var maxWidth = -1;
				$('.input-group').each(function(i, dom) {
					var $first = $('.input-group-addon:first', dom);
					var width = $first.width();

					if (width > maxWidth)
						maxWidth = width;

					$first.wrapInner('<div class="label-width" />');
				});
				$('.input-group').each(function(i, dom) {
					var $first = $('.input-group-addon:first', dom);
					$('.label-width', $first).width(maxWidth);
				});
			}
		});

		window.updateLiveForm = function() {
			var $form = $('form#live-form');

			$form.ajaxSubmit({
				url : '${request.contextPath}/live-form/update',
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (!data || !data.success) {
						alert((data && data.msg) || '保存失败！');
						return;
					}

					alert('保存成功！');
				},
				error : function() {
					alert('保存失败！');
				}
			});
		};
	});
</script>
</#if> </@t.foot>
