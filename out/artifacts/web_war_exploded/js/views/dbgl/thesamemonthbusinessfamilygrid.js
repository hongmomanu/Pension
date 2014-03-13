/**
 * Created by jack on 13-12-31.
 */
define(function () {

    function render(parameters) {
        var type=null;
        var options=$('#tabs').tabs('getSelected').panel('options');
        var businesstype=options.businesstype;
        require(['commonfuncs/BusinessGridCommon','jqueryplugin/jquery-formatDateTime'],function(js){
            var year=parseInt($.formatDateTime('yy',new Date()));
            var month=parseInt($.formatDateTime('mm',new Date()));
            var data=[];
            var mdata=[];
            for(var i=0;i<10;i++){
                data.push({
                    label:(year-i)+"年",
                    value:year-i
                });
            }
            for(var i=0;i<12;i++){
                mdata.push({
                    label:(i+1)+"月",
                    value:i+1
                });
            }

            $('#businesstb .year').combobox({
                valueField: 'value',
                textField: 'label',
                value:year,
                data:data
            });

            $('#businesstb .month').combobox({
                valueField: 'value',
                textField: 'label',
                value:month,
                data:mdata
            });

            var paramfunc=function (params){
                var statustype=processstatustype.ok;
                for(var item in processstatustype){
                    if(options.title.indexOf(processstatustype[item])>0){
                        statustype=processstatustype[item];
                        break;
                    }
                }
                var timestr='time';
                if(statustype===processstatustype.ok){
                    timestr='time'
                }else if(statustype===processstatustype.change){
                    timestr='changedate';
                }else{
                    timestr='logoutdate';
                }
                params.name=params.name?params.name.concat([timestr,'processstatustype']):[timestr,'processstatustype'];
                params.compare=params.compare?params.compare.concat(['month','=']):['month','='];

                var value=[$('#businesstb .year').combobox('getValue')+"-"+
                    ($('#businesstb .month').combobox('getValue')<10?'0'+
                        $('#businesstb .month').combobox('getValue'):$('#businesstb .month').combobox('getValue')),statustype];
                params.value=params.value?params.value.concat(value):value;
                params.logic =params.logic?params.logic.concat(['and','and']):['and','and'];
            }
            js.initbusinessgrid(type,businesstype,null,paramfunc);

        });

    }

    return {
        render: render

    };
});