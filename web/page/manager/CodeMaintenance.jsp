<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>代码维护</title>
</head>
<body>

<div class="easyui-tabs" data-options="tabWidth:112" style="width:1024px;height:450px;margin: 0px auto;">
    <div title="下拉选择项" style="padding:10px">
        <div class="easyui-layout" style="width:1010px;height:99%;margin: 0px auto;">
            <div data-options="region:'west',split:false,lines:true" style="width:500px;">
                <table class="easyui-datagrid"
                       data-options="url:'datagrid_data.json',fitColumns:true,singleSelect:true">
                    <thead>
                    <tr>
                        <th data-options="field:'code',width:100">Code</th>
                        <th data-options="field:'name',width:100">Name</th>
                        <th data-options="field:'price',width:100,align:'right'">Price</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div data-options="region:'center',split:false,lines:true">
                <input class="easyui-combobox"
                       name="language"
                       data-options="
                    url:'combobox_data1.json',
                    method:'get',
                    valueField:'id',
                    textField:'text',
                    panelHeight:'auto'
            ">
            </div>
        </div>

    </div>
    <div title="综合参数" style="padding:10px">
        <p>Maps Content.</p>
    </div>
    <div title="Journal" style="padding:10px">
        <p>Journal Content.</p>
    </div>
    <div title="History" style="padding:10px">
        <p>History Content.</p>
    </div>
    <div title="References" style="padding:10px">
        <p>References Content.</p>
    </div>
    <div title="Contact" data-options="tabWidth:110" style="padding:10px">
        <p>Contact Content.</p>
    </div>
</div>
</body>
</html>