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
        initPGY:function(){
            $('input[name$=_pingguy]').each(function(i){
                if($(this).val().length==0){
                    $(this).val(username);
                }
            })
        },
        initHiddenOthers:function(){
            $('.xuanfuhtml a').bind('click',function(){
                $('#mainform>fieldset').hide();
                $('#mainform>fieldset[id='+$(this).attr('href').substr(1)+']').show();
                $('#info0').show();
            })
        },
        syncRadioToResult:function(selection,value){
            $('#result1 '+selection).each(function(i){
                if($(this).prev().val()==value){
                    var p=$(this).parent().parent();
                    p.find(selection).each(function(){
                        $(this).prev()[0].checked = false;
                        $(this).removeClass("checked");
                    })
                    $(this).prev()[0].checked = true;
                    $(this).addClass("checked");
                    return;
                }
            })
        },
        info1:function(){
            var me=this;
            $('#info1').find(':input[type=radio]+label').each(function(i){
                $(this).bind('click',function(){

                    $($(this).parent().parent().children().last().children()[0]).val(($(this).prev()).val());
                    var sh_zongf=0;
                    $('#info1').find(':input[opt=info1pingfeng]').each(function(){
                        $(this).attr( 'disabled','disabled');
                        sh_zongf+=Number($(this).val())
                    })
                    $(':input[name=sh_zongf]').attr('disabled','disabled').val(sh_zongf)
                    $(':input[name=sh_pingguf]').val(sh_zongf)
                    $('#result1 :input[name=sum_sh_pingguf]').val(sh_zongf)
                    if($(this).prev().attr('name')=='sh_jiel'){
                        var v=$(this).prev().val()
                        me.syncRadioToResult(':input[name=sum_sh_jiel]+label',v);
                        //'
                    }
                })
            })
        },
        info2:function(){
            var me=this;
            $('#info2').find(':input[name=jj_shour]+label').each(function(i){
                $(this).bind('click',function(){
                    var v=$(this).prev().val();
                    $(':input[name=jj_pingguf]').val(v)
                    $('#result1 :input[name=sum_jj_pingguf]').val(v)
                    me.syncRadioToResult(':input[name=sum_jj_shour]+label',v);
                })
            })
        },
        info3:function(){
            var me=this;
            $('#info3').find(':input[name=jz_fenl]+label').each(function(i){
                $(this).bind('click',function(){
                    var v=$(this).prev().val();
                    $(':input[name=jz_pingguf]').val(v)
                    $('#result1 :input[name=sum_jz_pingguf]').val(v)
                    me.syncRadioToResult(':input[name=sum_jz_fenl]+label',v);
                })
            })
        },
        info4:function(){
            var me=this;
            $('#info4').find(':input[name=nl_fenl]+label').each(function(i){
                $(this).bind('click',function(){
                    var v=$(this).prev().val();
                    $(':input[name=nl_pingguf]').val(v)
                    $('#result1 :input[name=sum_nl_pingguf]').val(v) //同步
                    me.syncRadioToResult(':input[name=sum_nl_fenl]+label',v);
                })
            })
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
                    var nl=new Date().getFullYear()-date.getFullYear();
                    p.find("[opt=xynl]").val(nl);

                    var nldf=0;
                    if(nl<80){
                        nldf=0;
                    }else if(nl>=90){
                        nldf=2;
                    }else{
                        nldf=1;
                    }
                    p.find(":input[type=radio] + label").each(function(){
                        $(this).prev()[0].checked = false;
                        $(this).removeClass("checked");
                    })
                    var label=$(p.find(":input[type=radio] + label")[nldf])
                    label.addClass("checked").prev()[0].checked=true;
                    var v=$(label.prev()[0]).val();
                    p.find(":input[name=nl_pingguf]").val(v);
                    $('#result1 :input[name=sum_nl_pingguf]').val(v) //同步
                    me.syncRadioToResult(':input[name=sum_nl_fenl]+label',v);
                }
            });
        },
        info5:function(){
            var me=this;
            var checks=[];
            $('#info5').find(':input[type=checkbox]+label').each(function(i){
                checks.push($(this));
                $(this).bind('click',function(){
                    var sum=0;
                    for(var i=0;i<checks.length;i++){
                        if(checks[i].prev()[0].checked){
                            sum+=Number(checks[i].prev().val())
                        }
                    }
                    $(':input[name=gx_pingguf]').val(sum)
                    $('#result1 :input[name=gx_pingguf]').val(sum)
                })
            })
        }
    }
    var a=function(){
        require(fields,function(){
            for(var i=0;i<arguments.length;i++){
                $('#mainform').append(arguments[i]);
            }
            $('#tabs').tabs('getSelected').cssRadio();//渲染单选样式
            $('#tabs').tabs('getSelected').cssCheckBox();//渲染多选样式
            infoEvents.initPGY();
            infoEvents.info1();
            infoEvents.info2();
            infoEvents.info3();
            infoEvents.info4();
            infoEvents.info5();
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