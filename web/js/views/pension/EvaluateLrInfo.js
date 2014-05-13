define(function(){

    var fieldset=[
        {filehtml:'info0',title:'养老人员基本信息'},
        {filehtml:'info1',title:'生活自理能力'},
        {filehtml:'info2',title:'经济条件'},
        {filehtml:'info3',title:'居住环境'},
        {filehtml:'info4',title:'年龄情况'},
        {filehtml:'info5',title:'特殊贡献'},
        {filehtml:'info6',title:'残障情况'},
        {filehtml:'info7',title:'住房环境'},
        {filehtml:'info8',title:'重大疾病'},
        {filehtml:'result1',title:'评估部分计算'},
        {filehtml:'result2',title:'评估报告确认'}
    ]
    var fields=(function(){
        var arr=[];
        for(var i=0;i<fieldset.length;i++){
            var filehtml='text!views/pension/evaluatelrinfofieldset/'+fieldset[i].filehtml+".htm";
            arr.push(filehtml)
            var title=fieldset[i].title;
        }

        return arr;
    })()
    var initPGY=function(local){
        local.find('input[name$=_pingguy]').each(function(i){
            if($(this).val().length==0){
                $(this).val(username);
            }
        })
    }
    var initHiddenOthers=function(local){
        local.find('.xuanfuhtml a').bind('click',function(){
            $('#mainform>fieldset').hide();
            $('#mainform>fieldset[id='+$(this).attr('href').substr(1)+']').show();
            $('#info0').show();
        })
    }
    var initPage=function(local,f){
        local.find('div[opt=formcontentpanel]').panel({
            onResize:function(width, height){
                $(this).height($(this).height()-30);
                local.find('div[opt=form_btns]').height(30);
            }
        });

        require(fields,function(){
            for(var i=0;i<arguments.length;i++){
                local.find('form[opt=mainform]').append(arguments[i]);
            }
            //$.parser.parse(local.find('form[opt=mainform]'));//渲染
            if(f){
                f();
            }
        })
    }
    var initIdentityidandOtherComboxGrid=function(local,option){
        window.setTimeout(function(){

            local.find(':input[opt=identityid]').combogrid({
                panelWidth:350,
                panelHeight:400,
                url: 'ajax/getLrBasicInfo.jsp',
                idField:'owerid',
                textField:'owerid',
                validType:'personid',
                mode:'remote',
                fit:true,
                pagination:true,
                pageSize:15,
                pageList: [15, 30,50],
                border:false,
                fitColumns:true,
                onBeforeLoad: function(param){
                    var options =local.find(':input[opt=identityid]').combogrid('options');
                    if(param.q!=null){
                        param.query=param.q;
                    }
                },
                onClickRow:function(index,row){
                    $.ajax({
                        url:'lr.do?model=pension.EvaluateLrInfo&eventName=findLrBaseInfo',
                        data:{
                            lr_id:row.lr_id
                        },
                        type:'post',
                        success:function(res){
                            if(res){
                                var ores= eval('('+res+')');
                                local.find('form[opt=mainform]').form('load',ores);
                                local.find('[opt=csrq]').datebox('setValue',ores.birthd);
                            }else{
                                alert('无数据')
                            }

                        }
                    })
                },
                columns:[[
                    {field:'name',title:'姓名',width:20},
                    {field:'peopleid',title:'id',width:50},
                    {field:'place',title:'地址',width:50}
                ]]
            });
        },1000);

        require(['commonfuncs/division'],function(js){
            js.initDivisionWidget(
                local.find(":input[opt=registration]"),
                function(index,row){
                    $(':input[name=address]').val(row.totalname);
                })
        })
    }
    var a=function(mylocal,option){
        local=mylocal;
        Loption=option;
        initPage(local,function(){
            local.cssRadio(true);//渲染单选样式
            local.cssCheckBox();//渲染多选样式
            require(['views/pension/evaluatelrinfofieldset/edit',
                'views/pension/evaluatelrinfofieldset/readonly'],function(js1,js2){
                var js=js1;
                js.info0(local);
                js.info1(local);
                js.info2(local);
                js.info3(local);
                js.info4(local);
                js.info5(local);
                js.info6(local);
                js.info7(local);
                js.info8(local);
                js.result1(local);
            })
            initPGY(local); //初始化评估员
            initHiddenOthers(local);
            initIdentityidandOtherComboxGrid(local);
        });


        local.find('a[action=save]').bind('click',function(){
            require(['commonfuncs/Htmldb'],function(js){
                local.find('form[opt=mainform]').form('submit',{
                    url:lr.url(option,'save'),
                    onSubmit: function(param){
                        var isValid = $(this).form('validate');
                        if(isValid){
                            param.originalpage=js.parse(local)
                        }
                        return isValid;

                    },
                    success: function(res){
                        cj.ifSuccQest(res,"已操作成功是否关闭此页",function(){
                            require(['commonfuncs/TreeClickEvent'],function(js){
                                js.closeCurrentTab();
                            })
                        })
                    }
                })
            })

        })

    }
    var b=function(mylocal,option){
        console.log(option)
        ll=mylocal;
        local=mylocal;
        initPage(local,function(){
            var loadform=function(l,o){
                l.find('form[opt=mainform]').form('load',o.res.formdata)
                l.cssRadioOnly();
                l.cssCheckBoxOnly();
                require(['views/pension/evaluatelrinfofieldset/edit',
                    'views/pension/evaluatelrinfofieldset/readonly'],function(js1,js2){
                    var js=js2;
                    js.info1(l);

                    js.info4(l);

                    js.result1(l,o.res.formdata);
                    js.result2(l,o.res.formdata);
                    js.info0(l, o.res.formdata);
                })
            }
            if(!option.res){
                console.log(option)
                $.ajax({
                    url:lr.url(option,'queryById'),
                    data:{id:option.tprkey},
                    success:function(restext){
                        var res=eval('('+restext+')')
                        option.res=res;
                        loadform(local,option);
                    }
                })
            }else{
                loadform(local,option);
            }
        });
    }

    return {
        render:a,
        readonly:b
    }
})