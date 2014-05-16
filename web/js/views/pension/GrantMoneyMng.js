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
                toolbar:local.find('.businesstb'),
                url:'lr.do?model=pension.EvaluateLrInfoGrid&eventName=query',//lr.url(option,'query'),
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
            });

            var options=$('#businessgrid').datagrid('options');
            options.search_params={
                businesstype:businesstype,
                type:type,
                divisionpath:divisionpath
            };

            if(options.type==='month'){
                options.search_params.name=['time'];
                options.search_params.compare=['month'];
                options.search_params.value=[$('#businesstb .year').combobox('getValue')+"-"+
                    ($('#businesstb .month').combobox('getValue')<10?'0'+
                        $('#businesstb .month').combobox('getValue'):$('#businesstb .month').combobox('getValue'))];
                options.search_params.logic =['and'];
            }

            $('#businesstb .search,#businesstb .keyword').bind('click keypress',function(e){
                var keycode = (event.keyCode ? event.keyCode : event.which);
                if($(this).attr("type")==='keyword'&&keycode!=13)return;
                var search_params={
                    bgdate:$('#businesstb .bgdate').length>0?$('#businesstb .bgdate').datebox('getValue'):null,
                    eddate:$('#businesstb .eddate').length>0?$('#businesstb .eddate').datebox('getValue'):null,
                    keyword:$('#businesstb .keyword').val(),
                    statusType:($('#cc').attr('statusType')||0)
                };
                $('#businessgrid').datagrid('load',search_params);
                for(var item in search_params){
                    var options=$('#businessgrid').datagrid('options');
                    options.search_params[item]=search_params[item];
                }


            });

            require(['commonfuncs/LookupItemName'],function(lookjs){
                var isfind=lookjs.lookup(processRoleBtn,
                    {name:"name",value:"资金发放"});
                if(isfind){
                    $('#businesstb .newgrant').bind('click',function(e){
                        require(['views/dbgl/addnewgrantwin','jqueryplugin/jquery-formatDateTime'],function(js){
                            js.render();
                        });
                    });
                }else{
                    $('#businesstb .newgrant').hide();
                }
            });
        }

    }

    return a;
})