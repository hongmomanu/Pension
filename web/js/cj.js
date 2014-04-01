/**
 * Created by admin on 3/31/14.
 */

var cj = {

    version: '1.0',
    defaultTitle: '提示',
    ajaxaSynchronous: false,


    calert:function(res){
        var d=eval('('+res+')');
        if(d.success==true||d.success=='true'){
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