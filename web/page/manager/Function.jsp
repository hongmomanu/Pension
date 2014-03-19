<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>功能管理</title>
    <style>

        .formtable {
            width:100%;
            padding: 0px;
            margin: 0px;
            font-family:Arial, Tahoma, Verdana, Sans-Serif;
            border-left:1px solid #ADD8E6;
            border-top: 1px solid #ADD8E6;
            border-collapse:collapse;
        }



            /*单元格样式。*/
        .formtable td {
            border-right: 1px solid #ADD8E6;
            border-bottom: 1px solid #ADD8E6;

            /*background: #fff;*/

            font-size:12px;
            padding: 3px 3px 3px 6px;
            color: #303030;
            word-break:break-all;
            word-wrap:break-word;
            white-space:normal;
        }
            /*表头样式*/
        .formtable th {
            font-size:12px;
            font-weight:600;
            color: #303030;
            border-right: 1px solid #ADD8E6;
            border-bottom: 1px solid #ADD8E6;
            border-top: 1px solid #ADD8E6;
            letter-spacing: 2px;
            text-align: left;
            padding: 10px 0px 10px 0px;
            background: url(img/tablehdbg.png);
            white-space:nowrap;
            text-align:center;
            overflow: hidden;
        }
        .formtdtext {
            width: 13%;
        }


    </style>
</head>
<body>
<form id="form" method="post">
<div id="functionmng" class="easyui-layout" style="width:1024px;height:99%;margin: 0px auto;">
    <div data-options="region:'west',title:'功能树',split:false,lines:true" style="width:300px;">
        <ul id="functiontree"></ul>
    </div>

    <div data-options="region:'center',title:'功能详细信息'">


        <a href="#" class="easyui-linkbutton" id="newFun">新建</a>
        <a href="#" class="easyui-linkbutton" id="saveFun">保存</a>
        <a href="#" class="easyui-linkbutton" id="deleteFun">删除</a>
        <input type="hidden" name="functionid">
        <fieldset ><legend>功能</legend>
            <div>
                <table class="formtable">
                    <tr>
                        <td class="formtdtext">地址:</td>
                        <td colspan="3"><input name="location" size="70" data-options="required:true"></td>

                    </tr>
                    <tr>
                        <td class="formtdtext">中文名:</td>
                        <td><input type="text" searchtype="dbglapplytype" name="title"></td>
                        <td class="formtdtext">父节点:</td>
                        <td><input type="text" name="parent"></td>
                    </tr>
                    <tr>
                        <td class="formtdtext">序号:</td>
                        <td><input class="easyui-validatebox"  type="text" name="nodetype" name="orderno"></td>
                        <td class="formtdtext">节点类型:</td>
                        <td><input class="easyui-validatebox"  type="text" name="nodetype" data-options="required:true" value="0">(0叶子1节点)</td>

                    </tr>
                    <tr>
                        <td class="formtdtext">功能类型:</td>
                        <td><input class="easyui-validatebox" type="text" name="type" data-options="required:true" value="1">(0链接1组件)</td>
                        <td class="formtdtext">描述:</td>
                        <td><input class="easyui-validatebox" type="text" name="description" data-options="required:true" value="描述"></td>
                    </tr>
                    <tr>
                        <td class="formtdtext">是否日志:</td>
                        <td><input  type="text" name="log" value="1"></td>
                        <td class="formtdtext">开发者:</td>
                        <td><input  type="text" name="developer" ></td>

                    </tr>
                    <tr>
                        <td class="formtdtext">审核标志:</td>
                        <td><input class="easyui-validatebox"  type="text" name="nodetype" name="auflag" data-options="required:true" value="1"></td>
                        <td class="formtdtext">回退:</td>
                        <td><input class="easyui-validatebox"  type="text" name="nodetype" name="rbflag" data-options="required:true" value="1"></td>


                    </tr>
                    <tr>
                        <td class="formtdtext">参数1:</td>
                        <td><input type="text" name="param1" ></td>
                        <td class="formtdtext">参数2:</td>
                        <td><input type="text" name="param2"></td>


                    </tr>
                    <tr>
                        <td class="formtdtext">创建时间:</td>
                        <td><input type="text" name="createdate"></td>
                        <td class="formtdtext">拥有者:</td>
                        <td><input type="text" name="owner"></td>
                    </tr>
                    <tr>
                        <td class="formtdtext">备注:</td>
                        <td colspan="3"><textarea name="otherfamilyinfo" style="height: 60px;width: 60%;"></textarea></td>

                    </tr>
                </table>
            </div>

        </fieldset>

    </div>

</div>
</form>
</body>
</html>
<script>
$(function(){
    var funObj;
    $('#functiontree').tree({
        checkbox:true,
        url:'lr.do?model=manager.Function&eventName=queryFunctionTreeMng',
        onClick:function(node){
            funObj=node;
            $('#form').form('load','lr.do?model=manager.Function&eventName=queryFunctionById&node='+node.functionid);
        }
    });
    $('#newFun').bind('click',function(){
        if(!funObj){
            alert('请选择节点!');return;
        }
         if(!funObj.leaf){
             $('#form').form('clear').form('load',{
                 parent:funObj.functionid,
                 functionid:'-1'
             })
         }
    });
    $('#saveFun').bind('click',function(){
        $('#form').form('submit', {
            url:'lr.do?model=manager.Function&eventName=saveFunction',
            onSubmit: function(){
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.progress('close');	// hide progress bar while the form is invalid
            }
            return isValid;	// return false will stop the form submission
        },
        success: function(res){
            alert(res)
            $.messager.progress('close');	// hide progress bar while submit successfully
        }
    });
    });
    $('#deleteFun').bind('click',function(){
        $.ajax(
                {
                    type: "POST",
                    data: { functionid : funObj.functionid },
                    url:'lr.do?model=manager.Function&eventName=deleteFunction',
                    success:function(){alert('success')}
                }
        )
    })
    //$('functiontree').tree('expandAll');
})

</script>