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
    </style>
</head>
<body>
<div style="width:1024px;height:100%;margin: 0px auto;">
<table class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:1024px;height:500px"
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
        <th data-options="field:'roleid',width:60,align:'center'">角色id</th>
        <th data-options="field:'roledesc',width:60,align:'center'">描述</th>
        <%--<th data-options="field:'parent',width:60,align:'center'">父类角色</th>--%>
        <th data-options="field:'parent',width:60,align:'center'">授权</th>
        <th data-options="field:'rolename',width:60,align:'center'">角色名</th>
        <th data-options="field:'status',width:60,align:'center'">状态</th>
        <th data-options="field:'createdate',width:60,align:'center'">创建时间</th>
    </tr>
    </thead>
</table>
<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
    </div>
    <div>
        Date From: <input class="easyui-datebox" style="width:80px">
        To: <input class="easyui-datebox" style="width:80px">
        Language:
        <select class="easyui-combobox" panelHeight="auto" style="width:100px">
            <option value="java">Java</option>
            <option value="c">C</option>
            <option value="basic">Basic</option>
            <option value="perl">Perl</option>
            <option value="python">Python</option>
        </select>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
    </div>
</div>
</div>
</body>
</html>