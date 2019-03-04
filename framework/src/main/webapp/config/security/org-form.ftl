<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">
		<form name="form" id="form">
			<#if org??> <input type="hidden" name="id" id="id" value="${org.id}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">单位代码</div>
					<input type="text" class="form-control" id="code" name="code"
						value="${org.code}" placeholder="单位代码" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">单位名称</div>
					<input type="text" class="form-control" id="name" name="name"
						value="${org.name}" placeholder="单位名称" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">单位简称</div>
					<input type="text" class="form-control" id="alias" name="alias"
						value="${org.alias}" placeholder="单位简称" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort"
						value="${org.sort}" placeholder="显示顺序 默认0" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">单位描述</div>
					<textarea class="form-control" rows="3" id="description"
						name="description">${org.description}</textarea>
				</div>
			</div>
			</#if>
		</form>
		<div class="text-center">
			<div class="btn btn-info" id="btn-save">
				<i class="icon-save"></i> 保存
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	require(['suredyModal'], function(Modal) {
		if (!$('[name="code"]').val())
			$('[name="code"]').val(Suredy.randomString(10));
		
		$('#btn-save').click(function() {
			var code = $('#code').val();
			var name = $('#name').val();
			
			if (code == '') {
				alert('请填写代码！');
				return;
			}

			if (name == '') {
				alert('请填写名称！');
				return;
			}
	
			$.ajax({
				url : 'ou/org/save',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存单位信息失败！');
					} else if (!success.success) {
						alert('保存单位信息失败！\n\n' + success.msg);
					} else {
						alert('保存单位成功！');
						Suredy.loadContent('${request.contextPath}/config/'+$('#user-url').val());
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
