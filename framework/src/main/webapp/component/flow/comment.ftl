<link rel="stylesheet" href="${request.contextPath}/component/flow/css/flowComponent.css">
<div class="row panal-row">
	<div class="col-md-4 panal-col">
		<div class="title"><i class='icon-reorder'></i>常用意见</div>
		<div style="overflow: auto; height: 280px; padding-top: 1px;">
			<table class="table table-bordered table-hover table-striped table-condensed">
				<tbody id="userComment"></tbody>
			</table>
		</div>
	</div>

	<div class="col-md-8 input-group  panal-col">
		<div class="title"><i class='icon-reorder'></i>意见内容</div>
		<div style="overflow: auto; height: 280px;">
			<textarea rows="14" id="commentValue" class="form-control" style="border:0px;"></textarea>
		</div>
	</div>
</div>
<script>

$(function() {
	$('#userComment').append("<tr><td onclick='addToComment()'>同意。</td></tr>");
	$('#userComment').append("<tr><td onclick='addToComment()'>阅。</td></tr>");
	$('#userComment').append("<tr><td onclick='addToComment()'>已阅。</td></tr>");
	$('#userComment').append("<tr><td onclick='addToComment()'>请送</td></tr>");	
	$('#commentValue').val(hisComment);
});
function addToComment(){
	$('#commentValue').val($('#commentValue').val()+event.srcElement.innerText);
}
</script>