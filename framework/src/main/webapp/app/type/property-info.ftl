<div class="container-fluid suredy-form" style="padding-top: 0">
	<form id="form" name='form'>
		<#if type??>
		<h1>${type.typeName} 属性配置</h1>
		<input type="hidden" id="typeId" name="type.id" value="${type.id}" />
		</#if>
		<div class="row">
			<#if data??> <#list data as cs> <input type="hidden" name="id"
				id="id" value="${cs.id}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">属性名称</div>
					<input type="text" class="form-control" id="name"
						name="propertyName" value="${cs.propertyName}" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort"
						value="${cs.sort}" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">表头显示</div>
					<select class="form-control" id="isShow" name="isShow">
						<option value="1" ${(cs.isShow==1)?string('selected','')}>是</option>
						<option value="0" ${(cs.isShow==0)?string('selected','')}>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">查询条件</div>
					<select class="form-control" id="isSearch" name="isSearch">
						<option value="1" ${(cs.isSearch==1)?string('selected','')}>是</option>
						<option value="0" ${(cs.isSearch==0)?string('selected','')}>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">主要配置</div>
					<select class="form-control" id="isPrimeAttribute" name="isPrimeAttribute">
						<option value="1" ${(cs.isSearch==1)?string('selected','')}>是</option>
						<option value="0" ${(cs.isSearch==0)?string('selected','')}>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">需求项</div>
					<select class="form-control" id="isNeed" name="isNeed">
						<option value="1" ${(cs.isNeed==1)?string('selected','')}>是</option>
						<option value="0" ${(cs.isNeed==0)?string('selected','')}>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon">属性单位</div>
				<input type="text" class="form-control" id="propertyUnit" name="propertyUnit" value="${cs.propertyUnit}" />
			</div>
		</div>	
			<div class="text-center">
				<div class="btn btn-info" id="btn-save">
					<i class="icon-save"></i> 保存
				</div>
			</div>
		</div>
	</form>
	</#list></#if>
</div>

<script type="text/javascript">
require(['suredyModal'], function(Modal) {
	$("#btn-save").on('click', function(){
		var name = $("#name").val();
		var sort = $("#sort").val().trim();
		var typeid = $("#typeId").val();
		var reg = /^[0-9]*$/;
		if (name == "" || name == undefined) {
			alert("属性名称不能为空！");
			return;
		} else if (sort == "" || sort == undefined) {
			alert("显示顺序不能为空！");
			return;
		} else if (!reg.test(sort)) {
			alert("显示顺序只能是正整数字！");
			return;
		} else {
			$.ajax({
						datatype : 'json',
						type : 'post',
						data : $('#form').serialize(),
						url : '${request.contextPath}/classifyManageCtrl/saveData.do',
						success : function(data) {
							var m = data.data;
							if (m) {
								$.notify({title:'提示：',message:'数据保存成功！'});
								Suredy.loadContent('${request.contextPath}/classifyManageCtrl/getClassifyData.do?nodeid='+ typeid);
								Modal.closeModal();
								
							}
						},
						error : function() {
							alert("服务器连接失败!");
						}
					});
		}
	})
})
</script>