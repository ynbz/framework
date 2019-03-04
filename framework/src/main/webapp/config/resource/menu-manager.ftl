

	<div class="col-md-12 col-sm-12" style="background-color: #fff; height: 44px; line-height: 44px; border-left: 1px solid #ddd; border-right: 1px solid #ddd;">
		<i style="font-size: 20px; margin: 0 10px;">菜单资源管理</i> 
		<span>
			<div class="btn btn-info btn-sm" id="create" style="margin-top: -8px">新建菜单</div>
			<div class="btn btn-success btn-sm hidden" id="edit" style="margin-top: -8px">修改选中菜单</div>
			<div class="btn btn-danger btn-sm hidden" id="remove" style="margin-top: -8px">删除选中菜单</div>
		</span>
	</div>


<div class="col-md-12 col-sm-12 menu-tree" style="position: absolute; left: 0px; right: 0px; bottom: 0; overflow: auto; top: 43px;">加载中......</div>

<script type="text/javascript">
	require([ 'suredyTree', 'suredyModal' ], function(Tree, Modal) {
		Tree('.menu-tree', 'menu/tree', {
			autoCollapse : false,
			inContainer : false,
			size : 'sm',
		});

		// 取消active状态
		var dischecked = function() {
			var $checked = Tree.checked('.menu-tree');

			if ($checked.length)
				Tree.toggleActive($checked);

			$('#edit').addClass('hidden');
			$('#remove').addClass('hidden');
			$('#create').text('新建菜单');
		};
		dischecked();

		$('.menu-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
			if (Tree.isActive($node)) {
				$('#edit').removeClass('hidden');
				$('#remove').removeClass('hidden');
				$('#create').text('在“' + Tree.data($node, 'text') + '”下创建子菜单');
			} else {
				$('#edit').addClass('hidden');
				$('#remove').addClass('hidden');
				$('#create').text('新建菜单');
			}
		});

		$('#create').on('click', function() {
			var $checked = Tree.checked('.menu-tree');
			var parentId = '';

			if ($checked.length)
				parentId = Tree.data($checked, 'id');

			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建菜单',
				showFoot : false,
				uri : 'menu/edit?parentId=' + parentId
			});
		});

		// edit menu btn click
		$('#edit').on('click', function() {
			var $checked = Tree.checked('.menu-tree');
			var menuId = '';

			if ($checked.length)
				menuId = Tree.data($checked, 'id');

			if (!menuId) {
				alert('[警告] 未选择菜单节点，或者菜单节点不可编辑！');
				return;
			}

			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title : '修改菜单',
				showFoot : false,
				uri : 'menu/edit?id=' + menuId
			});

		});

		// remove btn click
		$('#remove').on('click', function() {
			var $checked = Tree.checked('.menu-tree');
			var menuId = '';

			if ($checked.length)
				menuId = Tree.data($checked, 'id');

			if (!menuId) {
				alert('请选择需要删除的菜单节点！');
				return;
			}

			if (!window.confirm('是否确认删除【选中的节点】？\n\n提示：\n该操作将会删除菜单下的所有子节点！\n请谨慎操作！'))
				return;

			$.ajax({
				url : 'menu/delete',
				type : 'post',
				data : {
					menuId : menuId
				},
				success : function(data) {
					if (!data || !data.success) {
						alert('删除菜单节点失败！\n\n' + ((data && data.msg) || ''));
						return;
					}

					$('.menu-tree').trigger('reloadtree');

					alert("删除菜单节点成功！");
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		});

		$('.menu-tree').on('reloadtree', function(event, id) {
			Tree.reload('.menu-tree');
			dischecked();

			if (id) {
				Tree.nodes('.menu-tree').each(function(i, dom) {
					var $n = $(dom);
					if (id == Tree.data($n, 'id')) {
						$n.find('>.node-info>.node-info-item.text').trigger('click');
					}
				});
			}
		});
	});
</script>