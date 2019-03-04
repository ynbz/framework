<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  
<div class="row" >
	<@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
	
</div>

<script type="text/javascript">
    function AfterDocumentOpened() {
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, false); //禁止另存
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(5, false); //禁止打印
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(6, false); //禁止页面设置
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(8, false); //禁止打印预览
    }
</script>