<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${(data.name)!'错误'}</title>
<link type="image/x-icon" rel="shortcut icon" href="${request.contextPath}/img/favicon.ico">
<link rel="stylesheet" href="${request.contextPath}/core/css/form-builder.min.css">
<link rel="stylesheet" href="${request.contextPath}/core/css/bootstrap.min.css">
<link rel="stylesheet" href="${request.contextPath}/css/suredy-ui.css">
<!--[if lt IE 9]>
<script src="${request.contextPath}/core/js/html5shiv.min.js"></script>
<script src="${request.contextPath}/core/js/respond.min.js"></script>
<![endif]-->

<style type="text/css">
[id^=frmb-][id$='-form-wrap'] .frmb li.form-field.editing .frm-holder {
	top: 22px;
}

.btn.disabled {
	cursor: not-allowed !important;
}
</style>
</head>
<body style="overflow: hidden;" data-e="${design?string('1','0')}">
	<#if notFound?? && notFound || !data>
	<div class="container">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<strong>错误!</strong> 无法打开指定的表单定义模版！
		</div>
	</div>
	<#else>
	<div class="container formediter${(design?string('',' editing'))}" style="margin-top: 20px;">
		<h3 class="text-info text-center">表单模版设计器v1.0版</h3>
		<div class="row">
			<form class="form-define-info">
				<input type="hidden" name="id" value="${data.id}">
				<input type="hidden" name="createTime" value="${data.createTime!.now}">
				<textarea class="hide" name="formData">${data.formData}</textarea>
				<div class="form-group col-md-12">
					<div class="input-group">
						<div class="input-group-addon">是否启用</div>
						<select class="form-control" name="enable">
							<option value="0"${((data.enable?string('',' selected')))!''}>停用</option>
							<option value="1"${((data.enable?string(' selected','')))!''}>启用</option>
						</select>
					</div>
				</div>
				<div class="form-group col-md-12">
					<div class="input-group">
						<div class="input-group-addon">表单名称</div>
						<input class="form-control" type="text" name="name" value="${data.name}">
					</div>
				</div>
				<div class="form-group col-md-12">
					<div class="input-group">
						<div class="input-group-addon">表单版本</div>
						<input class="form-control" type="text" name="version" value="${data.version}">
					</div>
				</div>
				<div class="form-group col-md-12">
					<div class="input-group">
						<div class="input-group-addon">描述信息</div>
						<textarea class="form-control" rows="5" name="formDesc">${data.formDesc}</textarea>
					</div>
				</div>
			</form>
			<div class="col-md-12">
				<div class="btn btn-danger change-view">设计表单</div>
				<#if data.id??>
				<div class="btn btn-primary save-form-data">保存数据</div>
				</#if>
			</div>
		</div>
	</div>
	<div class="bg-primary formediter${(design?string(' editing',''))}" style="height: 22px; line-height: 22px; position: fixed; top: 0; left: 0; right: 0; z-index: 1060;">
		表单模版设计器v1.0版
		<div class="btn btn-danger btn-xs change-view pull-left">基本信息</div>
	</div>
	<div class="suredy-formbuilder formediter${(design?string(' editing',''))}" style="padding-top: 22px"></div>

	<script type="text/javascript">
		var ctxp = '${request.contextPath}';
	</script>
	<script src="${request.contextPath}/require.js"></script>
	<script src="${request.contextPath}/core/js/jquery-1.11.3.min.js"></script>
	<script src="${request.contextPath}/config.js"></script>

	<script type="text/javascript">
		require([ 'suredyFormBuilder' ], function() {
		});
	</script>
	</#if>
</body>
</html>
