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
<table class="formtable" style="width: 900px" method="post">

    <tr>

        <td class="formtdtext">民族</td>

        <td>
            <input class="easyui-combobox" opt="nation"
                   name="nation"
                   style="width: 100px"
                   data-options="required:true, valueField:'id', textField:'text', data:cj.getEnum('nation'), editable:false">
        </td>
    </tr>
</table>
</form>
<button id="b1">load</button>
<button id="b2">set</button>
<button id="b3">submit</button>
<script>

var obj={
    gender:'1',nation:'02',culture:'2'
}
$('#b1').bind('click',function(){
        $('form').form('load',obj)
})
$('#b2').bind('click',function(){
    console.log($(':input[name=nation]'))
    $(':input[opt=nation]').combobox('setValue', '01') ;
})
$('#b3').bind('click',function(){
    $('form').form('submit',{
        url:'main',
        onSubmit: function(){
            var isValid = $(this).form('validate');
            if (!isValid){
               alert(1)
            }
            //$(this).form('disableValidation')
            return isValid;	// return false will stop the form submission
        }
    })
})
</script>