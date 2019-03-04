
<input type="checkbox" id="manualClose">
手动关闭
<hr>

<input type="text" class="datetimepicker" data-head="true" data-date="true" data-foot="true" data-format="yyyy-MM-dd" data-time="${.now}" readonly>
old version
<hr>

<input type="text" class="datetimepicker" data-format="yyyy-MM-dd" readonly>
默认
<hr>

<input type="text" class="datetimepicker" data-format="yyyy" data-view="Y" readonly>
只选年
<hr>

<input type="text" class="datetimepicker" data-format="yyyy-MM" data-view="M" readonly>
只选月
<hr>

<input type="text" class="datetimepicker" data-format="yyyy-MM-dd" data-view="D" readonly>
只选日期
<hr>

<input type="text" class="datetimepicker" data-format="HH:mm" data-view="T" readonly>
只选时间
<hr>

<input type="text" class="datetimepicker" data-format="yyyy-MM-dd HH:mm" data-view="D Y M T" readonly>
年月日时分
<hr>
<!-- <script src="${request.contextPath}/js/suredy-datetimepicker.js"></script> -->
<script type="text/javascript">
	require([ 'suredyDatetimepicker' ], function(Datetimepicker) {
		$('#manualClose').on('change', function() {
			$('.datetimepicker').data('manual-close', $(this).prop('checked'));

			Datetimepicker.AutoDelegate(true);
		});
	});
</script>