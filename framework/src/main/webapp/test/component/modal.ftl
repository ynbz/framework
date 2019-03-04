<div class="row margin-0">
	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					对话框
					<div class="btn btn-xs btn-danger pull-right modal1">显示</div>
				</h3>
			</div>
			<div class="panel-body modal-data1 padding-0" contenteditable="true" style="height: 350px; overflow: none;">
				<textarea class="compnent-data" style="width: 100%; height: 100%; resize: none; border: none;">
{
	size : 'lg',// lg sm normal
	showHead : true,
	showFoot : true,
	showBody : true,
	showOkBtn : true,
	showCancelBtn : true,
	ignoreBackgroundClick : true,
	backdrop : true,
	keyboard : true,
	icon : 'icon-exclamation-sign',
	title : '系统消息',
	okText : '确定',
	cancelText : '关闭',
	text : '系统消息',
	uri : '${request.contextPath}/test/guide',
	okClick : function() {
		alert('Ok clicked.');
	}
}
			</textarea>
			</div>
		</div>
	</div>
	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					带蒙板的提示框
					<div class="btn btn-xs btn-danger pull-right modal2">显示</div>
				</h3>
			</div>
			<div class="panel-body modal-data2 padding-0" contenteditable="true" style="height: 350px; overflow: none;">
				<textarea class="compnent-data" style="width: 100%; height: 100%; resize: none; border: none;">
{
	//size : 'sm',// lg sm normal
	keyboard : true,
	ignoreBackgroundClick : false,
	//text : '系统消息',
	//uri : '${request.contextPath}/test/guide'
}
			</textarea>
			</div>
		</div>
	</div>
	<div class="col-md-4 col-sm-4 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					蒙板
					<div class="btn btn-xs btn-danger pull-right modal3">显示</div>
				</h3>
			</div>
			<div class="panel-body modal-data3 padding-0" contenteditable="true" style="height: 350px; overflow: none;">
				<textarea class="compnent-data" style="width: 100%; height: 100%; resize: none; border: none;">
{
	ignoreBackgroundClick : false,
	keyboard : true
}
			</textarea>
			</div>
		</div>
	</div>
</div>

<!-- <script src="${request.contextPath}/js/suredy-modal.js"></script> -->
<script type="text/javascript">
	require([ 'suredyModal' ], function() {
		$('div.btn.modal1').on('click', function() {
			Suredy.Modal.showModal(eval('(' + $('div.panel-body.modal-data1 .compnent-data').val() + ')'));
		});

		$('div.btn.modal2').on('click', function() {
			Suredy.Modal.showProcessDialog(eval('(' + $('div.panel-body.modal-data2 .compnent-data').val() + ')'));
		});

		$('div.btn.modal3').on('click', function() {
			Suredy.Modal.showMask(eval('(' + $('div.panel-body.modal-data3 .compnent-data').val() + ')'));
		});
	});
</script>