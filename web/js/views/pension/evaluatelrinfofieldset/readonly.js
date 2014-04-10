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
        info1:function(local){
            var me=this;
            var sh_zongf=0;
            local.find('fieldset[opt=info1]')
                .find(':input[type=radio]+label').each(function(){
                    if($(this).prev()[0].checked){
                        var radioValue= ($(this).prev()).val();
                        $($(this).parent().parent().children().last().children()[0]).val(radioValue);
                        sh_zongf+=Number(radioValue)
                    }
                })
            local.find(':input[name=sh_zongf]').attr('disabled','disabled').val(sh_zongf)
        },


        info4:function(local){
            //日期没有存储
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