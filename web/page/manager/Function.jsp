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
<div id="functionmng" class="easyui-layout" style="width:1024px;height:100%;margin: 0px auto;">
    <div data-options="region:'west',title:'功能树',split:false" style="width:300px;">
        <ul id="functiontree"></ul>
    </div>
    <form id="form">
    <div data-options="region:'center',title:'功能详细信息'">
        <fieldset ><legend>功能</legend>
            <div>
                <table class="formtable">
                    <tr>
                        <td class="formtdtext">地址:</td>
                        <td colspan="3"><input name="location" size="70"></td>

                    </tr>
                    <tr>
                        <td class="formtdtext">中文名:</td>
                        <td><input class="easyui-combobox lazy-combobox" type="text" searchtype="dbglapplytype" name="TITLE"></td>
                        <td class="formtdtext">父节点:</td>
                        <td><input class="easyui-combobox lazy-combobox" type="text" name="parent"></td>
                    </tr>
                    <tr>
                        <td class="formtdtext">序号:</td>
                        <td><input class="easyui-validatebox" type="text" name="orderno"></td>
                        <td class="formtdtext">节点类型:</td>
                        <td><input class="easyui-validatebox"  type="text" name="nodetype"></td>

                    </tr>
                    <tr>
                        <td class="formtdtext">功能类型:</td>
                        <td><input class="easyui-combobox lazy-combobox" type="text" name="type"></td>
                        <td class="formtdtext">描述:</td>
                        <td><input  type="text" name="description"></td>
                    </tr>
                    <tr>
                        <td class="formtdtext">是否日志:</td>
                        <td><input  type="text" name="log" ></td>
                        <td class="formtdtext">开发者:</td>
                        <td><input  type="text" name="developer" ></td>

                    </tr>
                    <tr>
                        <td class="formtdtext">审核标志:</td>
                        <td><input  type="text" name="auflag" ></td>
                        <td class="formtdtext">回退:</td>
                        <td><input type="text" name="rbflag"></td>


                    </tr>
                    <tr>
                        <td class="formtdtext">参数1:</td>
                        <td><input type="text" name="param1" ></td>
                        <td class="formtdtext">参数2:</td>
                        <td><input type="text" name="PARAM2"></td>


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
    </form>
</div>
</body>
</html>
<script>
$(function(){
    $('#functiontree').tree({
        checkbox:true,
        url:'lr.do?model=manager.Function&eventName=queryFunctionTreeMng',
        onClick:function(){
            alert(node.id)
            //$('#form').form('load','lr.do?model=manager.Function&eventName=queryFunctionById&node='+node.id);
        }
    });
    //$('functiontree').tree('expandAll');
})

</script>