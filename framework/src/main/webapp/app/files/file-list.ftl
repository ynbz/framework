<div class="row">
	<div class="col-md-4 col-sm-4">
		<input type="hidden" value="" id="typeId" /> 
		<div class="row" style="padding: 9px 0px 5px 15px">	
			<div class="btn btn-primary btn-sm" id="updateIndex">
				<i class="icon-search"></i> 更新索引
			</div>	
						
			<div class="btn btn-info btn-sm" id="create">
				<i class="icon-plus"></i> 新建
			</div>
			<div class="btn btn-warning btn-sm hidden" id="edit">
				<i class="icon-edit "></i> 修改
			</div>	
			<div class="btn btn-success btn-sm hidden" id="permission">
				<i class="icon-user-md"></i> 权限
			</div>											
			<div class="btn btn-danger btn-sm hidden" id="remove">
				<i class="icon-remove"></i> 删除
			</div>	
							
		</div>
		<div class="file-tree" style="border-top: 1px solid #ddd; max-height:600px; overflow:auto;"  >加载中......</div>
	</div>
	<div class="col-md-8 col-sm-8">
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-12 col-sm-12" style="padding-bottom:5px;">
				<div class="form-inline">		
					<div class="btn btn-info btn-sm" id="adddatabtn">
						<i class="icon-plus"></i> 上传文件
					</div>
					<div class="btn btn-warning btn-sm " id="editbtn">
						<i class="icon-edit "></i> 修改文件
					</div>
					<div class="btn btn-danger btn-sm" id="deletebtn">
						<i class="icon-remove"></i> 删除文件
					</div>
					<#if permissions ? seq_contains('file_verify')>
					<div class="btn btn-primary btn-sm" id="verifybtn">
						<i class="icon-remove"></i> 通过审核
					</div>
					</#if>
				</div>
			</div>
		</div>
		<div role="tabpanel" class="suredy-tabs">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active">
					<a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">文件内容 </a>
				</li>
				<li role="presentation">
					<a href="#logInfo" aria-controls="logInfo" role="tab" data-toggle="tab">备忘录 [<span id="badge_log">0</span>]</a>
				</li>
			</ul>
			<div class="tab-content">
			<div role="tabpanel" id="tab1" class="tab-pane active" style="padding-top:5px;">
				<div class="row">
					<div class="col-md-12 col-sm-12">
						<div id="fileList">
							<table class="file-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>"style="width: 100%;">
								<tr class="title-row">
									<th style="display: none"></th>
									<th>文件标题</th>
									<th width="50" class="text-center">状态</th>
									<th width="150">上传人</th>
									<th width="100">上传时间</th>
								</tr>
								<#if data??> <#list data as file>
								<tr data-id="${file.id}">
									<td style="display: none">${file.id}</td>
									<td><a href='javascript:void(0)'onclick="getFileView('${file.id}')">${file.title}</a></td>
									<td width="50" align="center">
										<#if 1==file.status><font color="green">通过</font><#else><font color="red">未审</font></#if> 
									</td>
									<td width="150">${file.uploader}</td>
									<td width="100">${(file.uploadTime?string('yyyy-MM-dd'))!''}</td>
								</tr>
								</#list> </#if>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div role="tabpanel" id="logInfo" class="tab-pane" style="padding-top:5px;">
	
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

require(['suredyList','suredyTree','suredyModal', 'notify'], function(List, Tree, Modal) {
	var loadTree = function() {
		Tree('.file-tree', '${request.contextPath}/files/type-tree', {
			autoCollapse : true,
			collapseAll : true,
			inContainer : false,
			size : 'sm'
		});
		
		// 取消active状态
		$.each(Tree.checked('.file-tree'),function(i,v) {
			$(v).trigger('click');
		});
		
		$('.file-tree .suredy-tree li>span+ul').collapse('show');
		
		$('.file-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
			if (Tree.isActive($node)){
				var nodeData = Tree.data($node);
				
				$('#typeId').val(nodeData.id);
				$('#edit').removeClass('hidden');
				$('#remove').removeClass('hidden');
				$('#permission').removeClass('hidden');
				if(nodeData.id!=''){
					Suredy.loadContent('${request.contextPath}/files/log-list?folderId='+nodeData.id,{container:'#logInfo',callback:function(){$('#badge_log').text($("#log_list").find("tr").length-1);}});
				}
				
				var url='${request.contextPath}/files/manager?typeId=' + nodeData.id;
				$.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#fileList").html($("#fileList", $html).html());
					List('.file-list',listConfig);
				}, 'html');	
			} else {
				$('#typeId').val('');
				$('#edit').addClass('hidden');
				$('#remove').addClass('hidden');
				$('#permission').addClass('hidden');
			}
		});
	};
	loadTree();	
	
	$('#permission').on('click', function() {
		var typeId = $('#typeId').val();
		Modal.showModal({
			size : 'lg',
			icon : 'icon-user-md',
			title : '权限管理',
			showFoot : false,
			uri : '${request.contextPath}/files/folder-permission?typeId=' + typeId
		});
	});
	
	$('#create').on('click', function() {
		var parentId = $("#typeId").val();
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '创建文件类型',
			showFoot : false,
			uri : '${request.contextPath}/files/type-info?parentId=' + parentId
		});
	});

	// edit file btn click
	$('#edit').on('click', function() {	
		var typeId = $('#typeId').val();
		if (typeId == '') {
			alert('[警告] 未选择文件类型节点，或者文件类型节点不可编辑！');
			return;
		}
		Modal.showModal({
			size : 'lg',
			icon : 'icon-edit',
			title : '修改文件类型',
			showFoot : false,
			uri : '${request.contextPath}/files/type-info?id=' + typeId
		});
	
	});

	// remove btn click
	$('#remove').on('click', function() {
		// get selected node
		var typeId = $('#typeId').val();

		if (typeId == '') {
			alert('请选择需要删除的文件类型节点！');
			return;
		}


		var msg = '是否确认删除【选中的节点】？\n\n\提示：\n\该操作将会删除文件类型下的所有子节点！\n\请谨慎操作！';

		if (!window.confirm(msg)) {
			return false;
		}
		$.ajax({
			url : '${request.contextPath}/files/type-delete',
			type : 'POST',
			data : {
				typeId : typeId
			},
			success : function(msg) {
				if (!msg) {
					alert('Unknown exception!');
				} else if (!msg.success) {
					alert("删除文件类型节点失败！\n\n" + msg.msg);
				} else {
					$.notify({title:'提示：',message:'分类数据已删除！'});
					loadTree();
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
			var typeId=$('#typeId').val();
			var url='${request.contextPath}/files/manager?page=' + page + '&size=' + size+'&typeId='+typeId;
			
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#fileList").html($("#fileList", $html).html());
					List('.file-list',listConfig);
			}, 'html');
		}
	});
    List('.file-list',listConfig);	
    
    

	$("#adddatabtn").click(function(){
		var typeId=$('#typeId').val();
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '上传文件',
			showFoot : false,
			uri : '${request.contextPath}/files/file-upload?typeId='+typeId
		})
	});
	
	$('#updateIndex').click(function(){
		Modal.showProcessDialog({text:'系统正在更新索引以提供全文搜索，请稍候……'});
		$.ajax({
			url : '${request.contextPath}/files/index/update',
			type : 'POST',
			async : true ,
			cache : false, 
			success : function(msg) {
				Modal.closeProcessDialog();
			},
			error : function(a, b, c) {
				alert('Server error! ' + b);
				Modal.closeProcessDialog();
			}
		});		
	});
	
	$("#editbtn").click(function(){
		var typeId=$('#typeId').val();
		var checked = List.checked($('.file-list'));
		if (checked.length == 0 ) {
			alert('请选择需要修改的文件!');
			return;
		} else if (checked.length > 1) {
			alert('只能修改一个文件!');
			return;
		} else {
			var fileId =checked.eq(0).data('id') ;
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '上传文件管理',
				showFoot : false,
				uri : '${request.contextPath}/files/file-upload?typeId='+typeId+'&fileId='+fileId
			})
		}
		
	});
	
	// remove btn click
	$('#deletebtn').on('click', function() {
		
		var checked = List.checked($('.file-list'));
		if (checked.length == 0 ) {
			alert('请选择需要删除的文件!');
			return;
		} else if (checked.length > 1) {
			alert('只能删除一个文件!');
			return;
		} else {
			//TODO: 
			var fileid =checked.eq(0).data('id') ;
			var uri = '${request.contextPath}/files/file-delete?fileid=' + fileid;
			var msg = '是否确认删除文件？提示：请谨慎操作！';
	
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
						alert("删除文件失败！\n\n" + msg.msg);
					} else {
						$.notify({title:'提示：',message:'文件数据已删除！'});
						Suredy.loadContent('${request.contextPath}/files/manager');
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		}
	});
	
	// Verify btn click
	$('#verifybtn').on('click', function() {
		var checked = List.checked($('.file-list'));
		if (checked.length == 0 ) {
			alert('请选择需要审核通过的文件!');
			return;
		} else {
			var fileids ="";
			checked.each(function(index,tr){
				if(fileids!=''){
					fileids +=","+$(tr).data('id');
				}
				else{
					fileids =$(tr).data('id');
				}
			});
			var uri = '${request.contextPath}/files/file-verify';
			var msg = '是否确认审核通过？';
			if (!window.confirm(msg)) {
				return;
			}
			$.ajax({
				url : uri,
				type : 'POST',
				data : {
					fileids:fileids
				},
				dataType : 'json',
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert("确认文件失败！\n\n" + msg.msg);
					} else {
						$.notify({title:'提示：',message:'文件已审核通过！'});
						var page=$(".file-list").data('page');
						var size=$(".file-list").data('page-size');
						var typeId=$('#typeId').val();
						var url='${request.contextPath}/files/manager?page=' + page + '&size=' + size+'&typeId='+typeId;
						Suredy.loadContent( url+' #fileList>*',{container:'#fileList',callback:function(){
							List('.file-list',listConfig);
							}
						});
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		}
	});

});
function getFileView(fileId){	
	require(['suredyModal','jqueryForm'], function(Modal) {
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '文件显示',
			showFoot : false,
			uri : '${request.contextPath}/files/file-view?fileId='+fileId
		})
	});

};	

</script>