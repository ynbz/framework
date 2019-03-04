<#assign po=JspTaglibs["http://java.pageoffice.cn"]>  


<div class="row">
	 <div  style=" width:1350px; height:750px;">
        <div id="Div_Comments" style=" float:left; width:200px; height:700px; border:solid 1px red;">
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

    function Save() {
        document.getElementById("PageOfficeCtrl1").WebSave();
    }
    
    function AfterDocumentOpened() {
        refreshList();
    }
    
      //获取当前痕迹列表
     function refreshList() {
        var i;
        document.getElementById("ul_Comments").innerHTML = "";
        for (i = 1; i <= document.getElementById("PageOfficeCtrl1").Document.Revisions.Count; i++) {
            var str = "";
            str = str + document.getElementById("PageOfficeCtrl1").Document.Revisions.Item(i).Author;
            var  revisionDate=document.getElementById("PageOfficeCtrl1").Document.Revisions.Item(i).Date;
             //转换为标准时间
             str=str+" "+dateFormat(revisionDate,"yyyy-MM-dd HH:mm:ss");
              
            if (document.getElementById("PageOfficeCtrl1").Document.Revisions.Item(i).Type == "1") {
                str = str + ' 插入：' + document.getElementById("PageOfficeCtrl1").Document.Revisions.Item(i).Range.Text;
            }
            else if (document.getElementById("PageOfficeCtrl1").Document.Revisions.Item(i).Type == "2") {
                str = str + ' 删除：' + document.getElementById("PageOfficeCtrl1").Document.Revisions.Item(i).Range.Text;
            }
            else {
                str = str + ' 调整格式或样式。';
            }
            document.getElementById("ul_Comments").innerHTML += "<li><a href='#' onclick='goToRevision(" + i + ")'>" + str + "</a></li>"
        }

    }
     //GMT时间格式转换为CST
      dateFormat = function (date, format) {
        date = new Date(date); 
        var o = {
            'M+' : date.getMonth() + 1, //month
            'd+' : date.getDate(), //day
            'H+' : date.getHours(), //hour
            'm+' : date.getMinutes(), //minute
            's+' : date.getSeconds(), //second
            'q+' : Math.floor((date.getMonth() + 3) / 3), //quarter
            'S' : date.getMilliseconds() //millisecond
        };

        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));

        for (var k in o)
            if (new RegExp('(' + k + ')').test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));

        return format;
    }

    //定位到当前痕迹
    function goToRevision(index)
    {
     var sMac = "Sub myfunc() " + "\r\n"
                 + "ActiveDocument.Revisions.Item("+index+").Range.Select " + "\r\n"
                 + "End Sub ";

     document.getElementById("PageOfficeCtrl1").RunMacro("myfunc", sMac);
     
    }

    //刷新列表
   function refresh_click()
    {
    refreshList();    
    }


</script>