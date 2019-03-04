<@t.header>
<style>
.input-lable {
	min-width: 95px;
}
</style>
 </@t.header> <@t.body>
<div class="container-fluid suredy-form" style="padding: 50px 100px 0px 100px;;">
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">标题</div>
					<input type="text" class="form-control"  id="title" name="title" readonly="readonly"  value="${notice.title}"/>	
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
						<input type="text" class="form-control"  value="${(notice.type==1)?string('公司公告',(notice.type==2)?string('部门公告',''))}" readonly="readonly"/>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-xs-6">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">有效时间</div>
					<input type="text" class="form-control" id="validDate" name="validDate" value="${(notice.validDate?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 col-xs-12">
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon input-lable">内容</div>
						<div  class="form-control" style="overflow-y: auto;max-height: 300px;height: 300px;">${notice.content}</div>
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
</@t.body> <@t.foot>
<script type="text/javascript">
require([ 'suredyFile'],function(File) {
	
	
	//文件上传， 初始化文件管理组件
	var $file = File('.suredy-file', {
		// 基本参数
		fileCount : -1,//上传个数
		fileType : '',//上传类型
		view : 'icon',//显示视图样式
		changeView : true,//是否显示视图
		addFile : false,//是否可以上传
		editable : false,//是否移出文件
		download : false,//是否可以下载

		// 组件上传参数
		autoUpload : false,
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
	
	
});
</script>
</@t.foot>
