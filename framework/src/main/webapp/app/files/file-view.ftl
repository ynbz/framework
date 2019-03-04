<div role="tabpanel" id="tab1" class="tab-pane active"  style="padding-top:5px;">
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div class="form-group" >
				<div class="input-group">
					<div class="input-group-addon">文件类型</div>
					<input type="text" class="form-control" id="type" readonly="readonly" name="type" value="${file.folder.name}" />
					<input type="hidden" class="form-control" id="id" name="id" value="${file.folder.id}"/>
				</div>
			</div>
		</div>		
		<div class="col-md-12 col-xs-12">
			<div class="form-group" >
				<div class="input-group">
					<div class="input-group-addon">文件标题</div>
					<input type="text" class="form-control" id="title" name="title" value="${file.title}" readonly="readonly"  />
				</div>
			</div>	
		</div>
		<div class="col-md-12 col-xs-12">
			<div class="form-group" >
				<div class="input-group">
					<div class="input-group-addon">关键字/标签</div>
					<input type="text" class="form-control" id="keyWord" name="keyWord" value="${file.keyWord}" readonly="readonly"  />
				</div>
			</div>	
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div class="suredy-file"></div>
		</div>
	</div>		
</div>


<script>
	require([ 'suredyFile'],function(File) {
		// 初始化文件管理组件
		var $file = File('.suredy-file', {
			// 基本参数
			fileCount : -1,
			fileType : '',
			view : 'tiled',
			changeView : true,
			addFile : false,
			editable : false,
			download : true,

			// 组件上传参数
			autoUpload : true,
			fieldName : 'file'
		});

		// 初始化文件列表
		$.ajax({
			url : '${request.contextPath}/file/list-files',
			data : {
				fileIds : '${file.fileUrlId}'
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
	});
</script>
