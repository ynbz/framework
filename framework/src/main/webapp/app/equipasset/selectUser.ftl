<form id="searchform" style="height: 700px;" >
	<div class="row">
		<div class=" col-xs-6">
			<div class="form-group">
				<div class="input-group">
					<div class="input-lable input-group-addon">人员名称</div>
					<input type="text" class="form-control" id="username" name="username"  />
				</div>
			</div>
		</div>
		<div class="col-xs-6">
			<div class="btn btn-info pull-right"  id="searchbtn" >
				<i class="icon-search"></i> 查询
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class=" col-md-3 col-xs-3">
			<input type="hidden" value="" id="nodeId" name="type.id" />
			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="icon-user"></i> 部门分类
				</div>
				<div style="padding: 0; max-height: 550px; overflow: auto;">
					<div class="unit-user-tree"></div>
				</div>
			</div>
		</div>
		<div class="col-md-9 col-xs-9">
			<div class="panel panel-default">
					<div class="panel-heading">
						<i class="icon-user"></i> 人员列表
					</div>
				<div id="userList" style="padding: 0; max-height: 550px; overflow: auto;">
					<input type="hidden" id="name" value="${name}"/>
					<input type="hidden" id="unit" value="${unit}"/>
					<table class="user_list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>" >
						<tr class="title-row">
							<th style="display: none"></th>
							<th style="display: none"></th>
							<th style="display: none"></th>
							<th>姓名</th>
							<th>全称</th>
							<th>电话</th>
						</tr>
						<#if data??>
							<#list data as user>
							<tr>
								<td style="display: none">${user.id}</td>
								<td style="display: none">${user.unitId}</td>
								<td style="display: none">${user.fullUnitName}</td>
								<td>${user.fullUnitName}/${user.name}</td>
								<td>${user.fullName}</td>
								<td>${user.userphone}</td>
							</tr>
							</#list>
						</#if>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="text-center">
		<div class="btn btn-info" id="btn-confirm">
			<i class="icon-save"></i> 确定
		</div>
	</div>
		
</form>
<script type="text/javascript">
	require([ 'suredyTree', 'suredyList', 'suredyModal','validation' ], function(Tree, List, Modal) {
		Tree('.unit-user-tree', '${request.contextPath}/config/ou/tree/true/false/true', {
			style : 'department'
		});

		$('.unit-user-tree').on(Tree.nodeClick, function(event, $node) {
			var node = Tree.checked('.unit-user-tree');
			var nodeisActive =Tree.isActive($node);
			var falg = $('#falg').val();
			if(nodeisActive){
				var nodeData = Tree.data(node);
				var param={ unit:nodeData.id,falg:falg};
				$.post('${request.contextPath}/EquipAssetCtrl/getSelectUser.do',param,function(html, textStatus, jqXHR) {
					var $html = $(html);
						$("#userList").html($("#userList", $html).html());
						List('.user_list',listConfig);
				}, 'html');	
			}
			
		});
		
		$("#searchbtn").click(function(){
			var name=$('#username').val();
			var falg = $('#falg').val();
			var param={ name:name,falg:falg};
			$.post('${request.contextPath}/EquipAssetCtrl/getSelectUser.do',param,function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#userList").html($("#userList", $html).html());
					List('.user_list',listConfig);
			}, 'html');	
		});
		
		var listConfig = ({
			header : false,
			footer : true,
			search : false,
			checkbox : true,
			paginate : function(page,size,key) {
				var name = $('#name').val();
				var unit = $('#unit').val();
				var falg = $('#falg').val();
				var url='${request.contextPath}/EquipAssetCtrl/getSelectUser.do';
				$.post(url,{name:name,unit:unit,page:page,size:size,falg:falg},function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#userList").html($("#userList", $html).html());
					List('.user_list', listConfig);
				}, 'html');
			}
		});	
	 List('.user_list',listConfig);	
	 
	 
	 $("#btn-confirm").click(function(){
		 var isPublic = $('#isPublic').val();
		 var userUnitId = $('#userUnitId');
		 var userUnit = $('#userUnit');
		 var userID = $('#userID');
		 var userName = $('#userName');
		 var userPhone = $('#userPhone');
		 var responsibleId = $('#responsibleId');
		 var responsible = $('#responsible');
		 var responsiblePhone = $('#responsiblePhone');
		 var checked = List.checked($('.user_list'));
		 if (checked.length == 0 ) {
			alert('请选择人员!');
			return;
		 } else if (checked.length > 1) {
			alert('只能选择一个人员!');
			return;
		 } else {
			 var id = checked.find('td').eq(1).html();
			 var unitId = checked.find('td').eq(2).html();
			 var unitName = checked.find('td').eq(3).html();
			 var fullName = checked.find('td').eq(5).html();
			 var phone = checked.find('td').eq(6).html();
			 
			 var chk_value; 
			 $('input[name="checkAll"]:checked').each(function(){ 
			 	chk_value=$(this).val(); 
			 });
			 if(isPublic!='2'){
				 userUnitId.val(unitId);
				 userUnit.val(unitName);
				 userID.val(id);
				 userName.val(fullName);
				 userPhone.val(phone);
				 responsibleId.val(id);
				 responsible.val(fullName);
				 responsiblePhone.val(phone);
				 $('#form').data('bootstrapValidator').updateStatus('userName','NOT_VALIDATED',null).validateField('userName');
				 $('#form').data('bootstrapValidator').updateStatus('responsible','NOT_VALIDATED',null).validateField('responsible');
				 $('#form').data('bootstrapValidator').updateStatus('userPhone','NOT_VALIDATED',null).validateField('userPhone');
			 }else{
				 userUnitId.val(unitId);
				 userUnit.val(unitName);
				 responsibleId.val(id);
				 responsible.val(fullName);
				 responsiblePhone.val(phone);
				 $('#form').data('bootstrapValidator').updateStatus('responsible','NOT_VALIDATED',null).validateField('responsible');
			 }
			 Suredy.Modal.closeModal();
		 }
		 
	 });
	 
	 
	});
</script>
