<@t.header> </@t.header> <@t.body>
<div class="navbar navbar-default navbar-fixed-top padding-0 margin-0">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#left-menu-collapse" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">水滴科技开发框架</a>
		</div>
		<p class="navbar-text navbar-right hidden-xs">
			<a href="${request.contextPath}/logout" class="navbar-link">
				<i class="icon-signout"></i>
				退出
			</a>
			&nbsp;
		</p>

		<div class="collapse navbar-collapse" id="left-menu-collapse">
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
<script type="text/javascript">
	require([ 'suredyTreeMenu', 'suredyModal' ], function(TreeMenu, Modal) {
		$('i.user-config').on('click', function() {
			Modal.showModal({
				title : '修改密码',
				showFoot : false,
				icon : 'icon-cog',
				uri : '${request.contextPath}/user-config'
			});
		});

		var data = [ {
			collapse : true,
			icon : 'icon-wrench',
			text : 'system config',
			children : [ {
				icon : 'icon-wrench',
				text : 'xml config',
				uri : '${request.contextPath}/test/guide'
			} ]
		}, {
			collapse : true,
			icon : 'icon-bar-chart',
			text : 'Charts',
			children : [ {
				icon : 'icon-bar-chart',
				text : 'ECharts.js',
				uri : '${request.contextPath}/test/chart/echarts-charts'
			}, {
				icon : 'icon-bar-chart',
				text : '动态数据',
				uri : '${request.contextPath}/test/chart/echarts-charts-dmic'
			} ]
		}, {
			collapse : false,
			text : '组件',
			uri : '${request.contextPath}/test/component',
			children : [ {
				text : 'TreeMenu',
				uri : '${request.contextPath}/test/component/tree-menu'
			}, {
				text : 'TreeSelector',
				uri : '${request.contextPath}/test/component/tree-selector'
			}, {
				text : 'Tree',
				uri : '${request.contextPath}/test/component/tree'
			}, {
				text : 'List',
				uri : '${request.contextPath}/test/component/list'
			}, {
				text : 'Modal',
				uri : '${request.contextPath}/test/component/modal'
			}, {
				text : 'Datetime Picker',
				uri : '${request.contextPath}/test/component/datetime-picker'
			}, {
				text : 'Todo',
				uri : '${request.contextPath}/todo/todoList'
			}, {
				text : 'Validation',
				uri : '${request.contextPath}/test/component/validation'
			}, {
				text : 'QRCode',
				uri : '${request.contextPath}/test/component/qrcode'
			}, {
				active : true,
				text : 'FileUpload',
				uri : '${request.contextPath}/test/component/fileupload.html'
			} ]
		} ];
		TreeMenu('.left-tree-menu', data, {
			size : 'lg'
		});
	});
</script>
</@t.foot>
