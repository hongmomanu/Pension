<%@ page import="Pension.manager.configmanager.business.GetMessageControl" %>
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
    if(request.getParameter("type")==null){
        out.print("缺少参数type");
    }
    else{

        String type=request.getParameter("type");
        GetMessageControl control = new GetMessageControl();
        out.print(control.getEnumsBytype(type));
    }
%>