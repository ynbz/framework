<!--  <div class="suredy-fileupload default" data-auto-upload="false"></div>
<hr>
<div class="suredy-fileupload bs"></div>
<hr>-->
<div class="container-fluid suredy-form"  style="padding-top: 0;">
	<div class="row">
		<div class="row">
			<div class="col-md-6 col-xs-6">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">模板标题</div>
						<input type="text" class="form-control" id="TemplateTitel" name="TemplateTitel" value="${template.templateTitel}" readonly="readonly"  />
					</div>
				</div>	
			</div>
			
			<div class="col-md-6 col-xs-6">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">模板类型</div>
						<input type="text" class="form-control" id="TemplateType" readonly="readonly" name="TemplateType" value="${template.type.typeName}" />
						
					</div>
				</div>
			</div>
			
		</div>
		
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="suredy-template"></div>
			</div>
		</div>		
	</div>
</div>

<!--  <hr>
<div class="btn btn-danger download" data-file-id="402881485861ba93015861bc48a10000">下载“bootstrap-fileinput”</div>
<a class="btn btn-danger view" href="javascript:;" data-relative-file-path="20161114/b2e978b12284449e8f4d7039d1b03044">查看“clean”</a>
<a class="btn btn-danger view" href="javascript:;" data-file-id="4028814858618b280158618be6aa0002">查看“tree_icons”</a>
-->
<script>
	require([ 'suredyFile'],function(File) {
		// 初始化文件管理组件
		var $file = File('.suredy-template', {
			// 基本参数
			fileCount : -1,
			fileType : '',
			view : 'tiled',
			changeView : true,
			addFile : false,
			editable : false,
			download : true,

			// 组件上传参数
			autoUpload : false,
			fieldName : 'file',
			extendData : 'test',
		});

		// 初始化文件列表
		$.ajax({
			url : Suredy.ctxp + '/file/list-files',
			data : {
				fileIds : '${template.templateUrlId}',
				extendData : 'test',
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
