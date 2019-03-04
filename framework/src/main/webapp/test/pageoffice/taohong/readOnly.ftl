<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  


<div class="row">
	<a href="${request.contextPath}/pageoffice/taohong/openword">文件列表</a>
	<span style="width: 100px;"> </span><strong>文档主题：</strong><span style="color: Red;">测试文件</span>
</div>

<div class="row" >
	<@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
</div>

<script language="javascript">
	
    function ShowDialog1() {
        document.getElementById("PageOfficeCtrl1").ShowDialog(2);
    }
    function ShowDialog2() {
        document.getElementById("PageOfficeCtrl1").ShowDialog(5);
    }
    function ShowDialog3() {
        document.getElementById("PageOfficeCtrl1").ShowDialog(4);
    }

	//全屏/还原
    function IsFullScreen() {
        document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
    }
</script>