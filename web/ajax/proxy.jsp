<%@ page import="Pension.conmmon.UrlConnectHelper" %>
<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 13-8-9
  Time: 上午10:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    if(request.getParameter("url")==null){
       out.print("缺少参数url");
    }
    else{
        String url=request.getParameter("url");
        String params=request.getParameter("params");
        out.print(UrlConnectHelper.sendPost(url,params));

    }
%>