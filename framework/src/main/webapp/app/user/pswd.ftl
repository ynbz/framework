<#if !data??> 无法修改用户信息 <#else>
<form id="user-config-form">
	<div class="form-group">
		<div class="input-group">
			<div class="input-group-addon" style="background-color: #d9534f; color: white;">
				<div style="width: 69px;">原始密码</div>
			</div>
			<input type="password" class="form-control" name="old" />
		</div>
	</div>
	<div class="form-group">
		<div class="input-group">
			<div class="input-group-addon" style="background-color: #5cb85c; color: white;">
				<div style="width: 69px;">新的密码</div>
			</div>
			<input type="password" class="form-control" name="pswd" />
		</div>
	</div>
	<div class="form-group">
		<div class="input-group">
			<div class="input-group-addon" style="background-color: #5cb85c; color: white;">
				<div style="width: 69px;">确认新密码</div>
			</div>
			<input type="password" class="form-control" id="repeat" />
		</div>
	</div>
</form>
<hr>
<div class="row">
	<div class="col-md-12 text-right">
		<div class="btn btn-primary pswd-submit" style="margin-right: 10px;">确定</div>
		<div class="btn btn-danger" onclick="Suredy.Modal.closeModal();">取消</div>
	</div>
</div>
<!-- <script src="${request.contextPath}/core/js/jquery.form.js"></script> -->
<script type="text/javascript">
	require([ 'suredy', 'suredyModal', 'jqueryForm' ], function() {
		$('.btn.pswd-submit').on('click', function(event) {
			var $this = $(this);

			if ($this.hasClass('disabled'))
				return false;

			var old = $('input[name=old]').val();
			var pswd = $('input[name=pswd]').val();
			var repeat = $('input#repeat').val();

			if (!old || !pswd || !repeat) {
				alert('请正确填写密码信息!');
				return false;
			}

			if (pswd !== repeat) {
				alert('两次填写的[新密码]不一致!');
				return false;
			}

			$this.addClass('disabled');

			var form = $('form#user-config-form');

			form.ajaxSubmit({
				url : '${request.contextPath}/user-config',
				type : 'post',
				success : function(data, textStatus, jqXHR) {
					if (!data) {
						alert('密码修改失败！\n\n请重新确认原始密码是否正确');
						$this.removeClass('disabled');
						return false;
					}

					Suredy.Modal.closeModal();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('密码修改失败！\n' + errorThrown);
					$this.removeClass('disabled');
				}
			});

			return false;
		});
	});
</script>
</#if>
