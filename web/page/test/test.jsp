<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<link rel="stylesheet" type="text/css"  href="js/jqueryplugin/upload/uploadify.css">
<script src="js/jqueryplugin/jquery.uploadify.min.js"></script>

<div style="width: 300px;height: 100px;background-color: red;">
<input type="file" name="file_upload" id="file_upload" />
</div>
<p><a href="javascript:$('#file_upload').uploadify('upload')">上传</a>
    <a href="javascript:alert('关闭')">关闭</a></p>
    <a id="testalert">测试easyui alert</a></p>

<script type="text/javascript">
    $(function () {
        $("#file_upload").uploadify({
            buttonClass:'some-class',buttonText:'BROWSE...',
            auto: false,
            height: 30,
            swf: 'js/jqueryplugin/upload/uploadify.swf',
            uploader: 'upload.do',
            width: 120,
            onUploadSuccess: function (file, data, response) {
                console.log(data)
            }
        });

        $('#testalert').bind('click',function(){
            parent.require(['text!http://localhost:8080/jsondata/testeasyuialert.json'],function(res){
               var d=eval('('+res+')');
               $.messager.alert(cj.defaultTitle, d.message, 'info');
            })
        })
    });



</script>