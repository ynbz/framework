<div class="container-fluid suredy-form"  style="padding-top: 0;">
	<div class="row">	
	<form name="form" id="formupload">
		<input type="hidden" id="templateId" name="templateId"/>
		<div class="row">
			<div class="col-md-6 col-xs-6">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">模板标题</div>
						<input type="text" class="form-control" id="templateTitel" name="templateTitel" maxlength="50" value="${template.templateTitel}"  placeholder="&lt;最多50字&gt;"   />
					</div>
				</div>	
			</div>
			
			<div class="col-md-6 col-xs-6">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-group-addon">模板类型</div>
						<#if type??>
							<input type="hidden" class="form-control" id="templateTypeId" name="templateTypeId" value="${type.id}" />
							<input type="text" class="form-control" id="templateType" readonly="readonly" name="templateType" value="${type.typeName}" />
						<#else>
							<input type="hidden" class="form-control" id="templateTypeId" name="templateTypeId" value="${template.type.id}" />
							<input type="text" class="form-control" id="templateType" readonly="readonly" name="templateType" value="${template.type.typeName}"/>
						</#if>
					</div>
				</div>
			</div>
			
		</div>
		
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="suredy-template"></div>
			</div>
		</div>				
		<div class="text-center" style="margin-top: 100px;">
			<div class="btn btn-info hidden" id="btn-save" >
				<i class="icon-save"></i> 保存
			</div>
		</div>	
	</form>	
	</div>
</div>
<script>
	require([ 'suredyFile','suredyTreeSelector','validation','suredyModal','suredy', 'jqueryForm'], function(File,TreeSelector,validators,Modal) {
		
		$('#formupload').bootstrapValidator({
	        message: '输入错误',
	         feedbackIcons: {
//		            valid: 'glyphicon glyphicon-ok',
//		            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
			fields : {
				templateTitel : {
					validators : {
						notEmpty : {
							message : '请填写模板标题！'
						}
					}
				},
				templateType : {
					validators : {
						notEmpty : {
							message : '请选择模板类型！'
						}
					}
				}
			}
		});
		$.ajax({
			url : '${request.contextPath}/config/templatetype/tree',
			type : 'get',
			dataType : 'json',
			success : function(data) {
				TreeSelector('input#templateType',data, {
					leafCheckbox:true,
					size : 'lg',
					showTitle : true,
					autoCollapse : false,
					canFolderActive:false
				});
			}
		});		
		$('input#templateType').on(TreeSelector.nodeClick, function(event, $node) {
			if (!TreeSelector.isActive($node)) {
				$('input[name="templateTypeId"]').val('');
				$('input#templateType').val('');
				$('#formupload').data('bootstrapValidator')
	            .updateStatus('templateType', 'NOT_VALIDATED', null)          
	            .validateField('templateType');
				return false;
			}
			$('input[name="templateTypeId"]').val(TreeSelector.data($node));
			$('input#templateType').val(TreeSelector.data($node,'text'));
			$('#formupload').data('bootstrapValidator')
	        .updateStatus('templateType', 'NOT_VALIDATED', null)          
	        .validateField('templateType');
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
				url : '${request.contextPath}/config/templatemanage/saveTemplate',
				dataType : 'json',
				type : 'POST',
				data : $('#formupload').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存模板失败！');
					} else if (!success.success) {
						alert('保存模板失败！\n\n' + success.msg);
					} else {
						alert('保存模板成功！');
						Suredy.loadContent('${request.contextPath}/config/templatemanage/templateList');
						Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + c);
				}
			});
		});
		
		// 初始化文件管理组件
		var $file = File('.suredy-template', {
			// 基本参数
			fileCount : 1,//上传个数
			fileType : '',//上传类型
			view : 'tiled',//显示视图样式
			changeView : true,//是否显示视图
			addFile : true,//是否可以上传
			editable : true,//是否移出文件
			download : false,//是否可以下载

			// 组件上传参数
			autoUpload : true,
			fieldName : 'file',
			extendData : 'test',//扩展字段
		});
		
		
		
		// 单个文件上传完成后的监听事件
		$file.on('uploaded.suredy.filemanager', '>.file-item.file', function(event, result) {
			// result.success为boolean值。表示是否上传成功			
			//console.log(result);
			//console.log(File.itemData(this));
			var templateId = eval(File.itemData(this)).id;
			
			if(templateId!=''){
				$('#templateId').val(templateId);
				$('#btn-save').removeClass('hidden');
			}
			//File.plugin($file).ERR_MESSAGE('上传完毕事件, success:' + result.success, '提示');
		});
		
		// 单个文件删除完成后的监听事件
		$file.on('deleted.suredy.filemanager', function(event, result) {
			$('#templateId').val('');
			$('#btn-save').addClass('hidden');
		});
	});
</script>
