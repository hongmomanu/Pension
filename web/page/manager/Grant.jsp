<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>授权管理</title>
    <style>
        .icon-save{
            background:url('img/head/3.png') no-repeat;
        }
    </style>
</head>
<body>

<a href="#" class="easyui-linkbutton" onclick="getChecked()">GetChecked</a>
<div class="easyui-tabs" style="height:450px" data-options="tools:'#tab-tools'" >

    <div title="业务菜单" style="padding:10px">
        <ul id="functiontree" class="easyui-tree" data-options="animate:true"></ul>
    </div>

    <div title="About" style="padding:10px">
        <p style="font-size:14px">jQuery EasyUI framework helps you build your web pages easily.</p>
        <ul>
            <li>easyui is a collection of user-interface plugin based on jQuery.</li>
            <li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
            <li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
            <li>complete framework for HTML5 web page.</li>
            <li>easyui save your time and scales while developing your products.</li>
            <li>easyui is very easy but powerful.</li>
        </ul>
    </div>

    <div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
        This is the help content.
    </div>
</div>
<div id="tab-tools">
    <a href="javascript:void(0)" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'icon-save'" onclick="save()"></a>
</div>
</body>
</html>
<script>
$(function(){
    $('#functiontree').tree({
        checkbox:true,
        url:'lr.do?model=manager.Function&eventName=queryFunctionTreeMng',
        onClick:function(node){
            //funObj=node;
            //$('#form').form('load','lr.do?model=manager.Function&eventName=queryFunctionById&node='+node.functionid);
        }
    });
})
function getChecked(){
    var nodes = $('#functiontree').tree('getChecked');
    var s = '';
    for(var i=0; i<nodes.length; i++){
        if (s != '') s += ',';
        s += nodes[i].functionid;
    }
    $.ajax(
            {
                type: "POST",
                data: { functionids : s ,roleid:'00001'},
                url:'lr.do?model=manager.Grant&eventName=save',
                success:function(res){alert(res)}
            }
    )
}
</script>