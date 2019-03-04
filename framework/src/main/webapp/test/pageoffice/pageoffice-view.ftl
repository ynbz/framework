 <style type="text/css">
     #nav{
         width:300px; 
         height: 50px;
         border: 2px solid #477ccc; 
         position:fixed;right:0;top:15%

         }
        .style1
        {
            height: 111px;
        }
        html{/* IE6中防止抖动 */ 
            _background-image: url(about:blank); 
            _background-attachment: fixed; 
        } 
        #menubar{ 
            position:fixed;/*非IE6浏览器*/ 
            bottom:78px;  
            width:150px; 
            z-index:999; 
            height:175px; 
            border-left:solid 1px #ccc;
            _position: absolute;/*兼容IE6浏览器*/ 
            _top: expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-19)); 
        }
        #menubar ul
        {
            margin-left:13px;  font-weight:bold; color:#999;
            }
        #menubar ul li
        {
            font-size:14px;
            }
        
        .maodian
        {
            z-index:10; 
        }
        .off
        {
            color:#999; list-style-type:none;
        }
        .on
        {
            color:#333; list-style-type:square;
        }
        
    </style>
    
    <div class="row" style="margin-top: 100px;"align="center"> 
	    <div class="btn btn-info btn-sm update-btn" style="margin-top: -2px; margin-left: 10px" ><i class="icon-edit"></i> <a href="${request.contextPath}/pageoffice/openwindow?url=simple/openword&style=left=380px;top=140px;width=1400px;height=800px;" style="color: white;">普通修改</a></div>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <div class="btn btn-info btn-sm update-btn" style="margin-top: -2px; margin-left: 10px" ><i class="icon-edit"></i> <a href="${request.contextPath}/pageoffice/openwindow?url=readOnly/openword&style=left=380px;top=140px;width=1400px;height=800px;" style="color: white;">只读</a></div>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <div class="btn btn-info btn-sm update-btn" style="margin-top: -2px; margin-left: 10px" ><i class="icon-edit"></i> <a href="${request.contextPath}/pageoffice/openwindow?url=taohong/openword&style=left=380px;top=140px;width=1400px;height=800px;" style="color: white;">套红</a></div>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <div class="btn btn-info btn-sm update-btn" style="margin-top: -2px; margin-left: 10px" ><i class="icon-edit"></i> <a href="${request.contextPath}/pageoffice/openwindow?url=insertSeal/openword&style=left=380px;top=140px;width=1400px;height=800px;" style="color: white;">电子盖章</a></div>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <div class="btn btn-info btn-sm update-btn" style="margin-top: -2px; margin-left: 10px" ><i class="icon-edit"></i> <a href="${request.contextPath}/pageoffice/openwindow?url=revisionsList/openword&style=left=380px;top=140px;width=1400px;height=800px;" style="color: white;">修改痕迹</a></div>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    
    </div>

<script type="text/javascript">
	window.onload = function(){ 
		
		if(!PO_checkPageOffice()){
			var poCheck = document.getElementById("po_check"); // 显示信息，提示客户安装PageOffice
			poCheck.innerHTML = "<span style='font-size:12px;color:red;'>请先安装<a href=\"posetup.exe\" style=\"border:solid 1px #0473b3; color:#0473b3; padding:1px;\">PageOffice客户端</a></span>";

		}

	}
</script>