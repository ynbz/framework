<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">
		<form name="form" id="form">
			<#if role??> <input type="hidden" name="id" id="id"
				value="${role.id}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">所属单位</div>
					<select class="form-control" name="org" id="org">
						<option value="">==请选择==</option> <#if orgs??> <#list orgs as org>
						<option value="${org.id}" ${(role.orgId==
							org.id) ? string('selected=\"selected\"', '')}>${org.name}</option>
						</#list> </#if>
					</select>
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">角色代码</div>
					<input type="text" class="form-control" id="code" name="code"
						value="${role.code}" placeholder="角色代码" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">角色名称</div>
					<input type="text" class="form-control" id="name" name="name"
						value="${role.name}" placeholder="角色名称" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">角色简称</div>
					<input type="text" class="form-control" id="alias" name="alias"
						value="${role.alias}" placeholder="角色简称" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort"
						value="${role.sort}" placeholder="显示顺序 默认0" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">角色描述</div>
					<textarea class="form-control" rows="3" id="description"
						name="description">${role.description}</textarea>
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
		$('#btn-save').click(function() {
			var org = $('#org').val();
			var code = $('#code').val();
			var name = $('#name').val();
			
			if (org == '') {
				alert('请选择单位！');
				return;
			}
			if (code == '') {
				alert('请填写代码！');
				return;
			}

			if (name == '') {
				alert('请填写名称！');
				return;
			}

			$.ajax({
				url : 'role/save',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存角色失败！');
					} else if (!success.success) {
						alert('保存角色失败！\n\n' + success.msg);
					} else {
						alert('保存角色成功！');
						Suredy.loadContent('role/list');
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
