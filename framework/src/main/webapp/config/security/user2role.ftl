<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
	<form name="form" id="form">
	<#if user??>
		<input type="hidden"  name="id" value="${user.id}" />
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon">用户代码</div>
				<input type="text" class="form-control" name="name" readonly="readonly" value="${user.uniqueCode}" />
			</div>
		</div>	
		<div class="form-group">	
			<div class="input-group">
				<div class="input-group-addon">用户名称</div>
				<input type="text" class="form-control" name="name" readonly="readonly" value="${user.fullName}" />
			</div>
		</div>
	</#if>	
		<div class="form-group">
			<table  class="table table-striped table-bordered table-hover">						
				<tr>
					<th>角色代码</th>
					<th>角色名</th>
					<th>所属单位</th>
					<th style="text-align:center; width:80px;">映射</th>
				</tr>
				<#if roles??> <#list roles as role>
				<tr>
					<td>${role.uniqueCode}</td>
					<td>${role.name}</td>
					<td>${role.orgName}</td>
					<#assign isChecked="" />
					<#if relations??><#list relations as relation>
			        	<#if relation.roleId == role.id >
			        	    <#assign isChecked="checked=\"checked\"" />
			        	    <#break>
			        	</#if>
			        </#list></#if>
					<td class="text-center"><input type="checkbox" name="roleId" ${isChecked} value="${role.id}"  /></td>
				</tr>
				</#list></#if>
			</table>
		</div>
		<div class="text-center">
			<button class="btn btn-info" id="btn-save" type="button">
				<i class="icon-save"></i>
				保存
			</button>
		</div>	
	</form>	
	</div>
</div>
<script type="text/javascript">
	require([], function() {
		$('#btn-save').click(function() {
			$.ajax({
				type : 'POST',//发送请求的方式
				url : 'user/role-save.do',
				data : $('#form').serialize(),
				dataType : "json",//文件类型
				cache : false,
				timeout : 60000,//超时设置，单位为毫秒
				success : function(success) {
					if (!success) {
						alert('修改用户-角色映射失败！');
					} else if (!success.success) {
						alert('修改用户-角色映射失败！\n\n' + success.msg);
					} else {
						alert('修改用户-角色映射成功！');
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
