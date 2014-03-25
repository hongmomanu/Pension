define(function(){
    var fieldset=[
        {filehtml:'info0',title:'养老人员基本信息'},
        {filehtml:'info1',title:'生活自理能力'},
        {filehtml:'info2',title:'经济条件'},
        {filehtml:'info3',title:'居住环境'},
        {filehtml:'info3',title:'年龄情况'},
        {filehtml:'info3',title:'特殊贡献'},
        {filehtml:'info3',title:'残障情况'},
        {filehtml:'info3',title:'重大疾病'},
        {filehtml:'info3',title:'居住环境'},
        {filehtml:'result1',title:'评估部分计算'},
        {filehtml:'result2',title:'评估报告确认'}


    ]
    var a=function(){

        $('#submit').bind('click',function(){
            $('#pinggu').form('submit',{
                url:'lr.do?model=hzyl.EvaluateLrInfo&eventName=save',
                onSubmit: function(param){
                    param.p1 = 'value1';
                    param.p2 = 'value2';
                },
                success:function(data){
                    $.messager.alert('Info', data, 'info');
                }
            });
        })

        for(var i=0;i<fieldset.length;i++){
            var filehtml='text!views/pension/evaluatelrinfofieldset/'+fieldset[i].filehtml+".htm";
            var title=fieldset[i].title;
            (function(h,t){
                require([h],function(htmlfile){
                    $('#EvaluateLrInfo').tabs('add', {
                        title: t,
                        content: htmlfile
                    });
                })
            })(filehtml,title)

        }
    }

    return {
        render:a
    }
})