<style>
<!--
pre {
	border: 1px solid #ddd;
	border-radius: 0;
	margin-top: -1px;
	background-color: #fff;
}
-->
</style>
<div>
	<!-- Nav tabs -->
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active">
			<a href="#p-web-xml" aria-controls="home" role="tab" data-toggle="tab">web.xml</a>
		</li>
		<li role="presentation">
			<a href="#p-springMVC-servlet-xml" aria-controls="profile" role="tab" data-toggle="tab">springMVC-servlet.xml</a>
		</li>
		<li role="presentation">
			<a href="#p-jdbc-xml" aria-controls="messages" role="tab" data-toggle="tab">jdbc.xml</a>
		</li>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="p-web-xml">
			<pre id="web-xml">loading......</pre>
		</div>
		<div role="tabpanel" class="tab-pane" id="p-springMVC-servlet-xml">
			<pre id="springMVC-servlet-xml">loading......</pre>
		</div>
		<div role="tabpanel" class="tab-pane" id="p-jdbc-xml">
			<pre id="jdbc-xml">loading......</pre>
		</div>
	</div>

</div>

<script type="text/javascript">
	require([ 'suredy' ], function() {
		$.ajax({
			url : '${request.contextPath}/test/guide/web-xml',
			method : 'GET',
			success : function(xml) {
				$('#web-xml').text(xml);
			}
		});
		$.ajax({
			url : '${request.contextPath}/test/guide/springMVC-servlet-xml',
			method : 'GET',
			success : function(xml) {
				$('#springMVC-servlet-xml').text(xml);
			}
		});
		$.ajax({
			url : '${request.contextPath}/test/guide/jdbc-xml',
			method : 'GET',
			success : function(xml) {
				$('#jdbc-xml').text(xml);
			}
		});
	});
</script>