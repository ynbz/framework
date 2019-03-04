<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">
		<form name="form" id="form">
			<#if user??>
			<input type="hidden" id="unitId" name="unitId" value="${user.unitId}" />
			<input type="hidden" id="id" name="id" value="${user.id}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">所属部门</div>
					<input type="text" class="form-control" readonly="readonly" id="parentName" value="${user.unitName}" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">真实姓名</div>
					<input type="text" class="form-control" id="name" name="name" value="${user.name}" placeholder="真实姓名" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">用户简称</div>
					<input type="text" class="form-control" id="alias" name="alias" value="${user.alias}" placeholder="用户简称" />
				</div>
			</div>
			
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">姓名字母</div>
					<input type="text" class="form-control" id="shortPinyin" name="shortPinyin" value="${user.shortPinyin}" placeholder="每一个字的首字母，如：管理员，填写为：gly" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">职衔职务</div>
					<input type="text" class="form-control" id="title" name="title" value="${user.title}" placeholder="职衔职务" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">电话号码</div>
					<input type="text" class="form-control" id="userphone" name="userphone" value="${user.userphone}" placeholder="电话号码" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">邮件地址</div>
					<input type="text" class="form-control" id="email" name="email" value="${user.email}" placeholder="邮件地址" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">正式员工</div>
					<select class="form-control" id="isEmployee" name="isEmployee">
						<option value="1" ${(user.isEmployee==1)?string('selected','')}>是</option>
						<option value="0" ${(user.isEmployee==0)?string('selected','')}>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">登录用户</div>
					<select class="form-control" id="isLongUser" name="isLongUser">
						<option value="0" ${(user.isLongUser==0)?string('selected','')}>否</option>
						<option value="1" ${(user.isLongUser==1)?string('selected','')}>是</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${user.sort}" placeholder="显示顺序 默认0" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">用户描述</div>
					<textarea class="form-control" rows="3" id="description" name="description">${user.description}</textarea>
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
		TreeSelector('#parentName', '${request.contextPath}/config/ou/tree/true/false/false', {
			style : 'department'
		});
		
		// 初始化选中状态
		TreeSelector.nodes('#parentName').each(function(i, dom) {
			var $node = $(dom);

			if (TreeSelector.data($node, 'id') == $('#unitId').val()) {
				TreeSelector.setActive($node);
				return false;
			}
		});

		$('#parentName').on(TreeSelector.nodeClick, function(event, $node) {
			var $checked = TreeSelector.checked(this);
			var $unitId = $('#unitId');
			var $parentName = $('#parentName');

			if (!$checked.length) {
				$('#unitId').val('');
				$('#parentName').val('');
				return;
			}

			$('#unitId').val(TreeSelector.data($checked, 'id'));
			$('#parentName').val(TreeSelector.data($checked, 'name'));

			TreeSelector.hidden(this);
		});

		var $checked = Tree.checked('.unit-user-tree');

		// 新建时初始化
		if (!$('#id').val()) {
			var unitId = Tree.data($checked, 'id');
			$('#parentName').val(Tree.data($checked, 'name'));
			$('#unitId').val(unitId);
			TreeSelector.nodes('#parentName').each(function(i) {
	 			if (TreeSelector.data(this, 'id') !== unitId) {
	 				return true;
	 			}
	 			TreeSelector.setActive(this, true);
				return false;
			});
		}


		$('#btn-save').click(function() {
			var unitId = $('#unitId').val();
			var name = $('#name').val();

			if (unitId == '') {
				alert('请选择部门！');
				return;
			}

			if (name == '') {
				alert('请填写用户姓名！');
				return;
			}

			$.ajax({
				url : 'user/save.do',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存用户失败！');
					} else if (!success.success) {
						alert('保存用户失败！\n\n' + success.msg);
					} else {
						alert('保存用户成功！');
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
