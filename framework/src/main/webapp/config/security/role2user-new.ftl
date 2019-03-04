<div class="container-fluid suredy-form" style="padding-top: 0">
	<form name="form" id="form">
	<#if role??>
		<div class="row">
			<input type="hidden"  name="id" value="${role.id}" />
			<div class="col-md-6 col-sm-6">
				<div class="form-group">	
					<div class="input-group">
						<div class="input-group-addon">岗位名称</div>
						<input type="text" class="form-control" name="name" readonly="readonly" value="${role.name}" />
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">	
					<div class="input-group">
						<div class="input-group-addon">所属单位</div>
						<input type="text" class="form-control" name="orgName" readonly="readonly" value="${role.orgName}" />
					</div>
				</div>
			</div>			
		</div>	
	</#if>

		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="title">可选人员</div>
				<div style="overflow: auto; height: 550px; border-top:1px solid #ddd;">
					<div class="suredy-tree" id="users4selector"></div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="title">已关联人员(双击移除关联)</div>
				<div style="overflow: auto; height: 550px;">
					<table class="table table-bordered table-hover table-striped table-condensed">
						<tr class="title-row">
							<th style="display:none;"></th>
							<th>部门</th>
							<th>姓名</th>
						</tr>
						<tbody  id="selectedNode" style="border:none;"></tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="row text-center">
			<button class="btn btn-sm btn-info" id="btn-save" type="button">
				<i class="icon-save"></i> 保存
			</button>
		</div>		

	</form>	
</div>
<script type="text/javascript">
	var userData = new Array();
	<#if users??><#list users as user>
		userData.push({id:'${user.id}', unitName:'${user.unitName}', name:'${user.name}', fullName:'${user.fullName}'});
	</#list></#if>	
</script>
<script type="text/javascript">
	require(['suredyList', 'suredyTree', 'suredyModal', 'notify'], function( List, Tree, Modal) {
		Tree('#users4selector', '${request.contextPath}/config/ou/tree/true/true/true', {
			autoCollapse : false,
			inContainer : true,
			size : 'lg',
			asynLoadable : false,
			canLeafActive : true,
			leafCheckbox : true,
			canFolderActive : false,
			folderCheckbox : false,
			multiselect : true
		});
		
		
		$('#users4selector').on(Tree.nodeClick, function(event, node) {
			if ($(node).is('.leaf')){
				var unitName = Tree.data(node, 'unitName');
				var name = Tree.data(node, 'name');
				var clickId = Tree.data(node, 'id');
				if (Tree.isActive(node)) {
					var exist = false;
					$('#selectedNode').children('tr').each(function(index){
						var userId = $(this).children('td').eq(0).find('input').eq(0).val();
						if (clickId == userId) {
							exist = true;
							return false;
						}
					});
					if (!exist) {
						$('#selectedNode').append('<tr><td style="display:none"><input type="hidden" name="userId" value="'+ clickId +'" /></td><td>'+ unitName +'</td><td>'+ name +'</td></tr>');
					}
				} else {
					$('#selectedNode').children('tr').each(function(index){
						var userId = $(this).children('td').eq(0).find('input').eq(0).val();
						if (clickId == userId) {
							$(this).remove();
							return false;
						}
					});
				}
				
			}
		});
		
		$.each(userData, function(i, item) {	
			Tree.nodes('#users4selector').each(function(index){
				var node = $(this);
				var data = Tree.data(node);
				if (item.id == data.id) {
					$(node).find('>.node-info>.node-info-item.text').trigger('click');
					return false; //跳出当前each循环
				} 
			});
		});	
		
		$('#selectedNode').delegate('tr','dblclick',function(){
			var clickId = $(this).find('input').eq(0).val();
						
			$.each(Tree.checked('#users4selector'),function(index, node) {
				var userId = Tree.data(node, 'id');
				if (clickId == userId) {
					$(node).find('>.node-info>.node-info-item.text').trigger('click');
				}
			});
			
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
						alert('修改岗位-用户映射失败！');
					} else if (!success.success) {
						alert('修改岗位-用户映射失败！\n\n' + success.msg);
					} else {
						$.notify({title:'提示：',message:'修改岗位-用户映射成功!'});
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
