/**
 * Created by weipan on 3/31/14.
 */

var cj=(function(){
    var Enums={};
    var getEnum=function(searchtype){
        var enumObj=Enums[searchtype];
        if(enumObj){
            return enumObj;
        }else{
            var res=$.ajax({
                url:'cb.do?rows=-1',
                type:'post',
                async: false,
                data:{searchtype:searchtype}
            }).responseText;
            var d = eval('(' + res + ')');
            if (!!d && (d.success == true || d.success == 'true')) {
                Enums= d.rows;  //一次性全部加载过来
                return Enums[searchtype];
            }
        }

    }
    var enfmt=function(ef){
        return function(value,recode,index){
            for (var i = 0; i < ef.length; i++) {
                if (ef[i].id == value) {
                    return ef[i].text;
                }
            }
        }
    }
    var commonj = {
        version: '1.0',
        defaultTitle: '提示',
        ajaxaSynchronous: false,
        calert: function (res) {
            var d = eval('(' + res + ')');
            if (!!d && (d.success == true || d.success == 'true')) {
                $.messager.alert(cj.defaultTitle, '操作成功!', 'info');
            } else {
                $.messager.alert(cj.defaultTitle, '操作失败!', 'info');
            }
        },
        question: function (info, fun, title) {
            var boxtitle = cj.defaultTitle;
            if (title) {
                boxtitle = title;
            }
            $.messager.confirm(title, info, function (r) {
                if (r) {
                    fun()
                }
            });
        },

        ifSuccQest: function (res, info, fun, title) {
            var d = eval('(' + res + ')');
            if (d.success == true || d.success == 'true') {
                cj.question(info, fun, title)
            }
        },
        getByteLen: function (val) {
            var len = 0;
            for (var i = 0; i < val.length; i++) {
                if (val[i].match(/[^x00-xff]/ig) != null) //全角
                    len += 2;
                else
                    len += 1;
            }
            ;
            return len;
        },
        enumFormatter:function(ename,f){
            return enfmt(getEnum(ename))
        }
    }
    return commonj;
})()



jQuery.fn.cssRadio = function () {
    var me = ($(this))
    var selectRadio = ":input[type=radio] + label";
    $(selectRadio).each(function () {
        if ($(this).prev()[0].checked)
            $(this).addClass("checked"); //初始化,如果已经checked了则添加新打勾样式
    }).click(function () {               //为第个元素注册点击事件
            var s = $($(this).prev()[0]).attr('name')
            s = ":input[name=" + s + "]+label"
            me.find(s).each(function (i) {
                $(this).prev()[0].checked = false;
                $(this).removeClass("checked");
            });
            $(this).prev()[0].checked = true;
            $(this).addClass("checked");
        })
        .prev().hide();     //原来的圆点样式设置为不可见
};
jQuery.fn.cssCheckBox = function () {
    $(":input[type=checkbox] + label").each(function () {
        if ($(this).prev()[0].checked) {
            $(this).addClass("checked");
        }
    }).toggle(function () {
            $(this).prev()[0].checked = true;
            $(this).addClass("checked");
        },
        function () {
            $(this).prev()[0].checked = false;
            $(this).removeClass("checked");
        }).prev().hide();
}