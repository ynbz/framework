<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../../favicon.ico" rel="icon">
<title>水滴科技协同办公系统</title>

<link rel="stylesheet" href="core/css/bootstrap.min.css">
<link rel="stylesheet" href="core/css/font-awesome.min.css">
<link rel="stylesheet" href="core/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="css/suredy-ui.css">

<!--[if lt IE 9]>
<script src="core/js/html5shiv.min.js"></script>
<script src="core/js/respond.min.js"></script>
<![endif]-->
</head>
<body>
	<div class="container suredy-form">
		<div class="navbar navbar-default navbar-fixed-top">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#suredy-form-navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#" style="border-right: 1px solid #ddd;">
					<i class="icon-flag"></i>
					公司公告
				</a>
			</div>
			<div class="collapse navbar-collapse" id="suredy-form-navbar">
				<ul class="nav navbar-nav suredy-form-bar">
					<li>
						<a href="#">
							<i class="icon-save"></i>
							保存
						</a>
					</li>
					<li>
						<a href="#">
							<i class="icon-undo"></i>
							重置
						</a>
					</li>
					<li>
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
							<i class="icon-bar-chart"></i>
							按钮组
							<span class="icon-angle-down"></span>
						</a>
						<ul class="dropdown-menu" role="menu">
							<li>
								<a href="#">填写意见</a>
							</li>
							<li>
								<a href="#">发送</a>
							</li>
							<li>
								<a href="#">撤回</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="#">删除</a>
							</li>
						</ul>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="#" class="suredy-close-form">
							<i class="icon-remove close" style="margin-right: 10px;"></i>
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div role="tabpanel" class="suredy-tabs">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active">
					<a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab"> 表单 </a>
				</li>
				<li role="presentation">
					<a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">意见</a>
				</li>
				<li role="presentation">
					<a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">流转记录</a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="tab1">
					<form action="" method="post">
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">发文提名</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">主送单位</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">抄送单位</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">主办部门</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">发文单位</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">签发人</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">签发日期</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">拟稿人</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="row">
									<div class="col-md-6 col-xs-6">
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-addon">
													<span class="title-content">拟稿日起</span>
												</div>
												<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
											</div>
										</div>
									</div>
									<div class="col-md-6 col-xs-6">
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-addon">
													<span class="title-content">联系电话</span>
												</div>
												<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">发文类型</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">发文字号</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">紧急程度</span>
										</div>
										<select class="form-control" name="title">
											<option>紧急</option>
											<option>一般</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">会签时限</span>
										</div>
										<input type="text" class="form-control suredy-datetimepicker day" name="title" placeholder="格式：2015-06-07" value="" readonly>
										<div class="input-group-addon btn" style="border-left: 0;">
											<i class="icon-remove"></i>
										</div>
										<div class="input-group-addon btn">
											<i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">印发分数</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">印发时限</span>
										</div>
										<input type="text" class="form-control suredy-datetimepicker hour" name="title" placeholder="格式：08:09" value="" readonly>
										<div class="input-group-addon btn" style="border-left: 0;">
											<i class="icon-remove"></i>
										</div>
										<div class="input-group-addon btn">
											<i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">文件密级</span>
										</div>
										<select class="form-control" name="title">
											<option>保密</option>
											<option>公开</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">保密期限</span>
										</div>
										<input type="text" class="form-control suredy-datetimepicker all" name="title" placeholder="格式：2015-06-07 08:09" value="" readonly>
										<div class="input-group-addon btn" style="border-left: 0;">
											<i class="icon-remove"></i>
										</div>
										<div class="input-group-addon btn">
											<i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">保管期限</span>
										</div>
										<input type="text" class="form-control suredy-datetimepicker all" name="title" placeholder="格式：2015-06-07 08:09" value="" readonly>
										<div class="input-group-addon btn" style="border-left: 0;">
											<i class="icon-remove"></i>
										</div>
										<div class="input-group-addon btn">
											<i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">页数</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">主题词</span>
										</div>
										<input type="text" class="form-control" name="title" placeholder="最多100个汉字" value="">
										<div class="input-group-addon btn" style="border-width: 1px 0;">生成</div>
										<div class="input-group-addon btn">设置</div>
										<div class="input-group-addon btn">校验</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">备注</span>
										</div>
										<textarea class="form-control" rows="3" name="title" placeholder="最多100个汉字"></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">关联文件</span>
										</div>
										<div class="form-control" style="height: 100px; overflow-x: hidden;">
											<div class="row">
												<div class="col-md-12 col-xs-12">
													<div class="btn btn-default pull-right btn-xs">
														<i class="icon-minus-sign"></i>
													</div>
													<div class="btn btn-default pull-right btn-xs" style="margin-right: 5px;">
														<i class="icon-plus-sign"></i>
													</div>
													<span class="text-danger pull-right" style="margin-right: 5px;">关联文件仅作为参考资料，不作为文件附加</span>
												</div>
											</div>
											<hr style="margin: 5px 0 0 0;">
											<div class="row">
												<div class="col-md-12 col-xs-12">
													<input type="checkbox">
													附件1
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 col-xs-12">
													<input type="checkbox">
													附件1
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 col-xs-12">
													<input type="checkbox">
													附件1
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 col-xs-12">
													<input type="checkbox">
													附件1
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 col-xs-12">
													<input type="checkbox">
													附件1
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<span class="title-content">附件</span>
										</div>
										<div class="form-control">这里是附件信息</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div role="tabpanel" class="tab-pane" id="tab2">这里是意见栏</div>
				<div role="tabpanel" class="tab-pane" id="tab3">这里是流转记录</div>
			</div>
		</div>
	</div>
	<script src="core/js/jquery-1.11.3.min.js"></script>
	<script src="core/js/bootstrap.min.js"></script>
	<script src="core/js/bootstrap-datetimepicker.min.js"></script>
	<script src="core/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="js/suredy-datetimepicker.js"></script>
</body>
</html>
