;
require.config({
	baseUrl : ctxp || '/',
	paths : {
		// lib
		echarts:'./core/js/echarts.min',
		moment : './core/js/moment.min',
		jqueryForm : './core/js/jquery.form',
		jqueryUI : './core/js/jquery-ui.min',
		bootstrap : './core/js/bootstrap',
		notify : './core/js/bootstrap-notify.min',
		validation : './core/js/bootstrapValidator.min',
		formBuilder : './core/js/form-builder.min',
		formRender : './core/js/form-render.min',
		wysiwyg:'./core/js/bootstrap-wysiwyg',
		hotkeys:'./core/js/jquery.hotkeys',
		chalk:'./js_new/map/chalk',
		// suredy's js
		suredy : './js_new/suredy',
		suredyTreeMenu : './js_new/suredy-tree-menu',
		suredyTree : './js_new/suredy-tree',
		suredyList : './js_new/suredy-list',
		suredyModal : './js_new/suredy-modal',
		suredyDatetimepicker : './js_new/suredy-datetimepicker2',
		suredyTreeSelector : './js_new/suredy-tree-selector',
		suredyFile : './js_new/suredy-file',
		suredyFormBuilder : './js_new/suredy-formbuilder',
		suredyCookie : './js_new/suredy-cookie'
	},
	shim : {
		bootstrap : [ 'jquery' ],
		notify : [ 'bootstrap' ],
		validation : [ 'bootstrap' ],
		formBuilder : [ 'jqueryUI' ],
	}
});

require([ 'suredy', 'bootstrap' ], function($d) {
	// init context path to Suredy
	$d.contextPath = ctxp;
	$d.ctxp = ctxp;
});