<%-- 
    Document   : index
    Created on : 2013-1-26, 12:51:25
    Author     : jack
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1" >

    <title>用户登入</title>
    <script src="js/md5.js" type="text/javascript"></script>
    <script src="js/enc-base64-min.js" type="text/javascript"></script>
    <style type="text/css">
        html,body{
            margin: 0;
        padding: 0;
            height: 100%;
        }
            /*html {background-color:#06294e;}*/
        body { background-position:50% 50%; background-repeat:no-repeat;}            /*background-image:url(img/bg01.jpg);*/
        form {padding-top:300px; text-align:center}

        table, tr, td, th, input{ border:none;}
            /*.table-4 {text-align:center}
            .table-4 th, .table-4 td{padding:2px}
            .table-4 tr{ height:30px;padding: 1px}*/
        .rowhead {font-family:'微软雅黑',Tahoma,arial,verdana,sans-serif;width:80px;
            font-weight:normal; font-size:14px;  border:none; text-align:right;}

        .text-2  {width:150px; height:22px; background-color:#a4c5e0; border:1px solid #035793; font-size:16px; font-weight:bold}
        .select-2{width:150px}

        #welcome     {background:none; text-align:left; border:none; color:#FFF; padding-top:8px; font-size:16px; font-weight:normal}
        #poweredby   {color:#fff; margin-top:30px; height:50px; text-align:center; line-height:1; font-size:14px;}
        #poweredby a {color:#fff}
        #keeplogin   {color:white; font-size:14px; text-align:left;}
        .button-s {height:30px; width:80px; font-size:14px; font-weight:bold; border:none; text-align:right;}
        .debugwin, .helplink{display:none}
        #updater {width:500px; height:30px; border:none; overflow:hidden;}

        .hidden{display:none}   /* Add it to avoid the white flash when load css later for the updater. */
        #demoUsers {margin:25px 0px -15px 0px;}
        #demoUsers span{color:white}
        #demoUsers a{color:yellow}
        #browserlinkdiv{
            position: absolute;
            left: 10px;
            bottom: 20px;
            text-align: left;
            font-size: 13px;
        }
        #browserlinkdiv a{

        }
        .inputarea{
            position: absolute;
            bottom: 50px;
            left: 250px;
            background: url(img/bg03.jpg);
            width: 589px;
            height: 440px;
        }
        table{
            margin: 200 auto;

        }
    </style>

    <%--<link rel="stylesheet" type="text/css" href="css/login.css" />--%>

    <script type="text/javascript">

    </script>

</head>

<body>

<header class="body">


    <h1>

        <%-- <a style="padding-right:10px;text-decoration:none;color:white;   "  href="login.jsp">用户登录</a>
         <a style="padding-right:10px;text-decoration:none;display: none"  href="register.jsp">新用户</a>--%>
    </h1>

</header>
<div style="background-image: url(img/bg01.jpg);height: 80%;">
<section class="body">
    <%--<form method="post" action="login">
        <label>用户名</label>
        <input name="username" required="true" placeholder="在这里输入用户名">
        <label>密码</label>
        <input name="password" required="true"  placeholder="在这里输入密码" type="password">
        <input id="submit" type="submit" value="登录" name="submit">

    </form>--%>
    <form id="myform" method='post' action="login" >
        <div class="inputarea">
        <table class='table-4'>
            <!--<caption id='welcome'>欢迎使用舟山市民政救助系统</caption>-->
            <tr>
                <td class='rowhead'>用户名：</td>
                <td><input tabindex="1" class='text-2' type='text' required="true" placeholder="在这里输入用户名" name='username' id='account' /></td>
                <!--<td rowspan='2'>
                    <input type='submit' id='submit' value='登录' class='button-s' style="text-align:center" />
                </td>-->
                <td rowspan="2" align="center" >
                    <div tabindex="3" id="img_div">
                        <img id="loginbtn" src="img/loginbtn.png" onclick="javascript:btnClick();">
                        <!--<input type='submit' id='submit' value='登录' class='button-s' style="text-align:center" />-->
                    </div>
                </td>
            </tr>

            <tr>
                <td class='rowhead'>密&nbsp;&nbsp;&nbsp;码：</td>
                <td><input tabindex="2" class='text-2' id="password" type='password' required="true" placeholder="在这里输入密码"
                           name='password' onkeydown="javascript:onEnterKeyDown(this,event);"/></td>

            </tr>
            <tr>
                <td colspan="2" align="right" style="font-size: 13px;">&nbsp;<%= request.getSession().getAttribute("loginerromsg")==null?"":((String)request.getSession().getAttribute("loginerromsg")).trim()%></td>
            </tr>

        </table>
        </div>
        <div id='poweredby'>
            技术支持：浙江省海予信息技术有限公司
        </div>
        <div id='browserlinkdiv'>
            <span>如果系统不流畅，建议使用谷歌浏览器:</span><a href="http://192.168.2.141:8080/extjs4.2/GoogleChromeframeStandaloneEnterprise.msi">下载</a>
        </div>
        <input type="hidden" name="params" id="params" value=""/>
    </form>
        <%
            request.getSession().setAttribute("loginerromsg",null);
        %>

</section>
</div>

<div id="center" style=" height: 20%; background-image: url(img/bg02.jpg)" >
</div>
</body>
<!--[if lte IE 8]>
   <script type="text/javascript"
    src="http://ajax.googleapis.com/ajax/libs/chrome-frame/1/CFInstall.min.js" charset="gb2312"></script>

   <style>
    .chromeFrameInstallDefaultStyle {
      width: 100%; /* default is 800px */
      border: 5px solid blue;
    }
   </style>

   <div id="prompt">
    <!-- if IE without GCF, prompt goes here -->
</div>

<script>
    // The conditional ensures that this code will only execute in IE,
    // Therefore we can use the IE-specific attachEvent without worry
    /*window.attachEvent("onload", function() {
        var sUserAgent = navigator.userAgent;
        var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1
                || sUserAgent.indexOf("Windows XP") > -1;
        if(isWinXP){
            CFInstall.check({
                mode: "overlay", // the default
                node: "prompt",
                url: "http://www.google.com/chromeframe/?user=true"
            });
        }else{
            //alert("非xp");
        }

    });*/
</script>
<![endif]-->

</html>
<script type="text/javascript">

    window.onload=function(){
        /*var a=CryptoJS.MD5("hvit");
        a= CryptoJS.enc.Base64.stringify(a);
        alert(a)*/
        document.getElementById("account").focus();

        var imgdiv=document.getElementById('img_div');

        if(document.addEventListener){
            imgdiv.addEventListener('keydown',onKeyDown);
        }else if(document.attachEvent){
            imgdiv.attachEvent('onkeydown',onKeyDown);
        }
    };


    function btnClick(){
        var password=document.getElementById("password");
        var username=document.getElementById("username");
        if(''!=username||''!=password){
            //password.value=CryptoJS.enc.Base64.stringify(CryptoJS.MD5(password.value));
             //加密
            document.getElementById("myform").submit();
        }

    }
    function onEnterKeyDown(src,e){
        var keyPressed;
        if (window.event){
            keyPressed = window.event.keyCode; // IE
        }
        else{
            keyPressed = e.which; // Firefox
        }
        if (keyPressed == 13) {
            btnClick();
        }
    }

    function onKeyDown(e){
        onEnterKeyDown('',e);
    }



</script>