
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">角色管理</h3>
		</div>
		<div class="panel-body">
			<table class="role-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th style="display:none"></th>
					<th>角色代码</th>
					<th>角色名</th>
					<th>单位代码</th>
					<th>单位名称</th>					
				</tr>
				<#if data??> <#list data as role>
				<tr>
					<td style="display:none">${role.id}</td>
					<td>${role.uniqueCode}</td>
					<td>${role.name}</td>
					<td>${role.orgUniqueCode}</td>
					<td>${role.orgName}</td>										
				</tr>
				</#list></#if>				
			</table>
		</div>
		<div class="panel-footer"></div>
	</div>

<script type="text/javascript">
	require(['suredyList','suredyModal'], function(List,Modal) {
		var listConfig = ({
			header : true,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				Suredy.loadContent('role/list?page=' + page + '&size=' + size);
			},
			
			btns : [ {
				text :'新建',
				icon : 'icon-plus',
				style : 'btn-info btn-sm',
				click : function() {
					Modal.showModal({
						size : 'lg',
						icon : 'icon-plus',
						title : '创建角色',
						showFoot : false,
						uri : 'role/form'
					});
				}
			}, {
				text :'修改',
				icon : 'icon-edit',
				style : 'btn-danger btn-sm',
				click : function() {
					var checked = List.checked($('.role-list'));
					if (checked.length == 0 ) {
						alert('请选择需要修改的角色!');
						return;
					} else if (checked.length > 1) {
						alert('只能修改一个角色!');
						return;
					} else {
						var roleId = $( $( checked[0]).find('td' )[1] ).html();
						var uri = 'role/form?roleId=' + roleId;
						Modal.showModal({
							size : 'lg',
							icon : 'icon-edit',
							title : '修改角色',
							showFoot : false,
							uri : uri
						});
					}
				}
			},  {
				text : '删除',
				icon : 'icon-remove',
				style : 'btn-danger btn-sm',
				click : function() {
					var checked = List.checked($('.role-list'));
					if (checked.length == 0 ) {
						alert('请选择需要删除的角色!');
						return;
					} else if (checked.length > 1) {
						alert('只能删除一个角色!');
						return;
					} else {
						var roleId = $( $( checked[0]).find('td' )[1] ).html();
						var uri = 'role/delete?roleId=' + roleId;
						var msg = '是否确认删除角色？\n\n\提示：\n\该操作将会删除该角色及对应的权限映射及用户关系！\n\请谨慎操作！';
	
						if (!window.confirm(msg)) {
							return;
						}
						$.ajax({
							url : uri,
							type : 'POST',
							success : function(msg) {
								if (!msg) {
									alert('Unknown exception!');
								} else if (!msg.success) {
									alert("删除角色失败！\n\n" + msg.msg);
								} else {
									alert("删除角色成功！");
									Suredy.loadContent('role/list');
								}
							},
							error : function(a, b, c) {
								alert('Server error! ' + b);
							}
						});

					} //end else
				} // end click function
			} ,  {
				text : '用户',
				icon : 'icon-user',
				style : 'btn-default btn-sm',
				click : function() {
					var checked = List.checked($('.role-list'));
					if (checked.length == 0 ) {
						alert('请选择需要管理的角色!');
						return;
					} else if (checked.length > 1) {
						alert('只能管理一个角色!');
						return;
					} else {
						var roleId = $( $( checked[0]).find('td' )[1] ).html();
						var uri = 'role/user?roleId=' + roleId;
						Modal.showModal({
							size : 'lg',
							icon : 'icon-user',
							title : '角色-用户映射',
							showFoot : false,
							uri : uri
						});
					}
				}
			} ,  {
				text : '权限',
				icon : 'icon-wrench',
				style : 'btn-default btn-sm',
				click : function() {
					var checked = List.checked($('.role-list'));
					if (checked.length == 0 ) {
						alert('请选择需要管理的角色!');
						return;
					} else if (checked.length > 1) {
						alert('只能管理一个角色!');
						return;
					} else {
						var roleId = $( $( checked[0]).find('td' )[1] ).html();
						var uri = 'role/permission?roleId=' + roleId;
						Modal.showModal({
							size : 'lg',
							icon : 'icon-wrench',
							title : '角色-权限管理',
							showFoot : false,
							uri : uri
						});
					}			
				}
			}]
		});	
		
		List('.role-list', listConfig);
	});
</script>	