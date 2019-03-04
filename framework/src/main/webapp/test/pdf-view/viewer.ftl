<div class="row" style="overflow-x: auto; padding-top: 5px;">
	<div class="col-md-12 col-sm-12">
		<table class="file-list" data-page="1" data-page-size="20" data-count="10" style="width: 100%;">
			<tr class="title-row">
				<th>文件名称</th>
				<th>文件类型</th>
				<th>文件路径</th>
			</tr>
			<tr>
				<td><a href='${request.contextPath}/test/pdf-view/viewer.html?file=./test1.pdf' target="_blank">test1.pdf</a></td>
				<td><i class="icon-file"></i> PDF</td>
				<td>${request.contextPath}/test/pdf-vew/test1.pdf</td>
			</tr>
			<tr>
				<td><a href='${request.contextPath}/test/pdf-view/viewer.html?file=./test2.pdf' target="_blank">test2.pdf</a></td>
				<td><i class="icon-file"></i> PDF</td>
				<td>${request.contextPath}/test/pdf-vew/test2.pdf</td>
			</tr>			
		</table>
	</div>
</div>

<script type="text/javascript">
	var listConfig;
	var tempTree;
	var tempList;
	require([ 'suredyList'],function(List, Tree, Modal) {
		listConfig = ({
			header : false,
			footer : true,
			search : false,
			checkbox : false
		});
		List('.file-list', listConfig);

	});
	
	function getFileView(fileUrl){	
		alert(fileUrl);	
	};
</script>
