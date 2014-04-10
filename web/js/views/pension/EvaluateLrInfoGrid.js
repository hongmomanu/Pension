define(function(){
    var view=function(row){
        cj.showContent({
            url:'lr.do?model=hzyl.EvaluateLrInfo&eventName=queryById',
            title:'查看',
            htmfile:'text!views/pension/EvaluateLrInfo.htm',
            jsfile:'views/pension/EvaluateLrInfo',
            readonly:true,
            data:{id:row.pg_id},
            useproxy:true
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
                                        view(record)
                                    }else if($(this).attr("action")=='edit'){
                                        edit(record)
                                    }else{
                                        logout(record)
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