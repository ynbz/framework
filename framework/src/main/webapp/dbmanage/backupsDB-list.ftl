<style type="text/css">
.hr-line {
	margin-top: 0px;
	margin-bottom: 0px;
	border: 0;
	border-top: 1px solid #eee;
}
.bottom-input {
	padding-bottom: 5px;
}

</style>
<div class="row">
	<div class="col-md-12" style="margin-left: 5px;">
			<div class="row" style="padding-top: 10px;">
				<div class="form-inline">
					<div class="btn btn-info" id="addBackupsDB">
						<i class="icon-plus"></i> 一键备份
					</div>
					
					<div class="btn btn-danger" id="deletebtn">
						<i class="icon-remove"></i> 删除备份
					</div>	
					
					<div class="btn btn-info pull-right" id="properSearchbtn" style="display: none;margin-left:5px;">
						<i class="icon-search"></i> 查询
					</div>
					
					<div class="btn btn-info pull-right" id="conditionbtn" style=" ">
						<i class="icon-chevron-down"></i> 筛选
					</div>
				</div>
			</div>
			<div class="row">
				<div id="searchid" style="display: none; padding-left: 0px;">
					<form id="form" name="form">
						<div class="row" style="padding-top: 8px;">
							<div class="form-inline col-md-12" >
								<div class="form-group bottom-input">
									<div class="input-group" style="width: 400px;">
										<div class="input-group-addon" style="width: 85px;">开始时间</div>
										<input type="text" class="form-control datetimepicker"  id="startTime"  data-format="yyyy-MM-dd hh:MM:ss"  name="startTime" value="" />
									</div>
								</div>

								<div class="form-group bottom-input">
									<div class="input-group" style="width: 400px;">
										<div class="input-group-addon" style="width: 85px;">结束时间</div>
										<input type="text" class="form-control datetimepicker"  id="endTime" name="endTime" value="" data-format="yyyy-MM-dd hh:MM:ss" />
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		<div class="row" style="overflow-x: auto;padding-top: 5px;">
			<div id="backupsDBList">
				<table class="backupsDB-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>"style="width: 100%;">
					<tr class="title-row">
						<th style="display: none"></th>
						<th>备份人</th>
						<th>备份时间</th>
						<th>文件</th>
					</tr>
					<#if data??> <#list data as backupsDB>
					<tr>
						<td style="display: none">${backupsDB.id}</td>
						<td>${backupsDB.backupsPerson}</td>
						<td>${(backupsDB.backupsTime?string('yyyy-MM-dd hh:MM:ss'))!''}</td>
						<td>
							<div class="btn btn-warning btn-sm update-btn" style="margin-top: -2px; margin-left: 10px" onclick="restoreDB('${backupsDB.id}')"><i class="icon-edit"></i>数据库还原</div>&nbsp;&nbsp;&nbsp;&nbsp;					
							<div class="btn btn-warning btn-sm download-btn" style="margin-top: -2px; margin-left: 10px">
								<i class="icon-download"></i><a href='${request.contextPath}/config/dbmanage/download?fileId=${backupsDB.id}' style="color: white;">数据库下载</a>
							</div>
						</td>
					</tr>
					</#list> </#if>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var listConfig;
var tempTree;
var tempList;
require(['suredyList','jqueryForm','suredyDatetimepicker'], function(List) {
	var listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size) {
			var url='${request.contextPath}/config/dbmanage/backupsDBList.do?page=' + page + '&size=' + size;
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#backupsDBList").html($("#backupsDBList", $html).html());
					List('.backupsDB-list',listConfig);
			}, 'html');
		}
	});
    List('.backupsDB-list',listConfig);	
    
	$("#conditionbtn").click(function(){
		var btn = $('i', $(this));
		if (btn.hasClass('icon-chevron-down')) {
			btn.removeClass('icon-chevron-down');
			btn.addClass('icon-chevron-up');
			$('#properSearchbtn').show();
			$('#searchid').show();
			
		} else if (btn.hasClass('icon-chevron-up')) {
			btn.removeClass('icon-chevron-up');
			btn.addClass('icon-chevron-down');
			$('#properSearchbtn').hide();
			$('#searchid').hide();
		}
	});	
	$("#properSearchbtn").click(function(){
		var url='${request.contextPath}/config/dbmanage/backupsDBList.do';
		$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
			var $html = $(html);
			$("#backupsDBList").html($("#backupsDBList", $html).html());
			List('.backupsDB-list',listConfig);
		}, 'html');
	});

	$("#addBackupsDB").click(function(){
		$.ajax({
			url : 'dbmanage/backupsDB',
			dataType : 'json',
			type : 'POST',
			success : function(success) {
				if (!success) {
					alert('备份数据库失败！');
				} else if (!success.success) {
					alert('备份数据库失败！\n\n' + success.msg);
				} else {
					alert('备份数据库成功！');
					Suredy.loadContent('dbmanage/backupsDBList.do');
				}
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + c);
			}
		});
	});
	
	$("#deletebtn").click(function(){
		var chechedids="";
		var checked =List.checked($('.backupsDB-list'));
		if (checked.length == 0 ) {
			alert('请选择需要删除的备份数据库!');
			return;
		}else {
			var msg="备份数据库删除\n\n\提示：\n\删除的备份数据库将不存在\n\请谨慎操作！";
			if (!window.confirm(msg)) return false;
			for(var i=0;i<checked.length;i++){
				chechedids=chechedids+","+checked.eq(i).find('td').eq(1).html();
			}	
			$.ajax({
				type:'post',
				url:'dbmanage/deletedata.do?chechedids='+chechedids,
				success:function(data){
					var m=data.data;
					if(m){	
						alert('备份数据库删除成功！');
						Suredy.loadContent('dbmanage/backupsDBList.do');
					}
				},
				error:function(){
					//alert("服务器连接失败");
				}
			});
			
		} 	
	});	
});

function restoreDB(dbid){
	var msg = '是否还原数据库？\n\n提示：\n还原数据库可能导致数据丢失，请谨慎操作！';

	if (!window.confirm(msg)) {
		return;
	}
	
	$.ajax({
		url : 'dbmanage/restoreDB?id='+dbid,
		dataType : 'json',
		type : 'POST',
		success : function(success) {
			if (!success) {
				alert('还原数据库失败！');
			} else if (!success.success) {
				alert('还原数据库失败！\n\n' + success.msg);
			} else {
				alert('还原数据库成功！');
				Suredy.loadContent('dbmanage/backupsDBList.do');
			}
		},
		error : function(a, b, c) {
			alert('服务器错误! ' + c);
		}
	});
};	
</script>
