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
    var infoEvents={
        initHiddenOthers:function(){
            $('.xuanfuhtml a').bind('click',function(){
                $('#mainform>fieldset').hide();
                $('#mainform>fieldset[id='+$(this).attr('href').substr(1)+']').show();
                $('#info0').show();
            })
        },
        info1:function(){
            $('#info1').find('input[type=radio]+label').each(function(i){
                $(this).bind('click',function(){
                    $($(this).parent().parent().children()
                        .last().children()[0]).val(($(this).prev()).val())
                })
            })
        },
        info4:function(){
            $(':input[opt=csrq]')
                .datebox({
                required:true,
                onSelect: function(date){
                    var p=$(this).parent().parent().parent();
                    var m= (date.getMonth()+1);
                    m=m>9?m:'0'+m;
                    var d= date.getDate();
                    d=d>9?d:'0'+d;
                    p.find("[opt=textymd]").text(
                        date.getFullYear()+"年"+m+"月"+d+"日(以身份证为准)"
                    );
                    p.find("[opt=xynl]").val(new Date().getFullYear()-date.getFullYear());
                    $(p.find(":input[type=radio] + label")[0]).addClass("checked").prev()[0].checked=true;
                }
            });
        }
    }
    var a=function(){
        require(fields,function(){
            for(var i=0;i<arguments.length;i++){
                $('#mainform').append(arguments[i]);
            }
            $('#tabs').tabs('getSelected').cssRadio();//渲染单选样式
            infoEvents.info1();
            infoEvents.info4();
            $('[opt=hiddenOthers]').bind('click',infoEvents.initHiddenOthers);
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
                panelWidth:350,
                panelHeight:400,
                url: 'ajax/getLrBasicInfo.jsp',
                idField:'owerid',
                textField:'owerid',
                validType:'personid',
                mode:'remote',
                fit:true,
                pagination:true,
                pageSize:15,
                pageList: [15, 30,50],
                border:false,
                fitColumns:true,
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
                    {field:'peopleid',title:'id',width:50},
                    {field:'place',title:'地址',width:50}
                ]]
            });
        },1000);

        require(['commonfuncs/division'],function(js){
                js.initDivisionWidget(":input[name=registration]",function(index,row){
                   $(':input[name=address]').val(row.parentname+row.dvname);
                    $(':input[name=registration]').combogrid('setValues', ['001','007']);
                })
        })
        $('#save').bind('click',function(){
            $('#mainform').form('submit',{
                url:'lr.do?model=hzyl.EvaluateLrInfo&eventName=save',
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

    return {
        render:a
    }
})