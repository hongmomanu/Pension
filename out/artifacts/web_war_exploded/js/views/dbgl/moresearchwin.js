define(function () {
    return {
        grantmoney:function(btn){
            /*var form=$('#addnewgrantwin').find('.easyui-tabs').tabs('getSelected').find('form');
            var isnew=$(btn).linkbutton('options').isnew;
            require(['commonfuncs/AjaxForm'],function(ajax){
                var onsubmit=function(param){
                    if(form.attr('type')!='year'){
                        param.grantdate= $.formatDateTime('yy-mm-dd',new Date());
                    }
                    var businesstype=$('#tabs').tabs('getSelected').panel('options').businesstype;
                    param.isnew=isnew;
                    param.userid=userid;
                    param.businesstype=businesstype;
                    param.divisionpath=divisionpath;


                };
                var success=function(){
                    $.messager.alert('消息提示','资金发放成功');
                    $('#addnewgrantwin').dialog('close');
                    $('#businessgrid').datagrid('reload');
                };
                ajax.ajaxform(form,'ajax/grantmoneyform.jsp',onsubmit,success);
            });*/
        },
        search:function(){
            /**多条件数组**/
            var conditions=$('#moresearchwin form').form('serialize');
            var options=$('#businessgrid').datagrid('options');
            for(var item in conditions){
                conditions[item]=conditions[item].split(",");
                options.search_params[item]=options.search_params[item]?options.search_params[item].concat(conditions[item]):conditions[item];
            }
            $('#businessgrid').datagrid('load',conditions);

        },
        newcondition:function(btn){
            var me=this;
            require(['text!views/dbgl/moresearchwinitem.htm','jqueryplugin/easyui-form'],function(itemhtml){
                $('#moresearchwin table').append(itemhtml);
                var newitem=$('#moresearchwin table').find('tr').last();
                var searchitems=newitem.find('.searchitem');
                searchitems.bind('change',function () {
                    var isvalid=$('#moresearchwin form').form('validate');
                    if(isvalid){
                        $('#moresearchwin_search').linkbutton('enable');
                    }else{
                        $('#moresearchwin_search').linkbutton('disable');
                    }
                });
                $.parser.parse(newitem);
                newitem.find('.moresearchcomb').combobox({
                    onShowPanel: function () {
                        var mysearchtype = $(this).attr('searchtype');
                        mysearchtype=mysearchtype?mysearchtype:me.searchtype;
                        if(mysearchtype=='compare'){
                            var previtem=$(this).parent().prev().find('.moresearchcomb');
                            mysearchtype=me.searchtype
                                + previtem.combobox('getValue');
                        }
                        var url = 'ajax/getenumbytype.jsp?type=' + mysearchtype;
                        $(this).combobox('reload', url);
                    },
                    onSelect:function(record){
                        if(!$(this).attr('searchtype')){
                            var nextitem=$(this).parent().next().find('.moresearchcomb');
                            nextitem.combobox('enable');
                        };

                        var isvalid=$('#moresearchwin form').form('validate');
                        if(isvalid){
                            $('#moresearchwin_search').linkbutton('enable');
                        }else{
                            $('#moresearchwin_search').linkbutton('disable');
                        }
                    }

                });

                newitem.find('.name').combobox({
                    valueField: 'value',
                    url:'ajax/getenumbytype.jsp?type='+me.searchtype,
                    textField: 'label'
                });
                newitem.find('img').click(function(){
                    $(this).parent().parent().remove();
                    if($('#moresearchwin table').find('tr').length==1){
                        $('#moresearchwin_search').linkbutton('disable');
                    }
                });
            });
        },
        render:function(searchtype){
            var me=this;
            me.searchtype=searchtype;
            var moresearchwindiv=$('#moresearchwin');
            if(moresearchwindiv.length>0){
                moresearchwindiv.dialog('open');
            }
            else{
                require(['text!views/dbgl/moresearchwin.htm'],function(windiv,itemhtml){

                    $('body').append(windiv);

                    $('#moresearchwin').dialog({
                        title: '高级搜索',
                        width: 450,
                        height: 250,
                        //fit:true,

                        closed: false,
                        cache: false,
                        onOpen:function(){

                        },
                        buttons:[{
                            text:'检索',
                            id:'moresearchwin_search',
                            disabled:true,
                            handler:function(){
                                me.search();

                            }
                        },{
                            text:'新增',

                            handler:function(){
                                me.newcondition(this);
                            }
                        },{
                            text:'取消',
                            handler:function(){
                                $('#moresearchwin').dialog('close');
                            }
                        }],
                        maximized:false,
                        modal:true
                    });
                    $.parser.parse($('#moresearchwin').parent());

                    me.newcondition(null);

                });

            }


        }

    }


})