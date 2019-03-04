<div class="row margin-0">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Selector</h3>
			</div>
			<div class="panel-body tree-test padding-0" style="">
				<input type="text" class="tree-selector-test-on-modal" style="width: 100%;" />
			</div>
		</div>
	</div>
</div>
<!-- <script src="${request.contextPath}/js/suredy-tree.js"></script> -->
<script type="text/javascript">
	require([ 'suredyTreeSelector', 'suredyModal' ], function(Selector, Modal) {
		Selector('.tree-selector-test-on-modal', function() {
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

		$('.tree-selector-test-on-modal').on(Selector.nodeClick, function(event, $node) {
			var text = Selector.data($node, 'text');

			console.log('active status: ' + Selector.isActive($node));
			console.log('active status: ' + Selector.isChecked($node));
			console.log(text);
			console.dir(Selector.data($node));
			console.log(Selector.checked($('.tree-selector-test')));

			$(this).val(text);
		});
	});
</script>