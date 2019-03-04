<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  


<div class="row">
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
	</form>
</div>

<div class="row" >
	<@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
</div>

<script type="text/javascript">
</script>