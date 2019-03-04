
<div class="row">
	<div class="col-md-6">
		<form class="qrcode-data">
			<div class="form-group">
				<label>文本内容</label>
				<textarea class="form-control" rows="5" name="content"></textarea>
			</div>
			<div class="form-group">
				<label>容错级别</label>
				<select class="form-control" name="level">
					<option value="L">L（7%）</option>
					<option value="M">M（15%）</option>
					<option value="Q">Q（25%）</option>
					<option value="H">H（30%）</option>
				</select>
			</div>
			<div class="form-group">
				<label>外边距</label>
				<input type="number" class="form-control" name="margin" value="0" placeholder="只能是数字" />
			</div>
			<div class="form-group">
				<label>二维码高度</label>
				<input type="number" class="form-control" name="width" value="250" placeholder="只能是数字" />
			</div>
			<div class="form-group">
				<label>前景色</label>
				<input type="color" class="" name="front" value="#000000" />
				<span class="show-color bg-info">#000000</span>
			</div>
			<div class="form-group">
				<label>背景色</label>
				<input type="color" class="" name="background" value="#FFFFFF" />
				<span class="show-color bg-info">#FFFFFF</span>
			</div>
			<div class="form-group text-right">
				<div class="btn btn-danger draw">生成二维码</div>
			</div>
		</form>
	</div>
	<div class="col-md-6 bg-info" style="padding: 10px;">
		<img class="qrcode-img" src="" />
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$('[type="color"]').on('change', function() {
			var $this = $(this);
			$('~span.show-color', $this).text($this.val());
		});

		$('.draw').on('click', function() {
			var params = $('form.qrcode-data [name]');

			var src = '${request.contextPath}/test/component/img-qrcode?';

			params.each(function(i) {
				var $this = $(this);
				var key = $this.attr('name');
				var val = encodeURI($this.val().replace(/^#*/, ''));

				src += key + '=' + val + '&';
			});

			console.log(src);
			$('.qrcode-img').attr('src', src);
		});
	});
</script>