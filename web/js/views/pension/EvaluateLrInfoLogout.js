define(function(){
    var fieldset=[
        {filehtml:'info0',title:'养老人员基本信息'},
        {filehtml:'logoutfieldset',title:'注销'}
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
            //$.parser.parse(local.find('form[opt=mainform]'))
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
        require(['commonfuncs/CommToolBar'],function(tb){
            local.find('[opt=form_btns]').append(new tb([{
                text:'保存',
                iconCls:'icon-save',
                handler:function(){
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
                }
            },{
                text:'查看日志',
                iconCls:'icon-log',
                action:'searchlog'
            }]))
        })
    }

    return {
        render:a
    }
})