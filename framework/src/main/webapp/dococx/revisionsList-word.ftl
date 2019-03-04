<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  


<div class="row">
	 <div  style=" width:1350px; height:800px;">
        <div id="Div_Comments" style=" float:left; width:200px; height:700px; border:solid 1px #6666;">
        <h3>痕迹列表</h3>
        <input type="button" name="refresh" value="刷新"onclick=" return refresh_click()"/>
        <ul id="ul_Comments">
            
        </ul>
        </div>
       <div style="loat:right;">
        <@po.PageOfficeCtrl id="PageOfficeCtrl1" /> 
      </div>
	</div>
</div>

<script type="text/javascript">
</script>