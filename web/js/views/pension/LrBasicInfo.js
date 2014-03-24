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
            var editIndex = $('#familymembersgrid').datagrid('getRows').length-1;
            $('#familymembersgrid').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);



            /*require(['familygridfieldsbinds'], function (familygridfieldsbinds) {
                familygridfieldsbinds.personidbind(editIndex);
                familygridfieldsbinds.namebind(editIndex);
                familygridfieldsbinds.isenjoyedbind(editIndex);
            });*/

           /* $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);*/

        });

        $('#newfamilymemer_btn1').bind('click', function () {
            $('#familymembersgrid').datagrid('appendRow', {name: '', relationship: '其它'});
            var editIndex = $('#familymembersgrid').datagrid('getRows').length-1;
            $('#familymembersgrid').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        });

        $('#delfamilymemer_btn').bind('click', function () {

            var selectrow= $('#familymembersgrid').datagrid('getSelected');
            var index=$('#familymembersgrid').datagrid('getRowIndex',selectrow);
            $('#familymembersgrid').datagrid('deleteRow', index);

            /*if(!$('#delfamilymemer_btn').linkbutton('options').disabled){
                var selectrow= $('#familymembersgrid').datagrid('getSelected');
                var index=$('#familymembersgrid').datagrid('getRowIndex',selectrow);
                $('#familymembersgrid').datagrid('deleteRow', index);
                $('#delfamilymemer_btn').linkbutton('disable');
                $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);
            }*/

        });


        /*提交表单*/
        /*$('#pensionsubmit').form('submit',{
            url:'',
            onSubmit:function(){
                return $(this).form('validate');
            },
            success:function(data){
                $.message.show({
                    title:"成功",
                    msg:data
                });
            }
        });*/

        $('#pensionsubmit').bind('click',function(){
            /*alert("click");*/
            $('#pensionform').form('submit',{
                url:'lr.do?model=hzyl.PensionPeopleInfo&eventName=save',
                onSubmit:function(){
                    var isValid = $('#pensionform').form('validate');
                    return isValid;
                },
                success:function(data){
                    var obj=eval('('+data+')')
                    if(obj.success){
                        var tab=$('#tabs');
                        var pp = tab.tabs('getSelected');
                        var index = tab.tabs('getTabIndex',pp);
                        tab.tabs('close',index);
                    }

                }
            })

        })

        //根据身份证号初始化出生年月，性别，年龄
        $('#ownerid').change(function()
        {
            require(['views/pension/ShowBirthDay'], function (ShowBirthDay)
            {
                var sex_birth = ShowBirthDay.showBirthday($('#ownerid').val()) ;
                if(sex_birth.birthday){
                    $('#birthdate').datebox('setValue',sex_birth.birthday) ;
                    $('#sex').combobox('setValue',sex_birth.sex) ;
                    $('#age').val(sex_birth.age);
                    /*$(birthday.target).val(sex_birth.birthday);
                    $(sex.target).val(sex_birth.sex);
                    $(sex.target).combobox('setValue',sex_birth.sex);
                    $(age.target).val((new Date()).getFullYear()-parseInt(sex_birth.birthday.split("-")[0]));*/
                }
            })
        })



    }



    return {
        render: render
    };

})