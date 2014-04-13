define(function(){
    var view=function(option,row){
        cj.showContent({
            url:'lr.do?model=pension.EvaluateLrInfo&eventName=queryById',
            title:'查看',
            htmfile:'text!views/pension/EvaluateLrInfo.htm',
            jsfile:'views/pension/EvaluateLrInfo',
            location:option.location,
            functionid:option.functionid,
            readonly:true,
            tprkey:row.pg_id
        })
    }
    var edit=function(row){
        alert('去编辑')
    }
    var logout=function(row){
        alert('去注销')
    }
    var a={
        render:function(local,option){
            console.log(option)
            local.find('.easyui-datagrid-noauto').datagrid({
                url:lr.url(option,'query'),
                onLoadSuccess:function(data){
                    var viewbtns=local.find('[action=view]');
                    var editbtns=local.find('[action=edit]');
                    var logoutbtns=local.find('[action=logout]');
                    var btns_arr=[viewbtns,editbtns,logoutbtns];
                    var rows=data.rows;
                    for(var i=0;i<rows.length;i++){
                        for(var j=0;j<btns_arr.length;j++){
                            (function(index){
                                $(btns_arr[j][i]).click(function(){
                                    var record=rows[index];
                                    if($(this).attr("action")=='view'){
                                        view(option,record)
                                    }else if($(this).attr("action")=='edit'){
                                        edit(option,record)
                                    }else{
                                        logout(option,record)
                                    }
                                });
                            })(i);
                        }
                    }
                }
            })
        }

    }

    return a;
})