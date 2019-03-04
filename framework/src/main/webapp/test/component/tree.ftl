<div class="row margin-0">
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					树数据
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default btn-xs active">
							<input name="autoCollapse" type="checkbox" autocomplete="off" checked>
							自动折叠
						</label>
						<label class="btn btn-default btn-xs">
							<input name="multiselect" type="checkbox" autocomplete="off">
							多选
						</label>
						<label class="btn btn-default btn-xs">
							<input name="leafCheckbox" type="checkbox" autocomplete="off">
							子节点复选框
						</label>
						<label class="btn btn-default btn-xs">
							<input name="folderCheckbox" type="checkbox" autocomplete="off">
							父节点复选框
						</label>
						<label class="btn btn-default btn-xs active">
							<input name="canLeafActive" type="checkbox" autocomplete="off" checked>
							子节点可选中
						</label>
						<label class="btn btn-default btn-xs active">
							<input name="canFolderActive" type="checkbox" autocomplete="off" checked>
							父节点可选中
						</label>
						<label class="btn btn-default btn-xs">
							<input name="collapseAll" type="checkbox" autocomplete="off">
							收起所有节点
						</label>
						<label class="btn btn-default btn-xs">
							<input name="showTitle" type="checkbox" autocomplete="off">
							显示title
						</label>
						<label class="btn btn-default btn-xs active">
							<input name="toggleActive" type="checkbox" autocomplete="off" checked>
							toggle active
						</label>
					</div>
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default btn-xs">
							<input type="radio" name="size" autocomplete="off" value="xl">
							超大
						</label>
						<label class="btn btn-default btn-xs">
							<input type="radio" name="size" autocomplete="off" value="lg">
							大
						</label>
						<label class="btn btn-default btn-xs active">
							<input type="radio" name="size" autocomplete="off" value="normal" checked>
							正常
						</label>
						<label class="btn btn-default btn-xs">
							<input type="radio" name="size" autocomplete="off" value="sm">
							小
						</label>
					</div>
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-default btn-xs">
							<input type="radio" name="style" autocomplete="off" value="file">
							文件树
						</label>
						<label class="btn btn-default btn-xs">
							<input type="radio" name="style" autocomplete="off" value="department">
							组织树
						</label>
						<label class="btn btn-default btn-xs active">
							<input type="radio" name="style" autocomplete="off" value="normal" checked>
							默认
						</label>
					</div>
					<div class="btn btn-xs btn-danger pull-right redo">重绘</div>
				</h3>
			</div>
			<div class="panel-body tree-data padding-0" contenteditable="true" style="height: 550px; overflow: none;">
				<textarea class="compnent-data" style="width: 100%; height: 100%; resize: none; border: none;">
[ {
	text : '节点1',
	data : {
		a : 1
	},
	children : [ {
		text : '节点11',
		children : [ {
			active : true,
			text : '节点111',
			data : {
				a : 2
			},
		}, {
			checked : true,
			text : '节点112',
		} ]
	}, {
		text : '节点12',
		children : []
	}, {
		text : '节点12'
	} ]
}, {
	text : '节点2',
	icon : 'icon-caret-down',
	children : [ {
		text : '节点21',
	}, {
		text : '节点22',
	} ]
} ]
				</textarea>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">树</h3>
			</div>
			<div class="panel-body tree-test padding-0" style=""></div>
		</div>
	</div>
</div>
<!-- <script src="${request.contextPath}/js/suredy-tree.js"></script> -->
<script type="text/javascript">
	require([ 'suredyTree' ], function() {
		$('div.btn.redo').on('click', function() {
			Suredy.Tree('.tree-test', function() {
				return eval('(' + $('.compnent-data').val() + ')');
			}, {
				autoCollapse : $('[name="autoCollapse"]:checked').length > 0,
				multiselect : $('[name="multiselect"]:checked').length > 0,
				leafCheckbox : $('[name="leafCheckbox"]:checked').length > 0,
				folderCheckbox : $('[name="folderCheckbox"]:checked').length > 0,
				canLeafActive : $('[name="canLeafActive"]:checked').length > 0,
				canFolderActive : $('[name="canFolderActive"]:checked').length > 0,
				collapseAll : $('[name="collapseAll"]:checked').length > 0,
				size : $('[name="size"]:checked').val(),
				style : $('[name="style"]:checked').val(),
				showTitle : $('[name="showTitle"]:checked').length > 0,
				toggleActive : $('[name="toggleActive"]:checked').length > 0
			});

			$('.tree-test').on(Suredy.Tree.nodeClick, function(event, $node) {
				var text = Suredy.Tree.data($node, 'text');

				console.log('active status: ' + Suredy.Tree.isActive($node));
				console.log('active status: ' + Suredy.Tree.isChecked($node));
				console.log(text);
				console.dir(Suredy.Tree.data($node));
				console.log(Suredy.Tree.checked($('.tree-test')));
			});
		});

		$('div.btn.redo').trigger('click');
	});
</script>