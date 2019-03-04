
 <#if data??>
<table class="my-list index-todo-list" style="width:100%;">
			<tr class="title-row">
						<th>标题</th>
						<th width="100">发送人</th>
						<th width="100">发送时间</th>
					</tr>
		<#list data as d>
		<tr style="cursor: pointer;" data-uri="${(d.link)!''}">
			<td>${(d.title)}</td>
			<td width="70">${d.senderName}</td>
			<td width="160">${d.sendTime?string('MM-dd HH:mm')}</td>
		</tr>
		</#list>
</table>
<script type="text/javascript">
	$(function() {
		$('.index-todo-list').delegate('tr', 'click', function(event) {
			var $this = $(this);

			var uri = $this.data('uri');

			if (!uri)
				return;

			window.open(uri, '_blank');
		});
	});
</script>

<script src="${request.contextPath}/js/suredy-list.js"></script>
<script type="text/javascript">
	$(function() {$('.my-list').list(
		{
		footer : false,search:false,
		btns : [ {
				text : '新建工作单',
				icon : 'icon-user',
				style : 'btn-default',
				click : function(page, pageSize, key) {
					window.open('${request.contextPath}/workflow/new.do');
				}
			},{
				text : '新建发文',
				icon : 'icon-user',
				style : 'btn-default',
				click : function(page, pageSize, key) {
					window.open('${request.contextPath}/dism/newDis/402881495af02baf015af03172710007');
				}
			},{
				text : '新建收文',
				icon : 'icon-user',
				style : 'btn-default',
				click : function(page, pageSize, key) {
					window.open('${request.contextPath}/incdism/newIncDis.do');
				}
			}]
		});
	});
</script>
<#else>
<li class="list-group-item">
	<i class="icon-info-sign text-danger"></i>
	暂无待办
</li>
</#if>
