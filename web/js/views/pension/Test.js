define(function(){
    return {
        render:function(local,option){
            local.find('div[opt=formcontentpanel]').panel({
                onResize:function(width, height){
                    $(this).height($(this).height()-30);
                    local.find('div[opt=form_btns]').height(30);
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
    }
})