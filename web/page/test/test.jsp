<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        html,body {
            margin: 0;
            height:100%;
        }
    </style>

</head>
<body>
<input   id="owerid" type="text" name="owerid">
<div class="easyui-pagination" style="border:1px solid #ddd;" data-options="
                total: 114,
                showPageList: false,
                showRefresh: false,
                displayMsg: ''
            "></div>
<div class="easyui-pagination" style="border:1px solid #ddd;" data-options="
                total:114,
                layout:['first','prev','next','last']
            "></div>
<div id="p" style="padding:10px;">
    <p>panel content.</p>
    <p>panel content.</p>
</div>

<button id="winopen">打开</button>

<input id="ss">
<div id="mm" style="width:120px">
    <div data-options="name:'all',iconCls:'icon-ok'">All News</div>
    <div data-options="name:'sports'">Sports News</div>
</div>
</body>
</html>
<script>
$(function(){
    //D:\hvit\Pension\web\datagrid_data1.json
    $('#owerid').combogrid({
        panelWidth:400,
        url: 'datagrid_data1.json',
        idField:'owerid',
        textField:'owerid',
        validType:'personid',
        mode:'remote',
        fitColumns:true,
        //pagination:true,
        onBeforeLoad: function(param){
            var options = $('#owerid').combogrid('options');
            if(param.q!=null){
                param.query=param.q;
                param.start = (options.pageNumber - 1) * options.pageSize;
                param.limit = options.pageSize;
                $('#owerid').combogrid('setValue',param.q);
                $('#owerid').val(param.q);
            }

        },
        onClickRow: function(rownum,record){
            alert(1)
        },
        columns:[[
            {field:'productid',title:'',width:60,hidden:true},
            {field:'productname',title:'户主姓名',width:80},
            {field:'unitcost',title:'性别',align:'center',width:30},
            {field:'status',title:'类型',align:'center',width:100}
        ]]
    });

    pager =$('#owerid').combogrid('grid').datagrid('getPager');    // get the pager of datagrid
    console.log(pager);
    pager.pagination({
        showPageList:false,
        buttons:[{
            iconCls:'icon-search',
            handler:function(){
                alert('search');
            }
        },{
            iconCls:'icon-add',
            handler:function(){
                alert('add');
            }
        },{
            iconCls:'icon-edit',
            handler:function(){
                alert('edit');
            }
        }],
        onBeforeRefresh:function(){
            alert('before refresh');
            return true;
        }
    });

    $('#p').panel({
        width:500,
        height:150,
        title:'My Panel',
        tools:[{
            iconCls:'icon-add',
            handler:function(){alert('new')}
        },{
            iconCls:'icon-save',
            handler:function(){alert('save')}
        }]
    });
    $('#winopen').bind('click',function(){
        $('#ss').searchbox({
            searcher:function(value,name){
                alert(value + "," + name)
            },
            menu:'#mm',
            prompt:'Please Input Value'
        });
    })

})
</script>