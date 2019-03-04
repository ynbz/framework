<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
		<form name="form" id="form">	
		<#if data??>	
			<input type="hidden" name="id" id="id" value="${data.id}">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">资源名称</div>
					<input type="text" class="form-control" id="name" name="name" value="${data.name}" />
				</div>
			</div>
		
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">资源标识</div>
					<input type="text" class="form-control" id="uri" name="uri" value="${data.uri}" />
				</div>
			</div>		
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${data.sort}"/>
				</div>
			</div>	
		</#if>			
		</form>	
	</div>
</div>
<div class="text-center">
	<div class="btn btn-info" id="btn-save" >
		<i class="icon-save"></i> 保存
	</div>
</div>		
<script type="text/javascript">
	require(['suredyModal'], function(Modal) {
		$('#btn-save').click(function() {
			var uri = $('#uri').val();
			var name = $('#name').val();
			if (uri == '') {
				alert('请填写资源标识符！');
				return;
			}

			if (name == '') {
				alert('请填写资源名称！');
				return;
			}

			$.ajax({
				url : 'segment/save',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存单点控制信息失败！');
					} else if (!success.success) {
						alert('保存单点控制信息失败！\n\n' + success.msg);
					} else {
						alert('保存单点控制信息成功！');
						Suredy.loadContent('segment/manager');
						Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
				}
			});
		});
	});
</script>	
