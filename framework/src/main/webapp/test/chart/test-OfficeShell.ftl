
<div class="row margin-0">
	<input type="button" value="OfficeShell.DocScan sync" onclick="OfficeShell_DocScan_Sync();" />
</div>

<script language="javascript" type="text/javascript">
	var	OfficeShell	= new ActiveXObject( "eCooeOfficeShell.OfficeShell" );
	function MakeCBRet(code, msg) {
	    return "<code>"+code+"</code>"+"<msg>"+msg+"</msg>";
	}
	OfficeShell.EventCB = function (ps) {
	    eval("var psJson  = " + ps);
	    if (psJson.eType == "__CB_ETYPE_BEFORECLOSE__") {
	        return MakeCBRet(1, "can close");
	        return MakeCBRet(0, "cancel close");
	    } else if (psJson.eType == "__CB_ETYPE_CLOSED__") {
	        alert(ps);
	    } else if (psJson.eType == "__CB_ETYPE_RETURN__") {
	        alert(ps);
	    }
	    return MakeCBRet(-1, "unknown error");
	}
	function OfficeShell_DocScan_Sync() {
	alert("---");
	    var FilePath    = "C:\\Users\\dskj\\Desktop\\20160503140630.JPG";
		var JsonRet  = 
		OfficeShell.DocScan(
			"1111",                 //文件ID，用于做缓存，这里可以直接一个任意值。
			FilePath,               //当需要打开文件时，当此文件存在时，会直接被打开
			"【正文】这是一份测试", //打开后程序界面上标题栏显示的名称
			"",                     //置空
			false,                  //是否为模态打开，此处填false.
			"",                     //打开用户者的名字，填空
			true,                   //被打开的图片是否可以编辑.		
			""                      //扩展参数，置空
		);

	    eval("var r = " + JsonRet);
	    OkFile   = r.ret.SourcePath;  // get doc id
	    alert(JsonRet);
	    alert("得到新文件："+OkFile);
	}
 
    </script>