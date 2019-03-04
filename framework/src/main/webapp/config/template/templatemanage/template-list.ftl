<div class="row">
	<div class="col-md-4 col-sm-4">
		<input type="hidden" value="" id="temTypeId" /> 
		<div class="row" style="height: 51px; padding: 8px 0px 8px 15px">
			<div class="btn btn-info btn-sm" id="create">
					<i class="icon-plus"></i> 新建模板类型
				</div>					
				<div class="btn btn-info btn-sm hidden" id="edit">
					<i class="icon-edit "></i> 修改模板类型
				</div>
				<div class="btn btn-danger btn-sm  hidden" id="remove">
					<i class="icon-remove"></i> 删除模板类型
				</div>			
		</div>
		<div class="tem-tree" style=" border-top: 1px solid #ddd; max-height:600px; overflow:auto;"  >加载中......</div>
	</div>
	<div class="col-md-8 col-sm-8">
		<div class="row" style="padding-top: 10px;">
			<div class="form-inline">
				<div class="btn btn-info" id="adddatabtn">
					<i class="icon-plus"></i> 上传模板
				</div>
				<div class="btn btn-warning " id="editbtn">
					<i class="icon-edit "></i> 修改
				</div>
				<div class="btn btn-danger" id="deletebtn">
					<i class="icon-remove"></i> 删除模板
				</div>	
			</div>
		</div>
		<div class="row" style="overflow-x: auto;padding-top: 5px;">
			<div id="temList">
				
				<table class="tem-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>"style="width: 100%;">
					<tr class="title-row">
						<th style="display: none"></th>
						<th>模板标题</th>
						<th>模板类型</th>
						<th>上传人</th>
						<th>上传时间</th>
					</tr>
					<#if data??> <#list data as tem>
					<tr data-id="${tem.id}">
						<td style="display: none">${file.id}</td>
						<td><a href='javascript:void(0)'onclick="getFileView('${tem.id}')">${tem.templateTitel}</a></td>
						<td>${tem.type.typeName}</td>
						<td>${tem.uploadName}</td>
						<td>${(tem.uploadTime?string('yyyy-MM-dd'))!''}</td>
					</tr>
					</#list> </#if>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var listConfig;
require(['suredyList','suredyTree','suredyModal','jqueryForm'], function(List, Tree,Modal) {
	var loadTree = function() {
		Tree('.tem-tree', '${request.contextPath}/config/templatetype/tree', {
			autoCollapse : false,
			inContainer : false,
			style : 'file',
			size : 'sm'
		});
		
		// 取消active状态
		$.each(Tree.checked('.tem-tree'),function(i,v) {
			$(v).trigger('click');
		});
		
		$('.tem-tree .suredy-tree li>span+ul').collapse('show');
		
		$('.tem-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
			if (Tree.isActive($node)){
				var nodeData = Tree.data($node);
				
				$('#temTypeId').val(nodeData);
				$('#edit').removeClass('hidden');
				$('#remove').removeClass('hidden');
				
				 var url='${request.contextPath}/config/templatemanage/templateList.do?typeId=' + nodeData;
				 $.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#temList").html($("#temList", $html).html());
					List('.tem-list',listConfig);
				}, 'html');
				
			} else {
				$('#temTypeId').val('');
				$('#edit').addClass('hidden');
				$('#remove').addClass('hidden');
				 var url='filemanage/temList.do';
				 $.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#temList").html($("#temList", $html).html());
					List('.tem-list',listConfig);
				}, 'html');
			}
		});
	};
	loadTree();	
	
	$('#create').on('click', function() {
		var checked = Tree.checked('.tem-tree');
		var parentId = '';
		var temTypeId = $('#temTypeId').val();
		if (checked.length === 1) {
			parentId = Tree.data(Tree.checked('.tem-tree')[0]);
		}
		
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '创建模板类型',
			showFoot : false,
			uri : '${request.contextPath}/config/templatetype/info?parentId=' + parentId
		});
	});

	// edit file btn click
	$('#edit').on('click', function() {	
		var temTypeId = $('#temTypeId').val();
		if (temTypeId == '') {
			alert('[警告] 未选择模板类型节点，或者模板类型节点不可编辑！');
			return;
		}
		Modal.showModal({
			size : 'lg',
			icon : 'icon-edit',
			title : '修改模板类型',
			showFoot : false,
			uri : '${request.contextPath}/config/templatetype/info?id=' + temTypeId
		});
	
	});

	// remove btn click
	$('#remove').on('click', function() {
		// get selected node
		var temTypeId = $('#temTypeId').val();

		if (temTypeId == '') {
			alert('请选择需要删除的模板类型节点！');
			return;
		}


		var msg = '是否确认删除【选中的节点】？\n\n\提示：\n\该操作将会删除模板类型下的所有子节点！\n\请谨慎操作！';

		if (!window.confirm(msg))
			return;

		$.ajax({
			url : '${request.contextPath}/config/templatetype/delete',
			type : 'POST',
			data : {
				templateId : temTypeId
			},
			success : function(msg) {
				if (!msg) {
					alert('Unknown exception!');
				} else if (!msg.success) {
					alert("删除模板类型节点失败！\n\n" + msg.msg);
				} else {
					alert("删除模板类型节点成功！");

					loadTree();

					$('.delete-file').addClass('hidden');
				}
			},
			error : function(a, b, c) {
				alert('Server error! ' + b);
			}
		});
	});

	
	var listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size, key, sort) {
			var typeId=$('#temTypeId').val();
			var url='${request.contextPath}/config/templatemanage/templateList.do?page=' + page + '&size=' + size+'&typeId='+typeId;
			
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#temList").html($("#temList", $html).html());
					List('.tem-list',listConfig);
			}, 'html');
		}
	});
    List('.tem-list',listConfig);
    
	$("#adddatabtn").click(function(){
		var temTypeId=$('#temTypeId').val();
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '上传模板管理',
			showFoot : false,
			uri : '${request.contextPath}/config/templatemanage/uploadView?temTypeId='+temTypeId
		})
	});
	
	$("#editbtn").click(function(){
		var temTypeId=$('#temTypeId').val();
		var checked = List.checked($('.tem-list'));
		if (checked.length == 0 ) {
			alert('请选择需要修改的模板!');
			return;
		} else if (checked.length > 1) {
			alert('只能修改一个模板!');
			return;
		} else {
			//TODO: 
			var fileid =checked.eq(0).data('id') ;
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '上传模板管理',
				showFoot : false,
				uri : '${request.contextPath}/config/templatemanage/uploadView?temTypeId='+temTypeId+'&fileid='+fileid
			})
		}
		
	});
	
	// remove btn click
	$('#deletebtn').on('click', function() {
		
		var checked = List.checked($('.tem-list'));
		if (checked.length == 0 ) {
			alert('请选择需要删除的模板!');
			return;
		} else if (checked.length > 1) {
			alert('只能删除一个模板!');
			return;
		} else {
			//TODO: 
			var temid =checked.eq(0).data('id') ;
			var uri = '${request.contextPath}/config/templatemanage/delete?temid=' + temid;
			var msg = '是否确认删除模板？提示：请谨慎操作！';
	
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
						var ids = (msg.msg).split(',');
						for ( var i = 0;i<ids.length;i++){
							$.ajax({
								url : '${request.contextPath}/file/remove?fileId='+ids[i],
								type : 'POST',
								success : function(msg) {
									
								},
								error : function(a, b, c) {
									alert('Server error! ' + b);
								}
							});
						}
						alert("删除表单成功！");
						Suredy.loadContent('${request.contextPath}/config/templatemanage/templateList.do');
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		}
	});
});
function getFileView(templateId){	
	require(['suredyModal','jqueryForm'], function(Modal) {
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '模板显示',
			showFoot : false,
			uri : '${request.contextPath}/config/templatemanage/templateView?templateId='+templateId
		})
	});
};	

</script>