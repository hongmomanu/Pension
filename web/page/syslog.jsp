<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>操作日志</title>
</head>
<style>
    html,body {
        margin: 0;
        height:100%;
    }
    .icon-save{
        background:url('img/shenghe.gif') no-repeat;
    }
    .icon-refresh{
        background:url('img/refresh.gif') no-repeat;
    }.icon-search{
         background:url('img/search.gif') no-repeat;
     }.icon-log{
          background:url('img/mylog.gif') no-repeat;
      }
    .icon-chexiao{
        background:url('img/chexiao.jpg') no-repeat;
    }
    a:link, a:visited, a:active {
        color: #404040;
        text-decoration: none;
        /*display: block;*/
    }

</style>
<script>
    var digestformatter=function(v,r,i){
        var s = '<a style="text-decoration: none" href="#" onclick="view(\''+ r.functionid+','+ r.opseno+'\')">'+v+'</a> ';
        return s;
    }
    var styleFn=function(i,data){
        //if(++i%2==0)return "background-color:#EEE";
    }
    var bstimeformatter=function(v,r,i){
        return v.split('.')[0]
    }
    var chexiaoformat=function(v,r,i){
        if(r.rbflag=='1'){
           return '已回退';
        }else{
            return '<a href="#"  onclick="dbrol(\''+ r.functionid+','+ r.opseno+','+ r.loginname+'\')"'+'>回退</a>';
        }
    }
</script>
<body>
<table id="logGrid" class="easyui-datagrid-noauto"
       data-options="rownumbers:true,singleSelect:true,toolbar:toolbar,
           fit:true, pagination:true,
           pageSize:cj.getDataGridAttr('pageSize'),
           pageList:cj.getDataGridAttr('pageList'),
           border:false,rowStyler:styleFn,fitColumns: true">
    <thead>
    <tr>
        <th data-options="field:'opseno',width:100">操作日志流水号</th>
        <th data-options="field:'auditid',width:100,hidden:true">业务序号</th>
        <th data-options="field:'digest',width:300,align:'left',formatter:digestformatter"><a>摘要</a></th>
        <th data-options="field:'username',width:60,align:'left'">办理人</th>
        <th data-options="field:'bsnyue',width:60,align:'left'">业务期</th>
        <th data-options="field:'bstime',width:120,align:'left',formatter:bstimeformatter">时间</th>
        <th data-options="field:'ro',width:120,align:'left',formatter:chexiaoformat">回退</th>
    </tr>
    </thead>
</table>

</body>
</html>

<script>
    var editindex=undefined;
    var toolbar = [
        {
            text:'刷新',
            iconCls:'icon-refresh',
            handler:function(){$('#logGrid').datagrid('reload')}
        },{
            text:'高级搜索',
            iconCls:'icon-search',
            handler:function(){$('#logGrid').datagrid('reload')}
        }];
    var method='<%=request.getAttribute("method")%>';
    var isAuditLog='<%=request.getAttribute("isAuditLog")%>';
    function loadLogData(){
        var showAuditIdFlag=(!!isAuditLog&&isAuditLog!='null');
        $('#logGrid').datagrid({
            url:'log.do?method='+method+'&eventName='+(showAuditIdFlag?"queryAuditLog":"queryLog")
        })
        if(showAuditIdFlag){
            $('#logGrid').datagrid('showColumn','auditid');
        }
    }
    loadLogData();
    var saveApproval11=function(){
        var auditmsgs=[];
        $('#logGrid').datagrid('acceptChanges');
        $('#logGrid').datagrid('getSelections').forEach(function(item,i){
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
                auditmsgs:JSON.stringify(auditmsgs)
            },
            success:loadAuditData
        })
    }

    function view(a){
        var functionid= a.split(',')[0];
        var opseno= a.split(',')[1];
        $.ajax({
            url:'log.do?eventName=queryOriginalpage',
            data:{opseno:opseno,method:functionid},
            success:function(res){
                cj.showHtml('原始界面',res);
            }
        })
    }
    function dbrol(a){
        var functionid= a.split(',')[0];
        var opseno= a.split(',')[1];
        var loginname= a.split(',')[2];
        $.ajax({
            url:'log.do?eventName=rollback',
            data:{opseno:opseno,loginname:loginname,method:functionid},
            success:function(res){
                var d=eval('('+res+')');
                if(d.success=='true'||d.success==true){
                    $('#logGrid').datagrid('reload');
                }else{
                    $.messager.alert(cj.defaultTitle, d.message, 'info');
                }

            }
        })
    }

</script>