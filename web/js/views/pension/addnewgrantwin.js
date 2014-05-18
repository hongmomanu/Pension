define(function () {
    var gridInit=function(index){

        var date = new Date();
        var month=date.getMonth()+1;
        $(':input[name=bsnyue]').val(date.getFullYear()+(month>9?month:'0'+month));


        var gridid='.grantmoneyperson'+index+' [name=grid]';
        var btnid='.grantmoneypersonquery'+index+' [name=querybtn]';
        var query_textid='.grantmoneypersonquery'+index+' [name=query_text]';
        var persongrid=$(gridid);
        var fn=function(){persongrid.datagrid('load')}
        $(btnid).on('click',fn);
        $(query_textid).on( "keydown", function( event ) {
            if(event.which=='13'){
                fn();
            }
        });
        $('#money').bind('keydown',function(event){
            if(event.which=='13'){
                var rows=persongrid.datagrid('getData').rows;
                for(var i in rows){
                    rows[i].money=$(this).val();
                }
                console.log(rows)
                persongrid.datagrid('loadData',rows)
            }
        })

        var editIndex = undefined;
        function endEditing(){
            if (editIndex == undefined){return true}
            if (persongrid.datagrid('validateRow', editIndex)){
                persongrid.datagrid('endEdit', editIndex);
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }
        function onClickCell(index, field){
            if (endEditing()){
                persongrid.datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
                editIndex = index;
            }
        }

        var obj={
            onClickCell: onClickCell,
            url:'lr.do?model=pension.GrantMoneyMng&eventName=queryNeedGrant',
            onBeforeLoad: function (params) {
                 params.bsnyue=$(':input[name=bsnyue]').val();
                 params.querytype=$(querytype).val();
                params.keyword=$('[name=query_text]').val();
            }
        }
        persongrid.datagrid(obj);
    }
    var comboxInit=function(){
        $('#querytype').combobox({
            valueField: 'value', textField: 'label',readonly:!true,
            data: [{ label: '全部', value: '0' },{ label: '未发放', value: '1' },{ label: '已发放', value: '2' },{ label: '已到账', value: '3' }]
        });
    }
    var grantmoney=function(granttype){
        var rows=$('#addnewgrantwin .grantmoneyperson0 [name=grid]').datagrid('getChecked');
        var grantdata=[];
        for( var i in rows){

           grantdata.push({
               pg_id:rows[i].pg_id,
               money:rows[i].money||0
           })
        }
        var ajaxdata={
            granttype:granttype,
            money:$('#money').val(),
            bsnyue:$('#addnewgrantwin :input[name=bsnyue]').val()
        }
        if(grantdata.length>0){
            ajaxdata.grantdata=JSON.stringify(grantdata);
        }
        $.ajax({
            url:'lr.do?model=pension.GrantMoneyMng&eventName=save',
            data:ajaxdata,
            type:'post',
            success:function(){
                $('#addnewgrantwin .grantmoneyperson0 [name=grid]').datagrid('reload');
            }
        })
    }
    return {

        render:function(local){
            require(['text!views/pension/addnewgrantwin.htm'],function(windiv){
                var $grantwin=$('#addnewgrantwin');
                if($grantwin==null||$grantwin.length==0){
                    $('body').append(windiv);
                    $grantwin=$('#addnewgrantwin');
                    $grantwin.find('.easyui-tabs').tabs({'onSelect':function(title,index){
                        gridInit(index);
                    }});
                    comboxInit();
                    $grantwin.dialog({
                        title: '资金发放',
                        width: 590,
                        height: 470,
                        closed: false,
                        cache: false,
                        onOpen:function(){ },
                        onClose:function(){$grantwin.dialog('destroy');},
                        buttons: [
                            { text: '资金发放', isnew: true,
                                handler: function () {
                                    grantmoney('normalgrant');
                                }
                            },
                            { text: '重新发放', isnew: true,
                                handler: function () {
                                    grantmoney('regrant');
                                }
                            },
                            { text: '取消', handler: function () {
                                $grantwin.dialog('destroy');
                            }
                            }
                        ],
                        maximized:false,
                        modal:true
                    });
                    $grantwin.dialog('open');
                }

            })
        }
    }
})