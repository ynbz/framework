<div class="row">
	<div class="col-md-12 col-sm-12" style="padding: 6px 15px">
		<form id="form" name="form">
			<div class="input-group input-group-sm">
				<div class="input-group-addon">开始时间</div>
				<input type="text" class="form-control datetimepicker"  id="startTime"  data-format="yyyy-MM-dd hh:MM:ss"  name="startTime" value="" />
				<div class="input-group-addon" style="border-left:0px; border-right:0px;">结束时间</div>
				<input type="text" class="form-control datetimepicker"  id="endTime" name="endTime" value="" data-format="yyyy-MM-dd hh:MM:ss" />
				<div class="input-group-addon" style="border-left:0px; border-right:0px;">登录人</div>
				<input type="text" class="form-control"  id="loginName" name="loginName" />
				<div class="input-group-btn">
					<button class="btn btn-sm btn-info" id="properSearchbtn" style="margin-left:2px;"><i class="icon-search"></i> 查询</button>
				</div>
			</div>
		</form>
	</div>
</div>		

<div class="row" style="padding: 0px 15px">
	<div id="loginLogList">
		<table class="loginLog-list" data-page="${pageIndex ! '1'}" data-page-size="${pageSize!'20'}" data-count="${count!'0'}"s>
			<tr class="title-row">
				<th style="display: none"></th>
				<th>登录人</th>
				<th>登录IP</th>
				<th>登录时间</th>
			</tr>
			<#if data??> <#list data as loginLog>
			<tr>
				<td style="display: none">${loginLog.id}</td>
				<td>${loginLog.loginName}</td>
				<td>${loginLog.loginIp}</td>
				<td>${(loginLog.logindate?string('yyyy-MM-dd hh:MM:ss'))!''}</td>
			</tr>
			</#list> </#if>
		</table>
	</div>
</div>

<script type="text/javascript">
var listConfig;
var tempTree;
var tempList;
require(['suredyList','jqueryForm','suredyDatetimepicker'], function(List) {
	Suredy.Datetimepicker.AutoDelegate(true);
	var listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size) {
			var url='${request.contextPath}/config/loginlog/list.do?page=' + page + '&size=' + size;
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#loginLogList").html($("#loginLogList", $html).html());
					List('.loginLog-list',listConfig);
			}, 'html');
		}
	});
   
	$("#properSearchbtn").click(function(){
		var url='${request.contextPath}/config/loginlog/list.do';
		$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
			var $html = $(html);
			$("#loginLogList").html($("#loginLogList", $html).html());
			//$("#loginLogList").html($html[2].innerHTML);
			List('.loginLog-list',listConfig);
		}, 'html');
	});
	
	List('.loginLog-list',listConfig);	
});
</script>
