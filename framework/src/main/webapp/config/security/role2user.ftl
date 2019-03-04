<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
	<form name="form" id="form">
	<#if role??>
		<input type="hidden"  name="id" value="${role.id}" />
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon">角色代码</div>
				<input type="text" class="form-control" name="name" readonly="readonly" value="${role.uniqueCode}" />
			</div>
		</div>
		<div class="form-group">	
			<div class="input-group">
				<div class="input-group-addon">角色名称</div>
				<input type="text" class="form-control" name="name" readonly="readonly" value="${role.name}" />
			</div>
		</div>
	</#if>
	
	<#if relations??>
		<#list relations as relation>
		<input type="hidden" name="userId" value="${relation.userId}" />
		</#list>
		<div class="users-tree"></div>
	</#if>
		<div class="text-center" style="margin-top: 10px;">
			<button class="btn btn-info" id="btn-save" type="button">
				<i class="icon-save"></i>
				保存
			</button>
		</div>	
	</form>	
	</div>
</div>
<script type="text/javascript">
	require(['suredyTree'], function(Tree) {
		Tree('.users-tree', '${request.contextPath}/config/ou/tree/true/true/true', {
			autoCollapse:false,
			multiselect:true,
			leafCheckbox:true,
			canFolderActive:false,
			inContainer:false,
			size:'sm',
			style:'department'
		});

		$('.users-tree').on(Tree.nodeClick, function(event, $node) {
			var node = $node;
			var id = Tree.data(node, 'id');
			var dom = $('[name="userId"][value="' + id + '"]');
			var form = $('#form');
			
			if (Tree.isActive(node)) {
				if (dom.length !== 1)
					form.append('<input type="hidden" name="userId" value="' + id + '" />');
			} else {
				if (dom.length === 1)
					dom.remove();
			}
		});
		
		// init tree selection
		var userIds = [];
		$('[name="userId"]').each(function(i) {
			userIds[i] = $(this).val();
		});
		Tree.nodes('.users-tree').each(function(i) {
			var node = $(this);
			if ($.inArray(Tree.data(node, 'id'), userIds) !== -1) {
				if (!Tree.isActive(node)) {
					Tree.setActive(node);
				}
			}
		});
		
		$('#btn-save').click(function() {
			$.ajax({
				type : 'POST',//发送请求的方式
				url : 'role/user-save.do',
				data : $('#form').serialize(),
				dataType : "json",//文件类型
				cache : false,
				timeout : 60000,//超时设置，单位为毫秒
				success : function(success) {
					if (!success) {
						alert('修改角色-用户映射失败！');
					} else if (!success.success) {
						alert('修改角色-用户映射失败！\n\n' + success.msg);
					} else {
						alert('修改角色-用户映射成功！');
						Suredy.loadContent('role/list.do');
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
