<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>文件上传页面</title>
    <style>
        form {
            margin: 0;
        }
        textarea {
            display: block;
        }
    </style>

</head>

<body>
<h2>
    使用commons-fileupload上传文件
</h2>
<hr />
<!-- 文件上传的表单的属性必设值：method="post" enctype="multipart/form-data" -->
<form action="upload.do" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>
                请选择要上传的文件
            </td>
            <td>
                <input type="file" name="filePath" size="50" />
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value=" 上传 " />
                <input type="button" value=" 上传2 " />
            </td>
        </tr>
    </table>
</form>
<br />
<script type="text/javascript">
    $(':input[type=button]').bind('click',function(){
        alert(parent.cj.version);
    })
</script>