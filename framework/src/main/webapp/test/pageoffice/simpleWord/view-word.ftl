<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  
<div class="row" >
	<@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
	
</div>

<script type="text/javascript">
function Save() {
	document.getElementById("PageOfficeCtrl1").WebSave();
}
</script>