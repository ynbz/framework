<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">
		<form name="form" id="form">
			<input type="hidden" id="parentId" name="parentId" value="${parentId}" />
			<input type="hidden" id="parentType" name="parentType" value="${parentType}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">父节点名</div>
					<input type="text" class="form-control" readonly="readonly" id="parentName" value="${parentName}" />
				</div>
			</div>
			<#if unit??>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">部门代码</div>
					<input type="hidden" id="id" name="id" value="${unit.id}" />
					<input type="text" class="form-control" id="code" name="code" value="${unit.code}" placeholder="部门代码" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">部门名称</div>
					<input type="text" class="form-control" id="name" name="name" value="${unit.name}" placeholder="部门名称" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">部门简称</div>
					<input type="text" class="form-control" id="alias" name="alias" value="${unit.alias}" placeholder="部门简称" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${unit.sort}" placeholder="显示顺序,默认0" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">部门描述</div>
					<textarea class="form-control" rows="3" id="description" name="description">${unit.description}</textarea>
				</div>
			</div>
			</#if>
		</form>
		<div class="text-center">
			<div class="btn btn-info" id="btn-save">
				<i class="icon-save"></i>
				保存
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	require([ 'suredyTree', 'suredyModal', 'suredyTreeSelector' ], function(Tree, Modal, TreeSelector) {
		// 新建时初始化
		if (!$('[name="id"]').val()) {
			$('[name="code"]').val(Suredy.randomString(10));
		}
		TreeSelector('#parentName', '${request.contextPath}/config/ou/tree/true/false/false', {
			style : 'department'
		});

		$('#parentName').on(TreeSelector.nodeClick, function(event, $node) {
			var nodeData = TreeSelector.data($node);
			var nodeText = TreeSelector.data($node, "text");
			var isActive = TreeSelector.isActive($node);

			if (!isActive) {
				$('#parentId').val('');
				$('#parentType').val('');
				$('#parentName').val('');

				return false;
			}

			$('#parentId').val(nodeData.id);
			$('#parentType').val(nodeData.flag);
			$('#parentName').val(nodeText);
		});
		
		// 初始化树的选中状态
		var parentId;
		if ($('#id').val()!='') {
			parentId = $('#parentId').val();
		} else {
			parentId = $('#nodeId').val();
		}

		TreeSelector.nodes('#parentName').each(function(i) {
 			if (TreeSelector.data(this, 'id') !== parentId) {
 				return true;
 			}
 			TreeSelector.setActive(this, true);
			$('#parentName').val(TreeSelector.data(this, 'text'));
			var nodeData = TreeSelector.data(this);
			$('#parentId').val(nodeData.id);
			$('#parentType').val(nodeData.flag);
			return false;
		});

		$('#btn-save').click(function() {
			var code = $('#code').val();
			var name = $('#name').val();
			var parentId = $('#parentId').val();
			if (code == '') {
				alert('请填写代码！');
				return;
			}

			if (name == '') {
				alert('请填写名称！');
				return;
			}

			if (parentId == '') {
				alert('请选择非根节点的父亲节点！');
				return;
			}

			$.ajax({
				url : 'ou/unit/save.do',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存部门失败！');
					} else if (!success.success) {
						alert('保存部门失败！\n\n' + success.msg);
					} else {
						alert('保存部门成功！');
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
