<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
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
            <th data-options="field:'description',width:20">角色</th>
            <th data-options="field:'description',width:140">描述</th>

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
            $("input[name='regionid']").val(node.dvcode);
        }
    });

    $('#saveUser').bind('click',function(){
        $("input[name='userid']").val('-1');
        var regionid='';
        var me=$('#form');
        me.form('submit', {
            url:'lr.do?model=manager.User&eventName=saveUser',
            onSubmit: function(){
                regionid=$("input[name='regionid']").val();
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

</script>