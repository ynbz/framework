<div class="row">
	<div class="col-md-4 col-sm-4">
		<input type="hidden" value="" id="nodeType" />
		<input type="hidden" value="" id="nodeId" />
		<input type="hidden" id="user-url" value="usermuch/list"/>
		<div class="row" style="padding: 6px 15px">
			<div class="btn-group">
			  <button type="button" class="btn btn-sm btn-primary newUnit"><i class="icon-plus-sign"></i> 创建部门</button>
			  <button type="button" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    <span class="caret"></span>
			    <span class="sr-only">More...</span>
			  </button>
			  <ul class="dropdown-menu">
			    <li><a href="#" class="newUnit"><i class="icon-plus-sign"></i> 创建部门</a></li>
			    <li role="separator" class="divider"></li>
			    <li><a href="#" id="newOrg"><i class="icon-plus"></i> 创建单位 </a></li>
			  </ul>
			</div>	
			<div class="btn-group">
			  <button type="button" class="btn btn-sm btn-danger editOU"><i class="icon-edit"></i> 修改</button>
			  <button type="button" class="btn btn-sm btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    <span class="caret"></span>
			    <span class="sr-only">More...</span>
			  </button>
			  <ul class="dropdown-menu">
			    <li><a href="#" class="editOU"><i class="icon-edit"></i> 修改</a></li>
			    <li role="separator" class="divider"></li>
			    <li><a href="#" id="removeOU"><i class="icon-remove"></i> 删除 </a></li>
			  </ul>
			</div>	
			<div class="btn btn-default btn-sm" id="init-pinyin-btn">拼音简写</div>																			  			
		</div>
		<div class="unit-user-tree" style="max-height:800px; overflow: auto;"  >加载中......</div>
	</div>

	<div class="col-md-8 col-sm-8">
		<div class="row" style="padding: 6px 15px">
			<div class="col-md-6 col-sm-6" style="padding: 0px;">
				<div class="btn btn-primary btn-sm" id="newUser"><i class="icon-plus"></i> 新建</div>
				<div class="btn btn-primary btn-sm" id="editUser"><i class="icon-edit"></i> 修改</div>	
				<div class="btn btn-danger btn-sm" id="deleteUser"><i class="icon-remove"></i> 删除</div>	
				<div class="btn btn-warning btn-sm" id="user2Role"><i class="icon-magnet"></i> 角色</div>
				<div class="btn btn-warning btn-sm" id="userPermission"><i class="icon-user"></i> 权限</div>			
			</div>	
			<div class="col-md-6 col-sm-6" style="padding: 0px;">
				<div class="input-group input-group-sm">
					<input type="text" class="form-control" name="keyword" id="keyword" placeholder="&lt; 输入姓名拼音首字母查询, 如'张三'可输入'zs' &gt;" />
					<div class="input-group-btn"><button class="btn btn-primary" id="btn-search"><i class="icon-search"></i> 检索</button></div>
				</div>		
			</div>																									  			
		</div>	
		<div id="userList">
			<table class="user-list" data-page="${pageIndex ! '1'}" data-page-size="${pageSize ! '20'}" data-count="${count ! '0'}">
				<tr class="title-row">
					<th style="display: none"></th>
					<th>所属部门</th>
					<th>姓名</th>
					<th>全名</th>
					<th>识别码</th>
				</tr>
				<#if data??> <#list data as user>
				<tr>
					<td style="display: none">${user.id}</td>
					<td>${user.unitName}</td>
					<td>${user.name}</td>
					<td>${user.fullName}</td>
					<td>${user.uniqueCode}</td>
				</tr>
				</#list> </#if>
			</table>
		</div>	
	</div>	
</div>
<script type="text/javascript">
	require(['suredyTree','suredyList','suredyModal', 'notify'], function(Tree, List, Modal) {
		var listConfig = ({
			header : false,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				var url='usermuch/list.do?page=' + page + '&size=' + size+'&deptId='+$("#nodeId").val() + '&keyword=' + $("#keyword").val();
				$.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
						$("#userList").html($("#userList", $html).html());
						List('.user-list', listConfig);
				}, 'html');
			}
		});	
		List('.user-list', listConfig);
		
		Tree('.unit-user-tree', 'ou/tree', {
			autoCollapse : false,
			leafCheckbox : false,
			folderCheckbox : false,
			inContainer : false,
			style : 'department'
		});
			
		$('.unit-user-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
			var nodeData = Tree.data($node);
			var nodeType = nodeData.text;
			if ('org' === nodeData.flag) {
				$('#nodeType').val('org');
			}  else {
				$('#nodeType').val('unit');
			}					
			if (nodeData.id){
				$('#nodeId').val(nodeData.id);
			} else {
				$('#nodeId').val('');
			} 
			var url='usermuch/list.do?deptId='+nodeData.id;
			$.get(url, function(html, textStatus, jqXHR) {
				var $html = $(html);
				$("#userList").html($("#userList", $html).html());
				List('.user-list', listConfig);
			}, 'html');	
		});
	
		$('#btn-search').on('click', function() {			
			var url='usermuch/list.do?&deptId='+ $("#nodeId").val() + '&keyword=' + $("#keyword").val();
			$.get(url, function(data, textStatus, jqXHR) {
				var $html = $(data);
				$("#userList").html($("#userList", $html).html());
				List('.user-list', listConfig);
			}, 'html');
		});	
		


		$('#newUser').on('click', function() {	
			var $node = Tree.checked('.unit-user-tree');
			if (!$node.length) {
				alert('请选择部门后执行此操作！');
				return;
			}
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '新建人员',
				showFoot : false,
				uri : 'user/form'
			});
		
		});
 
		$('#editUser').on('click', function() {	
			var checked = List.checked($('.user-list'));
			if (checked.length == 0 ) {
				alert('请选择需要修改的人员!');
				return;
			} else if (checked.length > 1) {
				alert('只能修改一个人员!');
				return;
			} else {
				var userId = $( $( checked[0]).find('td' )[1] ).html();
				var uri = 'user/form?userId=' + userId;
				Modal.showModal({
					size : 'lg',
					icon : 'icon-edit',
					title : '修改人员',
					showFoot : false,
					uri : uri
				});
			}
			
		});

		$('#deleteUser').on('click', function() {	
			var checked = List.checked($('.user-list'));
			if (checked.length == 0 ) {
				alert('请选择需要删除的人员!');
				return;
			} else if (checked.length > 1) {
				alert('只能删除单个账户!');
				return;
			} else {
				if(confirm('是否确认删除该用户，删除后将不能进行恢复！')){
					var userId = $( $( checked[0]).find('td' )[1] ).html();
					$.ajax({
							url : 'user/delete?userId=' + userId,
							dataType : 'json',
							type : 'POST',
							success : function(success) {
								if (!success) {
									alert('用户删除失败！');
								} else if (!success.success) {
									alert('用户删除失败！\n\n' + success.msg);
								} else {
									alert('用户删除成功！');
									Suredy.loadContent('usermuch/list');
									Modal.closeModal();
								}
							},
							error : function(a, b, c) {
								alert('服务器错误! ' + b);
							}
						});
				}
			} 
			
		});
 		
		$('#user2Role').on('click', function() {	
			var checked = List.checked($('.user-list'));
			if (checked.length == 0 ) {
				alert('请选择需要管理的人员!');
				return;
			} else if (checked.length > 1) {
				alert('只能管理单个人员!');
				return;
			} else {
				var userId = $( $( checked[0]).find('td' )[1] ).html();
				var uri = 'user/role?userId=' + userId;
				Modal.showModal({
					size : 'lg',
					icon : 'icon-magnet',
					title : '人员-角色映射',
					showFoot : false,
					uri : uri
				});
			}			
		});
		
		$('#userPermission').on('click', function() {	
			var checked = List.checked($('.user-list'));
			if (checked.length == 0 ) {
				alert('请选择需要管理的人员!');
				return;
			} else if (checked.length > 1) {
				alert('只能管理单个人员!');
				return;
			} else {
				var userId = $( $( checked[0]).find('td' )[1] ).html();
				var uri = 'user/permission?userId=' + userId;
				Modal.showModal({
					size : 'lg',
					icon : 'icon-search',
					title : '人员权限定义',
					showFoot : false,
					uri : uri
				});
			}			
		});
		
		$('.newUnit').on('click', function() {	
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建部门',
				showFoot : false,
				uri : 'ou/unit/form'
			});
		
		});
		$('#newOrg').on('click', function() {	
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建单位',
				showFoot : false,
				uri : 'ou/org/form'
			});
		
		});		

		// edit menu btn click
		$('.editOU').on('click', function() {	
			var nodeId = $('#nodeId').val();
			var nodeType = $('#nodeType').val();
			if (nodeId == '') {
				alert('请选择需要修改的节点！');
				return;
			}
			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title : nodeType == 'unit' ? '修改部门' : '修改单位',
				showFoot : false,
				uri : nodeType == 'unit' ? 'ou/unit/form.do?unitId=' + nodeId : 'ou/org/form.do?orgId=' + nodeId
			});
		
		});
		// remove btn click
		$('#removeOU').on('click', function() {
			var nodeId = $('#nodeId').val();
			var nodeType = $('#nodeType').val();
	
			if (nodeId == '') {
				alert('请选择需要删除的节点！');
				return;
			}
	
			var msg = '是否确认删除【选中的节点】？\n\n\提示：\n\该操作将会删除该节点下的所有子节点！\n\请谨慎操作！';
	
			if (!window.confirm(msg)) {
				return;
			}
	
			$.ajax({
				url : nodeType == 'org' ? 'ou/org/delete.do' : 'ou/unit/delete.do',
				type : 'POST',
				data : {
					nodeId : nodeId
				},
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert("删除单位/部门失败！\n\n" + msg.msg);
					} else {
						$.notify({title:'提示：',message:'删除单位/部门成功！'});
						ouTree();
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		});	
		
		$('#init-pinyin-btn').on('click', function() {	
			Modal.showProcessDialog({});		
			$.ajax({
				url : '${request.contextPath}/config/user/pinyin',
				dataType : 'json',
				type : 'GET',
				success : function(success) {
					if (!success) {
						alert('初始化用户简拼信息失败！');
					} else if (!success.success) {
						alert('初始化用户简拼信息失败！\n\n' + success.msg);
					} else {
						alert('初始化用户简拼信息成功！');
					}
					Modal.closeProcessDialog();
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
					Modal.closeProcessDialog();
				}
			});	
		});
	});
</script>	