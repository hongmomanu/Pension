define(function(){

    var fieldset=[
        /*{filehtml:'text!views/pension/LrBasicInfo.htm',
            filejs:'views/pension/LrBasicInfo',
            title:'养老人员基本信息',refOther:true},*/
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
    var a=function(){
        require(fields,function(){
            for(var i=0;i<arguments.length;i++){
                $('#mainform').append(arguments[i]);
            }
        })

        for(var i=100;i<fieldset.length;i++){
            var filehtml='text!views/pension/evaluatelrinfofieldset/'+fieldset[i].filehtml+".htm";
            var title=fieldset[i].title;
            if(fieldset[i].refOther){
                filehtml=fieldset[i].filehtml;
            }

            (function(h,t){
                require([h],function(htmlfile){
                    /*$('#EvaluateLrInfo').tabs('add', {
                        title: t,
                        content: htmlfile
                    });*/
                    $('#mainform').append(htmlfile);
                })
            })(filehtml,title)
        }

        window.setTimeout(function(){
            $('#identityid').combogrid({
                panelWidth:430,
                panelHeight:400,
                url: 'ajax/getLrBasicInfo.jsp',
                idField:'owerid',
                textField:'owerid',
                validType:'personid',
                mode:'remote',
                fitColumns:true,
                pagination:true,
                onBeforeLoad: function(param){
                    var options = $('#identityid').combogrid('options');
                    if(param.q!=null){
                        param.query=param.q;
                    }
                },
                onClickRow:function(index,row){
                    $.ajax({
                        url:'lr.do?model=Test&eventName=editLrbasicInfo',
                        data:{
                            peopleid:row.peopleid
                        },
                        type:'post',
                        success:function(res){
                            if(res){
                                $('#mainform').form('load',eval('('+res+')'));
                            }else{
                                alert('无数据')
                            }

                        }
                    })
                },
                columns:[[
                    {field:'name',title:'姓名',width:20},
                    {field:'peopleid',title:'id',width:60},
                    {field:'place',title:'地址',width:100}
                ]]
            });
        },1000);


        $('#save').bind('click',function(){
            $('#mainform').form('submit',{
                url:'lr.do?model=hzyl.EvaluateLrInfo&eventName=save',
                success: function(res){
                   cj.ifSuccQest(res,"已操作成功是否关闭此",function(){
                       require(['commonfuncs/TreeClickEvent'],function(js){
                           js.closeCurrentTab();
                       })
                   })
                }
            })
        })

    }

    return {
        render:a
    }
})