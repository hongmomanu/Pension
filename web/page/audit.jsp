<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审核</title>
</head>
<style>
    html,body {
        margin: 0;
        height:100%;
    }
</style>
<script>
    var approvalProcess=['提交','审核','审批'];
    var pass = [{ "id": "1", "text": "通过" ,"selected":true},
        { "id": "0", "text": "不通过" }
    ];
    var passformatter=function(v,r,i){
        for (var i = 0; i < pass.length; i++) {
            if (pass[i].id == v) {
                return pass[i].text;
            }
        }
        return v;
    }
    var digestformatter=function(v,r,i){
        var s = '<a href="#" onclick="view(\''+ r.functionid+','+ r.tprkey+'\')">'+v+'</a> ';
        return s;
    }
    var styleFn=function(i,data){
        if(++i%2==0)return "background-color:#EEE";
    }

</script>
<body>
    <table id="auditGrid" class="easyui-datagrid"
           data-options="rownumbers:true,singleSelect:false,toolbar:toolbar,
           fit:true, pagination:true,pageSize:15,border:false,rowStyler:styleFn,
           onClickCell:onClickCellFun">
    <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'auditid',width:100">业务序号</th>
            <th data-options="field:'aulevel',width:60,align:'center',
            formatter: function(value,row,index){
                return approvalProcess[value]
			}">操作</th>
            <th data-options="field:'audefault',width:80,
            editor: { type: 'combobox',
                options: { data:pass, valueField: 'id', textField: 'text',panelHeight:'auto' } },
            formatter:passformatter
            ">通过</th>
            <th data-options="field:'audesc',width:250,align:'right',
            editor:{type:'text'}">备注</th>
            <th data-options="field:'digest',width:380,align:'left',formatter:digestformatter"><a>摘要</a></th>
            <th data-options="field:'username',width:80,align:'left'">办理人</th>
        </tr>
        </thead>
    </table>

</body>
</html>

<script>
    var editindex=undefined;
    var toolbar = [
        {
            text:'保存',
            iconCls:'approval',
            handler:function(){$('#auditGrid').datagrid('acceptChanges');}
        },'-',{
        text:'审核',
        iconCls:'approval',
        handler:function(){saveApproval();}
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
        console.log($(item).attr('audefault'))
        if($(item).attr('audefault')=='0'&&$(item).attr('audesc')==null){
            yz=false;
        }
    })

    if(!yz){
        $.messager.alert('提示','业务没有通过,请填写备注信息!','info');
        return;
    }

    $('#auditGrid').datagrid('getSelections').forEach(function(item,i){
        auditmsgs.push({
            auditid:$(item).attr('auditid'),
            auflag:$(item).attr('audefault'),
            aulevel:$(item).attr('aulevel'),
            audesc:$(item).attr('audesc')
        })
    })
    if(auditmsgs.length==0){
        $.messager.alert('提示','请选中再操作!','info');
        return;
    }
    $.ajax({
        url:'audit.do?method='+method+'&eventName=saveAudit',
        type:'post',
        data:{
            auditmsgs:JSON.stringify(auditmsgs)
        },
        success:loadAuditData
    })
}
    function onClickCellFun(index,field, value){
        if("audefault"==field||"audesc"==field){
            if(editindex){
                $(this).datagrid('endEdit',editindex);//
                //$(this).datagrid('selectRow',editindex);
            }
            $(this).datagrid('beginEdit', index);
            var ed = $(this).datagrid('getEditor', {index:index,field:field});
            $(ed.target).focus();
            editindex=index;
        }
    }
    function view(a){
        var functionid= a.split(',')[0];
        $.ajax({
            url:'lr.do?model=manager.Function&eventName=queryFunctionById',
            data:{id:functionid},
            success:function(res){
                var d=eval('('+res+')');
                var htmlfile, jsfile;
                if(d.location){
                    var widget=d.location.replace(/\./g,'/');
                    htmlfile='text!views/'+widget+'.htm';
                    jsfile='views/'+widget;
                }
                var title='查看-'+d.title;
                var folder="pension";
                parent.require(['commonfuncs/TreeClickEvent'],function(js){
                    js.closeTabByTitle(title);
                    js.ShowContent(htmlfile,jsfile,title);
                })


            }
        })

    }

</script>