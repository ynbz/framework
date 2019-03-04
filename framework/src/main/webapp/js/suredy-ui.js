$(function() {
	var resetPathNav = function($div) {
		var pUl = $div.parents('.secondmenu');
		var pathNav = $('ol.suredy-path-nav');
		var pathNavIco = '';
		var level1Text = null;
		var level2Text = null;

		if (pUl.length > 0) {
			// level2 menu
			pathNavIco = pUl.parent('li').children('div').children('.icon').attr('class');
			level1Text = $.trim(pUl.parent('li').children('div').text());
		} else {
			// level1 menu
			pathNavIco = $div.children('.icon').attr('class');
		}

		level2Text = $.trim($div.text());

		// reset path nav
		pathNav.children().remove();
		pathNav.append('<i class="' + pathNavIco + '" style="padding-right: 5px;"></i>');

		if (level1Text)
			pathNav.append('<li>' + level1Text + '</li>');

		if (level2Text)
			pathNav.append('<li>' + level2Text + '</li>');
	};

	// left menu click
	$('.suredy-left-nav').delegate('li>div:not(.nav-header)', 'click', function(event, reactive) {
		var $this = $(this);
		var $parentLi = $this.parent('li');

		// this ele has been activated.
		if (!reactive && $parentLi.hasClass('active'))
			return;

		// remove all class(active)
		$('.suredy-left-nav li.active').removeClass('active');

		// set current ele class(active)
		$parentLi.addClass('active');

		// reset path nav
		resetPathNav($this);

		// load html
		$.suredy.loadContent($this.data('uri'));
	});

	// close button event
	$('.suredy-close-form').delegate('.close', 'click', function(event) {
		window.close();
	});

	// focus current active ele
	$('.suredy-left-nav li.active>div').trigger('click', true);

	// update user pswd btn click
	$('.suredy-user .suredy-update-password').on('click', function(event) {
		var $this = $(this);
		$.suredy.showModal({
			icon : 'icon-key',
			title : '修改密码',
			showFoot : false,
			uri : $this.data('uri')
		});
	});
	
});