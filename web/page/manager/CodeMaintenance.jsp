<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>代码维护</title>
</head>
<script>
    var kwh = [{ "id": "1", "text": "是" ,"selected":true},
        { "id": "0", "text": "否" }
    ];
    var kwhformatter=function(v,r,i){
        for (var i = 0; i < kwh.length; i++) {
            if (kwh[i].id == v) {
                return kwh[i].text;
            }
        }
        return v;
    }
</script>
<body>

<div class="easyui-tabs" data-options="tabWidth:112,fit:true" style="width:1024px;height:100%;margin: 0px auto;">
    <div title="下拉选择项">
        <div class="easyui-layout" style="width:1010px;height:100%;margin: 0px auto;"
             data-options="border:false">
            <div  data-options="region:'west',split:false,lines:true" style="width:500px;">
                <table id="aa09" class="easyui-datagrid"
                       data-options="url:'lr.do?model=manager.CodeMaintenance&eventName=queryCode9',
                       fitColumns:true,singleSelect:true,border:false,pagination:true,fit:true">
                    <thead>
                    <tr>
                        <th data-options="field:'isnew',width:100,editor:'text',require:true,hidden:true"></th>
                        <th data-options="field:'aaa100',width:100,editor:'text',require:true">代码类别</th>
                        <th data-options="field:'aaa101',width:100,editor:'text'">类别名称</th>
                        <th data-options="field:'aaa104',width:100,align:'right',
                        editor: { type: 'combobox',
                        options: { data:kwh, valueField: 'id', textField: 'text',panelHeight:'auto' } },
                        formatter:kwhformatter">代码可维护标志</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div data-options="region:'center',split:false,lines:true">
                <table class="easyui-datagrid"
                       data-options="fitColumns:true,singleSelect:true,border:false">
                    <thead>
                    <tr>
                        <th data-options="field:'productid',width:100">productid</th>
                        <th data-options="field:'productname',width:100">productname</th>
                        <th data-options="field:'unitcost',width:100,align:'right'">unitcost</th>
                    </tr>
                    </thead>
                </table>
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

<script>
    $(function(){
        var a=100;
        var dg=$('#aa09');
        var editIndex = undefined;
        function endEditing(){
            if (editIndex == undefined){return true}
            if (dg.datagrid('validateRow', editIndex)){
                dg.datagrid('endEdit', editIndex);
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }
        function onClickRow(index){
            if (editIndex != index){
                if (endEditing()){
                    dg.datagrid('selectRow', index)
                            .datagrid('beginEdit', index);
                    editIndex = index;
                } else {
                    dg.datagrid('selectRow', editIndex);
                }
            }
        }
        function append(){
            if (endEditing()){
                dg.datagrid('appendRow',{isnew:'-1',aaa100:'newcode',aaa101:'中文名称',aaa104:'1'});
                editIndex = dg.datagrid('getRows').length-1;
                dg.datagrid('selectRow', editIndex)
                        .datagrid('beginEdit', editIndex);
            }
        }
        function removeit(){
            if (editIndex == undefined){return}
            dg.datagrid('cancelEdit', editIndex)
                    .datagrid('deleteRow', editIndex);
            editIndex = undefined;
        }
        function accept(){
            if (endEditing()){
                dg.datagrid('acceptChanges');
            }
        }
        function reject(){
            dg.datagrid('rejectChanges');
            editIndex = undefined;
        }
        function getChanges(){
            var rows = dg.datagrid('getChanges');
            alert(rows.length+' rows are changed!');
        }
        var pager = dg.datagrid().datagrid('getPager');    // get the pager of datagrid
        pager.pagination({
            buttons:[{
                iconCls:'icon-add',
                text:'添加新选项',
                handler:append
            },{
                iconCls:'icon-edit',
                text:'保存',
                handler:function(){
                    if (endEditing()){
                        var ds=dg.datagrid('acceptChanges').datagrid('getData').rows;
                        var d;
                        for(var i=0;i<ds.length;i++){
                            if(ds[i].isnew){
                                d=ds[i];
                                break;
                            }
                        }
                        $.ajax({
                            url:'lr.do?model=manager.CodeMaintenance&eventName=saveCode9',
                            data:d,
                            type:'post',
                            success:function(res){
                                cj.calert(res);
                            }
                        })
                    }
                }
            }]
        });
    })

</script>
