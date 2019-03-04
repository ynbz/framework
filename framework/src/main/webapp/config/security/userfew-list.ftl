<div class="row">
	<div class="col-md-12">
		<div class="btn btn-default btn-sm new-org-btn">新建单位</div>
		<div class="btn btn-info btn-sm new-unit-btn hidden">新建部门</div>
		<div class="btn btn-info btn-sm new-user-btn hidden">新建用户</div>
		<div class="btn btn-warning btn-sm update-btn hidden">修改</div>
		<div class="btn btn-danger btn-sm delete-btn hidden">删除</div>
		<!-- 用户拼音初始化，仅仅对已存在用户使用，只做一次，平时此按钮隐藏 -->
		<div class="btn btn-default btn-sm" id="init-pinyin-btn">拼音简写初始化</div>
		<input type="hidden" id="user-url" value="userfew/list"/>
		<!-- div class="btn btn-success btn-sm role-btn hidden">角色</div -->
	</div>
</div>
<div style="margin-top: 35px; overflow: auto; position: absolute; top: 0; right: 0; left: 0; bottom: 0; padding: 5px;">
	<div class="unit-user-tree"></div>
</div>

<script type="text/javascript">
	require([ 'suredyTree', 'suredyList', 'suredyModal' ], function(Tree, List, Modal) {
		Tree('.unit-user-tree', '${request.contextPath}/config/ou/tree/true/true/false', {
			autoCollapse : false,
			leafCheckbox : true,
			folderCheckbox : false,
			inContainer : false,
			style : 'department'
		});

		$('.unit-user-tree').on(Tree.nodeClick, function(event, $node) {
			// ctrl btns
			var $this = $(this);
			var active = Tree.isActive($node);
			var flag = Tree.data($node, 'flag');
			var canNewUnit = active && ('org' === flag || 'unit' === flag);
			var canNewUser = active && ('unit' === flag);
			var canModify = active;
			var canRole = active && ('user' === flag);

			if (canNewUnit)
				$('.new-unit-btn').removeClass('hidden');
			else
				$('.new-unit-btn').addClass('hidden');

			if (canNewUser)
				$('.new-user-btn').removeClass('hidden');
			else
				$('.new-user-btn').addClass('hidden');

			if (canModify) {
				$('.update-btn').removeClass('hidden');
				$('.delete-btn').removeClass('hidden');
			} else {
				$('.update-btn').addClass('hidden');
				$('.delete-btn').addClass('hidden');
			}

			if (canRole) {
				$('.role-btn').removeClass('hidden');
				$('.permissions-btn').removeClass('hidden');
			} else {
				$('.role-btn').addClass('hidden');
				$('.permissions-btn').addClass('hidden');
			}
		});

		var editNode = function(options) {
			var op = $.extend({
				title : '创建节点',
				uri : false
			}, options);

			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : op.title,
				showFoot : false,
				uri : op.uri
			});
		};

		var delNode = function(options) {
			var op = $.extend({
				uri : false,
				data : {},
				text : '节点信息',
				typeName : '节点'
			}, options);

			var msg = '是否确认删除' + op.typeName + '【' + op.text + '】？\n\n提示：\n该操作将会删除该节点下的所有子节点！\n请谨慎操作！';

			if (!window.confirm(msg)) {
				return;
			}

			$.ajax({
				url : op.uri,
				type : 'POST',
				data : op.data,
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert('删除' + op.typeName + '失败！\n\n' + msg.msg);
					} else {
						alert('删除' + op.typeName + '成功！');
						Suredy.loadContent('${request.contextPath}/config/userfew/list');
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		};

		// new org
		$('.new-org-btn').on('click', function() {
			editNode({
				title : '创建单位',
				uri : '${request.contextPath}/config/ou/org/form'
			});
		});
		
		$('#init-pinyin-btn').on('click', function() {	
			Modal.showProcessDialog({});		
			$.ajax({
				url : '${request.contextPath}/config/user/pinyin',
				dataType : 'json',
				type : 'GET',
				success : function(success) {
					if (!success) {
						alert('初始化用户简拼信息失败！');
					} else if (!success.success) {
						alert('初始化用户简拼信息失败！\n\n' + success.msg);
					} else {
						alert('初始化用户简拼信息成功！');
					}
					Modal.closeProcessDialog();
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
					Modal.closeProcessDialog();
				}
			});	
		});

		// new unit
		$('.new-unit-btn').on('click', function() {
			editNode({
				title : '创建部门',
				uri : '${request.contextPath}/config/ou/unit/form'
			});
		});

		// new user
		$('.new-user-btn').on('click', function() {
			editNode({
				title : '创建用户',
				uri : '${request.contextPath}/config/user/form'
			});
		});

		// edit
		$('.update-btn').on('click', function() {
			var node = Tree.checked('.unit-user-tree');
			var nodeData = Tree.data(node);
			var ops = {
				title : '',
				uri : ''
			};

			if ('org' === nodeData.flag) {
				ops.title = '修改单位';
				ops.uri = '${request.contextPath}/config/ou/org/form?orgId=' + nodeData.id;
			} else if ('unit' === nodeData.flag) {
				ops.title = '修改部门';
				ops.uri = '${request.contextPath}/config/ou/unit/form?unitId=' + nodeData.id;
			} else if ('user' === nodeData.flag) {
				ops.title = '修改人员';
				ops.uri = '${request.contextPath}/config/user/form?userId=' + nodeData.id;
			}

			editNode(ops);

			return true;
		});

		// delete
		$('.delete-btn').on('click', function() {
			var node = Tree.checked('.unit-user-tree');
			var text = Tree.data(node, 'text');
			var nodeData = Tree.data(node);
			var ops = {
				uri : false,
				data : false,
				text : text,
				typeName : '节点'
			};

			if ('org' === nodeData.flag) {
				ops.uri = '${request.contextPath}/config/ou/org/delete';
				ops.data = {
					nodeId : nodeData.id
				};
				ops.typeName = '单位';
			} else if ('unit' === nodeData.flag) {
				ops.uri = '${request.contextPath}/config/ou/unit/delete';
				ops.data = {
					nodeId : nodeData.id
				};
				ops.typeName = '部门';
			} else if ('user' === nodeData.flag) {
				ops.uri = '${request.contextPath}/config/user/delete';
				ops.data = {
					userId : nodeData.id
				};
				ops.typeName = '用户';
			}

			delNode(ops);

			return true;
		});

		// role
		$('.role-btn').on('click', function() {
			var node = Tree.checked('.unit-user-tree');
			var nodeData = Tree.data(node);

			var uri = '${request.contextPath}/config/user/role?userId=' + nodeData.id;
			Modal.showModal({
				size : 'lg',
				icon : 'icon-magnet',
				title : '人员-角色映射',
				showFoot : false,
				uri : uri
			});
		});

		// permissions
		$('.permissions-btn').on('click', function() {
			var node = Tree.checked('.unit-user-tree');
			var nodeData = Tree.data(node);

			var uri = '${request.contextPath}/config/user/permission?userId=' + nodeData.id;
			Modal.showModal({
				size : 'lg',
				icon : 'icon-search',
				title : '人员-权限定义',
				showFoot : false,
				uri : uri
			});
		});
	});
</script>
