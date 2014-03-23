<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h2>Cell Editing in DataGrid</h2>
<div class="demo-info">
    <div class="demo-tip icon-tip"></div>
    <div>Click a cell to start editing.</div>
</div>
<div style="margin:10px 0;"></div>

<table id="dg" class="easyui-datagrid" title="Cell Editing in DataGrid" style="width:700px;height:auto"
       data-options="
                iconCls: 'icon-edit',
                singleSelect: true,
                url: 'datagrid_data1.json',
                method:'get',
                onClickCell: onClickCell
            ">
    <thead>
    <tr>
        <th data-options="field:'itemid',width:80">Item ID</th>
        <th data-options="field:'productid',width:100,editor:'text'">Product</th>
        <th data-options="field:'listprice',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">List Price</th>
        <th data-options="field:'unitcost',width:80,align:'right',editor:'numberbox'">Unit Cost</th>
        <th data-options="field:'attr1',width:250,editor:'text'">Attribute</th>
        <th data-options="field:'status',width:60,align:'center',editor:{type:'checkbox',options:{on:'P',off:''}}">Status</th>
    </tr>
    </thead>
</table>

<script type="text/javascript">
    $.extend($.fn.datagrid.methods, {
        editCell: function(jq,param){
            return jq.each(function(){
                var opts = $(this).datagrid('options');
                var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor1 = col.editor;
                    if (fields[i] != param.field){
                        col.editor = null;
                    }
                }
                $(this).datagrid('beginEdit', param.index);
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor = col.editor1;
                }
            });
        }
    });

    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#dg').datagrid('validateRow', editIndex)){
            $('#dg').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickCell(index, field){
        if (endEditing()){
            $('#dg').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }
    }
</script>
</body>
</html>