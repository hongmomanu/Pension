define(function(){
    var fieldset=[
        {filehtml:'info0',title:'养老人员基本信息'},
        {filehtml:'change',title:'变更原因'},
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
            if(f){
                f();
            }
        })
    }
    var a=function(mylocal,option){
        local=mylocal;
        initPage(local,function(){
            var loadform=function(l,o){
                l.find('form[opt=mainform]').form('load',o.res)
                l.cssRadio(true);//渲染单选样式
                l.cssCheckBox();//渲染多选样式
                require(['views/pension/evaluatelrinfofieldset/edit',
                    'views/pension/evaluatelrinfofieldset/readonly'],function(js1,js2){
                    js1.info1(l);
                    js1.info2(l);
                    js1.info3(l);
                    js1.info4(l);
                    js1.info5(l);
                    js1.info6(l);
                    js1.info7(l);
                    js1.info8(l);
                    js1.result1(l);
                    
                    js2.info1(l);
                    js2.info4(l);
                    js2.result1(l,o.res);
                    js2.result2(l,o.res);
                    js2.info0(l, o.res);
                })
            }
            if(!option.res){
                $.ajax({
                    url:'lr.do?model=pension.EvaluateLrInfo&eventName=queryById',
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
        local.find('a[action=save]').bind('click',function(){
            require(['commonfuncs/Htmldb'],function(js){
                local.find('form[opt=mainform]').form('submit',{
                    url:lr.url(option,'save'),
                    onSubmit: function(param){
                        param.originalpage=js.parse(local)
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

    return {
        render:a
    }
})