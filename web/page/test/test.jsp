<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="button" value="ok">
<script>
    $("input[type='button']").bind('click',function(){
        $.ajax({
            url:'datagrid_data1.json',
            success:function(res){
                cj.question("yes",function(){
                    alert(1)
                })
            }
        })
    })
</script>