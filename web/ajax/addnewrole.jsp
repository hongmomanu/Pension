<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 13-8-9
  Time: 上午10:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="Pension.manager.usermanager.business.RoleControl" %>
<%@ page language="java"  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    if(request.getParameter("rolename")==null){
       out.print("缺少参数rolename");
    }
    else{
        String rolename=request.getParameter("rolename");
        RoleControl role=new RoleControl();
        out.print(role.addNewRole(rolename));
    }
    //out.print("ok");
%>