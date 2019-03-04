<link rel="stylesheet" type="text/css" href="${request.contextPath}/component/uploadify/webuploader.css" />
<div class="row">
	<div class="col-md-6 col-xs-6">
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon" id="upload-status">导入数据</div>
				<input id="fileField" name="fileField" type="text" class="form-control" placeholder="<未选择任何文件>" value="" readonly />
				<div class="input-group-addon btn btn-info" id="selector" style="border-left:none;">选择文件</div>
				<div class="input-group-addon btn btn-danger" id="import">合并计算</div>		
			</div>
		</div>
		<input type="hidden" name="storageField" id="storageField" value="OOXX" />
		<input type="hidden" name="batch" id="batch" value="2015-09" />				
	</div>		
</div>
<script type="text/javascript" src="${request.contextPath}/component/uploadify/webuploader.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/component/uploadify/upload-single.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$('#import').click(function() {
			var file = $('#fileField').val();
			var storage = $('#storageField').val();
			var batch = $('#batch').val();
			if (file == '') {
				alert('请选择要数据文件!');
				return;
			}
			var msg = '是否确认合并计算？\n\n\提示：\n\该操作将会XXX,且合并后不可撤销更改！\n\请谨慎操作！';
			if (!window.confirm(msg)) {
				return;
			}
			var para = {
					file:file,
					storage:storage,
					batch:batch
			}
			$.ajax({
				url : 'XXOO',
				type : 'POST',
				data : para,
				success : function(msg) {
					//TODO
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});			
								
		});	
				
	});
</script>	