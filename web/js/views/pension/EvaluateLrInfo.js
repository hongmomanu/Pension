
define(function(){
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
    }

    return {
        render:a
    }
})