
define(function()
{
    function render(local,option)
    {
        vv=local
        var res= option.res;


        var familymembersgrid =   local.find('[opt=familymembersgrid]');      //获取家庭成员列表的对象




        local.cssCheckBox();

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
                    url:'lr.do?model=pension.PensionPeopleInfoEdit&eventName=update',
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
                    local.find(':input[opt=sex]').combobox('setValue',sex_birth.sexcode) ;
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
            var imgpath = res.pensionimgpath;
            var division = res.districtid;
            local.cssCheckBox();
            local.cssCheckBoxOnly();
            local.find('[opt=personimg]').attr('src', imgpath) ;

            $.ajax({
                url:'lr.do?model=pension.PensionPeopleInfoEdit&eventName=setGxDate',
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
            window.setTimeout(function(){
                local.find(':input[opt=divisiontree]').combotree('setValue',division);
            },500);


        }

        //底层固定按钮
        local.find('div[opt=pensioneditformpanel]').panel({
            onResize:function(width, height){
                $(this).height($(this).height()-30);
                local.find('div[opt=pensionbutton]').height(30);
            }
        });


        //图片上传
        local.find('[opt=personimg]').click(function(){
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

        //行政区划
        /*require(['commonfuncs/division'],function(js){
            js.initDivisionWidget(
                local.find(":input[name=districtid]") ,
                function(index,row){
            })
        })*/

        var divisiontree = local.find(':input[opt=divisiontree]') ;

        divisiontree.combotree({
            url:'ajax/gettreedivisionnew.jsp?onlychild=true&node=330000',
            method: 'get',
            onLoadSuccess:function(load,data){
                if(!this.firstloaded&&!option){
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

    }



    return {
        render: render
    };

})