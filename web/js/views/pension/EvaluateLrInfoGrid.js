define(function(){
    var view=function(option,row){
        cj.showContent({
            url:'lr.do?model=pension.EvaluateLrInfo&eventName=queryById',
            title:'查看',
            htmfile:'text!views/pension/EvaluateLrInfo.htm',
            jsfile:'views/pension/EvaluateLrInfo',
            location:'pension.EvaluateLrInfo',
            functionid:option.functionid,
            readonly:true,
            tprkey:row.pg_id
        })
    }
    var edit=function(option,row){
        cj.showContent({
            url:'lr.do?model=pension.EvaluateLrInfo&eventName=queryById',
            title:'变更',
            htmfile:'text!views/pension/EvaluateLrInfoChange.htm',
            jsfile:'views/pension/EvaluateLrInfoChange',
            location:'pension.EvaluateLrInfoChange',
            functionid:'XGcoe8yNX0DfdZHn4CMa',
            tprkey:row.pg_id
        })
    }
    var logout=function(option,row){
        cj.showContent({
            url:'lr.do?model=pension.EvaluateLrInfo&eventName=queryById',
            title:'注销',
            htmfile:'text!views/pension/EvaluateLrInfoLogout.htm',
            jsfile:'views/pension/EvaluateLrInfoLogout',
            location:'pension.EvaluateLrInfoLogout',
            functionid:'P6IyVH34P1sgn7VzU4q8',
            tprkey:row.pg_id
        })
    }
    var a={
        render:function(local,option){
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
                            var row=rows[i];
                            if(row.unauditchange*1>0||row.active=='2'){
                                //存在未审核的数据时，不能变更和注销
                                //是注销通过的数据，不能变更和注销
                                if(j!=0){  //j==0时为查看
                                    $(btns_arr[j][i]).hide();
                                }
                            }
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