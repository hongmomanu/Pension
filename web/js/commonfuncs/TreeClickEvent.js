define(function(){

    var mainTab=$('#tabs');
    var a={
        ShowContent:function(option){
            require([option.htmfile,option.jsfile],function(htm,js){
                var title=option.title;
                if(mainTab.tabs('exists',title)){
                    mainTab.tabs('select', title);
                    return;
                }
                var getLocaltab = function () {
                    return mainTab.tabs('add', {
                        title: title,
                        content: htm,
                        closable: true
                    }).tabs('getTab', title);
                }

                if(option.readonly==true){
                    if(js.readonly){
                        js.readonly(getLocaltab(),option)
                    }else{
                        alert('未实现readonly接口')
                    }
                }else{
                    js.render(getLocaltab(),option)
                }

            })
        },
        ShowIframe:function(value,jsfile,title,customparam){
            var require_render=function(jsfile){
                if(mainTab.tabs('exists',title)){
                    mainTab.tabs('select', title);
                    return;
                }
                mainTab.tabs('add', {
                    title: title,
                    content: '<iframe src="' + value + '" width="100%" height="100%" frameborder="0"></iframe>',
                    closable: true
                });
            };
            require_render(jsfile);
        },
        closeCurrentTab:function(){
            var pp = mainTab.tabs('getSelected');
            var index = mainTab.tabs('getTabIndex',pp);
            mainTab.tabs('close',index);
        },
        closeTabByTitle:function(t){
            mainTab.tabs('close',t);
        },
        test:function(t){
            alert(t)
        }


    }

    return a;
});
