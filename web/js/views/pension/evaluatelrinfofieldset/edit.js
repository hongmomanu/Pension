define(function(){
    var infoEvents={

        syncRadioToResult:function(local,selection,value){
            local.find('[opt=result1]').find(selection).each(function(i){
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
        info0:function(local){     //手动渲染combox组件
            local.find('fieldset[opt=info0]').find('.easyui-combobox,.easyui-validatebox').each(function(){
                $.parser.parse($(this).parent())
            })
        },
        info1:function(local){
            var me=this;
            local.find('fieldset[opt=info1]')
                .find(':input[type=radio]+label').each(function(i){
                    $(this).bind('click',function(){
                        var radioValue=0;
                        if($(this).prev()[0].checked){
                            radioValue= ($(this).prev()).val();
                            $($(this).parent().parent().children().last().children()[0]).val(radioValue);
                        }else{
                            $($(this).parent().parent().children().last().children()[0]).removeAttr("value");
                        }
                        var sh_zongf=0;
                        local.find('fieldset[opt=info1]').find(':input[opt=info1pingfeng]').each(function(){
                            $(this).attr( 'disabled','disabled');
                            sh_zongf+=Number($(this).val())
                        })

                        local.find(':input[name=sh_zongf]').attr('disabled','disabled').val(sh_zongf)
                        local.find(':input[name=sh_pingguf]').val(sh_zongf)

                        local.find('fieldset[opt=result1]').find(':input[name=sum_sh_pingguf]').val(sh_zongf)
                        if($(this).prev().attr('name')=='sh_jiel'){
                            var v=$(this).prev().val()
                            me.syncRadioToResult(local,':input[name=sum_sh_jiel]+label',v);
                        }
                    })
                })
        },
        info2:function(local){
            var me=this;
            local.find('[opt=info2] :input[name=jj_shour]+label').each(function(i){
                $(this).bind('click',function(){
                    var v=$(this).prev().val();
                    local.find(':input[name=jj_pingguf]').val(v)
                    local.find('fieldset[opt=result1] :input[name=sum_jj_pingguf]').val(v)
                    me.syncRadioToResult(local,':input[name=sum_jj_shour]+label',v);
                })
            })
        },
        info3:function(local){
            var me=this;
            local.find('fieldset[opt=info3] :input[name=jz_fenl]+label').each(function(i){
                $(this).bind('click',function(){
                    var v=$(this).prev().val();
                    local.find(':input[name=jz_pingguf]').val(v)
                    local.find('fieldset[opt=result1] :input[name=sum_jz_pingguf]').val(v)
                    me.syncRadioToResult(local,':input[name=sum_jz_fenl]+label',v);
                })
            })
        },
        info4:function(local){
            var me=this;
            var info4= local.find('fieldset[opt=info4]');
            info4.find(':input[name=nl_fenl]+label').each(function(i){
                $(this).bind('click',function(){
                    var v=$(this).prev().val();
                    local.find(':input[name=nl_pingguf]').val(v)
                    local.find('fieldset[opt=result1] :input[name=sum_nl_pingguf]').val(v) //同步
                    me.syncRadioToResult(local,':input[name=sum_nl_fenl]+label',v);
                })
            })
            var onSelectOrChange=function(t,date){
                var p=t.parent().parent().parent();
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
                local.find('[opt=result1] :input[name=sum_nl_pingguf]').val(v) //同步
                me.syncRadioToResult(local,':input[name=sum_nl_fenl]+label',v);
            }
            info4.find(':input[opt=csrq]')
                .datebox({
                    required:true,
                    onSelect:function(date){
                        onSelectOrChange($(this),date);
                    }
                });
        },
        info5:function(local){
            var me=this;
            var checks=[];
            var info5=local.find('fieldset[opt=info5]');
            var result1= local.find('[opt=result1]');
            info5.find(':input[type=checkbox]+label').each(function(i){
                checks.push($(this));
                $(this).bind('click',function(){
                    var sum=0;

                    for(var i=0;i<checks.length;i++){
                        if(checks[i].prev()[0].checked){
                            sum+=Number(checks[i].prev().val())
                        }

                    }
                    info5.find(':input[name=gx_pingguf]').val(sum)
                    //同步到result1
                    result1.find(':input[name=sum_gx_pingguf]').val(sum);
                    var ischecked= $(this).prev()[0].checked;
                    var sum_gx_label=result1.find(':input[name=sum_'+$(this).prev()[0].name+']+label');
                    if(ischecked){
                        $(sum_gx_label).prev()[0].checked = true;
                        $(sum_gx_label).addClass("checked");
                    }else{
                        $(sum_gx_label).prev()[0].checked = false;
                        $(sum_gx_label).removeClass("checked");
                    }
                })
            })
        },
        info6:function(local){
            var radioLables=[];
            local.find('fieldset[opt=info6] :input[type=radio]+label').each(function(i){
                radioLables.push($(this))
                $(this).bind('click',function(){
                    var text='';
                    for(var i=0;i<radioLables.length;i++){
                        var title=$(radioLables[i].parent().parent().parent().parent().children()[0]).text()
                        if(radioLables[i].prev()[0].checked){
                            text+='-'+title+' : '+ radioLables[i].text()+'-';
                        }
                    }
                    local.find('fieldset[opt=result1] :input[opt=canzhangqingkuang]').val(text)
                })
            })
        },
        info7:function(local){
            var radioLables=[];
            var getMessage=function(){
                var text='';
                for(var i=0;i<radioLables.length;i++){
                    if(radioLables[i].prev()[0].checked){
                        var title=$(radioLables[i].parent().children()[0]).text().substr(2);
                        var youwu=radioLables[i].parent().children('label[class=checked]').text();
                        var tao=radioLables[i].parent().children(':input[class=pingfen]').val();
                        tao=tao?tao+'套':'';
                        text+='<'+title+youwu+tao+'>';
                    }
                }
                var qita= local.find('fieldset[opt=info7] :input[name=zf_qit]');
                if(qita){
                    text+='<其他'+qita.val()+'>';
                }
                local.find('fieldset[opt=result1] :input[opt=zhufangqingkuang]').val(text)
            }
            local.find('fieldset[opt=info7] :input[type=radio]+label').each(function(i){
                radioLables.push($(this))
                $(this).bind('click',getMessage)
                $(this).next(':input').bind('change',getMessage)
                $(this).parent().parent().find(':input[name=zf_qit]').bind('change',getMessage)
            })
        },
        info8:function(local){

            var info=local.find('fieldset[opt=info8]')
            info.find('ol:first>li').css({'line-height':'21px'});
            zhongdajibing=info.find('tr[opt=zhongdajibing]');
            var titles=[];
            var qks=['','医生诊断','在接受治疗','结束治疗'];
            $(zhongdajibing.children()[0]).find('li').each(function(){
                titles.push($(this).text())
            })
            for(var i=1;i<zhongdajibing.children().length;i++){
                $(zhongdajibing.children()[i]).find('li').each(function(index){
                    $(this).attr('opt1',i).attr('opt2',index)
                    var title=titles[$(this).attr('opt2')] ;

                    $(this).find(':input+label').attr('title',title).attr('qk',qks[i]).bind('click',function(){
                        var sum_text='';
                        zhongdajibing.find(':input[type=radio]+label').each(function(){
                            if($(this).prev()[0].checked){
                                sum_text+=$(this).attr('title')+  $(this).attr('qk')
                            }
                        })
                        local.find('fieldset[opt=result1] textarea[opt=zhongdajibing]').val(sum_text)
                    })

                })
            }
        },
        result1:function(local){
            var result=local.find('fieldset[opt=result1]') ;
            var calculate=function(){
                var value=0;
                result.find('td>:input[class=pingfen]').each(function(){
                    value+=Number($(this).val())
                })
                result.find(':input[name=pinggusum]').val(value)
            }
            result.find('td>:input[class=pingfen]').each(function(){
                $(this).bind('change',calculate)
            })
            result.find('a[opt=recalculate]').bind('click',calculate)
        }
    }

    return infoEvents;

})