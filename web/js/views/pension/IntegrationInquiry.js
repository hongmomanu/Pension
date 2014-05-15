define(function(){

    var fieldset=[
        //{filehtml:'info0',title:'养老人员基本信息'},
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
    var initPage=function(local,f){
        local.find('div[opt=formcontentpanel]').panel({
            onResize:function(width, height){
                $(this).height($(this).height()-35);
                $(this).find('.easyui-tabs').tabs('resize');
                local.find('div[opt=form_btns]').height(30);
            }
        });

        require(fields,function(){
            for(var i=0;i<arguments.length;i++){
                local.find('.easyui-tabs').tabs('getTab','人员基本信息').append(arguments[i]);
            }
            if(f){
                f();
            }
        })
    }
    var a=function(local){
        initPage(local,function(){
            local.cssRadio(true);//渲染单选样式
            local.cssCheckBox();//渲染多选样式
        })

        $('.xsearchbox').searchbox({
            searcher:function(value,name){
                alert(value + "," + name)
            },
            prompt:'Please Input Value'
        });
    }


    return {render:a}
})