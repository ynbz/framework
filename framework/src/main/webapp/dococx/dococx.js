
//保存
function Save() {
	document.getElementById("PageOfficeCtrl1").WebSave();
}

//加盖印章
function InsertSeal() {
      try {
        document.getElementById("PageOfficeCtrl1").ZoomSeal.AddSeal();
    }
    catch (e) { }
}

//签字
function AddHandSign() {
    document.getElementById("PageOfficeCtrl1").ZoomSeal.AddHandSign();
}

//验证印章
function VerifySeal() {
    document.getElementById("PageOfficeCtrl1").ZoomSeal.VerifySeal();
}

//修改密码
function ChangePsw() {
    document.getElementById("PageOfficeCtrl1").ZoomSeal.ShowSettingsBox();
}

//只读
function AfterDocumentReadOnly() {
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, false); //禁止另存
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(5, false); //禁止打印
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(6, false); //禁止页面设置
    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(8, false); //禁止打印预览
}

//修改痕迹只读
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
	document.getElementById("form1").action = "${request.contextPath}/dococx/taohong?mb=" + mb+"&id=${id}";
	document.forms[0].submit();
}

//另存到本地
function ShowDialog1() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(2);
}

//页面设置
function ShowDialog2() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(5);
}

//打印
function ShowDialog3() {
    document.getElementById("PageOfficeCtrl1").ShowDialog(4);
}

//全屏/还原
function IsFullScreen() {
    document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
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



