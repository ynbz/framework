<@t.header>
<meta property="type" value="login">
<link rel="stylesheet" href="${request.contextPath}/css/login.css" />
</@t.header> <@t.body>
<div class="container-fluid">
	<div class="row logo">
		<div class="col-md-12 text-center">
			<img src="${request.contextPath}/img/login/Sys_logo.png" />
		</div>
	</div>
	<form action="login" method="post">
		<input type="hidden" name="startupFlow" value="false" />
		<div class="row">
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
				<div class="form-group">
					<div class="input-group select-user">
						<div class="input-group-addon">
							<div class="login-form-icon">
								<i class="icon-user icon-2x" style="color: #21ade5"></i>
							</div>
						</div>
						<input type="hidden" name="userCode" value="${(userCode)!''}" />
						<input id="userName" class="form-control input-lg" type="text" value="${(userName)!''}" placeholder="&lt;请选择登录用户&gt;" readonly />
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">
							<div class="login-form-icon">
								<i class="icon-key icon-2x" style="color: #8fc53c"></i>
							</div>
						</div>
						<input placeholder="&lt;请输入您的密码&gt;" class="form-control input-lg" type="password" name="password" />
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">
							<div class="login-form-icon">
								<i class="icon-lock icon-2x"></i>
							</div>
						</div>
						<input placeholder="&lt;请输入验证码&gt;" class="form-control input-lg" type="text" name="checkCode" />
						<div class="input-group-addon btn" style="padding: 0;">
							<img class="suredy-checker-img" alt="点击刷新" src="${request.contextPath}/check-img/133/44">
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
				<input class="btn btn-success btn-lg btn-block" type="submit" value="登&nbsp;&nbsp;录" />
			</div>
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
				<div class="text-center bg-danger text-info" id="errMsg">${err_msg}</div>
			</div>
		</div>
	</form>
</div>
</@t.body> <@t.foot>
<img class="bg-top" src="${request.contextPath}/img/login/bg-top.png" />
<img class="bg-bottom" src="${request.contextPath}/img/login/bg-bottom.png" />
<script src="${request.contextPath}/js/login.js"></script>
</@t.foot>
