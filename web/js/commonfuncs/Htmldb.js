/*根据当前页面元素和easyui插件的值生成页面保存到后台*/

define(function(){
    var parse=function(local){
        var selections='.easyui-combobox';
        var eobj={};

        local.find('textarea,:input').each(function(){    //获得元素input的value
            if($(this).val()){
                eobj[$(this).attr('name')]=$(this).val();
            }
        })

        local.find(selections).each(function(){
            var comboname=$(this).attr('comboname');
            if(comboname){
                eobj[comboname]=$(this).combo('getText');
            }
        })

        var myclone=$(local.find('div.panel-body')[0]).clone();
        myclone.find(selections).each(function(){   //删除easyui样式
            $(this).removeAttr('data-options').css('display','inline')
                .removeClass('easyui-combobox').attr('comboname');
        })
        myclone.find('input').each(function(){
            $(this).removeClass('combogrid-f')
            $(this).removeClass('combo-f')
            $(this).removeClass('easyui-validatebox')
        })
        myclone.find('textarea,:input').each(function(){      //为input元素赋值
            var name=$(this).attr('name');
            var tagName=$(this).prop('tagName');
            if(tagName=="INPUT"){
                $(this).attr('value',eobj[name]);
            }else if(tagName=="TEXTAREA"){
                $(this).text(eobj[name]);
            }

            var comboname=$(this).attr('comboname')
            if(comboname){
                $(this).attr('value',eobj[comboname]);
            }
        })
        myclone.find('span[class=combo]').remove();
       /* $('#tabs').tabs('add',{
            title:'原始页面',
            content:myclone.html(),
            closable:true
        })*/
        console.log(eobj)
        return myclone.html();
    }

    return {parse:parse}
})