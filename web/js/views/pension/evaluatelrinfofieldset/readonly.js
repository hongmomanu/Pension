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


        result1:function(local,res){
            var result1=local.find('fieldset[opt=result1]');
            result1.find(':input[name=sum_sh_pingguf]').val(res.sh_pingguf)
            result1.find(':input[name=sum_jj_pingguf]').val(res.jj_pingguf)
            result1.find(':input[name=sum_jz_pingguf]').val(res.jz_pingguf)
            result1.find(':input[name=sum_nl_pingguf]').val(res.nl_pingguf)
            result1.find(':input[name=sum_gx_pingguf]').val(res.gx_pingguf)

            result1.find(':input[name=cz_pingguf]').val(res.cz_pingguf)
            result1.find(':input[name=zf_pingguf]').val(res.zf_pingguf)
            result1.find(':input[name=jb_pingguf]').val(res.jb_pingguf)
        },

        result2:function(local,res){
            var result2=local.find('fieldset[opt=result2]');
            result2.find(':input[name=jb_pingguf]').val(res.jb_pingguf)
            var pingguenddate=res.pingguenddate;
            if(pingguenddate){
                result2.find('[opt=pingguenddate]').text(pingguenddate.substr(0,pingguenddate.lastIndexOf('.')))
            }
            result2.find('[opt=pg_id]').text(res.pg_id)
        },
        info0:function(local){
            $.ajax({
                url:'lr.do?model=Test&eventName=editLrbasicInfo',
                data:{peopleid:'210303198412082729'},
                success:function(restext){
                    var res=eval('('+restext+')')
                    local.find('form').form('load',res)
                }
            })
        }
    }

    return infoEvents;

})