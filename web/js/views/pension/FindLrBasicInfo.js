/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
define(function(){
    function render(local,parameters,res){
        dd=local
        console.log(parameters);
         /*$('#test').bind('click',function(){
             alert("hello");
         })*/
        /* local.find(a[opt=search])
        $('#search').bind('click',function(){*/
        local.find('a[opt=search]').bind('click',function(){                                      //查询功能
             //var searchname = $('#lrname').val();
            var searchname =  local.find(':input[opt=lrname]').val();                        //获取查询条件
            /*var click = "click";*/
            //$('#lrxxid').datagrid({
            local.find('[opt=lrxxid]').datagrid({                                                        //获取查询结果，填充到datagrid表单中
                url:'ajax/searchLrBasicInfo.jsp' ,
                queryParams:{
                    lrname:searchname
                    /* ,                      ifchick:click*/
                }
            });

        });

        local.find('a[opt=edit]').bind('click',function(){
        /*$('#edit').bind('click',function(){*/
            var getrow = local.find('[opt=lrxxid]').datagrid('getSelected');
            if(getrow!=null){
               /* alert("edit"+getrow.name);*/
                $.ajax({
                    url:'lr.do?model=hzyl.PensionPeopleInfoEdit&eventName=editLrbasicInfo',
                    data:{
                        peopleid:getrow.peopleid
                    },
                    type:'post',
                    success:function(res){
                        if(res){
                            require(['commonfuncs/TreeClickEvent'],function(ShowContent){
                                var obj={
                                    htmfile:'text!views/pension/EditLrBasicInfo.htm',
                                    jsfile:'views/pension/EditLrBasicInfo',
                                    title:'修改老年基本信息',
                                    type:'',
                                    res: eval('('+res+')')
                                };
                                ShowContent.ShowContent(obj) ;
                                /*ShowContent.ShowContent('text!views/pension/EditLrBasicInfo.htm','views/pension/EditLrBasicInfo','修改老年基本信息','',
                                    eval('('+res+')')
                                );*/
                            })
                        }
                    }
                })

            }else{
                alert("没选中任何行！");
            }
        })
    }

    return{
        render:render
    }

})
