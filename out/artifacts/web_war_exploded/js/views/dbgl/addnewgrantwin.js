define(function () {
    var selectItmes=[];
    var gridInit=function(index){
        var gridid='#grantmoneyperson'+index+' [name=grid]';
        var btnid='#grantmoneyperson'+index+' [name=querybtn]';
        var query_textid='#grantmoneyperson'+index+' [name=query_text]';
        var persongrid=$(gridid);
        var fn=function(){persongrid.datagrid('load')}
        $(btnid).on('click',fn);
        $(query_textid).on( "keydown", function( event ) {
            if(event.which=='13'){
                fn();
            }
        });
        persongrid.datagrid({
            onCheck: function(rowIndex, rowData){
                if(selectItmes.indexOf(rowData.id)==-1){
                    selectItmes.push(rowData.id);
                }
            },
            onUncheck:function(rowIndex, rowData){
                selectItmes.splice(selectItmes.indexOf(rowData.id));
            },
            onCheckAll:function(rows){
                rows.forEach(function(rowData,index){
                    if(selectItmes.indexOf(rowData.id)==-1){
                        selectItmes.push(rowData.id);
                    }
                })
            },
            onUncheckAll:function(rows){
                rows.forEach(function(rowData,index){
                    selectItmes.splice(selectItmes.indexOf(rowData.id));
                })
            }
        });

        $.parser.parse(persongrid.parent());
        var grid=persongrid;

        var obj={
            url:'ajax/getpeopleinfo.jsp',
            onBeforeLoad: function (params) {
                var options = grid.datagrid('options');

                params.start = (options.pageNumber - 1) * options.pageSize;
                params.limit = options.pageSize;
                params.totalname = "total";
                params.rowsname = "rows";
                params.divisionpath=divisionpath;
                params.businesstype=$('#tabs').tabs('getSelected').panel('options').businesstype;
                params.keyword=$(query_textid).val();
            },
            onLoadSuccess:function(data){
                data.rows.forEach(function(record,index){
                    if(selectItmes.indexOf(record.id)>-1){
                        grid.datagrid('checkRow',index);
                    }
                })
            }
        }
        grid.datagrid(obj);
    }

    return {
        grantmoney:function(btn){
            var form=$('#addnewgrantwin').find('.easyui-tabs').tabs('getSelected').find('form');
            var isnew=$(btn).linkbutton('options').isnew;
            require(['commonfuncs/AjaxForm'],function(ajax){
                var onsubmit=function(param){
                    if(form.attr('type')!='year'){
                        param.grantdate= $.formatDateTime('yy-mm-dd',new Date());
                    }
                    var businesstype=$('#tabs').tabs('getSelected').panel('options').businesstype;
                    param.isnew=isnew;
                    param.userid=userid;
                    param.grantid=selectItmes;
                    param.businesstype=businesstype;
                    param.divisionpath=divisionpath;


                };
                var success=function(){
                    $.messager.alert('消息提示','资金发放成功');
                    $('#addnewgrantwin').dialog('close');
                    $('#businessgrid').datagrid('reload');
                };
                ajax.ajaxform(form,'ajax/grantmoneyform.jsp',onsubmit,success);
            });
        },
        render:function(){

            var me=this;
            var addnewgrantwindiv=$('#addnewgrantwin');

            if(addnewgrantwindiv.length>0){
                addnewgrantwindiv.dialog('open');
                selectItmes=[]; //清除checkbox选中的状态,并刷新数据
                for(var i=0;i<=1;i++){
                    var gridid='#grantmoneyperson'+i+' [name=grid]';
                    $(gridid).datagrid('load');
                }
            }

            else{
                require(['text!views/dbgl/addnewgrantwin.htm'],function(windiv){
                    $('body').append(windiv);


                    $('#addnewgrantwin').find('.easyui-tabs').tabs({'onSelect':function(title,index){
                        gridInit(index);
                    }});
                    $('#addnewgrantwin').dialog({
                        title: '资金发放',
                        width: 490,
                        height: 470,
                        //fit:true,

                        closed: false,
                        cache: false,
                        onOpen:function(){


                        },
                        buttons:[{
                            text:'资金发放',
                            isnew:true,
                            handler:function(){
                                me.grantmoney(this);

                            }
                        },{
                            text:'重新发放',
                            isnew:false,
                            handler:function(){
                                me.grantmoney(this);
                            }
                        },{
                            text:'取消',
                            handler:function(){
                                $('#addnewgrantwin').dialog('close');
                            }
                        }],
                        maximized:false,
                        modal:true
                    });
                    $.parser.parse($('#addnewgrantwin').parent());
                    var date = new Date();
                    var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
                    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
                    var form_1=$('#addnewgrantwin').find('.easyui-tabs').tabs('getTab',0).find('form');
                    var form_2=$('#addnewgrantwin').find('.easyui-tabs').tabs('getTab',1).find('form');

                    form_1.form('load',
                        {
                            bgdate:$.formatDateTime('yy-mm-dd',firstDay),
                            eddate:$.formatDateTime('yy-mm-dd',lastDay)
                        });
                    form_2.form('load',{
                        grantdate:$.formatDateTime('yy',new Date())
                    });

                });

            }


        }

    }


})