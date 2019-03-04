<div class="container-fluid suredy-form"  style="padding-top: 0;">
	<div class="row">	
	<form name="form" id="reportEdit">
	<#if data??>
		<input type="hidden" id="id" name="id" value="${data.id!''}" />
		<input type="hidden" id="fileId" name="fileId" value="${data.fileId!''}" />
		<input type="hidden" size="40" id="reportTypeId" name="reportTypeId" value="${data.typeId!''}" />
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">报表分类</div>
						<input type="text" class="form-control" id="reportType" readonly="readonly" name="reportType" placeholder="&lt;报表分类&gt;" value="${data.typeName!''}" />
					</div>
				</div>
			</div>		
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">报表名称</div>
						<input type="text" class="form-control" id="name" name="name" maxlength="64" placeholder="&lt;最多64字&gt;" value="${data.name!''}"   />
					</div>
				</div>	
			</div>	
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">显示顺序</div>
						<input type="text" class="form-control" id="sort" name="sort" placeholder="&lt;显示顺序,默认 0&gt;" value="${data.sort!''}" />
					</div>
				</div>	
			</div>		
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">文件上传</div>
						<div class="form-control suredy-file" style="height: auto;">
							
						</div>
					</div>
				</div>	
			</div>				
		</div>
		
			
		<div class="row text-center">
			<div class="btn btn-info" id="btn-save" >
				<i class="icon-save"></i> 保存
			</div>
		</div>	
	</#if>	
	</form>	
	</div>
</div>


<script>
	require([ 'suredyFile','suredyTreeSelector','suredyModal','suredy', 'jqueryForm'], function(File, TreeSelector, Modal) {
		$.ajax({
			url : 'report/type/tree',
			type : 'get',
			dataType : 'json',
			success : function(data) {
				TreeSelector('input#reportType',data, {
					leafCheckbox:true,
					folderCheckbox:true,
					size : 'lg',
					showTitle : true,
					autoCollapse : false,
					canFolderActive:false
				});
			}
		});		
		
		$('input#reportType').on(TreeSelector.nodeClick, function(event, $node) {
			
			if (!TreeSelector.isActive($node)) {
				$('input#reportTypeId').val('');
				$('input#reportType').val('');
			}
			$('input#reportTypeId').val(TreeSelector.data($node, 'id'));
			$('input#reportType').val(TreeSelector.data($node,'text'));
			TreeSelector.hidden(this);
			return true;
			
		});
		
		
		var $file = File('.suredy-file', {
			// 基本参数
			fileCount : 1,
			view : 'detail',
			changeView : true,
			addFile : true,
			editable : true,
			download : true,

			// 组件上传参数
			autoUpload : true,
			fieldName : 'file',
		});

		// 初始化文件列表
		$.ajax({
			url : Suredy.ctxp + '/file/list-files',
			data : {
				fileIds : '${data.fileId}'
			},
			type : 'post',
			dataType : 'json',
			success : function(data, textStatus, jqXHR) {
				if (!data || !data.success)
					alert('No file be loaded.');

				$.each(data.data, function(i, d) {
					File.addFile($file, d);
				});
			},
			error : function(jqXHR, textStatus, errorThrown) {
			}
		});
		
		// 单个文件上传完成后的监听事件
		$file.on('uploaded.suredy.filemanager', '>.file-item.file', function(event, result) {
			// result.success为boolean值。表示是否上传成功			
			//File.plugin($file).ERR_MESSAGE('上传完毕事件, success:' + result.success, '提示');
			//console.log(result.data); // 上传文件的数据
			if (result.success) {
				$('#fileId').val(result.data.id);
			}
		});

		// 单个文件删除完成后的监听事件
		$file.on('deleted.suredy.filemanager', function(event, result) {
			// result.success为boolean值。表示是否删除成功
			//File.plugin($file).ERR_MESSAGE('删除完毕事件, success:' + result.success, '提示');
			//console.log(result.$item); // 上传文件的数据
			if (result.success) {
				$('#fileId').val('');
			}
		});
		
		$("#btn-save").click(function(){
			
			var fileId = $('#fileId').val();
			if (fileId == '' || fileId.indexOf('-')!= -1) {
				alert('请上传报表文件');
				return;
			}	
			var reportTypeId = $('#reportTypeId').val();
			if (reportTypeId == '') {
				alert('请选择报表分类！');
				return;
			}				
			var name = $('#name').val();
			if (name == '') {
				alert('请填写报表名称！');
				return;
			}			
			
		
			$.ajax({
				url : 'report/file/save',
				dataType : 'json',
				type : 'POST',
				data : $('#reportEdit').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存报表失败！');
					} else if (!success.success) {
						alert('保存报表失败！\n\n' + success.msg);
					} else {
						alert('保存报表成功！');
						Suredy.loadContent('report/manager');
						Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + c);
				}
			});
		});
	});
</script>
