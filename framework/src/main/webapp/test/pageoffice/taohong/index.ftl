
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">套红列表</h3>
	</div>
	<div class="panel-body">
		<table class="table table-bordered " >
			<thead>
				<tr onmouseover="onColor(this);" onmouseout="offColor(this);">		
					<th width="20%" style="text-align:center;">
						文档名称
					</th>
					<th width="20%" style="text-align:center;">
						创建日期
					</th>
					<th width="60%" style="text-align:center;">
						操作
					</th>
					
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="text-align:center;">测试文件</td>
					<td style="text-align:center;">2017-01-05</td>
					<td style="text-align:center;">
						<a href="${request.contextPath}/pageoffice/taohong/edit"><span style=" color:Green;">编辑</span></a>&nbsp;&nbsp;&nbsp;
						<a href="${request.contextPath}/pageoffice/taohong/taohong"><span style=" color:Green;">套红</span></a>&nbsp;&nbsp;&nbsp;
						<a href="${request.contextPath}/pageoffice/taohong/readOnly"><span style=" color:Green;">正式发文</span></a>
					</td>
				</tr>
			</tbody>		
		</table>
	</div>
	<div class="panel-footer">
	</div>
</div>

<script type="text/javascript">
	function onColor(dd){
		dd.style.backgroundColor = "#D7FFEE";
	}
	function offColor(dd){
		dd.style.backgroundColor="";
	}
													
</script>