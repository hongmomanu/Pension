<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审核</title>
</head>
<body>
<div style="width:1024px;height:500px;margin: 0px auto;">
    <h2>CheckBox Selection on DataGrid</h2>
    <div class="demo-info">
        <div class="demo-tip icon-tip"></div>
        <div>Click the checkbox on header to select or unselect all selections.</div>
    </div>
    <div style="margin:10px 0;"></div>

    <table id="dg" class="easyui-datagrid" title="CheckBox Selection on DataGrid" style="width:700px;height:250px"
           data-options="rownumbers:true,singleSelect:false,url:'datagrid_data1.json',method:'get'">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'itemid',width:80">业务序号</th>
            <th data-options="field:'productid',width:100">审核</th>
            <th data-options="field:'listprice',width:80,align:'right'">备注</th>
            <th data-options="field:'unitcost',width:80,align:'right'">业务简介</th>
            <th data-options="field:'attr1',width:220">查看</th>
            <th data-options="field:'status',width:60,align:'center'">回退</th>
        </tr>
        </thead>
    </table>
</div>

</body>
</html>