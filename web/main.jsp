<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="js/extLocation.js"></script>
</head>
<body>
</body>
</html>
<script>
    document.write('<script type="text/javascript"  src="'+extLocation+
            'jquery-1.8.0.min.js"><\/script>');
    document.write('<script type="text/javascript"  src="'+extLocation+
            'jquery.easyui.min.js"><\/script>');
    document.write('<script type="text/javascript"  src="'+extLocation+
            'locale/easyui-lang-zh_CN.js"><\/script>');
    document.write('<link rel="stylesheet" type="text/css" id="swicth-style" href="'+extLocation+
            'themes/default/easyui.css"><\/>');
</script>
<jsp:include page="${requestScope.page}" flush="true" />
