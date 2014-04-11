<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html >
<html>
<head>
    <meta charset="utf-8">
    <title>测试</title>
    <style>
        form {
            margin: 0;
        }
        textarea {
            display: block;
        }
    </style>
   <%-- <link rel="stylesheet" href="http://localhost/kindeditor/themes/default/default.css">
    <script charset="utf-8" src="http://localhost/kindeditor/kindeditor-min.js"></script>
    <script charset="utf-8" src="http://localhost/kindeditor/lang/zh_CN.js"></script>--%>

</head>
<body>
<form>
<table class="formtable" style="width: 900px">

    <tr>

        <td class="formtdtext">民族</td>
        <td>
            <input class="easyui-combobox"
                   name="culture"
                   style="width: 100px"
                   data-options=" valueField:'id', textField:'text', data:cj.getEnum('culture') ">

        </td>
        <td>
            <input class="easyui-combobox easyui-validatebox"
                   name="nation"
                   style="width: 100px"
                   data-options=" valueField:'id', textField:'text', data:cj.getEnum('nation'), editable:false">
        </td>
    </tr>
</table>
</form>
<button>load</button>
<script>

var obj={
    gender:'1',nation:'02',culture:'2'
}
$('button').bind('click',function(){
        $('form').form('load',obj)
})

</script>