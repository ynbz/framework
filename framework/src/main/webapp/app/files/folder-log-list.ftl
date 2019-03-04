	<div class="row">
		<form name="form" id="logForm">
			<input type="hidden" value="${folderId}" id="folderId" name="folderId"/> 
			<div class="col-md-12 col-sm-12 ">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">
							备忘录
						</div>
						<textarea rows="3" name="logContent" id="logContent" class="form-control"></textarea>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">				
					<div class="text-right">
						<div class="btn btn-info btn-sm" id="btn-save-log">
							<i class="icon-save"></i> 保存
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<div  style="overflow:auto;max-height:400px;">	
				<table class="log-list" id="log_list">
					<tr class="title-row">
						<th style="display: none"></th>
						<th class="text-center">备忘内容</th>
						<th class="text-center" width="60px">操作</th>
					</tr>
					<#if logList??> <#list logList as log>
						<tr>
							<td style="display: none">${log.id}</td>
							<td><i><small>${log.addUser} ${(log.logDate?string('yyyy-MM-dd hh:mm'))!''}</small></i><br/>&nbsp;&nbsp;&nbsp;&nbsp;${log.logContent}</td>
							<td width="60px" class="text-center">
								<div class="btn btn-xs remove-btn" data-id="${log.id}">
									<i class="text-danger glyphicon glyphicon-remove"></i>
								</div>
							</td>
						</tr>
					</#list> </#if>
				</table>
				</div>
			</div>
		</div>
	</div>
<script>
require(['suredyList','notify'], function(List) {
	var logListConfig = ({
		header : false,
		footer : false,
		search : false,
		checkbox : false,			
	});
    List('.log-list',logListConfig);	
    
    $(".remove-btn").click(function(){
    	var id=$(this).data("id");
    	$.ajax({
			url : '${request.contextPath}/files/log-delete?folderLogIds='+id,
			type : 'POST',
			success : function(success) {
				Suredy.loadContent('${request.contextPath}/files/log-list?folderId='+$('#folderId').val(),{container:'#logInfo'});
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + c);
			}
		});
    	
    });
    $('#btn-save-log').click(function() {
		var logContent = $('#logContent').val();
		if (logContent == '') {
			alert('请填写备忘内容！');
			return;
		}
		
		$.ajax({
			url : '${request.contextPath}/files/log-save',
			dataType : 'json',
			type : 'POST',
			data : $('#logForm').serialize(),
			success : function(success) {
					Suredy.loadContent('${request.contextPath}/files/log-list?folderId='+$('#folderId').val(),{container:'#logInfo',callback:function(){$('#badge_log').text($("#log_list").find("tr").length-1);}});
					$('#badge_log').text($("#log_list").find("tr").length);
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + c);
			}
		});
    });
});
</script>