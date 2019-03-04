;
require([ 'suredyTreeSelector', 'suredyCookie' ], function(TreeSelector, Cookies) {
	var loadUser = function(){
		var userCode = Cookies.get('userCode');
		var userName = Cookies.get('userName');
		if (userCode) {
			$('input[name="userCode"]').val(userCode);
		}
		if (userName) {
			$('input#userName').val(userName);
		}
	};
	
	loadUser();
	$('.suredy-checker-img').on('click', function(event) {
		var $this = $(this);
		$this.attr('src', $this.attr('src') + '?_=' + Suredy.randomNumberString());
	});

	// init user tree
	TreeSelector('input#userName', Suredy.ctxp + '/login-data/asyn-org/1/0', {
		autoCollapse : false,
		style : 'department',
		showTitle : true,
		canFolderActive : false,
		size : 'lg',
		toggleActive : false,
		asynLoadable : true,
	});

	// 监听树的点击事件
	$('input#userName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('input[name="userCode"]').val('');
			$('input#userName').val('');
			return false;
		}

		var userCode = TreeSelector.data($node, 'userCode');
		var name = TreeSelector.data($node, 'name');

		$('input[name="userCode"]').val(userCode);
		$('input#userName').val(name);

		Cookies.set('input[name="userCode"]', userCode); 
		Cookies.set('input#userName', name);
		TreeSelector.hidden(this);

		return true;
	});
});
