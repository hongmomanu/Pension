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
    .icon-save{
        background:url('img/accept.png') no-repeat;
    }
    .icon-refresh{
        background:url('img/refresh.gif') no-repeat;
    }.icon-search{
        background:url('img/zoom.png') no-repeat;
    }.icon-log{
        background:url('img/application_cascade.png') no-repeat;
    }
    a:link, a:visited, a:active {
        color: #404040;
        text-decoration: none;
    }

</style>
<script>
    $.extend($.fn.validatebox.defaults.rules, {
        mustDesc: {
            validator: function(value,param){
                return true;
            },
            message: 'Field do not match.'
        }
    });
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
        var s = '<a style="text-decoration: none" href="#" onclick="view(\''+ r.functionid+','+ r.opseno+','+ r.tprkey+'\')">'+v+'</a> ';
        return s;
    }
    var styleFn=function(i,data){
        //if(++i%2==0)return "background-color:#EEE";
    }
    var bstimeformatter=function(v,r,i){
        return v.split('.')[0].substr(5)
    }
</script>
<body>
    <table id="auditGrid" class="easyui-datagrid-noauto"
           data-options="rownumbers:true,singleSelect:false,toolbar:toolbar,
           fit:true, pagination:true,
           pageSize:cj.getDataGridAttr('pageSize'),
           pageList:cj.getDataGridAttr('pageList'),
           border:false,rowStyler:styleFn,fitColumns: true,
           onClickCell:onClickCellFun">
    <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'auditid',width:60">业务序号</th>
            <th data-options="field:'aulevel',width:60,align:'center',
            formatter: function(value,row,index){
                return approvalProcess[value]
			}">操作</th>
            <th  data-options="field:'audefault',width:80,
            editor: { type: 'combobox',
                options: { data:pass, valueField: 'id', textField: 'text',panelHeight:'auto' } },
            formatter:passformatter
            ">通过</th>
            <th  data-options="field:'audesc',width:200,align:'right',
                        editor:{
                            type:'validatebox',
                            options:{
                                invalidMessage:'请输入备注信息!',validType:'mustDesc'
                            }}">备注</th>
            <th data-options="field:'digest',width:350,align:'left',formatter:digestformatter"><a>摘要</a></th>
            <th data-options="field:'username',width:60,align:'left'">办理人</th>
            <th data-options="field:'bsnyue',width:60,align:'center'">业务期</th>
            <th data-options="field:'bstime',width:90,align:'center',formatter:bstimeformatter">时间</th>
        </tr>
        </thead>
    </table>

</body>
</html>

<script>
    var editindex=undefined;
    var method='<%=request.getAttribute("method")%>';
    var functionid='<%=request.getAttribute("functionid")%>';

    var toolbar = [
        {
        text:'审核',
        iconCls:'icon-save',
        handler:function(){saveApproval();}
    },{
        text:'刷新',
        iconCls:'icon-refresh',
        handler:function(){$('#auditGrid').datagrid('reload')}
    },{
        text:'高级搜索',
        iconCls:'icon-search',
        handler:function(){$('#auditGrid').datagrid('reload')}
    },{
        text:'操作日志',
        iconCls:'icon-log',
        handler:function(){cj.searchLog(functionid,true)}
    }];

function loadAuditData(){
    $('#auditGrid').datagrid({
        url:'audit.do?method='+method+'&eventName=queryAudit'
    })
}
    loadAuditData();
var saveApproval=function(){
    var auditmsgs=[];
    $('#auditGrid').datagrid('acceptChanges');
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
    var a= eval('('+JSON.stringify(auditmsgs)+')')
    for(var i=0;i<a.length;i++){
        m=a[i];
        if(m.auflag=='0'&&(!m.audesc || !(m.audesc.trim()))){
            $.messager.alert('提示','业务没有通过,请填写备注信息!','info');
            return;
        }
    }
    $.ajax({
        url:'audit.do?method='+method+'&eventName=saveAudit',
        type:'post',
        data:{
            auditmsgs:JSON.stringify(auditmsgs),
            functionid:functionid
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
        var opseno= a.split(',')[1];
        var tprkey= a.split(',')[2];
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
                var title='原始界面';
                $.ajax({
                    url:'log.do?eventName=queryOriginalpage',
                    data:{opseno:opseno,method:functionid},
                    success:function(res2){
                        cj.showContent({
                            title:title,
                            htmfile:htmlfile,
                            jsfile:jsfile,
                            readonly:true,
                            location:d.location,
                            functionid: d.functionid,
                            res:parent.$.evalJSON(res2),
                            tprkey:tprkey,
                            useproxy:!true
                        })
                    }
                })
            }
        })

    }

</script>