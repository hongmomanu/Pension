
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
                 identityid: {
                     validator: PersonidValidator.IdentityCodeValid,
                     message: '身份证不合法,请确认身份证是否正确输入!'
                 }
             });

         })

        /*家庭成员添加与删除*/
       /* $('#newfamilymemer_btn').bind('click', function () {
            $('#familymembersgrid').datagrid('appendRow', {name: '', relationship: '其它'});
            var editIndex = $('#familymembersgrid').datagrid('getRows').length-1;
            $('#familymembersgrid').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);

            *//*require(['familygridfieldsbinds'], function (familygridfieldsbinds) {
                familygridfieldsbinds.personidbind(editIndex);
                familygridfieldsbinds.namebind(editIndex);
                familygridfieldsbinds.isenjoyedbind(editIndex);
        });*//*

           *//* $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);*//*

        });*/

        $('#newfamilymemeradd_btn').bind('click', function () {
            $('#familymembersgrid').datagrid('appendRow', {name: "", relationship: ""});
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
            var lrid = $("input[name='lr_id']").val();
            var gxmess = JSON.stringify($('#familymembersgrid').datagrid('acceptChanges').datagrid('getData').rows);
            if(lrid!=null&&lrid!=""){
                $('#pensionform').form('submit',{
                    url:'lr.do?model=hzyl.PensionPeopleInfo&eventName=update',
                    onSubmit:function(param){
                        var isValid = $('#pensionform').form('validate');
                        param.p1 = gxmess;
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
            }else{
                $('#pensionform').form('submit',{
                    url:'lr.do?model=hzyl.PensionPeopleInfo&eventName=save',
                    onSubmit:function(param){
                        var isValid = $('#pensionform').form('validate');
                        param.p1 = gxmess;
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
            }


        })

        //根据身份证号初始化出生年月，性别，年龄
        $('#identityid').change(function()
        {
            require(['views/pension/ShowBirthDay'], function (ShowBirthDay)
            {
                var sex_birth = ShowBirthDay.showBirthday($('#identityid').val()) ;
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

        $('#setdaytime').datebox('setValue',myformatter(new Date()));     //设置填表时间

        $('#familymembersgrid').datagrid({                     //点击行进行编辑
            onDblClickRow:function(rowIndex,rowData){
                   $(this).datagrid('beginEdit',rowIndex);
            }
        })


        if(res&&res!="pension")               //填充表单
        {
            $('#pensionform').form('load',res);
            var lr_id = res.lr_id;
            //alert(lr_id);
            $.ajax({
                url:"lr.do?model=hzyl.PensionPeopleInfo&eventName=setGxDate",
                data:{
                    lr_id:lr_id
                },
                type:"post",
                success:function(suc){
                    if(suc){
                        //console.log(suc);
                        //console.log(eval('('+suc+')'))  ;
                        //console.log( $('#familymembersgrid'));
                        $('#familymembersgrid').datagrid('loadData',eval('('+suc+')'));               //eval

                    }else{

                    }
                }

            })
        }else{

        }


    }



    return {
        render: render
    };

})