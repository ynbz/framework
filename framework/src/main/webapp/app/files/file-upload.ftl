<div class="container-fluid suredy-form"  style="padding-top: 0;">
	<div class="row">	
	<form name="form" id="formupload">
		<input type="hidden" id="fileIds" name="fileIds" value="${file.fileUrlId}"/>
		<input type="hidden" id="id" name="id" value="${file.id}"/>
		<div  class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">文件类型</div>
						<input type="hidden" class="form-control" id="fileTypeId" name="typeId" value="${file.folder.id}" />
						<input type="text" class="form-control" id="fileType" readonly="readonly" name="fileType" value="${file.folder.name}" />				
					</div>
				</div>
			</div>		
		</div>		
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">文件标题</div>
						<input type="text" class="form-control" id="title" name="title" maxlength="200" value="${file.title}" placeholder="&lt;最多200字&gt;"   />
					</div>
				</div>	
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">关键字/标签</div>
						<input type="text" class="form-control" id="keyWord" name="keyWord" maxlength="200" value="${file.keyWord}" placeholder="&lt;关键字 Or 标签&gt;"   />
					</div>
				</div>	
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="suredy-file"></div>
			</div>
		</div>				
		<div class="text-center" style="margin-top: 50px;">
			<div class="btn btn-info hidden" id="btn-save"  >
				<i class="icon-save"></i> 保存
			</div>
		</div>	
	</form>	
	</div>
</div>


<script>
	require([ 'suredyFile', 'suredyTreeSelector','validation','suredyModal','notify'], function(File,  TreeSelector, validators,Modal) {
		
		if($('#fileIds').val()!=''){
			$('#btn-save').removeClass('hidden');
		}
		
		// 初始化文件管理组件
		var $file = File('.suredy-file', {
			// 基本参数
			fileCount : -1,
			fileType : '',
			view : 'tiled',
			changeView : true,
			addFile : true,
			editable : true,
			download : true,

			// 组件上传参数
			autoUpload : true,
			fieldName : 'file'
		});

		// 初始化文件列表
		$.ajax({
			url : '${request.contextPath}/file/list-files',
			data : {
				fileIds : '${(file.fileUrlId)!'null'}'
			},
			type : 'post',
			dataType : 'json',
			success : function(data, textStatus, jqXHR) {
				if (!data || !data.success) {
					alert('No file be loaded.');
				}
				if (data.data) {
					$.each(data.data, function(i, d) {
						File.addFile($file, d);
					});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
			}
		});


		// 单个文件上传完成后的监听事件
		$file.on('uploaded.suredy.filemanager', '>.file-item.file', function(event, result) {
			// result.success为boolean值。表示是否上传成功			
			//console.log(result);
			var $this = $(this);
			var fileIds = eval(File.itemData(this)).id;
			if(fileIds!=''){
				if($('#fileIds').val()!=''){
					var fileIdsrep = $('#fileIds').val()+","+fileIds;			
					$('#fileIds').val(fileIdsrep.replace(",,",","));
				} else {
					$('#fileIds').val(fileIds);
				}
				$('#btn-save').removeClass('hidden');
			}
			
			if (result.data) {
				var fileName = result.data.fileName;
				var title = fileName.substring(0, fileName.lastIndexOf('.'));
				if ($('#title').val() == '') {
					$('#title').val(title);
				}
				if (result.data.repeated) {
					if(!confirm('系统中存在一份内容完全一样的文件.\n点 [确定] 按钮忽略重复内容完成文件上传;\n点 [取消] 按钮删除当前重复文件!')) {
						$.ajax({
							url : Suredy.ctxp + '/files/remove',
							data : {
								fileId : result.data.id
							},
							type : 'post',
							dataType : 'json',
							success : function(data, textStatus, jqXHR) {
								var success = data && !!data.success;

								if (!success) {
									alert((data && data.msg) || '无法移除文件，请重试！');
								} 
								console.log($this); // 上传文件的数据
								$this.remove();
							},
							error : function(jqXHR, textStatus, errorThrown) {
								alert('无法移除文件，请重试！' + errorThrown);
							}
						});
					}
				}
			}
			//File.plugin($file).ERR_MESSAGE('上传完毕事件, success:' + result.success, '提示');
		});
		
		// 单个文件删除完成后的监听事件
		$file.on('deleted.suredy.filemanager', function(event, result) {
			
			var fid = eval(result.data).id;
			var fileIds=$('#fileIds').val();
			var repfileIds=fileIds.replace(fid,'');
			fileIds=repfileIds.replace(",,",",");
			if(fileIds.length<=1){
				$('#btn-save').addClass('hidden');	
				$('#fileIds').val('');
			}
			
			$('#fileIds').val(fileIds);
		});
		
		
		$('#formupload').bootstrapValidator({
	        message: '输入错误',
	         feedbackIcons: {
//		            valid: 'glyphicon glyphicon-ok',
//		            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
			fields : {
				title : {
					validators : {
						notEmpty : {
							message : '请填写文件标题！'
						}
					}
				},
				fileType : {
					validators : {
						notEmpty : {
							message : '请选择文件类型！'
						}
					}
				}
			}
		});
		
		
		TreeSelector('input#fileType', '${request.contextPath}/files/type-tree', {
			leafCheckbox:true,
			size : 'lg',
			showTitle : true,
			autoCollapse : false,
			canFolderActive:false
		});		
		
		
// 		$.ajax({
// 			url : '${request.contextPath}/files/type-tree',
// 			type : 'get',
// 			dataType : 'json',
// 			success : function(data) {
// 				TreeSelector('input#fileType',data, {
// 					leafCheckbox:true,
// 					size : 'lg',
// 					showTitle : true,
// 					autoCollapse : false,
// 					canFolderActive:false
// 				});
// 			}
// 		});		
		
		
		TreeSelector.nodes('input#fileType').each(function(i, dom) {
			var $node = $(dom);

			if (TreeSelector.data($node, 'id') == $('#fileTypeId').val()) {
				TreeSelector.setActive($node);
				return false;
			}
		});
		
		
		$('input#fileType').on(TreeSelector.nodeClick, function(event, $node) {
			if (!TreeSelector.isActive($node)) {
				$('input#fileTypeId').val('');
				$('input#fileType').val('');
				$('#formupload').data('bootstrapValidator')
	            .updateStatus('fileType', 'NOT_VALIDATED', null)          
	            .validateField('fileType');
				return false;
			}
			$('input#fileTypeId').val(TreeSelector.data($node, 'id'));
			$('input#fileType').val(TreeSelector.data($node,'text'));
			$('#formupload').data('bootstrapValidator')
	        .updateStatus('fileType', 'NOT_VALIDATED', null)          
	        .validateField('fileType');
			TreeSelector.hidden(this);
			return true;
		});
		
		
		$("#btn-save").click(function(){
			var d1=$('#formupload').data('bootstrapValidator').validate();
			var vaild=$('#formupload').data('bootstrapValidator').isValid();
			//alert(vaild);
			if(!vaild){
				return false;
			}
			
		
			$.ajax({
				url : '${request.contextPath}/files/file-save',
				dataType : 'json',
				type : 'POST',
				data : $('#formupload').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存文件失败！');
					} else if (!success.success) {
						alert('保存文件失败！\n\n' + success.msg);
					} else {
						$.notify({title:'提示：',message:'文件数据已保存！'});
						Suredy.loadContent('${request.contextPath}/files/manager');
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
