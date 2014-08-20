<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="js/extLocation.js"></script>
</head>
<body>
</body>
</html>
<script>
    document.write('<script type="text/javascript"  src="'+extEasyui+
            'jquery-1.8.0.min.js"><\/script>');
    document.write('<script type="text/javascript"  src="'+extEasyui+
            'jquery.easyui.min.js"><\/script>');
    document.write('<script type="text/javascript"  src="'+extEasyui+
            'locale/easyui-lang-zh_CN.js"><\/script>');
    document.write('<link rel="stylesheet" type="text/css"  href="'+extEasyui+
            'themes/default/easyui.css"><\/>');
    document.write('<link rel="stylesheet" type="text/css"  href="'+
            'img/css/easyui_overwrite.css"><\/>');

    var cj=parent.cj;
</script>
<%--<script type="text/javascript" src="js/cj.js"></script>--%>
<jsp:include page="${requestScope.page}" flush="true" />
