<div class="panel panel-default">
	<div class="panel-heading">
		<div class="text-left">				
			<button class="btn btn-primary" id="btn-pdf" type="button">PDF格式</button>
			<button class="btn btn-warning" id="btn-word" type="button">Word格式</button>
			<button class="btn btn-success" id="btn-excel" type="button">Excel格式</button>
		</div>
	</div>
	<div class="panel-body">

		<div class="row">
		<#if result??>
			<iframe src="${result}" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="0" id="frame1" name="frame1"></iframe>
		<#else>
			<div class="text-center"><h4>报表并未正确运行,请检查相关输入参数...</h4></div>
		</#if>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>
<form id="form1" style="display:none;"  method="post">
	<input type="hidden" name="reportId" id="reportId" value="${reportId}" />
	<input type="hidden" name="fileId" id="fileId" value="${fileId}" />
</form>
<script type="text/javascript">
	$(document).ready(function() {
		$('#frame1').load(function() {
			$(this).height(0); //用于每次刷新时控制IFRAME初始化 
			var height = $(this).contents().height() + 20;
			$(this).height(height < 300 ? 300 : height);
		});

		$('#btn-pdf').click(function() {
			var form = $('#form1');
			form.attr('target', '');
			form.attr('action', 'report/run/pdf');
			form.submit();
		});


		$('#btn-word').click(function() {
			var form = $('#form1');
			form.attr('target', '');
			form.attr('action', 'report/run/word');
			form.submit();
		});

		$('#btn-excel').click(function() {
			var form = $('#form1');
			form.attr('target', '');
			form.attr('action', 'report/run/excel');
			form.submit();
		});
	});
</script>
