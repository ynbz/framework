
<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
	<form name="form" id="form">
	
		<input type="hidden" readonly="readonly" id="id" name="id" value="${flm.id}" />
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">流程名称</div>
						<input type="text" class="form-control" id="name" name="name" maxlength="50" placeholder="&lt;最多50字&gt;"  value="${flm.name}" />
					</div>
				</div>	
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">流程代号</div>
						<input type="text" class="form-control" id="code" name="code"  maxlength="50" placeholder="&lt;最多50字&gt;"  value="${flm.code}" />
					</div>
				</div>
			</div>
		</div>					
				
		<div class="text-center">
			<div class="btn btn-info" id="btn-save" >
				<i class="icon-save"></i> 保存
			</div>
		</div>	
	</form>	
	</div>
</div>
<script src="${request.contextPath}/js/suredy-tree.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btn-save').click(function() {
			var name = $('#name').val();
			var code = $('#code').val();
			if (name == '') {
				alert('请填写流程名称！');
				return;
			}
			
			if (code == '') {
				alert('请填写流程代号！');
				return;
			}

			$.ajax({
				url : '${request.contextPath}/config/flow/save-edit.do',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('操作失败！');
					} else if (!success.success) {
						alert('操作失败！\n\n' + success.msg);
					} else {
						alert('操作成功！');
						Suredy.loadContent('${request.contextPath}/config/flow/list.do');
						Suredy.Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
				}
			});
		});	
	});
</script>	
