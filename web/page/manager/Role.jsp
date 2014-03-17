<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
    <style>
        .icon-add{
            background:url('img/edit_add.png') no-repeat;
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
           alert('对<'+b+'>进行授权');
        }
    </script>
</head>
<body>
<div style="width:1024px;height:100%;margin: 0px auto;">
<table id="rolegrid" class="easyui-datagrid"  style="width:1024px;height:500px"
       data-options="rownumbers:true,singleSelect:true,url:'lr.do?model=manager.Role&eventName=queryRole',method:'get',toolbar:'#tb',fitColumns:true">
    <thead>
    <tr>
        <%--<th data-options="field:'userid',width:80">用户ID</th>
        <th data-options="field:'passwd',width:100">密码</th>
        <th data-options="field:'loginname',width:80,align:'right'">登录名</th>
        <th data-options="field:'dept',width:80,align:'right'">部门</th>
        <th data-options="field:'description',width:240">描述</th>
        <th data-options="field:'useful',width:60,align:'center'">有效</th>
        <th data-options="field:'regionid',width:60,align:'center'">区域代码</th>
        <th data-options="field:'username',width:60,align:'center'">用户名称</th>
        <th data-options="field:'createdate',width:60,align:'center'">创建时间</th>--%>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  plain="true"
           onclick="$('#w').window('open')">add</a>

        <input class="easyui-datebox" style="width:80px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
    </div>
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