<@t.header>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/core/css/bootstrap-datetimepicker.css" />
</@t.header> <@t.body>
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<div class=" btn navbar-toggle collapsed" data-toggle="collapse" data-target="#left-menu-collapse" aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</div>
			<div class="navbar-brand" href="#">水滴科技开发框架</div>
		</div>

		<div class="collapse navbar-collapse" id="left-menu-collapse">
			<p class="navbar-text navbar-right">
				<i class="icon-signout"></i>
				<a href="${request.contextPath}/logout" class="navbar-link"> 退出 </a>
				&nbsp;
			</p>
			<p class="navbar-text navbar-right">
				<i class="icon-leaf"></i>
				<a href="${request.contextPath}/config/index" class="navbar-link"> 系统配置 </a>
			</p>
			<div class="left-menu">
				<div class="user-info">
					<i class="icon-user"></i>
					${Session['login_user'].name}
					<i class="icon-cog text-primary user-config" style="cursor: pointer;"></i>
				</div>

				<div class="menu-info">
					<div class="left-tree-menu"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid index-container suredy-content"></div>
</@t.body> <@t.foot>
<script src="${request.contextPath}/core/js/bootstrap-datetimepicker.js"></script>
<!-- <script src="${request.contextPath}/js/suredy-tree-menu.js"></script>
<script src="${request.contextPath}/js/suredy-modal.js"></script> -->
<script type="text/javascript">
	require([ 'suredyModal', 'suredyTreeMenu' ], function(Modal, TreeMenu) {
		$('i.user-config').on('click', function() {
			Modal.showModal({
				title : '修改密码',
				showFoot : false,
				icon : 'icon-cog',
				uri : '${request.contextPath}/user-config'
			});
		});

		TreeMenu('.left-tree-menu', '${request.contextPath}/config/menu/user', {
			size : 'lg'
		});
	});
</script>
</@t.foot>
