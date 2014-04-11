/**
 * Created by weipan on 3/31/14.
 */

var cj=(function(){
    var pz=17;
    var dataGridAttr={ pageSize:pz, pageList: [pz, 30,50]}
    var Enums={};
    var singleFun=function(){
        var result={};
        $.ajax({
            url:'cb.do?rows=-1',
            type:'post',
            data:{searchtype:'no1'},
            success:function(res){
                var d = eval('(' + res + ')');
                if (!!d && (d.success == true || d.success == 'true')) {
                    Enums= d.rows
                    return Enums;  //一次性全部加载过来
                }
            }
        })
        return result;
    }
    window.setTimeout(singleFun,1000);
    var getEnumlower=function(searchtype){
        return Enums[searchtype];
    }
    var enfmt=function(ef){
        return function(value,recode,index){
            if(ef){
                for (var i = 0; i < ef.length; i++) {
                    if (ef[i].id == value) {
                        return ef[i].text;
                    }
                }
                return value;
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
        getDataGridAttr:function(name){
            return dataGridAttr[name]
        },
        enumFormatter:function(ename,f){
            return enfmt(getEnumlower(ename.toLowerCase()))
        },
        getEnum:function(ename){
            return getEnumlower(ename.toLowerCase())
        },
        showContent:function(option){

            var f=function(show,load){
                var js=show;
                if(option.res){  //已经有数据
                    show.closeTabByTitle(option.title);
                    show.ShowContent(option)
                }else{
                    if(option.useproxy==false){ //不使用代理,根据id来传递数据,开发人员根据id来自行加载数据
                        show.closeTabByTitle(option.title);
                        show.ShowContent(option)
                    }else{                      //使用代理
                        load.load(option,function(res){
                            option.res=eval('('+res+')');
                            show.closeTabByTitle(option.title);
                            show.ShowContent(option)
                        })
                    }
                }
            }

            require(['commonfuncs/TreeClickEvent','commonfuncs/LoadFromData'],f)
        }
    }
    return commonj;
})()


jQuery.fn.cssRadioOnly = function (){
    var selectRadio = ":input[type=radio] + label";
    $(selectRadio).each(function () {
        if ($(this).prev()[0].checked)
            $(this).addClass("checked");
    }).prev().hide();
}
jQuery.fn.cssCheckBoxOnly = function () {
    var selectCheck = ":input[type=checkbox] + label";
    $(selectCheck).each(function () {
        if ($(this).prev()[0].checked) {
            $(this).addClass("checked");
        }
    }).prev().hide();
}
jQuery.fn.cssRadio = function (toggle) {
    var me = ($(this))
    var selectRadio = ":input[type=radio] + label";
    $(selectRadio).each(function () {
        if ($(this).prev()[0].checked)
            $(this).addClass("checked"); //初始化,如果已经checked了则添加新打勾样式
    }).click(function () {               //为第个元素注册点击事件
            var s = $($(this).prev()[0]).attr('name')
            s = ":input[name=" + s + "]+label"
            var isChecked=$(this).prev()[0].checked;
            me.find(s).each(function (i) {
                $(this).prev()[0].checked = false;
                $(this).removeClass("checked");
            });
            if(isChecked&&toggle){
                //如果单选已经为选中状态并且参数为true,则什么都不做
            }else{
                $(this).prev()[0].checked = true;
                $(this).addClass("checked");
            }

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


