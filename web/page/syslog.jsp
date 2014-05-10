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
    var method='<%=request.getAttribute("method")%>';
    var isAuditLog='<%=request.getAttribute("isAuditLog")%>';
    var showAuditIdFlag=(!!isAuditLog&&isAuditLog!='null');

    var digestformatter=function(v,r,i){
        if(showAuditIdFlag){
            return v;
        }else{
            return '<a style="text-decoration: none" href="#" onclick="view(\''+ r.functionid+','+ r.opseno+'\')">'+v+'</a> ';
        }
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
        <th data-options="field:'opseno',width:70">操作日志流水号</th>
        <th data-options="field:'tprkey',width:50,hidden:true">业务序号</th>
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

    function loadLogData(){

        $('#logGrid').datagrid({
            url:'log.do?method='+method+'&eventName='+(showAuditIdFlag?"queryAuditLog":"queryLog")
        })
        if(showAuditIdFlag){
            $('#logGrid').datagrid('showColumn','tprkey');
        }
    }
    loadLogData();

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