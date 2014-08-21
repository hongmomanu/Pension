<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
    <link rel="stylesheet" type="text/css"  href="js/commonfuncs/ext/popwin.css">
    <script type="text/javascript"  src="js/commonfuncs/ext/popwin.js"></script>
    <style>
        .icon-add{
            background:url('../../img/application_form_add.png') no-repeat;
        }.icon-save{
             background:url('img/head/3.png') no-repeat;
        }.icon-remove{
              background:url('img/del.gif') no-repeat;
        }

        .formtable {
            width:100%;
            padding: 0px;
            margin: 0px;
            font-family:Arial, Tahoma, Verdana, Sans-Serif;
            border-left:1px solid #ADD8E6;
            border-top: 1px solid #ADD8E6;
            border-collapse:collapse;
        }



            /*单元格样式。*/
        .formtable td {
            border-right: 1px solid #ADD8E6;
            border-bottom: 1px solid #ADD8E6;

            /*background: #fff;*/

            font-size:12px;
            padding: 3px 3px 3px 6px;
            color: #303030;
            word-break:break-all;
            word-wrap:break-word;
            white-space:normal;
        }
            /*表头样式*/
        .formtable th {
            font-size:12px;
            font-weight:600;
            color: #303030;
            border-right: 1px solid #ADD8E6;
            border-bottom: 1px solid #ADD8E6;
            border-top: 1px solid #ADD8E6;
            letter-spacing: 2px;
            text-align: left;
            padding: 10px 0px 10px 0px;
            background: url(img/tablehdbg.png);
            white-space:nowrap;
            text-align:center;
            overflow: hidden;
        }
        .formtdtext {
            width: 13%;
        }
    </style>
    <script>
        function formatPrice(val,row){
            return '<a href="javascript:void(0)" ' +
                    'onclick="f(\''+row.roleid+'\',\''+row.rolename+'\');"><span style="color:red;">授权</span><a>';
        }
        function f(a,b){
            $.webox({
                height:480,
                width:400,
                bgvisibel:true,
                title:'对角色<<span style="color: green;">'+b+'</span>>进行授权',
                iframe:'lr.do?model=manager.Grant&roleid='+a
            });
        }
    </script>
</head>
<body>
<table id="rolegrid" class="easyui-datagrid"
       data-options="rownumbers:true,singleSelect:true,url:'lr.do?model=manager.Role&eventName=queryRole',
       fit:true, pagination:true, pageSize:10,border:false,
       method:'get',toolbar:'#tb',fitColumns:true">
    <thead>
    <tr>
            <th data-options="field:'rolename',width:60,align:'center'">角色名</th>
            <th data-options="field:'roleid',width:60,align:'center'">角色id</th>
            <th data-options="field:'roledesc',width:60,align:'center'">描述</th>
        <%--<th data-options="field:'parent',width:60,align:'center'">父类角色</th>--%>
            <th data-options="field:'grant',width:60,align:'center',formatter:formatPrice">授权</th>
        <th data-options="field:'status',width:60,align:'center'">状态</th>
        <th data-options="field:'createdate',width:60,align:'center'">创建时间</th>
    </tr>
    </thead>
</table>
<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"<%--  plain="true"--%>
           onclick="$('#w').window('open')">增加</a>

        <input class="easyui-datebox" style="width:80px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </div>
</div>

<div id="w"   class="easyui-window"
     data-options="iconCls:'icon-save',closed:true" style="width:500px;height:200px;padding:10px;">
    <form id="form" method="post">
        <fieldset ><legend>角色</legend>
            <div>
                <table class="formtable">

                    <tr>
                        <td class="formtdtext">角色id:</td>
                        <td><input type="text" name="roleid"></td>
                        <td class="formtdtext">描述:</td>
                        <td><input type="text" name="roledesc"></td>
                    </tr>

                    <tr>
                        <td class="formtdtext">状态:</td>
                        <td><input type="text" name="status"></td>
                        <td class="formtdtext">角色名:</td>
                        <td><input type="text" name="rolename"></td>
                    </tr>

                </table>
            </div>

        </fieldset>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" id="saveRole" class="easyui-linkbutton">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">取消</a>
        </div>
    </form>
</div>




</body>
</html>
<script>
$(function(){
    $('#saveRole').bind('click',function(){
        $('#form').form('submit', {
            url:'lr.do?model=manager.Role&eventName=saveRole',
            onSubmit: function(){
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');	// hide progress bar while the form is invalid
                }
                return isValid;	// return false will stop the form submission
            },
            success: function(res){
                $('#w').window('close');
                $('#rolegrid').datagrid('reload');
                $.messager.progress('close');	// hide progress bar while submit successfully
            }
        });
    });

    function clearForm(){
        $('#form').form('clear');
    }

})
</script>