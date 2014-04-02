/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-24
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
define(function(){
    function render(parameters,res){
         /*$('#test').bind('click',function(){
             alert("hello");
         })*/

        $('#search').bind('click',function(){
             var searchname = $('#lrname').val();
            /*var click = "click";*/
            $('#lrxxid').datagrid({
                url:'ajax/searchLrBasicInfo.jsp' ,
                queryParams:{
                    lrname:searchname
                    /* ,                      ifchick:click*/
                }
            });

        });

        $('#edit').bind('click',function(){
            var getrow = $('#lrxxid').datagrid('getSelected');
            if(getrow!=null){
                alert("edit"+getrow.name);
                $.ajax({
                    url:'lr.do?model=Test&eventName=editLrbasicInfo',
                    data:{
                        peopleid:getrow.peopleid
                    },
                    type:'post',
                    success:function(res){
                        if(res){
                            require(['commonfuncs/TreeClickEvent'],function(ShowContent){
                                ShowContent.ShowContent('text!views/pension/LrBasicInfo.htm','views/pension/LrBasicInfo','修改老年基本信息','',
                                    eval('('+res+')')
                                );
                            })

                        }else{

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
