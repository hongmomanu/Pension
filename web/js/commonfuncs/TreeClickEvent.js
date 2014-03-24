define(function(){

    var a={
        ShowContent_0:function(htmlfile,jsfile,title,value,folder,res,id,businesstype){
            require(['commonfuncs/LookupItemName','commonfuncs/LoadingMask'],function(LookupItemName,LoadingMask){
                LoadingMask.ajaxLoading();
                var require_render=function(htmlfile,jsfile){

                    var options= {
                        title: title,
                        content: htmlfile,
                        id:id,
                        businesstype:businesstype,
                        closable: true
                    };
                    if($('#tabs').tabs('exists',1)){

                        $('#tabs').tabs('select', 1);
                        $('#tabs').tabs('close',1);

                    }

                    $('#tabs').tabs('add',options);
                    var lookupname=LookupItemName.lookupitemname(formwidgettype,value);
                    if(lookupname){
                        var firstform=applyformviews[lookupname][0];
                        firstformhtml='text!'+folder+firstform+'.htm';
                        firstformjs=folder+firstform;
                        require([firstformhtml,firstformjs],function(firstformhtml,firstformjs){
                            $('#mainform').append(firstformhtml);
                            firstformjs.render($('#mainform').children()[0],res);
                            jsfile.render(lookupname,folder,LoadingMask,res);
                            LoadingMask.ajaxLoadEnd();
                        });
                    }
                    else{
                        LoadingMask.ajaxLoadEnd();

                        jsfile.render(lookupname,folder,LoadingMask,res);
                    }

                    //jsfile.render(lookupname,folder,LoadingMask,res);
                    jsfile.render();

                };
                require([htmlfile,jsfile],require_render);


            });


        },
        ShowContent:function(htmlfile,jsfile,title,value,customparam){
            var require_render=function(htmlfile,jsfile){

                if($('#tabs').tabs('exists',title)){
                    $('#tabs').tabs('select', title);
                    return;
                }

                $('#tabs').tabs('add', {
                    title: title,
                    content: htmlfile,
                    closable: true,
                    tools: [
                        {
                            iconCls: 'icon-mini-refresh',
                            handler: function () {
                                alert('refresh');
                            }
                        }
                    ]
                });
                jsfile.render()
            };
            require([htmlfile,jsfile],require_render);
        },
        ShowIframe:function(value,jsfile,title,customparam){
            var require_render=function(jsfile){
                if($('#tabs').tabs('exists',title)){
                    $('#tabs').tabs('select', title);
                    return;
                }
                $('#tabs').tabs('add', {
                    title: title,
                    content: '<iframe src="' + value + '" width="100%" height="100%" frameborder="0"></iframe>',
                    closable: true,
                    tools: [
                        {
                            iconCls: 'icon-mini-refresh',
                            handler: function () {
                                alert('refresh');
                            }
                        }
                    ]
                });
            };
            require_render(jsfile);
        }

    }

    return a;
});
