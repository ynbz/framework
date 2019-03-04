<div class="row">
	<div class="col-md-4 col-sm-4">
		<input type="hidden" value="" id="typeId" /> 
		<div class="row" style="height:53px; padding: 10px 0px 10px 15px">
			<div class="btn btn-info btn-sm" id="add">
				<i class="icon-plus"></i> 新增分类
			</div>										
			<div class="btn btn-info btn-sm hidden" id="edit">
				<i class="icon-edit "></i> 修改分类
			</div>
			<div class="btn btn-danger btn-sm hidden" id="remove">
				<i class="icon-remove"></i> 删除分类
			</div>				
		</div>
		<div class="panel-body menu-tree" id="typetreeid" style="padding: 0; max-height: 850px;">加载中......</div>
	</div>
	<div class="col-md-8 col-sm-8">
		<div class="panel-body" id="frmList">
			<table class="frm-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th style="display:none"></th>
					<th>类型名称</th>
					<th>文件分类</th>
					<th>类型代码</th>
					<th>创建时间</th>
				</tr>
				<#if data??> <#list data as form>
				<tr data-id="${form.id}">
					<td style="display:none">${form.id}</td>
					<td>${form.name}</td>
					<td>${form.typeName}</td>	
					<td>${form.fileType}</td>		
					<td>${form.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>				
				</tr>
				</#list></#if>				
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
require(['suredyList','suredyTree','suredyModal','notify'], function(List, Tree, Modal) {
	var listConfig = ({
		header : true,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size) {
			var url='${request.contextPath}/config/formm/list.do?page=' + page + '&size=' + size+'typeId='+ $('#typeId').val();
			$.get(url, function(html, textStatus, jqXHR) {
				var $html = $(html);
				$("#frmList").html($("#frmList", $html).html());
				List('.frm-list', listConfig);
			}, 'html');
		},
		
		btns : [ {
			text :'创建',
			icon : 'icon-plus',
			style : 'btn-info',
			click : function() {
				Modal.showModal({
					size : 'lg',
					icon : 'icon-plus',
					title : '创建表单',
					showFoot : false,
					uri : '${request.contextPath}/config/form/form-info?typeId='+$('#typeId').val()
				});
			}
		},{
			text :'修改',
			icon : 'icon-edit',
			style : 'btn-danger',
			click : function() {
				var checked = List.checked($('.frm-list'));
				if (checked.length == 0 ) {
					alert('请选择需要修改的表单!');
					return;
				} else if (checked.length > 1) {
					alert('只能修改一个表单!');
					return;
				} else {
					var formId = checked.eq(0).data('id');//.find('td').eq(1).html();// $( $( checked[0]).find('td' )[1] ).html();
					var uri = '${request.contextPath}/config/form/form-info?formId=' + formId;
					Modal.showModal({
						size : 'lg',
						icon : 'icon-edit',
						title : '修改表单',
						showFoot : false,
						uri : uri
					});
				}
			}
		},  {
			text : '删除',
			icon : 'icon-remove',
			style : 'btn-danger',
			click : function() {
				var checked = List.checked($('.frm-list'));
				if (checked.length == 0 ) {
					alert('请选择需要删除的表单!');
					return;
				} else if (checked.length > 1) {
					alert('只能删除一个表单!');
					return;
				} else {
					//TODO: 
					var formId =checked.eq(0).data('id') ;// $( $( $.suredy.list.checked($('.frm-list'))[0]).find('td' )[1] ).html();
					var uri = '${request.contextPath}/config/form/form-delete?formId=' + formId;
					var msg = '是否确认删除申报？提示：请谨慎操作！';
			
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
								alert("删除表单失败！\n\n" + msg.msg);
							} else {
								$.notify({title:'提示：',message:'表单删除成功！'});
								Suredy.loadContent('${request.contextPath}/config/form/list.do');
							}
						},
						error : function(a, b, c) {
							alert('Server error! ' + b);
						}
					});

				} //end else
			} // end click function
		}]
	});	
	List('.frm-list', listConfig);
	
	var ouTree = function() {
		Tree('.menu-tree', '${request.contextPath}/config/form/type-tree',{
			style : 'file',
			autoCollapse : false,
			leafCheckbox : false,
			folderCheckbox : false,
			inContainer : false
		});	
		
		$('.menu-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
			var nodeData = Tree.data($node);	
			if (nodeData.id){
				 $('#edit').removeClass('hidden');
				 $('#remove').removeClass('hidden');
				 $('#typeId').val(nodeData.id);
			} else {
				 $('#edit').addClass('hidden');
				 $('#remove').addClass('hidden');
				 $('#typeId').val('');
			} 
			 var url='${request.contextPath}/config/form/list.do?typeId=' + nodeData.id;
			 $.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#frmList").html($("#frmList", $html).html());
					List('.frm-list', listConfig);
			 }, 'html'); 			
		});
	};
	ouTree();	
		
	$('#add').on('click', function() {
		
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '新增表单类型',
			showFoot : false,
			uri : '${request.contextPath}/config/form/type-info.do?parentId='+$('#typeId').val()
		});
	
	});
		
	$('#edit').on('click', function() {	
		var typeId = $('#typeId').val();
		if (typeId == '') {
			alert('请选择需要修改的节点！');
			return;
		}
		Modal.showModal({
			size : 'lg',
			icon : 'icon-edit',
			title :'修改表单类型',
			showFoot : false,
			uri : '${request.contextPath}/config/form/type-info.do?typeId='+typeId
		});
	
	});

	
	$('#remove').on('click', function() {
		var typeId = $('#typeId').val();
		if (typeId == '') {
			alert('请选择需要删除的表单类型！');
			return;
		}
		var msg = '数据删除\n\n\提示：\n\该类型下面的表单数据也会删除\n\是否确认删除【选中的节点】？';

		if (!window.confirm(msg)) {
			return;
		}
		$.ajax({
			url : '${request.contextPath}/config/form/type-delete.do?typeId='+typeId,
			type : 'get',
			success : function(msg) {
				if (!msg) {
					alert('Unknown exception!');
				} else if (!msg.success) {
					alert("删除失败！\n\n" + msg.msg);
				} else {
					//alert("删除成功！");
					$.notify({title:'提示：',message:'数据删除成功！'});
					ouTree();
				}
			},
			error : function(a, b, c) {
				alert('Server error! ' + b);
			}
		});
	});	
});
</script>