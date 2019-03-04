
<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
	<form name="checkupform" id="checkupform">
	
		<input type="hidden" id="assetId" name="assetId" value="${assetId}" />
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-lable input-group-addon">巡检状态</div>
						<select class="form-control" id="checkupStatus" name="checkupStatus">
							<option value="1">正常</option>
							<option value="2">异常</option>
							<option value="0">损坏</option>
						</select>
					</div>
				</div>	
			</div>
		</div>				
	
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group">
					<div class="input-group">
							<div class="input-group-addon">
								<span class="title-content">巡检备注</span>
							</div>
							<textarea id="remark" name="remark" class="form-control" maxlength="5000" placeholder="&lt;最多5000字&gt;" rows="5"></textarea>
					</div>
				</div>
			</div>
		</div>				
		<div class="text-center">
			<div class="btn btn-info" id="btn-save" >
				<i class="icon-save"></i> 保存
			</div>
		</div>	
	</form>	
	</div>
</div>
<script type="text/javascript">
require(['suredyModal'],function(Modal) {
	$('#btn-save').click(function() {
		$.ajax({
			url :'${request.contextPath}/EquipAssetCtrl/eqm-checkup/record',
			dataType : 'json',
			type : 'POST',
			data : $('#checkupform').serialize(),
			success : function(success) {
				if (!success) {
					$.notify({title:'提示：',message:'保存失败！'});
				} else if (!success.success) {
					$.notify({title:'提示：',message:'保存失败！\n\n' + success.msg});
				} else {
					$.notify({title:'提示：',message:'保存成功！'});
					var wopen = window.parent;
					wopen.checkupLogList(1, 25);
					Suredy.Modal.closeModal();
				}
			},
			error : function(a, b, c) {
				$.notify({title:'提示：',message:'服务器错误！'+b});
			}
		});
	});
});	
</script>	
