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
        })
    }
    var a=function(local,option){
        initPage(local,option);
    }

    return {
        render:a
    }
})