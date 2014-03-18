define(function(){
    $('#newfamilymemer_btn').bind('click', function () {
        $('#familymembersgrid').datagrid('appendRow', {name: '', relationship: '其它'});
        var editIndex = $('#familymembersgrid').datagrid('getRows').length - 1;
        $('#familymembersgrid').datagrid('selectRow', editIndex)
            .datagrid('beginEdit', editIndex);



        require(['views/dbgl/familygridfieldsbinds'], function (familygridfieldsbinds) {
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
})