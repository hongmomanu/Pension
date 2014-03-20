define(function()
{
    function render(parameters,res)
    {
        /*上传照片*/
        $('#personimg').click(function () {
            $('#imgwin').window('open');
        });

        $('#imgwin_cancel').bind('click', function () {
            $('#imgwin').window('close');
        });
        $('#imgwin_submit').bind('click', function () {
            require(['jqueryplugin/jquery-form'],function(AjaxFormjs){
                var success=function(data, jqForm, options)
                {
                    $('#personimg').attr('src', data.filepath);
                    $('#imgwin').window('close');
                };
                var options = {
                    //beforeSubmit:  showRequest,  // pre-submit callback
                    dataType:"json",
                    success: success,  // post-submit callback
                    timeout:   3000
                };
                $('#personimg_form').ajaxForm(options).submit() ;

            });
        });

        /*身份证号验证*/
         require(['commonfuncs/PersonidValidator'],function(PersonidValidator){
             $.extend($.fn.validatebox.defaults.rules, {
                 personid: {
                     validator: PersonidValidator.IdentityCodeValid,
                     message: '身份证不合法,请确认身份证是否正确输入!'
                 }
             });
         })

        /*家庭成员添加与删除*/
        $('#newfamilymemer_btn').bind('click', function () {
            $('#familymembersgrid').datagrid('appendRow', {name: '', relationship: '其它'});
            var editIndex = $('#familymembersgrid').datagrid('getRows').length;
            $('#familymembersgrid').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);



            /*require(['familygridfieldsbinds'], function (familygridfieldsbinds) {
                familygridfieldsbinds.personidbind(editIndex);
                familygridfieldsbinds.namebind(editIndex);
                familygridfieldsbinds.isenjoyedbind(editIndex);
            });*/

           /* $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);*/

        });

        $('#delfamilymemer_btn').bind('click', function () {

            if(!$('#delfamilymemer_btn').linkbutton('options').disabled){
                var selectrow= $('#familymembersgrid').datagrid('getSelected');
                var index=$('#familymembersgrid').datagrid('getRowIndex',selectrow);
                $('#familymembersgrid').datagrid('deleteRow', index);
                $('#delfamilymemer_btn').linkbutton('disable');
              /*  $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);*/
            }

        });
    }



    return {
        render: render
    };

});