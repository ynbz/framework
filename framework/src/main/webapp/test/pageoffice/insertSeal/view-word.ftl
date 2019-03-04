<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  


<div class="row">
	 <span style="color: red;">操作说明：</span>点“插入印章”按钮即可，插入印章时的用户名为：李志，密码默认为：111111。<br />
        关键代码：点右键，选择“查看源文件”，看js函数<span style="background-color: Yellow;">InsertSeal()</span>
        <p>点击 <a href="${request.contextPath}/adminseal.do">电子印章简易管理平台</a> 添加、删除印章。管理员:admin 密码:111111</p>
</div>

<div class="row" >
	<@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
</div>

<script type="text/javascript">
    function InsertSeal() {
        try {
            document.getElementById("PageOfficeCtrl1").ZoomSeal.AddSeal();
        }
        catch (e) { }
    }
    function AddHandSign() {
        document.getElementById("PageOfficeCtrl1").ZoomSeal.AddHandSign();
    }
    function VerifySeal() {
        document.getElementById("PageOfficeCtrl1").ZoomSeal.VerifySeal();
    }
    function ChangePsw() {
        document.getElementById("PageOfficeCtrl1").ZoomSeal.ShowSettingsBox();
    }
</script>