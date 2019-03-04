<div class="row margin-0">
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					菜单数据
					<div class="btn btn-default btn-xs auto-collapse active autoCollapse" data-toggle="button" autocomplete="off">自动折叠</div>
					<div class="btn btn-default btn-xs auto-collapse active triggerUrl" data-toggle="button" autocomplete="off">加载URL内容</div>
					<div class="btn btn-default btn-xs auto-collapse alwaysActive" data-toggle="button" autocomplete="off">总是允许选中节点</div>
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-xs btn-default">
							<input type="radio" name="size" id="option1" value="xl" autocomplete="off">
							超大
						</label>
						<label class="btn btn-xs btn-default">
							<input type="radio" name="size" id="option1" value="lg" autocomplete="off">
							大
						</label>
						<label class="btn btn-xs btn-default active">
							<input type="radio" name="size" id="option1" value="normal" autocomplete="off" checked>
							中（默认）
						</label>
						<label class="btn btn-xs btn-default">
							<input type="radio" name="size" id="option1" value="sm" autocomplete="off">
							小
						</label>
					</div>
					<div class="btn btn-xs btn-danger pull-right redo">重绘</div>
				</h3>
			</div>
			<div class="panel-body tree-menu-data padding-0" contenteditable="true" style="height: 550px; overflow: none;">
				<textarea class="compnent-data" style="width: 100%; height: 100%; resize: none; border: none;">
[ {
	collapse : true,
	active : false,
	icon : 'icon-wrench',
	text : '一级',
	badge : '<i class="icon-heart-empty">&nbsp;9527</i>',
	children : [ {
	collapse : true,
		text : '二级',
		children : [ {
		collapse : true,
			active : true,
			text : '三级',
			// uri : 'need a uri'
		}, {
			collapse : true,
			text : '三级',
		} ]
	}, {
		collapse : true,
		text : '二级',
	} ]
}, {
	collapse : true,
	text : '一级',
	children : [ {
		collapse : true,
		text : '二级',
		children : [ {
			collapse : true,
			text : '三级',
		}, {
			collapse : true,
			text : '三级',
		} ]
	}, {
		collapse : true,
		text : '二级',
	}, {
		collapse : true,
		text : '二级',
		children : [ {
			collapse : true,
			text : '三级',
		}, {
			collapse : true,
			text : '三级',
		} ]
	} ]
} ]
</textarea>
			</div>
		</div>
	</div>
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">菜单</h3>
			</div>
			<div class="panel-body tree-menu-test" style="padding: 0px;"></div>
		</div>
	</div>
</div>
<!-- <script src="${request.contextPath}/js/suredy-tree-menu.js"></script> -->
<script type="text/javascript">
	require([ 'suredyTreeMenu' ], function() {
		$('div.btn.redo').on('click', function() {
			Suredy.TreeMenu('.tree-menu-test', function() {
				return eval('(' + $('.compnent-data').val() + ')')
			}, {
				autoCollapse : $('.btn.auto-collapse.autoCollapse.active').length > 0,
				triggerUrl : $('.btn.auto-collapse.triggerUrl.active').length > 0,
				alwaysActive : $('.btn.auto-collapse.alwaysActive.active').length > 0,
				size : $('[name="size"]:checked').eq(0).val()
			});

			$('.tree-menu-test').on(Suredy.TreeMenu.nodeClick, function(event, $node) {
				var data = Suredy.TreeMenu.data($node);

				console.dir(data);
			});
		});

		$('div.btn.redo').trigger('click');
	});
</script>