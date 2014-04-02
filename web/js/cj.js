/**
 * Created by admin on 3/31/14.
 */

var cj = {

    version: '1.0',
    defaultTitle: '提示',
    ajaxaSynchronous: false,


    calert:function(res){
        var d=eval('('+res+')');
        if(!!d&&(d.success==true||d.success=='true')){
            $.messager.alert(cj.defaultTitle,'操作成功!','info');
        }else{
            $.messager.alert(cj.defaultTitle,'操作失败!','info');
        }
    },
    question:function(info,fun,title){
        var boxtitle = cj.defaultTitle;
        if(title){
            boxtitle = title;
        }
        $.messager.confirm(title,info,function(r){
            if (r){
                fun()
            }
        });
    },

    ifSuccQest:function(res,info,fun,title){
        var d=eval('('+res+')');
        if(d.success==true||d.success=='true'){
            cj.question(info,fun,title)
        }
    }


}


jQuery.fn.cssRadio = function () {
    var a=function(){
        var selectRadio=":input[type=radio] + label";
        $(selectRadio).each(function () {
            if ($(this).prev()[0].checked)
                $(this).addClass("checked");
        }).click(function () {
                var contents = $(this).parent().parent();
                $(selectRadio, contents).each(function () {
                    $(this).prev()[0].checked = false;
                    $(this).removeClass("checked");
                });
                $(this).prev()[0].checked = true;
                $(this).addClass("checked");
            }).prev().hide();
    }

    return a();

};