<#macro header>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>水滴科技</title>
<link type="image/x-icon" rel="shortcut icon" href="${request.contextPath}/img/favicon.ico">
<link rel="stylesheet" href="${request.contextPath}/core/css/bootstrap.min.css">
<link rel="stylesheet" href="${request.contextPath}/core/css/bootstrapValidator.css">
<link rel="stylesheet" href="${request.contextPath}/core/css/font-awesome.min.css">
<link rel="stylesheet" href="${request.contextPath}/core/css/animate.min.css">
<link rel="stylesheet" href="${request.contextPath}/css/suredy-ui.css">
<#nested/>
<!--[if lt IE 9]>
<script src="${request.contextPath}/core/js/html5shiv.min.js"></script>
<script src="${request.contextPath}/core/js/respond.min.js"></script>
<![endif]-->
</head>
<body>
	</#macro> <#macro body> <#nested/> </#macro> <#macro foot>
	<script type="text/javascript">
		var ctxp = '${request.contextPath}';
	</script>
	<script src="${request.contextPath}/require.js"></script>
	<script src="${request.contextPath}/core/js/jquery-1.11.3.min.js"></script>
	<script src="${request.contextPath}/config.js"></script>
	<#nested/>
</body>
</html>
</#macro>
