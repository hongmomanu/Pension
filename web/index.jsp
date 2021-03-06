<%@ page import="Pension.listener.SessionListener" %>
<%@ page import="Pension.common.sys.util.CurrentUser" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Object o=request.getSession().getAttribute("user");
    CurrentUser user=new CurrentUser();
    if(o==null) {
        response.sendRedirect("login.jsp");
    }else{
        user=(CurrentUser)o;
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

    <script type="text/javascript">

        /*session全局变量*/
        var onlinenums=<%= SessionListener.size()%>;
        var userid='<%=user.getUserid()%>';
        var username="<%=user.getUserName()%>";
        var displayname=username;
        var regionid=<%=user.getRegionid()%>;
        var dvname='<%=user.getDvname()%>';


    </script>

    <script>
        /**加载easyui**/
        document.write('<script type="text/javascript"  src="'+extEasyui+
                'jquery-1.8.0.min.js"><\/script>');
        document.write('<script type="text/javascript"  src="'+extEasyui+
                'jquery.easyui.min.js"><\/script>');
        document.write('<script type="text/javascript"  src="'+extEasyui+
                'locale/easyui-lang-zh_CN.js"><\/script>');
        document.write('<link rel="stylesheet" type="text/css" id="swicth-style" href="'+extEasyui+
                'themes/default/easyui.css"><\/>');


    </script>
    <link rel="stylesheet" type="text/css" href="index.css">


</head>
<body class="easyui-layout" id="mainlayoutpanel" border="false">
<div region="north" border="false" class="cs-north" >
    <div class="cs-north-bg">
        <div class="cs-north-logo"></div>
        <ul id="headnavul">
            <li class="li-skinitem" ><img src="img/head/1.png"/><a id="welcomename">欢迎您:</a></li>
            <li class="li-skinitem"><img src="img/head/2.png"/><a id="domneedtodocount" style="cursor:pointer">待办业务(55)</a></li>
            <li class="li-skinitem"><img src="img/head/4.png"/><a id="onlinenums">在线人数(1)</a></li>
            <li class="li-skinitem"><img src="img/head/5.png"/><a id="domshowalterpwd">重设密码</a></li>
            <li class="li-skinitem"><img src="img/head/7.png"/> <a id="domlogout" style="text-decoration: none;cursor:pointer">退出</a></li>
            <li class="li-skinitem"><span id="currentDate"></span></li>
        </ul>


    </div>
</div>
<div region="west" id="westpanel" border="true" split="true" title="业务导航" class="cs-west" style="border-right: 0">
    <div id="menu_pension" fit="true" border="false">
    </div>
</div>
<div id="mainPanle" region="center" border="true" border="false">
    <div id="tabs" class="easyui-tabs"   fit="true" border="false" >
        <div title="主页" class="indexbackground">
            <%--主页有图片时显示会有闪烁--%>
            <div class="cs-home-remark">
                <h3>欢迎使用养老服务管理系统</h3>
            </div>
        </div>
    </div>
</div>



</body>
</html>



<script src="js/cj.js"></script>
<script data-main="js/mainapp" src="require.js"></script>
<script type="text/javascript"  src="js/commonfuncs/jquery.json-2.4.js"></script>

<link rel="stylesheet" type="text/css"  href="js/commonfuncs/ext/popwin.css">
<script type="text/javascript"  src="js/commonfuncs/ext/popwin.js"></script>
<link rel="stylesheet" type="text/css"  href="js/jqueryplugin/upload/uploadify.css">
<script src="js/jqueryplugin/jquery.uploadify.min.js"></script>
