<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css"  href="js/commonfuncs/ext/popwin.css">
    <script type="text/javascript"  src="js/commonfuncs/ext/popwin.js"></script>

    <script>
        function formatRole(val,row){
            return '<a href="javascript:void(0)" ' +
                    'onclick="f(\''+row.userid+'\',\''+row.username+'\');"><span style="color:red;">角色</span><a>';
        }
        function f(a,b){
            //openIframe('对<'+b+'>进行授权');
            var u= '当前用户 <span style="color:green;">'+b+'</span>';
            $('#win_rolegrid').window({title:u}).window('open');
            loadRoleGrid(a)
        }
    </script>
</head>
<body>
<div id="usermng" class="easyui-layout" style="width:1024px;height:100%;margin: 0px auto;">
    <div data-options="region:'west',title:'行政区划',split:false" style="width:300px;">
        <ul id="divisiontree"></ul>
    </div>
    <div data-options="region:'center',title:'功能详细信息'">
    <table id="usergrid" class="easyui-datagrid"
           data-options="rownumbers:true,singleSelect:true,url:'lr.do?model=manager.User&eventName=queryUser',method:'get',toolbar:'#tb',fitColumns:true">
        <thead>
        <tr>
            <%--<th data-options="field:'userid',width:80">用户ID</th>--%>
            <%--<th data-options="field:'passwd',width:100">密码</th>--%>
            <th data-options="field:'loginname',width:40,align:'right'">登录名</th>
            <%--<th data-options="field:'dept',width:80,align:'right'">部门</th>--%>
            <th data-options="field:'useful',width:60,align:'center'">有效</th>
            <th data-options="field:'dvname',width:60,align:'center'">区域代码</th>
            <th data-options="field:'username',width:60,align:'center'">用户名称</th>
            <th data-options="field:'createdate',width:60,align:'center'">创建时间</th>
            <th data-options="field:'grant',width:60,align:'center',formatter:formatRole">角色</th>
            <th data-options="field:'description',width:140">描述</th>

        </tr>
        </thead>
    </table>
    <div id="tb" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" <%-- plain="true"--%>
               onclick="$('#w').window('open')">add</a>

            <input class="easyui-datebox" style="width:80px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
        </div>
    </div>
</div>

<div id="w"   class="easyui-window"
     data-options="iconCls:'icon-save',closed:true" style="width:600px;height:220px;">
    <form id="form" method="post">
        <fieldset ><legend>用户</legend>
            <input type="hidden" name="regionid" value="330100">
            <input type="hidden" name="userid">
            <div>
                <table class="formtable">

                    <tr>
                        <td class="formtdtext">描述:</td>
                        <td colspan="3"><input type="text" name="description"></td>
                    </tr>
                    <tr>
                        <td class="formtdtext">用户名称:</td>
                        <td><input type="text" name="username"></td>
                        <td class="formtdtext">登录名:</td>
                        <td><input type="text" name="loginname"></td>
                    </tr>
                    <tr>
                        <td class="formtdtext">密码:</td>
                        <td><input type="text" name="passwd"></td>
                        <td class="formtdtext">其它信息:</td>
                        <td><input type="text" name="otherinfo"></td>
                    </tr>
                </table>
            </div>

        </fieldset>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" id="saveUser" class="easyui-linkbutton">保存</a>
        </div>
    </form>
</div>
</div>
</div>

<div id="win_rolegrid" class="easyui-window"  style="width:600px;height:400px"
     data-options="iconCls:'icon-save',modal:true,closed:true">
    <div class="easyui-layout" data-options="fit:true">
        <input type="hidden" name="userid">
        <a href="#" class="easyui-linkbutton" onclick="saveUserRole();">保存</a>
        <table id="rolegrid" class="easyui-datagrid" style="width:580px;">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th data-options="field:'roleid',width:80">ID</th>
                <th data-options="field:'roledesc',width:100">描述</th>
                <th data-options="field:'rolename',width:80,align:'right'">角色名称</th>
                <th data-options="field:'status',width:80,align:'right'">状态</th>
                <th data-options="field:'createdate',width:120,align:'right'">创建时间</th>
            </tr>
            </thead>
        </table>

    </div>
</div>

</body>
</html>
<script>
$(function(){

    $('#divisiontree').tree({
        checkbox:true,
        url:'lr.do?model=manager.User&eventName=queryDivisionTree',
        onClick:function(node){
            $('#usergrid').datagrid('load',{
                dvcode: node.dvcode
            })
            $("#w input[name='regionid']").val(node.dvcode);
        }
    });

    $('#saveUser').bind('click',function(){
        $("#w input[name='userid']").val('-1');
        var regionid='';
        var me=$('#form');
        me.form('submit', {
            url:'lr.do?model=manager.User&eventName=saveUser',
            onSubmit: function(){
                regionid=$("#w input[name='regionid']").val();
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');	// hide progress bar while the form is invalid
                }
                return isValid;	// return false will stop the form submission
            },
            success: function(res){
                me.form('clear').form('load',{regionid:regionid})
                $('#w').window('close');
                $('#usergrid').datagrid('reload');
                $.messager.progress('close');	// hide progress bar while submit successfully
            }
        });
    });
})


function loadRoleGrid(userid){
    $("#win_rolegrid input[name=userid]").val(userid);
    $('#rolegrid').datagrid({
        url:'lr.do?model=manager.Role&eventName=queryRole',
        method:'POST',
        singleSelect:false,collapsible:true,
        onBeforeLoad: function (params) {
            params.userid=userid;
        },
        onLoadSuccess : function(data) {
            if (data) {
                $.each(data.rows, function(index, item) {
                    if (item.selected=='true') {
                        $('#rolegrid').datagrid('checkRow',index);
                    }
                });
            }
        }
    });
}
function saveUserRole(){
    var array=[];
    $('#rolegrid').datagrid('getChecked').forEach(function(item,index){
        array.push(item.roleid)
    })
    if(array.length==0){
       alert('0')
       return;
    }
    var obj={
        userid:$("#win_rolegrid input[name=userid]").val(),
        roleids:array.toString()
    }
    $.ajax({
        type: "POST",
        url: 'lr.do?model=manager.Grant&eventName=saveRoleUser',
        data:obj,
        success: function(res){
            $("#win_rolegrid").window('close');
            var obj=eval('('+res+')');
            if(obj.success=='true'||obj.success){
                $.messager.alert('提示','操作成功!','info');
            }else{
                $.messager.alert('提示','操作失败!','info');
            }}
    })
}

</script>
