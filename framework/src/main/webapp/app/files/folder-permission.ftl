<div class="container-fluid suredy-form" style="padding-top: 0">
	<#if folder??>
		<form name="permissionForm" id="permissionForm">
		<div class="row">
			<input type="hidden" id="id" name="id" value="${folder.id!''}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">分类名称</div>
					<input type="text" class="form-control" id="fullName" name="fullName" value="${folder.fullName}" />
				</div>
			</div>
		<#if (folder.isPublic == 1)>
			<div class="text-center">
				<div class="text-center"><h4>当前分类设置为公开,所有用户均可访问分类下的内容,无需设置权限.</h4></div>
			</div>	
		<#else>
			<div class="col-md-6 col-sm-6">
				<input type="hidden" id="unitPermissions" name="unitPermissions" value="${unitPermissions!''}" />
				<div><h5>部门权限分配</h5></div>
				<div class="unit-tree" style="border-top: 1px solid #ddd; height:400px; max-height:400px; overflow:auto;"  >加载中......</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<input type="hidden" id="rolePermissions" name="rolePermissions" value="${rolePermissions!''}" />
				<div><h5>岗位权限分配</h5></div>
				<div class="role-tree" style="border-top: 1px solid #ddd; height:400px; max-height:400px; overflow:auto;"  >加载中......</div>
			</div>
			
			<div class="row">
				<div class="text-center">
					<div class="btn btn-info" id="btn-save" ><i class="icon-save"></i> 保存</div>
				</div>
			</div>				
		</#if>	
		</div>
		</form>
	
	<#else>
	<div class="row">	
		<div class="text-center"><h4>错误,未能获取对应的文件分类信息,请联系技术支持...</h4></div>	
	</div>	
	</#if>	
</div>
<script type="text/javascript">
	require(['suredyTree','suredyModal', 'notify'], function(Tree, Modal) {		
		var loadTree = function() {
			Tree('.unit-tree', '${request.contextPath}/config/ou/tree/true/false/false', {
				autoCollapse : false,
				style : 'file',
				canFolderActive : true,
				folderCheckbox : true,
				canLeafActive : true,
				leafCheckbox : true,
				multiselect : true

			});
			Tree('.role-tree', '${request.contextPath}/config/role/tree', {
				autoCollapse : true,
				style : 'department',
				canLeafActive : true,
				leafCheckbox : true,
				multiselect : true
			});			
			
			$('.unit-tree .suredy-tree li>span+ul').collapse('show');
			$('.role-tree .suredy-tree li>span+ul').collapse('show');
		};
		var setChecked = function(){
			// 取消选中状态
			$.each(Tree.checked('.unit-tree'),function(i,v) {
				$(v).trigger('click');
			});
			$.each(Tree.checked('.role-tree'),function(i,v) {
				$(v).trigger('click');
			});
			var unitPermissions = $('#unitPermissions').val();
			if (unitPermissions) {
				var units = unitPermissions.split(',');
				Tree.nodes('.unit-tree').each(function(index){
					var node = $(this);
					var id = Tree.data(node, 'id');
					
					for (var i=0; i<units.length; i++) {
						if (units[i] == id) {
							Tree.setActive(node);
							//node.click();
							break; //跳出当前each循环
						} 
					}


				});
			}
			
			var rolePermissions = $('#rolePermissions').val();
			if (rolePermissions) {
				var roles = rolePermissions.split(',');
				Tree.nodes('.role-tree').each(function(index){
					var node = $(this);
					var id = Tree.data(node, 'id');
					for(var i=0; i<roles.length; i++){  
						if (roles[i] == id) {
							Tree.setActive(node);
							//node.click();
							break; //跳出当前each循环
						} 
					}  
				});
			}
			
		}
		
		loadTree();			
		setChecked();
		
		$('#btn-save').click(function() {
			var roles = new Array();
			var units = new Array();
			$.each(Tree.checked('.unit-tree'),function(index) {
				units.push(Tree.data($(this), 'id'));
			});
			$.each(Tree.checked('.role-tree'),function(index) {
				roles.push(Tree.data($(this), 'id'));
			});		
			$('#unitPermissions').val(units.join(','));
			$('#rolePermissions').val(roles.join(','));
			$.ajax({
				url : '${request.contextPath}/files/permission-save',
				dataType : 'json',
				type : 'POST',
				data : $('#permissionForm').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存分类权限失败！');
					} else if (!success.success) {
						alert('保存分类权限失败！\n\n' + success.msg);
					} else {
						Suredy.loadContent('${request.contextPath}/files/manager');
						Modal.closeModal();
						$.notify({title:'提示：',message:'分类权限已保存！'});
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + c);
				}
			});
		});
	});
</script>	
