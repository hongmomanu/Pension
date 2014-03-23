<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审核</title>
</head>
<script>
    var approvalProcess=['提交','审核','审批'];
    var pass = [{ "id": "1", "text": "通过" ,"selected":true},
        { "id": "0", "text": "不通过" }
    ];
</script>
<body>
<div style="width:1024px;height:500px;margin: 0px auto;">
    <table id="auditGrid" class="easyui-datagrid"
           data-options="rownumbers:true,singleSelect:false,toolbar:toolbar,onClickCell: onClickCell">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'auditid',width:80">业务序号</th>
            <th data-options="field:'aulevel',width:60,
            formatter: function(value,row,index){
                return approvalProcess[value]
			}">操作</th>
            <th data-options="field:'audefault',width:80,
            editor: { type: 'combobox',
                options: { data:pass, valueField: 'id', textField: 'text',panelHeight:'auto' } }
            ">通过</th>
            <th data-options="field:'audesc',width:180,align:'right',
            editor:{type:'text'}">备注</th>
            <th data-options="field:'digest',width:280,align:'right'">摘要</th>
            <th data-options="field:'username',width:50,align:'right'">办理人</th>
            <th data-options="field:'attr1',width:50">查看</th>
            <th data-options="field:'status',width:50,align:'center'">回退</th>
        </tr>
        </thead>
    </table>
</div>

</body>
</html>
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
        if ($('#auditGrid').datagrid('validateRow', editIndex)){
            $('#auditGrid').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickCell(index, field){
        if (endEditing()){
            $('#auditGrid').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }
    }
</script>
<script>
    var toolbar = [{
        text:'Add',
        iconCls:'icon-add',
        handler:function(){alert('add')}
    },{
        text:'Cut',
        iconCls:'icon-cut',
        handler:function(){alert('cut')}
    },'-',{
        text:'审核',
        iconCls:'approval',
        handler:function(){saveApproval();}
    },'-',{
        text:'操作日志',
        iconCls:'approval',
        handler:function(){alert(this.text)}
    }];
var method='<%=request.getAttribute("method")%>';
function loadAuditData(){
    $('#auditGrid').datagrid({
        url:'audit.do?method='+method+'&eventName=queryAudit'
    })
}
    loadAuditData();
var saveApproval=function(){
    var auditmsgs=[];
    $('#auditGrid').datagrid('acceptChanges');
    var yz=true;
    $('#auditGrid').datagrid('getChecked').forEach(function(item,i){
        if($(item).attr('audefault')=='0'&&$(item).attr('audesc')==null){
            yz=false;
        }
    })
    if(!yz){
        alert(' 业务没有通过,请填写备注信息');return;
    }

    $('#auditGrid').datagrid('getChecked').forEach(function(item,i){
        auditmsgs.push({
            auditid:$(item).attr('auditid'),
            auflag:$(item).attr('audefault'),
            aulevel:$(item).attr('aulevel'),
            audesc:$(item).attr('audesc')
        })
    })
    $.ajax({
        url:'audit.do?method='+method+'&eventName=saveAudit',
        type:'post',
        data:{
            auditmsgs:JSON.stringify(auditmsgs)
        },
        success:loadAuditData
    })
}
</script>