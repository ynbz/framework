<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  


<div class="row">
	<a href="${request.contextPath}/pageoffice/taohong/openword">文件列表</a>
	<span style="width: 100px;"> </span><strong>文档主题：</strong><span style="color: Red;">测试文件</span>
</div>

<div class="row" >
	<@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
</div>

<script type="text/javascript">
   function Save() {
       document.getElementById("PageOfficeCtrl1").WebSave();
   }

   //领导圈阅签字
   function StartHandDraw() {
       document.getElementById("PageOfficeCtrl1").HandDraw.SetPenWidth(5);
       document.getElementById("PageOfficeCtrl1").HandDraw.Start();
   }

   //分层显示手写批注
   function ShowHandDrawDispBar() {
       document.getElementById("PageOfficeCtrl1").HandDraw.ShowLayerBar(); ;
   }

   //全屏/还原
   function IsFullScreen() {
       document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
   }

</script>