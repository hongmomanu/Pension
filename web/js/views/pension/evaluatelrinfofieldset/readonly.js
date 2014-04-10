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

            result1.find(':input[name=sum_cz_pingguf]').val(res.gx_pingguf)
            result1.find(':input[name=sum_zf_pingguf]').val(res.gx_pingguf)
            result1.find(':input[name=sum__pingguf]').val(res.gx_pingguf)
        }
    }

    return infoEvents;

})