define( function () {

    function render(parameters,res) {
        /**这里添加事件**/

        if(res){
            var affixfileitem=$('.affixfile');
            for(var j=0;j<affixfileitem.length;j++){
                var affix=res.affixfile;
                for(var i=0;i<affix.length;i++){
                    if(affix[i].attachmenttype==$(affixfileitem[j]).attr("type")){
                        affixfileitem[j].formdata=affix[i].results;
                        (function(item,data){
                            require(['commonfuncs/UpdateItemNum'],function(UpdateItemNum){
                                UpdateItemNum.updateitemnum(item,
                                    data,"(",")");
                            });
                        })($(affixfileitem[j]),affix[i].results.length);

                        break;
                    }
                }
            }

        }

        $('#uploadaffixdialog').bind('change',function(){
            var filename= $(this).val().slice($(this).val().lastIndexOf("\\")+1);
            $('#uploadaffixname').val(filename);
        });
        $('.affixfile').unbind('click').click(function(){
            //console.log(this);

            $('#affixwin').window('open');
            $('#affixwin').window('window').clickitem=this;
            var data=$('#affixwin').window('window').clickitem.formdata?
                $('#affixwin').window('window').clickitem.formdata:[];
            $('#affixfilegrid').datagrid('loadData',data);
            $('#affixfilegrid').datagrid({
                onClickRow:function(rowIndex, rowData){
                    $('#affixwin_del').linkbutton('enable');
                }

            });
            $('#affixwin_del').unbind('click').click(function(){
                var rows = $('#affixfilegrid').datagrid('getSelections');
                $.messager.progress();
                for(var i=0;i<rows.length;i++){
                    var index=$('#affixfilegrid').datagrid('getRowIndex',rows[i]);
                    $('#affixfilegrid').datagrid('deleteRow',index);

                }
                $.messager.progress('close');
                $('#affixwin_del').linkbutton('disable');


            });
            $('#affixfilegrid').datagrid('getPanel').find('.easyui-tooltip').each(function(){
                var index = parseInt($(this).attr('data-p1'));
                $(this).tooltip({
                    content: $('<div></div>'),
                    onUpdate: function(cc){
                        var row = $('#affixfilegrid').datagrid('getRows')[index];
                        var content = '<div><img width="200" height="190" src="'+row.attachmentpath+'"></div>';

                        cc.panel({
                            width:200,
                            height:200,
                            content:content
                        });
                    },
                    position:'right'
                });
            });


        });
        $('#affixwin_confirm').unbind('click').click(function(){

            $('#affixwin').window('window').clickitem.formdata=$('#affixfilegrid').datagrid('getRows');
            require(['commonfuncs/UpdateItemNum'],function(UpdateItemNum){
                UpdateItemNum.updateitemnum($($('#affixwin').window('window').clickitem),
                    $('#affixfilegrid').datagrid('getRows').length,"(",")");
                $('#affixwin').window('close');
            });
        });
        $('#affixwin_submit').unbind('click').click(function () {
            require(['jqueryplugin/jquery-form'],function(AjaxFormjs){
                var success=function(data, jqForm, options)
                {
                    $('#affixfilegrid').datagrid('appendRow',{
                        attachmentname: data.filename,
                        attachmentpath:data.filepath
                    });
                };
                var options = {
                    //beforeSubmit:  showRequest,  // pre-submit callback
                    dataType:"json",
                    success: success,  // post-submit callback
                    timeout:   3000
                };
                $('#affixwinimg_form').ajaxForm(options).submit() ;

            });
        });

        $('#affixwin_cancel').unbind('click').click(function () {
            $('#affixwin').window('close');
        });

    }

    return {
        render: render
    };
});