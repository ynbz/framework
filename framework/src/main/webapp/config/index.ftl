<@t.header> </@t.header> <@t.body>
<div class="navbar navbar-default navbar-fixed-top padding-0 margin-0">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#left-menu-collapse" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">管理控制台</a>
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
	require(['suredyTreeMenu','suredyModal'], function(TreeMenu, Modal) {
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
			icon : 'icon-home',
			text : '安全管理',
			children : [  {
				icon : 'icon-sitemap',
				text : '组织机构',
				uri : 'userfew/list.do'
			}, {
				icon : 'icon-sitemap',
				text : '组织机构',
				uri : 'usermuch/list.do'
			}, {
				icon : 'icon-user',
				text : '角色管理',
				uri : 'role/list.do'
			},{
				icon : 'icon-magnet',
				text : '基本权限',
				uri : 'basic/permission.do'
			 },{
					icon : 'icon-file',
					text : '登录记录',
					uri : 'loginlog/list.do'
			}]
		}, {
			collapse : false,
			text : '资源管理',
			icon : 'icon-qrcode',
			children : [ {
				icon : 'icon-list-alt',
				text : '菜单资源',
				uri : 'menu/manager.do'
			},{
				icon : 'icon-star',
				text : '单点控制',
				uri : 'segment/manager.do'
			},{
				icon : 'icon-list-ul',
				text : '报表资源',
				uri : 'report/manager.do'
			},{
				icon : 'icon-inbox',
				text : '数据库操作',
				uri : 'dbmanage/backupsDBList.do'
			},{
				icon : 'icon-pencil',
				text : '流程管理',
				uri : 'flow/list.do'
			} ,{
				icon : 'icon-file',
				text : '文件类型',
				uri : 'form/list.do'
			},{
				icon : 'icon-file',
				text : '模板管理',
				uri : 'templatemanage/templateList.do'
			},{
				icon : 'icon-edit',
				text : '表单管理',
				uri : '${request.contextPath}/component/formbuilder/list-index.html'
			}, {
				active : true,
				icon : 'icon-file',
				text : '文件管理',
				uri : '${request.contextPath}/files/manager'
			}, {
				icon : 'icon-rss',
				collapse : true,
				text : '内容管理',
				children : [{
						text : '栏目管理',
						uri : '${request.contextPath}/app/cms/channel/manager'
					}, {
						text : '文章管理',
						uri : '${request.contextPath}/app/cms/article/manager'						
					}, {
						text : '广告管理',
						uri : '${request.contextPath}/app/cms/banner/manager'							
					}, , {
						text : '模版管理',
						uri : '${request.contextPath}/app/cms/template/manager'							
					}
				]
			}  ]
		} ];

		TreeMenu('.left-tree-menu', data, {
			size: 'lg'
		});
	});
</script>
</@t.foot>
