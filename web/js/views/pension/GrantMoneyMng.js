define(function(){
    var view=function(option,row){
        cj.showContent({
            title:'人员综合查询',
            htmfile:'text!views/pension/IntegrationInquiry.htm',
            jsfile:'views/pension/IntegrationInquiry',
            location:'pension.IntegrationInquiry',
            readonly:!true,
            identityid:row.identityid,
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
    var logout=function(option,row,local){
        cj.question(
            "您准备要注销资金发放信息",
            function(){
                $.ajax({
                    url:lr.url(option,'delete'),
                    type:'post',
                    data:{grantid:row.grantid},
                    complete:function(res){
                        local.find('.easyui-datagrid-noauto').datagrid('reload');
                    }
                })
            }
        )
    }
    var a={
        render:function(local,option){
            local.find('.easyui-datagrid-noauto').datagrid({
                toolbar:local.find('.businesstb'),
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
                                    //$(btns_arr[j][i]).hide();
                                }
                            }
                            (function(index){
                                $(btns_arr[j][i]).click(function(){
                                    var record=rows[index];
                                    if($(this).attr("action")=='view'){
                                        view(option,record)
                                    }else if($(this).attr("action")=='edit'){
                                        edit(option,record)
                                    }else if($(this).attr("action")=='logout'){
                                        logout(option,record,local)
                                    }
                                });
                            })(i);
                        }
                    }
                }
            });



            $('.businesstb .newgrant').bind('click',function(e){
                require(['views/pension/addnewgrantwin','jqueryplugin/jquery-formatDateTime'],function(js){
                    js.render(local);
                });
            });
        }

    }

    return a;
})