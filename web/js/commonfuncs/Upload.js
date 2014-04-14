define(function(){
    var render=function(f){
        $("#file_upload").uploadify({
            buttonClass:'some-wclass',buttonText:'选择文件...',
            auto: false,
            height: 30,
            uploadLimit:4,
            swf: 'js/jqueryplugin/upload/uploadify.swf',
            uploader: 'upload.do',
            onUploadSuccess: function (file, data, response) {
                var d=eval('('+data+')');
                if(d.filepath){
                    $('#file_upload_message').text(d.filepath)
                }
                f(data)
            }
        });
    }

    return {show:function(f){
        require(['text!views/notepad/Upload.htm'],function(uploadhtm){
            $.webox({
                height:350,
                width:500,
                top:130,
                bgvisibel:true,
                title:'<<span style="color: green;">上传</span>>',
                html:uploadhtm
            });
            render(f);
        })
    }}
})