/**
 * Created by jack on 13-12-31.
 */
define(['views/MainTree','commonfuncs/LoadingMask','commonfuncs/TabRightClick','commonfuncs/FullCalendar'],
    function(MainTree,LoadingMask,TabRightClick,FullCalendar){

    function start(){
        addAccordion();
        TabRightClick.init();
        window.setTimeout(function(){$('#currentDate').text(FullCalendar.RunGLNL())},1000);
    }
    function addAccordion(){
        var menu_pension=$('#menu_pension');
        menu_pension.accordion({
            onSelect:function(title,index){
                var panel=$(this).accordion('getPanel',index);
                if(panel.attr('wasSelected')){
                    return;
                }
                panel.attr('wasSelected',true);
                $(panel).append('<ul class="easyui-tree"></ul>');
                require(['views/AccordionTree'],function(js){
                    js.render(panel)
                })
            }

        });

        var getAccordion=function(res){
            var isselected=true;
            $(eval('('+res+')')).each(function(i){
                var me=$(this);
                if(me.attr('leaf'))return;//如果是叶子,则没有这个抽屉

                menu_pension.accordion('add', {
                    title: me.attr('text'),
                    selected: isselected
                }).children(':last').attr('functionid',me.attr('id'));
                if(isselected){
                    isselected=false;//第一个叶子默认打开
                }
            })

        }
        //加载抽屉
        $.ajax(
            {
                url:'lr.do?model=manager.Function&eventName=queryFunctionTree',
                success:getAccordion
            }
        )

    }


    return {
        start:start
    };
});
