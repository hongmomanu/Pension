
define(function()
{
    function render(local,option)
    {
        vv=local
        var res= option.res;


        var familymembersgrid =   local.find('[opt=familymembersgrid]');      //获取家庭成员列表的对象


        /*身份证号验证*/
        require(['commonfuncs/PersonidValidator'],function(PersonidValidator){
            $.extend($.fn.validatebox.defaults.rules, {
                identityid: {
                    validator: PersonidValidator.IdentityCodeValid,
                    message: '身份证不合法,请确认身份证是否正确输入!'
                }
            });

        })

        /*家庭成员列表添加新成员*/
        local.find('a[opt=newfamilymemeradd_btn]').bind('click', function () {
            familymembersgrid.datagrid('appendRow', {name: "", relationship: ""});
            var editIndex = familymembersgrid.datagrid('getRows').length-1;
            familymembersgrid.datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        });

        /*删除选中的家庭成员*/
        local.find('a[opt=delfamilymemer_btn]').bind('click', function () {
            var selectrow= familymembersgrid.datagrid('getSelected');
            var index=familymembersgrid.datagrid('getRowIndex',selectrow);
            familymembersgrid.datagrid('deleteRow', index);

        });

        /*提交修改内容*/
        local.find('a[opt=pensionsubmit]').bind('click',function(){
        /*$('#pensionsubmit').bind('click',function(){*/
            var lrid = $("input[name='lr_id']").val();                                                 //获取唯一值
            var gxmess = JSON.stringify(familymembersgrid.datagrid('acceptChanges').datagrid('getData').rows);
            if(lrid!=null&&lrid!=""){
                local.find('form[opt=pensionform]').form('submit',{
                    url:'lr.do?model=hzyl.PensionPeopleInfoEdit&eventName=update',
                    onSubmit:function(param){
                        var isValid = local.find('form[opt=pensionform]').form('validate');
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
            }/*else{
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
            }*/


        })

        /*根据身份证号初始化出生年月，性别，年龄*/
        local.find(':input[opt=identityid]').change(function()
        {
            require(['views/pension/ShowBirthDay'], function (ShowBirthDay)
            {
                var sex_birth = ShowBirthDay.showBirthday(local.find(':input[opt=identityid]').val()) ;
                if(sex_birth.birthday){
                    local.find(':input[opt=birthdate]').datebox('setValue',sex_birth.birthday) ;
                    local.find(':input[opt=sex]').combobox('setValue',sex_birth.sex) ;
                    local.find(':input[opt=age]').val(sex_birth.age);
                }
            })
        })

        local.find(':input[opt=setdaytime]').datebox('setValue',myformatter(new Date())); //设置填表时间


        familymembersgrid.datagrid({                     //点击行进行编辑
            onDblClickRow:function(rowIndex,rowData){
                $(this).datagrid('beginEdit',rowIndex);
            }
        })


        if(res&&res!="pension")               //填充表单
        {
            local.find('form[opt=pensionform]').form('load',res);
            var lr_id = res.lr_id;
            $.ajax({
                url:"lr.do?model=hzyl.PensionPeopleInfoEdit&eventName=setGxDate",
                data:{
                    lr_id:lr_id
                },
                type:"post",
                success:function(suc){
                    if(suc){
                        familymembersgrid.datagrid('loadData',eval('('+suc+')'));               //eval

                    }else{

                    }
                }

            })
        }else{

        }

        local.find('div[opt=pensioneditformpanel]').panel({
            onResize:function(width, height){
                $(this).height($(this).height()-30);
                local.find('div[opt=pensionbutton]').height(30);
            }
        });


    }



    return {
        render: render
    };

})