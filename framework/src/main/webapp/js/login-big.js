;
require([ 'suredyTreeSelector' ], function(TreeSelector) {
	$('.suredy-checker-img').on('click', function(event) {
		var $this = $(this);
		$this.attr('src', $this.attr('src') + '?_=' + (new Date().getTime()));
	});

	// init user tree
	$.ajax({
		url : Suredy.ctxp + '/login-data/org-tree',
		dataType : 'json',
		success : function(data, textStatus, jqXHR) {
			TreeSelector('input#unitName', data, {
				autoCollapse : false,
				style : 'department',
				showTitle : true,
				canFolderActive : true,
				size : 'lg',
				toggleActive : false
			});
		}
	});
	// 监听组织树的点击事件
	$('input#unitName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('input#unitId').val('');
			$('input#unitName').val('');
			return false;
		}

		var unitId = TreeSelector.data($node, 'id');
		var unitName = TreeSelector.data($node, 'name');

		$('input#unitId').val(unitId);
		$('input#unitName').val(unitName);

		TreeSelector.hidden(this);

	
		TreeSelector('input#userName', Suredy.ctxp + '/login-data/user-tree?unit=' + unitId, {
			autoCollapse : false,
			style : 'department',
			showTitle : true,
			canFolderActive : false,
			size : 'lg',
			toggleActive : false
		});
	
		
		return true;

	});
	$('input#unitName').on('click', function() {
		TreeSelector.hidden('input#userName');
	})
	
	//监听用户列表点击事件
	$('input#userName').off(TreeSelector.nodeClick);
	$('input#userName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('input#userCode').val('');
			$('input#userName').val('');
			return false;
		}

		var userCode = TreeSelector.data($node, 'userCode');
		var userName = TreeSelector.data($node, 'name');

		$('input#userCode').val(userCode);
		$('input#userName').val(userName);

		TreeSelector.hidden(this);

		return true;

	});


	
});
