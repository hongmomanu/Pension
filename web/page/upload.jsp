<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<link rel="stylesheet" type="text/css"  href="js/jqueryplugin/upload/uploadify.css">
<script src="js/jqueryplugin/jquery.uploadify.min.js"></script>

<div style="width:100%;height: 80%;">
    <input type="file" name="file_upload" id="file_upload" />
</div>
<div style=";width:100%;height: 10%;background-color: beige;">
    <ul style="list-style:none;">
        <li style="list-style: none;float:right;"><a href="javascript:$('#file_upload').uploadify('upload')">上传</a></li>
        <li style="list-style: none;float:right;"><a href="#" id="sc2">上传2</a></li>
    </ul>
</div>

<script type="text/javascript">
    $(function () {
        $("#file_upload").uploadify({
            buttonClass:'some-class',buttonText:'BROWSE...',
            auto: false,
            height: 30,
            swf: 'js/jqueryplugin/upload/uploadify.swf',
            uploader: 'upload.do',
            onUploadSuccess: function (file, data, response) {

            }
        });
        $("#sc2").bind('click',function(){
            alert(cj.getSelected())
        })
    });



</script>