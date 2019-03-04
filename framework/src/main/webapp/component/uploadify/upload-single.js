jQuery(function() {
    var $ = jQuery,
        $status = $('#upload-status'),
        $fileField = $('#fileField'),
        server = 'component/uploader?storage=' + ($('#storageField').val() == '' ? '' : $('#storageField').val()),
        state = 'pending',
        uploader;
    uploader = WebUploader.create({
    	
        // 不压缩image
        resize: false,

        // swf文件路径
        swf: 'component/uploadify/Uploader.swf',

        // 文件接收服务端。
        server: server ,
        //自动上传
        auto:true,

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            id: '#selector',
            multiple:false
        },
        
        accept: {
            title: 'excel',
            extensions: 'xls,xlsx'
        }
       
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
    	$status.empty();
        $status.append( '<div id="' + file.id + '"> 等待上传...</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id );
        $li.text('上传中');
    });

    uploader.on( 'uploadSuccess', function( file, response) {
    	$fileField.val(response.data);
        $( '#'+file.id ).text('已上传');
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).text('上传出错');
    });


    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

    });
    /*
    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
    */

});
