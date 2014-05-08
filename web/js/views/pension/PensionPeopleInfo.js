
define(function()
{
    function render(local,parameters,res)
    {
        dd = local;
        console.log(parameters);
        var familymembersgrid =   local.find('[opt=familymembersgrid]');                  //家庭成员列表的对象
         // local.find(':input[opt=identityid]')
        /*上传照片*/
        /*$('#personimg').click(function () {
            $('#imgwin').window('open');
        });*/
       /* local.find(':img[opt=personimg]').click(function () {
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
        });*/

        /*身份证号验证*/
         require(['commonfuncs/PersonidValidator'],function(PersonidValidator){           //身份证号验证
             $.extend($.fn.validatebox.defaults.rules, {
                 identityid: {
                     validator: PersonidValidator.IdentityCodeValid,
                     message: '身份证不合法,请确认身份证是否正确输入!'
                 }
             });

         })


        local.cssCheckBox();

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
              // local.find(':input[opt=identityid]')
        /*$('#newfamilymemeradd_btn').bind('click', function () {
            $('#familymembersgrid').datagrid('appendRow', {name: "", relationship: ""});
            var editIndex = $('#familymembersgrid').datagrid('getRows').length-1;
            $('#familymembersgrid').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        });*/


        local.find('a[opt=newfamilymemeradd_btn]').bind('click', function () {         //成员信息表添加
            familymembersgrid.datagrid('appendRow', {name: "", relationship: ""});
            var editIndex = familymembersgrid.datagrid('getRows').length-1;
            familymembersgrid.datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        });

        /*$('#delfamilymemer_btn').bind('click', function () {

            var selectrow= $('#familymembersgrid').datagrid('getSelected');
            var index=$('#familymembersgrid').datagrid('getRowIndex',selectrow);
            $('#familymembersgrid').datagrid('deleteRow', index);*/

        local.find('a[opt=delfamilymemer_btn]').bind('click', function () {               //成员信息表删除

            var selectrow= familymembersgrid.datagrid('getSelected');
            var index=familymembersgrid.datagrid('getRowIndex',selectrow);
            familymembersgrid.datagrid('deleteRow', index);

            /*if(!$('#delfamilymemer_btn').linkbutton('options').disabled){
                var selectrow= $('#familymembersgrid').datagrid('getSelected');
                var index=$('#familymembersgrid').datagrid('getRowIndex',selectrow);
                $('#familymembersgrid').datagrid('deleteRow', index);
                $('#delfamilymemer_btn').linkbutton('disable');
                $('#FamilyPersons').val($('#familymembersgrid').datagrid('getRows').length);
            }*/

        });



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
        /*提交表单*/

        /*$('#pensionsubmit').bind('click',function(){*/
            /*alert("click");*/
        local.find('a[opt=pensionsubmit]').bind('click',function(){
            //var lrid = $("input[name='lr_id']").val();       //判断是新添加的还是修改的
            var gxmess = JSON.stringify(familymembersgrid.datagrid('acceptChanges').datagrid('getData').rows);
            /*if(lrid!=null&&lrid!=""){
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
             }else{ */
             /* local.find('[opt=pensionform]')
             $('#pensionform').form('submit',{*/


            require(['commonfuncs/Htmldb'],function(js){
                local.find('[opt=pensionform]').form('submit',{                                        //保存提交
                    url:'lr.do?model=pension.PensionPeopleInfo&eventName=save',            //将数据传递到后台进行保存
                    onSubmit:function(param){
                        param.p1 = gxmess;                                                                    //将家庭成员数据独立传递
                        param.functionid = parameters.functionid;
                        param.originalpage=js.parse(local)
                        var isValid = local.find('[opt=pensionform]').form('validate');        //传送数据先进性数据验证
                        console.log(isValid);
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
        })


        /*$('#identityid').change(function()
        {
            require(['views/pension/ShowBirthDay'], function (ShowBirthDay)
            {
                var sex_birth = ShowBirthDay.showBirthday($('#identityid').val()) ;
                if(sex_birth.birthday){
                    $('#birthdate').datebox('setValue',sex_birth.birthday) ;
                    $('#sex').combobox('setValue',sex_birth.sex) ;
                    $('#age').val(sex_birth.age);*/
        local.find(':input[opt=identityid]').change(function()                                   //根据身份证号初始化出生年月，性别，年龄
        {
            require(['views/pension/ShowBirthDay'], function (ShowBirthDay)
            {
                var sex_birth = ShowBirthDay.showBirthday(local.find(':input[opt=identityid]').val()) ;
                if(sex_birth.birthday){
                    local.find(':input[opt=birthdate]').datebox('setValue',sex_birth.birthday) ;
                    local.find(':input[opt=sex]').combobox('setValue',sex_birth.sexcode) ;
                    local.find(':input[opt=age]').val(sex_birth.age);

                    /*$(birthday.target).val(sex_birth.birthday);
                    $(sex.target).val(sex_birth.sex);
                    $(sex.target).combobox('setValue',sex_birth.sex);
                    $(age.target).val((new Date()).getFullYear()-parseInt(sex_birth.birthday.split("-")[0]));*/
                }
            })
        })

       /* $('#setdaytime').datebox('setValue',myformatter(new Date()));*/
        local.find(':input[opt=setdaytime]').datebox('setValue',myformatter(new Date())); //设置填表时间


        familymembersgrid.datagrid({                                                                    //点击行进行编辑
            onDblClickRow:function(rowIndex,rowData){
                   $(this).datagrid('beginEdit',rowIndex);
            }
        })


/*        if(res&&res!="pension")               //填充表单
        {
            *//*$('#pensionform').form('load',res);*//*
            local.find(':form[opt=pensionform]').form('load',res);
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
                        familymembersgrid.datagrid('loadData',eval('('+suc+')'));               //eval

                    }else{

                    }
                }

            })
        }else{

        }*/

        //上传头像
        /*$('#personimg').click(function() {
            $('#imgwin').window('open');
        });
        $('#imgwin_cancel').bind('click', function(){
            $('#imgwin').window('close');
        });*/

       /* local.find('[opt=personimg]').click(function(){
            alert(22222)
            $('#imgwin').window('open');
        }) ;
        $('#imgwin_cancel').bind('click',function(){
            $('#imgwin').window('close');
        });
        $('#imgwin_submit').bind('click', function () {
            require(['jqueryplugin/jquery-form'],function(AjaxFormjs){
                var success=function(data, jqForm, options)
                {
                    alert('getin');
                    local.find('[opt=personimg]').attr('src', data.filepath);
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
        });*/


        local.find('[opt=personimg]').click(function(){                                            //图片上传
            require(['commonfuncs/Upload'],function(up){
                up.show(
                    function(data){
                        alert(99) ;
                        var a = eval('('+data+')');
                        local.find('[opt=personimg]').attr('src', a.filepath) ;
                        local.find('[name=pensionimgpath]').val(a.filepath) ;
                    }
                )
            })
        }) ;


        local.find('div[opt=pensionformpanel]').panel({                                            //固定按钮
            onResize:function(width, height){
                $(this).height($(this).height()-30);
                local.find('div[opt=form_btns]').height(30);
            }
        });


        /*require(['commonfuncs/division'],function(js){                                  //行政区划
            js.initDivisionWidget(
                local.find(":input[name=districtid]") ,
                function(index,row){
                    })

        })*/
        var divisiontree = local.find(':input[opt=divisiontree]') ;                               //行政区划的树结构

        divisiontree.combotree({
            url:'ajax/gettreedivisionnew.jsp?onlychild=true&node=330000',
            method: 'get',
            onLoadSuccess:function(load,data){
                if(!this.firstloaded&&!res){
                    divisiontree.combotree('setValue', data.divisionpath);
                    this.firstloaded=true;
                }
            },
            onBeforeExpand: function (node) {
                divisiontree.combotree("tree").tree("options").url
                    = "ajax/gettreedivisionnew.jsp?onlychild=true&node=" + node.parentid;
            },
            onHidePanel: function () {
                divisiontree.combotree('setValue',
                    divisiontree.combotree('tree').tree('getSelected').divisionpath);
            }
        });


        local.find(':input[opt=operators]').val(username);




    }



    return {
        render: render
       /* readonly:function(local,option){
            render(local,option)
            local.find('[opt=form_btns]').fadeOut();
        }*/
    };

})