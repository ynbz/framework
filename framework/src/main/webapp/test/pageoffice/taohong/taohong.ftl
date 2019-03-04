<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  


<div class="row">
	<a href="${request.contextPath}/pageoffice/taohong/openword">文件列表</a>
	<span style="width: 100px;"> </span><strong>文档主题：</strong><span style="color: Red;">测试文件</span>
	<form method="post" id="form1">
		<strong>模板列表：</strong>
		<span style="color: Red;"> <select name="templateName"
				id="templateName" style='width: 240px;'>
				<option value='temp2008.doc' selected="selected">
					模板一
				</option>
				<option value='temp2009.doc'>
					模板二
				</option>
				<option value='temp2010.doc'>
					模板三
				</option>
			</select> </span>
		<span style="color: Red;"><input type="button" value="一键套红" onclick="taoHong()"/> </span>
		<span style="color: Red;"><input type="button" value="保存关闭" onclick="saveAndClose()"/> </span>
	</form>
</div>

<div class="row" >
	<@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
</div>

<script type="text/javascript">
	//初始加载模板列表
	function load() {
		if (getQueryString("mb") != null)
			document.getElementById("templateName").value = getQueryString("mb");
	}

	//获取url参数 
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		else
			return null;
	}

	//套红
	function taoHong() {
		var mb = document.getElementById("templateName").value;
		document.getElementById("form1").action = "${request.contextPath}/pageoffice/taohong/taohong?mb=" + mb;

		document.forms[0].submit();
	}

	//保存并关闭
	function saveAndClose() {
		document.getElementById("PageOfficeCtrl1").WebSave();
		location.href = "${request.contextPath}/pageoffice/taohong/openword";
	}
</script>