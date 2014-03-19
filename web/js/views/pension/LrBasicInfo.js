define(function()
{
    function render(parameters,res)
    {
         require(['commonfuncs/PersonidValidator'],function(PersonidValidator){
             $.extend($.fn.validatebox.defaults.rules, {
                 personid: {
                     validator: PersonidValidator.IdentityCodeValid,
                     message: '身份证不合法,请确认身份证是否正确输入!'
                 }
             });
         })


        $('#newfamilymemer_btn').bind('click', function () {
            $('#familymembersgrid').datagrid('appendRow', {name: '', relationship: '其它'});
            var editIndex = $('#familymembersgrid').datagrid('getRows').length - 1;
            $('#familymembersgrid').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);



            require(['familygridfieldsbinds'], function (familygridfieldsbinds) {
                familygridfieldsbinds.personidbind(editIndex);
                familygridfieldsbinds.namebind(editIndex);
                familygridfieldsbinds.isenjoyedbind(editIndex);
            });

           /* $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);*/

        });

        $('#delfamilymemer_btn').bind('click', function () {

            if(!$('#delfamilymemer_btn').linkbutton('options').disabled){
                var selectrow= $('#familymembersgrid').datagrid('getSelected');
                var index=$('#familymembersgrid').datagrid('getRowIndex',selectrow);
                $('#familymembersgrid').datagrid('deleteRow', index);
                $('#delfamilymemer_btn').linkbutton('disable');
                $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);
            }

        });
    }



    return {
        render: render
    };

});