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
    a:link, a:visited, a:active {
        color: #404040;
        text-decoration: none;
        display: block;
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
</script>
<body>
<table id="logGrid" class="easyui-datagrid-noauto"
       data-options="rownumbers:true,singleSelect:true,toolbar:toolbar,
           fit:true, pagination:true,
           pageSize:15,
		    pageList: [15, 30,50],border:false,rowStyler:styleFn,fitColumns: true">
    <thead>
    <tr>
        <th data-options="field:'opseno',width:100">操作日志流水号</th>
        <th data-options="field:'digest',width:300,align:'left',formatter:digestformatter"><a>摘要</a></th>
        <th data-options="field:'username',width:60,align:'left'">办理人</th>
        <th data-options="field:'bsnyue',width:60,align:'left'">业务期</th>
        <th data-options="field:'bstime',width:120,align:'left',formatter:bstimeformatter">时间</th>
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
    function loadLogData(){
        $('#logGrid').datagrid({
            url:'log.do?method='+method+'&eventName=queryLog'
        })
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

</script>