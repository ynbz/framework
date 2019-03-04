<style>
.input-lable {
	min-width: 95px;
}
</style>
<form method="post" name="noticeForm" id="noticeForm">
	<input type="hidden" id="fileId" name="fileId" value="${notice.fileId}"/>
	<input type="hidden" id="id" name="id" value="${notice.id}"/>
	<input type="hidden" id="content" name="content"/>
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">标题</div>
					<input type="text" class="form-control"  id="title" name="title"  value="${notice.title}"/>	
				</div>
			</div>
		</div>
	</div>
		
	<div class="row">
		<div class="col-md-6 col-xs-6">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">发布人</div>
					<input type="text" class="form-control"  id="issuer" name="issuer" readonly="readonly"  value="${notice.issuer}"/>	
				</div>
			</div>
		</div>
		<div class="col-md-6 col-xs-6">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">发布部门</div>
					<input type="text" class="form-control"  id="issueUnit" name="issueUnit" readonly="readonly"  value="${notice.issueUnit}"/>	
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-6 col-xs-6">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">发布类型</div>
						<select class="form-control" name="type" id="type">
							<option value="1" ${((notice.type==1)?string('selected',''))!''}>公司公告</option>
							<option value="2" ${((notice.type==2)?string('selected',''))!''}>部门公告</option>
						</select>	
				</div>
			</div>
		</div>
		<div class="col-md-6 col-xs-6">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">有效时间</div>
					<input type="text" class="form-control datetimepicker" id="validDate" name="validDate" value="${(notice.validDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div id="alerts"></div>
		    <div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor" style="padding-left: 95px;">
			      <div class="btn-group">
			        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font"><i class="icon-font"></i><b class="caret"></b></a>
			          <ul class="dropdown-menu">
			          </ul>
			        </div>
			      <div class="btn-group">
			        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size"><i class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
			          <ul class="dropdown-menu">
			          <li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
			          <li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
			          <li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
			          </ul>
			      </div>
			      <div class="btn-group">
			        <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i class="icon-bold"></i></a>
			        <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a>
			        <a class="btn" data-edit="strikethrough" title="Strikethrough"><i class="icon-strikethrough"></i></a>
			        <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i class="icon-underline"></i></a>
			      </div>
			      <div class="btn-group">
			        <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i class="icon-list-ul"></i></a>
			        <a class="btn" data-edit="insertorderedlist" title="Number list"><i class="icon-list-ol"></i></a>
			        <a class="btn" data-edit="outdent" title="Reduce indent (Shift+Tab)"><i class="icon-indent-left"></i></a>
			        <a class="btn" data-edit="indent" title="Indent (Tab)"><i class="icon-indent-right"></i></a>
			      </div>
			      <div class="btn-group">
			        <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i class="icon-align-left"></i></a>
			        <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i class="icon-align-center"></i></a>
			        <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i class="icon-align-right"></i></a>
			        <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i class="icon-align-justify"></i></a>
			      </div>
			      <div class="btn-group">
			        <a class="btn" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i class="icon-undo"></i></a>
			        <a class="btn" data-edit="redo" title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
			      </div>
			      <input type="text" data-edit="inserttext" name="" id="voiceBtn" x-webkit-speech="">
		    </div>
		
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">内容</div>
						<div id="editor"  class="form-control" style="overflow-y: auto;max-height: 300px;height: 300px;">${notice.content}</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div class="suredy-file"></div>
		</div>
	</div>	
	<div class="text-center" style="margin-top: 10px;">
		<div class="btn btn-info" id="btn-save">
			<i class="icon-save"></i> 保存
		</div>
	</div>
</form>
<script type="text/javascript">
require([ 'validation','suredyFile','suredyModal','suredyDatetimepicker','wysiwyg','hotkeys'],function(validators,File,Modal) {
	Suredy.Datetimepicker.AutoDelegate(true);
	//富文本编辑器
	 $(function(){
	    function initToolbarBootstrapBindings() {
	      var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier', 'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande',
	                   'Lucida Sans', 'Tahoma', 'Times','Times New Roman', 'Verdana'],
	      fontTarget = $('[title=Font]').siblings('.dropdown-menu');
	      $.each(fonts, function (idx, fontName) {
	          fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
	      });
	      $('a[title]').tooltip({container:'body'});
	    	$('.dropdown-menu input').click(function() {return false;})
			    .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
	       .keydown('esc', function () {this.value='';$(this).change();});

	      $('[data-role=magic-overlay]').each(function () { 
	        var overlay = $(this), target = $(overlay.data('target')); 
	        overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
	      });
	      if ("onwebkitspeechchange"  in document.createElement("input")) {
	        var editorOffset = $('#editor').offset();
	        $('#voiceBtn').css('position','absolute').offset({top: editorOffset.top, left: editorOffset.left+$('#editor').innerWidth()-35});
	      } else {
	        $('#voiceBtn').hide();
	      }
		};
		function showErrorAlert (reason, detail) {
			var msg='';
			if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
			else {
				console.log("error uploading file", reason, detail);
			}
			$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
			 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
		};
	    initToolbarBootstrapBindings();
		$('#editor').wysiwyg({ fileUploadError: showErrorAlert} );
	});
	
	//文件上传， 初始化文件管理组件
	var $file = File('.suredy-file', {
		// 基本参数
		fileCount : -1,//上传个数
		fileType : '',//上传类型
		view : 'icon',//显示视图样式
		changeView : true,//是否显示视图
		addFile : true,//是否可以上传
		editable : true,//是否移出文件
		download : false,//是否可以下载

		// 组件上传参数
		autoUpload : true,
		fieldName : 'file',
		extendData : 'test',//扩展字段
	});
	
	if('${notice.fileId!=''}'){
		// 初始化文件列表
		$.ajax({
			url : '${request.contextPath}/file/list-files',
			data : {
				fileIds : '${notice.fileId}'
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
	}
	
	// 单个文件上传完成后的监听事件
	$file.on('uploaded.suredy.filemanager', '>.file-item.file', function(event, result) {
		var fileId = eval(File.itemData(this)).id;
		if(fileId!=''){
			if($('#fileId').val()!=''){
				var fileIdsrep = $('#fileId').val()+","+fileId;			
				$('#fileId').val(fileIdsrep.replace(",,",","));
			} else {
				$('#fileId').val(fileId);
			}
		}
	});
	
	// 单个文件删除完成后的监听事件
	$file.on('deleted.suredy.filemanager', function(event, result) {
		
		var fid = eval(result.data).id;
		var fileId=$('#fileId').val();
		if(fileId.indexOf(fid)==0){
			fileId=fileId.replace(fid+",",'');
		}else{
			var repfileIds=fileId.replace(fid,'');
			fileId=repfileIds.replace(",,",",");
		}
		
		if(fileId.length<=2){
			$('#fileId').val('');
		}
		$('#fileId').val(fileId);
	});
		
	$("#btn-save").click(function(){
		var title = $('#title').val();
		var validDate = $('#validDate').val();
		var content = $('#editor').html();
		if(title==''){
			alert('请填写标题！');
			return false;
		}
		if(validDate==''){
			alert('请填写有效时间！');
			return false;
		}
		if(content==''){
			alert('请填写内容！');
			return false;
		}else{
			$('#content').val(content);
		}
		
		$.ajax({
			url : '${request.contextPath}/notice/notice-save',
			dataType : 'json',
			type : 'POST',
			data : $('#noticeForm').serialize(),
			success : function(success) {
				if (!success) {
					alert('保存文件失败！');
				} else if (!success.success) {
					alert('保存文件失败！\n\n' + success.msg);
				} else {
					alert('保存文件成功！');
					Suredy.loadContent('${request.contextPath}/notice/notice-list');
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