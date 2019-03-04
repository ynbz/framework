
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">公告管理</h3>
		</div>
		<div class="panel-body" id="noticeList">
			<table class="notice-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th>标题</th>
					<th>公告类型</th>
					<th>发布人</th>
					<th>发布部门</th>
					<th>发布时间</th>
					<th>有效时间</th>
				</tr>
				<#if data??> <#list data as notice>
				<tr data-id="${notice.id}">
					<td><a href='javascript:void(0)'onclick="noticeView('${notice.id}')">${notice.title}</a></td>
					<td>${(notice.type==1)?string('公司公告',(notice.type==2)?string('部门公告',''))}</td>
					<td>${notice.issuer}</td>
					<td>${notice.issueUnit}</td>
					<td>${notice.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>			
					<td>${notice.validDate?string("yyyy-MM-dd HH:mm:ss")}</td>				
				</tr>
				</#list> </#if>				
			</table>
		</div>
		<div class="panel-footer">
		</div>
	</div>


<script type="text/javascript">
require(['suredyList', 'suredyModal'], function(List, Modal) {
	var listConfig = ({
		header : true,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size) {
			var url='${request.contextPath}/notice/notice-list.do?page=' + page + '&size=' + size;
			$.get(url, function(html, textStatus, jqXHR) {
				var $html = $(html);
				$("#noticeList").html($("#noticeList", $html).html());
				List('.notice-list', listConfig);
			}, 'html');
		},
		
		btns : [ {
			text :'新增',
			icon : 'icon-plus',
			style : 'btn-info',
			click : function() {
				Modal.showModal({
					size : 'lg',
					icon : 'icon-plus',
					title : '新增公告',
					showFoot : false,
					uri : '${request.contextPath}/notice/notice-info'
				});
			}
		}, {
			text :'修改',
			icon : 'icon-edit',
			style : 'btn-danger',
			click : function() {
				var checked = List.checked($('.notice-list'));
				if (checked.length == 0 ) {
					alert('请选择需要修改的公告!');
					return;
				} else if (checked.length > 1) {
					alert('只能修改一个公告!');
					return;
				} else {
					var id = checked.eq(0).data('id');
					var uri = '${request.contextPath}/notice/notice-info?id=' + id;
					Modal.showModal({
						size : 'lg',
						icon : 'icon-edit',
						title : '修改公告',
						showFoot : false,
						uri : uri
					});
				}
			}
		},  {
			text : '删除',
			icon : 'icon-remove',
			style : 'btn-danger',
			click : function() {
				var checked = List.checked($('.notice-list'));
				if (checked.length == 0 ) {
					alert('请选择需要删除的公告!');
					return;
				} else {
					//TODO: 
					var ids='';
					for(var i = 0;i<checked.length;i++){
						ids =ids+checked.eq(i).data('id')+",";
					}
					ids = ids.substring(0, ids.length-1);//去掉最后的逗号
					var uri = '${request.contextPath}/notice/notice-delete?ids=' + ids;
					var msg = '是否确认删除公告？提示：请谨慎操作！';
			
					if (!window.confirm(msg)) {
						return;
					}
					$.ajax({
						url : uri,
						type : 'POST',
						success : function(msg) {
							if (!msg) {
								alert('Unknown exception!');
							} else if (!msg.success) {
								alert("删除文件失败！\n\n" + msg.msg);
							} else {
								var ids = (msg.msg).split(',');
								for ( var i = 0;i<ids.length;i++){
									$.ajax({
										url : '${request.contextPath}/file/remove?fileId='+ids[i],
										type : 'POST',
										success : function(msg) {
											
										},
										error : function(a, b, c) {
											alert('Server error! ' + b);
										}
									});
								}
								alert("删除文件成功！");
								Suredy.loadContent('${request.contextPath}/notice/notice-list');
							}
						},
						error : function(a, b, c) {
							alert('Server error! ' + b);
						}
					});

				} //end else
			} // end click function
		} ]
	});	
	List('.notice-list', listConfig);
});
function noticeView(id){	
	window.open('${request.contextPath}/notice/notice-view?id='+id)
};	
</script>	