<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <link rel="stylesheet" type="text/css" href="index.css">
</head>
<body>

    <P>第一组</p>

    <div>
        <p><input type="radio" name="radio1"/> <label>Option 1</label></p>
        <p><input type="radio" name="radio1"/> <label>Option 2</label></p>
        <p><input type="radio" name="radio1"/> <label>Option 3</label></p>
        <p><input type="radio" name="radio1"/> <label>Option 4</label></p>
    </div>

    <table class="formtable">
        <tr>
            <td rowspan="5">经济条件</td>
            <td>分类</td>
            <td>判断评分</td>
            <td>本人收入</td>
        </tr>
        <tr>
            <td>退休工资(2000元-3000元)</td>
            <td><input name="jj_shour" type="radio" value="10" /><label>10分</label></td>
            <td><p><input type="radio" name="radio2"/> <label>Option 1</label></p></td>
        </tr>
        <tr>
            <td>低退休工资(2000元以下)</td>
            <td><input name="jj_shour" type="radio" value="15" /><label>15分</label></td>
            <td><p><input type="radio" name="radio2"/> <label>Option 2</label></p></td>
        </tr>
        <tr>
            <td>无社保</td>
            <td><input name="jj_shour" type="radio" value="20" /><label>20分</label></td>
            <td><p><input type="radio" name="radio2"/> <label>Option 3</label></p></td>
        </tr>
        <tr>
            <td>低保</td>
            <td><input name="jj_shour" type="radio" value="25" /><label>25分</label></td>
            <td><p><input type="radio" name="radio2"/> <label>Option 4</label></p></td>
        </tr>
        <tr><td>评分</td>
            <td colspan="3">
                <input name="jj_pingguf">
            </td>
        </tr>
        <tr><td>评估员</td>
            <td colspan="3">
                <input name="jj_pingguy">
            </td>
        </tr>
    </table>
</body>
</html>
<script>
    $("div").cssRadio();
</script>