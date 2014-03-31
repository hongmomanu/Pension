define(function(){
    var fieldset=[
        {filehtml:'text!views/pension/LrBasicInfo.htm',
            filejs:'views/pension/LrBasicInfo',
            title:'养老人员基本信息',refOther:true},
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
    var a=function(){


        for(var i=0;i<fieldset.length;i++){
            var filehtml='text!views/pension/evaluatelrinfofieldset/'+fieldset[i].filehtml+".htm";
            var title=fieldset[i].title;
            if(fieldset[i].refOther){
                filehtml=fieldset[i].filehtml;
            }

            (function(h,t){
                require([h],function(htmlfile){
                    $('#EvaluateLrInfo').tabs('add', {
                        title: t,
                        content: htmlfile
                    });
                })
            })(filehtml,title)

        }
        window.setTimeout(function(){
            $('#ownerid').combogrid({
                panelWidth:420,
                url: 'ajax/getLrBasicInfo.jsp',
                idField:'owerid',
                textField:'owerid',
                validType:'personid',
                mode:'remote',
                fitColumns:true,
                pagination:true,
                onBeforeLoad: function(param){
                    var options = $('#ownerid').combogrid('options');
                    if(param.q!=null){
                        param.query=param.q;
                    }

                },
                onClickRow:function(index,row){
                    $('#pensionform').form('load',row);
                },
                columns:[[
                    {field:'name',title:'姓名',width:60},
                    {field:'peopleid',title:'id',width:80},
                    {field:'place',title:'地址',width:30}
                ]]
            });
        },5000);
        $('#EvaluateLrInfo').tabs({
            tools:[{
                iconCls:'bigSaveBtn',
                text:'保存',
                handler:function(){
                    $('#pensionform').form('submit',{
                        url:'lr.do?model=hzyl.EvaluateLrInfo&eventName=save',
                        onSubmit: function(param){
                            param.name = 'xx';
                            param.identityid = '341282198707084617';
                            param.registration = '33333';
                        },
                        success:function(data){
                            $.messager.alert('Info', data, 'info');
                        }
                    });
                }
            }]
        })

    }

    return {
        render:a
    }
})