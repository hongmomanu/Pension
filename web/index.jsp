<%@ page import="Pension.listener.SessionListener" %>
<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 13-12-31
  Time: 下午2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(request.getSession().getAttribute("username")==null) {
        response.sendRedirect("login.jsp");
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires"CONTENT="0">


    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1" >
    <title>杭州市民政养老救助管理系统</title>

    <script type="text/javascript" src="js/extLocation.js"></script>
    <script type="text/javascript" src="js/config.js"></script>
    <script type="text/javascript">

        /*session全局变量*/
        var onlinenums=<%= SessionListener.size()%>;
        var userid='<%=request.getSession().getAttribute("userid")%>';
        var username="<%=request.getSession().getAttribute("username")%>";
        var roleid=<%=request.getSession().getAttribute("roleid")%>;
        var displayname="<%=request.getSession().getAttribute("displayname")%>";
        var divisionpath="<%=request.getSession().getAttribute("divisionpath")%>";
        var password="<%=request.getSession().getAttribute("password")%>";
        var divisionid=<%=request.getSession().getAttribute("divisionid")%>;


    </script>

    <script>
        /**加载easyui**/

        document.write('<script type="text/javascript"  src="'+extLocation+
                'jquery-1.8.0.min.js"><\/script>');
        document.write('<script type="text/javascript"  src="'+extLocation+
                'jquery.easyui.min.js"><\/script>');
        document.write('<script type="text/javascript"  src="'+extLocation+
                'locale/easyui-lang-zh_CN.js"><\/script>');
        document.write('<link rel="stylesheet" type="text/css" id="swicth-style" href="'+extLocation+
                'themes/gray/easyui.css"><\/>');//resources/css/ext-all.css

    </script>
    <link rel="stylesheet" type="text/css" href="index.css">
    <script data-main="js/mainapp" src="require.js"></script>
    <script type="text/javascript"  src="js/commonfuncs/jquery.json-2.4.js"></script>

</head>
<body class="easyui-layout" id="mainlayoutpanel">
<div region="north" border="true" class="cs-north" >
    <div class="cs-north-bg">
        <div class="cs-north-logo">

        </div>


        <ul class="ui-skin-nav" style="display: block;">
            <li class="li-skinitem" title="gray"><span class="gray" rel="gray"></span></li>
            <li class="li-skinitem" title="default"><span class="default" rel="default"></span></li>
            <li class="li-skinitem" title="bootstrap"><span class="bootstrap" rel="bootstrap"></span></li>
            <li class="li-skinitem" title="black"><span class="black" rel="black"></span></li>
            <li class="li-skinitem" title="metro"><span class="metro" rel="metro"></span></li>
            <li class="li-skinitem" title="pepper-grinder"><span class="pepper-grinder" rel="pepper-grinder"></span></li>
            <li class="li-skinitem" title="blue"><span class="blue" rel="blue"></span></li>
            <li class="li-skinitem" title="cupertino"><span class="cupertino" rel="cupertino"></span></li>
            <li class="li-skinitem" title="dark-hive"><span class="dark-hive" rel="dark-hive"></span></li>
            <li class="li-skinitem" title="sunny"><span class="sunny" rel="sunny"></span></li>
        </ul>

        <ul id="headnavul">
            <li class="li-skinitem" ><img src="img/head/1.png"/><a id="welcomename">欢迎您:</a></li>
            <li class="li-skinitem"><img src="img/head/2.png"/><a id="domneedtodocount" style="cursor:pointer">待办业务(55)</a></li>
            <li class="li-skinitem"><img src="img/head/4.png"/><a id="onlinenums">在线人数(1)</a></li>
            <li class="li-skinitem"><img src="img/head/5.png"/><a id="domshowalterpwd">重设密码</a></li>
            <li class="li-skinitem"><img src="img/head/7.png"/>
                <a id="domlogout" style="text-decoration: none;">退出</a></li>
            <li class="li-skinitem">
                <input id="routermenu" class="easyui-combobox" style="position: absolute;top:20px;float: left;padding:0;" data-options="
        valueField: 'value',
        mode:'remote',
        textField: 'name',
        url: 'ajax/getfuncsbyrule.json'">

            </li>
        </ul>


    </div>
</div>
<div region="west" id="westpanel" border="true" split="true" title="业务导航" class="cs-west">

</div>
<div id="mainPanle" region="center" border="true" border="false">
    <div id="tabs" class="easyui-tabs"   fit="true" border="false" >
        <div title="主页" class="indexbackground">
            <div class="cs-home-remark">
                <h1>欢迎使用民政养老救助系统</h1>
            </div>
        </div>
    </div>
</div>

<%--<div region="south" border="false" class="cs-south">
    <a  id="indextime"></a>
</div>--%>


</body>
</html>
